package de.fhb.sailboat.gui;

import java.util.List;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
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
public class GUIModelImpl implements GUIModel{
	
	// CONSTANTS
	
	// VARIABLES
	private WindModel wind;
	
	private CompassModel compass;
	
	private GPSModel gps;
	
	// for the planned exercise
	private List<GPS> circleMarkerList;
	private List<MapPolygon> polyList;
	
	private boolean sailMode;
	
	private Mission currentWholeMission;			// current mission of the sailboat as a whole from start to finish
	private Mission missionTasksLeft;				// current state of the mission (tasks left)
	
	private StringBuffer missionReport;
	
	public GUIModelImpl() {
		this.wind = new WindModelImpl();
		this.compass = new CompassModelImpl();
		this.compass.setCompass(new Compass(170,0,0));
		this.gps = new GPSModelImpl();
		this.sailMode = false;
		this.currentWholeMission = new MissionImpl();
		this.missionTasksLeft = new MissionImpl();
		this.missionReport = new StringBuffer();
	}

	// Getter/ Setter
	// Compass
	@Override
	public CompassModel getCompass() {
		return this.compass;
	}
	
	@Override
	public void setCompass(CompassModel compass) {
		this.compass = compass;
	}

	// Wind
	@Override
	public void setWind(WindModel wind) {
		this.wind = wind;
	}
	
	@Override
	public WindModel getWind() {
		return this.wind;
	}

	// GPS
	@Override
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}
	
	@Override
	public GPSModel getGps() {
		return gps;
	}

	@Override
	public List<GPS> getCircleMarkerList() {
		return circleMarkerList;
	}

	@Override
	public void setCircleMarkerList(List<GPS> markerList) {
		this.circleMarkerList = markerList;
	}

	@Override
	public List<MapPolygon> getPolyList() {
		return polyList;
	}

	@Override
	public void setPolyList(List<MapPolygon> polyList) {
		this.polyList = polyList;
	}

	@Override
	public boolean isSailMode() {
		return sailMode;
	}

	@Override
	public void setSailMode(boolean sailMode) {
		this.sailMode = sailMode;
	}

	@Override
	public Mission getCurrentWholeMission() {
		return currentWholeMission;
	}

	@Override
	public Mission getMissionTasksLeft() {
		return missionTasksLeft;
	}

	@Override
	public StringBuffer getMissionReport() {
		return missionReport;
	}

	@Override
	public void setCurrentWholeMission(Mission currentWholeMission) {
		this.currentWholeMission = currentWholeMission;
	}

	@Override
	public void setMissionTasksLeft(Mission missionTasksLeft) {
		this.missionTasksLeft = missionTasksLeft;
	}

	@Override
	public void setMissionReport(StringBuffer missionReport) {
		this.missionReport = missionReport;
	}

	
}
