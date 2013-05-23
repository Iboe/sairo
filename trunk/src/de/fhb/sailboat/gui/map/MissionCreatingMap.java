package de.fhb.sailboat.gui.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.OsmFileCacheTileLoader;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.utilities.GPSTransformations;

/**
 * Class for dividing between a map for representing in the GUI and a map
 * recognizing the interactions with it. Not sure if this should be done or
 * not..
 * 
 * @author Paul Lehmann
 * 
 */

public class MissionCreatingMap extends GeneralMap {

	private static final long serialVersionUID = 1L;

	//private static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745);
	private static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414,System.currentTimeMillis());

	private static final Logger LOG = LoggerFactory
			.getLogger(MissionCreatingMap.class);

	private int markerMode = 0;

	private MapPolygon currentPoly;
	private List<GPS> polyList = new ArrayList<GPS>();

	public MissionCreatingMap() {
		super();
	}

	/**
	 * Returns usable Panel with the map in it.
	 * 
	 * @param mapArea
	 *            Panel in which the map shall be displayed
	 * @return usable Panel with the map in it
	 */
	@Override
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
						clearMarkerList();
						addMapMarker(GPSTransformations.coordinateToGPS(target));
						break;

					case 1:
						if (currentPoly != null)
							map.removeMapPolygon(currentPoly);
						addPointToPolygon(GPSTransformations
								.coordinateToGPS(target));
						break;

					case 2:
						addObstacle(GPSTransformations.coordinateToGPS(target));
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

		mapArea.setLayout(new BorderLayout());
		mapArea.add(map, BorderLayout.CENTER);

		return mapArea;
	}

	/**
	 * Adds point to polygon.
	 * 
	 * @param target
	 *            next polygon point
	 */
	public void addPointToPolygon(GPS target) {
		polyList.add(target);
		markerList.add(new MapMarkerDot(Color.RED, target.getLatitude(), target
				.getLongitude()));
		map.addMapMarker(markerList.get(markerList.size() - 1));
		currentPoly = new MapPolygonImpl(polyList, Color.BLACK);
		map.addMapPolygon(currentPoly);
	}

	/**
	 * Clears marker list.
	 */
	private void clearMarkerList() {
		if (this.markerList.size() > 0) {
			map.removeMapMarker(this.markerList.get(0));
			this.markerList.remove(0);
		}
	}

	/**
	 * Clears Map.
	 */
	public void clear() {
		map.removeMapMarker(this.markerList.get(0));
		clearMarkerList();

		map.removeMapPolygon(currentPoly);
		currentPoly = null;
	}

	public int getMarkerMode() {
		return markerMode;
	}

	public void setMarkerMode(int markerMode) {
		this.markerMode = markerMode;
	}

	public MapPolygon getCurrentPoly() {
		return currentPoly;
	}

	public void setCurrentPoly(MapPolygon currentPoly) {
		this.currentPoly = currentPoly;
	}
}
