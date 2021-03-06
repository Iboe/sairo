package de.fhb.sairo.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import de.fhb.sairo.data.CompassCourseList;
import de.fhb.sairo.data.GpsDataList;
import de.fhb.sairo.data.MissionList;
import de.fhb.sairo.data.WindDatalist;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class Model extends Observable{

	Logger log = Logger.getLogger(Model.class);
	
	private File logFile;
	private ArrayList<MissionList> missions;
	private ArrayList<String> aksenLog;
	private GpsDataList gpsDataList;
	private CompassCourseList compassCourseList;
	private WindDatalist windDatalist;
	private MissionList missionList;
	private DefaultListModel<String> listMissionsModel;
	private DefaultListModel<String> logfileModel;
	
	private JFreeChart guiChart;
	
	public String logFileModelToString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<logfileModel.size();i++){
			sb.append(logfileModel.get(i));
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	public Model(){
		log.trace("Model initialized");
		this.missions = new ArrayList<MissionList>();
		this.gpsDataList = new GpsDataList();
		this.compassCourseList = new CompassCourseList();
		this.windDatalist = new WindDatalist();
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
	}

	/**
	 * @return the gpsDataList
	 */
	public GpsDataList getGpsDataList() {
		return gpsDataList;
	}

	public CompassCourseList getCompassCourseList() {
		return compassCourseList;
	}

	public void setCompassCourseList(CompassCourseList compassCourseList) {
		this.compassCourseList = compassCourseList;
		update();
	}

	/**
	 * @param gpsDataList the gpsDataList to set
	 */
	public void setGpsDataList(GpsDataList gpsDataList) {
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

	public WindDatalist getWindDatalist() {
		return windDatalist;
	}

	public void setWindDatalist(WindDatalist windDatalist) {
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
	
	public ArrayList<String> getAksenLog() {
		return aksenLog;
	}

	public void setAksenLog(ArrayList<String> aksenLog) {
		this.aksenLog = aksenLog;
	}

	public void update(){
		setChanged();
		notifyObservers();
	}
}
