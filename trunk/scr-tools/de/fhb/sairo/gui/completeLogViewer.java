package de.fhb.sairo.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class completeLogViewer extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList listLogfile;
	private JLabel lblLogInfo;
	
	public completeLogViewer() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblLogFileView = new JLabel("Logfile");
		GridBagConstraints gbc_lblLogFileView = new GridBagConstraints();
		gbc_lblLogFileView.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogFileView.gridx = 1;
		gbc_lblLogFileView.gridy = 0;
		add(lblLogFileView, gbc_lblLogFileView);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weightx = 0.25;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane_1, gbc_scrollPane_1);
		
		lblLogInfo = new JLabel("LogInfo");
		lblLogInfo.setVerticalAlignment(SwingConstants.TOP);
		lblLogInfo.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setViewportView(lblLogInfo);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weightx = 0.75;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		add(scrollPane, gbc_scrollPane);
		
		listLogfile = new JList();
		scrollPane.setViewportView(listLogfile);
	}

	public JList getListLogfile() {
		return listLogfile;
	}

	public void setListLogfile(JList listLogfile) {
		this.listLogfile = listLogfile;
	}

	public JLabel getLblLogInfo() {
		return lblLogInfo;
	}

	public void setLblLogInfo(JLabel lblLogInfo) {
		this.lblLogInfo = lblLogInfo;
	}

}
