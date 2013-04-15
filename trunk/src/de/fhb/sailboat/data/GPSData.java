package de.fhb.sailboat.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class GPSData {

	private double latitude;
	private double longitude;
	private int satelliteCount;
	private double speed;
	
	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

	public GPSData(double latitude, double longitude, int satelliteCount,
			double speed, Date timeStamp) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.satelliteCount = satelliteCount;
		this.speed = speed;
		this.timeStamp = timeStamp;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getSatelliteCount() {
		return satelliteCount;
	}

	public void setSatelliteCount(int satelliteCount) {
		this.satelliteCount = satelliteCount;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStampString() {
		return timeStampString;
	}

	public void setTimeStampString(String timeStampString) {
		this.timeStampString = timeStampString;
	}
	
}
