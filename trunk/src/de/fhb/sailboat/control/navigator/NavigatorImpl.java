package de.fhb.sailboat.control.navigator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.RepeatTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Implementation of {@link Navigator} which calculates commands based on
 * the {@link Task} to execute. The commands are hand over to a {@link Pilot}. 
 * 
 * @author hscheel
 *
 */
public class NavigatorImpl implements Navigator {

	private static final Logger LOG = LoggerFactory.getLogger(NavigatorImpl.class);
	private final Pilot pilot;
	private final WorldModel worldModel;
	private Thread workerThread = null;
	
	/**
	 * Creates a new instance with the specified {@link Pilot} to hand over commands 
	 * 
	 * @param pilot the pilot to hand over commands, must not be null
	 */
	public NavigatorImpl(Pilot pilot) {
		if (pilot == null) {
			throw new NullPointerException();
		}
		
		this.pilot = pilot;
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	/**
	 * Executes the specified task or throws an {@link UnsupportedOperationException} if the 
	 * task is not supported.
	 */
	@Override
	public void doTask(Task task) {
		LOG.info("execute task: {}", task);
		
		if (task == null) {
			throw new NullPointerException();
		} else if (task instanceof ReachCircleTask) {
			navigateToCircle((ReachCircleTask) task);
		} else if (task instanceof ReachPolygonTask) {
			navigateToPolygon((ReachPolygonTask) task);
		} else if (task instanceof PrimitiveCommandTask) {
			handlePrimitiveCommands((PrimitiveCommandTask) task);
		} else if (task instanceof CompassCourseTask) {
			driveCompassCourse((CompassCourseTask) task);
		} else if (task instanceof HoldAngleToWindTask) {
			holdAngleToWind((HoldAngleToWindTask) task);
		} else if (task instanceof RepeatTask) {
			handleRepeatTask((RepeatTask) task);
		} else if (task instanceof StopTask) {
			stop((StopTask) task);
		} else if (task instanceof BeatTask) {
			beat((BeatTask) task);
		} else {
			throw new UnsupportedOperationException("can not handle task: " + task);
		}
	}
	
	/**
	 * Instructs the pilot to directly set the specified values to the actuators.
	 * 
	 * @param task the task to be executed
	 */
	private void handlePrimitiveCommands(PrimitiveCommandTask task) {
		stopThread();
		pilot.stopThread();
		
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
	
	/**
	 * Instructs the pilot to hold the specified angle relative to the current compass angle.
	 * 
	 * @param task the task to be executed
	 */
	private void driveCompassCourse(CompassCourseTask task) {
		stopThread();
		pilot.driveAngle(task.getAngle());
	}
	
	/**
	 * Instructs the pilot to hold the specified angle relative to the current wind direction. 
	 * 
	 * @param task the task to be executed
	 */
	private void holdAngleToWind(HoldAngleToWindTask task) {
		stopThread();
		pilot.holdAngleToWind(task.getAngle());
	}
	
	/**
	 * Executes the current task of the list of tasks, that shall be repeated.
	 * 
	 * @param task the task to be executed
	 */
	private void handleRepeatTask(RepeatTask task) {
		doTask(task.getCurrentTask());
	}
	
	/**
	 * Holds the current position. The task is never set to be finished.
	 * 
	 * @param task the task to be executed
	 */
	private void stop(StopTask task) {
		ReachCircleTask reachCircleTask = new ReachCircleTask(
				worldModel.getGPSModel().getPosition(), 10);
		
		if (task.isTurnPropellorOff()) {
			pilot.setPropellor(LocomotionSystem.PROPELLOR_NORMAL);
		}
		navigateToCircle(reachCircleTask);
	}
	
	/**
	 * Starts a thread which drives diagonal to the goal until it can be reached directly.
	 * 
	 * @param task the task to be executed
	 */
	private void beat(BeatTask task) {
		if (workerThread == null) {
			workerThread = new BeatWorker(pilot, this);
		} else if (workerThread.isAlive() && !(workerThread instanceof BeatWorker)) {
			//stop thread if other type of task is executed
			stopThread();
			workerThread = new BeatWorker(pilot, this);
		}
		
		((BeatWorker) workerThread).setTask(task);
		if (!workerThread.isAlive()) {
			workerThread.start();
		}
	}
	
	/**
	 * Starts a thread which repeatedly calculates angle to the center of the circle.
	 * 
	 * @param task the task to be executed
	 */
	private void navigateToCircle(ReachCircleTask task) {
		if (workerThread == null) {
			workerThread = new ReachCircleWorker(pilot, this);
		} else if (workerThread.isAlive() && !(workerThread instanceof ReachCircleWorker)) {
			//stop thread if other type of task is executed
			stopThread();
			workerThread = new ReachCircleWorker(pilot, this);
		}
		
		((ReachCircleWorker) workerThread).setTask(task);
		if (!workerThread.isAlive()) {
			workerThread.start();
		}
	}
	
	/**
	 * Starts a thread which repeatedly calculates angle to the center of the polygon.
	 * 
	 * @param task the task to be executed
	 */
	private void navigateToPolygon(ReachPolygonTask task) {
		if (workerThread == null) {
			workerThread = new ReachPolygonWorker(pilot, this);
		} else if (workerThread.isAlive() && !(workerThread instanceof ReachPolygonWorker)) {
			//stop thread if other type of task is executed
			stopThread();
			workerThread = new ReachPolygonWorker(pilot, this);
		}
		
		((ReachPolygonWorker) workerThread).setTask(task);
		if (!workerThread.isAlive()) {
			workerThread.start();
		}
	}
	
	/**
	 * Stops the workerThread by interrupting it.
	 */
	private void stopThread() {
		if (workerThread != null) {
			workerThread.interrupt();
		}
	}
}
