package de.fhb.sailboat.pilot.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
	private BufferedImage diagramImage;
	private Canvas paintCanvas;
	
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
		
		paintCanvas = new Canvas();
		paintCanvas.setSize(400, 360);
		diagramImage = new BufferedImage(400, 360, BufferedImage.TYPE_INT_RGB);
		
		contentPanel.add(pLabel);
		contentPanel.add(iLabel);
		contentPanel.add(dLabel);
		contentPanel.add(realAngleLabel);
		contentPanel.add(targetAngleLabel);
		contentPanel.add(steuersignalLabel);
		contentPanel.add(abtastrateLabel);
		contentPanel.add(paintCanvas);
		
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
		
		Graphics gfx = diagramImage.getGraphics();
		gfx.copyArea(0, 0, 399, 359, -1, 0);
		
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
	
	public void paint(Graphics gfx) {
		super.paint(gfx);
		
		gfx.drawImage(diagramImage, 0, 0, 399, 359, paintCanvas);
	}

}
