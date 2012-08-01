package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.Task;

/**
 * Implements a beat behavior. Avoids the direct angle to the goal by holding a
 * specified angle relative to the wind. After the goal can be reached directly, 
 * the original {@link Task} is executed again. 
 * 
 * @author hscheel
 *
 */
public class BeatWorker extends WorkerThread<BeatTask> {

	/**
	 * The angle relative to the wind, where the boat can drive fastest to the left.
	 */
	public static final int BEAT_ANGLE_LEFT = Integer.parseInt(System.getProperty(
			BeatWorker.class.getSimpleName() + ".beatAngleLeft"));
	
	/**
	 * The angle relative to the wind, where the boat can drive fastest to the right.
	 */
	public static final int BEAT_ANGLE_RIGHT = Integer.parseInt(System.getProperty(
			BeatWorker.class.getSimpleName() + ".beatAngleRight"));
	
	/**
	 * The compass angle that the boat should have towards the goal, when the beating should stop.
	 */
	public static final int MAX_ANGLE_TO_DRIVE = 90;
	
	/**
	 * Creates a new initialized instance.
	 * 
	 * @param pilot the pilot to send the commands to
	 * @param navigator the navigator who created this instance
	 */
	public BeatWorker(Pilot pilot, Navigator navigator) {
		super(pilot, navigator);
	}

	/**
	 * Checks if the goal can be reached directly. If not, the boat drives fast diagonal
	 * to the goal by holding a specific angle to the wind.
	 */
	@Override
	public void run() {
		boolean pilotSet = false;
		
		while(!isInterrupted()) {
			double angle = calcAngleToGPS(task.getGoal()) - 
				worldModel.getCompassModel().getCompass().getYaw();
			
			if (angle >= MAX_ANGLE_TO_DRIVE || angle <= -MAX_ANGLE_TO_DRIVE) {
				break;
			}
			
			if (!pilotSet) {
				if (angle >= 0) {
					pilot.holdAngleToWind(BEAT_ANGLE_RIGHT);
				} else {
					pilot.holdAngleToWind(BEAT_ANGLE_LEFT);
				}
				
				pilotSet = true;
			}
			waitForNextCycle();
		}
		
		LOG.debug("leaving run method now");
		
		if (task.getContinueTask() != null) {
			navigator.doTask(task.getContinueTask());
		}
	}
}
