package de.fhb.sailboat.control.pilot;

/**
 * Enum for modes which determine the way for the {@link Pilot} to generate commands
 * for the rudder. 
 * 
 * @author hscheel
 *
 */
public enum DriveAngleMode {

	/**
	 * Controls the boat with main respect to the compass.
	 */
	COMPASS,
	
	/**
	 * Controls the boat with main respect to the wind sensor.
	 */
	WIND;
}
