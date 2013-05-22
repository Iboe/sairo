package de.fhb.sairo.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class completeLogViewer extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblLogInfo;
	private JTextArea textArea;
	
	public completeLogViewer() {
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.weighty = 0.25;
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.weightx = 0.25;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		add(scrollPane_1, gbc_scrollPane_1);
		
		lblLogInfo = new JLabel("LogInfo");
		lblLogInfo.setVerticalAlignment(SwingConstants.TOP);
		lblLogInfo.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setViewportView(lblLogInfo);
		
		JLabel lblLogFileView = new JLabel("Logfile");
		GridBagConstraints gbc_lblLogFileView = new GridBagConstraints();
		gbc_lblLogFileView.gridwidth = 2;
		gbc_lblLogFileView.insets = new Insets(0, 0, 5, 5);
		gbc_lblLogFileView.gridx = 0;
		gbc_lblLogFileView.gridy = 3;
		add(lblLogFileView, gbc_lblLogFileView);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.weighty = 0.75;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		add(scrollPane, gbc_scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}

	public JLabel getLblLogInfo() {
		return lblLogInfo;
	}

	public void setLblLogInfo(JLabel lblLogInfo) {
		this.lblLogInfo = lblLogInfo;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}
