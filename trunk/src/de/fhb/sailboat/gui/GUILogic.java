/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.sailboat.gui;

import de.fhb.sailboat.control.Planner;

/**
 *
 * @author Frocean
 */
public interface GUILogic {
    
    public final static int UPDATE_RATE = 500;
    
    Planner planner = null;;
    GUIController controller = null;
    
    public void initializeMissionMap(javax.swing.JPanel missionMapPanel);
    
    public void updateGPSLongitude(javax.swing.JLabel gpsLongitudeLabel);
    public void updateGPSLatitude(javax.swing.JLabel gpsLatitudeLabel);
    public void updateGPSSatelites(javax.swing.JLabel gpsSatelites);
    
    public void updateCompassAzimuth(javax.swing.JLabel compassAzimuth);
    public void updateCompassPitch(javax.swing.JLabel compassPitch);
    public void updateCompassRoll(javax.swing.JLabel compassRoll);
    
    public void updateWindVelocity(javax.swing.JLabel windVelocity);
    public void updateWindDirection(javax.swing.JLabel windDirection);
    
    public void updateSystemInfo(javax.swing.JTextArea systemTabTextArea);
    public void updateMissionInfo(javax.swing.JTextArea missionTabTextArea);
    
    public void updateLogic();
    
    public void closeGUI();
    
    // the following are methods for primitive tests and should be removed in future releases (namely after missionCreatorDialog is finished)
    public void sendCircleMarkers();
    public void sendPolyMapMarkers();
    public void sendReachCompass();
    public void sendHoldAngleToWind();
    public void sendStop();
    public void sendResetActors();
    public void sendResetMissionMap();
    public void setSailMode(boolean sailMode);
    
}
