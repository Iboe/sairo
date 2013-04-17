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
	private JLabel targetAngleLabel;
	private JLabel steuersignalLabel;
	private JLabel abtastrateLabel;
	private JPanel contentPanel;
	
	private JLabel kpLabel;
	private JLabel kiLabel;
	private JLabel kdLabel;
	private JTextField kpText;
	private JTextField kiText;
	private JTextField kdText;
	
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
		realAngleLabel = new JLabel("real angle: n/a �");
		targetAngleLabel = new JLabel("target angle: n/a �");
		steuersignalLabel = new JLabel("Steuersignal: n/a");
		abtastrateLabel = new JLabel("Abtastrate: n/a Hz");
		
		kpLabel = new JLabel("Kp:");
		kiLabel = new JLabel("Ki:");
		kdLabel = new JLabel("Kd:");
		
		kpText = new JTextField();
		kiText = new JTextField();
		kdText = new JTextField();
		
		paintCanvas = new Canvas();
		paintCanvas.setSize(400, 360);
		
		contentPanel.add(pLabel);
		contentPanel.add(iLabel);
		contentPanel.add(dLabel);
		contentPanel.add(realAngleLabel);
		contentPanel.add(targetAngleLabel);
		contentPanel.add(steuersignalLabel);
		contentPanel.add(abtastrateLabel);
		contentPanel.add(kpLabel);
		contentPanel.add(kpText);
		contentPanel.add(kiLabel);
		contentPanel.add(kiText);
		contentPanel.add(kdLabel);
		contentPanel.add(kdText);
		contentPanel.add(paintCanvas);
		
		this.add(contentPanel);
		updateTextFields();
		this.pack();
		this.setVisible(true);
	}

	public void updateTextFields(){
		try{
		this.kpText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					pidController.setKd(Double.valueOf(kpText.getText()));
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.kiText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					pidController.setKi(Double.valueOf(kiText.getText()));
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.kdText.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					pidController.setKd(Double.valueOf(kdText.getText()));
				}
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		}
		catch (NumberFormatException e){
			
		}
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
		realAngleLabel.setText("real angle: " + updateData.get(3) + "�");
		targetAngleLabel.setText("target angle: " + updateData.get(4) + "�");
		steuersignalLabel.setText("Steuersignal: " + updateData.get(5));
		abtastrateLabel.setText("Abtastrate: " + updateData.get(6) + " Hz");
		
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
