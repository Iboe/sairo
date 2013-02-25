package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task for crossing a line, defined by two {@link GPS} points. The boat heads toward the line until
 * the line it is reached. The task is finished when the boat crossed the line. 
 * 
 * The task is not completely implemented yet, so it must not be used.
 * 
 * @author hscheel
 *
 */
public class CrossLineTask implements Task {

	private final GPS startPoint;
	private final GPS endPoint;
	
	/**
	 * Creates a new instance defining the line to cross by it end points.
	 * 
	 * @param startpoint the start of the line
	 * @param endPoint the end of the line
	 */
	public CrossLineTask(GPS startpoint, GPS endPoint) {
		this.startPoint = startpoint;
		this.endPoint = endPoint;
	}
	
	/**
	 * Getter for the {@link GPS} position of the start point of the line to cross.
	 * 
	 * @return the position of the start point of the line
	 */
	public GPS getStartPoint() {
		return startPoint;
	}
	
	/**
	 * Getter for the {@link GPS} position of the end point of the line to cross.
	 * 
	 * @return the position of the end point of the line
	 */
	public GPS getEndPoint() {
		return endPoint;
	}

	/*
	 * Checks if the line was already crossed.
	 * 
	 * This is not implemented yet.
	 */
	@Override
	public boolean isFinished(GPS position) {
		throw new IllegalStateException("not implemented yet");
	}

	@Override
	public String toString() {
		return "CrossLineTask [endPoint=" + endPoint + ", startPoint=" + startPoint + "]";
	}
}
