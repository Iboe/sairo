package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class StopTask implements Task{

	private final boolean turnPropellorOff;
	
	public StopTask() {
		this(false);
	}
	
	public StopTask(boolean turnPropellorOff) {
		this.turnPropellorOff = turnPropellorOff;
	}
	
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	public boolean isTurnPropellorOff() {
		return turnPropellorOff;
	}
}
