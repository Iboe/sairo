package de.fhb.sailboat.ufer.prototyp.utility;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.sensor.GpsSensor;

public class GPSTransformations {

	public static GPS coordinateToGPS(Coordinate coordinate) {
		return new GPS(coordinate.getLat(), coordinate.getLon(), 0);
	}

	public static Coordinate gpsToCoordinate(GPS gps) {
		return new Coordinate(gps.getLatitude(), gps.getLongitude());
	}

	public static List<GPS> mapMarkerListToGpsList(List<MapMarker> markerList) {
		List<GPS> gpsList = new ArrayList<GPS>();
		for (int i = 0; i < markerList.size(); i++) {
			gpsList.add(new GPS(markerList.get(i).getLat(), markerList.get(i)
					.getLon()));
		}
		return gpsList;
	}
}
