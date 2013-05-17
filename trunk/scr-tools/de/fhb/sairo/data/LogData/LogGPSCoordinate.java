package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhb.sairo.logAnalyze.logTextblocks;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class LogGPSCoordinate {

	private double latitude;
	private double longitude;
	private int satelliteCount;
	private double speed;
	
	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

	public LogGPSCoordinate(double latitude, double longitude, int satelliteCount,
			double speed, Date timeStamp) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.satelliteCount = satelliteCount;
		this.speed = speed;
		this.timeStamp = timeStamp;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp: " + this.timeStampString+ " ");
		sb.append(logTextblocks.gpsSensorLatitude + ": " + this.latitude + " ");
		sb.append(logTextblocks.gpsSensorLongitude + ": " + this.longitude + " ");
		sb.append(logTextblocks.gpsSensorSatelites + ": " + this.satelliteCount + " ");
		sb.append(logTextblocks.gpsSensorSpeed + ": " + this.speed);
		return sb.toString();
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
