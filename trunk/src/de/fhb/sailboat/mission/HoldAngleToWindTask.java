package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task for driving a course only based on the wind. The boat holds a specific angle relative 
 * to the current apparent wind. The task does not finish.
 * 
 * @author hscheel
 *
 */
public class HoldAngleToWindTask implements Task {

	private static final long serialVersionUID = 7090870114993826318L;
	private final int angle;
	
	/**
	 * Creates a new instance with the angle that should be hold towards the wind.
	 * 
	 * @param angle the angle to hold relative to the apparent wind
	 */
	public HoldAngleToWindTask(int angle) {
		this.angle = angle;
	}
	
	/**
	 * The task does not finish, so <code>false</code> is returned. 
	 */
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	public int getAngle() {
		return angle;
	}
	
	@Override
	public String toString() {
		return "HoldAngleToWindTask [angle=" + angle + "]";
	}
}
