/**
 * 
 */
package de.fhb.sailboat.communication.serverModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.TransmissionModule;

/**
 * @author Michael Kant
 *
 */
public class MissionReceiver implements TransmissionModule {

	public enum eOperationType{
		
		OT_BeginMission(1),
		OT_CancelMission(2),
		OT_EndMission(3),
		OT_NewTask(4),
		OT_Error(5);
		
		
		private int typeId;
		
		private eOperationType(int id){
		
			typeId=id;
		}
		
		public int getValue(){
			
			return typeId;
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
	
	public MissionReceiver(CommunicationBase base){
	
		this.base=base;
		mode=eTransmissionMode.TM_Idle;
	}
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#receivedObject(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		byte opCode=0; //TODO
		
		switch(mode){
		
			case TM_Idle:
			case TM_MissionBegin_AACK:
			case TM_Task_Wait:
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
