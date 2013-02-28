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
	
	/**
	 * Sets the {@link GPS} object which contains the coordinates of the current position.
	 * @param compass The {@link GPS} object which contains the coordinates of the current position.
	 */
	void setPosition(GPS position);
	
	/**
	 * Returns the {@link GPS} object which contains the coordinates of the current position.
	 * @return The {@link GPS} object which contains the coordinates of the current position.
	 */
	GPS getPosition();
	
	/**
	 * Returns the history of past {@link GPS} objects, which were set by {@link #setPosition(GPS)}.
	 * @return The history of past {@link GPS} objects, which were set by {@link #setPosition(GPS)}.
	 */
	List<GPS> getHistory();
}
