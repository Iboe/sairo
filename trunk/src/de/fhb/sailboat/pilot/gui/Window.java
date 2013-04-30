package de.fhb.sailboat.pilot.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	
	public Canvas paintCanvas;
	
	public PIDController pidController;
	
	public Window(PIDController pidController) {
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
		
		paintCanvas = new Canvas();
		paintCanvas.setSize(400, 360);
		
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
		contentPanel.add(paintCanvas);
		
		this.add(contentPanel);
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
		ArrayList updateData = (ArrayList<String>) arg1;
		
		pLabel.setText("P: " + updateData.get(0));
		iLabel.setText("I: " + updateData.get(1));
		dLabel.setText("D: " + updateData.get(2));
		realAngleLabel.setText("real angle: " + updateData.get(3) + "°");
		deltaAngleLabel.setText("delta angle: " + updateData.get(5) + "°");
		steuersignalLabel.setText("Steuersignal: " + updateData.get(6));
		abtastrateLabel.setText("Abtastrate: " + updateData.get(7) + " Hz");
		
		Graphics gfx = paintCanvas.getGraphics();
		gfx.copyArea(0, 0, 399, 359, -1, 0);
		gfx.setColor(Color.WHITE);
		gfx.drawLine(399, 0, 399, 359);
		
		// target angle
		gfx.setColor(Color.BLACK);
		gfx.drawLine(400, 180 + (int)(Double.parseDouble((String) updateData.get(4))), 400, (int)(Double.parseDouble((String) updateData.get(4))));
		// real angle
		gfx.setColor(Color.BLACK);
		gfx.drawLine(400, 180 + (int)(Double.parseDouble((String) updateData.get(3))), 400, (int)(Double.parseDouble((String) updateData.get(3))));
		// steuersignal
		gfx.setColor(Color.BLACK);
		gfx.drawLine(400, 180 + (int)(Double.parseDouble((String) updateData.get(5))), 400, (int)(Double.parseDouble((String) updateData.get(5))));
	}
}
