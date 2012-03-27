package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.Task;
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
	public void commitMarkerList(Planner planner) {
		ArrayList<MapMarker> markerList = this.model.getMarkerList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		for (int i = 0; i < markerList.size(); i++) {
			tasks.add(new ReachCircleTask(new GPS(markerList.get(i).getLat(),
					markerList.get(i).getLon()), 3));
		}

		mission.setTasks(tasks);
		
		System.out.println("Mission passed.");
		System.out.println("First GPS marker: " + markerList.get(0).getLat() + " / " + markerList.get(0).getLon());
		
		//TODO weiterleiten der Mission an ein Planner-Objekt
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

		this.model.setGpsPosition(new GPS(dice.nextDouble(), dice.nextDouble()));

		this.model.setGpsPrecision((float) dice.nextInt(2));
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
	public void setMarkerList(ArrayList<MapMarker> markerList) {
		this.model.setMarkerList(markerList);
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

	public double getGpsPrecision() {
		return this.model.getGpsPrecision();
	}

}
