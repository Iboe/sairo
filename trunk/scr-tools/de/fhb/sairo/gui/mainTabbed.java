package de.fhb.sairo.gui;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;

public class mainTabbed extends JFrame {
	
	/**
	 * Menuitems
	 */
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemOpenFile;
	private JMenu menuLoad;
	private JMenuItem menuItemLoadGps;
	private JMenu menuSave;
	private JMenuItem menuItemSaveGpsKml;
	private JMenuItem menuItemSegmentLog;
	private JMenuItem menuItemLoadCompassData;
	private JMenu menuChart;
	private JMenuItem menuItemCompassCourseTimeChart;
	private JMenuItem menuItemLoadAllData;
	private JMenuItem menuItemLoadMissionData;
	private JMenuItem menuItemSaveCompassCourseWithDesiredAngleToCsv;
	
	/**
	 * Panels
	 */
	private completeLogViewer logViewer;
	private missionTaskInfo missionTaskInfo;
	private ChartPanel panel;
	
	public mainTabbed() {
		initalizeMenu();
		logViewer = new completeLogViewer();
		missionTaskInfo = new missionTaskInfo();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Missions/Tasks",missionTaskInfo);
		tabbedPane.addTab("Logfile", logViewer);
		tabbedPane.setToolTipTextAt(1, "Shows the complete loaded logfile");
		
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
//		getContentPane().add(panel, BorderLayout.CENTER);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	private void initalizeMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemOpenFile = new JMenuItem("open file");
		menuFile.add(menuItemOpenFile);
		
		menuLoad = new JMenu("Load");
		menuBar.add(menuLoad);
		
		menuItemLoadGps = new JMenuItem("GPS Data");
		menuLoad.add(menuItemLoadGps);
		
		menuItemLoadCompassData = new JMenuItem("Compass Data");
		menuLoad.add(menuItemLoadCompassData);
		
		menuItemLoadMissionData = new JMenuItem("Load Mission Data");
		menuLoad.add(menuItemLoadMissionData);
		
		menuItemLoadAllData = new JMenuItem("Load all data");
		menuLoad.add(menuItemLoadAllData);
		
		menuSave = new JMenu("Save");
		menuBar.add(menuSave);
		
		menuItemSaveGpsKml = new JMenuItem("Save GPS data to KML file");
		menuSave.add(menuItemSaveGpsKml);
		
		menuItemSegmentLog = new JMenuItem("segment log file");
		menuSave.add(menuItemSegmentLog);
		
		menuItemSaveCompassCourseWithDesiredAngleToCsv = new JMenuItem("compass course and desired angle to csv from task");
		menuSave.add(menuItemSaveCompassCourseWithDesiredAngleToCsv);
		
		menuChart = new JMenu("Chart");
		menuBar.add(menuChart);
		
		menuItemCompassCourseTimeChart = new JMenuItem("Compasscourse-Time chart");
		menuChart.add(menuItemCompassCourseTimeChart);
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenu getMenuFile() {
		return menuFile;
	}

	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}

	public JMenuItem getMenuItemOpenFile() {
		return menuItemOpenFile;
	}

	public void setMenuItemOpenFile(JMenuItem menuItemOpenFile) {
		this.menuItemOpenFile = menuItemOpenFile;
	}

	public JMenu getMenuLoad() {
		return menuLoad;
	}

	public void setMenuLoad(JMenu menuLoad) {
		this.menuLoad = menuLoad;
	}

	public JMenuItem getMenuItemLoadGps() {
		return menuItemLoadGps;
	}

	public void setMenuItemLoadGps(JMenuItem menuItemLoadGps) {
		this.menuItemLoadGps = menuItemLoadGps;
	}

	public JMenu getMenuSave() {
		return menuSave;
	}

	public void setMenuSave(JMenu menuSave) {
		this.menuSave = menuSave;
	}

	public JMenuItem getMenuItemSaveGpsKml() {
		return menuItemSaveGpsKml;
	}

	public void setMenuItemSaveGpsKml(JMenuItem menuItemSaveGpsKml) {
		this.menuItemSaveGpsKml = menuItemSaveGpsKml;
	}

	public JMenuItem getMenuItemSegmentLog() {
		return menuItemSegmentLog;
	}

	public void setMenuItemSegmentLog(JMenuItem menuItemSegmentLog) {
		this.menuItemSegmentLog = menuItemSegmentLog;
	}

	public JMenuItem getMenuItemLoadCompassData() {
		return menuItemLoadCompassData;
	}

	public void setMenuItemLoadCompassData(JMenuItem menuItemLoadCompassData) {
		this.menuItemLoadCompassData = menuItemLoadCompassData;
	}

	public JMenu getMenuChart() {
		return menuChart;
	}

	public void setMenuChart(JMenu menuChart) {
		this.menuChart = menuChart;
	}

	public JMenuItem getMenuItemCompassCourseTimeChart() {
		return menuItemCompassCourseTimeChart;
	}

	public void setMenuItemCompassCourseTimeChart(
			JMenuItem menuItemCompassCourseTimeChart) {
		this.menuItemCompassCourseTimeChart = menuItemCompassCourseTimeChart;
	}

	public JMenuItem getMenuItemLoadAllData() {
		return menuItemLoadAllData;
	}

	public void setMenuItemLoadAllData(JMenuItem menuItemLoadAllData) {
		this.menuItemLoadAllData = menuItemLoadAllData;
	}

	public JMenuItem getMenuItemLoadMissionData() {
		return menuItemLoadMissionData;
	}

	public void setMenuItemLoadMissionData(JMenuItem menuItemLoadMissionData) {
		this.menuItemLoadMissionData = menuItemLoadMissionData;
	}

	public JMenuItem getMenuItemSaveCompassCourseWithDesiredAngleToCsv() {
		return menuItemSaveCompassCourseWithDesiredAngleToCsv;
	}

	public void setMenuItemSaveCompassCourseWithDesiredAngleToCsv(
			JMenuItem menuItemSaveCompassCourseWithDesiredAngleToCsv) {
		this.menuItemSaveCompassCourseWithDesiredAngleToCsv = menuItemSaveCompassCourseWithDesiredAngleToCsv;
	}

	public completeLogViewer getLogViewer() {
		return logViewer;
	}

	public void setLogViewer(completeLogViewer logViewer) {
		this.logViewer = logViewer;
	}

	public missionTaskInfo getMissionTaskInfo() {
		return missionTaskInfo;
	}

	public void setMissionTaskInfo(missionTaskInfo missionTaskInfo) {
		this.missionTaskInfo = missionTaskInfo;
	}
}
