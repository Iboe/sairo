package de.fhb.sailboat.control.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.ActuatorModel;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Class for calculating values for the actuators in a concurrent way. Tries to hold an angle
 * given by the {@link Navigator} until a new command is activated. The calculated values are 
 * handed over to the {@link LocomotionSystem} for passing them to the actuators.
 * 
 * @author hscheel
 *
 */
public class DriveAngleThread extends Thread {
	
	/**
	 * @see {@link Pilot}.MAX_RELEVANT_ANGLE_PROPERTY
	 */
	public static final int MAX_RELEVANT_ANGLE = Integer.parseInt(System.getProperty(
			Pilot.MAX_RELEVANT_ANGLE_PROPERTY));
	
	/**
	 * @see {@link Pilot}.WAIT_TIME_PROPERTY
	 */
	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Pilot.WAIT_TIME_PROPERTY));

	/**
	 * @see {@link Pilot}.K_PROPERTY
	 */
	public static final double k = Double.parseDouble(System.getProperty(Pilot.K_PROPERTY));
	
	/**
	 * @see {@link Pilot}.Hmax_PROPERTY
	 */
	public static final double Hmax = Double.parseDouble(System.getProperty(Pilot.Hmax_PROPERTY));
	
	/**
	 * @see {@link Pilot}.Vmax_PROPERTY
	 */
	public static final double Vmax = Double.parseDouble(System.getProperty(Pilot.Vmax_PROPERTY));
	
	/**
	 * @see {@link LocomotionSystem}.SAIL_SHEET_IN;
	 */
	public static final double tightMax = LocomotionSystem.SAIL_SHEET_IN;
	
	/**
	 * @see {@link LocomotionSystem}.SAIL_SHEET_OUT;
	 */
	public static final double easeMax = LocomotionSystem.SAIL_SHEET_OUT;
	
	/**
	 * @see {@link LocomotionSystem}.SAIL_NORMAL;
	 */
	public static final double sailNormal = LocomotionSystem.SAIL_NORMAL;

	private static final Logger LOG = LoggerFactory.getLogger(DriveAngleThread.class);
	
	private final LocomotionSystem locSystem;
	private final CompassModel compassModel;
	private final WindModel windModel;
	private final GPSModel gpsModel;
	private final ActuatorModel actuatorModel;
	
	//local variables determining the direction to drive
	private int desiredAngle;
	private DriveAngleMode mode;
	
	private SimplePIDController simplePIDController;
	private PIDController PIDController;
	
	//local variables for calculation of the rudder position, they are not 
	//in the corresponding method, so they can be logged in the run() method 
	private double rudderPos=0;
	private int deltaAngle=0;
	private long time_old;
	private double Ta; //Ta in PID-Regler
	private double Kd;
	
	// sailPos
	private double lastSailPos;
	private double sailPos;
	private Calculations calc;
	private double desiredHeeling = 0;
	private double trueWindDirection;;
	
	/**
	 * Creates a new instance, which calculates commands for the {@link LocomotionSystem}
	 * handed over. Has to be started with run().
	 * 
	 * @param locSystem the {@link LocomotionSystem} which executes the calculated commands  
	 */
	public DriveAngleThread(LocomotionSystem locSystem) {
		this.locSystem = locSystem;
		compassModel = WorldModelImpl.getInstance().getCompassModel();
		windModel = WorldModelImpl.getInstance().getWindModel();
		gpsModel = WorldModelImpl.getInstance().getGPSModel();
		actuatorModel = WorldModelImpl.getInstance().getActuatorModel();
		simplePIDController = new SimplePIDController();
		PIDController = new PIDController();
		calc = new Calculations();
	}
	
        /*
	 * Starts the {@link Thread}. Calculates positions for the rudder and the sail until
	 * it is interrupted. 
	 */
	@Override
	public void run() {
		int counter = 0;
		
		while(!isInterrupted() ) {

			calculateRudderPosisition();
			calculateSailPosition();
			
			// log status every three cycles
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
	 * Set desired Sail-Pos by using Wind-Direction.
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
			trueWindDirection = t_d;
			
			// DEBUG TODO for once, use the apparent wind direction!
	t_d = w_s;
			// TODO better fragmentation
			if (t_d > 0 && t_d < 38) {
				// No-Go-Area! No Idea what to do with the sails.
				sailPos = sailNormal;
			}
			else if (t_d < 55) sailPos = (tightMax);
			else if (t_d < 85) sailPos = tightMax+( (sailNormal-tightMax)/2 );
			else if (t_d < 95) sailPos = sailNormal;
			else if (t_d < 135) sailPos = easeMax-( (easeMax-sailNormal)/2);
			else sailPos = easeMax;
		}
		// adjust sailPos, if heeling is too high
		double actualHeeling = compassModel.getCompass().getRoll();
		double pctChange = 0;
		// way too low
		if ( actualHeeling > 5 )
				pctChange = 5;
		// too low
		if ( actualHeeling > 10 )
			pctChange = 10;
		// to high
		if ( actualHeeling > 20 )
			pctChange = 20;
		// way too high
		if ( actualHeeling > 35 )
			pctChange = 50;
		
		sailPos = sailPos + (pctChange * sailPos );
		if (sailPos < tightMax) sailPos = tightMax;
		if (sailPos > easeMax) sailPos = easeMax;
		
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
		// Command in Worldmodel? (aka command send correct)
		if (actuatorModel.getSail().getValue() == sailPos)
		{
			lastSailPos = sailPos;
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
     * @return the value for the sail to set
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
	 * Calculates actual Value for SailPos and sets new Value as new lastSailPos.
	 * 
	 * @param pctChange percent Value to change lastSailPos
	 * @return the value for the sail to set
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
	 * desiredHeeling in addiction to windSpeed and -direction.
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
	 * Calculates the new position for the rudder and hands it over to the {@link LocomotionSystem}. 
	 * Tries to hold the desired angle dependent to the currently active {@link DriveAngleMode}.
	 */
	private void calculateRudderPosisition() {
		
		synchronized (mode) {
//			if (DriveAngleMode.COMPASS.equals(mode)) {
//				deltaAngle = (int) (desiredAngle - compassModel.getCompass().getYaw());
//				deltaAngle = transformAngle(deltaAngle);
//				
//			} else if (DriveAngleMode.WIND.equals(mode)) {
//				deltaAngle = (int) (desiredAngle - windModel.getWind().getDirection());
//				//here negating because the wind is not influenced by the boat and 
//				//the wind angle depends on the boat
//				deltaAngle = -transformAngle(deltaAngle); 
//			}
//		}
		
		//Neuimplementierung PIDController
		deltaAngle = (int) PIDController.controll((int)compassModel.getCompass().getYaw(),desiredAngle);
		}
		rudderPos = Math.min(MAX_RELEVANT_ANGLE, Math.abs(deltaAngle));
		
		if (deltaAngle < 0) {
			//rudder to the very left, assuming very left is the smallest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_LEFT-LocomotionSystem.RUDDER_NORMAL);
		} else {
			//rudder to the very right, assuming very right is the biggest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_RIGHT-LocomotionSystem.RUDDER_NORMAL);
		}
		
		//adding offset, to match with the absolute rudder values
		rudderPos += LocomotionSystem.RUDDER_NORMAL;
		//rudderPos = simplePIDController.control(rudderPos);
		LOG.debug("Set rudder angle to: " + rudderPos);
		locSystem.setRudder((int) rudderPos);
	}
		
	/**
	 * Transforms the specified angle to the range from -180 to +180. 
	 * 
	 * @param angle the angle to be transformed
	 * @return the angle in range from -180 to +180
	 */
	public int transformAngle(int angle) {
		angle = angle % 360;
		
		if (angle > 180) {
			angle -= 360;
		} else if (angle < -180) {
			angle += 360;
		}

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
	
	/**
	 * Getter for the desired angle, set by the {@link Navigator}.
	 * 
	 * @return the angle to hold
	 */
	public double getDesiredAngle() {
		return desiredAngle;
	}
}
