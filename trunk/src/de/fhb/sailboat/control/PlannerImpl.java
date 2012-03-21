package de.fhb.sailboat.control;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class PlannerImpl implements Planner {

	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Planner.WAIT_TIME_PROPERTY));
	
	private static final Logger LOG = LoggerFactory.getLogger(PlannerImpl.class);
			
	private final Navigator navigator;
	private final WorldModel worldModel;
	private WorkerThread workerThread;
	
	public PlannerImpl(Navigator navigator) {
		this.navigator = navigator;
		this.worldModel = WorldModelImpl.getInstance();
		this.workerThread = null;
	}
	
	@Override
	public void doMission(Mission mission) {
		if (mission == null || mission.getTasks() == null || mission.getTasks().isEmpty()) {
			LOG.warn("could not start mission: mission is null or emtpy: {}", mission);
		} else {

			if (workerThread == null) {
				workerThread = new WorkerThread();
			} else if (workerThread.isAlive()) {
				//shut down thread before starting new mission
				workerThread.interrupt();
				
				try {
					workerThread.join(5000l);
				} catch (InterruptedException e) {
					// TODO implement exception handling
					LOG.error("could not stop thread", e);
				}
				workerThread = new WorkerThread();
			}
			
			workerThread.setTasks(mission.getTasks());
			if (!workerThread.isAlive()) {
				workerThread.start();
			}
		}
	}

	@Override
	public void stop() {
		navigator.doTask(new StopTask());
	}

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
						navigator.doTask(currentTask);
					}
					
					//remove task if it is finished
					if (currentTask.isFinished(worldModel.getGPSModel().getPosition())) {
						LOG.debug("task finished, removing from mission");
						tasks.remove(0);
					}
				//if no task is available the boat is stopped
				} else {
					LOG.warn("no task to execute: use StopTask ");
					
					//execute new StopTask if one is not already started  
					if (!(currentTask instanceof StopTask)) {
						currentTask = new StopTask();
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
				// do nothing, since earlier execution should not disturb program
				interrupt();
				LOG.warn("interrupted while waiting", e);
			}
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}
	}
}
