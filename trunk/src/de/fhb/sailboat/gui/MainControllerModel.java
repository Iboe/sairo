package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;

/**
 * This interfaced class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 */
public interface MainControllerModel {

	/**
	 * Returns a reference to the CompassModel object.
	 * @return CompassModel
	 */
	public CompassModel getCompass();
	
	/**
	 * Sets the current CompassModel.
	 * @param compass
	 */
	public void setCompass(CompassModel compass);

	/**
	 * Sets the current WindModel.
	 * @param wind
	 */
	public void setWind(WindModel wind);
	
	/**
	 * Returns a reference to the WindModel object.
	 * @return WindModel
	 */
	public WindModel getWind();

	/**
	 * Sets the current GPSModel.
	 * @param gps
	 */
	public void setGps(GPSModel gps);
	
	/**
	 * Returns a reference to the GPSModel object.
	 * @return GPSModel
	 */
	public GPSModel getGps();

	@Deprecated
	/**
	 * Old method returning a list of GPS points for usage with ReachCircleTasks.
	 * @return gpsList
	 */
	public List<GPS> getCircleMarkerList();

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with ReachCircleTasks.
	 * @param pointList
	 */
	public void setCircleMarkerList(List<GPS> markerList);

	@Deprecated
	/**
	 * Old method returning a lMapPolygon for usage with ReachPolygonTask.
	 * @return mapPolygon
	 */
	public List<MapPolygon> getPolyList();

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with a ReachPolygonTask.
	 * @param polyList
	 */
	public void setPolyList(List<MapPolygon> polyList);

	@Deprecated
	/**
	 * Old method which returned the current state of sailMode (see setSailMode).
	 * @return sailMode
	 */
	public boolean isSailMode();

	@Deprecated
	/**
     * Old method used for activating/ deactivation SailMode, resulting in
     * usage of propellor or not.
     * @param sailMode true if propellor should be deactivated
     */
	public void setSailMode(boolean sailMode);

	/**
	 * Returns the whole mission. This mission contains all tasks defined for it, regardless of completion.
	 * @return wholeMission
	 */
	public Mission getCurrentWholeMission();

	/**
	 * Returns currently active mission. This mission reference only contains tasks left for processing, not finished ones.
	 * @return mission
	 */
	public Mission getMissionTasksLeft();

	/**
	 * Sets the current whole mission.
	 * @param currentWholeMission
	 */
	public void setCurrentWholeMission(Mission currentWholeMission);

	/**
	 * Sets the tasks left of the current whole mission.
	 * @param missionTasksLeft
	 */
	public void setMissionTasksLeft(Mission missionTasksLeft);
	
	/**
	 * Sets propellor value.
	 */
	public void setPropellor(int propellor);
	
	/**
	 * Sets rudder value.
	 */
	public void setRudder(int rudder);
	
	/**
	 * Sets sail value.
	 */
	public void setSail(int sail);
	
	/**
	 * Returns current value of propellor (refer to configuration for further details on value ranges).
	 * @return propellor value
	 */
	public int getPropellor();
	
	/**
	 * Returns current value of rudder (refer to configuration for further details on value ranges).
	 * @return rudder value
	 */
	public int getRudder();
	
	/**
	 * Returns current value of sail (refer to configuration for further details on value ranges).
	 * @return sail value
	 */
	public int getSail();
	
	/**
	 * Returns state of missionUpdating. True equals a recent update (task finished).
	 * @return missionUpdateStatus
	 */
	public boolean isMissionUpdated();
}
