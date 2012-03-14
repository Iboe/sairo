package de.fhb.sailboat.control;

import de.fhb.sailboat.data.GPS;

/**
 * 
 * @author Michael Kant
 */
public interface Pilot {

	/**
	 * Telling the pilot to change to a certain direction (angle), relative to the current direction.
	 * The angle range goes from -180° to 180°.
	 * If an angle above 180° is given, it will automatically trnform it to the corresponding negative angle. 
	 * 
	 * CURRENTLY: Just changes the rudder position to turn to the right direction initially.
	 *            A continuous observation and adjustment of the rudder based on the changing direction has yet to come.
	 * 
	 * @param angle The relative angle to the desired direction. 
	 */
	void driveAngle(int angle);
	/**
	 * TODO
	 * @param angle
	 */
	void holdAngleToWind(int angle);
	/**
	 * TODO
	 * @param point
	 */
	void driveToGPSPoint(GPS point);
}
