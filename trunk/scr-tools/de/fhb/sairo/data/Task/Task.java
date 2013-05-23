package de.fhb.sairo.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.CompassCourseList;
import de.fhb.sairo.data.LogData.LogCompassCourse;
import de.fhb.sairo.logAnalyze.LogTextblocks;
import de.fhb.sairo.logAnalyze.filter;

public class Task {

	private ArrayList<String> log;
	
	private Date startTime;
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	private String taskDescription;
	private String taskArguments;
	
	private CompassCourseList compassCourseList;
	
	public Task(String pTaskDescription){
		this.taskDescription=pTaskDescription;
		this.log = new ArrayList<String>();
		this.compassCourseList = new CompassCourseList();
	}

	public void extractCompassCourseList(){
		String zeile=null;
		for(int i=0;i<this.log.size();i++){
			zeile=log.get(i);
			if(zeile.contains(LogTextblocks.compassThreadName)){
				Date d=filter.filterTimestamp(zeile);
				int startAzimuth = zeile.indexOf(LogTextblocks.compassAzimuthMark)+LogTextblocks.compassAzimuthMark.length();
				int endAzimuth = zeile.indexOf(LogTextblocks.compassPitchMark)-1;
				String azimuth = zeile.substring(startAzimuth,endAzimuth);
				compassCourseList.add(new LogCompassCourse(Float.valueOf(azimuth), d));
		}
		}
	}
	
	/**
	 * @return the taskDescription
	 */
	public String getTaskDescription() {
		return taskDescription;
	}

	/**
	 * @param taskDescription the taskDescription to set
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	/**
	 * Gets the task arguments.
	 *
	 * @return the task arguments
	 */
	public String getTaskArguments() {
		return taskArguments;
	}

	/**
	 * Sets the task arguments.
	 *
	 * @param taskArguments the new task arguments
	 */
	public void setTaskArguments(String taskArguments) {
		this.taskArguments = taskArguments;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public ArrayList<String> getLog() {
		return log;
	}

	public void setLog(ArrayList<String> log) {
		this.log = log;
	}

	public String getTaskLog(){
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<this.getLog().size();i++){
			sb.append(getLog().get(i));
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	
	
	public CompassCourseList getCompassCourseList() {
		return compassCourseList;
	}

	public void setCompassCourseList(CompassCourseList compassCourseList) {
		this.compassCourseList = compassCourseList;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getTaskDescription());
		sb.append(",");
		sb.append("Logentries:" +  this.getLog().size());
		return sb.toString();
	}
}
