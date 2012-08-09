package de.fhb.sailboat.gui.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.GUIModel;
import de.fhb.sailboat.mission.Mission;

/**
 * Primary Map-class which uses all the other classes.
 * 
 * @author Paul Lehmann
 */

public class GeneralMap extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int PIXEL_TO_CALCULATE_SCALE = 80;
	private static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745);
	private static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414);

	private static final Logger LOG = LoggerFactory.getLogger(GeneralMap.class);

	protected List<MapMarker> markerList;

	protected JMapViewer map;

	protected MissionVisualization visualize;

	protected List<MapMarker> obstacles;

	public GeneralMap() {
		this.map = new JMapViewer();
		this.markerList = new ArrayList<MapMarker>();
		this.visualize = new MissionVisualization(map);
	}

	public JPanel mapPanel(final javax.swing.JPanel mapArea) {

		// startpositions, Regattastrecke or FH Brandenburg
		// if needed defining of other position with navigateTo(GPS)
		// navigateTo(FH_BRANDENBURG);
		navigateTo(REGATTASTRECKE);

		map.setTileSource(new OsmTileSource.Mapnik());

		try {
			map.setTileLoader(new OsmFileCacheTileLoader(map));
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}

		mapArea.setLayout(new BorderLayout());
		mapArea.add(map, BorderLayout.CENTER);

		return mapArea;
	}

	/**
	 * Adds a MapMarker at a given gps-position
	 * 
	 * @param target
	 *            gps position
	 */
	public void addMapMarker(GPS target) {
		markerList.add(new MapMarkerDot(Color.RED, target.getLatitude(), target
				.getLongitude()));
		map.addMapMarker(markerList.get(markerList.size() - 1));
	}

	public void visualizeMission(GUIModel model) {
		visualize.visualize(model);
	}
	
	public void visualizeMission(Mission mission) {
		visualize.visualize(mission);
	}

	public void addObstacle(GPS gps) {
		obstacles.add(new MapMarkerDot(Color.BLACK, gps.getLatitude(), gps
				.getLongitude()));
		map.addMapMarker(obstacles.get(obstacles.size() - 1));
	}

	public void removeObstacles() {
		for (int i = 0; i < obstacles.size(); i++) {
			map.removeMapMarker(obstacles.get(i));
		}
		obstacles = new ArrayList<MapMarker>();
	}

	/**
	 * Clear the map from artifacts.
	 */
	public void removeEveryObject() {
		removeMapMarkerFromMap();
		visualize.clearMap();
	}

	private void removeMapMarkerFromMap() {
		map.getMapMarkerList().clear();
		this.getMarkerList().clear();
	}

	/**
	 * Move the map to a point at maximum zoomlevel.
	 * 
	 * @param gps
	 */
	protected void navigateTo(GPS gps) {
		navigateTo(gps, 18);
	}

	/**
	 * Move the map to a point and to a given zoom.
	 * 
	 * @param gps
	 * @param zoomLevel
	 */
	protected void navigateTo(GPS gps, int zoomLevel) {
		map.setDisplayPositionByLatLon(gps.getLatitude(), gps.getLongitude(),
				zoomLevel);
	}

	/**
	 * TODO doesn't work yet.
	 * 
	 * Loads tiles around a given coordinate.
	 * 
	 * @param coordinate
	 * @param zoomlevel
	 * @return
	 * @throws InterruptedException
	 */
	public void loadTilesInCache(Coordinate coordinate, int zoomlevel)
			throws InterruptedException {
		if (zoomlevel <= 18) {
			for (int i = zoomlevel - 3; i < zoomlevel + 3 && i <= 18; i++) {
				map.setDisplayPositionByLatLon(coordinate.getLon(),
						coordinate.getLat(), i);

			}
		}
	}

	public JMapViewer getMap() {
		return map;
	}

	public void setMap(JMapViewer map) {
		this.map = map;
	}

	public List<MapMarker> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(List<MapMarker> markerList) {
		this.markerList = markerList;
	}

	public List<MapMarker> getObstacles() {
		return obstacles;
	}

	public void setObstacles(List<MapMarker> obstacles) {
		this.obstacles = obstacles;
		for (int i = 0; i < obstacles.size(); i++) {
			map.addMapMarker(obstacles.get(i));
		}
	}
}
