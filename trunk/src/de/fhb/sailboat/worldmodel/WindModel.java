package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Wind;

/**
 * Sub-model which holds the data of the current wind values along with a history of past wind values. <br> 
 * Each set of wind values is encapsulated within a {@link Wind} object.
 * @see {@link Wind}
 *  
 * @author Helge Scheel, Michael Kant
 */
public interface WindModel {

	static final String HISTORY_SIZE_PROPERTY = 
		WindModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	/**
	 * Sets the {@link Wind} object which contains the current wind values.
	 * @param compass The {@link Wind} object which contains the current wind values.
	 */
	void setWind(Wind wind);
	
	/**
	 * Returns the {@link Wind} object which contains the current wind values.
	 * @return The {@link Wind} object which contains the current wind values.
	 */
	Wind getWind();
	
	/**
	 * Returns the history of past {@link Wind} objects, which were set by {@link #setWind(Wind)}.
	 * @return The history of past {@link Wind} objects, which were set by {@link #setWind(Wind)}.
	 */
	List<Wind> getHistory();
	
	/**
	 * Calculates the average wind, using the past wind values of the wind history. 
	 * 
	 * @return {@link Wind} object, which contains the average wind.
	 */
	Wind calcAverageWind();
}
