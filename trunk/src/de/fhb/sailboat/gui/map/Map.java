package de.fhb.sailboat.gui.map;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.GUIModel;
import de.fhb.sailboat.gui.GUIModelImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;

/**
 * Primary Map-class which uses all the other classes.
 * 
 * @author Paul Lehmann
 */

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int NO_MARK = 0;
	private static final int MARKER = 1;
	private static final int POLYGON = 2;
	private static final int EVERY_X_GPS_POSITION = 3;
	public static final int PIXEL_TO_CALCULATE_SCALE = 80;
	private static final int EARTH_CIRCUMFERENCE = 40074000;
	private static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745);
	private static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414);

	private static final Logger LOG = LoggerFactory.getLogger(Map.class);

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

	public Map() {
		this.map = new JMapViewer();
		this.markerList = new ArrayList<MapMarker>();
		this.polygonList = new ArrayList<MapPolygon>();
		this.polyHelpList = new ArrayList<MapMarker>();
		this.positionHistoryList = new ArrayList<GPS>();
		this.visualize = new MissionVisualization(map);
	}

	public JPanel mapPanel(final javax.swing.JPanel mapArea) {

		// startpositions, Regattastrecke or FH Brandenburg
		// if needed defining of other position with navigateTo(GPS)
		// navigateTo(FH_BRANDENBURG);
		navigateTo(REGATTASTRECKE);

		map.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				if (SwingUtilities.isLeftMouseButton(arg0)) {
					Coordinate target = map.getPosition(arg0.getPoint());

					switch (markerMode) {
					case 0:
						break;

					case 1:
						addMapMarker(GPSTransformations.coordinateToGPS(target));

						break;

					case 2:
						removeTrail();
						addPointToPolygon(GPSTransformations
								.coordinateToGPS(target));
						break;

					default:
						break;
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		map.setTileSource(new OsmTileSource.Mapnik());

		try {
			map.setTileLoader(new OsmFileCacheTileLoader(map));
		} catch (Exception e) {
			LOG.warn(e.getMessage());
		}

		final JCheckBox markOnMap = new JCheckBox("Add Marker");
		final JCheckBox markPolygon = new JCheckBox("Add Polygon");
		final ScalePanel meterPerPixelPanel = new ScalePanel();

		markOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markOnMap.isSelected()) {
					markerMode = MARKER;
					markPolygon.setSelected(false);
				} else {
					markerMode = NO_MARK;
				}
			}
		});

		markPolygon.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markPolygon.isSelected()) {
					markerMode = POLYGON;
					markOnMap.setSelected(false);
				} else {
					markerMode = NO_MARK;
					if (currentPoly != null)
						addPointToPolygon(currentPoly.get(0));
				}
			}
		});

		Runnable recalculateScale = new Runnable() {
			public void run() {
				while (true) {
					if (currentZoom != map.getZoom()) {
						currentZoom = map.getZoom();
						int meterPerPixel = (int) (PIXEL_TO_CALCULATE_SCALE
								* (EARTH_CIRCUMFERENCE * Math.cos(Math
										.toRadians(map.getPosition(10,
												mapArea.getHeight() - 10)
												.getLat()))) / Math.pow(2,
								map.getZoom() + 8));

						meterPerPixelPanel.getMeterPerPixelLabel().setText("");
						meterPerPixelPanel.getMeterPerPixelLabel().setText(
								"        " + meterPerPixel + " m          ");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LOG.warn(e.getMessage());
					}
				}
			}
		};
		new Thread(recalculateScale).start();

		JPanel markerCheckBoxes = new JPanel();
		markerCheckBoxes.add(meterPerPixelPanel);
		markerCheckBoxes.add(markOnMap);
		markerCheckBoxes.add(markPolygon);

		mapArea.setLayout(new BorderLayout());
		mapArea.add(map, BorderLayout.CENTER);
		mapArea.add(markerCheckBoxes, BorderLayout.SOUTH);

		/*GUIModel model = new GUIModelImpl();

		List<Task> list = new ArrayList<Task>();
		List<GPS> points = new ArrayList<GPS>();
		points.add(FH_BRANDENBURG);
		points.add(REGATTASTRECKE);
		points.add(new GPS(FH_BRANDENBURG.getLatitude()
				+ REGATTASTRECKE.getLatitude(), FH_BRANDENBURG.getLatitude()
				+ REGATTASTRECKE.getLongitude()));
		list.add(new ReachPolygonTask(points));
		for (int i = 0; i <= 3; i++) {
			list.add(new ReachCircleTask(new GPS(REGATTASTRECKE.getLatitude()
					+ i, REGATTASTRECKE.getLongitude() + i), 0));
		}

		

		model.getMissionTasksLeft().setTasks(list);
		model.getCurrentWholeMission().setTasks(list);

		visualize.visualize(model);

		model.getMissionTasksLeft().getTasks().remove(0);

		visualize.visualize(model);*/

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

	/**
	 * Adds a point to the current list of points (currentPoly). When the
	 * checkbox for polygon-marking is deselected this list will be cleared and
	 * the polygon will be painted.
	 * 
	 * @param target
	 *            gps-point with coordinates
	 */
	public void addPointToPolygon(GPS target) {
		if (currentPoly == null) {
			currentPoly = new ArrayList<GPS>();
			currentPoly.add(target);
			polyHelpList.add(new MapMarkerDot(Color.BLACK,
					target.getLatitude(), target.getLongitude()));
			map.addMapMarker(polyHelpList.get(polyHelpList.size() - 1));
		} else {
			// if the current point to add has the same coordinates as the first
			// point in the polygon, finish this polygon, else add this point to
			// the current polygon
			if (currentPoly.get(0).getLatitude() == target.getLatitude()
					&& currentPoly.get(0).getLongitude() == target
							.getLongitude()) {
				MapPolygon builtPolygon = new MapPolygonImpl(
						ArrangePolygon.arrange(currentPoly), Color.BLACK,
						new BasicStroke(3));
				map.addMapPolygon(builtPolygon);
				polygonList.add(builtPolygon);
				for (int i = 0; i < polyHelpList.size(); i++)
					map.removeMapMarker(polyHelpList.get(i));
				currentPoly = null;
			} else {
				currentPoly.add(target);
				polyHelpList.add(new MapMarkerDot(Color.BLACK, target
						.getLatitude(), target.getLongitude()));
				map.addMapMarker(polyHelpList.get(polyHelpList.size() - 1));
			}
		}
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

		currentPosition = new MapMarkerDot(Color.LIGHT_GRAY,
				boatPosition.getLatitude(), boatPosition.getLongitude());

		map.addMapMarker(currentPosition);

		if (positionHistoryList.size() < 1) {
			positionHistoryList.add(new GPS(boatPosition.getLatitude(),
					boatPosition.getLongitude()));
		} else {
			if (positionHistoryList.size() == 1) {

				positionHistoryList.add(new GPS(boatPosition.getLatitude(),
						boatPosition.getLongitude()));

				for (int i = positionHistoryList.size() - 2; i >= 0; i--)
					positionHistoryList.add(positionHistoryList.get(i));

				positionHistory = new MapPolygonImpl(positionHistoryList,
						Color.LIGHT_GRAY, new BasicStroke(3));
				map.addMapPolygon(positionHistory);

			} else {
				if (followCounter == EVERY_X_GPS_POSITION) {
					map.removeMapPolygon(positionHistory);
					int j = (positionHistoryList.size() / 2) + 1;
					while (j < positionHistoryList.size())
						positionHistoryList.remove(j);

					positionHistoryList.add(new GPS(boatPosition.getLatitude(),
							boatPosition.getLongitude()));

					for (int i = positionHistoryList.size() - 2; i >= 0; i--)
						positionHistoryList.add(positionHistoryList.get(i));

					positionHistory = new MapPolygonImpl(positionHistoryList,
							Color.LIGHT_GRAY, new BasicStroke(3));
					followCounter = 0;
					map.addMapPolygon(positionHistory);
				} else
					followCounter++;
			}
		}
	}

	public void visualizeMission(GUIModel model) {
		visualize.visualize(model);
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

	/**
	 * Clear the map from artifacts.
	 */
	public void removeEveryObject() {
		removeMapMarkerFromMap();
		removePolygonsFromMap();
	}

	private void removeMapMarkerFromMap() {
		map.getMapMarkerList().clear();
		this.getMarkerList().clear();
	}

	private void removePolygonsFromMap() {
		map.getMapPolygonList().clear();
		this.getPolygonList().clear();
		currentPoly = null;
	}

	/**
	 * Move the map to a point at maximum zoomlevel.
	 * 
	 * @param gps
	 */
	private void navigateTo(GPS gps) {
		navigateTo(gps, 18);
	}

	/**
	 * Move the map to a point and to a given zoom.
	 * 
	 * @param gps
	 * @param zoomLevel
	 */
	private void navigateTo(GPS gps, int zoomLevel) {
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

	public List<MapPolygon> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(List<MapPolygon> polygonList) {
		this.polygonList = polygonList;
	}

	public List<GPS> getCurrentPoly() {
		return currentPoly;
	}

	public void setCurrentPoly(List<GPS> currentPoly) {
		this.currentPoly = currentPoly;
	}

}
