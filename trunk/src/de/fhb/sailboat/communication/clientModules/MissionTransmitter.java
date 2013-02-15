
package de.fhb.sailboat.communication.clientModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jmx.snmp.tasks.TaskServer;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.MissionNegotiationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.communication.MissionNegotiationBase.eOperationType;
import de.fhb.sailboat.communication.serverModules.MissionReceiver;
import de.fhb.sailboat.control.Planner;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.Task;

/**
 * Transmitter module for serializing and sending a mission with all its {@link Task}s over the underlying {@link OutputStream}.<br>
 * The module ensures a reliable transmission of the mission to the connected endpoint, using three-way-handshake and mission transmission modes.
 * 
 * @author Michael Kant
 *
 */
public class MissionTransmitter extends MissionNegotiationBase implements TransmissionModule,Planner{

	private static final Logger LOG = LoggerFactory.getLogger(MissionTransmitter.class);
	
	private eTransmissionMode mode;
	private CommunicationBase base;
	private List<Task> missionAssembly;
	//private Task pendingTask;
	
	/**
	 * 
	 * @param base
	 */
	public MissionTransmitter(CommunicationBase base){
	
		this.base=base;
		mode=eTransmissionMode.TM_Idle;
		missionAssembly=null;
		//pendingTask=null;
		//error=eErrorType.ET_None;
	}
	
	//Planner methods
	/**
	 * Takes the given mission and instigates the transmission to the remote endpoint.
	 */
	@Override
	public void doMission(Mission mission) {
		
		synchronized(mode){
				
			if(missionAssembly != null){
				
				missionAssembly.clear();
				missionAssembly=null;
			}
			missionAssembly = mission != null ? mission.getTasks() : null;
			
			if(mode == eTransmissionMode.TM_Idle){
				
				if(mission != null){
				
					LOG.debug("Start mission transmission.");
					mode=eTransmissionMode.TM_MissionBegin;
					base.requestTransmission(this);
				}
				//pendingTask=null;
				
			}
			else{
				
				
			}
		}
		
	}
	/**
	 * Not supported yet.
	 */
	@Override
	public void doPrimitiveCommand(PrimitiveCommandTask task) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	
	//Transmission module methods
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int signature;
		byte opCode=0; 
		
		signature=stream.read();
		
		opCode=(byte)(signature & 0x0F);
		
		LOG.warn("receivedObject - Mode: "+mode+" - received: " +eOperationType.getByValue(opCode));
		
		switch(mode){
		
		case TM_MissionBegin:
			//LOG.debug("receivedObject - TM_MissionBegin.");
			if(opCode == eOperationType.OT_BeginMission_ACK.getValue()){
				
				mode=eTransmissionMode.TM_MissionBegin_ACK;
				base.requestTransmission(this);
			}
		break;
			
		case TM_MissionBegin_ACK:
			//LOG.debug("receivedObject - TM_MissionBegin_ACK.");
			if(opCode == eOperationType.OT_BeginMission_ACK.getValue()){
				
				base.requestTransmission(this);
			}
		break;
		case TM_Task_New:
			//LOG.debug("receivedObject - TM_Task_New.");
			if(opCode == eOperationType.OT_NewTask_ACK.getValue()){
				
				mode=eTransmissionMode.TM_Task_ACK;
				base.requestTransmission(this);
			}
		break;
		case TM_Task_ACK:
			//LOG.debug("receivedObject - TM_Task_ACK.");
			if(opCode == eOperationType.OT_NewTask_ACK.getValue()){
				
				base.requestTransmission(this);
			}
		break;
		case TM_MissionEnd:
			//LOG.debug("receivedObject - TM_MissionBegin.");
			if(opCode == eOperationType.OT_EndMission_ACK.getValue()){
				
				mode=eTransmissionMode.TM_MissionEnd_ACK;
				base.requestTransmission(this);
			}
		break;
			
		case TM_MissionEnd_ACK:
			//LOG.debug("receivedObject - TM_MissionEnd_ACK.");
			if(opCode == eOperationType.OT_EndMission_ACK.getValue()){
				
				base.requestTransmission(this);
			}
		break;
			
		}		
	}

	@Override
	public boolean skipNextCycle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		switch(mode){
		case TM_Idle:
			break;
		case TM_MissionBegin:
			LOG.debug("requestObject - TM_MissionBegin.");
			stream.write(eOperationType.OT_BeginMission.getValue());
			//mode=eTransmissionMode.TM_MissionBegin_ACK;
		break;
		
		case TM_MissionBegin_ACK:
			LOG.debug("requestObject - TM_MissionBegin_ACK.");
			stream.write(eOperationType.OT_BeginMission_ACK.getValue());
			mode=eTransmissionMode.TM_Task_New;
		break;
		case TM_Task_New:
			LOG.debug("requestObject - TM_Task_New.");
			if(missionAssembly != null && missionAssembly.size() > 0);
			//stream.write(missionAssembly.get(0)... TODO: transmit task
			else
			{
				LOG.debug("requestObject - TM_Task_New - NO TASK LEFT!.");
				mode=eTransmissionMode.TM_MissionEnd;
			}
		break;
		case TM_Task_ACK:
			LOG.debug("requestObject - TM_Task_ACK.");
			stream.write(eOperationType.OT_NewTask_ACK.getValue());
			mode=eTransmissionMode.TM_Task_New;
		break;
		case TM_MissionEnd:
			LOG.debug("requestObject - TM_MissionEnd.");
			stream.write(eOperationType.OT_EndMission.getValue());
			//mode=eTransmissionMode.TM_MissionBegin_ACK;
		break;
		
		case TM_MissionEnd_ACK:
			LOG.debug("requestObject - TM_MissionEnd_ACK.");
			stream.write(eOperationType.OT_EndMission_ACK.getValue());
			mode=eTransmissionMode.TM_Idle;
		break;
		
		//case TM_
		}
	}

	@Override
	public void connectionReset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTransmissionInterval() {
		
		int interval=0;
		
		switch(mode){
		
		case TM_MissionBegin:
		case TM_MissionBegin_ACK:
		case TM_MissionEnd:
		case TM_MissionEnd_ACK:
			interval=2000;
		break;
		case TM_Task_New:
		case TM_Task_ACK:
			interval=1000;
		break;
		}
		return interval;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	
}
