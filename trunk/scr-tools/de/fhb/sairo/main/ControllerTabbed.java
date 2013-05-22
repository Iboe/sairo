package de.fhb.sairo.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
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
import de.fhb.sairo.fileio.FileLoader;
import de.fhb.sairo.fileio.FileSaver;
import de.fhb.sairo.gui.mainTabbed;
import de.fhb.sairo.gui.dialogs.openFileDialog;
import de.fhb.sairo.logAnalyze.loadGpsData;
import de.fhb.sairo.logAnalyze.loadMissionData;

public class ControllerTabbed implements Observer {

	private Model model;
	private mainTabbed gui;

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss:SS");

	public ControllerTabbed(mainTabbed pGui, Model pModel) {
		this.gui = pGui;
		this.model = pModel;
		model.addObserver(this);
		gui.getMissionTaskInfo().getListMissionList()
				.setModel(model.getListMissionsModel());
		initAddActionListeners();
	}

	private void initAddActionListeners() {
		gui.getMenuItemOpenFile().addActionListener(new openFile());
		gui.getMenuItemLoadMissionData().addActionListener(
				new loadMissionDataFromFile());
		gui.getMenuItemSegmentLog().addActionListener(new segmentLogFile());
		gui.getMissionTaskInfo().getListMissionList()
				.addListSelectionListener(new loadMissionDetails());
		gui.getMissionTaskInfo().getListTaskList()
				.addListSelectionListener(new loadTaskDetails());
		gui.getMenuItemSaveCompassCourseWithDesiredAngleToCsv()
				.addActionListener(
						new saveTaskCompassCourseWithDesiredAngleToCsvFile());
	}

