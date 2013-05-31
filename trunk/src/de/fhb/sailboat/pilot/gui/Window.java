package de.fhb.sailboat.pilot.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.fhb.sailboat.control.pilot.PIDController;

/**
 * 
 */

/**
 * @author Frank Mertens
 *
 */
public class Window extends JFrame implements Observer {
	private JLabel pLabel;
	private JLabel iLabel;
	private JLabel dLabel;
	private JLabel realAngleLabel;
	private JLabel deltaAngleLabel;
	private JLabel steuersignalLabel;
	private JLabel abtastrateLabel;
	private JPanel contentPanel;
	
	private JLabel kpLabel;
	private JLabel kiLabel;
	private JLabel kdLabel;
	private JTextField kpText;
	private JTextField kiText;
	private JTextField kdText;
	
	private JButton setCoefficients;
	
	public PIDController pidController;
	private JPanel panel;
	
	private XYSeriesCollection dataSet;
	private XYSeries deltaAngleDataSet;
	private XYSeries outputDataSet;
	
	private int updates;
public  Window(PIDController pidController) {
		updates=-1;
		dataSet = new XYSeriesCollection();
		deltaAngleDataSet = new XYSeries("deltaAngle");
		outputDataSet = new XYSeries("output");
		dataSet.addSeries(deltaAngleDataSet);
		dataSet.addSeries(outputDataSet);
		this.setTitle("PID Debug");
		this.setSize(300, 200);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		pLabel = new JLabel("P: n/a");
		iLabel = new JLabel("I: n/a");
		dLabel = new JLabel("D: n/a");
		realAngleLabel = new JLabel("real angle: n/a °");
		deltaAngleLabel = new JLabel("delta angle: n/a °");
		steuersignalLabel = new JLabel("Steuersignal: n/a");
		abtastrateLabel = new JLabel("Abtastrate: n/a Hz");
		
		kpLabel = new JLabel("Kp:");
		kiLabel = new JLabel("Ki:");
		kdLabel = new JLabel("Kd:");
		
		kpText = new JTextField();
		kiText = new JTextField();
		kdText = new JTextField();
		
		setCoefficients = new JButton("Set");
		
		contentPanel.add(pLabel);
		contentPanel.add(iLabel);
		contentPanel.add(dLabel);
		contentPanel.add(realAngleLabel);
		contentPanel.add(deltaAngleLabel);
		contentPanel.add(steuersignalLabel);
		contentPanel.add(abtastrateLabel);
		contentPanel.add(kpLabel);
		contentPanel.add(kpText);
		contentPanel.add(kiLabel);
		contentPanel.add(kiText);
		contentPanel.add(kdLabel);
		contentPanel.add(kdText);
		contentPanel.add(setCoefficients);
		
		getContentPane().add(contentPanel);
		
		panel = new JPanel();
		panel.setSize(250, 250);
		createChart();
		contentPanel.add(panel);
		this.pidController=pidController;
		updateTextFields();
		this.pack();
		this.setVisible(true);
	}

	public void updateTextFields(){
		setCoefficients.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pidController.setKp(Double.valueOf(kpText.getText()));
				pidController.setKi(Double.valueOf(kiText.getText()));
				pidController.setKd(Double.valueOf(kdText.getText()));
			}
		});
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		ArrayList<String> updateData = (ArrayList<String>) arg1;
		updates++;
		if(updates>=100) {
			updates=0;
			deltaAngleDataSet.clear();
			outputDataSet.clear();
			System.out.println("reset updates to 1");
		}
		pLabel.setText("P: " + updateData.get(0));
		iLabel.setText("I: " + updateData.get(1));
		dLabel.setText("D: " + updateData.get(2));
		deltaAngleLabel.setText("delta angle: " + updateData.get(3) + "°");
		steuersignalLabel.setText("Steuersignal: " + updateData.get(4));
		abtastrateLabel.setText("Abtastrate: " + updateData.get(5) + " Hz");
		double data = Double.valueOf(updateData.get(3));
		double data2 = Double.valueOf(updateData.get(4));
		if(data2==Double.POSITIVE_INFINITY || data2==Double.NEGATIVE_INFINITY){
			data2=0;
		}
		deltaAngleDataSet.add(updates,data);
		outputDataSet.add(updates,data2);
		System.out.println("Add to dataSet deltaAngle: [" + updates +"][" + data + "]");
		System.out.println("Add to dataSet output: [" + updates +"][" + data2 + "]");
	}
	
	private void createChart(){
		JFreeChart pChart = ChartFactory.createXYLineChart("PIDController", "controlls", "values", dataSet, PlotOrientation.VERTICAL, true, true, false);
		ChartPanel chartPanel = new ChartPanel(pChart);
		this.panel=chartPanel;
	}
}
