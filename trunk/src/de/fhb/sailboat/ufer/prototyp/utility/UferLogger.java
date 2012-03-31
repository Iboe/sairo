package de.fhb.sailboat.ufer.prototyp.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.fhb.sailboat.ufer.prototyp.Controller;
import de.fhb.sailboat.ufer.prototyp.Model;

/**
 * 
 * @author Patrick Rutter
 *
 */
public class UferLogger {
	
	// CONSTANTS
	public static final String TIME_FORMAT = "HH:mm:ss:ms yyyy-MM-dd";
	public static final String DATA_TERMINATOR = "[NEXT]";
	
	// VARIABLES
	private Controller controller;
	
	private StringBuffer windData;
	private StringBuffer gpsData;
	private StringBuffer compassData;
	
	private boolean dumpWindToConsole = true; // If true new wind-data will be printed to console
	private boolean dumpGPSToConsole = true; // If true new GPS-data will be printed to console
	private boolean dumpCompassToConsole = true; // If true new compass-data will be printed to console
	
	public UferLogger(Controller controller) {
		this.controller = controller;
		
		this.windData = new StringBuffer("");
		this.gpsData = new StringBuffer("");
		this.compassData = new StringBuffer("");
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
		// TODO add wind
	}
	
	private void dumpGPS() {
		String data = "";
		
		data += "[GPS]\n";
		data += "Lat: " + controller.getGps().getPosition().getLatitude() + " Lon: " + controller.getGps().getPosition().getLatitude() + " Prc: " + controller.getGpsPrecision() + "\n";
		data += "Timestamp: " + currentTime() + "\n";
		data += DATA_TERMINATOR + "\n";
		
		System.out.print(data);
		
		gpsData.append(data);
	}
	
	private void dumpCompass() {
		
	}
	
	public static String currentTime() {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
	    return sdf.format(cal.getTime());
	}

}
