package de.fhb.sailboat.ufer.prototyp.utility;

import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.GPS;

public class MapMarkerToGPS {

	public static ArrayList<GPS> toGPS(ArrayList<MapMarker> markerList) {
		ArrayList<GPS> gpsList = new ArrayList<GPS>();
		for (int i = 0; i < markerList.size(); i++) {
			gpsList.add(new GPS(markerList.get(i).getLat(), markerList.get(i)
					.getLon()));
		}
		return gpsList;
	}
}
