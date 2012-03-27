package de.fhb.sailboat.ufer.prototyp;


import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class represents a monitor for viewing and checking data send by/ concerning the sailboat. After choosing the type of data to be displayed it
 * provides a panel for adding to the main user interface. Such a data view is called a "perspective" within this class.
 * @author Patrick Rutter
 *
 */
public class BoatMonitor {
	
	// CONSTANTS
	final static public int PERSPECITVE_ID_BLANK = 0; // actually the implementation treats every not-defined perspective ID as blank to evade issues
	final static public int PERSPECITVE_ID_COMPASS = 1;
	final static public int PERSPECITVE_ID_GPS = 2;
	final static public int PERSPECITVE_ID_WIND = 3;
	final static public int PERSPECITVE_ID_MISSION = 4;
	
	final static public String P_BLANK_NAME = "Monitor";
	final static public int P_WIDTH = 200;
	final static public int P_HEIGHT = 120;
	
	final static public String P_COMPASS_NAME = "Kompass";
	final static public String P_GPS_NAME = "GPS";
	final static public String P_WIND_NAME = "Wind";
	final static public String P_MISSION_NAME = "Mission";

	final static public int L_OFFSET = 8; // Offset between certain elements (mostly descriptor- and valuelabels)
	final static public int L_DSIZE = 90; // Common width for descriptor labels
	final static public int L_VSIZE = 60; // Common width for value labels
	final static public int L_VSIZE_FULL = P_WIDTH - 20; // Width for extra-large value labels which are positioned to use the full panel width (keeping in mind offset)
	final static public int L_LINE = 18; // Common height of a line of text, used as distance between two lines of text (may be removed later)

	final static public String L_COMP_DIRECTION_NAME = "Azimuth:"; // The identifier-label for displaying the direction(?) measured
	final static public String L_COMP_DIRECTION_UNIT = " °"; // this will be appended to the value-label text for displaying the direction measured
	final static public int L_COMP_DIRECTION_X = 8;
	final static public int L_COMP_DIRECTION_Y = 16;
	final static public int L_COMP_DIRECTION_DSIZE = L_DSIZE;
	
	final static public int L_COMP_DIRECTION_V_X = L_COMP_DIRECTION_X + L_COMP_DIRECTION_DSIZE + L_OFFSET;
	final static public int L_COMP_DIRECTION_V_Y = L_COMP_DIRECTION_Y;
	final static public int L_COMP_DIRECTION_V_VSIZE = L_VSIZE;
	
	final static public String L_COMP_ACCELERATION_NAME = "Beschl.:"; // The identifier-label for displaying the acceleration measured
	final static public String L_COMP_ACCELERATION_UNIT = " ?"; // this will be appended to the value-label text for displaying the ACCELERATION measured
	final static public int L_COMP_ACCELERATION_X = L_COMP_DIRECTION_X;
	final static public int L_COMP_ACCELERATION_Y = L_COMP_DIRECTION_Y + L_LINE;
	final static public int L_COMP_ACCELERATION_DSIZE = L_DSIZE;
	
	final static public int L_COMP_ACCELERATION_V_X = L_COMP_ACCELERATION_X + L_COMP_ACCELERATION_DSIZE + L_OFFSET;
	final static public int L_COMP_ACCELERATION_V_Y = L_COMP_ACCELERATION_Y;
	final static public int L_COMP_ACCELERATION_V_VSIZE = L_VSIZE;
	
	final static public String L_COMP_PITCH_NAME = "Pitch:"; // The identifier-label for displaying the PITCH measured
	final static public String L_COMP_PITCH_UNIT = " °"; // this will be appended to the value-label text for displaying the PITCH measured
	final static public int L_COMP_PITCH_X = L_COMP_ACCELERATION_X;
	final static public int L_COMP_PITCH_Y = L_COMP_ACCELERATION_Y + L_LINE;
	final static public int L_COMP_PITCH_DSIZE = L_DSIZE;
	
	final static public int L_COMP_PITCH_V_X = L_COMP_PITCH_X + L_COMP_PITCH_DSIZE + L_OFFSET;
	final static public int L_COMP_PITCH_V_Y = L_COMP_PITCH_Y;
	final static public int L_COMP_PITCH_V_VSIZE = L_VSIZE;
	
