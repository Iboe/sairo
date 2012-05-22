package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.mission.Task;

public interface Navigator {

	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Navigator.class.getSimpleName() + ".waitTime";
	
	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String MAX_LINE_DIST_PROPERTY = Navigator.class.getSimpleName() + ".maxLineDist";
	
	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String MAX_LINE_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".maxLineAngle";
	
	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String MAX_BEAT_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".maxBeatAngle";
	
	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String MIN_BEAT_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".minBeatAngle";
	
	
	
	void doTask(Task task);
}
