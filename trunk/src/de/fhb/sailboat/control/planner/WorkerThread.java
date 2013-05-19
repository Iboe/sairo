package de.fhb.sailboat.control.planner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * {@link Thread} for observing the current executed task. Checks repeatedly
 * if the task is finished and starts the next task afterwards. If no task
 * is available for execution, a {@link StopTask} is performed.
 * 
 * @author hscheel
 *
 */
public class WorkerThread extends Thread {
	
	/**
	 * @see {@link Planner}.WAIT_TIME_PROPERTY
	 */
	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Planner.WAIT_TIME_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(WorkerThread.class);
	
	private Task currentTask;
	private List<Task> tasks;
	private Navigator navigator;
	private final WorldModel worldModel;
	
	/**
	 * Creates a new instance with the specified {@link Navigator} to hand over tasks.
	 * 
	 * @param navigator the navigator to hand over tasks, must not be <code>null</code>
	 */
	public WorkerThread (Navigator navigator) {
		if (navigator == null) {
			throw new NullPointerException();
		}
		
		this.navigator = navigator;
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			
			//execute task till it is finished
			if (tasks != null && !tasks.isEmpty()) {
				//execute new task
				if (!(currentTask == tasks.get(0))) {
					currentTask = tasks.get(0);
					try {
						navigator.doTask(currentTask);
					} catch (IllegalStateException e) {
						LOG.warn("could not execute task - remove task {}", currentTask);
						tasks.remove(0);
					}
				}
				
				//remove task if it is finished
				if (currentTask.isFinished(worldModel.getGPSModel().getPosition())) {
					LOG.debug("task finished, removing from mission");
					tasks.remove(0);
				}
			//if no task is available the boat is stopped
			} else {
				
				//execute new StopTask if one is not already started  
				if (!(currentTask instanceof StopTask)) {
					currentTask = new StopTask();
					LOG.warn("no task to execute: use StopTask ");
					navigator.doTask(currentTask);
				}
			}
			waitForNextCycle();
		}
	}
	
	/**
	 * Waits for an defined time and interrupts thread if there was an 
	 * interrupt during waiting.
	 */
	private void waitForNextCycle() {
		try {
			Thread.sleep(WAIT_TIME);
		} catch (InterruptedException e) {
			//interrupt again to shut down thread
			LOG.debug("interrupted while waiting");
			interrupt();
		}
	}

	/**
	 * Setter for the {@link Task}s to execute.
	 * 
	 * @param tasks the {@link Task}s of the current {@link Mission}
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}