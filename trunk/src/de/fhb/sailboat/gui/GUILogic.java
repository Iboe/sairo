package de.fhb.sailboat.gui;

import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JOptionPane;

import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.gui.map.Map;
import de.fhb.sailboat.mission.MissionVO;

/**
 * This class represents the program logic used by a GUInterface. It handles
 * updating, sending and converting of value displayed and set via the GUI.
 * 
 * @author Patrick Rutter
 * @author Modifications by Andy Klay <klay@fh-brandenburg.de>
 */
public class GUILogic {

	// public static final int LIVE_MODUS = 0;
	//
	// public static final int EMULATOR_MODUS = 1;
	//
	// public static final int PLANNING_MODUS = 2;
	//
	// public static final int SIMULATION_MODUS = 3;

	/**
	 * format string for GPS longitude/ latitude display style
	 */
	final static public String GPS_DECIMAL_FORMAT = "00.000000";

	/**
	 * will be appended to longitude/ latitude display strings
	 */
	final static public String GPS_UNIT = "°";

	/**
	 * will be appended to azimuth, pitch & roll display strings
	 */
	final static public String COMPASS_UNIT = "°";

	/**
	 * will be appended to windVelocity display strings
	 */
	final static public String WIND_VELOCITY_UNIT = " m/s";

	/**
	 * will be appended to windDirection display strings
	 */
	final static public String WIND_DIRECTION_UNIT = "°";

	/**
	 * the GUIController is used for getting and setting values from and to the
	 * world model/ local database
	 */
	private MainController controller;

	/**
	 * the Planner is used for sending mission sets
	 */
	private Planner planner;

	/**
	 * the Map is used to visualize the missionArea and landmarks/ points of
	 * interest
	 */
	private Map missionMap;

	/**
	 * if true random values will be generated for each update, instead of
	 * trying to retrieve true values (only hard code setting)
	 */
	private boolean testMode;

	/**
	 * used to format display strings
	 */
	private DecimalFormat gpsDecimalFormat;

	/**
	 * Constructs and initializes the GUILogic with the assigned planner
	 * reference.
	 * 
	 * @param planner
	 */
	public GUILogic(Planner planner) {
		this.testMode = false;
		this.gpsDecimalFormat = new DecimalFormat(GPS_DECIMAL_FORMAT);
		this.controller = new MainController();
		this.planner = planner;
	}

	/**
	 * Commit a mission to planner.
	 * 
	 * @param mission
	 */
	public void commitMission(MissionVO mission) {
		this.controller.commitMission(planner, mission);
	}

	/**
	 * Initializes a Map Object and assigns it to the given panel. The map will
	 * fit itself to the size of the given panel and can be resized.
	 * 
	 * @param missionMapPanel
	 */
	public void initializeMissionMap(javax.swing.JPanel missionMapPanel) {
		missionMap = new Map();
		missionMap.mapPanel(missionMapPanel);
	}

	/**
	 * Gets the current longitude of the boat and sets the given label to the
	 * resulting value.
	 * 
	 * @param gpsLongitudeLabel
	 */
	public void updateGPSLongitude(javax.swing.JLabel gpsLongitudeLabel) {
		String value = "";

		if (!testMode) {
			value = value
					+ gpsDecimalFormat.format(controller.getGps().getPosition()
							.getLongitude()) + GPS_UNIT;
		} else {
			Random random = new Random();
			value = value + gpsDecimalFormat.format((random.nextDouble() + 13))
					+ GPS_UNIT;
		}

		gpsLongitudeLabel.setText(value);
	}

	/**
	 * Gets the current latitude of the boat and sets the given label to the
	 * resulting value.
	 * 
	 * @param gpsLatitudeLabel
	 */
	public void updateGPSLatitude(javax.swing.JLabel gpsLatitudeLabel) {
		String value = "";

		if (!testMode) {
			value = value
					+ gpsDecimalFormat.format(controller.getGps().getPosition()
							.getLatitude()) + GPS_UNIT;
		} else {
			Random random = new Random();
			value = value + gpsDecimalFormat.format((random.nextDouble() + 13))
					+ GPS_UNIT;
		}

		gpsLatitudeLabel.setText(value);
	}

	/**
	 * Gets the current number of received satellites and sets the given label
	 * to the resulting value.
	 * 
	 * @param gpsSatelites
	 */
	public void updateGPSSatelites(javax.swing.JLabel gpsSatelites) {
		String value = "";

		if (!testMode) {
			value = value + controller.getGps().getPosition().getSatelites();
		} else {
			Random random = new Random();
			value = value + random.nextInt(9);
		}

		gpsSatelites.setText(value);
	}

