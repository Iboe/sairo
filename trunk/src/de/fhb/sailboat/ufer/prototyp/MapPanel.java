package de.fhb.sailboat.ufer.prototyp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.OsmTileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapRectangle;
import org.openstreetmap.gui.jmapviewer.interfaces.TileLoader;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.ufer.prototyp.utility.MapRectangleImpl;

public class MapPanel extends JPanel {

	private final int MAXIMUM_COUNT_LAST_POSITION = 30;

	private static final long serialVersionUID = 1L;

	final static int P_MAP_X = 412;
	final static int P_MAP_Y = 4;
	final static int P_MAP_WIDTH = 435;
	final static int P_MAP_HEIGHT = 535;

	private ArrayList<MapMarker> markerList;
	private ArrayList<MapMarker> positionHistory = new ArrayList<MapMarker>();
	private ArrayList<MapRectangle> rectList;
	private JMapViewer map;
	private boolean inMarkerMarkMode = false;
	private boolean inRectMarkMode = false;

	private Coordinate firstCorner = null;

	public MapPanel() {
		this.markerList = new ArrayList<MapMarker>();
		this.rectList = new ArrayList<MapRectangle>();
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
		
		map.setDisplayPositionByLatLon(52.410771, 12.538745, 18);

		map.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				Coordinate target = map.getPosition(arg0.getPoint());
				if (inMarkerMarkMode) {
					
					for (int i = 0; i < rectList.size(); i++) {
						map.removeMapRectangle(rectList.get(i));
					}

					markerList.add(new MapMarkerDot(Color.GREEN, target
							.getLat(), target.getLon()));

					map.addMapMarker(markerList.get(markerList.size() - 1));

				}

				if (inRectMarkMode) {

					for (int i = 0; i < markerList.size(); i++) {
						map.removeMapMarker(markerList.get(i));
					}

					addRectsToMap(target);

				}

				/*
				 * Coordinate currentPosition =
				 * map.getPosition(map.getWidth()/2, map.getHeight()/2); int
				 * currentZoomLevel = map.getZoom();
				 * map.setDisplayPositionByLatLon(currentPosition.getLat(),
				 * currentPosition.getLon(), currentZoomLevel);
				 */
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

		JComboBox tileSourceSelector = new JComboBox(new TileSource[] {
				new OsmTileSource.Mapnik(), new OsmTileSource.TilesAtHome(),
				new OsmTileSource.CycleMap() });
		tileSourceSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				map.setTileSource((TileSource) e.getItem());
			}
		});
		JComboBox tileLoaderSelector;
		try {
			tileLoaderSelector = new JComboBox(new TileLoader[] {
					new OsmFileCacheTileLoader(map), new OsmTileLoader(map) });
		} catch (IOException e) {
			tileLoaderSelector = new JComboBox(
					new TileLoader[] { new OsmTileLoader(map) });
		}
		tileLoaderSelector.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				map.setTileLoader((TileLoader) e.getItem());
			}
		});
		map.setTileLoader((TileLoader) tileLoaderSelector.getSelectedItem());

		final JCheckBox markOnMap = new JCheckBox("Add Marker");
		final JCheckBox markRectOnMap = new JCheckBox("Add Rectangle");

		markOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				inMarkerMarkMode = markOnMap.isSelected();
				markOnMap.setSelected(inMarkerMarkMode);
				if (inRectMarkMode) {
					inRectMarkMode = false;
					markRectOnMap.setSelected(inRectMarkMode);
				}
			}
		});

		markRectOnMap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				inRectMarkMode = markRectOnMap.isSelected();
				markRectOnMap.setSelected(inRectMarkMode);
				if (inMarkerMarkMode) {
					inMarkerMarkMode = false;
					markOnMap.setSelected(inMarkerMarkMode);
				}
			}
		});

		JPanel selectors = new JPanel();
		selectors.add(tileSourceSelector);
		selectors.add(tileLoaderSelector);
		selectors.setBounds(P_MAP_X, 0, P_MAP_WIDTH, 50);

		JPanel markerCheckBoxes = new JPanel();
		markerCheckBoxes.add(markOnMap);
		markerCheckBoxes.add(markRectOnMap);

		mapArea.setLayout(new BorderLayout());
		mapArea.add(selectors, BorderLayout.NORTH);
		mapArea.add(map, BorderLayout.CENTER);
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

		if (positionHistory.size() > MAXIMUM_COUNT_LAST_POSITION) {
			map.removeMapMarker(positionHistory.get(0));
			positionHistory.remove(0);
		}

		map.addMapMarker(positionHistory.get(positionHistory.size() - 1));
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

	public boolean isInMarkerMarkMode() {
		return inMarkerMarkMode;
	}

	public void setInMarkerMarkMode(boolean inMarkerMarkMode) {
		this.inMarkerMarkMode = inMarkerMarkMode;
	}

	public boolean isInRectMarkMode() {
		return inRectMarkMode;
	}

	public void setInRectMarkMode(boolean inRectMarkMode) {
		this.inRectMarkMode = inRectMarkMode;
	}
}
