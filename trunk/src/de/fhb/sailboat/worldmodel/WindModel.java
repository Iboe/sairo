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
	
	void setWind(Wind wind);
	Wind getWind();
	List<Wind> getHistory();
	Wind calcAverageWind();
}
