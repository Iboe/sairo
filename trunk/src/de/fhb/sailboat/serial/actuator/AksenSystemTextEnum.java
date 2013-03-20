package de.fhb.sailboat.serial.actuator;

/***
 * This enumeration provides standarded textblocks for text outputs about the aksen system
 * 
 * @author Tobias Koppe
 * @version 1.0
 */
public enum AksenSystemTextEnum {
	
	/**
	 * This standarded textblock is used if got command <<set rudder to left>>
	 */
	AKSEN_COMMAND_RUDDER_LEFT {
		public String toString() {
			return "AKSEN Servocommand: set rudder to left";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set rudder to right>>
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
			return "";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set sail sheet out>>
	 */
	AKSEN_COMMAND_SAIL_OUT{
		public String toString(){
			return "";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to minimum speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_MIN{
		public String toString(){
			return "";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to maximum speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_MAX{
		public String toString(){
			return "";
		}
	},
	
	/**
	 * This standarded textblock is used if got command <<set propellor to normal speed>>
	 */
	AKSEN_COMMAND_PROPELLOR_NORMAL{
		public String toString(){
			return "";
		}
	},
	
	/**
	 * This standarded textblock is used if received character n from aksen-board
	 */
	AKSEN_COMMAND_RECEIVED_FAILURE{
		public String toString(){
			return "FAILURE: Received character n from AKSEN-Board.";
		}
	}

}
