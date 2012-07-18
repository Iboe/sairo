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
	 * Creates a new initialized instance.
	 * 
	 * @param pilot the pilot to send the commands to
	 * @param navigator the navigator who created this instance
	 */
	public BeatWorker(Pilot pilot, Navigator navigator) {
		super(pilot, navigator);
	}

	@Override
	public void run() {
		boolean pilotSet = false;
		
		while(!isInterrupted()) {
			double angle = calcAngleToGPS(task.getGoal()) - 
				worldModel.getCompassModel().getCompass().getYaw();
			
			if (angle >= 90 || angle <= -90) {
				break;
			}
			
			if (!pilotSet) {
				if (angle >= 0) {
					pilot.holdAngleToWind(-45);
				} else {
					pilot.holdAngleToWind(45);
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
