package de.fhb.sairo.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import de.fhb.sairo.data.MissionList;
import de.fhb.sairo.data.compassCourseList;
import de.fhb.sairo.data.gpsDataList;
import de.fhb.sairo.data.windDatalist;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class Model extends Observable{

	Logger log = Logger.getLogger(Model.class);
	
	private File logFile;
	private ArrayList<MissionList> missions;
	private gpsDataList gpsDataList;
	private compassCourseList compassCourseList;
	private windDatalist windDatalist;
	private MissionList missionList;
	private DefaultListModel<String> listMissionsModel;
	private DefaultListModel<String> logfileModel;
	
	private JFreeChart guiChart;
	
	public Model(){
		log.trace("Model initialized");
		this.missions = new ArrayList<MissionList>();
		this.gpsDataList = new gpsDataList();
		this.compassCourseList = new compassCourseList();
		this.windDatalist = new windDatalist();
		this.missionList = new MissionList();
		listMissionsModel = new DefaultListModel<String>();
		logfileModel = new DefaultListModel<String>();
	}

	/**
	 * @return the logFile
	 */
	public File getLogFile() {
		return logFile;
	}

	/**
	 * @param logFile the logFile to set
	 */
	public void setLogFile(File logFile) {
		this.logFile = logFile;
		log.debug(this.getClass().getName() + "setLogFile("+logFile+")");
	}

	/**
	 * @return the gpsDataList
	 */
	public gpsDataList getGpsDataList() {
		return gpsDataList;
	}

	public compassCourseList getCompassCourseList() {
		return compassCourseList;
	}

	public void setCompassCourseList(compassCourseList compassCourseList) {
		this.compassCourseList = compassCourseList;
		update();
	}

	/**
	 * @param gpsDataList the gpsDataList to set
	 */
	public void setGpsDataList(gpsDataList gpsDataList) {
		this.gpsDataList = gpsDataList;
		System.out.println("Got: " + gpsDataList.toString());
		update();
	}
	
	public JFreeChart getGuiChart() {
		return guiChart;
	}

	public void setGuiChart(JFreeChart guiChart) {
		this.guiChart = guiChart;
	}

	public ArrayList<MissionList> getMissions() {
		return missions;
	}

	public void setMissions(ArrayList<MissionList> missions) {
		this.missions = missions;
		update();
	}

	public windDatalist getWindDatalist() {
		return windDatalist;
	}

	public void setWindDatalist(windDatalist windDatalist) {
		this.windDatalist = windDatalist;
		update();
	}
	
	public MissionList getMissionList() {
		return missionList;
	}

	public void setMissionList(MissionList missionList) {
		this.missionList = missionList;
		update();
	}

	public DefaultListModel<String> getListMissionsModel() {
		return listMissionsModel;
	}

	public void setListMissionsModel(DefaultListModel<String> listMissionsModel) {
		this.listMissionsModel = listMissionsModel;
	}

	
	
	public DefaultListModel<String> getLogfileModel() {
		return logfileModel;
	}

	public void setLogfileModel(DefaultListModel<String> logfileModel) {
		this.logfileModel = logfileModel;
	}

	public void update(){
		log.trace("update for gui called");
		setChanged();
		notifyObservers();
	}
}
