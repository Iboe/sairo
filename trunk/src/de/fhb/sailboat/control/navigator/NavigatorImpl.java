package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class NavigatorImpl implements Navigator{

	//private static final Logger LOG = LoggerFactory.getLogger(NavigatorImpl.class);
	private final Pilot pilot;
	private final WorldModel worldModel;
	private Thread workerThread = null;
	
	public NavigatorImpl(Pilot pilot) {
		this.pilot = pilot;
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	@Override
	public void doTask(Task task) {
		if (task == null) {
			throw new NullPointerException();
		} else if (task instanceof ReachCircleTask) {
			navigateToCircle((ReachCircleTask) task);
		} else if (task instanceof ReachPolygonTask) {
			navigateToPolygon((ReachPolygonTask) task);
		} else if (task instanceof PrimitiveCommandTask) {
			handlePrimitiveCommands((PrimitiveCommandTask) task);
		} else if (task instanceof StopTask) {
			stop((StopTask) task);
		} else {
			throw new UnsupportedOperationException("can not handle task: " + task);
		}
	}
	
	private void handlePrimitiveCommands(PrimitiveCommandTask task) {
		if (task.getPropellor() != null) {
			pilot.setPropellor(task.getPropellor().intValue());
		}
		
		if (task.getRudder() != null) {
			pilot.setRudder(task.getRudder().intValue());
		}
		
		if (task.getSail() != null) {
			pilot.setSail(task.getSail().intValue());
		}
		
		task.setExecuted(true);
	}
	
	private void stop(StopTask task) {
		ReachCircleTask reachCircleTask = new ReachCircleTask(
				worldModel.getGPSModel().getPosition(), 10);
		
		navigateToCircle(reachCircleTask);
	}
	
	/**
	 * Starts new thread which repeatedly calculates angle to goal.
	 * 
	 * @param task the task to be executed
	 */
	private void navigateToCircle(ReachCircleTask task) {
		if (workerThread == null) {
			workerThread = new ReachCircleWorker(pilot);
		} else if (workerThread.isAlive() && !(workerThread instanceof ReachCircleWorker)) {
			//stop thread if other type of task is executed
			stopThread();
			workerThread = new ReachCircleWorker(pilot);
		}
		
		((ReachCircleWorker) workerThread).setTask(task);
		if (!workerThread.isAlive()) {
			workerThread.start();
		}
	}
	
	private void navigateToPolygon(ReachPolygonTask task) {
		if (workerThread == null) {
			workerThread = new ReachPolygonWorker(pilot);
		} else if (workerThread.isAlive() && !(workerThread instanceof ReachPolygonWorker)) {
			//stop thread if other type of task is executed
			stopThread();
			workerThread = new ReachPolygonWorker(pilot);
		}
		
		((ReachPolygonWorker) workerThread).setTask(task);
		if (!workerThread.isAlive()) {
			workerThread.start();
		}
	}
	
	private void stopThread() {
		workerThread.interrupt();
	}
}
