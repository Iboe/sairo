package de.fhb.sailboat.ufer.prototyp.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.fhb.sailboat.ufer.prototyp.Controller;

/**
 * 
 * @author Patrick Rutter
 *
 */
public class UferLogger {
	
	// CONSTANTS
	public static final int SAVE_RATE = 20; // Rate in update cycles at which data dumps will be written to a session-specific-file (buffers will be emptied)
	
	public static final String TIME_FORMAT = "HH:mm:ss:SS yyyy-MM-dd";
	public static final String DATA_TERMINATOR = "[NEXT]";
	
	// VARIABLES
	private Controller controller;
	
	private boolean debugOutput; // Shall data be dumped to console, too? Enabled per default, can be set to false.
	
	private StringBuffer windData;
	private String lastWind;
	
	private StringBuffer gpsData;
	private String lastGps;
	
	private StringBuffer compassData;
	private String lastCompass;
	
	private boolean dumpWindToConsole = true; // If true new wind-data will be printed to console
	private boolean dumpGPSToConsole = true; // If true new GPS-data will be printed to console
	private boolean dumpCompassToConsole = true; // If true new compass-data will be printed to console
	
	private int dumpCount;
	
	public UferLogger(Controller controller) {
		this.controller = controller;
		
		this.windData = new StringBuffer("");
		this.lastWind = "";
		
		this.gpsData = new StringBuffer("");
		this.lastGps = "";
		
		this.compassData = new StringBuffer("");
		this.lastCompass = "";
		
		this.dumpCount = 0;
		
		this.debugOutput = false;
	}
	
	/**
	 * Dumps all data.
	 */
	public void dumpAll() {
		dumpWind();
		dumpGPS();
		dumpCompass();
	}
	
	private void dumpWind() {
		String data = "";
		
		data += "[WIND]\n";
		data += "No wind data available.\n";
		data += "Timestamp: " + currentTime() + "\n";
		data += DATA_TERMINATOR + "\n";
		
		if (debugOutput) System.out.print(data);
		lastWind = data;
		
		windData.append(data);
	}
	
	private void dumpGPS() {
		String data = "";
		
		data += "[GPS]\n";
		data += "Lat: " + controller.getGps().getPosition().getLatitude() + " Lon: " + controller.getGps().getPosition().getLatitude() + " Sat: " + controller.getGpsSatelites() + "\n";
		data += "Timestamp: " + currentTime() + "\n";
		data += DATA_TERMINATOR + "\n";
		
		if (debugOutput) System.out.print(data);
		lastGps = data;
		
		gpsData.append(data);
	}
	
	private void dumpCompass() {
		String data = "";
		
		data += "[COMPASS]\n";
		data += "Azimuth: " + controller.getCompass().getCompass().getAzimuth() + "° Acceleration: " + controller.getCompass().getCompass().getAcceleration() + " kn \n";
		data += "Pitch: " + controller.getCompass().getCompass().getPitch() + "° Roll: " + controller.getCompass().getCompass().getRoll() + "°\n";
		data += "Timestamp: " + currentTime() + "\n";
		data += DATA_TERMINATOR + "\n";
		
		if (debugOutput) System.out.print(data);
		lastCompass = data;
		
		compassData.append(data);
	}
	
	/**
	 * Advances the internal dumpCount by 1. If dumpCount >= SAVE_RATE all buffers will be written to files (not yet implemented) and then emptied. 
	 */
	public void advanceDumpCount() {
		this.dumpCount++;
		if (this.dumpCount >= SAVE_RATE) {
			// TODO dumpToFile
			compassData = new StringBuffer("");
			gpsData = new StringBuffer("");
			windData = new StringBuffer("");
			dumpCount = 0;
		}
	}
	
	public static String currentTime() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
	    return sdf.format(cal.getTime());
	}
	
	public void setDebugOutput(boolean mode) {
		this.debugOutput = mode;
	}

	public String getLastWind() {
		return lastWind;
	}

	public String getLastGps() {
		return lastGps;
	}

	public String getLastCompass() {
		return lastCompass;
	}
	
	public StringBuffer getWindData() {
		return windData;
	}

	public StringBuffer getGpsData() {
		return gpsData;
	}

	public StringBuffer getCompassData() {
		return compassData;
	}

}