	final static public String L_COMP_ROLL_NAME = "Roll:"; // The identifier-label for displaying the ROLL measured
	final static public String L_COMP_ROLL_UNIT = " °"; // this will be appended to the value-label text for displaying the ROLL measured
	final static public int L_COMP_ROLL_X = L_COMP_PITCH_X;
	final static public int L_COMP_ROLL_Y = L_COMP_PITCH_Y + L_LINE;
	final static public int L_COMP_ROLL_DSIZE = L_DSIZE;
	
	final static public int L_COMP_ROLL_V_X = L_COMP_ROLL_X + L_COMP_ROLL_DSIZE + L_OFFSET;
	final static public int L_COMP_ROLL_V_Y = L_COMP_ROLL_Y;
	final static public int L_COMP_ROLL_V_VSIZE = L_VSIZE;
	
	final static public String L_COMP_TEMPERATURE_NAME = "Temperatur:"; // The identifier-label for displaying the temperature measured
	final static public String L_COMP_TEMPERATURE_UNIT = " °C"; // this will be appended to the value-label text for displaying the temperature measured
	final static public int L_COMP_TEMPERATURE_X = L_COMP_ROLL_X;
	final static public int L_COMP_TEMPERATURE_Y = L_COMP_ROLL_Y + L_LINE;
	final static public int L_COMP_TEMPERATURE_DSIZE = L_DSIZE;
	
	final static public int L_COMP_TEMPERATURE_V_X = L_COMP_TEMPERATURE_X + L_COMP_TEMPERATURE_DSIZE + L_OFFSET;
	final static public int L_COMP_TEMPERATURE_V_Y = L_COMP_TEMPERATURE_Y;
	final static public int L_COMP_TEMPERATURE_V_VSIZE = L_VSIZE;
	
	final static public String L_GPS_LONG_NAME = "Longitude:";
	final static public String L_GPS_LONG_UNIT = "°"; // this will be appended to the value-label text for displaying the longitude
	final static public int L_GPS_LONG_X = 8;
	final static public int L_GPS_LONG_Y = 16;
	final static public int L_GPS_LONG_DSIZE = L_DSIZE;
	
	final static public int L_GPS_LONG_V_X = L_GPS_LONG_X + L_OFFSET;
	final static public int L_GPS_LONG_V_Y = L_GPS_LONG_Y + L_LINE;
	final static public int L_GPS_LONG_V_VSIZE = L_VSIZE * 2;
	
	final static public String L_GPS_LAT_NAME = "Latitude:";
	final static public String L_GPS_LAT_UNIT = "°"; // this will be appended to the value-label text for displaying the Latitude
	final static public int L_GPS_LAT_X = 8;
	final static public int L_GPS_LAT_Y = 16 + (L_LINE * 2);
	final static public int L_GPS_LAT_DSIZE = L_DSIZE;
	
	final static public int L_GPS_LAT_V_X = L_GPS_LAT_X + L_OFFSET;
	final static public int L_GPS_LAT_V_Y = L_GPS_LAT_Y + L_LINE;
	final static public int L_GPS_LAT_V_VSIZE = L_VSIZE * 2;
	
	final static public String L_GPS_PRECISION_NAME = "Präzision:";
	final static public String L_GPS_PRECISION_UNIT = ""; // this will be appended to the value-label text for displaying the precision
	final static public int L_GPS_PRECISION_X = 8;
	final static public int L_GPS_PRECISION_Y = 16 + (L_LINE * 4);
	final static public int L_GPS_PRECISION_DSIZE = L_DSIZE;
	
	final static public int L_GPS_PRECISION_V_X = L_GPS_PRECISION_X + L_GPS_PRECISION_DSIZE + L_OFFSET;
	final static public int L_GPS_PRECISION_V_Y = L_GPS_PRECISION_Y;
	final static public int L_GPS_PRECISION_V_VSIZE = L_VSIZE;
	
	final static public String L_WIND_VELOCITY_NAME = "Windgeschw:";
	final static public String L_WIND_VELOCITY_UNIT = " kn"; // this will be appended to the value-label text for displaying the wind velocity
	final static public int L_WIND_VELOCITY_X = 8;
	final static public int L_WIND_VELOCITY_Y = 16;
	final static public int L_WIND_VELOCITY_DSIZE = L_DSIZE;
	
