package de.fhb.sairo.data.Data;

import java.util.Date;

import de.fhb.sairo.logAnalyze.logTextblocks;

// TODO: Auto-generated Javadoc
/**
 * *
 * This class represents an gps coordinate with all values contained: latitude, longitude, satellites count and speed.
 *
 * @author Tobias Koppe
 */
public class GPSCoordinate {

	/** The latitude. */
	private double latitude;
	
	/** The longitude. */
	private double longitude;
	
	/** The satellite count. */
	private int satelliteCount;
	
	/** The speed. */
	private double speed;

	
	/**
	 * Instantiates a new GPS coordinate.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param satelliteCount the satellite count
	 * @param speed the speed
	 * @param timeStamp the time stamp
	 */
	public GPSCoordinate(double latitude, double longitude, int satelliteCount,
			double speed, Date timeStamp) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.satelliteCount = satelliteCount;
		this.speed = speed;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(logTextblocks.gpsSensorLatitude + ": " + this.latitude + " ");
		sb.append(logTextblocks.gpsSensorLongitude + ": " + this.longitude + " ");
		sb.append(logTextblocks.gpsSensorSatelites + ": " + this.satelliteCount + " ");
		sb.append(logTextblocks.gpsSensorSpeed + ": " + this.speed);
		return sb.toString();
	}
	
	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets the satellite count.
	 *
	 * @return the satellite count
	 */
	public int getSatelliteCount() {
		return satelliteCount;
	}

	/**
	 * Sets the satellite count.
	 *
	 * @param satelliteCount the new satellite count
	 */
	public void setSatelliteCount(int satelliteCount) {
		this.satelliteCount = satelliteCount;
	}

	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
}
