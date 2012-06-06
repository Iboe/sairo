/**
 * 
 */
package de.fhb.sailboat.communication;


/**
 * Base class for the mission transmitter and receiver. <br> 
 * It contains the enumeration definitions for transmission, operation and error modes.
 * 
 * @author Michael Kant
 *
 */
public class MissionNegotiationBase {

	/**
	 * Enumeration, describing the types of packets that can be sent within the mission transmission process.
	 * 
	 * @author Michael Kant
	 *
	 */
	public enum eOperationType{
		
		OT_BeginMission(1),
		OT_CancelMission(2),
		OT_EndMission(3),
		OT_NewTask(4),
		OT_Error(5),
		OT_NoOp(6),
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
	
	/**
	 * Enumeration of error types that may occur within the states of mission transmission.
	 * 
	 * @author Michael Kant
	 *
	 */
	public enum eErrorType{
		
		ET_None(0),
		ET_WrongOPType(1),
		ET_BadOPType(2);
		
		
		private int typeId;
		
		private eErrorType(int id){
		
			typeId=id;
		}
		
		public int getValue(){
			
			return typeId;
		}
		
		public static eErrorType getByValue(int val)
	    {
			eErrorType errType = null;

	        for (eErrorType err : eErrorType.values())
	        {
	            if(val == err.getValue())
	            {
	                errType = err;
	                break;
	            }
	        }

	        return errType;
	    }
	}
	
	/**
	 * Enumeration, describing the state of the mission transmission.
	 * 
	 * @author Michael Kant
	 *
	 */
	public enum eTransmissionMode {
		
		TM_Idle(0),
		TM_MissionBegin(1),
		TM_MissionBegin_ACK(2),
		TM_Task_Wait(3),
		TM_Task_ACK(4),
		TM_MissionEnd(5),
		TM_MissionEnd_ACK(6),
		TM_MissionCancel(7),
		TM_MissionCancel_ACK(8);
		
		private int modeId;
		
		private eTransmissionMode(int id){
		
			modeId=id;
		}
		
		public int getValue(){
			
			return modeId;
		}
		
	}
}
