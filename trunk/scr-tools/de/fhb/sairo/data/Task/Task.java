package de.fhb.sairo.data.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.CompassCourseList;
import de.fhb.sairo.data.pidControllerStateList;
import de.fhb.sairo.data.Data.PidControllerState;
import de.fhb.sairo.data.LogData.LogCompassCourse;
import de.fhb.sairo.logAnalyze.LoadCpuPerformance;
import de.fhb.sairo.logAnalyze.LoadPidController;
import de.fhb.sairo.logAnalyze.filter;

public class Task {

	private ArrayList<String> log;
	private ArrayList<String> pidLog;
	private ArrayList<Double> cpuPerformance;
	
	private Date startTime;
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	
	private String taskDescription;
	private String taskArguments;
	
	private CompassCourseList compassCourseList;
	private ArrayList<PidControllerState> pidList;
	
	public Task(String pTaskDescription, String pTaskArguments){
		this.taskDescription=pTaskDescription;
		this.taskArguments = pTaskArguments;
		this.log = new ArrayList<String>();
		this.compassCourseList = new CompassCourseList();
		cpuPerformance = new ArrayList<Double>();
		pidList = new pidControllerStateList();
	}

	public void extractData(){
		extractCompassCourseList();
		extractPidControllerLog();
		cpuPerformance=LoadCpuPerformance.loadCpuPerformanceData(log);
	}
	
	private void extractPidControllerLog(){
		if(this.pidLog!=null){this.pidLog.clear();}
		this.pidLog = LoadPidController.loadPidControllerLog(this.log);
		pidList=LoadPidController.extractPidControllerData(pidLog);
	}
	
	public void extractCompassCourseList(){
		System.out.println("Try to extract compasscourse elements from tasklog");
		String zeile=null;
		for(int i=0;i<this.log.size();i++){
			zeile=log.get(i);
			if(zeile.contains(LogTextblocks.compassThreadName)){
				Date d=filter.filterTimestamp(zeile);
				int startAzimuth = zeile.indexOf(LogTextblocks.compassAzimuthMark)+LogTextblocks.compassAzimuthMark.length();
				int endAzimuth = zeile.indexOf(LogTextblocks.compassPitchMark)-2;
				String azimuth = zeile.substring(startAzimuth,endAzimuth);
				compassCourseList.add(new LogCompassCourse(Float.valueOf(azimuth), d));
		}
		}
		System.out.println("Found " + compassCourseList.size() + " compasscourse elements in this task");
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
	
	public String getStartTimeString(){
		return simpleDateFormat.format(getStartTime());
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public ArrayList<String> getLog() {
		return log;
	}

	public void setLog(ArrayList<String> log) {
		this.log = log;
		extractPidControllerLog();
		extractCompassCourseList();
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

	public ArrayList<PidControllerState> getPidList() {
		return pidList;
	}

	public void setPidList(de.fhb.sairo.data.pidControllerStateList pidList) {
		this.pidList = pidList;
	}

	/**
	 * @return the pidLog
	 */
	public ArrayList<String> getPidLog() {
		return pidLog;
	}

	/**
	 * @param pidLog the pidLog to set
	 */
	public void setPidLog(ArrayList<String> pidLog) {
		this.pidLog = pidLog;
	}
	
	
	
}
