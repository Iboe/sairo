package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.CompassModelImpl;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.GPSModelImpl;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WindModelImpl;



/**
 * This class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 *
 */
public class Model {
	
	// CONSTANTS
	
	// VARIABLES
	private WindModel wind;
	
	private CompassModel compass;
	
	private GPSModel gps;
	private double gpsPrecision; // Used to value the reliability of GPS values, currently cosmetic and unused (ranged from 0.0 for 0% relaiability to 1.0 fpr 100%)
	
	// for the planned exercise
	private ArrayList<MapMarker> markerList;
	
	public Model() {
		this.wind = new WindModelImpl();
		this.compass = new CompassModelImpl();
		this.compass.setCompass(new Compass(170,0,0));
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
	public void setWind(WindModel wind) {
		this.wind = wind;
	}
	
	public WindModel getWind() {
		return this.wind;
	}

	// GPS
	public void setGps(GPSModel gps) {
		this.gps = gps;
	}
	
	public GPSModel getGps() {
		return gps;
	}

	public double getGpsPrecision() {
		return gpsPrecision;
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