	final static public int L_WIND_VELOCITY_V_X = L_WIND_VELOCITY_X + L_WIND_VELOCITY_DSIZE + L_OFFSET;
	final static public int L_WIND_VELOCITY_V_Y = L_WIND_VELOCITY_Y;
	final static public int L_WIND_VELOCITY_V_VSIZE = L_VSIZE;
	
	final static public String L_WIND_DIRECTION_NAME = "Windrichtung:";
	final static public String L_WIND_DIRECTION_UNIT = " °"; // this will be appended to the value-label text for displaying the winddirection
	final static public int L_WIND_DIRECTION_X = 8;
	final static public int L_WIND_DIRECTION_Y = 16 + L_LINE;
	final static public int L_WIND_DIRECTION_DSIZE = L_DSIZE;
	
	final static public int L_WIND_DIRECTION_V_X = L_WIND_DIRECTION_X + L_WIND_DIRECTION_DSIZE + L_OFFSET;
	final static public int L_WIND_DIRECTION_V_Y = L_WIND_DIRECTION_Y;
	final static public int L_WIND_DIRECTION_V_VSIZE = L_VSIZE;

	// VARIABLES
	private Controller controller; // used controller, interface to model
	
	private JPanel panel;
	private int panelX;
	private int panelY;
	
	private int perspectiveID; // current perspective represent as ID (see constants)
	
	// compass perspective
	private JLabel lCompDirection;
	private JLabel lCompDirectionV;
	private JLabel lCompAcceleration;
	private JLabel lCompAccelerationV;
	private JLabel lCompPitch;
	private JLabel lCompPitchV;
	private JLabel lCompRoll;
	private JLabel lCompRollV;
	
	// GPS perspective
	private JLabel lGPSLongitude;
	private JLabel lGPSLongitudeV;
	private JLabel lGPSLatitude;
	private JLabel lGPSLatitudeV;
	private JLabel lGPSPrecision;
	private JLabel lGPSPrecisionV;
	
	// wind perspective
	private JLabel lWindVelocity;
	private JLabel lWindVelocityV;
	private JLabel lWindDirection;
	private JLabel lWindDirectionV;
	
	// temperature perspective
	private JLabel lCompTemperature; // identifier label for Temperature
	private JLabel lCompTemperatureV; // value label for Temperature
	
	public BoatMonitor(int panelX, int panelY, Controller controller) {
		this.controller = controller;
		panel = new JPanel();
		this.panelX = panelX;
		this.panelY = panelY;
		perspectiveID = PERSPECITVE_ID_BLANK;
	}
	
