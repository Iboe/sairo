package de.fhb.sailboat.control.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
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

	public static final double k = Double.parseDouble(System.getProperty(Pilot.K_PROPERTY));
	public static final double Hmax = Double.parseDouble(System.getProperty(Pilot.Hmax_PROPERTY));
	public static final double Vmax = Double.parseDouble(System.getProperty(Pilot.Vmax_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(DriveAngleThread.class);
	
	private final LocomotionSystem locSystem;
	private final CompassModel compassModel;
	private final WindModel windModel;
	private final GPSModel gpsModel;
	
	//local variables determining the direction to drive
	private int desiredAngle;
	private DriveAngleMode mode;
	
	//local variables for the pid controller
	private SimplePIDController simplePIDController;
	private double p = 0;
	private double i = 0;
	private double d = 0;
	
	//local variables for calculation of the rudder position, they are not 
	//in the corresponding method, so they can be logged in the run() method 
	private double lastRudderPos = 0;
	private double rudderPos=0;
	private int deltaAngle=0;
	
	// sailPos
	private double lastSailPos = 0;
	private double sailPos=0;
	
	
	public DriveAngleThread(LocomotionSystem locSystem) {
		this.locSystem = locSystem;
		compassModel = WorldModelImpl.getInstance().getCompassModel();
		windModel = WorldModelImpl.getInstance().getWindModel();
		gpsModel = WorldModelImpl.getInstance().getGPSModel();
		simplePIDController = new SimplePIDController();
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
				interrupt();
			}
		}
	}
	/**
	 * Calculate the (optimal) Sail-Position by influences of:
	 * - windspeed
	 * - winddirection (! atmospheric vs. relative wind ! )
	 * - heel ("Krängung")
	 * 
	 * as in Stelzer et al. "Fuzzy Logic Control System for Autonomous Sailboats":
	 * 1) Calculate desired Heeling
	 * 2) fuzzy set for "heeling" = Desired H. - Actual Heeling
	 * 		- Too low (*)
	 * 		- Too high (*)
	 * 		- Optimal
	 * TODO what's the role of pitch-values?
	 * 3) React with sail change, if (*)
	 * 		- too low ==> tighten
	 * 		- too high ==> ease off
	 * 		- optimal ==> keep
	 * 
	 * @author schmidst
	 */
	private void calculateSailPosition() {
		double pctChange;
		// 1) 
		double desiredHeeling = desiredHeeling();
		// 2)
		double actualHeeling = compassModel.getCompass().getRoll();
		double heeling = desiredHeeling - actualHeeling;
		// TODO get real values!
		
		// 3)
		
		// way too low
		if ( heeling < -5 )
				pctChange = -10;
		// too low
		if ( heeling < 0 )
			pctChange = -5;
		// to high
		if ( heeling > 0 )
			pctChange = 5;
		// way too high
		if ( heeling > 5 )
			pctChange = 10;
		
		// DEBUG / Non-violent
		pctChange= 0;  // Keep; 
		if (pctChange != 0) 
		{
			sailPos = calculateSailChange(pctChange);
			locSystem.setSail((int) sailPos);
		}
	}

	/**
	 * calculate actual Value for SailPos
	 * also set new Value as new lastSailPos
	 * 
	 * @param pctChange percent Value to change lastSailPos
	 * @return sailChangeValue new to set Value 
	 * @author schmidst
	 */
	private double calculateSailChange(double pctChange)
	{
		double sailChangeValue = 0;
		double tightMax = LocomotionSystem.SAIL_SHEET_IN;
		double easeMax = LocomotionSystem.SAIL_SHEET_OUT;
		//double normal = LocomotionSystem.SAIL_NORMAL;
		
		sailChangeValue = lastSailPos + (pctChange * lastSailPos );
		if (sailChangeValue < tightMax) sailChangeValue = tightMax;
		if (sailChangeValue > easeMax) sailChangeValue = easeMax;
		
		lastSailPos = sailChangeValue;
		return sailChangeValue;
		
	}
	/**
	 * 
	 * @return double desired heeling in percent/degree ? TODO
	 * @author schmidst
	 */
	private double desiredHeeling()
	{
		// h = max(0,(h{max} - k*|a|)* (min(v,v{max})/v{max}))
		double h = 0d;
		// v actual windspeed
		double v = windModel.getWind().getSpeed();
		// a = atmospheric wind in degree ( -180 to 180° )
		double a = calculateAtmosphericWind(windModel.getWind().getDirection(), v, 0d);
		// k = 
		// h{max} = max. heeling of the boat for a of v{max} or above
		// v{max}
		
		
		
		return h;
	}
	
	/**
	 * Calculate the atmospheric Wind Direction out of the relative Wind Direction and the speed of the boat
	 * 
	 * @param r relative wind direction in °
	 * @param v speed of relative wind in m/s
	 * @param vb speed of boat according gps in m/s
	 * @return atmospheric Wind Direction in °
	 * 
	 * TODO put somewhere else, so everybody can use this
	 */
	private double calculateAtmosphericWind(double r, double v, double vb) {
		double a = 0d;
		
		
		
		return a;
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
		//rudderPos = pidController(rudderPos);
		rudderPos = simplePIDController.control(rudderPos);
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
