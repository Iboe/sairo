package de.fhb.sailboat.gui.map.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.MainControllerModel;
import de.fhb.sailboat.gui.map.MapMarkerDot;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.gui.map.MapPolygonImpl;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.MissionVO;
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

	private MissionVO whole;

	private JMapViewer map;

	private List<MapMarker> markerList;
	private List<MapPolygon> polygonList;

	// variable for determining whether it's a new mission or the old one
	private int taskCountLastCall = 0;

	private MainControllerModel model;

	public MissionVisualization(JMapViewer map) {
		this.map = map;

		markerList = new ArrayList<MapMarker>();
		polygonList = new ArrayList<MapPolygon>();
	}

	/**
	 * Visualizes the current mission with access to data of the boat.
	 * 
	 * @param model
	 *            boat data
	 */
	public void visualize(MainControllerModel model) {

		clearMap();

		MissionVO current = model.getMissionTasksLeft();

		if (current.getTasks() != null) {
			this.model = model;

			if (whole == null || taskCountLastCall < current.getTasks().size()) {
				whole = new MissionVO();
				whole.setTasks(new ArrayList<Task>());
				for (int i = 0; i < current.getTasks().size(); i++)
					whole.getTasks().add(current.getTasks().get(i));
			}

			if (current.getTasks().size() < whole.getTasks().size()) {
				MissionVO solved = getSolved(current);
				visualizeTasks(current, solved);
			} else {
				visualizeTasks(whole);
			}
			taskCountLastCall = current.getTasks().size();
		}
	}

	/**
	 * Visualizes the current mission without access to data of the boat.
	 * 
	 * @param mission
	 *            mission to be displayed
	 */
	public void visualize(MissionVO mission) {

		clearMap();

		MissionVO current = mission;

		if (current.getTasks() != null) {
			this.model = null;

			if (whole == null || taskCountLastCall < current.getTasks().size()) {
				whole = new MissionVO();
				whole.setTasks(new ArrayList<Task>());
				for (int i = 0; i < current.getTasks().size(); i++)
					whole.getTasks().add(current.getTasks().get(i));
			}

			if (current.getTasks().size() < whole.getTasks().size()) {
				MissionVO solved = getSolved(current);
				visualizeTasks(current, solved);
			} else {
				visualizeTasks(whole);
			}
			taskCountLastCall = current.getTasks().size();
		}
	}

	/**
	 * Visualize tasks in a mission if no task is solved yet.
	 * 
	 * @param whole
	 *            whole mission
	 */
	private void visualizeTasks(MissionVO whole) {
		visualizeTasks(whole, null);
	}

	/**
	 * Visualize tasks in a mission and the solved tasks.
	 * 
	 * @param current
	 *            current mission
	 * @param solved
	 *            solved tasks
	 */
	private void visualizeTasks(MissionVO current, MissionVO solved) {
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

	/**
	 * Visualize a single task.
	 * 
	 * @param task
	 *            task to be visualized
	 * @param color
	 *            color in which the task should be displayed
	 */
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

		} else if (task instanceof CompassCourseTask) {
			if (model != null)
				drawLine(model.getGps().getPosition(),
						((CompassCourseTask) task).getAngle(), color);

		} else if (task instanceof HoldAngleToWindTask) {
			/*
			 * if (model != null) drawLine(model.getGps().getPosition(),
			 * ((HoldAngleToWindTask) task).getAngle(), color);
			 */

		} else if (task instanceof RepeatTask) {
			// TODO
		} else if (task instanceof StopTask) {
			// TODO
		} else if (task instanceof BeatTask) {
			// TODO
		}
	}

	/**
	 * Writes Tasks which are solved into solvedTasks.
	 * 
	 * @return solved tasks
	 */
	private MissionVO getSolved(MissionVO current) {
		MissionVO solved = new MissionVO();
		solved.setTasks(new ArrayList<Task>());

		for (int i = 0; i < whole.getTasks().size() - current.getTasks().size(); i++)
			solved.getTasks().add(whole.getTasks().get(i));

		return solved;
	}

	/**
	 * Draws a line from point a in an angle.
	 * 
	 * @param a
	 *            start of line
	 * @param angle
	 *            angle of line
	 * @param color
	 *            color of line
	 */
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

	/**
	 * Clears map.
	 */
	public void clearMap() {
		for (int i = 0; i < this.markerList.size(); i++)
			map.removeMapMarker(this.markerList.get(i));
		this.markerList.clear();

		for (int i = 0; i < this.polygonList.size(); i++)
			map.removeMapPolygon(this.polygonList.get(i));
		this.polygonList.clear();
	}

}
