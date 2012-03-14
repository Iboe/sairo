package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.mission.Mission;

public interface WorldModel {

	ActuatorModel getActuatorModel();
	CompassModel getCompassModel();
	GPSModel getGPSModel();
	WindModel getWindModel();
	MapModel getMapModel();
	void setBatteryState(int batteryState);
	int getBatteryState();
	Mission getMission();
}
