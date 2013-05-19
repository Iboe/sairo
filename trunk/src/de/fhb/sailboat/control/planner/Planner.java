package de.fhb.sailboat.control.planner;

import de.fhb.sailboat.mission.MissionVO;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.Task;

/**
 * Interface for executing a {@link Mission}. Implementations are responsible for
 * arranging the order of the {@link Task} elements of the {@link Mission} and 
 * the execution of the tasks.
 * 
 * @author hscheel
 *
 */
public interface Planner {

	/**
	 * Name of the property defining the wait time between two planning cycles.
	 */
	static final String WAIT_TIME_PROPERTY = Planner.class.getSimpleName() + ".waitTime";
	
	/**
	 * Executes a mission by executing the tasks it contains. The sequence of the tasks
	 * may change during execution.
	 * Calling this while another mission is executed will cause stopping the old
	 * mission and executing the new one.
	 * 
	 * @param mission the mission to be executed
	 */
	void doMission(MissionVO mission);
	
	/**
	 * Stops executing the current mission. To avoid the boat being pushed around
	 * without control, it will try to hold its current position.
	 */
	void stop();
	
	/**
	 * Executes a primitive command directly.
	 * Calling this while a mission is executed will cause stopping the
	 * mission and executing the task.
	 * 
	 * @param task the primitive task to be executed
	 */
	void doPrimitiveCommand(PrimitiveCommandTask task);
}
