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
	private boolean taskChanged;
	
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
		this.taskChanged = false;
	}
	
	/**
	 * Sets the task and indicates that the current task has changed.
	 * 
	 * @param task the new task to execute
	 */
	public void setTask(T task) {
		this.task = task;
		this.taskChanged = true;
	}
	
	/**
	 * Sets the task and indicates that the current task has changed. Additional, the
	 * position of the boat at the start of the task is set. 
	 * 
	 * @param task the new task to execute
	 * 
	 */
	public void setTask(T task, GPS startPosition, GPS goal) {
		setTask(task);
		this.startPosition = startPosition;
		this.goal = goal;
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
		
		//the differences has to be calculated only once for one task
		//but has to be recalculated if task changed
		if (taskChanged) {
			calcStartGoalDifference();
			taskChanged = false;
		}
		
		if(currentPos != null && startGoalDistance != 0){
			dx13 = goal.getLongitude() - currentPos.getLongitude();
			dy13 = goal.getLatitude() - currentPos.getLatitude();
			
			//without Math. abs, we're additionally having the information if the current position is left (negative) or right (positive) from that line
			dist = /*Math.abs*/( (dx12)*(dy13) - (dx13)*(dy12) ) / startGoalDistance;   
		}
		return dist;
	}
	
	private void calcStartGoalDifference() {
		if (startPosition == null || goal == null) {
			dx12 = goal.getLongitude() - startPosition.getLongitude();
			dy12 = goal.getLatitude() - startPosition.getLatitude();
			startGoalDistance = Math.sqrt( (dx12)*(dx12) + (dy12)*(dy12));
		} else {
			dx12 = 0;
			dy12 = 0;
			startGoalDistance = 0;
		}
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