	/***
	 * This class loads the task details of selected task
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class loadTaskDetails implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			new Thread() {
				public void run() {
					DefaultListModel<String> taskInfoModel = new DefaultListModel<String>();

					int selection = getSelectedMission();
					int selectionTask = getSelectedTask();
					Task tmpTask = model.getMissionList().get(selection)
							.getTaskList().get(selectionTask);
					taskInfoModel.addElement("Task: "
							+ tmpTask.getTaskDescription());
					taskInfoModel.addElement("Taskarguments: "
							+ tmpTask.getTaskArguments());
					taskInfoModel.addElement("Task logs: "
							+ tmpTask.getLog().size());
					gui.getMissionTaskInfo().getListTaskInfo()
							.setModel(taskInfoModel);
				}
			}.start();
		}
	}

	/***
	 * This class segements the loaded logfile in missions parted logfiles
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class segmentLogFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Able to segment into: "
					+ model.getMissionList().size() + " files");
			String fileName = model.getLogFile().getAbsolutePath();
			MissionList tmpMissionList = new MissionList();
			tmpMissionList = model.getMissionList();
			for (int i = 0; i < model.getMissionList().size(); i++) {
				System.out.println("Save: "
						+ fileName
						+ "_"
						+ i
						+ " with "
						+ model.getMissionList().get(i).getLogFromMission()
								.size() + " entries.");
				FileSaver.saveAsTxtFile(fileName + "_" + i,
						tmpMissionList.get(i).getLogFromMission());
			}
		}

	}

	/***
	 * This class loads the details of selected mission
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class loadMissionDetails implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int selection = getSelectedMission();
			DefaultListModel<String> tmpModel = new DefaultListModel<String>();
			DefaultListModel<String> tmpTasksModel = new DefaultListModel<String>();
			tmpModel.addElement("Mission: "
					+ model.getMissionList().get(selection).getDescription());
			tmpModel.addElement("Mission start: "
					+ model.getMissionList().get(selection)
							.getStartTimeString());
			tmpModel.addElement("Task count: "
					+ model.getMissionList().get(selection).getTaskList()
							.toString());
			for (int i = 0; i < model.getMissionList().get(selection)
					.getTaskList().size(); i++) {
				tmpTasksModel.addElement(model.getMissionList().get(selection)
						.getTaskList().get(i).toString());
			}
			gui.getMissionTaskInfo().getListMissionInfo().setModel(tmpModel);
			gui.getMissionTaskInfo().getListTaskList().setModel(tmpTasksModel);
		}

	}

	/***
	 * This class loads the mission and task data from the opened logfile
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class loadMissionDataFromFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			model.setMissionList(loadMissionData.loadMissions(model
					.getLogFile().getAbsolutePath()));
			for (int i = 0; i < model.getMissionList().size(); i++) {
				System.out.println("Add: "
						+ model.getMissionList().get(i).toString()
						+ " to JList");
				model.getListMissionsModel().addElement(
						model.getMissionList().get(i).toString());
			}
			gui.getMissionTaskInfo().getListMissionList().updateUI();
			System.out.println(model.getMissionList().toString());
		}

	}

	/***
	 * This class saves the csv data for compasscourse task with the compass
	 * courses and the desired compass course to a csv file
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class saveTaskCompassCourseWithDesiredAngleToCsvFile implements
			ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selection = getSelectedMission();
			int selectionTask = getSelectedTask();
			CompassCourseTask tmpTask = (CompassCourseTask) model
					.getMissionList().get(selection).getTaskList()
					.get(selectionTask);
			tmpTask.extractCompassCourseList();
			String[] csvList = new String[tmpTask.getCompassCourseList().size() + 1];
			csvList[0] = "Zeitstempel;Ist-Kurs;Soll-Kurs";
			for (int i = 1; i < csvList.length; i++) {
				csvList[i] = simpleDateFormat.format(tmpTask
						.getCompassCourseList().get(i - 1).getTimeStamp())
						+ ";"
						+ String.valueOf(
								tmpTask.getCompassCourseList().get(i - 1)
										.getCompassCourseAzimuth()).replace(
								'.', ',')
						+ ";"
						+ String.valueOf(tmpTask.getCompassCourseAngle())
								.replace('.', ',');
			}
			try {
				File tmpSaveFile = new File(model.getLogFile() + "_"
						+ selection + "_" + selectionTask + ".csv");
				CSVWriter csvWriter = new CSVWriter(
						new FileWriter(tmpSaveFile), '\n', '\0', '\0');
				csvWriter.writeNext(csvList);
				csvWriter.close();
				System.out.println(tmpSaveFile.getAbsolutePath() + " saved");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/***
	 * This method returns the index of selected task in list
	 * 
	 * @return index of selected task
	 */
	private int getSelectedTask() {
		int selectionTask = gui.getMissionTaskInfo().getListTaskList()
				.getSelectedIndex();
		return selectionTask;
	}

	/***
	 * This method returns the index of selected mission in list
	 * 
	 * @return index of selected mission
	 */
	private int getSelectedMission() {
		int selection = gui.getMissionTaskInfo().getListMissionList()
				.getSelectedIndex();
		return selection;
	}

	/***
	 * This class open a fileopen dialog and save the selected filename into the
	 * model
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class openFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			model.getLogfileModel().clear();
			model.setLogFile(openFileDialog.openFileDialog());
			enableItems();
			loadLogfileToModel();
		}
	}

	/***
	 * This method loads the complete logfile to the model for viewing
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	private void loadLogfileToModel() {
		new Thread() {
			public void run() {
				BufferedReader reader = FileLoader.openLogfile(model
						.getLogFile().getAbsolutePath());
				String zeile = null;
				try {
					while ((zeile = reader.readLine()) != null) {
						model.getLogfileModel().addElement(zeile);
					}
					// gui.getLogViewer().getTextPane().setText(model.logFileModelToString());
					gui.getLogViewer().getTextArea()
							.setText(model.logFileModelToString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		System.out.println("Loading logfile for viewing completed");
	}

	/***
	 * This class loads the gps data from given logfile in a seperated thread
	 * 
	 * @author Tobias Koppe
	 * 
	 */
	class LoadGpsData implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread() {
				public void run() {
					model.setGpsDataList(loadGpsData.load(model.getLogFile()
							.getAbsolutePath()));
				}
			}.start();
		}

	}

	private void loadLogDataSet() {

	}

	public void enableItems() {

	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}

}
