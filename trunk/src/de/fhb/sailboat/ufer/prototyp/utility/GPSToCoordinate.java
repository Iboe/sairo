package de.fhb.sailboat.ufer.prototyp.utility;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import de.fhb.sailboat.data.GPS;

public class GPSToCoordinate {

	public static GPS coordinateToGPS(Coordinate coordinate) {
		return new GPS(coordinate.getLat(), coordinate.getLon(), 0);
	}

	public static Coordinate gpsToCoordinate(GPS gps) {
		return new Coordinate(gps.getLatitude(), gps.getLongitude());
	}
}
