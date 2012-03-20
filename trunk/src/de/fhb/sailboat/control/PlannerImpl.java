package de.fhb.sailboat.control;

import java.util.List;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class PlannerImpl implements Planner {

	public static final int WAIT_TIME = //1500; //TODO load from property file 
	Integer.parseInt(System.getProperty(Planner.WAIT_TIME_PROPERTY));
	
	//private final Logger LOG = LoggerFactory.getLogger(PlannerImpl.class);
			
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
			//LOG.warn("could not start mission: mission is null or emtpy", mission);
		} else {

			if (workerThread == null) {
				workerThread = new WorkerThread();
			} else if (workerThread.isAlive()) {
				//shut down thread before starting new mission
				workerThread.setStop(true);
				
				try {
					workerThread.join();
				} catch (InterruptedException e) {
					// TODO implement exception handling
					//LOG.error("could not stop thread", e);
				}
			}
			
			workerThread.setTasks(mission.getTasks());
			workerThread.setStop(false);
			workerThread.start();
		}
	}

	@Override
	public void stop() {
		navigator.doTask(new StopTask());
	}

	private class WorkerThread extends Thread {
		private Task currentTask;
		private List<Task> tasks;
		private boolean stop;
		
		@Override
		public void run() {
			int counter = 0;
			while (!stop && !isInterrupted()) {
				//TODO remove this - just too lazy to write a test
				if (counter++ == 10) {
					System.out.println("removing first task " + tasks.get(0));
					tasks.remove(0);
				} else if (counter == 20) {
					System.out.println("removing second task " + tasks.get(0));
					tasks.remove(0);
				}
				
				//execute task till it is finished
				if (tasks != null && !tasks.isEmpty()) {
					//execute new task
					if (!(currentTask == tasks.get(0))) {
						currentTask = tasks.get(0);
						navigator.doTask(currentTask);
					}
					
					/*while (!currentTask.isFinished(worldModel.getGPSModel().getPosition())) {
						waitForNextCycle();
					}*/
					//remove task if it is finished
					if (currentTask.isFinished(worldModel.getGPSModel().getPosition())) {
						System.out.println("task finished, removing");
						tasks.remove(0);
					}
				//if no task is available the boat is stopped
				} else {
					System.out.println("WARNUNG: WorkerThread: kein Task mehr vorhanden: nutze StopTask ");
					if (!(currentTask instanceof StopTask)) {
						currentTask = new StopTask();
						navigator.doTask(currentTask);
					}
				}
				waitForNextCycle();
				System.out.println("workerthread runs and runs");
			}
		}
		
		private void waitForNextCycle() {
			try {
				Thread.sleep(WAIT_TIME);
			} catch (InterruptedException e) {
				// do nothing, since earlier execution does not disturb program
				//LOG.warn("interrupted while waiting", e);
			}
		}

		public void setTasks(List<Task> tasks) {
			this.tasks = tasks;
		}

		/**
		 * Indicates whether the thread should stop running. If set, the thread tries to end normally,
		 * this can possibly not be done immediately.
		 * 
		 * @param stop
		 */
		public void setStop(boolean stop) {
			this.stop = stop;
		}
	}
}
