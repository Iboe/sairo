package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Compass;

/**
 * Sub-model which holds the data of the current compass values along with a history of past compass values. <br> 
 * Each set of compass values is encapsulated within a {@link Compass} object.
 * @see {@link Compass}
 *  
 * @author Helge Scheel, Michael Kant
 */
public interface CompassModel {

	static final String HISTORY_SIZE_PROPERTY = 
		CompassModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	void setCompass(Compass compass);
	Compass getCompass();
	List<Compass> getHistory();
}
