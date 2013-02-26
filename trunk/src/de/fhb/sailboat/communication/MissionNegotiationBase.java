/**
 * 
 */
package de.fhb.sailboat.communication;


/**
 * Base class for the mission transmitter and receiver, which contains the enumeration definitions for transmission, operation and error modes.
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
		
		/**
		 * Internal id, associated with the enum value.
		 */
		private int typeId;
		
		/**
		 * Private constructor.
		 * @param id The id associated with the operation type.
		 */
		private eOperationType(int id){
		
			typeId=id;
		}
		
		/**
		 * Returns the id value of the current {@link eOperationType} instance.
		 * @return the id value of the current {@link eOperationType} instance.
		 */
		public int getValue(){
			
			return typeId;
		}
		
		/**
		 * Getting the enum instance that's related to the given id.
		 * @param val The id to get the enum instance for.
		 * @return The enum instance if the id is valid, otherwise null.
		 */
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
		
		/**
		 * Internal id, associated with the enum value.
		 */
		private int typeId;
		
		/**
		 * Private constructor.
		 * @param id The id associated with the operation type.
		 */
		private eErrorType(int id){
		
			typeId=id;
		}
		
		/**
		 * Returns the id value of the current {@link eErrorType} instance.
		 * @return the id value of the current {@link eErrorType} instance.
		 */
		public int getValue(){
			
			return typeId;
		}
		
		/**
		 * Getting the enum instance that's related to the given id.
		 * @param val The id to get the enum instance for.
		 * @return The enum instance if the id is valid, otherwise null.
		 */
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
	 * Enumeration, describing the possible states of the mission transmission.
	 * 
	 * @author Michael Kant
	 *
	 */
	public enum eTransmissionMode {
		
		TM_Idle(0),
		TM_MissionBegin(1),
		TM_MissionBegin_ACK(2),
		TM_Task_New(3),
		TM_Task_Wait(4),
		TM_Task_ACK(5),
		TM_MissionEnd(6),
		TM_MissionEnd_ACK(7),
		TM_MissionCancel(8),
		TM_MissionCancel_ACK(9);
		
		/**
		 * Internal id, associated with the enum value.
		 */
		private int modeId;
		
		/**
		 * Private constructor.
		 * @param id The id associated with the operation type.
		 */
		private eTransmissionMode(int id){
		
			modeId=id;
		}
		
		/**
		 * Returns the id value of the current {@link eTransmissionMode} instance.
		 * @return the id value of the current {@link eTransmissionMode} instance.
		 */
		public int getValue(){
			
			return modeId;
		}
		
	}
}
