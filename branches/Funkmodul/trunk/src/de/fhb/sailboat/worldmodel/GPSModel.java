package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.GPS;

public interface GPSModel {

	static final String HISTORY_SIZE_PROPERTY = 
		GPSModel.class.getSimpleName() + History.MAX_SIZE_PROPERTY_NAME;
	
	void setPosition(GPS position);
	GPS getPosition();
	List<GPS> getHistory();
}
