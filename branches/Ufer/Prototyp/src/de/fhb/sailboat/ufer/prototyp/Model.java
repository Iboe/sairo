package de.fhb.sailboat.ufer.prototyp;


/**
 * This class serves as storage for all data relevant to be served to and displayed by View.
 * @author Patrick Rutter
 * @lastUpdate 03.02.2012
 *
 */
public class Model {
	
	// CONSTANTS
	
	// VARIABLES
	private double compTemperature;
	private int compDirection;
	
	private int windDirection;
	private int windVelocity;
	
	private double gpsLongitude;
	private double gpsLatitude;
	private double gpsPrecision;
	
	// for the planned exercise
	private double gpsTargetLongitude;
	private double gpsTargetLatitude;
	
	public Model() {
		this.compTemperature = 0.0f;
		this.compDirection = 0;
		this.windDirection = 0;
		this.windVelocity = 0;
		this.gpsLatitude = 0;
		this.gpsLongitude = 0;
		this.gpsPrecision = 0.0f;
		this.gpsTargetLatitude = 0;
		this.gpsTargetLongitude = 0;
	}

	// Getter/ Setter
	public double getCompTemperature() {
		return compTemperature;
	}

	public void setCompTemperature(double compTemperature) {
		this.compTemperature = compTemperature;
	}

	public int getCompDirection() {
		return compDirection;
	}

	public void setCompDirection(int compDirection) {
		this.compDirection = compDirection;
	}

	public int getWindDirection() {
		return windDirection;
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

	public double getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public double getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public double getGpsPrecision() {
		return gpsPrecision;
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
	
	public double getGpsTargetLongitude() {
		return gpsTargetLongitude;
	}

	public void setGpsTargetLongitude(double gpsTargetLongitude) {
		this.gpsTargetLongitude = gpsTargetLongitude;
	}

	public double getGpsTargetLatitude() {
		return gpsTargetLatitude;
	}

	public void setGpsTargetLatitude(double gpsTargetLatitude) {
		this.gpsTargetLatitude = gpsTargetLatitude;
	}
	
}
