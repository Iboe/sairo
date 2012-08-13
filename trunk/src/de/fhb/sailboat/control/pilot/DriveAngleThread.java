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
	
	public static final double tightMax = LocomotionSystem.SAIL_SHEET_IN;
	public static final double easeMax = LocomotionSystem.SAIL_SHEET_OUT;
	public static final double sailNormal = LocomotionSystem.SAIL_NORMAL;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(DriveAngleThread.class);
	
	private final LocomotionSystem locSystem;
	private final CompassModel compassModel;
	private final WindModel windModel;
	private final GPSModel gpsModel;
	
	//local variables determining the direction to drive
	private int desiredAngle;
	private DriveAngleMode mode;
	
	private SimplePIDController simplePIDController;
	
	//local variables for calculation of the rudder position, they are not 
	//in the corresponding method, so they can be logged in the run() method 
	private double rudderPos=0;
	private int deltaAngle=0;
	
	// sailPos
	private double lastSailPos;
	private double sailPos;
	Calculations calc;
	double desiredHeeling=0;
	
	
	public DriveAngleThread(LocomotionSystem locSystem) {
		this.locSystem = locSystem;
		compassModel = WorldModelImpl.getInstance().getCompassModel();
		windModel = WorldModelImpl.getInstance().getWindModel();
		gpsModel = WorldModelImpl.getInstance().getGPSModel();
		simplePIDController = new SimplePIDController();
		calc = new Calculations();
	}
	
	public void run() {
		int counter = 0;
		
		while(!isInterrupted() ) {
			
			calculateRudderPosisition();
			calculateSailPosition();
			
			if (++counter == 3) {
				StringBuffer message = new StringBuffer();
				
				if (mode == DriveAngleMode.COMPASS) {
					message.append("compass=").append(compassModel.getCompass().getYaw());
				} else if (mode == DriveAngleMode.WIND) {
					message.append("wind=").append(windModel.getWind().getDirection());
				}
				
				message.append(", desiredAngle=").append(desiredAngle);
				message.append(", delta=").append(deltaAngle); 
				message.append(", rudderPos=").append(rudderPos);
				message.append(", sailPos=").append(sailPos);
				
				LOG.debug("Summarize: {}", message.toString());
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
	 * set desired Sail-Pos by using Wind-Direction
	 * 
	 * @author schmidst
	 */
	private void calculateSailPosition() {
		// Minimize Commands send to LocSystem
		boolean minCom = true;
		// Normal Position as Standard
		sailPos = sailNormal;
		// get true wind, if necessary
		// w_v actual windspeed
		double w_s = windModel.getWind().getSpeed();
		// w_d actual wind direction on the boat
		double w_d = windModel.getWind().getDirection();		
		// b_s speed of the boat according gps
		double b_s = gpsModel.getPosition().getSpeed();
		
		// 1. calculate true wind, if wind/boatspeed > 0
		if (w_s > 0)
		{
			calc.trueWind(w_d, w_s, b_s);
			double t_d = calc.getTrue_diff();  // Wertebereich?
			t_d = (double) Math.abs(transformAngle((int) t_d));
			if (Double.isNaN(t_d))
			{
				sailPos = easeMax;
			}
			
			// TODO better fragmentation
			if (t_d > 0 && t_d < 45) sailPos = (tightMax);
			else if (t_d < 85) sailPos = tightMax+( (sailNormal-tightMax)/2 );
			else if (t_d < 95) sailPos = sailNormal;
			else if (t_d < 135) sailPos = easeMax-( (easeMax-sailNormal)/2);
			else sailPos = easeMax;
		}
		
		// only send Command if really a new Sail-Position calculated
		if (minCom)
		{
			if (Double.compare(sailPos, lastSailPos) == 1)
			{
				locSystem.setSail((int) sailPos);
			}
		} else 
		{
			locSystem.setSail((int) sailPos);	
		}
		lastSailPos = sailPos;
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
	private double calculateSailByHeeling() {
		double sP = lastSailPos;
		double pctChange = 0;
		// 1) 
		desiredHeeling = desiredHeeling();
		// 2)
		double actualHeeling = compassModel.getCompass().getRoll();
		double heeling = desiredHeeling - actualHeeling;
		
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
		//if (pctChange != 0 && sP != lastSailPos) 
		//{
			sP = calculateSailChange(pctChange);
		//}
		
		return sP;
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

		sailChangeValue = lastSailPos + (pctChange * lastSailPos );
		if (sailChangeValue < tightMax) sailChangeValue = tightMax;
		if (sailChangeValue > easeMax) sailChangeValue = easeMax;
		
		lastSailPos = sailChangeValue;
		return sailChangeValue;
		
	}
	/**
	 * desiredHeeling in addiction to windSpeed and -direction
	 * 
	 * @return double desired heeling in percent/degree ?
	 * @author schmidst
	 */
	private double desiredHeeling()
	{
		// h = max(0,(h{max} - k*|a|)* (min(v,v{max})/v{max}))
		double h = 0d;
		// v actual windspeed
		double v = windModel.getWind().getSpeed();
		// a actual wind direction on the boat
		double a = windModel.getWind().getDirection();
		// k = 
		// h{max} = max. heeling of the boat for a of v{max} or above
		// v{max}
		double x;
		if ( v <= Vmax) {
			x = v/Vmax;
		} else {
			x = 1;
		}
		
		h = ((Hmax - k*Math.abs(a)) * x );
		
		if (h > 0) 
			return h;
		else
			return 0;
	}
	

	
	/**
	 * 
	 */
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
		rudderPos = simplePIDController.control(rudderPos);
		locSystem.setRudder((int) rudderPos);
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
