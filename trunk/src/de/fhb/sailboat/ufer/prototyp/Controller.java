package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.ufer.prototyp.utility.MapMarkerToGPS;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * This class manages the data pipe between view, model and the sensors.
 * 
 * @author Patrick Rutter
 * 
 */
public class Controller {

	// Variables
	private Model model;
	
	private WorldModel worldModel;

	public Controller() {
		this.model = new Model();
		worldModel=WorldModelImpl.getInstance();
	}

	// Committer (used for sending data to other sailbot classes)

	/**
	 * For the milestone. Stores MapMarkers set in View and stored in Model as a
	 * collection of CircleTasks in Mission.
	 */
	public void commitCircleMarkerList(Planner planner) {
		ArrayList<MapMarker> markerList = this.model.getCircleMarkerList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(null, null, 100));
		for (int i = 0; i < markerList.size(); i++) {
			tasks.add(new ReachCircleTask(new GPS(markerList.get(i).getLat(),
					markerList.get(i).getLon()), 3));
		}
		tasks.add(new PrimitiveCommandTask(null, null, 73));

		mission.setTasks(tasks);
		
		System.out.println("Mission passed.");
		System.out.println("First GPS marker: " + markerList.get(0).getLat() + " / " + markerList.get(0).getLon());
		
		planner.doMission(mission);
	}
	
	public void commitPolyMarkerList(Planner planner) {
		ArrayList<MapMarker> markerList = this.model.getPolyMarkerList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(null, null, 100));
		tasks.add(new ReachPolygonTask(MapMarkerToGPS.toGPS(markerList)));
		tasks.add(new PrimitiveCommandTask(null, null, 73));
		
		mission.setTasks(tasks);
		
		System.out.println("Mission passed.");
		//System.out.println("First GPS marker: " + markerList.get(0).getLat() + " / " + markerList.get(0).getLon());
		
		planner.doMission(mission);
	}
	
	public void commitStopMotor(Planner planner) {
		// TODO Implement proper stop mission task beyond resetting motor
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(null, null, 73));
		
		mission.setTasks(tasks);
		
		System.out.println("Mission passed.");
		//System.out.println("First GPS marker: " + markerList.get(0).getLat() + " / " + markerList.get(0).getLon());
		
		planner.doMission(mission);
	}

	// Updater (used to update a sensor reading and store it in model, kind of
	// like a more sophisticated setter)
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 */
	public void updateAll() {
		updateWind();
		updateCompass();
		updateGps();
	}

	/**
	 * This method is used to create an wide array of pseudo-random values for
	 * testing the GUI's ability to display values.
	 */
	public void updateRandom() {
		Random dice = new Random();

		//this.model.setCompDirection(dice.nextInt(361));
		
		this.model.getWind().setWind(new Wind(dice.nextInt(361), dice.nextInt(3000)));

		this.model.setGpsPosition(new GPS(dice.nextDouble() + dice.nextInt(12), dice.nextDouble() + dice.nextInt(12)));

		this.model.setGpsSatelites(dice.nextInt(5));
	}

	public void updateWind() {
		this.model.setWind(worldModel.getWindModel());
	}
	
	public void updateCompass() {
		this.model.setCompass(worldModel.getCompassModel());
	}

	public void updateGps() {
		this.model.setGps(worldModel.getGPSModel());
	}


	// Setter (values given by View to store in Model)
	public void setCircleMarkerList(ArrayList<MapMarker> markerList) {
		this.model.setCircleMarkerList(markerList);
		System.out.println("Set passed.");
	}
	
	public void setPolyMarkerList(ArrayList<MapMarker> markerList) {
		this.model.setPolyMarkerList(markerList);
		System.out.println("Set passed.");
	}

	// Getter ("tunneled" from Model)
	public CompassModel getCompass() {
		return this.model.getCompass();
	}

	public WindModel getWind() {
		return this.model.getWind();
	}

	public GPSModel getGps() {
		return this.model.getGps();
	}

	public int getGpsSatelites() {
		return this.model.getGpsSatelites();
	}

}
