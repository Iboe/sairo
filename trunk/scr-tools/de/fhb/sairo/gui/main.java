package de.fhb.sairo.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.JTextArea;

public class main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

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
	
	private JPanel panel;
	private JLabel lblGpsData;
	private JLabel lblGpsDataValue;
	private JLabel lblCompassData;
	private JLabel lblCompassDataValue;
	private JLabel lblMissionData;
	private JLabel lblMissionDataValue;
	private JPanel panel_1;
	private JLabel lblListMissions;
	private JLabel lblPIDControllerData;
	private JLabel lblPIDControllerDataValue;
	private JLabel lblWindsensorData;
	private JLabel lblWindsensorDataValue;
	private JList listMissions;
	private JScrollPane scrollPane_missions;
	private JList listMissionInfo;
	private JScrollPane scrollPane_tasks;
	private JList listTasks;
	private JList listTaskInfo;
	private JPanel panel_2;
	private JTextArea txtLogArea;
	private JScrollPane scrollPaneTxtLogArea;
	
	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 450, 300);
		
		initalizeMenu();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(10, 4, 0, 0));
		
		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblGpsData = new JLabel("GPS Data: ");
		panel.add(lblGpsData);
		
		lblGpsDataValue = new JLabel("n/a");
		panel.add(lblGpsDataValue);
		
		lblCompassData = new JLabel("Compass Data: ");
		panel.add(lblCompassData);
		
		lblCompassDataValue = new JLabel("n/a");
		panel.add(lblCompassDataValue);
		
		lblPIDControllerData = new JLabel("PID Controller Data");
		panel.add(lblPIDControllerData);
		
		lblPIDControllerDataValue = new JLabel("n/a");
		panel.add(lblPIDControllerDataValue);
		
		lblWindsensorData = new JLabel("Windsensor Data: ");
		panel.add(lblWindsensorData);
		
		lblWindsensorDataValue = new JLabel("n/a");
		panel.add(lblWindsensorDataValue);
		
		lblMissionData = new JLabel("Mission Data: ");
		panel.add(lblMissionData);
		
		lblMissionDataValue = new JLabel("n/a");
		panel.add(lblMissionDataValue);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(100, 400));
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 5, 0, 0));
		
		lblListMissions = new JLabel("Missions");
		panel_1.add(lblListMissions);
		listMissions = new JList();
		scrollPane_missions = new JScrollPane(listMissions);
		scrollPane_missions.setVisible(true);
		panel_1.add(scrollPane_missions);
		
		listMissionInfo = new JList();
		panel_1.add(listMissionInfo);
		listTasks = new JList();
		scrollPane_tasks = new JScrollPane(listTasks);
		panel_1.add(scrollPane_tasks);
		
		listTaskInfo = new JList();
		panel_1.add(listTaskInfo);
		
		panel_2 = new JPanel();
		contentPane.add(panel_2);

		txtLogArea = new JTextArea();
		txtLogArea.setRows(20);
		txtLogArea.setTabSize(10);
		scrollPaneTxtLogArea = new JScrollPane(txtLogArea);
		contentPane.add(scrollPaneTxtLogArea);
		
		panel_1.setVisible(true);
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

	/**
	 * @return the menuItemOpenFile
	 */
	public JMenuItem getMenuItemOpenFile() {
		return menuItemOpenFile;
	}

	/**
	 * @param menuItemOpenFile the menuItemOpenFile to set
	 */
	public void setMenuItemOpenFile(JMenuItem menuItemOpenFile) {
		this.menuItemOpenFile = menuItemOpenFile;
	}

	/**
	 * @return the menuItemLoadGps
	 */
	public JMenuItem getMenuItemLoadGps() {
		return menuItemLoadGps;
	}

	/**
	 * @param menuItemLoadGps the menuItemLoadGps to set
	 */
	public void setMenuItemLoadGps(JMenuItem menuItemLoadGps) {
		this.menuItemLoadGps = menuItemLoadGps;
	}

	/**
	 * @return the menuItemSaveGpsKml
	 */
	public JMenuItem getMenuItemSaveGpsKml() {
		return menuItemSaveGpsKml;
	}

	/**
	 * @param menuItemSaveGpsKml the menuItemSaveGpsKml to set
	 */
	public void setMenuItemSaveGpsKml(JMenuItem menuItemSaveGpsKml) {
		this.menuItemSaveGpsKml = menuItemSaveGpsKml;
	}

	public JLabel getLblGpsDataValue() {
		return lblGpsDataValue;
	}

	public void setLblGpsDataValue(JLabel lblGpsDataValue) {
		this.lblGpsDataValue = lblGpsDataValue;
	}

	public JLabel getLblCompassDataValue() {
		return lblCompassDataValue;
	}

	public void setLblCompassDataValue(JLabel lblCompassDataValue) {
		this.lblCompassDataValue = lblCompassDataValue;
	}

	public JMenuItem getMenuItemLoadCompassData() {
		return menuItemLoadCompassData;
	}

	public void setMenuItemLoadCompassData(JMenuItem menuItemLoadCompassData) {
		this.menuItemLoadCompassData = menuItemLoadCompassData;
	}

	public JMenuItem getMenuItemCompassCourseTimeChart() {
		return menuItemCompassCourseTimeChart;
	}

	public void setMenuItemCompassCourseTimeChart(
			JMenuItem menuItemCompassCourseTimeChart) {
		this.menuItemCompassCourseTimeChart = menuItemCompassCourseTimeChart;
	}

	public JLabel getLblPIDControllerDataValue() {
		return lblPIDControllerDataValue;
	}

	public void setLblPIDControllerDataValue(JLabel lblPIDControllerDataValue) {
		this.lblPIDControllerDataValue = lblPIDControllerDataValue;
	}

	public JLabel getLblWindsensorDataValue() {
		return lblWindsensorDataValue;
	}

	public void setLblWindsensorDataValue(JLabel lblWindsensorDataValue) {
		this.lblWindsensorDataValue = lblWindsensorDataValue;
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

	public JLabel getLblMissionDataValue() {
		return lblMissionDataValue;
	}

	public void setLblMissionDataValue(JLabel lblMissionDataValue) {
		this.lblMissionDataValue = lblMissionDataValue;
	}

	public JMenuItem getMenuItemSegmentLog() {
		return menuItemSegmentLog;
	}

	public void setMenuItemSegmentLog(JMenuItem menuItemSegmentLog) {
		this.menuItemSegmentLog = menuItemSegmentLog;
	}

	public JList getListMissions() {
		return listMissions;
	}

	public void setListMissions(JList listMissions) {
		this.listMissions = listMissions;
	}

	public JList getListMissionInfo() {
		return listMissionInfo;
	}

	public void setListMissionInfo(JList listMissionInfo) {
		this.listMissionInfo = listMissionInfo;
	}

	public JList getListTasks() {
		return listTasks;
	}

	public void setListTasks(JList listTasks) {
		this.listTasks = listTasks;
	}

	public JList getListTaskInfo() {
		return listTaskInfo;
	}

	public void setListTaskInfo(JList listTaskInfo) {
		this.listTaskInfo = listTaskInfo;
	}

	public JTextArea getTxtLogArea() {
		return txtLogArea;
	}

	public void setTxtLogArea(JTextArea txtLogArea) {
		this.txtLogArea = txtLogArea;
	}

	public JMenuItem getMenuItemSaveCompassCourseWithDesiredAngleToCsv() {
		return menuItemSaveCompassCourseWithDesiredAngleToCsv;
	}

	public void setMenuItemSaveCompassCourseWithDesiredAngleToCsv(
			JMenuItem menuItemSaveCompassCourseWithDesiredAngleToCsv) {
		this.menuItemSaveCompassCourseWithDesiredAngleToCsv = menuItemSaveCompassCourseWithDesiredAngleToCsv;
	}

	
	
}