	/**
	 * Initializes the currently used perspective by applying it to the monitor panel.
	 */
	private void initializePanel() {
		panel.removeAll();
		panel.setLayout(null);
		panel.setBounds(panelX, panelY, P_WIDTH, P_HEIGHT);
		
		switch (perspectiveID) {
			case (PERSPECITVE_ID_COMPASS) : {
				// free other components
				freeAllBut(PERSPECITVE_ID_COMPASS);
				
				// add compass components
				panel.setBorder(new javax.swing.border.TitledBorder(P_COMPASS_NAME));
				
				// Direction
				lCompDirection = new JLabel();
				lCompDirection.setText(L_COMP_DIRECTION_NAME);
				lCompDirection.setSize(new Dimension(L_COMP_DIRECTION_DSIZE, L_LINE));
				lCompDirection.setLocation(L_COMP_DIRECTION_X, L_COMP_DIRECTION_Y);
				panel.add(lCompDirection);
				
				lCompDirectionV = new JLabel();
				lCompDirectionV.setText("0" + L_COMP_DIRECTION_UNIT);
				lCompDirectionV.setSize(new Dimension(L_VSIZE, L_LINE));
				lCompDirectionV.setLocation(L_COMP_DIRECTION_X + L_COMP_DIRECTION_DSIZE + L_OFFSET, L_COMP_DIRECTION_Y);
				panel.add(lCompDirectionV);
				
				// Acceleration
				lCompAcceleration = new JLabel();
				lCompAcceleration.setText(L_COMP_ACCELERATION_NAME);
				lCompAcceleration.setSize(new Dimension(L_COMP_ACCELERATION_DSIZE, L_LINE));
				lCompAcceleration.setLocation(L_COMP_ACCELERATION_X, L_COMP_ACCELERATION_Y);
				panel.add(lCompAcceleration);
				
				lCompAccelerationV = new JLabel();
				lCompAccelerationV.setText("0" + L_COMP_ACCELERATION_UNIT);
				lCompAccelerationV.setSize(new Dimension(L_VSIZE, L_LINE));
				lCompAccelerationV.setLocation(L_COMP_ACCELERATION_X + L_COMP_ACCELERATION_DSIZE + L_OFFSET, L_COMP_ACCELERATION_Y);
				panel.add(lCompAccelerationV);
				
				// Pitch
				lCompPitch = new JLabel();
				lCompPitch.setText(L_COMP_PITCH_NAME);
				lCompPitch.setSize(new Dimension(L_COMP_PITCH_DSIZE, L_LINE));
				lCompPitch.setLocation(L_COMP_PITCH_X, L_COMP_PITCH_Y);
				panel.add(lCompPitch);
				
				lCompPitchV = new JLabel();
				lCompPitchV.setText("0" + L_COMP_PITCH_UNIT);
				lCompPitchV.setSize(new Dimension(L_VSIZE, L_LINE));
				lCompPitchV.setLocation(L_COMP_PITCH_X + L_COMP_PITCH_DSIZE + L_OFFSET, L_COMP_PITCH_Y);
				panel.add(lCompPitchV);
				
				// Roll
				lCompRoll = new JLabel();
				lCompRoll.setText(L_COMP_ROLL_NAME);
				lCompRoll.setSize(new Dimension(L_COMP_ROLL_DSIZE, L_LINE));
				lCompRoll.setLocation(L_COMP_ROLL_X, L_COMP_ROLL_Y);
				panel.add(lCompRoll);
				
				lCompRollV = new JLabel();
				lCompRollV.setText("0" + L_COMP_ROLL_UNIT);
				lCompRollV.setSize(new Dimension(L_VSIZE, L_LINE));
				lCompRollV.setLocation(L_COMP_ROLL_X + L_COMP_ROLL_DSIZE + L_OFFSET, L_COMP_ROLL_Y);
				panel.add(lCompRollV);
				
				// Temperature
				lCompTemperature = new JLabel();
				lCompTemperature.setText(L_COMP_TEMPERATURE_NAME);
				lCompTemperature.setSize(new Dimension(L_COMP_TEMPERATURE_DSIZE, L_LINE));
				lCompTemperature.setLocation(L_COMP_TEMPERATURE_X, L_COMP_TEMPERATURE_Y);
				panel.add(lCompTemperature);
				
				lCompTemperatureV = new JLabel();
				lCompTemperatureV.setText("0" + L_COMP_TEMPERATURE_UNIT);
				lCompTemperatureV.setSize(new Dimension(L_VSIZE, L_LINE));
				lCompTemperatureV.setLocation(L_COMP_TEMPERATURE_X + L_COMP_TEMPERATURE_DSIZE + L_OFFSET, L_COMP_TEMPERATURE_Y);
				panel.add(lCompTemperatureV);
				
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_GPS) : {
				// free other components
				freeAllBut(PERSPECITVE_ID_GPS);
				
				// add GPS components
				panel.setBorder(new javax.swing.border.TitledBorder(P_GPS_NAME));
				
				// Longitude
				lGPSLongitude = new JLabel();
				lGPSLongitude.setText(L_GPS_LONG_NAME);
				lGPSLongitude.setSize(new Dimension(L_GPS_LONG_DSIZE, L_LINE));
				lGPSLongitude.setLocation(L_GPS_LONG_X, L_GPS_LONG_Y);
				panel.add(lGPSLongitude);
				
				lGPSLongitudeV = new JLabel();
				lGPSLongitudeV.setText("0" + L_GPS_LONG_UNIT);
				lGPSLongitudeV.setSize(new Dimension(L_VSIZE_FULL, L_LINE));
				lGPSLongitudeV.setLocation(L_GPS_LONG_V_X, L_GPS_LONG_V_Y);
				panel.add(lGPSLongitudeV);
				
				// Latitude
				lGPSLatitude = new JLabel();
				lGPSLatitude.setText(L_GPS_LAT_NAME);
				lGPSLatitude.setSize(new Dimension(L_GPS_LAT_DSIZE, L_LINE));
				lGPSLatitude.setLocation(L_GPS_LAT_X, L_GPS_LAT_Y);
				panel.add(lGPSLatitude);
				
				lGPSLatitudeV = new JLabel();
				lGPSLatitudeV.setText("0" + L_GPS_LAT_UNIT);
				lGPSLatitudeV.setSize(new Dimension(L_VSIZE_FULL, L_LINE));
				lGPSLatitudeV.setLocation(L_GPS_LAT_V_X, L_GPS_LAT_V_Y);
				panel.add(lGPSLatitudeV);
				
				// Precision
				lGPSPrecision = new JLabel();
				lGPSPrecision.setText(L_GPS_PRECISION_NAME);
				lGPSPrecision.setSize(new Dimension(L_GPS_PRECISION_DSIZE, L_LINE));
				lGPSPrecision.setLocation(L_GPS_PRECISION_X, L_GPS_PRECISION_Y);
				panel.add(lGPSPrecision);
				
				lGPSPrecisionV = new JLabel();
				lGPSPrecisionV.setText("0" + L_GPS_PRECISION_UNIT);
				lGPSPrecisionV.setSize(new Dimension(L_VSIZE, L_LINE));
				lGPSPrecisionV.setLocation(L_GPS_PRECISION_X + L_GPS_PRECISION_DSIZE + L_OFFSET, L_GPS_PRECISION_Y);
				panel.add(lGPSPrecisionV);
				
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_WIND) : {
				// free other components
				freeAllBut(PERSPECITVE_ID_WIND);
				
				// add wind components
				panel.setBorder(new javax.swing.border.TitledBorder(P_WIND_NAME));
				
				// Velocity
				lWindVelocity = new JLabel();
				lWindVelocity.setText(L_WIND_VELOCITY_NAME);
				lWindVelocity.setSize(new Dimension(L_WIND_VELOCITY_DSIZE, L_LINE));
				lWindVelocity.setLocation(L_WIND_VELOCITY_X, L_WIND_VELOCITY_Y);
				panel.add(lWindVelocity);
				
				lWindVelocityV = new JLabel();
				lWindVelocityV.setText("0" + L_WIND_VELOCITY_UNIT);
				lWindVelocityV.setSize(new Dimension(L_VSIZE, L_LINE));
				lWindVelocityV.setLocation(L_WIND_VELOCITY_X + L_WIND_VELOCITY_DSIZE + L_OFFSET, L_WIND_VELOCITY_Y);
				panel.add(lWindVelocityV);
				
				// Direction
				lWindDirection = new JLabel();
				lWindDirection.setText(L_WIND_DIRECTION_NAME);
				lWindDirection.setSize(new Dimension(L_WIND_DIRECTION_DSIZE, L_LINE));
				lWindDirection.setLocation(L_WIND_DIRECTION_X, L_WIND_DIRECTION_Y);
				panel.add(lWindDirection);
				
				lWindDirectionV = new JLabel();
				lWindDirectionV.setText("0" + L_WIND_DIRECTION_UNIT);
				lWindDirectionV.setSize(new Dimension(L_VSIZE, L_LINE));
				lWindDirectionV.setLocation(L_WIND_DIRECTION_X + L_WIND_DIRECTION_DSIZE + L_OFFSET, L_WIND_DIRECTION_Y);
				panel.add(lWindDirectionV);
				
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_MISSION) : {
				// free other components
				freeAllBut(PERSPECITVE_ID_MISSION);
				
				// create temperature components
				panel.setBorder(new javax.swing.border.TitledBorder(P_MISSION_NAME));
				
				panel.validate();
				break;
			}
			default : {
				// blank components
				panel.setBorder(new javax.swing.border.TitledBorder(P_BLANK_NAME));
				
				panel.validate();
				break;
			}
		}
	}
	
