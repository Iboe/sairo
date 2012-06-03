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
import org.openstreetmap.gui.jmapviewer.interfaces.MapRectangle;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import de.fhb.sailboat.data.GPS;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<MapMarker> markerList;
	private List<MapMarker> positionHistory = new ArrayList<MapMarker>();
	private List<MapMarker> polyHelpList = new ArrayList<MapMarker>();
	private List<MapPolygon> polygonList;
	private JMapViewer map;
	private int markerMode = Constants.NO_MARK;

	private Coordinate firstCorner = null;
	private List<GPS> currentPoly = null;

	private int currentZoom;

	public Map() {
		this.map = new JMapViewer();
		this.markerList = new ArrayList<MapMarker>();
		this.polygonList = new ArrayList<MapPolygon>();
	}

	public JPanel mapPanel(final javax.swing.JPanel mapArea) {
		// Startposition auf FH gestellt
		// navigateTo(Constants.FH_BRANDENBURG);

		navigateTo(Constants.REGATTASTRECKE);

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
						markerList.add(new MapMarkerDot(Color.RED, target
								.getLat(), target.getLon()));
						map.addMapMarker(markerList.get(markerList.size() - 1));
						break;

					case 2:
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
			e.printStackTrace();
		}

		final JCheckBox markOnMap = new JCheckBox("Add Marker");
		final JCheckBox markPolygon = new JCheckBox("Add Polygon");
		final ScalePanel meterPerPixelPanel = new ScalePanel();

		markOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markOnMap.isSelected()) {
					markerMode = Constants.MARKER;
					markPolygon.setSelected(false);
				} else {
					markerMode = Constants.NO_MARK;
				}
			}
		});

		markPolygon.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markPolygon.isSelected()) {
					markerMode = Constants.POLYGON;
					markOnMap.setSelected(false);
				} else {
					markerMode = Constants.NO_MARK;
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
						int meterPerPixel = (int) (Constants.PIXEL_TO_CALCULATE_SCALE
								* (Constants.EARTH_CIRCUMFERENCE * Math
										.cos(Math.toRadians(map.getPosition(10,
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
						e.printStackTrace();
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

		return mapArea;
	}

	/**
	 * Adds a point to the current list of points (currentPoly). When the
	 * checkbox for polygon-marking is deselected this list will be cleared and
	 * the polygon will be painted.
	 * 
	 * @param target
	 *            gps-point with coordinates
	 */
	private void addPointToPolygon(GPS target) {
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
				for (int i = 0; i < polyHelpList.size(); i++) {
					map.removeMapMarker(polyHelpList.get(i));
				}
				currentPoly = null;
				System.out.println(builtPolygon);
			} else {
				currentPoly.add(target);
				polyHelpList.add(new MapMarkerDot(Color.BLACK, target
						.getLatitude(), target.getLongitude()));
				map.addMapMarker(polyHelpList.get(polyHelpList.size() - 1));
			}
		}
	}

	/**
	 * Adds MapMarker for the last MAXIMUM_COUNT_LAST_POSITION positions of the
	 * boat.
	 * 
	 * @param boatPosition
	 *            current position of the boat
	 */
	public void followBoat(GPS boatPosition) {
		if (boatPosition.getLatitude() != 0.0) {
			positionHistory.add(new MapMarkerDot(Color.DARK_GRAY, boatPosition
					.getLatitude(), boatPosition.getLongitude()));

			if (positionHistory.size() > Constants.MAXIMUM_COUNT_LAST_POSITION) {
				map.removeMapMarker(positionHistory.get(0));
				positionHistory.remove(0);
			}

			map.addMapMarker(positionHistory.get(positionHistory.size() - 1));
		}
	}

	/**
	 * Draw a line from gps-position a to gps-position b.
	 * 
	 * @param a
	 *            src
	 * @param b
	 *            dest
	 */
	public void paintLine(GPS a, GPS b) {
		List<GPS> list = new ArrayList<GPS>();
		list.add(a);
		list.add(b);
		map.addMapPolygon(new MapPolygonImpl(list, Color.LIGHT_GRAY,
				new BasicStroke(3)));
	}

	/**
	 * Clear the map from artifacts.
	 */
	public void removeEveryObject() {
		removeMapMarkerFromMap();
		removePolygonsFromMap();
		removeRectanglesFromMap();
	}

	private void removeMapMarkerFromMap() {
		map.getMapMarkerList().clear();
	}

	private void removePolygonsFromMap() {
		map.getMapPolygonList().clear();
		currentPoly = null;
	}

	private void removeRectanglesFromMap() {
		map.getMapRectangleList().clear();
	}

	/**
	 * Move the map to a point at current zoomlevel.
	 * 
	 * @param gps
	 */
	private void navigateTo(GPS gps) {
		navigateTo(gps, map.getZoom());
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

	public List<MapMarker> getPositionHistory() {
		return positionHistory;
	}

	public void setPositionHistory(List<MapMarker> positionHistory) {
		this.positionHistory = positionHistory;
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
}
