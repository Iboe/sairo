package de.fhb.sailboat.mission;

import java.util.List;

/**
 * Simple implementation of a mission.
 * 
 * @author hscheel
 *
 */
public class MissionImpl implements Mission {

	private List<Task> tasks;

	@Override
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public List<Task> getTasks() {
		return tasks;
	}
}
