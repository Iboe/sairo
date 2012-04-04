package de.fhb.sailboat.mission;

import java.util.List;

import de.fhb.sailboat.data.GPS;

public class ReachPolygonTask implements Task {

	private final List<GPS> points;

	public ReachPolygonTask(List<GPS> points) {
		if (points == null || points.size() <= 2) {
			throw new IllegalArgumentException("at least three GPS points needed");
		} else {
			//for calculations, the polygon must be closed, which means the first
			//and the last point are the same
			if (!points.get(0).equals(points.get(points.size() - 1))) {
				points.add(points.get(0));
			}
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

	@Override
	public String toString() {
		return "ReachPolygonTask [points=" + points + "]";
	} 
}
