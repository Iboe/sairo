package de.fhb.sailboat.control.planner;

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
			LOG.warn("execute mission {}", mission);
			LOG.warn("number of mission elements:" + mission.getTasks().size(), mission);
			
			if (workerThread == null) {
				workerThread = new WorkerThread(navigator);
			} else if (workerThread.isAlive()) {
				//start mission in a new thread
				stopThread();
				workerThread = new WorkerThread(navigator);
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
	
 	/**
	 * Stops the {@link WorkerThread} by interrupting it.
	 */
	private void stopThread() {
		if (workerThread != null && workerThread.isAlive()) {
			workerThread.interrupt();
		}
	}
}
