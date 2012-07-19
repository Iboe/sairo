package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.ReachCircleTask;

/**
 * Calculates the relative angle from the current position to the goal defined in the task
 * to execute and hands it over to the pilot.
 * 
 * @author hscheel
 */
public class ReachCircleWorker extends WorkerThread<ReachCircleTask> {
	
	//private static final Logger LOG = LoggerFactory.getLogger(ReachCircleWorker.class);
	
	/**
	 * Creates a new initialized instance.
	 * 
	 * @param pilot the pilot to send the commands to
	 * @param navigator the navigator who created this instance
	 */
	public ReachCircleWorker(Pilot pilot, Navigator navigator) {
		super(pilot, navigator);
	}
	
	/**
	 * Calculates the angle towards the goal and hands it over to the pilot. Checks if beating
	 * is necessary.
	 */
	@Override
	public void run() {
		while (!isInterrupted()) {
			double angle = calcAngleToGPS(task.getCenter());
			
			//angle += calcIdealLineAngle(worldModel.getGPSModel().getPosition());
			
			if (isBeatNecessary(angle)) {
				navigator.doTask(new BeatTask(task, task.getCenter()));
			} else {
				pilot.driveAngle((int) angle);
			}
			
			waitForNextCycle();
		}
	}
}
