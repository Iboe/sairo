package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * 
 * 
 * @author hscheel
 *
 */
public class CrossLineTask implements Task {

	private final GPS startPoint;
	private final GPS endPoint;
	
	public CrossLineTask(GPS startpoint, GPS endPoint) {
		this.startPoint = startpoint;
		this.endPoint = endPoint;
	}
	
	public GPS getStartPoint() {
		return startPoint;
	}
	
	public GPS getEndPoint() {
		return endPoint;
	}

	@Override
	public boolean isFinished(GPS position) {
		// TODO Auto-generated method stub
		return false;
	}
}
