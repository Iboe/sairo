package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class StopTask implements Task{

	@Override
	public boolean isFinished(GPS position) {
		return false;
	}
}
