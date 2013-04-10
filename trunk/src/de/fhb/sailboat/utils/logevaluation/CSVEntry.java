package de.fhb.sailboat.utils.logevaluation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class CSVEntry {

	//Zeitstempel,Kompasskurs Azimuth,Differenz SimplePIDController, 
	//Rudderposition SimplePIDController,Pilot Rudderposition, 
	//AKSEN Winkel Rudderposition
	
	private static DateFormat dFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
	
	private Date timeStamp;
	private long time; //Zeit seit 01.01.1970
	private String compassAzimuth;
	private String simplePidDiff;
	private String simplePidRudderPos;
	private String pilotRudderPos;
	private String aksenRudderAngle;
	
	public CSVEntry(Date timeStamp, long time,String compassAzimuth,
			String simplePidDiff, String simplePidRudderPos,
			String pilotRudderPos, String aksenRudderAngle) {
		this.timeStamp = timeStamp;
		this.time = time;
		this.compassAzimuth = compassAzimuth;
		this.simplePidDiff = simplePidDiff;
		this.simplePidRudderPos = simplePidRudderPos;
		this.pilotRudderPos = pilotRudderPos;
		this.aksenRudderAngle = aksenRudderAngle;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(dFormate.format(getTimeStamp()));
		sb.append(";");
		sb.append(time);
		sb.append(";");
		sb.append(compassAzimuth);
		sb.append(";");
		sb.append(simplePidDiff);
		sb.append(";");
		sb.append(simplePidRudderPos);
		sb.append(";");
		sb.append(pilotRudderPos);
		sb.append(";");
		sb.append(aksenRudderAngle);
		sb.append(System.getProperty("line.separator"));
		return sb.toString();
	}
	
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getCompassAzimuth() {
		return compassAzimuth;
	}
	public void setCompassAzimuth(String compassAzimuth) {
		this.compassAzimuth = compassAzimuth;
	}
	public String getSimplePidDiff() {
		return simplePidDiff;
	}
	public void setSimplePidDiff(String simplePidDiff) {
		this.simplePidDiff = simplePidDiff;
	}
	public String getSimplePidRudderPos() {
		return simplePidRudderPos;
	}
	public void setSimplePidRudderPos(String simplePidRudderPos) {
		this.simplePidRudderPos = simplePidRudderPos;
	}
	public String getPilotRudderPos() {
		return pilotRudderPos;
	}
	public void setPilotRudderPos(String pilotRudderPos) {
		this.pilotRudderPos = pilotRudderPos;
	}
	public String getAksenRudderAngle() {
		return aksenRudderAngle;
	}
	public void setAksenRudderAngle(String aksenRudderAngle) {
		this.aksenRudderAngle = aksenRudderAngle;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
