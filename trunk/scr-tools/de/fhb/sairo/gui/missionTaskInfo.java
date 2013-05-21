package de.fhb.sairo.gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class missionTaskInfo extends JPanel {
	
	private GridBagLayout gridBagLayout;
	private JLabel lblMissionListTopic;
	private JLabel lblMissionInfoBoxTopic;
	private JLabel lblTaskListTopic;
	private JLabel lblTaskInfoBoxTopic;
	private JScrollPane scrollPaneMissionList;
	private JList listMissionList;
	private JScrollPane scrollPaneMissionInfo;
	private JList listMissionInfo;
	private JScrollPane scrollPaneTaskList;
	private JList listTaskList;
	private JScrollPane scrollPaneTaskInfo;
	private JList listTaskInfo;
	
	public missionTaskInfo() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 50, 50, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblMissionListTopic = new JLabel("Missionlist");
		lblMissionListTopic.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMissionListTopic = new GridBagConstraints();
		gbc_lblMissionListTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMissionListTopic.insets = new Insets(5, 5, 5, 5);
		gbc_lblMissionListTopic.gridx = 0;
		gbc_lblMissionListTopic.gridy = 0;
		add(lblMissionListTopic, gbc_lblMissionListTopic);
		
		lblMissionInfoBoxTopic = new JLabel("Missioninfo");
		lblMissionInfoBoxTopic.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMissionInfoBoxTopic = new GridBagConstraints();
		gbc_lblMissionInfoBoxTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMissionInfoBoxTopic.insets = new Insets(5, 5, 5, 5);
		gbc_lblMissionInfoBoxTopic.gridx = 1;
		gbc_lblMissionInfoBoxTopic.gridy = 0;
		add(lblMissionInfoBoxTopic, gbc_lblMissionInfoBoxTopic);
		
		lblTaskListTopic = new JLabel("Tasks");
		lblTaskListTopic.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTaskListTopic = new GridBagConstraints();
		gbc_lblTaskListTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTaskListTopic.insets = new Insets(5, 5, 5, 5);
		gbc_lblTaskListTopic.gridx = 2;
		gbc_lblTaskListTopic.gridy = 0;
		add(lblTaskListTopic, gbc_lblTaskListTopic);
		
		lblTaskInfoBoxTopic = new JLabel("Taskinfo");
		lblTaskInfoBoxTopic.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTaskInfoBoxTopic = new GridBagConstraints();
		gbc_lblTaskInfoBoxTopic.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTaskInfoBoxTopic.insets = new Insets(5, 5, 5, 0);
		gbc_lblTaskInfoBoxTopic.gridx = 3;
		gbc_lblTaskInfoBoxTopic.gridy = 0;
		add(lblTaskInfoBoxTopic, gbc_lblTaskInfoBoxTopic);
		
		scrollPaneMissionList = new JScrollPane();
		GridBagConstraints gbc_scrollPaneMissionList = new GridBagConstraints();
		gbc_scrollPaneMissionList.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneMissionList.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneMissionList.gridx = 0;
		gbc_scrollPaneMissionList.gridy = 1;
		add(scrollPaneMissionList, gbc_scrollPaneMissionList);
		
		listMissionList = new JList();
		scrollPaneMissionList.setViewportView(listMissionList);
		
		scrollPaneMissionInfo = new JScrollPane();
		GridBagConstraints gbc_scrollPaneMissionInfo = new GridBagConstraints();
		gbc_scrollPaneMissionInfo.weightx = 0.2;
		gbc_scrollPaneMissionInfo.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneMissionInfo.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneMissionInfo.gridx = 1;
		gbc_scrollPaneMissionInfo.gridy = 1;
		add(scrollPaneMissionInfo, gbc_scrollPaneMissionInfo);
		
		listMissionInfo = new JList();
		scrollPaneMissionInfo.setViewportView(listMissionInfo);
		
		scrollPaneTaskList = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTaskList = new GridBagConstraints();
		gbc_scrollPaneTaskList.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPaneTaskList.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTaskList.gridx = 2;
		gbc_scrollPaneTaskList.gridy = 1;
		add(scrollPaneTaskList, gbc_scrollPaneTaskList);
		
		listTaskList = new JList();
		scrollPaneTaskList.setViewportView(listTaskList);
		
		scrollPaneTaskInfo = new JScrollPane();
		GridBagConstraints gbc_scrollPaneTaskInfo = new GridBagConstraints();
		gbc_scrollPaneTaskInfo.weightx = 0.2;
		gbc_scrollPaneTaskInfo.insets = new Insets(5, 5, 5, 0);
		gbc_scrollPaneTaskInfo.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTaskInfo.gridx = 3;
		gbc_scrollPaneTaskInfo.gridy = 1;
		add(scrollPaneTaskInfo, gbc_scrollPaneTaskInfo);
		
		listTaskInfo = new JList();
		scrollPaneTaskInfo.setViewportView(listTaskInfo);
	}

	public JList getListMissionList() {
		return listMissionList;
	}

	public void setListMissionList(JList listMissionList) {
		this.listMissionList = listMissionList;
	}

	public JList getListMissionInfo() {
		return listMissionInfo;
	}

	public void setListMissionInfo(JList listMissionInfo) {
		this.listMissionInfo = listMissionInfo;
	}

	public JList getListTaskList() {
		return listTaskList;
	}

	public void setListTaskList(JList listTaskList) {
		this.listTaskList = listTaskList;
	}

	public JList getListTaskInfo() {
		return listTaskInfo;
	}

	public void setListTaskInfo(JList listTaskInfo) {
		this.listTaskInfo = listTaskInfo;
	}

}
