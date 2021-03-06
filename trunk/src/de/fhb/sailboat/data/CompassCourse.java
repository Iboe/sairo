package de.fhb.sailboat.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * This class represents the azimuth state of compasscourse with saving
 * the timestamp
 * @author Tobias Koppe
 */
public class CompassCourse {

	private float compassCourseAzimuth;
	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public CompassCourse (float pCompassCourse, Date pTimeStamp){
		this.compassCourseAzimuth = pCompassCourse;
		this.timeStamp = pTimeStamp;
		this.timeStampString=simpleDateFormat.format(getTimeStamp());
	}

	public float getCompassCourseAzimuth() {
		return compassCourseAzimuth;
	}

	public void setCompassCourseAzimuth(float compassCourseAzimuth) {
		this.compassCourseAzimuth = compassCourseAzimuth;
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
		sb.append("CompassCourse azimuth[" + this.compassCourseAzimuth + "] timestamp["+simpleDateFormat.format(getTimeStamp())+"]");
		return sb.toString();
	}
}
