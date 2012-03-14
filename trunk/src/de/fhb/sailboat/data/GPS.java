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

	/**
	 * Constructs a new instance with the values handed over.
	 * 
	 * @param latitude the value for latitude
	 * @param longitude the value for longitude
	 */
	public GPS(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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
