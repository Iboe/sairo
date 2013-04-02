package de.fhb.sailboat.data;

import java.util.Date;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class PilotDriveAngleRudderCommand {

	private Date timeStamp;
	private float rudderPosition;
	
	public PilotDriveAngleRudderCommand(Date timeStamp, float rudderPosition) {
		this.timeStamp = timeStamp;
		this.rudderPosition = rudderPosition;
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
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getName());
		sb.append("(" + this.getTimeStamp() +")");
		sb.append("(" + this.getRudderPosition()+")");
		return sb.toString();
	}
	
}
