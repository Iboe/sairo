package de.fhb.sailboat.data;

import java.io.Serializable;

/**
 * Data object representing a GPS coordinate, containing the information
 * gathered by a GPS device.
 * 
 * @author Helge Scheel, Michael Kant
 * 
 */
public class GPS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;
	private int satelliteCount;
	private double speed;



	/**
	 * Initialization constructor.<br>
	 * Creates a new GPS object with a given latitude and longitude.
	 * 
	 * @param latitude the latitude of the GPS object
	 * @param longitude the longitude of the GPS object
	 */
	public GPS(double latitude, double longitude) {
		this(latitude, longitude, -1);
	}

	/**
	 * Initialization constructor.<br>
	 * Creates a new GPS object with a given latitude, longitude and the number<br>
	 * of satellites that were involved when obtaining the values.
	 * 
	 * @param latitude the latitude of the GPS object
	 * @param longitude the longitude of the GPS object
	 * @param satelliteCount number of satellites in range
	 */
	public GPS(double latitude, double longitude, int satelliteCount) {
		this(latitude, longitude, satelliteCount, 0);
	}
	
	/**
	 * Initialization constructor.<br>
	 * Creates a new GPS object with a given latitude, longitude and the <br> 
	 * approximated speed.
	 * 
	 * @param latitude the latitude of the GPS object
	 * @param longitude the longitude of the GPS object
	 * @param speed the approximated speed
	 */
	public GPS(double latitude, double longitude, double speed) {
		this(latitude, longitude, -1, speed);
	}
	
	/**
	 * Initialization constructor.<br>
	 * Creates a new GPS object with a given latitude, longitude, the number<br>
	 * of satellites that were involved when obtaining the values and the approximated speed.
	 * 
	 * @param latitude the latitude of the GPS object
	 * @param longitude the longitude of the GPS object
	 * @param nrSatelites the number of satellites, this GPS data originates from
	 * @param speed the approximated speed
	 */
	public GPS(double latitude, double longitude, int nrSatelites, double speed) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.satelliteCount = nrSatelites;
		this.speed = speed;
	}

	/**
	 * Checks if the specified GPS object has the same coordinates as this
	 * object.
	 * 
	 * @param other the GPS object to compare to
	 * @return false if they have not the same coordinates or the specified object is null
	 */
	public boolean hasEqualCoordinates(GPS other) {
		if (other == null) {
			return false;
		} else if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude)) {
			return false;
		} else if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GPS other = (GPS) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		return true;
	}

	/**
	 * Returns the latitude of the GPS coordinate. 
	 * @return The latitude of the GPS coordinate.
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Returns the longitude of the GPS coordinate. 
	 * @return The longitude of the GPS coordinate.
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Returns the number of satellites where this GPS coordinate originated from. 
	 * @return The number of satellites where this GPS coordinate originated from.
	 */
	public int getSatelites() {
		return satelliteCount;
	}

	/**
	 * Returns the approximated speed.
	 * @return The approximated speed.
	 */
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "GPS [latitude: " + latitude + ", longitude: " + longitude
				+ ", satelites: " + satelliteCount + ", speed: " + speed + "]";
	}
}
