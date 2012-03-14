package de.fhb.sailboat.mission;

import java.util.List;

import de.fhb.sailboat.data.GPS;

public class ReachPolygonTask implements Task {

	private final List<GPS> points;

	public ReachPolygonTask(List<GPS> points) {
		if (points == null || points.size() <= 1) {
			throw new IllegalArgumentException("at least two GPS points needed");
		} else {
			this.points = points;
		}
	}
	
	public List<GPS> getPoints() {
		return points;
	}

	@Override
	public boolean isFinished(GPS position) {
		// TODO Auto-generated method stub
		return false;
	} 
}
