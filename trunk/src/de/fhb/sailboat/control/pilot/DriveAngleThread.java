package de.fhb.sailboat.control.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class DriveAngleThread extends Thread {
	
	/**
	 * @see Pilot.MAX_RELEVANT_ANGLE_PROPERTY
	 */
	public static final int MAX_RELEVANT_ANGLE = Integer.parseInt(System.getProperty(
			Pilot.MAX_RELEVANT_ANGLE_PROPERTY));
	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Pilot.WAIT_TIME_PROPERTY));
	public static final double P = Double.parseDouble(System.getProperty(Pilot.P_PROPERTY));
	public static final double I = Double.parseDouble(System.getProperty(Pilot.I_PROPERTY));
	public static final double D = Double.parseDouble(System.getProperty(Pilot.D_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(DriveAngleThread.class);
	
	private final LocomotionSystem locSystem;
	private final CompassModel compassModel;
	private final WindModel windModel;
	
	//local variables determining the direction to drive
	private int desiredAngle;
	private DriveAngleMode mode;
	
	//local variables for the pid controller
	private double p = 0;
	private double i = 0;
	private double d = 0;
	
	//local variables for calculation of the rudder position, they are not 
	//in the corresponding method, so they can be logged in the run() method 
	private double lastRudderPos = 0;
	private double rudderPos=0;
	private int deltaAngle=0;
	
	public DriveAngleThread(LocomotionSystem locSystem) {
		this.locSystem = locSystem;
		compassModel = WorldModelImpl.getInstance().getCompassModel();
		windModel = WorldModelImpl.getInstance().getWindModel();
	}
	
	public void run() {
		int counter = 0;
		
		while(!isInterrupted() ) {
			
			calculateRudderPosisition();
			calculateSailPosition();
			
			if (++counter == 3) {
				String message = "";
				
				if (mode == DriveAngleMode.COMPASS) {
					message = "compass=" + compassModel.getCompass().getYaw();
				} else if (mode == DriveAngleMode.WIND) {
					message = "wind=" + windModel.getWind().getDirection();
				}
				
				message += ", desiredAngle=" + desiredAngle + ", delta=" + deltaAngle + 
					", rudderPos=" + rudderPos;
				LOG.debug("Summarize: {}", message);
				counter = 0;
			}
			
			try {
				Thread.sleep(WAIT_TIME);
			}
			catch (InterruptedException e) {
				LOG.debug("interrupted while waiting");
			}
		}
	}
	
	private void calculateSailPosition() {
		
	}

	private void calculateRudderPosisition() {
		
		synchronized (mode) {
			if (DriveAngleMode.COMPASS.equals(mode)) {
				deltaAngle = (int) (desiredAngle - compassModel.getCompass().getYaw());
				deltaAngle = transformAngle(deltaAngle);
				
			} else if (DriveAngleMode.WIND.equals(mode)) {
				deltaAngle = (int) (desiredAngle - windModel.getWind().getDirection());
				//here negating because the wind is not influenced by the boat and 
				//the wind angle depends on the boat
				deltaAngle = -transformAngle(deltaAngle); 
			}
		}
		
		rudderPos=Math.min(MAX_RELEVANT_ANGLE, Math.abs(deltaAngle)); 
		
		if (deltaAngle < 0) {
			//rudder to the very left, assuming very left is the smallest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_LEFT-LocomotionSystem.RUDDER_NORMAL);
		} else {
			//rudder to the very right, assuming very right is the biggest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_RIGHT-LocomotionSystem.RUDDER_NORMAL);
		}
		
		//adding offset, to match with the absolute rudder values
		rudderPos += LocomotionSystem.RUDDER_NORMAL;
		rudderPos = pidController(rudderPos);
		locSystem.setRudder((int) rudderPos);
	}
	
	private double pidController(double rudderPos) {
		p = rudderPos * P;
		i = (i + rudderPos) * I;
		d = (rudderPos - lastRudderPos) * D;
		lastRudderPos = rudderPos;
		
		return p + i + d;
	}
	
	/**
	 * Transforms the specified angle to the range from -180 to +180. 
	 * 
	 * @param angle the angle to be transformed
	 * @return the angle in range from -180 to +180
	 */
	public int transformAngle(int angle) {
		
		angle=angle%360;
		
		if(angle > 180) 
			angle-=360;
		else if(angle < -180) 
			angle+=360;
		
		return angle;
	}

	/**
	 * Drives to the desired angle and holds it until a new command arrives.
	 * 
	 * @param desiredAngle the angle to drive, relative to the boat
	 * @param mode determines which sensor is used for calculation.
	 */
	public void driveAngle(int desiredAngle, DriveAngleMode mode) {
		synchronized (mode) {
			this.desiredAngle = transformAngle(desiredAngle);
			this.mode = mode;
		}
	}
	
	public double getDesiredAngle() {
		return desiredAngle;
	}
}
