package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhb.sairo.data.Data.SensorData;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class LogWindData extends SensorData{

	private Date timeStamp;
	private String timeStampString;
	private int direction;
	private double speed;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

	public LogWindData(Date timeStamp, int direction,
			double speed) {
		this.timeStamp = timeStamp;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
		this.direction = direction;
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

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