	/**
	 * Gets the current azimuth of the boat and sets the given label to the
	 * resulting value.
	 * 
	 * @param compassAzimuth
	 */
	public void updateCompassAzimuth(javax.swing.JLabel compassAzimuth) {
		String value = "";

		if (!testMode) {
			value = value + controller.getCompass().getCompass().getAzimuth()
					+ COMPASS_UNIT;
		} else {
			Random random = new Random();
			value = value + random.nextInt(361) + COMPASS_UNIT;
		}

		compassAzimuth.setText(value);
	}

	/**
	 * Gets the current pitch of the boat and sets the given label to the
	 * resulting value.
	 * 
	 * @param compassPitch
	 */
	public void updateCompassPitch(javax.swing.JLabel compassPitch) {
		String value = "";

		if (!testMode) {
			value = value + controller.getCompass().getCompass().getPitch()
					+ COMPASS_UNIT;
		} else {
			Random random = new Random();
			value = value + random.nextInt(361) + COMPASS_UNIT;
		}

		compassPitch.setText(value);
	}

	/**
	 * Gets the current roll of the boat and sets the given label to the
	 * resulting value.
	 * 
	 * @param compassRoll
	 */
	public void updateCompassRoll(javax.swing.JLabel compassRoll) {
		String value = "";

		if (!testMode) {
			value = value + controller.getCompass().getCompass().getRoll()
					+ COMPASS_UNIT;
		} else {
			Random random = new Random();
			value = value + random.nextInt(361) + COMPASS_UNIT;
		}

		compassRoll.setText(value);
	}

	/**
	 * Gets the current wind velocity recognized by the boat and sets the given
	 * label to the resulting value.
	 * 
	 * @param windVelocity
	 */
	public void updateWindVelocity(javax.swing.JLabel windVelocity) {
		String value = "";

		if (!testMode) {
			value = value + controller.getWind().getWind().getSpeed()
					+ WIND_VELOCITY_UNIT;
		} else {
			Random random = new Random();
			value = value + random.nextInt(361) + WIND_VELOCITY_UNIT;
		}

		windVelocity.setText(value);
	}

	/**
	 * Gets the current wind direction recognized by the boat and sets the given
	 * label to the resulting value.
	 * 
	 * @param windDirection
	 */
	public void updateWindDirection(javax.swing.JLabel windDirection) {
		String value = "";

		if (!testMode) {
			value = value + controller.getWind().getWind().getDirection()
					+ WIND_DIRECTION_UNIT;
		} else {
			Random random = new Random();
			value = value + random.nextInt(361) + WIND_DIRECTION_UNIT;
		}

		windDirection.setText(value);
	}

	/**
	 * Able to put out info in the given TextArea. Currently not used. TODO why
	 * not used !!!!
	 * 
	 * @param systemTabTextArea
	 */
	public void updateSystemInfo(javax.swing.JTextArea systemTabTextArea) {
		String value = "";

		if (!testMode) {
			value = value + "Einzelpunkte:\n"
					+ missionMap.getMarkerList().toString() + "\nPolygon:\n"
					+ missionMap.getPolygonList().toString();
		} else {
			value = value + "Einzelpunkte:\n"
					+ missionMap.getMarkerList().toString() + "\nPolygon:\n"
					+ missionMap.getPolygonList().toString();
		}

		systemTabTextArea.setText(value);
	}

	/**
	 * Able to put out info in the given TextArea. Currently not used. TODO why
	 * not used !!!!
	 * 
	 * @param missionTabTextArea
	 */
	public void updateMissionInfo(javax.swing.JTextArea missionTabTextArea) {
		String value = "";

		if (!testMode) {
			// value = value + "..."; //TODO add info
		} else {
			Random random = new Random();
			value = value + random.nextInt(99999) + "\n"
					+ random.nextInt(99999) + "\n" + random.nextInt(99999)
					+ "\n" + random.nextInt(99999) + "\n";
		}

		missionTabTextArea.setText(value);
	}

