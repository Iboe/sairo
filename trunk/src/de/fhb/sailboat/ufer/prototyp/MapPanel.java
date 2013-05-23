package de.fhb.sailboat.ufer.prototyp;

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

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MapPolygon;
import de.fhb.sailboat.gui.map.MapPolygonImpl;
import de.fhb.sailboat.gui.map.utilities.ArrangePolygon;
import de.fhb.sailboat.gui.map.utilities.GPSTransformations;
import de.fhb.sailboat.gui.map.utilities.JMapViewer;
import de.fhb.sailboat.gui.map.utilities.ScalePanel;
/**
 * 
 * @author Paul Lehmann
 *
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int NO_MARK = 0;
	private static final int MARKER = 1;
	private static final int POLYGON = 2;
	private static final int MAXIMUM_COUNT_LAST_POSITION = 30;
	public static final int PIXEL_TO_CALCULATE_SCALE = 80;
	private static final int EARTH_CIRCUMFERENCE = 40074000;
	private static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745,System.currentTimeMillis());
	private static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414,System.currentTimeMillis());

	final static int P_MAP_X = 412;
	final static int P_MAP_Y = 4;
	final static int P_MAP_WIDTH = 435;
	final static int P_MAP_HEIGHT = 435;

	private ArrayList<MapMarker> markerList;
	private ArrayList<MapMarker> positionHistory = new ArrayList<MapMarker>();
	private ArrayList<MapMarker> polyHelpList = new ArrayList<MapMarker>();
	private ArrayList<MapPolygon> polygonList;
	private JMapViewer map;
	private int markerMode = NO_MARK;

	private Coordinate firstCorner = null;
	private List<GPS> currentPoly = null;

	private int currentZoom;

	public MapPanel() {
		this.map = new JMapViewer();
		this.markerList = new ArrayList<MapMarker>();
		this.polygonList = new ArrayList<MapPolygon>();
	}

	public JPanel mapPanel() {
		final JPanel mapArea = new JPanel();
		mapArea.setOpaque(true);
		mapArea.setBounds(P_MAP_X, P_MAP_Y, P_MAP_WIDTH, P_MAP_HEIGHT);
		mapArea.setBorder(new javax.swing.border.BevelBorder(
				javax.swing.border.BevelBorder.RAISED));
		setLayout(null);

		// Startposition auf FH gestellt

		// navigateTo(GPSToCoordinate.gpsToCoordinate(FH_BRANDENBURG));

		navigateTo(GPSTransformations.gpsToCoordinate(REGATTASTRECKE));

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

					/*
					 * case 1: { int g = 0; if (markerList.size() >= 10) { g =
					 * 255; } else { g = (int) ((markerList.size() * 255) / 9);
					 * } Color markerColor = new Color(0, g, 0);
					 * markerList.add(new MapMarkerDot(markerColor, target
					 * .getLat(), target.getLon()));
					 * map.addMapMarker(markerList.get(markerList.size() - 1));
					 * } break;
					 */

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
					/*
					 * Coordinate currentPosition =
					 * map.getPosition(map.getWidth()/2, map.getHeight()/2); int
					 * currentZoomLevel = map.getZoom();
					 * map.setDisplayPositionByLatLon(currentPosition.getLat(),
					 * currentPosition.getLon(), currentZoomLevel);
					 */
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
					if (currentPoly != null) {
						addPointToPolygon(currentPoly.get(0));
					}
				}
			}
		});

		Runnable recalculateScale = new Runnable() {
			public void run() {
				while (true) {
					if (currentZoom != map.getZoom()) {
						currentZoom = map.getZoom();
						int meterPerPixel = (int) (PIXEL_TO_CALCULATE_SCALE
								* (EARTH_CIRCUMFERENCE * Math
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
		mapArea.add(map, BorderLayout.NORTH);
		mapArea.add(markerCheckBoxes, BorderLayout.SOUTH);

		return mapArea;
	}


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
			} else {
				currentPoly.add(target);
				polyHelpList.add(new MapMarkerDot(Color.BLACK, target
						.getLatitude(), target.getLongitude()));
				map.addMapMarker(polyHelpList.get(polyHelpList.size() - 1));
			}
		}
	}

	/**
	 * Adds MapMarker to the last MAXIMUM_COUNT_LAST_POSITION positions of the
	 * boat.
	 * 
	 * @param boatPosition
	 *            current position of the boat
	 */

	public void followBoat(GPS boatPosition) {
		if (boatPosition.getLatitude() != 0.0) {
			positionHistory.add(new MapMarkerDot(Color.DARK_GRAY, boatPosition
					.getLatitude(), boatPosition.getLongitude()));

			if (positionHistory.size() > MAXIMUM_COUNT_LAST_POSITION) {
				map.removeMapMarker(positionHistory.get(0));
				positionHistory.remove(0);
			}

			map.addMapMarker(positionHistory.get(positionHistory.size() - 1));
		}
	}

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
	}

	private void removeRectanglesFromMap() {
		map.getMapRectangleList().clear();
	}

	private void navigateTo(Coordinate coor) {
		navigateTo(coor, 18);
	}

	private void navigateTo(Coordinate coor, int zoomLevel) {
		map.setDisplayPositionByLatLon(coor.getLat(), coor.getLon(), zoomLevel);
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

	public ArrayList<MapMarker> getPositionHistory() {
		return positionHistory;
	}

	public void setPositionHistory(ArrayList<MapMarker> positionHistory) {
		this.positionHistory = positionHistory;
	}

	public JMapViewer getMap() {
		return map;
	}

	public void setMap(JMapViewer map) {
		this.map = map;
	}

	public ArrayList<MapMarker> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(ArrayList<MapMarker> markerList) {
		this.markerList = markerList;
	}

	public ArrayList<MapPolygon> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(ArrayList<MapPolygon> polygonList) {
		this.polygonList = polygonList;
	}
}
