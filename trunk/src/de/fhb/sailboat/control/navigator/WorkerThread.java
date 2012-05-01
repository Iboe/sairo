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
	protected T task;
	
	/**
	 * Initial GPS position of the boat, when the {@link WorkerThread} was started.
	 */
	protected GPS startPosition;
	
	/**
	 * Position of the goal of the current task. 
	 */
	protected GPS goal;
	
	private double dx12, dy12, startGoalDistance;
	
	public WorkerThread(Pilot pilot) {
		this.pilot = pilot;
		this.worldModel = WorldModelImpl.getInstance();
		this.startPosition = null;
		this.goal = null;
	}
	
	/**
	 * Sets the task and triggers calculations for the new task.
	 * 
	 * @param task the new task to execute
	 */
	public final void setTask(T task) {
		setTask(task, null, null);
	}
	
	/**
	 * Sets the task and triggers calculations for the new task. Additional, the
	 * position of the boat at the start of the task is set. 
	 * 
	 * @param task the new task to execute
	 * 
	 */
	public final void setTask(T task, GPS startPosition, GPS goal) {
		this.startPosition = startPosition;
		this.goal = goal;
		this.task = task;
		taskHasChanged();
	}
	
	/**
	 * Calculates everything necessary when the task to be executed has changed.
	 * This method is a hook for subclasses to add own calculations. Subclasses that need own calculations to be done
	 * when the task changed, should override this method, call the method from the base class (this method) 
	 * and execute their calculations afterwards.
	 */
	protected void taskHasChanged() {
		calcStartGoalDifference();
	}
	
	private void calcStartGoalDifference() {
		if (startPosition != null && goal != null) {
			dx12 = goal.getLongitude() - startPosition.getLongitude();
			dy12 = goal.getLatitude() - startPosition.getLatitude();
			startGoalDistance = Math.sqrt((dx12)*(dx12) + (dy12)*(dy12));
		} else {
			dx12 = 0;
			dy12 = 0;
			startGoalDistance = 0;
		}
	}
	
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
			LOG.debug("interrupted while waiting");
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
	 * Calculates the distance from the current position to the ideal line between a start and the goal position.<br>
	 * A negative value means that the given currentPos is left from the ideal line, based on the direction of travel<br>
	 * A positive value means that the given currentPos is right from the ideal line, based on the direction of travel<br>
	 * The interpretation of the resulting distance is matter of the caller. e.g. A transformation into meters had to be done yet, if required.
	 * (Internal note: perhaps we can misuse that function to perform a cross line task too!)
	 * 
	 * @param start Start position that defines the line 
	 * @param goal Destination position that defines the line
	 * @param currentPos Current position for which the distance to that line shall be calculated
	 * @return The distance of the given currentPos to the imaginary line between start and goal
	 */
	public double calcIdealLineDist(GPS currentPos){
		double dist = 0;
		double dx13, dy13;
		
		if(currentPos != null && startGoalDistance != 0){
			dx13 = goal.getLongitude() - currentPos.getLongitude();
			dy13 = goal.getLatitude() - currentPos.getLatitude();
			
			//without Math. abs, we're additionally having the information if the current position is left (negative) or right (positive) from that line
			dist = /*Math.abs*/( (dx12)*(dy13) - (dx13)*(dy12) ) / startGoalDistance;   
		}
		return dist;
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
