// License: GPL. For details, see LICENSE file.
package de.fhb.sailboat.ufer.prototyp.utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import de.fhb.sailboat.data.GPS;

/**
 * @author Vincent
 * 
 */
public class MapPolygonImpl implements MapPolygon {

	private List<GPS> points;
	private Color color;
	private Stroke stroke;

	public MapPolygonImpl(List<GPS> points) {
		this(points, Color.BLUE, new BasicStroke(2));
	}

	public MapPolygonImpl(List<GPS> currentPoly, Color color,
			Stroke stroke) {
		this.points = currentPoly;
		this.color = color;
		this.stroke = stroke;
	}

	@Override
	public List<GPS> getPoints() {
		return points;
	}

	@Override
	public void paint(Graphics g, List<Point> points) {
		// Prepare graphics
		Color oldColor = g.getColor();
		g.setColor(color);
		Stroke oldStroke = null;
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			oldStroke = g2.getStroke();
			g2.setStroke(stroke);
		}
		// Draw

		int[] longPoints = new int[points.size()];
		int[] latPoints = new int[points.size()];

		for (int i = 0; i < points.size(); i++) {
			longPoints[i] = points.get(i).x;
			latPoints[i] = points.get(i).y;
		}

		g.drawPolygon(longPoints, latPoints, longPoints.length);
		// Restore graphics
		g.setColor(oldColor);
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setStroke(oldStroke);
		}
	}
}
