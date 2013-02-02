package de.fhb.sailboat.gui.map;

import java.util.ArrayList;
import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Class for rearranging the polygon (list of points)
 * 
 * @author Paul Lehmann
 */
public class ArrangePolygon {

	/**
	 * Arranges the points so that they build a "well-looking" polygon.
	 * 
	 * @param polygon
	 *            old list without any order
	 * @return ordered list
	 */
	public static List<GPS> arrange(List<GPS> polygon) {
		List<GPS> arrangedPolygon = new ArrayList<GPS>();
		List<Double> distances = new ArrayList<Double>();

		// Take the first element in the list of the old polygon and remove it
		// in that list.
		arrangedPolygon.add(polygon.get(0));
		polygon.remove(0);

		// Retrieve the point with the lowest distance to the element we took
		// last. Take this minimum as next element and delete it from the old
		// list.
		while (polygon.size() > 1) {
			for (int i = 0; i < polygon.size(); i++) {
				distances.add(euklidDistance(
						arrangedPolygon.get(arrangedPolygon.size() - 1),
						polygon.get(i)));
			}
			arrangedPolygon.add(polygon.get(returnMinimum(distances)));
			polygon.remove(returnMinimum(distances));
			distances = new ArrayList<Double>();
		}
		// If there is just one element left in the old list, add it to the new
		// list.
		if (polygon.size() > 0)
			arrangedPolygon.add(polygon.get(0));

		return arrangedPolygon;
	}

	/**
	 * Calculates distance between two given points.
	 * 
	 * @param a
	 * @param b
	 * @return distance in degrees
	 */
	private static double euklidDistance(GPS a, GPS b) {
		return Math.sqrt(Math.pow(a.getLatitude() - b.getLatitude(), 2)
				+ Math.pow(a.getLongitude() - b.getLongitude(), 2));
	}

	/**
	 * Returns index of the minimum in a list.
	 * 
	 * @param list
	 * @return index of minimum
	 */
	private static int returnMinimum(List<Double> list) {
		int x = 0;
		double minimum = list.get(0);
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i) < minimum) {
				x = i;
				minimum = list.get(i);
			}
		}
		return x;
	}

}
