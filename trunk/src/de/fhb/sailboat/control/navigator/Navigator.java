package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.mission.Task;

/**
 * Interface for executing a {@link Task}. Implementations are responsible to 
 * control the boat in such a way, that the task will be finished eventually.
 * 
 * @author hscheel
 *
 */
public interface Navigator {

	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Navigator.class.getSimpleName() + ".waitTime";
	
	/**
	 * Name of the property defining the maximum line distance for calculation.
	 */
	static final String MAX_LINE_DIST_PROPERTY = Navigator.class.getSimpleName() + ".maxLineDist";
	
	/**
	 * Name of the property defining the minimum line distance for calculation.
	 */
	static final String MAX_LINE_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".maxLineAngle";
	
	/**
	 * Name of the property defining the maximum angle of the wind towards the boat,
	 * which can not be reached by the boat directly.
	 */
	static final String MAX_BEAT_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".maxBeatAngle";
	
	/**
	 * Name of the property defining the minimum angle of the wind towards the boat,
	 * which can not be reached by the boat directly.
	 */
	static final String MIN_BEAT_ANGLE_PROPERTY = Navigator.class.getSimpleName() + ".minBeatAngle";
	
	/**
	 * Executes a {@link Task}.
	 * 
	 * @param task the task to be executed, must not be <code>null</code>
	 */
	void doTask(Task task);
}