	/**
	 * called once per gui_loop (see UPDATE_RATE) and can be used to manage
	 * timed tasks. Basic usage is to call update logic.
	 */
	public void updateLogic() {
		controller.updateAll();
		missionMap.followBoat(controller.getGps().getPosition(), controller
				.getWind().getWind(), controller.getCompass().getCompass());

		// visualize mission if mission is present and has changed
		// TODO beat paul into writing the promised overload for visualize
		/*
		 * if (this.controller.getCurrentWholeMission() != null) { if
		 * (this.controller.isMissionUpdated()) { if
		 * ((this.controller.getCurrentWholeMission().getTasks() != null) &&
		 * (this.controller.getCurrentMission().getTasks() != null))
		 * this.missionMap.visualizeMission(this.controller.getCurrentMission(),
		 * this.controller.getCurrentWholeMission()); } }
		 */

		/*
		 * GUIModelImpl paul = new GUIModelImpl();
		 * paul.setCurrentWholeMission(this
		 * .controller.getCurrentWholeMission());
		 * paul.setMissionTasksLeft(this.controller.getCurrentMission()); if
		 * ((this.controller.getCurrentWholeMission().getTasks() != null) &&
		 * (this.controller.getCurrentMission().getTasks() != null)) { if
		 * ((!this.controller.getCurrentWholeMission().getTasks().isEmpty()) &&
		 * (!this.controller.getCurrentMission().getTasks().isEmpty()))
		 * missionMap.visualizeMission(paul); }
		 */
		missionMap.visualizeMission(this.controller.getModel());
	}

	/**
	 * Closes the GUI with all necessary actions to do so in a clean state.
	 */
	public void closeGUI() {

		// TODO send StopTask before closing

		System.exit(0);
	}

	// the following are methods for primitive tests and are kept only for
	// future reference, refer to MissionCreator for mission handling
	@Deprecated
	/**
	 * Old method for sending ReachCircleTasks.
	 */
	public void sendCircleMarkers() {
		if (missionMap.getMap().getMapMarkerList().size() > 0) {
			// this.controller.setCircleMarkerList(GPSTransformations.mapMarkerListToGpsList(missionMap.getMap().getMapMarkerList()));
			this.controller.commitCircleMarkerList(planner);
			sendResetMissionMap();
		}
	}

	@Deprecated
	/**
	 * Old method for sending ReachPolygonTasks.
	 */
	public void sendPolyMapMarkers() {
		this.controller.setPolyList(missionMap.getPolygonList());
		this.controller.commitPolyList(planner);
		sendResetMissionMap();
	}

	@Deprecated
	/**
	 * Old method for sending a ReachCompassTask.
	 */
	public void sendReachCompass() {
		String str = JOptionPane.showInputDialog(null,
				"Bitte gewünschten Winkel eingeben: ", "Winkeleingabe", 1);
		if (str != null && str.length() != 0) {
			this.controller.commitReachCompassTask(Integer.parseInt(str),
					planner);
		}
	}

	@Deprecated
	/**
	 * Old method for sending a HoldAngleToWindTask.
	 */
	public void sendHoldAngleToWind() {
		String str = JOptionPane.showInputDialog(null,
				"Bitte gewünschten Winkel eingeben: ", "Winkeleingabe", 1);
		if (str.length() != 0) {
			this.controller.commitHoldAngleToWind(Integer.parseInt(str),
					planner);
		}
	}

	@Deprecated
	/**
	 * Old method for sending a StopTask.
	 */
	public void sendStop() {
		int choice = JOptionPane.showConfirmDialog(null,
				"Wirklich Mission stoppen?", "Bitte bestätigen",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			this.controller.commitStopTask(planner);
		}
	}

	@Deprecated
	/**
	 * Old method for resetting the actors (propellor, sail, rudder).
	 */
	public void sendResetActors() {
		int choice = JOptionPane.showConfirmDialog(null,
				"Wirklich Motor, Segel und Ruder zurücksetzen?",
				"Bitte bestätigen", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			this.controller.resetActorsTask(planner);
		}
	}

	/**
	 * resetting the MissionMap (markers, lines).
	 */
	public void sendResetMissionMap() {
		missionMap.removeEveryObject();
		missionMap.removeTrail();
		missionMap.getMap().repaint();
	}

	/**
	 * activating/ deactivation SailMode, resulting in usage of propellor or
	 * not.
	 * 
	 * @param sailMode
	 *            true if propellor should be deactivated
	 */
	public void setSailMode(boolean sailMode) {
		this.controller.setSailMode(sailMode);
	}

	/**
	 * Returns a reference to the MainController object.
	 * 
	 * @return MainController
	 */
	public MainController getController() {
		return this.controller;
	}

	/**
	 * Returns a reference to the Planner object.
	 */
	public Planner getPlanner() {
		return this.planner;
	}

	/**
	 * Returns a reference to the Map object (MissionMap).
	 */
	public Map getMissionMap() {
		return this.missionMap;
	}

	/**
	 * get the activating/ deactivation SailMode, resulting in usage of
	 * propellor or not. TODO Sailmode entfernen
	 */
	public boolean isSailMode() {
		return this.controller.isSailMode();
	}

}