	/**
	 * Updates values to be displayed in the current perspective.
	 */
	public void updatePanel() {
		switch (perspectiveID) {
			case (PERSPECITVE_ID_COMPASS) : {
				if (lCompDirectionV != null) lCompDirectionV.setText(controller.getCompass().getCompass().getAzimuth() + L_COMP_DIRECTION_UNIT);
				if (lCompAccelerationV != null) lCompAccelerationV.setText(controller.getCompass().getCompass().getAcceleration() + L_COMP_ACCELERATION_UNIT);
				if (lCompPitchV != null) lCompPitchV.setText(controller.getCompass().getCompass().getPitch() + L_COMP_PITCH_UNIT);
				if (lCompRollV != null) lCompRollV.setText(controller.getCompass().getCompass().getRoll() + L_COMP_ROLL_UNIT);
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_GPS) : {
				if (lGPSLongitudeV != null) lGPSLongitudeV.setText(controller.getGps().getPosition().getLongitude() + L_GPS_LONG_UNIT);
				if (lGPSLatitudeV != null) lGPSLatitudeV.setText(controller.getGps().getPosition().getLatitude() + L_GPS_LAT_UNIT);
				if (lGPSPrecisionV != null) lGPSPrecisionV.setText(controller.getGpsPrecision() + L_GPS_PRECISION_UNIT);
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_WIND) : {
				if (lWindVelocityV != null) lWindVelocityV.setText(controller.getWind().getWind().getSpeed() + L_WIND_VELOCITY_UNIT);
				if (lWindDirectionV != null) lWindDirectionV.setText(controller.getWind().getWind().getDirection() + L_WIND_DIRECTION_UNIT);
				panel.validate();
				break;
			}
			case (PERSPECITVE_ID_MISSION) : {
				if (lCompTemperatureV != null) lCompTemperatureV.setText(controller.getCompass().getCompass().getTemperature() + L_COMP_TEMPERATURE_UNIT);
				panel.validate();
				break;
			}
			default : {
				panel.validate();
				break;
			}
		}
	}
	
