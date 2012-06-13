package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class HoldAngleToWindTask implements Task {

	private final int angle;
	
	public HoldAngleToWindTask(int angle) {
		this.angle = angle;
	}
	
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	public int getAngle() {
		return angle;
	}
	
	@Override
	public String toString() {
		return "HoldAngleToWindTask [angle=" + angle + "]";
	}
}
