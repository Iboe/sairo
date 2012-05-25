/**
 * 
 */
package de.fhb.sailboat.communication.serverModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.Task;

/**
 * @author Michael Kant
 *
 */
public class MissionReceiver implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(MissionReceiver.class);
	
	
	public enum eOperationType{
		
		OT_BeginMission(1),
		OT_CancelMission(2),
		OT_EndMission(3),
		OT_NewTask(4),
		OT_Error(5),
		OT_BeginMission_ACK(9),
		OT_CancelMission_ACK(10),
		OT_EndMission_ACK(11),
		OT_NewTask_ACK(12);
		
		
		private int typeId;
		
		private eOperationType(int id){
		
			typeId=id;
		}
		
		public int getValue(){
			
			return typeId;
		}
		
		public static eOperationType getByValue(int val)
	    {
			eOperationType opType = null;

	        for (eOperationType op : eOperationType.values())
	        {
	            if(val == op.getValue())
	            {
	                opType = op;
	                break;
	            }
	        }

	        return opType;
	    }
	}
	
	public enum eTransmissionMode {
		
		TM_Idle(0),
		TM_MissionBegin_AACK(1),
		TM_Task_Wait(2),
		TM_Task_AACK(3),
		TM_MissionEnd_AACK(4),
		TM_MissionCancel_AACK(5);
		
		private int modeId;
		
		private eTransmissionMode(int id){
		
			modeId=id;
		}
		
		public int getValue(){
			
			return modeId;
		}
		
	}
	
	private eTransmissionMode mode;
	private CommunicationBase base;
	private List<Task> missionAssembly;
	private Task pendingTask;
	
	public MissionReceiver(CommunicationBase base){
	
		this.base=base;
		mode=eTransmissionMode.TM_Idle;
		missionAssembly=null;
		pendingTask=null;
	}
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#receivedObject(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int signature;
		byte opCode=0; //TODO
		
		signature=stream.read();
		
		opCode=(byte)(signature & 0x0F);
		
		switch(mode){
		
			case TM_Idle:
				if(opCode == eOperationType.OT_BeginMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionBegin_AACK;
					//initiate mission acknowledge
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
				
				break;
					
			case TM_MissionBegin_AACK:
				if(opCode == eOperationType.OT_BeginMission_ACK.getValue()){
					
					missionAssembly=new LinkedList<Task>();
					mode=eTransmissionMode.TM_Task_Wait;
				}
				else
					LOG.warn("Mode: "+mode+" - Invalid operation type ("+ eOperationType.getByValue(opCode) +") for the current mode.");
				
				break;
				
			case TM_Task_Wait:
				if(opCode == eOperationType.OT_NewTask.getValue()){
					
					//TODO: pendingTask=
					mode=eTransmissionMode.TM_Task_AACK;
				}
				else if(opCode == eOperationType.OT_CancelMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionCancel_AACK;
				}
				else if(opCode == eOperationType.OT_EndMission.getValue()){
					
					mode=eTransmissionMode.TM_MissionEnd_AACK;
				}
				break;
			case TM_Task_AACK:
			case TM_MissionEnd_AACK:
			case TM_MissionCancel_AACK:
				
		}

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 */
	@Override
	public boolean skipNextCycle() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
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
