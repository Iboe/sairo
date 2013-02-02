package de.fhb.sailboat.gui.map;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;

/**
 * Class for transforming GPS-objects in Coordinate-objects and vice versa
 * 
 * @author Paul Lehmann
 * 
 */
public class GPSTransformations {

	/**
	 * Transforms the Coordinate format of JMapViewer into our GPS format.
	 * @param coordinate the one to be transformed
	 * @return transformed data
	 */
	public static GPS coordinateToGPS(Coordinate coordinate) {
		return new GPS(coordinate.getLat(), coordinate.getLon(), 0);
	}

	/**
	 * Transforms the our GPS format into JMapViewer's Coordinate format.
	 * @param gps the one to be transformed
	 * @return transformed data
	 */
	public static Coordinate gpsToCoordinate(GPS gps) {
		return new Coordinate(gps.getLatitude(), gps.getLongitude());
	}
}
