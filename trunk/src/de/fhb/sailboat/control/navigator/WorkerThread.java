package de.fhb.sailboat.control.navigator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Base class for navigation threads. Subclasses are responsible for executing a specific 
 * {@link Task} in a concurrent way. This class provides general methods for navigation, 
 * that can be used in multiple subclasses. 
 * 
 * @author hscheel
 *
 * @param <T> the {@link Task}, that a subclass executes
 */
public abstract class WorkerThread<T extends Task> extends Thread {

	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Navigator.WAIT_TIME_PROPERTY));
	
	public static final double MAX_LINE_DIST = Double.parseDouble(System.getProperty(
			Navigator.MAX_LINE_DIST_PROPERTY));
	public static final double MAX_LINE_ANGLE = Double.parseDouble(System.getProperty(
			Navigator.MAX_LINE_ANGLE_PROPERTY));
	
	public static final int MAX_BEAT_ANGLE = Integer.parseInt(System.getProperty(
			Navigator.MAX_BEAT_ANGLE_PROPERTY));
	public static final int MIN_BEAT_ANGLE = Integer.parseInt(System.getProperty(
			Navigator.MIN_BEAT_ANGLE_PROPERTY));
	
	protected static final Logger LOG = LoggerFactory.getLogger(WorkerThread.class);
	
	protected final WorldModel worldModel;
	protected final Pilot pilot;
	protected final Navigator navigator;
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
	
	/**
	 * Creates a new initialized instance.
	 * 
	 * @param pilot the pilot to send the commands to
	 * @param navigator the navigator who created this instance
	 */
	public WorkerThread(Pilot pilot, Navigator navigator) {
		this.navigator = navigator;
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
		calcStartGoalDifference();
		taskHasChanged();
	}
	
	/**
	 * Calculates everything necessary when the task to be executed has changed.
	 * This method is a hook for subclasses to add own calculations. Subclasses that need own calculations to be done
	 * when the task changed, should override this method and execute their calculations.
	 */
	protected void taskHasChanged() {
		
	}
	
	/**
	 * Calculates the difference between the starting position and the goal of the 
	 * task to be executed. This is used for the calcIdealLine-methods. If the starting
	 * position or the goal is <code>null</code>, the distance is set to 0. 
	 *  
	 */
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
	 * Calculates the absolute angle from the current position to the goal.
	 * 
	 * @param goal the {@link GPS} to reach
	 * @return angle from the current position to goal
	 */
	public double calcAngleToGPS(GPS goal) {
		double angle;
		GPS difference;
		
		//calculate difference vector between current and goal position, 
		//as new origin of coordinates
		difference = new GPS(goal.getLatitude() - worldModel.getGPSModel().getPosition().getLatitude(), 
				goal.getLongitude() - worldModel.getGPSModel().getPosition().getLongitude());
		
		//calculate angle between difference vector and x-axis
		if (difference.getLongitude() == 0) {
			if (difference.getLatitude() > 0) {
				angle = 90;
			} else if (difference.getLatitude() < 0) {
				angle = -90;
			} else {
				angle = 0;
			}
		} else {
			angle = Math.atan(difference.getLatitude() / difference.getLongitude());
			//convert to degrees
			angle = toDegree(angle);
		}
		
		if (difference.getLongitude() < 0) {
			angle += 180;
		} else if(difference.getLatitude() < 0 && difference.getLongitude() >= 0) {
			angle += 360;
		}
			
		//angle between current and goal position = 
		//north (== y-axis) - angle between difference vector and x-axis 
		angle = 90 - angle;
		
		LOG.debug("calculated angle: {}", angle);
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
			dist = ( (dx12)*(dy13) - (dx13)*(dy12) ) / startGoalDistance;   
		}
		
		return dist;
	}
	
	/**
	 * Calculates the angle from the current position to the line between a start position 
	 * to the goal.
	 * 
	 * @param currentPos the current position of the boat
	 * @return the angle to reach the line
	 */
	public double calcIdealLineAngle(GPS currentPos) {
		double dist = calcIdealLineDist(currentPos);
		
		if (Math.abs(dist) >= MAX_LINE_DIST) {
			return MAX_LINE_ANGLE;
		} else {
			return dist / MAX_LINE_DIST * MAX_LINE_ANGLE;
		}
	}
	
	/**
	 * Checks if the boat can reach the desired angle considering the current
	 * wind.
	 * 
	 * @param desiredAngle the angle to the goal
	 * @return if the angle can be reached
	 */
	public boolean isBeatNecessary(double desiredAngle) {
		Wind averageWind = worldModel.getWindModel().calcAverageWind();
		
		if (averageWind != null) {
			double angleToDrive = desiredAngle - worldModel.getCompassModel().getCompass().getYaw();
			double windGoalDiffernce = angleToDrive - transformAngle(averageWind.getDirection());
			
			return windGoalDiffernce <= MAX_BEAT_ANGLE && windGoalDiffernce >= MIN_BEAT_ANGLE;
	
		} else {
			return false;
		}
	}

	public double calAngleToGPSIncludingBeat(GPS goal) {
		double angle = calcAngleToGPS(goal);
		
		if (isBeatNecessary(angle)) {
			if (angle > 0) {
				angle += 45;
			} else {
				angle -= 45;
			}
		}
		
		return angle;
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
	 * Converts a radian value to degrees.
	 * 
	 * @param radian the radian value
	 * @return the value in degrees
	 */
	public double toDegree(double radian) {
		return (radian / Math.PI * 180);
	}
	
	/**
	 * NOT READY! MUST NOT BE USED!
	 * @return nonsense
	 */
	protected double calcAngleToGoal() {
		final int beatingParameter = 1;
		int boatPosition = 0;
		int targetPosition = 0;
		
		int vectorBoatToTarget = targetPosition - boatPosition;
		double currentAngle = worldModel.getCompassModel().getCompass().getYaw();
		double newAngle = currentAngle;
		double windDirectionInverse = 360 - calcRealWind().getDirection();
		double n = 1 + beatingParameter / Math.abs(vectorBoatToTarget);
		
		return 0;
	}
	
	/**
	 * NOT READY! MUST NOT BE USED!
	 * @return nonsense
	 */
	protected Wind calcRealWind() {
		return worldModel.getWindModel().getWind();
	}
	
	/**
	 * Calculates the amount of a two dimensional vector.
	 * 
	 * @param x first value of the vector
	 * @param y second value of the vector
	 * @return (x² + y²)^0.5
	 */
	private double amountOfVector(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}
}
