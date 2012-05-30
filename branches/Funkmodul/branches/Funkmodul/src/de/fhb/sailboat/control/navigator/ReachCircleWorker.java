package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.ReachCircleTask;

/**
 * Calculates relative angle from current position to goal defined in task and 
 * hands it over to the pilot.
 */
public class ReachCircleWorker extends WorkerThread<ReachCircleTask> {
	
	//private static final Logger LOG = LoggerFactory.getLogger(ReachCircleWorker.class);
	
	public ReachCircleWorker(Pilot pilot, Navigator navigator) {
		super(pilot, navigator);
	}
	
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
