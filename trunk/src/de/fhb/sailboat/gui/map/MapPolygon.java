package de.fhb.sailboat.gui.map;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Interface for Polygons.
 * 
 * @author Paul Lehmann
 */
public interface MapPolygon {

	public List<GPS> getPoints();

	public void paint(Graphics g, List<Point> points);
}
