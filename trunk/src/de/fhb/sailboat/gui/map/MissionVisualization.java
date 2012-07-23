package de.fhb.sailboat.gui.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.GUIModel;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.RepeatTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;

/**
 * Class to visualize a mission, saves missions of older calls so that it can
 * display the current task. Solved tasks are displayed grey, current task is
 * green and upcoming tasks will be red.
 * 
 * @author Paul Lehmann
 * 
 */
public class MissionVisualization {

	private Mission whole;

	private JMapViewer map;

	private List<MapMarker> markerList;
	private List<MapPolygon> polygonList;

	private GUIModel model;

	public MissionVisualization(JMapViewer map) {
		this.map = map;

		markerList = new ArrayList<MapMarker>();
		polygonList = new ArrayList<MapPolygon>();
	}

	public void visualize(GUIModel model) {

		clearMap();

		Mission current = model.getMissionTasksLeft();

		this.model = model;

		if (whole == null) {
			whole = new MissionImpl();
			whole.setTasks(new ArrayList<Task>());
			for (int i = 0; i < model.getMissionTasksLeft().getTasks().size(); i++)
				whole.getTasks().add(
						model.getMissionTasksLeft().getTasks().get(i));
		}

		System.out.println(whole.getTasks().size());
		if (current.getTasks().size() < whole.getTasks().size()) {
			Mission solved = getSolved(current);
			visualizeTasks(current, solved);
		} else {
			visualizeTasks(whole);
		}
	}

	private void visualizeTasks(Mission whole) {
		visualizeTasks(whole, null);
	}

	private void visualizeTasks(Mission current, Mission solved) {
		if (solved == null) {
			// visualize whole mission
			visualizeTask(current.getTasks().get(0), Color.GREEN);

			for (int i = 1; i < current.getTasks().size(); i++)
				visualizeTask(current.getTasks().get(i), Color.RED);

		} else {
			// visualize solved and upcoming tasks
			for (int i = 0; i < solved.getTasks().size(); i++)
				visualizeTask(solved.getTasks().get(i), Color.GRAY);

			visualizeTask(current.getTasks().get(0), Color.GREEN);

			for (int i = 1; i < current.getTasks().size(); i++)
				visualizeTask(current.getTasks().get(i), Color.RED);
		}
	}

	private void visualizeTask(Task task, Color color) {

		if (task instanceof ReachCircleTask) {
			markerList.add(new MapMarkerDot(color, ((ReachCircleTask) task)
					.getCenter().getLatitude(), ((ReachCircleTask) task)
					.getCenter().getLongitude()));
			map.addMapMarker(markerList.get(markerList.size() - 1));

		} else if (task instanceof ReachPolygonTask) {
			polygonList.add(new MapPolygonImpl(((ReachPolygonTask) task)
					.getPoints(), color));
			map.addMapPolygon(polygonList.get(polygonList.size() - 1));

		} else if (task instanceof CompassCourseTask)
			drawLine(model.getGps().getPosition(),
					((CompassCourseTask) task).getAngle(), color);

		else if (task instanceof HoldAngleToWindTask)
			drawLine(model.getGps().getPosition(),
					((HoldAngleToWindTask) task).getAngle(), color);

		else if (task instanceof RepeatTask) {
			// TODO
		} else if (task instanceof StopTask) {
			// TODO
		} else if (task instanceof BeatTask) {
			// TODO
		}
	}

	/**
	 * Writes Tasks which are solved into solvedTasks.
	 */
	private Mission getSolved(Mission current) {
		Mission solved = new MissionImpl();
		solved.setTasks(new ArrayList<Task>());

		for (int i = 0; i < whole.getTasks().size() - current.getTasks().size(); i++)
			solved.getTasks().add(whole.getTasks().get(i));

		return solved;
	}

	private void drawLine(GPS a, int angle, Color color) {
		double longitude;
		if (angle > 180 || angle < 0) {
			longitude = GPSTransformations.coordinateToGPS(
					map.getPosition(0, 0)).getLongitude();
		} else {
			longitude = GPSTransformations.coordinateToGPS(
					map.getPosition(map.getWidth() - 1, 0)).getLongitude();
		}

		double deltaX = longitude - a.getLongitude();
		double deltaY;
		if (Math.sin(90 - angle) != 0) {
			deltaY = deltaX * Math.sin(angle) / (Math.sin(90 - angle));
		} else
			deltaY = 0;

		GPS b = new GPS(a.getLatitude() + deltaY, longitude);

		drawLine(a, b, color);
	}

	/**
	 * Draw a line from gps-position a to gps-position b.
	 * 
	 * @param a
	 *            src
	 * @param b
	 *            dest
	 */
	private void drawLine(GPS a, GPS b, Color color) {
		List<GPS> list = new ArrayList<GPS>();
		list.add(a);
		list.add(b);
		polygonList.add(new MapPolygonImpl(list, color));
		map.addMapPolygon(polygonList.get(polygonList.size() - 1));
	}

	public void clearMap() {
		for (int i = 0; i < this.markerList.size(); i++)
			map.removeMapMarker(this.markerList.get(i));
		this.markerList.clear();

		for (int i = 0; i < this.polygonList.size(); i++)
			map.removeMapPolygon(this.polygonList.get(i));
		this.polygonList.clear();
	}

}
