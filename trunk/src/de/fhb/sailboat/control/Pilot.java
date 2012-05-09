package de.fhb.sailboat.control;


/**
 * 
 * @author Michael Kant
 */
public interface Pilot {

	/**
	 * Name of the property defining the wait time between two planning cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Pilot.class.getSimpleName() + ".waitTime";
	
	static final String P_PROPERTY = Pilot.class.getSimpleName() + ".pFactor";
	static final String I_PROPERTY = Pilot.class.getSimpleName() + ".iFactor";
	static final String D_PROPERTY = Pilot.class.getSimpleName() + ".dFactor";
	
	/**
	 * Telling the pilot to change to a certain direction (angle), relative to the current direction.
	 * The angle range goes from -180° to 180°.
	 * If an angle above 180° is given, it will automatically transform it to the corresponding negative angle. 
	 * 
	 * @param angle The relative angle to the desired direction. 
	 */
	void driveAngle(int angle);
	/**
	 * TODO
	 * @param angle
	 */
	void holdAngleToWind(int angle);
	
	void setSail(int value);
	
	void setRudder(int value);
	
	void setPropellor(int value);
}
