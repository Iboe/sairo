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

/**
 * @author Vincent
 * 
 */
public class MapPolygonImpl implements MapPolygon {

	private ArrayList<Coordinate> points;
	private Color color;
	private Stroke stroke;

	public MapPolygonImpl(ArrayList<Coordinate> points) {
		this(points, Color.BLUE, new BasicStroke(2));
	}

	public MapPolygonImpl(ArrayList<Coordinate> points, Color color,
			Stroke stroke) {
		this.points = points;
		this.color = color;
		this.stroke = stroke;
	}

	@Override
	public ArrayList<Coordinate> getPoints() {
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
		g.drawRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x,
				bottomRight.y - topLeft.y);
		// Restore graphics
		g.setColor(oldColor);
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setStroke(oldStroke);
		}
	}

	public String toString() {
		return "MapRectangle from " + topLeft + " to " + bottomRight;
	}
}
