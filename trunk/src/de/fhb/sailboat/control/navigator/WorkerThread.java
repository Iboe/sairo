package de.fhb.sailboat.control.navigator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public abstract class WorkerThread<T extends Task> extends Thread {

	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Navigator.WAIT_TIME_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkerThread.class);
	
	protected final WorldModel worldModel;
	protected final Pilot pilot;
	
	public WorkerThread(Pilot pilot) {
		this.pilot = pilot;
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	public abstract void setTask(T task);
	
	/**
	 * Waits for an defined time and interrupts thread if there was an 
	 * interrupt during waiting.
	 */
	protected void waitForNextCycle() {
		try {
			Thread.sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			//the exception causes the interrupt flag to be set to false,
			//so another interrupt is needed
			interrupt();
			LOG.debug("interrupted while waiting", e);
		}
	}
	
	/**
	 * Calculates relative angle from current position to goal.
	 * 
	 * @param goal the GPS point to reach
	 * @return angle from current position to goal
	 */
	public double calcAngleToGPS(GPS goal) {
		double angle;
		GPS difference;
		
		//calculate difference vector between current and goal position, 
		//as new origin of coordinates
		difference = new GPS(goal.getLatitude() - worldModel.getGPSModel().getPosition().getLatitude(), 
				goal.getLongitude() - worldModel.getGPSModel().getPosition().getLongitude());
		//calculate angle between difference vector and x-axis
		angle = Math.atan(difference.getLatitude() / difference.getLongitude());
		//convert to degrees
		angle = toDegree(angle);
		
		if (difference.getLongitude() < 0) {
			angle += 180;
		} else if(difference.getLatitude() < 0 && difference.getLongitude() >= 0) {
			angle += 360;
		}
			
		LOG.debug("calculated angle: {}", angle);
		
		//angle between current and goal position = 
		//north (== y-axis) - current angle (direction of the bow as difference to north)
		//- angle between difference vector and x-axis 
		angle = 90 - angle - worldModel.getCompassModel().getCompass().getYaw();
		
		return angle;
	}
	
	/**
	 * Converts an radian value to degrees.
	 * 
	 * @param radian the radian value
	 * @return the value in degrees
	 */
	public double toDegree(double radian) {
		return (radian / Math.PI * 180);
	}
}
