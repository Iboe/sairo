package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;

/**
 * This class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 *
 */
public interface GUIModel {

	public CompassModel getCompass();
	
	public void setCompass(CompassModel compass);

	public void setWind(WindModel wind);
	
	public WindModel getWind();

	public void setGps(GPSModel gps);
	
	public GPSModel getGps();

	public List<GPS> getCircleMarkerList();

	public void setCircleMarkerList(List<GPS> markerList);

	public List<MapPolygon> getPolyList();

	public void setPolyList(List<MapPolygon> polyList);

	public boolean isSailMode();

	public void setSailMode(boolean sailMode);

	public Mission getCurrentWholeMission();

	public Mission getMissionTasksLeft();

	public StringBuffer getMissionReport() ;

	public void setCurrentWholeMission(Mission currentWholeMission);

	public void setMissionTasksLeft(Mission missionTasksLeft);

	public void setMissionReport(StringBuffer missionReport);
	
	public void setPropellor(int propellor);
	
	public void setRudder(int rudder);
	
	public void setSail(int sail);
	
	public int getPropellor();
	
	public int getRudder();
	
	public int getSail();
}
