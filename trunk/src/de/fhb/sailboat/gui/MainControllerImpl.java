package de.fhb.sailboat.gui;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.MissionVO;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.missionplayer.Player;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * This class manages the data pipe between GUI (logic), model and the boat (world model/ planner).
 * 
 * @author Patrick Rutter
 * @author Modifications by Andy Klay <klay@fh-brandenburg.de>
 */
public class MainControllerImpl {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainControllerImpl.class);

    public final static int GUI_UPDATE_RATE_DEFAULT = Integer.parseInt(System.getProperty("GUI_UPDATE_RATE_DEFAULT"));	// sleep in ms after each gui loop
    private int actualUpdateRate=GUI_UPDATE_RATE_DEFAULT;
	private static int GUI_UPDATE_RATE_MAX = Integer.parseInt(System.getProperty("GUI_UPDATE_RATE_MAX"));
	private static int GUI_UPDATE_RATE_MIN = Integer.parseInt(System.getProperty("GUI_UPDATE_RATE_MIN"));
	
	// Constants
	public final static int PROPELLOR_MAX = Integer.parseInt(System.getProperty("AKSENLocomotion.PROPELLOR_MAX"));			// full forward
	public final static int PROPELLOR_STOP = Integer.parseInt(System.getProperty("AKSENLocomotion.PROPELLOR_STOP"));	// propellor off
	public final static int PROPELLOR_REVERSE = Integer.parseInt(System.getProperty("AKSENLocomotion.PROPELLOR_REVERSE"));			// full backward
	public final static int RUDDER_LEFT = Integer.parseInt(System.getProperty("AKSENLocomotion.RUDDER_LEFT"));
	public final static int RUDDER_NORMAL = Integer.parseInt(System.getProperty("AKSENLocomotion.RUDDER_NORMAL"));
	public final static int RUDDER_RIGHT = Integer.parseInt(System.getProperty("AKSENLocomotion.RUDDER_RIGHT"));
	public final static int SAIL_IN = Integer.parseInt(System.getProperty("AKSENLocomotion.SAIL_SHEET_IN"));
	public final static int SAIL_NORMAL = Integer.parseInt(System.getProperty("AKSENLocomotion.SAIL_SHEET_NORMAL"));
	public final static int SAIL_OUT = Integer.parseInt(System.getProperty("AKSENLocomotion.SAIL_SHEET_OUT"));
	
	
	/**
	 * GUIModel is used to store values locally
	 */
	private MainControllerModel model;

	/**
	 * An instance of the world model is used to get values from the boat
	 */
	private WorldModel worldModel;
	
	private Player player;
	
	/**
	 * Sets the actual Update-Rate for GUI
	 * @param miliSecs
	 */
	public void setUpdateIntervall(int miliSecs) {
		if (miliSecs <= GUI_UPDATE_RATE_MAX && miliSecs >= GUI_UPDATE_RATE_MIN) {
			this.actualUpdateRate = miliSecs;
		}
	}

	/**
	 * Gets the actual updaterate of GUI
	 * @return miliSecs
	 */
	public int getActualUpdateRate() {
		return actualUpdateRate;
	}

	/**
	 * Contruct and intiialize the MainControllerImpl object.
	 */
	public MainControllerImpl() {
		this.model = new MainControllerModelImpl();
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	 
	/**
	 * Commit a mission to a sailboat via its planner.
	 * @param planner
	 * @param mission
	 */
	public void commitMission(Planner planner, MissionVO mission) {
		LOG.warn("Missionssize: "+ mission.getTasks().size());
		planner.doMission(mission);
	}

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of ReachCircleTasks.
	 * @param planner
	 */
	public void commitCircleMarkerList(Planner planner) {
		List<GPS> markerList = this.model.getCircleMarkerList();

		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		
		for (int i = 0; i < markerList.size(); i++) {
			tasks.add(new ReachCircleTask(new GPS(markerList.get(i)
					.getLatitude(), markerList.get(i).getLongitude()), 3));
		}
		
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_STOP));

		mission.setTasks(tasks);
		System.out.println(mission.getTasks().toString());
		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a ReachPolygonTask.
	 * @param planner
	 */
	public void commitPolyList(Planner planner) {
		List<MapPolygon> polyList = this.model.getPolyList();

		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		
		for (int i = 0; i < polyList.size(); i++) {
			tasks.add(new ReachPolygonTask(polyList.get(i).getPoints()));
		}
		
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_STOP));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a ReachCompassTask.
	 * @param planner
	 */
	public void commitReachCompassTask(int angle, Planner planner) {
		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		
		tasks.add(new CompassCourseTask(angle));
		
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_STOP));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements
		planner.doMission(mission);
	}

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a HoldAngleToWindTask.
	 * @param planner
	 */
	public void commitHoldAngleToWind(int angle, Planner planner) {
		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		tasks.add(new HoldAngleToWindTask(angle));
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_STOP));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Deprecated
	/**
	 * Old method for commiting a mission solely composed of a StopTask.
	 * @param planner
	 */
	public void commitStopTask(Planner planner) {
		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new StopTask(true));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}
	
	@Deprecated
	/**
	 * Resets the actors of the boat (propellor, sail, rudder).
	 * @param planner
	 */
	public void resetActorsTask(Planner planner) {
		MissionVO mission = new MissionVO();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(SAIL_NORMAL, RUDDER_NORMAL, PROPELLOR_STOP));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}
	
	/**
	 * Commits a special manual command to either sail, rudder or propellor actor. Used primary by remoteDialog for remote control.
	 * 
	 * @param planner planner reference used for commiting
	 * @param propellor new value for propellor, may be null
	 * @param rudder new value for rudder, may be null
	 * @param sail new value for sail, may be null
	 */
	public void commitPrimitiveCommand(Planner planner, Integer propellor, Integer rudder, Integer sail) {
		if (planner != null) planner.doPrimitiveCommand(new PrimitiveCommandTask(sail, rudder, propellor));
	}

	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 * (used to update a sensor reading and store it in model, kind of
	 * like a more sophisticated setter)
	 */
	public void updateAll() {
		updateWind();
		updateCompass();
		updateGps();
		updateMission();//XXX Achtung wieder einkommentiert, zeigt er nun etwas an im infofenster??
		setPropellor();
		setSail();
		setRudder();
	}

	/**
	 * Update wind data.
	 */
	public void updateWind() {
		this.model.setWind(worldModel.getWindModel());
	}

	/**
	 * Update compass data.
	 */
	public void updateCompass() {
		this.model.setCompass(worldModel.getCompassModel());
	}

	/**
	 * Update GPS data.
	 */
	public void updateGps() {
		this.model.setGps(worldModel.getGPSModel());
	}

	/**
	 * Update current mission data.
	 * 
	 */
	public void updateMission() {
		if (worldModel.getMission() != this.model.getMissionTasksLeft()) {
			this.model.setMissionTasksLeft(worldModel.getMission());
			//TODO???
//			generateMissionReport();
		}
	}

	/**
	 * Old method for creating a list of GPS points for usage with ReachCircleTasks.
	 * @param pointList
	 */
	public void setCircleMarkerList(List<GPS> pointList) {
		this.model.setCircleMarkerList(pointList);
		System.out.println("Set passed.");
	}

	/**
	 * Old method for creating a list of GPS points for usage with a ReachPolygonTask.
	 * @param polyList
	 */
	public void setPolyList(List<MapPolygon> polyList) {
		this.model.setPolyList(polyList);
		System.out.println("Set passed.");
	}

	/**
	 * Returns a reference to the CompassModel object.
	 * @return CompassModel
	 */
	public CompassModel getCompass() {
		return this.model.getCompass();
	}

	/**
	 * Returns a reference to the WindModel object.
	 * @return WindModel
	 */
	public WindModel getWind() {
		return this.model.getWind();
	}

	/**
	 * Returns a reference to the GPSModel object.
	 * @return GPSModel
	 */
	public GPSModel getGps() {
		return this.model.getGps();
	}

	/**
     * activating/ deactivation SailMode, resulting in
     * usage of propellor or not.
     * @param sailMode true if propellor should be deactivated
     */
	public boolean isSailMode() {
		return this.model.isSailMode();
	}

	/**
	 * Returns current value of propellor (refer to configuration for further details on value ranges).
	 * @return propellor value
	 */
	public int getPropellor() {
		return this.model.getPropellor();
	}
	
	/**
	 * Returns current value of rudder (refer to configuration for further details on value ranges).
	 * @return rudder value
	 */
	public int getRudder() {
		return this.model.getRudder();
	}

	/**
	 * Returns current value of sail (refer to configuration for further details on value ranges).
	 * @return sail value
	 */
	public int getSail() {
		return this.model.getSail();
	}
	
	/**
	 * Sets propellor value.
	 */
	public void setPropellor() {
		int value = this.worldModel.getActuatorModel().getPropeller().getValue();
		if ((value <= PROPELLOR_MAX) && (value <= PROPELLOR_STOP)) {
			this.model.setPropellor(3); 	// set propellor to maximum forward
		}
		else if (value == PROPELLOR_STOP) {
			this.model.setPropellor(2); 	// set propellor to off
		}
		else this.model.setPropellor(1); 	// set propellor to maximum backward
	}
	
	/**
	 * Sets rudder value.
	 */
	public void setRudder() {
		this.model.setPropellor(this.worldModel.getActuatorModel().getRudder().getValue());
	}
	
	/**
	 * Sets sail value.
	 */
	public void setSail() {
		this.model.setSail(this.worldModel.getActuatorModel().getSail().getValue());
	}

	/**
    * activating/ deactivation SailMode, resulting in
    * usage of propellor or not.
    * @param sailMode true if propellor should be deactivated
    */
	public void setSailMode(boolean sailMode) {
		this.model.setSailMode(sailMode);
	}
	
	/**
	 * Returns currently active mission. This mission reference only contains tasks left for processing, not finished ones.
	 * @return mission
	 */
	public MissionVO getCurrentMission() {
		return this.model.getMissionTasksLeft();
	}
	
	/**
	 * Returns the whole mission. This mission contains all tasks defined for it, regardless of completion.
	 * @return wholeMission
	 */
	public MissionVO getCurrentWholeMission() {
		return this.model.getCurrentWholeMission();
	}
	
	/**
	 * Returns state of missionUpdating. True equals a recent update (task finished).
	 * @return missionUpdateStatus
	 */
	public boolean isMissionUpdated() {
		return this.model.isMissionUpdated();
	}
	
	/**
	 * Returns a reference to the MainControllerModel object.
	 * @return ManControllerModel
	 */
	public MainControllerModel getModel() {
		return this.model;
	}

	 
	/**
	 * starts the Player with a specific log-file
	 * 
	 * @param filePath
	 */
	public void startPlayer(String filePath) {
		player=new Player(this.worldModel, filePath);
		player.start();
	}

	 
	/**
	 * stops playing the player-module
	 */
	public void stopPlaying() {
		player.stop();
	}

	 
	/**
	 * pause player-module
	 */
	public void pausePlaying() {
		player.pause();
	}

	 
	/**
	 * plays the loaded log-file with the player-module
	 */
	public void playPlayer() {
		player.play();
	}

	 
	/**
	 * Sets the playing speed in a range
	 * from Player.SPEED_INTERVAL_MAX to Player.SPEED_INTERVAL_MIN
	 * 
	 * @param value
	 */
	public void setPlayingSpeed(int value) {
		player.setSpeedIntervall(value);
		this.setUpdateIntervall(value);
	}

	 
	/**
	 * sets the GUI-update-rate back to default
	 */
	public void setActualUpdateRateBackToDefault() {
	    actualUpdateRate=GUI_UPDATE_RATE_DEFAULT;
	}
}
