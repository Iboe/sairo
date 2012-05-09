package de.fhb.sailboat.ufer.prototyp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapRectangle;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.ufer.prototyp.utility.Constants;
import de.fhb.sailboat.ufer.prototyp.utility.GPSToCoordinate;
import de.fhb.sailboat.ufer.prototyp.utility.MapRectangleImpl;
import de.fhb.sailboat.ufer.prototyp.utility.ScalePanel;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final static int P_MAP_X = 412;
	final static int P_MAP_Y = 4;
	final static int P_MAP_WIDTH = 435;
	final static int P_MAP_HEIGHT = 435;

	private ArrayList<MapMarker> markerList;
	private ArrayList<MapMarker> positionHistory = new ArrayList<MapMarker>();
	private ArrayList<MapRectangle> rectList;
	private ArrayList<MapMarker> polygon;
	private JMapViewer map;
	private int markerMode = Constants.NO_MARK;

	private Coordinate firstCorner = null;

	private int currentZoom;

	public MapPanel() {
		this.markerList = new ArrayList<MapMarker>();
		this.rectList = new ArrayList<MapRectangle>();
		this.polygon = new ArrayList<MapMarker>();
		this.map = new JMapViewer();
	}

	public MapPanel(ArrayList<MapMarker> markerList,
			ArrayList<MapRectangle> rectList) {
		this.markerList = markerList;
		this.rectList = rectList;
		this.map = new JMapViewer();
	}

	public JPanel mapPanel() {
		JPanel mapArea = new JPanel();
		mapArea.setOpaque(true);
		mapArea.setBounds(P_MAP_X, P_MAP_Y, P_MAP_WIDTH, P_MAP_HEIGHT);
		mapArea.setBorder(new javax.swing.border.BevelBorder(
				javax.swing.border.BevelBorder.RAISED));
		setLayout(null);

		// Startposition auf FH gestellt

		// navigateTo(GPSToCoordinate.gpsToCoordinate(Constants.FH_BRANDENBURG));

		navigateTo(GPSToCoordinate.gpsToCoordinate(Constants.REGATTASTRECKE));

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

					case 1: {
						markerList.add(new MapMarkerDot(Color.GREEN, target
								.getLat(), target.getLon()));
						map.addMapMarker(markerList.get(markerList.size() - 1));
					}
						break;

					case 2:
						addRectsToMap(target);
						break;

					case 3: {
						polygon.add(new MapMarkerDot(Color.RED,
								target.getLat(), target.getLon()));
						map.addMapMarker(polygon.get(polygon.size() - 1));
					}
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
		final JCheckBox markRectOnMap = new JCheckBox("Add Rectangle");
		final JCheckBox markPolygon = new JCheckBox("Add Polygon");
		final ScalePanel meterPerPixelPanel = new ScalePanel();

		markOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markOnMap.isSelected()) {
					markerMode = Constants.MARKER;
					markRectOnMap.setSelected(false);
					markPolygon.setSelected(false);
					removeEveryObject();
				} else {
					markerMode = Constants.NO_MARK;
				}
			}
		});

		markRectOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (markRectOnMap.isSelected()) {
					markerMode = Constants.RECTANGLE;
					markOnMap.setSelected(false);
					markPolygon.setSelected(false);
					removeEveryObject();
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
					markRectOnMap.setSelected(false);
					removeEveryObject();
				} else {
					markerMode = Constants.NO_MARK;
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
												P_MAP_HEIGHT - 10).getLat()))) / Math
								.pow(2, map.getZoom() + 8));

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
		markerCheckBoxes.add(markRectOnMap);
		markerCheckBoxes.add(markPolygon);

		mapArea.setLayout(new BorderLayout());
		mapArea.add(map, BorderLayout.NORTH);
		mapArea.add(markerCheckBoxes, BorderLayout.SOUTH);

		return mapArea;
	}

	/**
	 * Adds a rectangle to the map. If firstCorner isn't set yet firstCorner
	 * will be set by target (current click). If firstCorner is set, target will
	 * be the second corner of the rectangle and the rectangle will be
	 * displayed.
	 * 
	 * @param target
	 *            coordinates of the last click
	 */
	private void addRectsToMap(Coordinate target) {
		if (firstCorner == null)
			firstCorner = target;
		else {
			Coordinate topLeft = new Coordinate(Math.max(firstCorner.getLat(),
					target.getLat()), Math.min(firstCorner.getLon(),
					target.getLon()));

			Coordinate bottomRight = new Coordinate(Math.min(
					firstCorner.getLat(), target.getLat()), Math.max(
					firstCorner.getLon(), target.getLon()));

			MapRectangleImpl rectangle = new MapRectangleImpl(topLeft,
					bottomRight);

			rectList.add(rectangle);

			map.addMapRectangle(rectList.get(rectList.size() - 1));

			firstCorner = null;
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
		positionHistory.add(new MapMarkerDot(Color.DARK_GRAY, boatPosition
				.getLatitude(), boatPosition.getLongitude()));

		if (positionHistory.size() > Constants.MAXIMUM_COUNT_LAST_POSITION) {
			map.removeMapMarker(positionHistory.get(0));
			positionHistory.remove(0);
		}

		map.addMapMarker(positionHistory.get(positionHistory.size() - 1));
	}

	// FIXME Set this to public so I can reset the Map manually through View -
	// Patrick
	public void removeEveryObject() {
		removeMapMarkerFromMap();
		removePolygonsFromMap();
		removeRectanglesFromMap();
	}

	private void removeMapMarkerFromMap() {
		for (int i = this.markerList.size() - 1; i >= 0; i--) {
			this.map.removeMapMarker(this.markerList.get(i));
		}
		this.markerList = new ArrayList<MapMarker>();
	}

	private void removePolygonsFromMap() {
		for (int i = this.polygon.size() - 1; i >= 0; i--) {
			this.map.removeMapMarker(this.polygon.get(i));
		}
		this.polygon = new ArrayList<MapMarker>();
	}

	private void removeRectanglesFromMap() {
		for (int i = this.rectList.size() - 1; i >= 0; i--) {
			this.map.removeMapRectangle(this.rectList.get(i));
		}
		this.rectList = new ArrayList<MapRectangle>();
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

	public ArrayList<MapMarker> getMarkerList() {
		return markerList;
	}

	public void setMarkerList(ArrayList<MapMarker> markerList) {
		this.markerList = markerList;
	}

	public ArrayList<MapMarker> getPositionHistory() {
		return positionHistory;
	}

	public void setPositionHistory(ArrayList<MapMarker> positionHistory) {
		this.positionHistory = positionHistory;
	}

	public ArrayList<MapRectangle> getRectList() {
		return rectList;
	}

	public void setRectList(ArrayList<MapRectangle> rectList) {
		this.rectList = rectList;
	}

	public JMapViewer getMap() {
		return map;
	}

	public void setMap(JMapViewer map) {
		this.map = map;
	}

	public ArrayList<MapMarker> getPolygon() {
		return polygon;
	}

	public void setPolygon(ArrayList<MapMarker> polygon) {
		this.polygon = polygon;
	}

}
