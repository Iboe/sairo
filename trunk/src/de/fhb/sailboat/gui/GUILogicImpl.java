/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.sailboat.gui;

import java.text.DecimalFormat;
import java.util.Random;

import javax.swing.JOptionPane;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.gui.map.GPSTransformations;
import de.fhb.sailboat.gui.map.Map;

/**
 *
 * @author Frocean
 */
public class GUILogicImpl implements GUILogic {

    @SuppressWarnings("FieldNameHidesFieldInSuperclass")
    public final static int UPDATE_RATE = 500;                      // sleep in ms after each gui loop
    final static public String GPS_DECIMAL_FORMAT = "00.000000";    // format string for GPS longitude/ latitude display style
    final static public String GPS_UNIT = "°";                      // this will be appended to longitude/ latitude display strings
    final static public String COMPASS_UNIT = "°";                  // this will be appended to azimuth, pitch & roll display strings
    final static public String WIND_VELOCITY_UNIT = " m/s";         // this will be appended to windVelocity display strings
    final static public String WIND_DIRECTION_UNIT = "°";           // this will be appended to windDirection display strings
    
    
    private GUIController controller;
    private Planner planner;
    private Map missionMap;
    private boolean testMode;                                       // if true random values will be generated for each update, instead of trying to retrieve true values
    private DecimalFormat gpsDecimalFormat;

    public GUILogicImpl() {
    	this.testMode = false;
        this.gpsDecimalFormat = new DecimalFormat(GPS_DECIMAL_FORMAT);
        this.controller = new GUIControllerImpl();
    }
    
    public GUILogicImpl(Planner planner) {
        this.testMode = false;
        this.gpsDecimalFormat = new DecimalFormat(GPS_DECIMAL_FORMAT);
        this.controller = new GUIControllerImpl();
        this.planner = planner;
    }

    public GUILogicImpl(boolean testMode) {
        this();
        this.testMode = testMode;
    }

    @Override
    public void initializeMissionMap(javax.swing.JPanel missionMapPanel) {
        missionMap = new Map();
        missionMap.mapPanel(missionMapPanel);
    }

    @Override
    public void updateGPSLongitude(javax.swing.JLabel gpsLongitudeLabel) {
        String value = "";

        if (!testMode) {
            value = value + gpsDecimalFormat.format(controller.getGps().getPosition().getLongitude()) + GPS_UNIT;
        } else {
            Random random = new Random();
            value = value + gpsDecimalFormat.format((random.nextDouble() + 13)) + GPS_UNIT;
        }

        gpsLongitudeLabel.setText(value);
    }

    @Override
    public void updateGPSLatitude(javax.swing.JLabel gpsLatitudeLabel) {
        String value = "";

        if (!testMode) {
            value = value + gpsDecimalFormat.format(controller.getGps().getPosition().getLatitude()) + GPS_UNIT;
        } else {
            Random random = new Random();
            value = value + gpsDecimalFormat.format((random.nextDouble() + 13)) + GPS_UNIT;
        }

        gpsLatitudeLabel.setText(value);
    }

    @Override
    public void updateGPSSatelites(javax.swing.JLabel gpsSatelites) {
        String value = "";

        if (!testMode) {
            value = value + controller.getGpsSatelites(); //FIXME proper GPS class usage
        } else {
            Random random = new Random();
            value = value + random.nextInt(9);
        }

        gpsSatelites.setText(value);
    }

    @Override
    public void updateCompassAzimuth(javax.swing.JLabel compassAzimuth) {
        String value = "";

        if (!testMode) {
            value = value + controller.getCompass().getCompass().getAzimuth() + COMPASS_UNIT;
        } else {
            Random random = new Random();
            value = value + random.nextInt(361) + COMPASS_UNIT;
        }

        compassAzimuth.setText(value);
    }

    @Override
    public void updateCompassPitch(javax.swing.JLabel compassPitch) {
        String value = "";

        if (!testMode) {
            value = value + controller.getCompass().getCompass().getPitch() + COMPASS_UNIT;
        } else {
            Random random = new Random();
            value = value + random.nextInt(361) + COMPASS_UNIT;
        }

        compassPitch.setText(value);
    }

