package de.fhb.sailboat.gui.map;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import de.fhb.sailboat.data.GPS;

public interface MapPolygon {

	/**
	 * @return Latitude/Longitude of top left of rectangle
	 */
	public List<GPS> getPoints();

	/**
	 * Paints the map rectangle on the map. The <code>topLeft</code> and
	 * <code>bottomRight</code> are specifying the coordinates within
	 * <code>g</code>
	 * 
	 * @param g
	 * @param position
	 */
	public void paint(Graphics g, List<Point> points);
}
