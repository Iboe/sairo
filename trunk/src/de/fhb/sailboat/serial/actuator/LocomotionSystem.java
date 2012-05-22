/**
 * 
 */
package de.fhb.sailboat.serial.actuator;

/**
 * @author KI-Lab
 *
 */
public interface LocomotionSystem {
	
	//TODO which are the correct ones??? not using two constants
	//for the same value, use for example properties file instead
	public static final int RUDDER_LEFT = 31;//34; 
	public static final int RUDDER_NORMAL = 73;//68;
	public static final int RUDDER_RIGHT = 114;//108;
	
	public static final int PROPELLOR_MIN = 170;
	public static final int PROPELLOR_NORMAL = 125;
	public static final int PROPELLOR_MAX = 80;
	
	/**
	 * relative Position
	 * Port 0 => servo sail
	 * Range: 31 .. 114
	 * Neutral: 73
	 * TODO normalize -41 .. 0 .. +41 => -100 .. 0 .. +100
	 */	
	public void setSail(int value);

	/**
	 * relative Position
	 * Port 1 => servo rudder
	 * Range: 34 .. 108
	 * Neutral: 68
	 * TODO normalize -36 .. 0 .. +36 => -100 .. 0 .. +100
	 */
	public void setRudder(int value);
	
	/**
	 * absolute Position
	 * Port 3 => servo propellor ("Flautenschieber")
	 * Range: 31 .. 114
	 * Neutral: 73
	 * TODO normalize 0 .. 82 => -100 .. 0 .. +100
	 */	
	public void setPropellor(int value);
	
	/**
	 * TODO Reset-Method on AKSEN-BOARD?
	 */
	public void reset();
	public void resetRudder();
	public void resetSail();
	public void resetPropellor();
	public int getBatteryState();
}
