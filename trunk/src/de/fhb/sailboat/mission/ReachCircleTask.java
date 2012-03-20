package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class ReachCircleTask implements Task {

	private final GPS center;
	private final int radius;
	
	/**
	 * Creates a new initialized instance.
	 * 
	 * @param center goal of this task
	 * @param radius tolerance range to center in meter
	 */
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
		Bearing bearing = Bearing.calculateBearing(center.getLongitude(), center.getLatitude(), 
				position.getLongitude(), position.getLatitude());
		
		return bearing.getDistance() < radius;
	}
}
