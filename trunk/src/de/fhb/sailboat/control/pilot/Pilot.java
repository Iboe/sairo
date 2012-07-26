package de.fhb.sailboat.control.pilot;


/**
 * 
 * @author Michael Kant
 */
public interface Pilot {

	
	/**
	 * Name of the property defining the max relevant angle where the boat rudder 
	 * reaches the maximum deflection. 
	 */
	static final String MAX_RELEVANT_ANGLE_PROPERTY = Pilot.class.getSimpleName() + ".maxRelevantAngle";
	
	/**
	 * Name of the property defining the wait time between two planning cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Pilot.class.getSimpleName() + ".waitTime";
	
	/**
	 * Name of the property defining the ratio of p for the pid controller
	 */
	static final String P_PROPERTY = Pilot.class.getSimpleName() + ".pFactor";

	/**
	 * Name of the property defining the ratio of i for the pid controller
	 */
	static final String I_PROPERTY = Pilot.class.getSimpleName() + ".iFactor";

	/**
	 * Name of the property defining the ratio of d for the pid controller.
	 */
	static final String D_PROPERTY = Pilot.class.getSimpleName() + ".dFactor";
	
	/**
	 * Name of the property defining the ratio of k for desired heeling.
	 */
	static final String K_PROPERTY = Pilot.class.getSimpleName() + ".sailKfactor";
	
	/**
	 * Name of the property defining the max. Heeling of the boat.
	 */
	static final String Hmax_PROPERTY = Pilot.class.getSimpleName() + ".sailHmax";	
	
	/**
	 * Name of the property defining the max wind speed for desired heeling.
	 */
	static final String Vmax_PROPERTY = Pilot.class.getSimpleName() + ".sailVmax";	
	/**
	 * Telling the pilot to change to a certain direction (angle), relative to the current compass direction.
	 * The angle range goes from -180° to 180°.
	 * If an angle above 180° is given, it will automatically transform it to the corresponding negative angle. 
	 * 
	 * @param angle the relative angle to the desired compass direction. 
	 */
	void driveAngle(int angle);
	
	/**
	 * Telling the pilot to change to a certain direction (angle), relative to the current wind direction.
	 * The angle range goes from -180° to 180°.
	 * If an angle above 180° is given, it will automatically transform it to the corresponding negative angle. 
	 * 
	 * @param angle The relative angle to the desired wind direction. 
	 */
	void holdAngleToWind(int angle);
	
	/**
	 * Directly sets a value for the motor controlling the sail.
	 * If the value is outside of the range of valid values, the command will be ignored.
	 * 
	 * @param value the value to set for the motor controlling the sail
	 */
	void setSail(int value);
	
	/**
	 * Directly sets a value for the motor controlling the rudder.
	 * If the value is outside of the range of valid values, the command will be ignored.
	 * 
	 * @param value the value to set for the motor controlling the rudder
	 */
	void setRudder(int value);
	
	/**
	 * Directly sets a value for the motor controlling the propellor.
	 * If the value is outside of the range of valid values, the command will be ignored.
	 * 
	 * @param value the value to set for the motor controlling the propellor
	 */
	void setPropellor(int value);
}
