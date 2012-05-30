package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.MapPoint;

public interface MapModel {

	List<MapPoint> getMapPoints();
	void addMapPoint(MapPoint point);
	void removeMapPoint(MapPoint point);
}
