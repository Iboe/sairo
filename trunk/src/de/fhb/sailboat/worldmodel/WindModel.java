package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Wind;

public interface WindModel {

	static final String HISTORY_SIZE_PROPERTY = 
		WindModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	void setWind(Wind wind);
	Wind getWind();
	List<Wind> getHistory();
}
