package de.fhb.sailboat.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Implementation of {@link Planner} which executes the tasks in their 
 * original sequence. The current {@link Task} is hand over to the {@link Navigator}.
 * The planner checks concurrently if the current task is finished and executes the next
 * task afterwards. 
 * 
 * @author hscheel
 *
 */
public class PlannerImpl implements Planner {

	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Planner.WAIT_TIME_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(PlannerImpl.class);
			
	private final Navigator navigator;
	private final WorldModel worldModel;
	private WorkerThread workerThread;
	
	/**
	 * Creates a new instance with the specified {@link Navigator} to hand over tasks.
	 * 
	 * @param navigator the navigator to hand over tasks, must not be <code>null</code>
	 */
	public PlannerImpl(Navigator navigator) {
		if (navigator == null) {
			throw new NullPointerException();
		}
		
		this.navigator = navigator;
		this.worldModel = WorldModelImpl.getInstance();
		this.workerThread = null;
	}
	
	@Override
	public void doMission(Mission mission) {
		if (mission == null || mission.getTasks() == null || mission.getTasks().isEmpty()) {
			LOG.warn("could not start mission: mission is emtpy: {}", mission);
		} else {
			worldModel.setMission(mission);
			LOG.info("execute mission {}", mission);
			
			if (workerThread == null) {
				workerThread = new WorkerThread();
			} else if (workerThread.isAlive()) {
				//start mission in a new thread
				stopThread();
				workerThread = new WorkerThread();
			}
			
			workerThread.setTasks(mission.getTasks());
			if (!workerThread.isAlive()) {
				workerThread.start();
			}
		}
	}

	@Override
	public void doPrimitiveCommand(PrimitiveCommandTask task) {
		stopThread();
		navigator.doTask(task);
	}
	
	@Override
	public void stop() {
		stopThread();
		navigator.doTask(new StopTask(false));
	}

	private void stopThread() {
		if (workerThread != null && workerThread.isAlive()) {
			workerThread.interrupt();
		}
	}
	
	/**
	 * {@link Thread} for observing the current executed task. Checks repeatedly
	 * if the task is finished and starts the next task afterwards. If no task
	 * is available for execution, a {@link StopTask} is performed.
	 * 
	 * @author hscheel
	 *
	 */
	private class WorkerThread extends Thread {
		private Task currentTask;
		private List<Task> tasks;
		
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
		
		private void waitForNextCycle() {
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				//interrupt again to shut down thread
				LOG.debug("interrupted while waiting");
				interrupt();
			}
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}
	}
}
