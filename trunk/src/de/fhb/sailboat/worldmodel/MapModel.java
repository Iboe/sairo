package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.MapPoint;

/**
 * Sub-model which represents an internal topological map, consisting of a list of map points, which can represent miscellaneous world objects.<br> 
 * Each map point is represented by {@link MapPoint} object.
 * 
 * @see {@link MapPoint}
 *  
 * @author Helge Scheel, Michael Kant
 */
public interface MapModel {

	/**
	 * Returns all {@link MapPoint} objects that were added.
	 * 
	 * @return All {@link MapPoint} objects that were added.
	 */
	List<MapPoint> getMapPoints();
	
	/**
	 * Adding the given {@link MapPoint} object to the {@link MapModel}
	 * @param point The {@link MapPoint} to add.
	 */
	void addMapPoint(MapPoint point);
	
	/**
	 * Removing the given {@link MapPoint} object from the {@link MapModel}
	 * @param point The {@link MapPoint} to remove.
	 */
	void removeMapPoint(MapPoint point);
}
