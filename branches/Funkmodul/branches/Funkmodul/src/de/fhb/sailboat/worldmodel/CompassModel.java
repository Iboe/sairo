package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Compass;

public interface CompassModel {

	static final String HISTORY_SIZE_PROPERTY = 
		CompassModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	void setCompass(Compass compass);
	Compass getCompass();
	List<Compass> getHistory();
}
