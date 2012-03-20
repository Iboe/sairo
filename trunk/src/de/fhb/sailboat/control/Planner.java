package de.fhb.sailboat.control;

import de.fhb.sailboat.mission.Mission;

public interface Planner {

	/**
	 * Name of the property defining the wait time between two planning cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Planner.class.getSimpleName() + ".waitTime";
	
	void doMission(Mission mission);
	void stop();
}