	/**
	 * Sets all but the components of the chosen perspective to null, thus "freeing" them to be disposed by the garbage collector.
	 * If accidentally the currently set perspective is attempted to be freed the command is ignored.
	 * @param perspectiveID
	 */
	private void freeAllBut(int perspectiveID) {
		switch (perspectiveID) {
			case (PERSPECITVE_ID_COMPASS) : {
				if (perspectiveID != PERSPECITVE_ID_COMPASS) {
					lGPSLatitude = null;
					lGPSLatitudeV = null;
					lGPSLongitude = null;
					lGPSLongitudeV = null;
					lGPSPrecision = null;
					lGPSPrecisionV = null;
					lWindVelocity = null;
					lWindVelocityV = null;
					lWindDirection = null;
					lWindDirectionV = null;
				}
				
				break;
			}
			case (PERSPECITVE_ID_GPS) : {
				if (perspectiveID != PERSPECITVE_ID_WIND) {
					lWindVelocity = null;
					lWindVelocityV = null;
					lWindDirection = null;
					lWindDirectionV = null;
					lCompDirection = null;
					lCompDirectionV = null;
					lCompAcceleration = null;
					lCompAccelerationV = null;
					lCompPitchV = null;
					lCompRollV = null;
					lCompTemperature = null;
					lCompTemperatureV = null;
				}
				
				break;
			}
			case (PERSPECITVE_ID_WIND) : {
				if (perspectiveID != PERSPECITVE_ID_WIND) {
					lGPSLatitude = null;
					lGPSLatitudeV = null;
					lGPSLongitude = null;
					lGPSLongitudeV = null;
					lGPSPrecision = null;
					lGPSPrecisionV = null;
					lCompDirection = null;
					lCompDirectionV = null;
					lCompAcceleration = null;
					lCompAccelerationV = null;
					lCompPitchV = null;
					lCompRollV = null;
					lCompTemperature = null;
					lCompTemperatureV = null;
				}
				
				break;
			}
			case (PERSPECITVE_ID_MISSION) : {
				if (perspectiveID != PERSPECITVE_ID_MISSION) {
					lGPSLatitude = null;
					lGPSLatitudeV = null;
					lGPSLongitude = null;
					lGPSLongitudeV = null;
					lGPSPrecision = null;
					lGPSPrecisionV = null;
					lWindVelocity = null;
					lWindVelocityV = null;
					lWindDirection = null;
					lWindDirectionV = null;
					lCompDirection = null;
					lCompDirectionV = null;
					lCompAcceleration = null;
					lCompAccelerationV = null;
					lCompPitchV = null;
					lCompRollV = null;
					lCompTemperature = null;
					lCompTemperatureV = null;
				}
				
				break;
			}
		}
	}
	
	public JPanel getJPanel() {
		return panel;
	}
	
	public void setPerspective(int perspectiveID) {
		this.perspectiveID = perspectiveID;
		initializePanel();
	}
	
	public int getPerspective() {
		return this.perspectiveID;
	}
	
}
