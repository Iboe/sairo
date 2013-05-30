package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhb.sairo.data.Data.SensorData;

/***
 * This class represents a rudder positions at a time with saving the angle and timestamp
 * @author Tobias Koppe
 *
 */
public class LogRudderPosition extends SensorData{
	
	private int angle;
	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public LogRudderPosition(int angle, Date timeStamp) {
		this.angle = angle;
		this.timeStamp = timeStamp;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
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

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Rudderposition angle[" + this.getAngle() + "] timestamp["+simpleDateFormat.format(getTimeStamp())+"]");
		return sb.toString();
	}
}
