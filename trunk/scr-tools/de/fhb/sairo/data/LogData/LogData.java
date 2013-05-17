package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogData {

	private Date timeStamp;
	private String timeStampString;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public LogData() {
		
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTimeStampString() {
		return simpleDateFormat.format(timeStamp);
	}

	public void setTimeStampString(String timeStampString) {
		this.timeStampString = timeStampString;
	}

}
