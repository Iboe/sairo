package de.fhb.sailboat.gui;

import java.util.List;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
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
public class MainControllerModelImpl implements MainControllerModel{
	
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
	protected boolean sailMode;					// stores current test mission mode
	protected int propellor;						// current propellor mode, 1=maximum backward/ 2=off/ 3=maximum forward
	protected int rudder;							// current rudder value
	protected int sail;							// current sail value
	
	protected MissionImpl currentWholeMission;		// current mission of the sailboat as a whole from start to finish
	protected MissionImpl missionTasksLeft;			// current state of the mission (tasks left)
	
	public MainControllerModelImpl() {
		this.missionUpdated = false;
		this.wind = new WindModelImpl();
		this.compass = new CompassModelImpl();
		this.compass.setCompass(new Compass(170,0,0));
		this.gps = new GPSModelImpl();
		this.sailMode = false;
		this.currentWholeMission = new MissionImpl();
		this.missionTasksLeft = new MissionImpl();
		this.propellor = 2;
		this.rudder = 0;
		this.sail = 0;
	}

	@Override
	/**
	 * Returns a reference to the CompassModel object.
	 * @return CompassModel
	 */
	public CompassModel getCompass() {
		return this.compass;
	}
	
	@Override
	/**
	 * Sets the current CompassModel.
	 * @param compass
	 */
	public void setCompass(CompassModel compass) {
		this.compass = compass;
	}

	// Wind
	@Override
	/**
	 * Sets the current WindModel.
	 * @param wind
	 */
	public void setWind(WindModel wind) {
		this.wind = wind;
	}
	
	@Override
	/**
	 * Returns a reference to the WindModel object.
	 * @return WindModel
	 */
	public WindModel getWind() {
		return this.wind;
	}

	// GPS
	@Override
	/**
	 * Sets the current GPSModel.
	 * @param gps
	 */
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}
	
	@Override
	/**
	 * Returns a reference to the GPSModel object.
	 * @return GPSModel
	 */
	public GPSModel getGps() {
		return gps;
	}

	@Override
	@Deprecated
	/**
	 * Old method returning a list of GPS points for usage with ReachCircleTasks.
	 * @return gpsList
	 */
	public List<GPS> getCircleMarkerList() {
		return circleMarkerList;
	}

	@Override
	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with ReachCircleTasks.
	 * @param pointList
	 */
	public void setCircleMarkerList(List<GPS> markerList) {
		this.circleMarkerList = markerList;
	}

	@Override
	@Deprecated
	/**
	 * Old method returning a lMapPolygon for usage with ReachPolygonTask.
	 * @return mapPolygon
	 */
	public List<MapPolygon> getPolyList() {
		return polyList;
	}

	@Override
	@Deprecated
	/**
	 * Old method for creating a list of GPS points for usage with a ReachPolygonTask.
	 * @param polyList
	 */
	public void setPolyList(List<MapPolygon> polyList) {
		this.polyList = polyList;
	}

	@Override
	@Deprecated
	/**
	 * Old method which returned the current state of sailMode (see setSailMode).
	 * @return sailMode
	 */
	public boolean isSailMode() {
		return sailMode;
	}

	@Override
	@Deprecated
	/**
     * Old method used for activating/ deactivation SailMode, resulting in
     * usage of propellor or not.
     * @param sailMode true if propellor should be deactivated
     */
	public void setSailMode(boolean sailMode) {
		this.sailMode = sailMode;
	}

	@Override
	/**
	 * Returns the whole mission. This mission contains all tasks defined for it, regardless of completion.
	 * @return wholeMission
	 */
	public MissionImpl getCurrentWholeMission() {
		return currentWholeMission;
	}

	@Override
	/**
	 * Returns currently active mission. This mission reference only contains tasks left for processing, not finished ones.
	 * @return mission
	 */
	public MissionImpl getMissionTasksLeft() {
		return missionTasksLeft;
	}

	@Override
	/**
	 * Sets the current whole mission.
	 * @param currentWholeMission
	 */
	public void setCurrentWholeMission(MissionImpl currentWholeMission) {
		this.currentWholeMission = currentWholeMission;
	}

	@Override
	/**
	 * Sets the tasks left of the current whole mission.
	 * @param missionTasksLeft
	 */
	public void setMissionTasksLeft(MissionImpl missionTasksLeft) {
		if ((missionTasksLeft.getTasks() != null) && (this.missionTasksLeft.getTasks() != null)) {
			if (!missionTasksLeft.getTasks().equals(
					this.missionTasksLeft.getTasks())) {
				this.missionTasksLeft = missionTasksLeft;
				this.missionUpdated = true;
			}
		} else {
			this.missionTasksLeft = missionTasksLeft;
		}
	}

	@Override
	/**
	 * Returns current value of propellor (refer to configuration for further details on value ranges).
	 * @return propellor value
	 */
	public int getPropellor() {
		return this.propellor;
	}
	
	@Override
	/**
	 * Returns current value of rudder (refer to configuration for further details on value ranges).
	 * @return rudder value
	 */
	public int getRudder() {
		return this.rudder;
	}
	
	@Override
	/**
	 * Returns current value of sail (refer to configuration for further details on value ranges).
	 * @return sail value
	 */
	public int getSail() {
		return this.sail;
	}
	
	@Override
	/**
	 * Sets propellor value.
	 */
	public void setPropellor(int propellor) {
		this.propellor = propellor;
	}
	
	@Override
	/**
	 * Sets sail value.
	 */
	public void setSail(int sail) {
		this.sail = sail;
	}
	
	@Override
	/**
	 * Sets rudder value.
	 */
	public void setRudder(int rudder) {
		this.rudder = rudder;
	}
	
	@Override
	/**
	 * Returns true if the mission has been updated (=tasks been altered). Calling this method will set the update status back to false (!).
	 */
	public boolean isMissionUpdated() {
		boolean myReturn = this.missionUpdated;
		this.missionUpdated = false;
		return myReturn;
	}
}
