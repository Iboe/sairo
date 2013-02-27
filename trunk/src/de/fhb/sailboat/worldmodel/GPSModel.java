package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Sub-model which holds the data of the current GPS values along with a history of past GPS values. <br> 
 * Each set of GPS values is encapsulated within a {@link GPS} object.
 * @see {@link GPS}
 *  
 * @author Helge Scheel, Michael Kant
 */
public interface GPSModel {

	static final String HISTORY_SIZE_PROPERTY = 
		GPSModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	void setPosition(GPS position);
	GPS getPosition();
	List<GPS> getHistory();
}
