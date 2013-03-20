package de.fhb.sailboat.serial.actuator;

/***
 * This enumeration provides standarded textblocks for text outputs about the aksen system
 * and simplify comparing between string outputs
 * 
 * @author Tobias Koppe
 * @version 1.1
 */
public enum AksenSystemTextEnum {
	
	/**
	 * This standarded textblock is used if got command "set rudder to left"
	 * @since 1.0
	 */
	AKSEN_COMMAND_RUDDER_LEFT {
		public String toString() {
			return "AKSEN Servocommand: set rudder to left";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set rudder to right>>
	 * @since 1.0
	 */
	AKSEN_COMMAND_RUDDER_RIGHT{
		public String toString(){
			return "AKSEN Servocommand: set rudder to right";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set rudder to normal>>
	 */
	AKSEN_COMMAND_RUDDER_NORMAL{
		public String toString(){
			return "AKSEN Servocommand: set rudder to normal";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set rudder to a given angle>>
	 */
	AKSEN_COMMAND_RUDDER_TOANGLE{
		public String toString(){
			return "AKSEN Servocommand: set rudder to angle: ";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set sail sheet in>>
	 */
	AKSEN_COMMAND_SAIL_IN{
		public String toString(){
			return "AKSEN Servocommand: set sail sheet in";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set sail sheet out>>
	 */
	AKSEN_COMMAND_SAIL_OUT{
		public String toString(){
			return "AKSEN Servocommand: set sail sheet out";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to minimum speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_MIN{
		public String toString(){
			return "AKSEN Servocommand: set propellor to minimum speed";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to maximum speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_MAX{
		public String toString(){
			return "AKSEN Servocommand: set propellor to maximum speed";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to normal speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_NORMAL{
		public String toString(){
			return "AKSEN Servocommand: set propellor to normal speed";
		}
	},
	
	/**
	 * This standarded textblock is used if received character n from aksen-board
	 */
	AKSEN_COMMAND_RECEIVED_FAILURE{
		public String toString(){
			return "FAILURE: Received character n from AKSEN-Board.";
		}
	},
	
	//TODO insert character which is sended to aksen board
	
	/**
	 * This standard textblock is used if the aksen board state are setted to 
	 * <<waiting for receiving commands>>
	 */
	AKSEN_SET_STATE_WAITINGFORCOMMANDS{
		public String toString(){
			return "AKSEN Board state setted to: waiting for receiving commands";
		}
	},
	
	//TODO insert character which is sended to aksen board
	
	/**
	 * This standard textblock is used if the aksen board state are setted to
	 * <<waiting for receiving to execute commands>> 
	 */
	AKSEN_SET_STATE_WAINTINGFOREXECUTION{
		public String toString(){
			return "AKSEN Board state setted to: waiting for receiving execute commands";
		}
	},
	
	//TODO insert character which is received to aksen board
	
	/**
	 * This standard textblock is used if the aksen board state are setted to
	 * <<inputted commands executed>>
	 */
	AKSEN_SET_STATE_COMMANDSEXECUTED{
		public String toString(){
			return "AKSEN Board set settetd to: inputted commands executed";
		}
	},
	
	/**
	 * @since 1.1
	 */
	AKSEN_COMMAND_REQUEST_CONNECTION{
		public String toString(){
			return "";
		}
	},
	
	AKSEN_COMMAND_GOT_ACKNOWLEDGE{
		public String toString(){
			return "";
		}
	},
	
	AKSEN_COMMAND_RECEIVED{
		public String toString(){
			return "";
		}
	},
	
	AKSEN_COMMAND_END_SENDING_COMMANDS{
		public String toString(){
			return "";
		}
	},
	
	AKSEN_COMMAND_ACKNOWLEDGE_COMMAND_SENDING{
		public String toString(){
			return "";
		}
	}

}