    @Override
    public void updateCompassRoll(javax.swing.JLabel compassRoll) {
        String value = "";

        if (!testMode) {
            value = value + controller.getCompass().getCompass().getPitch() + COMPASS_UNIT;
        } else {
            Random random = new Random();
            value = value + random.nextInt(361) + COMPASS_UNIT;
        }

        compassRoll.setText(value);
    }

    @Override
    public void updateWindVelocity(javax.swing.JLabel windVelocity) {
        String value = "";

        if (!testMode) {
            value = value + controller.getWind().getWind().getSpeed() + WIND_VELOCITY_UNIT;
        } else {
            Random random = new Random();
            value = value + random.nextInt(361) + WIND_VELOCITY_UNIT;
        }

        windVelocity.setText(value);
    }

    @Override
    public void updateWindDirection(javax.swing.JLabel windDirection) {
        String value = "";

        if (!testMode) {
            value = value + controller.getWind().getWind().getDirection() + WIND_DIRECTION_UNIT;
        } else {
            Random random = new Random();
            value = value + random.nextInt(361) + WIND_DIRECTION_UNIT;
        }

        windDirection.setText(value);
    }

    @Override
    public void updateSystemInfo(javax.swing.JTextArea systemTabTextArea) {
        String value = "";

        if (!testMode) {
            value = value + "Einzelpunkte:\n" + missionMap.getMap().getMapMarkerList().toString() + "\nPolygon:\n" + missionMap.getPolygonList().toString();
        } else {
            Random random = new Random();
            value = value + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n";
        }

        systemTabTextArea.setText(value);
    }

    @Override
    public void updateMissionInfo(javax.swing.JTextArea missionTabTextArea) {
        String value = "";

        if (!testMode) {
            //value = value + "..."; //TODO add info
        } else {
            Random random = new Random();
            value = value + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n" + random.nextInt(99999) + "\n";
        }

        missionTabTextArea.setText(value);
    }

    @Override
    /**
     * This will be called once per gui_loop (see UPDATE_RATE) and can be used
     * to manage timed tasks.
     */
    public void updateLogic() {
        
    }

    @Override
    public void closeGUI() {
        System.exit(0);
    }
    
    // the following are methods for primitive tests and should be removed in future releases (namely after missionCreatorDialog is finished)
    @Override
    public void sendCircleMarkers() {
    	if (missionMap.getMap().getMapMarkerList().size() > 0) {
			this.controller.setCircleMarkerList(GPSTransformations
					.mapMarkerListToGpsList(missionMap.getMap().getMapMarkerList()));
			this.controller.commitCircleMarkerList(planner);
			sendResetMissionMap();
		}
    }
    
    @Override
    public void sendPolyMapMarkers() {
    	this.controller.setPolyList(missionMap.getPolygonList());
		this.controller.commitPolyList(planner);
		sendResetMissionMap();
    }
    
    @Override
    public void sendReachCompass() {
    	String str = JOptionPane.showInputDialog(null,
				"Bitte gewünschten Winkel eingeben: ", "Winkeleingabe", 1);
		if (str.length() != 0) {
			this.controller.commitReachCompassTask(Integer.parseInt(str),
					planner);
		}
    }
    
    @Override
    public void sendHoldAngleToWind() {
    	String str = JOptionPane.showInputDialog(null,
				"Bitte gewünschten Winkel eingeben: ", "Winkeleingabe", 1);
		if (str.length() != 0) {
			this.controller.commitHoldAngleToWind(Integer.parseInt(str),
					planner);
		}
    }
    
    @Override
    public void sendStop() {
    	int choice = JOptionPane.showConfirmDialog(null,
				"Wirklich Mission stoppen?", "Bitte bestätigen",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			this.controller.commitStopTask(planner);
		}
    }
    
    @Override
    public void sendResetActors() {
    	int choice = JOptionPane.showConfirmDialog(null,
				"Wirklich Motor, Segel und Ruder zurücksetzen?", "Bitte bestätigen",
				JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			this.controller.resetActorsTask(planner);
		}
    }
    
    @Override
    public void sendResetMissionMap() {
        missionMap.removeEveryObject();
        missionMap.getMap().repaint();
    }
    
    @Override
    public void setSailMode(boolean sailMode) {
        this.controller.setSailMode(sailMode);
    }
}
