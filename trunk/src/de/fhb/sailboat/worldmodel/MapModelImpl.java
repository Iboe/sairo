package de.fhb.sailboat.worldmodel;

import java.util.LinkedList;
import java.util.List;

import de.fhb.sailboat.data.MapPoint;

/**
 * Concrete implementation of the {@link MapModel}.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link MapModel}
 */
public class MapModelImpl implements MapModel {

	private List<MapPoint> mapPoints;
	
	public MapModelImpl() {
		mapPoints = new LinkedList<MapPoint>();
	}
	
	@Override
	public List<MapPoint> getMapPoints() {
		return mapPoints;
	}
	
	@Override
	public void addMapPoint(MapPoint point) {
		mapPoints.add(point);
	}

	@Override
	public void removeMapPoint(MapPoint point) {
		mapPoints.remove(point);
	}
}
