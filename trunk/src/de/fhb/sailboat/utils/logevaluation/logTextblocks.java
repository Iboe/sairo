package de.fhb.sailboat.utils.logevaluation;

/***
 * This class provides the textblocks for config the logfile evaluation
 * @author Tobias Koppe
 * @version 1
 */
public class logTextblocks {
	
	public static final String aksenlocomotionClassName="de.fhb.sailboat.serial.actuator.AKSENLocomotion";
	public static final String compassThreadName="de.fhb.sailboat.serial.sensor.CompassSensor$CompassSensorThread";
	public static final String simplePidControllerClassName="de.fhb.sailboat.control.pilot.SimplePIDController";
	public static final String driveAngleThreadName="de.fhb.sailboat.control.pilot.DriveAngleThread";
	
	public static final String compassAzimuthMark="Azimuth:";
	public static final String compassPitchMark="Pitch:";
	
	public static final String setAksenState="";
	
	public static final String sendAksenServoCommand="new aksenboard state: will send servo command";
	public static final String sendingAksenServoCommandIncorrect=": incorrect/not send";
	public static final String sendingAksenServoCommandCorrect=": correct";
	public static final String receivedValueFailure="new aksenboard state: received: f";
	public static final String settingRudderTo="setRudder to";

}
