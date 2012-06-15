package de.fhb.sailboat.gui.map;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScalePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int LINE_X1 = 5;
	private final int LINE_Y1 = 5;
	private final int LINE_X2 = LINE_X1 + Map.PIXEL_TO_CALCULATE_SCALE;
	private final int LINE_Y2 = LINE_Y1;

	private JLabel meterPerPixelLabel;

	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawLine(LINE_X1, LINE_Y1, LINE_X2, LINE_Y2);
	}

	public ScalePanel() {
		super();
		this.setSize(120, 20);
		meterPerPixelLabel = new JLabel();
		meterPerPixelLabel.setBounds(LINE_X2, LINE_Y2, 100, 20);
		this.add(meterPerPixelLabel);
	}

	public JLabel getMeterPerPixelLabel() {
		return meterPerPixelLabel;
	}

	public void setMeterPerPixelLabel(JLabel meterPerPixelLabel) {
		this.meterPerPixelLabel = meterPerPixelLabel;
	}
}
