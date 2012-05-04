package de.fhb.sailboat.data;

/**
 * Data object representing a GPS coordinate, containing the information gathered 
 * by a GPS device.
 *  
 * @author hscheel
 *
 */
public class GPS {

	private final double latitude;
	private final double longitude;
	private Integer nrSatelites = null;


	/**
	 * Initialization constructor.<br> 
	 * Creates a new GPS object with a given latitude and longitude.
	 * 
	 * @param latitude The latitude of the GPS object
	 * @param longitude The longitude of the GPS object
	 */
	public GPS(double latitude, double longitude){
		
		this(latitude,longitude,-1);
	}
	
	/**
	 * Initialization constructor.<br> 
	 * Creates a new GPS object with a given latitude, longitude and the number of satellites that were involved when obtaining the values.
	 * 
	 * @param latitude The latitude of the GPS object
	 * @param longitude The longitude of the GPS object
	 */
	public GPS(double latitude, double longitude, int nrSatelites){
		this.latitude = latitude;
		this.longitude = longitude;
		this.nrSatelites = nrSatelites;
	}

	/**
	 * Checks if the specified GPS object has the same coordinates as
	 * this object.
	 * 
	 * @param other the GPS object to compare to
	 * @return false if they have not the same coordinates or the specified 
	 * object is null 
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
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public int getSatelites()
	{
		return nrSatelites;
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
		return "GPS [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
