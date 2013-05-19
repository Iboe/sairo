package de.fhb.sailboat.mission;

import java.util.List;

/**
 * Simple implementation of a mission.
 * 
 * @author hscheel
 *
 */
public class MissionImpl {

	private List<Task> tasks;


	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}


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
