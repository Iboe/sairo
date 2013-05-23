package de.fhb.sailboat.gui;

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
 * This class serves as storage for all data relevant to be served to and
 * displayed by View.
 * 
 * @author Patrick Rutter
 * @author Modifications by Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class GUIModel {

	protected boolean missionUpdated;

	protected WindModel wind;

	protected CompassModel compass;

	protected GPSModel gps;

	// for the planned exercise
	@Deprecated
	protected List<GPS> circleMarkerList;
	@Deprecated
	protected List<MapPolygon> polyList;

	@Deprecated
	/**
	 * stores current test mission mode
	 */
	protected boolean sailMode;

	/**
	 * current propellor mode, 1=maximum backward/ 2=off/ 3=maximum forward
	 */
	protected int propellor;

	/**
	 * current rudder value
	 */
	protected int rudder;

	/**
	 * current sail value
	 */
	protected int sail;

	/**
	 * current mission of the sailboat as a whole from start to finish
	 */
	protected MissionVO currentWholeMission;

	/**
	 * current state of the mission (tasks left)
	 */
	protected MissionVO missionTasksLeft;

	public GUIModel() {
		this.missionUpdated = false;
		this.wind = new WindModelImpl();
		this.compass = new CompassModelImpl();
		this.compass.setCompass(new Compass(170, 0, 0, System
				.currentTimeMillis()));
		this.gps = new GPSModelImpl();
		this.sailMode = false;
		this.currentWholeMission = new MissionVO();
		this.missionTasksLeft = new MissionVO();
		this.propellor = 2;
		this.rudder = 0;
		this.sail = 0;
	}

	/**
	 * Returns a reference to the CompassModel object.
	 * 
	 * @return CompassModel
	 */
	public CompassModel getCompass() {
		return this.compass;
	}

	/**
	 * Sets the current CompassModel.
	 * 
	 * @param compass
	 */
	public void setCompass(CompassModel compass) {
		this.compass = compass;
	}

	/**
	 * Sets the current WindModel.
	 * 
	 * @param wind
	 */
	public void setWind(WindModel wind) {
		this.wind = wind;
	}

	/**
	 * Returns a reference to the WindModel object.
	 * 
	 * @return WindModel
	 */
	public WindModel getWind() {
		return this.wind;
	}

	/**
	 * Sets the current GPSModel.
	 * 
	 * @param gps
	 */
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}

	/**
	 * Returns a reference to the GPSModel object.
	 * 
	 * @return GPSModel
	 */
	public GPSModel getGps() {
		return gps;
	}

	@Deprecated
	/**
	 * Old method returning a list of GPS points for usage with ReachCircleTasks.
	 * @return gpsList
	 */
	public List<GPS> getCircleMarkerList() {
		return circleMarkerList;
	}

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with ReachCircleTasks.
	 * @param pointList
	 */
	public void setCircleMarkerList(List<GPS> markerList) {
		this.circleMarkerList = markerList;
	}

	@Deprecated
	/**
	 * Old method returning a lMapPolygon for usage with ReachPolygonTask.
	 * @return mapPolygon
	 */
	public List<MapPolygon> getPolyList() {
		return polyList;
	}

	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with a ReachPolygonTask.
	 * @param polyList
	 */
	public void setPolyList(List<MapPolygon> polyList) {
		this.polyList = polyList;
	}

	@Deprecated
	/**
	 * Old method which returned the current state of sailMode (see setSailMode).
	 * @return sailMode
	 */
	public boolean isSailMode() {
		return sailMode;
	}

	@Deprecated
	/**
	 * Old method used for activating/ deactivation SailMode, resulting in
	 * usage of propellor or not.
	 * @param sailMode true if propellor should be deactivated
	 */
	public void setSailMode(boolean sailMode) {
		this.sailMode = sailMode;
	}

	/**
	 * Returns the whole mission. This mission contains all tasks defined for
	 * it, regardless of completion.
	 * 
	 * @return wholeMission
	 */
	public MissionVO getCurrentWholeMission() {
		return currentWholeMission;
	}

	/**
	 * Returns currently active mission. This mission reference only contains
	 * tasks left for processing, not finished ones.
	 * 
	 * @return mission
	 */
	public MissionVO getMissionTasksLeft() {
		return missionTasksLeft;
	}

	/**
	 * Sets the current whole mission.
	 * 
	 * @param currentWholeMission
	 */
	public void setCurrentWholeMission(MissionVO currentWholeMission) {
		this.currentWholeMission = currentWholeMission;
	}

	/**
	 * Sets the tasks left of the current whole mission.
	 * 
	 * @param missionTasksLeft
	 */
	public void setMissionTasksLeft(MissionVO missionTasksLeft) {
		if ((missionTasksLeft.getTasks() != null)
				&& (this.missionTasksLeft.getTasks() != null)) {
			if (!missionTasksLeft.getTasks().equals(
					this.missionTasksLeft.getTasks())) {
				this.missionTasksLeft = missionTasksLeft;
				this.missionUpdated = true;
			}
		} else {
			this.missionTasksLeft = missionTasksLeft;
		}
	}

	/**
	 * Returns current value of propellor (refer to configuration for further
	 * details on value ranges).
	 * 
	 * @return propellor value
	 */
	public int getPropellor() {
		return this.propellor;
	}

	/**
	 * Returns current value of rudder (refer to configuration for further
	 * details on value ranges).
	 * 
	 * @return rudder value
	 */
	public int getRudder() {
		return this.rudder;
	}

	/**
	 * Returns current value of sail (refer to configuration for further details
	 * on value ranges).
	 * 
	 * @return sail value
	 */
	public int getSail() {
		return this.sail;
	}

	/**
	 * Sets propellor value.
	 */
	public void setPropellor(int propellor) {
		this.propellor = propellor;
	}

	/**
	 * Sets sail value.
	 */
	public void setSail(int sail) {
		this.sail = sail;
	}

	/**
	 * Sets rudder value.
	 */
	public void setRudder(int rudder) {
		this.rudder = rudder;
	}

	/**
	 * Returns true if the mission has been updated (=tasks been altered).
	 * Calling this method will set the update status back to false (!).
	 */
	public boolean isMissionUpdated() {
		boolean myReturn = this.missionUpdated;
		this.missionUpdated = false;
		return myReturn;
	}
}
