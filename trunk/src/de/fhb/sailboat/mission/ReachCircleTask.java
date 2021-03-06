package de.fhb.sailboat.mission;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.GPS;

/**
 * Task for driving towards a circle, consisting of a {@link GPS} point as center and a radius.
 * The task is finished if the boat position is inside of the circle. 
 * 
 * @author hscheel
 *
 */
public class ReachCircleTask implements Task {

	private static final Logger LOG = Logger.getLogger(ReachCircleTask.class);
	
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
		LOG.debug(this.toString());
	}
	
	/**
	 * Getter for the {@link GPS} position of the center of the circle to reach.
	 * 
	 * @return the {@link GPS} position of the center of the circle
	 */
	public GPS getCenter() {
		return center;
	}
	
	/**
	 * Getter for the radius of the circle to reach.
	 * 
	 * @return the radius of the circle
	 */
	public int getRadius() {
		return radius;
	}

	/*
	 * Checks if the {@link GPS} point is inside the circle.
	 */
	@Override
	public boolean isFinished(GPS position) {
		Bearing bearing = Bearing.calculateBearing(center.getLongitude(), center.getLatitude(), 
				position.getLongitude(), position.getLatitude());
		LOG.debug(this.toString() + " finished");
		return bearing.getDistance() < radius;
	}

	/***
	 * @author Tobias Koppe
	 */
	@Override
	public String toString() {
		return MissionSystemTextEnum.REACH_CIRCLE_TASK + " [center=" + center + ", radius=" + radius + "]";
	}
}
