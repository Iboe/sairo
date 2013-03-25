package de.fhb.sailboat.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * This class represents a rudder positions at a time
 * @author Tobias Koppe
 *
 */
public class RudderPosition {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	private int angle;
	private Date timeStamp;
	
	public RudderPosition(int angle, Date timeStamp) {
		this.angle = angle;
		this.timeStamp = timeStamp;
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
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Rudderposition angle[" + this.getAngle() + "] timestamp["+simpleDateFormat.format(getTimeStamp())+"]");
		return sb.toString();
	}
}
