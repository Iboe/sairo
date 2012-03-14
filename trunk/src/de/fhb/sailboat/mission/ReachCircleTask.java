package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class ReachCircleTask implements Task {

	private final GPS center;
	private final int radius;
	
	public ReachCircleTask(GPS center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public GPS getCenter() {
		return center;
	}
	
	public int getRadius() {
		return radius;
	}

	@Override
	public boolean isFinished(GPS position) {
		double distLong = center.getLongitude() - position.getLongitude();
		double distLat = center.getLatitude() - position.getLatitude();
		double length = Math.sqrt(Math.pow(distLat, 2) - Math.pow(distLong, 2));
		
		return length < radius;
	}
}
