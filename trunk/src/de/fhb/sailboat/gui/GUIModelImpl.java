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
	
	protected Mission currentWholeMission;		// current mission of the sailboat as a whole from start to finish
	protected Mission missionTasksLeft;			// current state of the mission (tasks left)
	
	public GUIModelImpl() {
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
	@Deprecated
	public List<GPS> getCircleMarkerList() {
		return circleMarkerList;
	}

	@Override
	@Deprecated
	public void setCircleMarkerList(List<GPS> markerList) {
		this.circleMarkerList = markerList;
	}

	@Override
	@Deprecated
	public List<MapPolygon> getPolyList() {
		return polyList;
	}

	@Override
	@Deprecated
	public void setPolyList(List<MapPolygon> polyList) {
		this.polyList = polyList;
	}

	@Override
	@Deprecated
	public boolean isSailMode() {
		return sailMode;
	}

	@Override
	@Deprecated
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
	public void setCurrentWholeMission(Mission currentWholeMission) {
		this.currentWholeMission = currentWholeMission;
	}

	@Override
	public void setMissionTasksLeft(Mission missionTasksLeft) {
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
	public int getPropellor() {
		return this.propellor;
	}
	
	@Override
	public int getRudder() {
		return this.rudder;
	}
	
	@Override
	public int getSail() {
		return this.sail;
	}
	
	@Override
	public void setPropellor(int propellor) {
		this.propellor = propellor;
	}
	
	@Override
	public void setSail(int sail) {
		this.sail = sail;
	}
	
	@Override
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
