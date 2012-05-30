package de.fhb.sailboat.mission;

import java.util.List;

/**
 * Interface for a mission, consisting of a list of tasks. 
 * 
 * @author hscheel
 *
 */
public interface Mission {

	/**
	 * Setter for the tasks.
	 * 
	 * @param tasks the tasks to set
	 */
	void setTasks(List<Task> tasks);
	
	/**
	 * Getter for the tasks.
	 * 
	 * @return the tasks of the mission
	 */
	List<Task> getTasks();
}
