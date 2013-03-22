package de.fhb.sailboat.gui;

import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.gui.map.Map;
import de.fhb.sailboat.mission.Mission;

/**
 * This class represents the program logic used by a GUInterface. It handles updating, sending and converting of value displayed
 * and set via the GUI.
 * 
 * @author Patrick Rutter
 */
public interface GUILogic {
    
    Planner planner = null;;						// the Planner is used for sending mission sets
    MainController controller = null;				// the GUIController is used for getting and setting values from and to the world model/ local database
    
    /**
     * Commit a mission to planner.
     * @param mission
     */
    public void commitMission(Mission mission);
    
    /**
     * Initializes a Map Object and assigns it to the given panel.
     * @param missionMapPanel
     */
    public void initializeMissionMap(javax.swing.JPanel missionMapPanel);
    
    /**
     * Gets the current longitude of the boat and sets the given label to the resulting value.
     * @param gpsLongitudeLabel
     */
    public void updateGPSLongitude(javax.swing.JLabel gpsLongitudeLabel);
    
    /**
     * Gets the current latitude of the boat and sets the given label to the resulting value.
     * @param gpsLatitudeLabel
     */
    public void updateGPSLatitude(javax.swing.JLabel gpsLatitudeLabel);
    
    /**
     * Gets the current number of received satellites and sets the given label to the resulting value.
     * @param gpsSatelites
     */
    public void updateGPSSatelites(javax.swing.JLabel gpsSatelites);
    
    /**
     * Gets the current azimuth of the boat and sets the given label to the resulting value.
     * @param compassAzimuth
     */
    public void updateCompassAzimuth(javax.swing.JLabel compassAzimuth);
    
    /**
     * Gets the current pitch of the boat and sets the given label to the resulting value.
     * @param compassPitch
     */
    public void updateCompassPitch(javax.swing.JLabel compassPitch);
    
    /**
     * Gets the current roll of the boat and sets the given label to the resulting value.
     * @param compassRoll
     */
    public void updateCompassRoll(javax.swing.JLabel compassRoll);
    
    /**
     * Gets the current wind velocity recognized by the boat and sets the given label to the resulting value.
     * @param windVelocity
     */
    public void updateWindVelocity(javax.swing.JLabel windVelocity);
    
    /**
     * Gets the current wind direction recognized by the boat and sets the given label to the resulting value.
     * @param windDirection
     */
    public void updateWindDirection(javax.swing.JLabel windDirection);
    
    /**
     * Able to put out info in the given TextArea.
     * @param systemTabTextArea
     */
    public void updateSystemInfo(javax.swing.JTextArea systemTabTextArea);
    
    /**
     * Able to put out info in the given TextArea.
     * @param missionTabTextArea
     */
    public void updateMissionInfo(javax.swing.JTextArea missionTabTextArea);
    
    /**
     * This will be called once per gui_loop (see UPDATE_RATE) and can be used
     * to manage timed tasks. Basic usage is to call update logic.
     */
    public void updateLogic();
    
    /**
     * Closes the GUI with all necessary actions to do so in a clean state.
     */
    public void closeGUI();
    
    /**
     * Gets the  MainController.
     * @return MainController
     */
    public MainController getController();
    
    /**
     * Get the Planner.
     * @return Planner
     */
    public Planner getPlanner();
    
    // the following are methods for primitive tests and are kept only for reference
    @Deprecated
    /**
     * Old method for sending ReachCircleTasks.
     */
    public void sendCircleMarkers();
    @Deprecated
    /**
     * Old method for sending ReachPolygonTasks.
     */
    public void sendPolyMapMarkers();
    @Deprecated
    /**
     * Old method for sending a ReachCompassTask.
     */
    public void sendReachCompass();
    @Deprecated
    /**
     * Old method for sending a HoldAngleToWindTask.
     */
    public void sendHoldAngleToWind();
    @Deprecated
    /**
     * Old method to send a StopTask.
     */
    public void sendStop();
    @Deprecated
    /**
     * Old method to reset Actors (propellor, sail, rudder).
     */
    public void sendResetActors();
    @Deprecated
    /**
     * Old method for reseting the MissionMap (markers, lines).
     */
    public void sendResetMissionMap();
    @Deprecated
    /**
     * Old method used for activating/ deactivation SailMode, resulting in
     * usage of propellor or not.
     * @param sailMode true if propellor should be deactivated
     */
    public void setSailMode(boolean sailMode);
    
    /**
     * Returns the referrence of the MissionMap object.
     * @return MissionMap
     */
    public Map getMissionMap();
    
}
