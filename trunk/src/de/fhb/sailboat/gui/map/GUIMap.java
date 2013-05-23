package de.fhb.sailboat.gui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.utilities.JMapViewer;
import de.fhb.sailboat.gui.map.utilities.MissionVisualization;

/**
 * Class for dividing between a map for representing in the GUI and a map
 * recognizing the interactions with it. Not sure if this should be done or not..
 * 
 * @author Paul Lehmann
 * 
 */
public class GUIMap extends GeneralMap {

	private static final long serialVersionUID = 1L;

	private static final int NO_MARK = 0;
	private static final int MARKER = 1;
	private static final int POLYGON = 2;
	private static final int EVERY_X_GPS_POSITION = 3;
	public static final int PIXEL_TO_CALCULATE_SCALE = 80;
	private static final int EARTH_CIRCUMFERENCE = 40074000;
	private static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745,System.currentTimeMillis());
	private static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414,System.currentTimeMillis());

	private static final Logger LOG = LoggerFactory.getLogger(GeneralMap.class);

	private List<MapMarker> markerList;
	private MapMarker currentPosition;

	private List<GPS> positionHistoryList;
	private MapPolygon positionHistory = null;
	private List<MapPolygon> polygonList;
	private List<MapMarker> polyHelpList;
	private List<GPS> currentPoly = null;

	private JMapViewer map;

	private int markerMode = NO_MARK;
	private int followCounter = 0;
	private int currentZoom;

	private MissionVisualization visualize;

	public GUIMap() {
		super();
	}

	/**
	 * Creates a line with a node every (EVERY_X_GPS_POSITION + 1)
	 * positions/function calls representing the positions of the boat in the
	 * current mission. Should be reset with removeTrail() at the start of a new
	 * mission.
	 * 
	 * @param boatPosition
	 *            current Position of the boat
	 */
	public void followBoat(GPS boatPosition) {

		if (map.getMapMarkerList().contains(currentPosition))
			map.removeMapMarker(currentPosition);

		currentPosition = new MapMarkerDot(Color.MAGENTA,
				boatPosition.getLatitude(), boatPosition.getLongitude());

		map.addMapMarker(currentPosition);

		if (positionHistoryList.size() < 1) {
			positionHistoryList.add(new GPS(boatPosition.getLatitude(),
					boatPosition.getLongitude(),System.currentTimeMillis()));
		} else {
			if (positionHistoryList.size() == 1) {

				positionHistoryList.add(new GPS(boatPosition.getLatitude(),
						boatPosition.getLongitude(),System.currentTimeMillis()));

				for (int i = positionHistoryList.size() - 2; i >= 0; i--)
					positionHistoryList.add(positionHistoryList.get(i));

				positionHistory = new MapPolygonImpl(positionHistoryList,
						Color.MAGENTA, new BasicStroke(3));
				map.addMapPolygon(positionHistory);

			} else {
				if (followCounter == EVERY_X_GPS_POSITION) {
					map.removeMapPolygon(positionHistory);
					int j = (positionHistoryList.size() / 2) + 1;
					while (j < positionHistoryList.size())
						positionHistoryList.remove(j);

					positionHistoryList.add(new GPS(boatPosition.getLatitude(),
							boatPosition.getLongitude(),System.currentTimeMillis()));

					for (int i = positionHistoryList.size() - 2; i >= 0; i--)
						positionHistoryList.add(positionHistoryList.get(i));

					positionHistory = new MapPolygonImpl(positionHistoryList,
							Color.MAGENTA, new BasicStroke(3));
					followCounter = 0;
					map.addMapPolygon(positionHistory);
				} else
					followCounter++;
			}
		}
	}

	/**
	 * Removes line of positions.
	 */
	public void removeTrail() {
		if (map.getMapMarkerList().contains(currentPosition))
			map.removeMapMarker(currentPosition);
		if (map.getMapPolygonList().contains(positionHistory))
			map.removeMapPolygon(positionHistory);
		positionHistoryList = new ArrayList<GPS>();
	}
}
