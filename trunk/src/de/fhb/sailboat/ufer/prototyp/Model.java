package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.CompassModelImpl;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.GPSModelImpl;



/**
 * This class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 * @lastUpdate 03.02.2012
 *
 */
public class Model {
	
	// CONSTANTS
	
	// VARIABLES
	private CompassModel compass;
	
	private int windDirection;
	private int windVelocity;
	
	private GPSModel gps;
	private double gpsPrecision; // Used to value the reliability of GPS values, currently cosmetic and unused (ranged from 0.0 for 0% relaiability to 1.0 fpr 100%)
	
	// for the planned exercise
	private ArrayList<MapMarker> markerList;
	
	public Model() {
		this.windDirection = 0;
		this.windVelocity = 0;
		
		this.compass = new CompassModelImpl();
		this.gps = new GPSModelImpl();
	}

	// Getter/ Setter
	// Compass
	public CompassModel getCompass() {
		return this.compass;
	}
	
	public void setCompass(CompassModel compass) {
		this.compass = compass;
	}

	// Wind
	public int getWindDirection() {
		return this.windDirection;
	}

	public void setWindDirection(int windDirection) {
		this.windDirection = windDirection;
	}

	public int getWindVelocity() {
		return windVelocity;
	}

	public void setWindVelocity(int windVelocity) {
		this.windVelocity = windVelocity;
	}

	// GPS
	public GPSModel getGps() {
		return gps;
	}

	public double getGpsPrecision() {
		return gpsPrecision;
	}
	
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}
	
	/**
	 * Helper method which should *ONLY* be used for testing/ debugging. Sets the GPS position manually and
	 * deletes local GPS History.
	 * @param position
	 */
	public void setGpsPosition(GPS position) {
		this.gps = new GPSModelImpl();
		gps.setPosition(position);
	}

	public void setGpsPrecision(double gpsPrecision) {
		if (gpsPrecision > 1.0f) {
			gpsPrecision = 1.0f;
		}
		else {
			if (gpsPrecision < 0.0f) gpsPrecision = 0.0f;
		}
		this.gpsPrecision = gpsPrecision;
	}

	public ArrayList<MapMarker> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(ArrayList<MapMarker> markerList) {
		this.markerList = markerList;
	}
	
	
	
}
