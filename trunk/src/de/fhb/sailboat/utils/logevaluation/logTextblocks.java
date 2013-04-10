package de.fhb.sailboat.utils.logevaluation;

/***
 * This class provides the textblocks for config the logfile evaluation
 * @author Tobias Koppe
 * @version 1
 */
public class logTextblocks {
	
	/**
	 * Textblocks for evaluate all , means class or threadnames
	 */
	public static final String aksenlocomotionClassName="de.fhb.sailboat.serial.actuator.AKSENLocomotion";
	public static final String compassThreadName="de.fhb.sailboat.serial.sensor.CompassSensor$CompassSensorThread";
	public static final String simplePidControllerClassName="de.fhb.sailboat.control.pilot.SimplePIDController";
	public static final String driveAngleThreadName="de.fhb.sailboat.control.pilot.DriveAngleThread";
	
	/**
	 * Textblocks for evaluate pilot log
	 */
	public static final String driverSetRudderTo="Set rudder angle to: ";
	
	/**
	 * Textblocks for evaluate windsensor log
	 */
	
	public static final String windSensorClassName="";
	public static final String windSensorWindDirection="WindDirection";
	public static final String windSensorWindSpeed="WindSpeed";
	
	/***
	 * Textblocks for evaluate gps sensor log
	 */
	
	public static final String gpsSensorThreadName="de.fhb.sailboat.serial.sensor.GpsSensor$GpsSensorThread";
	public static final String gpsSensorLongitude="longitude";
	public static final String gpsSensorLatitude="latitude";
	public static final String gpsSensorSatelites="satelites";
	public static final String gpsSensorSpeed="speed";
	
	/**
	 * Textblocks for evaluate simple pid controller
	 */
	
	public static final String simplePidControllerControllRudder="SimplePIDController controll rudder:";
	public static final String simplePidControllerControllRudderDifference="difference";
	public static final String simplePidControllerControllRudderCurrentValue="currentValue";
	public static final String simplePidControllerControllRudderOutput="output";
	
	/**
	 * Textblocks for evaluate compass log
	 */
	public static final String compassAzimuthMark="Azimuth:";
	public static final String compassPitchMark="Pitch:";
	
	/**
	 * Textblocks for evaluate aksen log
	 */
	public static final String setAksenState="";
	public static final String sendAksenServoCommand="new aksenboard state: will send servo command";
	public static final String sendingAksenServoCommandIncorrect=": incorrect/not send";
	public static final String sendingAksenServoCommandCorrect=": correct";
	public static final String receivedValueFailure="new aksenboard state: received: f";
	public static final String settingRudderTo="setRudder to";

}
