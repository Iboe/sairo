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

	private static final long serialVersionUID = 1926498008220645097L;
	private final int angle;
	
	/**
	 * Creates a new instance with the compass angle that should be driven.
	 *  
	 * @param angle the compass angle to be driven 
	 */
	public CompassCourseTask(int angle) {
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
		return "CompassCourseTask [angle=" + angle + "]";
	}
}
