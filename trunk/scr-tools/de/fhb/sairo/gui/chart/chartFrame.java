package de.fhb.sairo.gui.chart;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Panel;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;

public class chartFrame extends JFrame {

	public chartFrame(ChartPanel chartPanel) throws HeadlessException {
		this.setSize(640, 480);
		this.add(chartPanel);
		this.setVisible(true);
	}

}
