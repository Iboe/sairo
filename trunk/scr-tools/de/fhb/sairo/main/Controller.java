package de.fhb.sairo.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import opencsv.CSVWriter;

import de.fhb.sairo.data.MissionList;
import de.fhb.sairo.data.Task.CompassCourseTask;
import de.fhb.sairo.data.Task.Task;
import de.fhb.sairo.fileio.FileSaver;
import de.fhb.sairo.fileio.saveGpsListAsKmlFile;
import de.fhb.sairo.gui.main;
import de.fhb.sairo.gui.dialogs.openFileDialog;
import de.fhb.sairo.logAnalyze.loadCompassData;
import de.fhb.sairo.logAnalyze.loadGpsData;
import de.fhb.sairo.logAnalyze.loadMissionData;
import de.fhb.sairo.logAnalyze.loadWindData;

public class Controller implements Observer{

	private main gui;
	private Model model;
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	public Controller(main pGui, Model pModel){
		this.gui = pGui;
		this.model = pModel;
		init();
		model.addObserver(this);
		gui.getListMissions().setModel(model.getListMissionsModel());
	}
	
	private void init(){
		System.out.println("Controller init()");
		gui.getMenuItemOpenFile().addActionListener(new openFile());
		gui.getMenuItemLoadGps().addActionListener(new LoadGpsData());
		gui.getMenuItemSaveGpsKml().addActionListener(new saveGpsAsKml());
		gui.getMenuItemLoadCompassData().addActionListener(new LoadCompassData());
		gui.getMenuItemCompassCourseTimeChart().addActionListener(new createCompassCourseTimeChart());
		gui.getMenuItemLoadAllData().addActionListener(new loadAllData());
		gui.getMenuItemLoadMissionData().addActionListener(new loadMissionDataFromFile());
		gui.getMenuItemSegmentLog().addActionListener(new segmentLogFile());
		gui.getListMissions().addListSelectionListener(new loadMissionDetails());
		gui.getListTasks().addListSelectionListener(new loadTaskDetails());
		gui.getMenuItemSaveCompassCourseWithDesiredAngleToCsv().addActionListener(new saveTaskCompassCourseWithDesiredAngleToCsvFile());
		gui.getMenuItemLoadGps().setEnabled(false);
		gui.getMenuItemSaveGpsKml().setEnabled(false);
		gui.getMenuItemLoadCompassData().setEnabled(false);
		gui.getMenuItemCompassCourseTimeChart().setEnabled(false);
		gui.getMenuItemLoadAllData().setEnabled(false);
		gui.getMenuItemLoadMissionData().setEnabled(false);
		gui.getMenuItemSegmentLog().setEnabled(false);
		gui.getListMissions().setEnabled(false);
	}
	
