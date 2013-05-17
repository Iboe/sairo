package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * This class represents a simplepidcontroller state with saving the difference, rudderposition and timestamp
 * @author Tobias Koppe
 *
 */
public class LogSimplePidControllerState {

	private double difference;
	private double rudderPos;
	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public LogSimplePidControllerState(double difference, double rudderPos,
			Date timeStamp) {
		this.difference = difference;
		this.rudderPos = rudderPos;
		this.timeStamp = timeStamp;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
	}

	public double getDifference() {
		return difference;
	}

	public void setDifference(double difference) {
		this.difference = difference;
	}

	public double getRudderPos() {
		return rudderPos;
	}

	public void setRudderPos(double rudderPos) {
		this.rudderPos = rudderPos;
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
