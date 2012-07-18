package de.fhb.sailboat.control.navigator;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.mission.BeatTask;

public class BeatWorker extends WorkerThread<BeatTask> {

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
		
		LOG.debug("leaving run methode now");
		
		if (task.getContinueTask() != null) {
			navigator.doTask(task.getContinueTask());
		}
	}
}
