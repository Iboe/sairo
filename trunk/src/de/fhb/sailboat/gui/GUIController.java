package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;

public interface GUIController {

	GUIModelImpl model = null;
	
	public void commitCircleMarkerList(Planner planner);

	public void commitPolyList(Planner planner);

	public void commitReachCompassTask(int angle, Planner planner);

	public void commitHoldAngleToWind(int angle, Planner planner);

	public void commitStopTask(Planner planner);
	
	public void resetActorsTask(Planner planner);

	public void updateAll();

	public void updateWind();

	public void updateCompass();

	public void updateGps();

	public void updateMission();

	public void generateMissionReport();

	// Setter (values given by View to store in Model)
	public void setCircleMarkerList(List<GPS> pointList);

	public void setPolyList(List<MapPolygon> polyList);

	// Getter ("tunneled" from Model)
	public CompassModel getCompass();

	public WindModel getWind();

	public GPSModel getGps();

	public int getGpsSatelites();

	public boolean isSailMode();

	public void setSailMode(boolean sailMode);

}
