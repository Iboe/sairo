package de.fhb.sairo.data.LogData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.TaskList;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class LogMission {

	private String description;
	private Date startTime;
	private ArrayList<String> logFromMission;
	private TaskList taskList;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public LogMission(String pDescription) {
		this.description = pDescription;
		this.logFromMission = new ArrayList<String>();
		this.taskList = new TaskList();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getLogFromMission() {
		return logFromMission;
	}

	public void setLogFromMission(ArrayList<String> logFromMission) {
		this.logFromMission = logFromMission;
	}
	
	public String getStartTimeString(){
		return simpleDateFormat.format(startTime);
	}
	
	public TaskList getTaskList() {
		return taskList;
	}

	public void setTaskList(TaskList taskList) {
		this.taskList = taskList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.getDescription());
		return sb.toString();
	}
	
}
