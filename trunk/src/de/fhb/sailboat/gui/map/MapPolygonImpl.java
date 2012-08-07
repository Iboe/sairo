package de.fhb.sailboat.gui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Class which represents a polygon as a list and drawable on the map.
 * 
 * @author Paul Lehmann
 * 
 */
public class MapPolygonImpl implements MapPolygon {

	private List<GPS> points;
	private Color color;
	private Stroke stroke;

	public MapPolygonImpl(List<GPS> points) {
		this(points, Color.BLUE, new BasicStroke(2));
	}

	public MapPolygonImpl(List<GPS> points, Color color) {
		this(points, color, new BasicStroke(3));

	}

	public MapPolygonImpl(List<GPS> points, Color color, Stroke stroke) {
		this.points = points;
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

	public String toString() {
		String polygon = "MapPolygon: [";

		for (int i = 0; i < this.points.size() - 1; i++) {
			polygon += this.points.get(i).getLatitude() + ", "
					+ this.points.get(i).getLongitude() + "; ";
		}

		polygon += this.points.get(this.points.size() - 1).getLatitude() + ", "
				+ this.points.get(this.points.size() - 1).getLongitude() + "]";

		return polygon;
	}
}
