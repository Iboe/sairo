package de.fhb.sailboat.ufer.prototyp;

import java.util.List;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.MissionVO;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.CompassModelImpl;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.GPSModelImpl;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WindModelImpl;



/**
 * This class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 *
 */
public class Model {
	
	// CONSTANTS
	
	// VARIABLES
	private WindModel wind;
	
	private CompassModel compass;
	
	private GPSModel gps;
	
	// for the planned exercise
	private List<GPS> circleMarkerList;
	private List<MapPolygon> polyList;
	
	private boolean sailMode;
	
	private MissionVO currentWholeMission;			// current mission of the sailboat as a whole from start to finish
	private MissionVO missionTasksLeft;				// current state of the mission (tasks left)
	
	private StringBuffer missionReport;
	
	public Model() {
		this.wind = new WindModelImpl();
		this.compass = new CompassModelImpl();
		this.compass.setCompass(new Compass(170,0,0,System.currentTimeMillis()));
		this.gps = new GPSModelImpl();
		this.sailMode = false;
		this.currentWholeMission = new MissionVO();
		this.missionTasksLeft = new MissionVO();
		this.missionReport = new StringBuffer();
	}

	// Getter/ Setter
	// Compass
	public CompassModel getCompass() {
		return this.compass;
	}
	
	public void setCompass(CompassModel compass) {
		this.compass = compass;
	}

	// Wind
	public void setWind(WindModel wind) {
		this.wind = wind;
	}
	
	public WindModel getWind() {
		return this.wind;
	}

	// GPS
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}
	
	public GPSModel getGps() {
		return gps;
	}
	
	/**
	 * Helper method which should *ONLY* be used for testing/ debugging. Sets the GPS position manually and
	 * deletes local GPS History.
	 * @param position
	 */
	public void setGpsPosition(GPS position) {
		this.gps = new GPSModelImpl();
		gps.setPosition(position);
	}

	public List<GPS> getCircleMarkerList() {
		return circleMarkerList;
	}

	public void setCircleMarkerList(List<GPS> markerList) {
		this.circleMarkerList = markerList;
	}

	public List<MapPolygon> getPolyList() {
		return polyList;
	}

	public void setPolyList(List<MapPolygon> polyList) {
		this.polyList = polyList;
	}

	public boolean isSailMode() {
		return sailMode;
	}

	public void setSailMode(boolean sailMode) {
		this.sailMode = sailMode;
	}

	public MissionVO getCurrentWholeMission() {
		return currentWholeMission;
	}

	public MissionVO getMissionTasksLeft() {
		return missionTasksLeft;
	}

	public StringBuffer getMissionReport() {
		return missionReport;
	}

	public void setCurrentWholeMission(MissionVO currentWholeMission) {
		this.currentWholeMission = currentWholeMission;
	}

	public void setMissionTasksLeft(MissionVO missionTasksLeft) {
		this.missionTasksLeft = missionTasksLeft;
	}

	public void setMissionReport(StringBuffer missionReport) {
		this.missionReport = missionReport;
	}

	
}
