/**
 * 
 */
package de.fhb.sailboat.communication.serverModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.MissionNegotiationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.communication.clientModules.MissionTransmitter;
import de.fhb.sailboat.communication.mission.TaskSerializer;
import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.Task;

/**
 * Transmitter module for receiving and deserializing a mission with all its {@link Task}s.<br>
 * The module ensures a reliable receiving process of the mission, using three-way-handshake and mission transmission modes.
 * This {@link TransmissionModule} is a sending and receiving module.
 * 
 * @author Michael Kant
 *
 */
public class MissionReceiver extends MissionNegotiationBase implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(MissionReceiver.class);
	
	private Planner planner;
	
	/**
	 * Current transmission mode of this {@link MissionReceiver}.
	 */
	private eTransmissionMode mode;
	
	/**
	 * Reference to the {@link CommunicationBase} instance where this module is registered at.
	 */
	private CommunicationBase base;
	
	/**
	 * Current pending list of tasks received.
	 */
	private List<Task> missionAssembly;
	
	/**
	 * Current pending Task in transmission process.
	 */
	private Task pendingTask;
	
	/**
	 * {@link TaskSerializer} instance that's used for deserializing the mission tasks.
	 */
	private TaskSerializer deserializer;
	
	/**
	 * Current error mode for handling errors. NOT USED YET.
	 */
	private eErrorType error;
	
	/**
	 * Initialization constructor.
	 * 
	 * @param base The {@link CommunicationBase} where this module was registered.
	 * @param planner The {@link Planner} to pass a received mission to.
	 */
	public MissionReceiver(CommunicationBase base, Planner planner){
	
		this.planner=planner;
		this.base=base;
		mode=eTransmissionMode.TM_Idle;
		missionAssembly=null;
		pendingTask=null;
		deserializer=new TaskSerializer();
		
		error=eErrorType.ET_None;
	}
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#receivedObject(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int signature;
		byte opCode=0; 
		
		signature=stream.read();
		
		opCode=(byte)(signature & 0x0F);
		
		LOG.debug("receivedObject - Mode: "+mode+" - received: " +eOperationType.getByValue(opCode)); 
		
		switch(mode){
		
			case TM_Idle:
				if(opCode == eOperationType.OT_BeginMission.getValue()){
					
					LOG.info("Mission receive started!");
					mode=eTransmissionMode.TM_MissionBegin_ACK;
					base.requestTransmission(this);
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_ACK;
					base.requestTransmission(this);
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
			break;
					
			case TM_MissionBegin_ACK:
				if(opCode == eOperationType.OT_BeginMission_ACK.getValue()){
					
					missionAssembly=new LinkedList<Task>();
					mode=eTransmissionMode.TM_Task_Wait;
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_ACK;
					base.requestTransmission(this);
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
			break;
				
			case TM_Task_Wait:
				if(opCode == eOperationType.OT_NewTask.getValue()){
					
					byte[] taskData;
					int size=stream.read();
					if(size > 0){
						
						taskData=new byte[size];
						if(stream.read(taskData) == size){
							
							pendingTask=deserializer.deserializeTask(taskData);
							
							if(pendingTask != null){
								
								mode=eTransmissionMode.TM_Task_ACK;
								base.requestTransmission(this);
							}
						}
					}
					
				}
				//if we receive a task ACK while waiting for new tasks, the other end didn't get our first ACK.. thus, resending 
				else if(opCode == eOperationType.OT_NewTask_ACK.getValue()){
					
					mode=eTransmissionMode.TM_Task_ACK;
					base.requestTransmission(this);
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_ACK;
					base.requestTransmission(this);
				}
				else if(opCode == eOperationType.OT_EndMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionEnd_ACK;
					base.requestTransmission(this);
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
				
			break;
				
			case TM_Task_ACK:
				if(opCode == eOperationType.OT_NewTask_ACK.getValue()){
					
					missionAssembly.add(pendingTask);
					pendingTask=null;
					mode=eTransmissionMode.TM_Task_Wait;
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_ACK;
					base.requestTransmission(this);
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
			break;
				
			case TM_MissionEnd_ACK:
				if(opCode == eOperationType.OT_EndMission_ACK.getValue()){
					
					Mission m=new MissionImpl();
					m.setTasks(missionAssembly);
					planner.doMission(m);
					LOG.info("Mission receive finished!");
					missionAssembly=null;
					mode=eTransmissionMode.TM_Idle;
					
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_ACK;
					//TODO: initiate mission cancel acknowledge
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");	
			break;
				
			case TM_MissionCancel_ACK:
				if(opCode == eOperationType.OT_CancelMission_ACK.getValue()){
					
					LOG.info("Mission receive canceled!");
					missionAssembly.clear();
					missionAssembly=null;
					mode=eTransmissionMode.TM_Idle;
				}	
			break;
				
		}

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 */
	@Override
	public boolean skipNextCycle() {
		
		//don't instigate a transmission within this two modes
		if(mode == eTransmissionMode.TM_Idle || mode == eTransmissionMode.TM_Task_Wait)
			return true;

		return false;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		int opCode;
		
			if(error == eErrorType.ET_None){
				
				switch(mode){
				
					case TM_MissionBegin_ACK:
						opCode=eOperationType.OT_BeginMission_ACK.getValue();
					break;	
					case TM_Task_ACK:
						opCode=eOperationType.OT_NewTask_ACK.getValue();
					break;
					case TM_MissionEnd_ACK:
						opCode=eOperationType.OT_EndMission_ACK.getValue();
					break;
					case TM_MissionCancel_ACK:
						opCode=eOperationType.OT_CancelMission_ACK.getValue();	
					break;
						
					case TM_Idle:
					case TM_Task_Wait:
					default:
						opCode=eOperationType.OT_NoOp.getValue();
					break;
				}
				LOG.warn("requestObject - Mode: "+mode+" - sending: " +eOperationType.getByValue(opCode));
			}
			else {
				
				opCode=eOperationType.OT_Error.getValue() | (error.getValue() << 4);
			}
				
		stream.write(opCode);
		
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#connectionReset()
	 */
	@Override
	public void connectionReset() {
		
		

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		int interval;
		switch(mode){
		
			case TM_MissionBegin_ACK:			
			case TM_Task_ACK:
			case TM_MissionEnd_ACK:
			case TM_MissionCancel_ACK:
				interval=2000;
			break;
			
			case TM_Idle:
			case TM_Task_Wait:
			default:
				interval=0;
		}

		return interval;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
