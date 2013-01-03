package de.fhb.sailboat.gui;

import de.fhb.sailboat.control.Planner;
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
    GUIController controller = null;				// the GUIController is used for getting and setting values from and to the world model/ local database
    
    /**
     * Commit a mission to planner.
     * @param mission
     */
    public void commitMission(Mission mission);
    
    /**
     * Initializes a Map Object and assigns it to the given panel.
     */
    public void initializeMissionMap(javax.swing.JPanel missionMapPanel);
    
    /**
     * Gets the current longitude of the boat and sets the given label to the resulting value. 
     */
    public void updateGPSLongitude(javax.swing.JLabel gpsLongitudeLabel);
    
    /**
     * Gets the current latitude of the boat and sets the given label to the resulting value. 
     */
    public void updateGPSLatitude(javax.swing.JLabel gpsLatitudeLabel);
    
    /**
     * Gets the current number of received satellites and sets the given label to the resulting value. 
     */
    public void updateGPSSatelites(javax.swing.JLabel gpsSatelites);
    
    /**
     * Gets the current azimuth of the boat and sets the given label to the resulting value. 
     */
    public void updateCompassAzimuth(javax.swing.JLabel compassAzimuth);
    
    /**
     * Gets the current pitch of the boat and sets the given label to the resulting value. 
     */
    public void updateCompassPitch(javax.swing.JLabel compassPitch);
    
    /**
     * Gets the current roll of the boat and sets the given label to the resulting value. 
     */
    public void updateCompassRoll(javax.swing.JLabel compassRoll);
    
    /**
     * Gets the current wind velocity recognized by the boat and sets the given label to the resulting value. 
     */
    public void updateWindVelocity(javax.swing.JLabel windVelocity);
    
    /**
     * Gets the current wind direction recognized by the boat and sets the given label to the resulting value. 
     */
    public void updateWindDirection(javax.swing.JLabel windDirection);
    
    /**
     * Able to put out info in the given TextArea.
     */
    public void updateSystemInfo(javax.swing.JTextArea systemTabTextArea);
    
    /**
     * Able to put out info in the given TextArea.
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
    
    public GUIController getController();
    
    public Planner getPlanner();
    
    // the following are methods for primitive tests and are kept only for reference
    @Deprecated
    public void sendCircleMarkers();
    @Deprecated
    public void sendPolyMapMarkers();
    @Deprecated
    public void sendReachCompass();
    @Deprecated
    public void sendHoldAngleToWind();
    @Deprecated
    public void sendStop();
    @Deprecated
    public void sendResetActors();
    @Deprecated
    public void sendResetMissionMap();
    @Deprecated
    public void setSailMode(boolean sailMode);
    
    public Map getMissionMap();
    
}
