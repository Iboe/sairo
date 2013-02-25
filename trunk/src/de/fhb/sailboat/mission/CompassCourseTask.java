package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task for driving a course only based on the compass. The boat heads towards a specific compass 
 * angle. The task does not finish.
 * 
 * @author hscheel
 *
 */
public class CompassCourseTask implements Task {

	private final int angle;
	
	/**
	 * Creates a new instance with the compass angle that should be driven.
	 *  
	 * @param angle the compass angle to be driven 
	 */
	public CompassCourseTask(int angle) {
		this.angle = angle;
	}
	
	/*
	 * The task does not finish, so <code>false</code> is returned. 
	 */
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	/**
	 * Getter for the desired angle, which should be hold with respect to the compass.
	 * 
	 * @return the desired angle
	 */
	public int getAngle() {
		return angle;
	}

	@Override
	public String toString() {
		return "CompassCourseTask [angle=" + angle + "]";
	}
}
