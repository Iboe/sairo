package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;

/**
 * This interfaced class manages the data pipe between GUI (logic), model and
 * the boat (world model/ planner).
 * 
 * @author Patrick Rutter
 * 
 */
public interface MainController {

	/**
	 * Commit a mission to a sailboat via its planner.
	 * 
	 * @param planner
	 * @param mission
	 */
	public void commitMission(Planner planner, MissionImpl mission);

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of ReachCircleTasks.
	 * @param planner
	 */
	public void commitCircleMarkerList(Planner planner);

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a ReachPolygonTask.
	 * @param planner
	 */
	public void commitPolyList(Planner planner);

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a ReachCompassTask.
	 * @param planner
	 */
	public void commitReachCompassTask(int angle, Planner planner);

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a HoldAngleToWindTask.
	 * @param planner
	 */
	public void commitHoldAngleToWind(int angle, Planner planner);

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a StopTask.
	 * @param planner
	 */
	public void commitStopTask(Planner planner);

	// remote control methods
	/**
	 * Commits a special manual command to either sail, rudder or propellor
	 * actor. Used primary by remoteDialog for remote control.
	 * 
	 * @param planner
	 *            planner reference used for commiting
	 * @param propellor
	 *            new value for propellor, may be null
	 * @param rudder
	 *            new value for rudder, may be null
	 * @param sail
	 *            new value for sail, may be null
	 */
	public void commitPrimitiveCommand(Planner planner, Integer propellor,
			Integer rudder, Integer sail);

	/**
	 * Resets the actors of the boat (propellor, sail, rudder).
	 * 
	 * @param planner
	 */
	public void resetActorsTask(Planner planner);

	// update routines
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 */
	public void updateAll();

	/**
	 * Updates wind data.
	 */
	public void updateWind();

	/**
	 * Updates compass data.
	 */
	public void updateCompass();

	/**
	 * Updates GPS data.
	 */
	public void updateGps();

	/**
	 * Updates data for current mission.
	 */
	public void updateMission();

	// Setter (values given by View to store in Model)

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with ReachCircleTasks.
	 * @param pointList
	 */
	public void setCircleMarkerList(List<GPS> pointList);

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with a ReachPolygonTask.
	 * @param polyList
	 */
	public void setPolyList(List<MapPolygon> polyList);

	/**
	 * activating/ deactivation SailMode, resulting in
	 * usage of propellor or not.
	 * @param sailMode true if propellor should be deactivated
	 */
	public void setSailMode(boolean sailMode);

	// Getter ("tunneled" from Model)
	/**
	 * Returns a reference to the CompassModel object.
	 * 
	 * @return CompassModel
	 */
	public CompassModel getCompass();

	/**
	 * Returns a reference to the WindModel object.
	 * 
	 * @return WindModel
	 */
	public WindModel getWind();

	/**
	 * Returns a reference to the GPSModel object.
	 * 
	 * @return GPSModel
	 */
	public GPSModel getGps();

	/**
	 * returned the current state of sailMode (see setSailMode).
	 * 
	 * @return sailMode
	 */
	public boolean isSailMode();

	/**
	 * Returns current value of propellor (refer to configuration for further
	 * details on value ranges).
	 * 
	 * @return propellor value
	 */
	public int getPropellor();

	/**
	 * Returns current value of rudder (refer to configuration for further
	 * details on value ranges).
	 * 
	 * @return rudder value
	 */
	public int getRudder();

	/**
	 * Returns current value of sail (refer to configuration for further details
	 * on value ranges).
	 * 
	 * @return sail value
	 */
	public int getSail();

	/**
	 * Sets propellor value.
	 */
	public void setPropellor();

	/**
	 * Sets propellor value.
	 */
	public void setRudder();

	/**
	 * Sets propellor value.
	 */
	public void setSail();

	/**
	 * Returns currently active mission. This mission reference only contains
	 * tasks left for processing, not finished ones.
	 * 
	 * @return mission
	 */
	public MissionImpl getCurrentMission();

	/**
	 * Returns the whole mission. This mission contains all tasks defined for
	 * it, regardless of completion.
	 * 
	 * @return wholeMission
	 */
	public MissionImpl getCurrentWholeMission();

	/**
	 * Returns state of missionUpdating. True equals a recent update (task
	 * finished).
	 * 
	 * @return missionUpdateStatus
	 */
	public boolean isMissionUpdated();

	/**
	 * Returns a reference to the MainControllerModel object.
	 * 
	 * @return ManControllerModel
	 */
	public MainControllerModel getModel();

	/**
	 * starts the Player with a specific log-file
	 * 
	 * @param filePath
	 */
	public void startPlayer(String filePath);

	/**
	 * stops playing the player-module
	 */
	public void stopPlaying();

	/**
	 * pause player-module
	 */
	public void pausePlaying();

	/**
	 * play-function of the Player-Module with loaded file
	 */
	public void playPlayer();

	/**
	 * Sets the playing speed in a range from...to ... TODO
	 * 
	 * @param value
	 */
	public void setPlayingSpeed(int value);

	/**
	 * Gets the actual updaterate of GUI
	 * @return miliSecs
	 */
	public int getActualUpdateRate();

	/**
	 * sets the Update-Rate of GUI back to default
	 * 
	 * @param filePath
	 */
	public void setActualUpdateRateBackToDefault();
}
