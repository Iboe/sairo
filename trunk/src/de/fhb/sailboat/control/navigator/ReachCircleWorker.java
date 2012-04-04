package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.mission.ReachCircleTask;

/**
 * Calculates relative angle from current position to goal defined in task and 
 * hands it over to the pilot.
 */
public class ReachCircleWorker extends WorkerThread<ReachCircleTask> {
	
	//private static final Logger LOG = LoggerFactory.getLogger(ReachCircleWorker.class);
	
	private ReachCircleTask task;
	
	public ReachCircleWorker(Pilot pilot) {
		super(pilot);
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			double angle = calcAngleToGPS(task.getCenter());
			
			pilot.driveAngle((int) angle);
			waitForNextCycle();
		}
	}
	
	@Override
	public void setTask(ReachCircleTask task) {
		this.task = task;
	}
}
