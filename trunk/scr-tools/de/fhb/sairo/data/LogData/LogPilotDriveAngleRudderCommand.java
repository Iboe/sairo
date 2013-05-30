package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhb.sairo.data.Data.SensorData;

/***
 * Thias class represents a rudder command from pilot module with saving the rudderPosition and timestamp
 * @author Tobias Koppe
 *
 */
public class LogPilotDriveAngleRudderCommand extends SensorData{

	private Date timeStamp;
	private float rudderPosition;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public LogPilotDriveAngleRudderCommand(Date timeStamp, float rudderPosition) {
		this.timeStamp = timeStamp;
		this.rudderPosition = rudderPosition;
		this.timeStampString = simpleDateFormat.format(getTimeStamp());
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public float getRudderPosition() {
		return rudderPosition;
	}
	public void setRudderPosition(float rudderPosition) {
		this.rudderPosition = rudderPosition;
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
		sb.append(this.getClass().getName());
		sb.append("(" + this.getTimeStamp() +")");
		sb.append("(" + this.getRudderPosition()+")");
		return sb.toString();
	}
	
}
