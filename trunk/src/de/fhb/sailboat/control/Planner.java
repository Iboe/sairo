package de.fhb.sailboat.control;

import de.fhb.sailboat.mission.Mission;

public interface Planner {

	void doMission(Mission mission);
	void stop();
}
