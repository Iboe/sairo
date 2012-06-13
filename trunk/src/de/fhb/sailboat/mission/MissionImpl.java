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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("MissionImpl [");
		for (Task task : tasks) {
			buffer.append(task.getClass().getSimpleName()).append(", ");
		}
		buffer.append("]");
		
		return buffer.toString();
	}
}
