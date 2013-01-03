package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;

/**
 * This interfaced class manages the data pipe between GUI (logic), model and the boat (world model/ planner).
 * 
 * @author Patrick Rutter
 * 
 */
public interface GUIController {
	
	public void commitMission(Planner planner, Mission mission);
	
	// temporary test mission commit methods
//	public void commitCircleMarkerList(Planner planner);
//
//	public void commitPolyList(Planner planner);
//
//	public void commitReachCompassTask(int angle, Planner planner);
//
//	public void commitHoldAngleToWind(int angle, Planner planner);
//
//	public void commitStopTask(Planner planner);
	
	// remote control methods
	/**
	 * Commits a special manual command to either sail, rudder or propellor actor. Used primary by remoteDialog for remote control.
	 * 
	 * @param planner planner reference used for commiting
	 * @param propellor new value for propellor, may be null
	 * @param rudder new value for rudder, may be null
	 * @param sail new value for sail, may be null
	 */
	public void commitPrimitiveCommand(Planner planner, Integer propellor, Integer rudder, Integer sail);
	
//	/**
//	 * Resets the actors of the boat.
//	 * @param planner
//	 */
//	public void resetActorsTask(Planner planner);
	
	// update routines
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 */
	public void updateAll();

	public void updateWind();

	public void updateCompass();

	public void updateGps();

	public void updateMission();

	/**
	 * Currently a unused stub. Tries to generate a report on the status of the current mission.
	 */
	//public void generateMissionReport();

	// Setter (values given by View to store in Model)
	
//	@Deprecated
//	public void setCircleMarkerList(List<GPS> pointList);

//	@Deprecated
//	public void setPolyList(List<MapPolygon> polyList);
	
//	@Deprecated
//	public void setSailMode(boolean sailMode);

	// Getter ("tunneled" from Model)
	public CompassModel getCompass();

	public WindModel getWind();

	public GPSModel getGps();

	public int getGpsSatelites();

//	@Deprecated
//	public boolean isSailMode();

	public int getPropellor();
	
	public int getRudder();
	
	public int getSail();
	
	public void setPropellor();
	
	public void setRudder();
	
	public void setSail();
	
	public Mission getCurrentMission();
	
	public Mission getCurrentWholeMission();
	
	public boolean isMissionUpdated();
	
	public GUIModel getModel();

	public void startEmulation(String filePath);

	public void stopEmulation();

	public void pauseEmulation();

	public void playEmulation();
	
	public void regulateEmulationSpeed(int value);
	
	public int getActualUpdateRate();
	
	public void setActualUpdateRateBackToDefault();
}
