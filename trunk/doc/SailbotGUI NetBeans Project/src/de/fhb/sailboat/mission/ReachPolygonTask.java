package de.fhb.sailboat.mission;

import java.awt.Polygon;
import java.util.List;

import de.fhb.sailboat.data.GPS;

public class ReachPolygonTask implements Task {

	private static final int PRECISION = 100000;
	private final List<GPS> points;
	private final Polygon polygon;

	public ReachPolygonTask(List<GPS> points) {
		if (points == null || points.size() <= 2) {
			throw new IllegalArgumentException("at least three GPS points needed");
		} else {
			//for calculations, the polygon must be closed, which means the first
			//and the last point have to have equal coordinates
			if (!points.get(0).hasEqualCoordinates(points.get(points.size() - 1))) {
				points.add(points.get(0));
			}
			this.points = points;
		}
		
		polygon = buildPolygon();
	}
	
	public List<GPS> getPoints() {
		return points;
	}

	/**
	 * Checks if the GPS point is inside of the polygon.
	 */
	@Override
	public boolean isFinished(GPS position) {
		//this implementation may be optimized by own calculation if the
		//given position is inside the polygon, for example using
		//http://rw7.de/ralf/inffaq/polygon.html
		
		//java.awt.polygon uses integer values for its points, so the GPS
		//coordinates have to scaled up to achieve the required precision
		int latitude = (int) (position.getLatitude() * PRECISION);
		int longitude = (int) (position.getLongitude() * PRECISION);
		
		return polygon.contains(latitude, longitude);
	}

	private Polygon buildPolygon() {
		int size = points.size();
		int[] latitudes = new int[size];
		int[] longitudes = new int[size];
		
		//java.awt.polygon uses integer values for its points, so the GPS
		//coordinates have to scaled up to achieve the required precision
		for (int i = 0; i < size; i++) {
			latitudes[i] = (int) (points.get(i).getLatitude() * PRECISION);
			longitudes[i] = (int) (points.get(i).getLongitude() * PRECISION);
		}
		
		return new Polygon(latitudes, latitudes, size);
	}
	
	@Override
	public String toString() {
		return "ReachPolygonTask [points=" + points + "]";
	} 
}
