package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;
import java.util.List;

import de.fhb.sailboat.data.GPS;

public class ArrangePolygon {

	/**
	 * arranges the points so that they build a "well-looking" polygon
	 * @param polygon old list without any order
	 * @return
	 * 	ordered list
	 */
	public static List<GPS> arrange(List<GPS> polygon) {
		List<GPS> arrangedPolygon = new ArrayList<GPS>();
		List<Double> distances = new ArrayList<Double>();
		arrangedPolygon.add(polygon.get(0));
		polygon.remove(0);

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

		arrangedPolygon.add(polygon.get(0));

		return arrangedPolygon;
	}

	private static double euklidDistance(GPS a, GPS b) {
		return Math.sqrt(Math.pow(a.getLatitude() - b.getLatitude(), 2)
				+ Math.pow(a.getLongitude() - b.getLongitude(), 2));
	}

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
	//
	// public static void main(String[] args) {
	// System.out.println(euklidDistance(new GPS(0.0, 0.0),
	// new GPS(30.0, 30.0)));
	// }
}