	class saveTaskCompassCourseWithDesiredAngleToCsvFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int selection = gui.getListMissions().getSelectedIndex();
			int selectionTask = gui.getListTasks().getSelectedIndex();
			CompassCourseTask tmpTask = (CompassCourseTask) model.getMissionList().get(selection).getTaskList().get(selectionTask);
			tmpTask.extractCompassCourseList();
			String [] csvList = new String[tmpTask.getCompassCourseList().size()+1];
			csvList[0]="Zeitstempel;Ist-Kurs;Soll-Kurs";
			for(int i=1;i<csvList.length;i++){
				csvList[i]=simpleDateFormat.format(tmpTask.getCompassCourseList().get(i-1).getTimeStamp())+";"+String.valueOf(tmpTask.getCompassCourseList().get(i-1).getCompassCourseAzimuth()).replace('.', ',')+";"+String.valueOf(tmpTask.getCompassCourseAngle()).replace('.', ',');
			}
			try {
				File tmpSaveFile = new File (model.getLogFile()+"_"+selection+"_"+selectionTask+".csv");
				CSVWriter csvWriter = new CSVWriter(new FileWriter(tmpSaveFile),'\n','\0','\0');
					csvWriter.writeNext(csvList);
				csvWriter.close();
				System.out.println(tmpSaveFile.getAbsolutePath() + " saved");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	class loadTaskDetails implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			DefaultListModel<String> taskInfoModel = new DefaultListModel<String>();
			
			int selection = gui.getListMissions().getSelectedIndex();
			int selectionTask = gui.getListTasks().getSelectedIndex();
			Task tmpTask = model.getMissionList().get(selection).getTaskList().get(selectionTask);
			taskInfoModel.addElement("Task: " + tmpTask.getTaskDescription());
			taskInfoModel.addElement("Taskarguments: " + tmpTask.getTaskArguments());
			taskInfoModel.addElement("Task logs: " + tmpTask.getLog().size());
			gui.getListTaskInfo().setModel(taskInfoModel);
			gui.getTxtLogArea().setText(tmpTask.getTaskLog());
		}
		
	}
	
	class loadMissionDetails implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selection = gui.getListMissions().getSelectedIndex();
			DefaultListModel<String> tmpModel = new DefaultListModel<String>();
			DefaultListModel<String> tmpTasksModel = new DefaultListModel<String>();
			tmpModel.addElement("Mission: " + model.getMissionList().get(selection).getDescription());
			tmpModel.addElement("Mission start: "  + model.getMissionList().get(selection).getStartTimeString());
			tmpModel.addElement("Task count: " + model.getMissionList().get(selection).getTaskList().toString());
			for(int i=0;i<model.getMissionList().get(selection).getTaskList().size();i++){
				tmpTasksModel.addElement(model.getMissionList().get(selection).getTaskList().get(i).toString());
			}
			gui.getListMissionInfo().setModel(tmpModel);
			gui.getListTasks().setModel(tmpTasksModel);
		}
		
	}
	
	class loadMissionDataFromFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			model.setMissionList(loadMissionData.loadMissions(model.getLogFile().getAbsolutePath()));
			for(int i=0;i<model.getMissionList().size();i++){
				System.out.println("Add: " + model.getMissionList().get(i).toString() + " to JList");
				model.getListMissionsModel().addElement(model.getMissionList().get(i).toString());
			}
			gui.getListMissions().updateUI();
			System.out.println(model.getMissionList().toString());
		}
		
	}
	
	class segmentLogFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Able to segment into: " + model.getMissionList().size() + " files");
			String fileName = model.getLogFile().getAbsolutePath();
			MissionList tmpMissionList = new MissionList();
			tmpMissionList = model.getMissionList();
			for(int i=0;i<model.getMissionList().size();i++){
				System.out.println("Save: " + fileName+"_"+i + " with " + model.getMissionList().get(i).getLogFromMission().size() + " entries.");
				FileSaver.saveAsTxtFile(fileName+"_"+i, tmpMissionList.get(i).getLogFromMission());
			}
		}
		
	}
	
	class createCompassCourseTimeChart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			gui.getPanelChart().add(createChart.createChartPanel(createChart.createLineChart(createChart.createCompassCourseChartDataset(model.getCompassCourseList()))));
//			gui.getPanelChart().setVisible(false);
//			gui.getPanelChart().setVisible(true);
		}
		
	}
	
	class LoadCompassData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Load Compass Data from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setCompassCourseList(loadCompassData.load(model.getLogFile().getAbsolutePath()));
		}
		
	}
	
	class saveGpsAsKml implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileName = openFileDialog.openFileDialog().getAbsolutePath();
			System.out.println("Save gpsDataList to: " + fileName);
			saveGpsListAsKmlFile.save(fileName, model.getGpsDataList());
		}
		
	}
	
	class LoadGpsData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Loading GPS Data from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setGpsDataList(loadGpsData.load(model.getLogFile().getAbsolutePath()));
		}
		
	}
	
	class openFile implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
//			model.setLogFile(openFileDialog.openFileDialog());
			model.setLogFile(new File("C:\\Users\\Tobias\\Desktop\\sailboat_15_05_2013_wasser_02.log"));
			gui.getMenuItemLoadGps().setEnabled(true);
			gui.getMenuItemSaveGpsKml().setEnabled(true);
			gui.getMenuItemLoadCompassData().setEnabled(true);
			gui.getMenuItemCompassCourseTimeChart().setEnabled(true);
			gui.getMenuItemLoadAllData().setEnabled(true);
			gui.getMenuItemLoadMissionData().setEnabled(true);
			gui.getMenuItemSegmentLog().setEnabled(true);
			gui.getListMissions().setEnabled(true);
		}
	}
	
	class loadWinddata implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Load Winddata from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setWindDatalist(loadWindData.loadWindData(model.getLogFile().getAbsolutePath()));
			
		}
	}
	
	class loadAllData implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Load Compass Data from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setCompassCourseList(loadCompassData.load(model.getLogFile().getAbsolutePath()));
			System.out.println("Loading GPS Data from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setGpsDataList(loadGpsData.load(model.getLogFile().getAbsolutePath()));
			System.out.println("Load Winddata from Logfile: " + model.getLogFile().getAbsolutePath());
			model.setWindDatalist(loadWindData.loadWindData(model.getLogFile().getAbsolutePath()));
		}
		
	}

	@Override
	public void update(Observable o, Object arg) {
		gui.getLblGpsDataValue().setText(String.valueOf(model.getGpsDataList().size()));
		gui.getLblCompassDataValue().setText((String.valueOf(model.getCompassCourseList().size())));
		gui.getLblWindsensorDataValue().setText(String.valueOf(model.getWindDatalist().size()));
		gui.getLblMissionDataValue().setText(String.valueOf(model.getMissionList().size()));
	}
}
