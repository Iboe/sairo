package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class BeatTask implements Task {

	private final GPS goal;
	private final Task continueTask;
	
	public BeatTask(GPS goal) {
		this(null, goal);
	}
	
	public BeatTask(Task continueTask, GPS goal) {
		this.continueTask = continueTask;
		this.goal = goal;
	}
	
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	public Task getContinueTask() {
		return continueTask;
	}

	public GPS getGoal() {
		return goal;
	}
}