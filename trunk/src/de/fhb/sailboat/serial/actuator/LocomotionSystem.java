/**
 * 
 */
package de.fhb.sailboat.serial.actuator;

/**
 * @author KI-Lab
 *
 */
public interface LocomotionSystem {
	// Sail
	static final int SAIL_NUMBER = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".sailNo"));
	public static final int SAIL_SHEET_IN = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".SAIL_SHEET_IN")); 	// "dichtholen"
	public static final int SAIL_SHEET_OUT = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".SAIL_SHEET_OUT")); //"fieren", let out the sail
	public static final int SAIL_NORMAL = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".SAIL_SHEET_NORMAL"));
	// Rudder
	static final int RUDDER_NUMBER = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".rudderNo"));
	public static final int RUDDER_LEFT = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".RUDDER_LEFT"));
	public static final int RUDDER_NORMAL = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".RUDDER_NORMAL"));
	public static final int RUDDER_RIGHT = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".RUDDER_RIGHT"));
	// Propellor
	static final int PROPELLOR_NUMBER = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".propellorNo"));
	public static final int PROPELLOR_MIN = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".PROPELLOR_MIN"));
	public static final int PROPELLOR_NORMAL = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".PROPELLOR_NORMAL"));
	public static final int PROPELLOR_MAX = Integer.parseInt(System.getProperty(AKSENLocomotion.class.getSimpleName() + ".PROPELLOR_MAX"));

	public int getStatus();

	
	/**
	 * relative Position
	 * Port 0 => servo sail
	 * Range: 31 .. 114
	 * Neutral: 73
	 * TODO normalize -41 .. 0 .. +41 => -100 .. 0 .. +100
	 */	
	public void setSail(int angle);

	/**
	 * relative Position
	 * Port 1 => servo rudder
	 * Range: 34 .. 108
	 * Neutral: 68
	 * TODO normalize -36 .. 0 .. +36 => -100 .. 0 .. +100
	 */
	public void setRudder(int angle);
	
	/**
	 * absolute Position
	 * Port 3 => servo propellor ("Flautenschieber")
	 * Range: 31 .. 114
	 * Neutral: 73
	 * TODO normalize 0 .. 82 => -100 .. 0 .. +100
	 */	
	public void setPropellor(int angle);
	
	/**
	 * TODO Reset-Method on AKSEN-BOARD?
	 */
	public void reset();
	public void resetRudder();
	public void resetSail();
	public void resetPropellor();
	public int getBatteryState();
	public void setDebug(boolean debug);
	public long getWait_Sleep();
	public void setWait_Sleep(int wait_sleep);
}
