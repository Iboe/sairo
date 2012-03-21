package de.fhb.sailboat.control;

import de.fhb.sailboat.mission.Task;

public interface Navigator {

	/**
	 * Name of the property defining the wait time between two navigation cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Navigator.class.getSimpleName() + ".waitTime";
	
	void doTask(Task task);
}
