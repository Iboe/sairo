package de.fhb.sailboat.control.navigator;

import java.util.List;

import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.ReachPolygonTask;

/**
 * Calculates relative angle from current position to the centroid of the polygon 
 * defined in task and hands it over to the pilot.
 */
public class ReachPolygonWorker extends WorkerThread<ReachPolygonTask> {

	private ReachPolygonTask task;
	private boolean taskChanged;
	private GPS centroid;
	
	public ReachPolygonWorker(Pilot pilot) {
		super(pilot);
		taskChanged = true;
	}

	@Override
	public void setTask(ReachPolygonTask task) {
		this.task = task;
		taskChanged = true;
	}
	
	@Override
	public void run() {
		while (!isInterrupted()) {
			double angle;
			
			//the centroid has only to be calculated once
			//but has to be recalculated if task changed
			if (taskChanged) {
				centroid = calcCentroid();
			}
			
			angle = calcAngleToGPS(centroid);
			pilot.driveAngle((int) angle);
			waitForNextCycle();
		}
	}
	
	/**
	 * Calculate the centroid of the polygon, link to formula: 
	 * http://de.wikipedia.org/wiki/Geometrischer_Schwerpunkt#Polygon 
	 * 
	 * @return the centroid of the polygon defined in task
	 */
	private GPS calcCentroid() {
		double area = 0;
		double cenLat = 0;
		double cenLong = 0;
		double temp;
		List<GPS> points = task.getPoints();
		int n = points.size() - 1;
		GPS current, next;
		
		for (int i = 0; i < n; i++) {
			current = points.get(i);
			next = points.get(i + 1);
			
			temp = current.getLatitude() * next.getLongitude() - next.getLatitude() * current.getLongitude();
			area += temp;
			cenLat += temp * (current.getLatitude() + next.getLatitude() );
			cenLong += temp * (current.getLongitude() + next.getLongitude() );
		}
		
		if (area == 0) {
			throw new IllegalStateException("can not calculate centroid: invalid polygon " + task);
		}
		cenLat /= area * 3; //the correct value for area is area / 2, but it is
							//not used directly, so the real value is not calculated 
		cenLong /= area * 3;
		return new GPS(cenLat, cenLong);
	}
}
