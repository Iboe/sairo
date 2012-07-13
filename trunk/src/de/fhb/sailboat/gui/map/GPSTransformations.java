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

	public static GPS coordinateToGPS(Coordinate coordinate) {
		return new GPS(coordinate.getLat(), coordinate.getLon(), 0);
	}

	public static Coordinate gpsToCoordinate(GPS gps) {
		return new Coordinate(gps.getLatitude(), gps.getLongitude());
	}

	public static List<GPS> mapMarkerListToGpsList(List<MapMarker> mapMarkerList) {
		List<GPS> list = new ArrayList<GPS>();

		for (int i = 0; i < mapMarkerList.size(); i++) {
			list.add(new GPS(mapMarkerList.get(i).getLat(), mapMarkerList
					.get(i).getLon()));
		}

		return list;
	}
}
