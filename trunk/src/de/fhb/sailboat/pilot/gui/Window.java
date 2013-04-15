package de.fhb.sailboat.pilot.gui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JLabel targetAngleLabel;
	private JLabel steuersignalLabel;
	private JLabel abtastrateLabel;
	private JPanel contentPanel;
	
	public Window() {
		this.setTitle("PID Debug");
		this.setSize(300, 200);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		pLabel = new JLabel("P: n/a");
		iLabel = new JLabel("I: n/a");
		dLabel = new JLabel("D: n/a");
		realAngleLabel = new JLabel("real angle: n/a °");
		targetAngleLabel = new JLabel("target angle: n/a °");
		steuersignalLabel = new JLabel("Steuersignal: n/a");
		abtastrateLabel = new JLabel("Abtastrate: n/a Hz");
		
		contentPanel.add(pLabel);
		contentPanel.add(iLabel);
		contentPanel.add(dLabel);
		contentPanel.add(realAngleLabel);
		contentPanel.add(targetAngleLabel);
		contentPanel.add(steuersignalLabel);
		contentPanel.add(abtastrateLabel);
		
		this.add(contentPanel);
		this.pack();
		this.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		ArrayList updateData = (ArrayList<String>) arg1;
		
		pLabel.setText("P: " + updateData.get(0));
		iLabel.setText("I: " + updateData.get(1));
		dLabel.setText("D: " + updateData.get(2));
		realAngleLabel.setText("real angle: " + updateData.get(3) + "°");
		targetAngleLabel.setText("target angle: " + updateData.get(4) + "°");
		steuersignalLabel.setText("Steuersignal: " + updateData.get(5));
		abtastrateLabel.setText("Abtastrate: " + updateData.get(6) + " Hz");
		
	}

}
