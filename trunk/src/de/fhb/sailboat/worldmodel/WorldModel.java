package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.mission.Mission;

/**
 * Interface for the central world model, which has references to all sub-models and the currently running mission.
 *  
 * @author Helge Scheel, Michael Kant
 */
public interface WorldModel {

	/**
	 * Returns the {@link ActuatorModel} instance.
	 * @return The {@link ActuatorModel} instance.
	 */
	ActuatorModel getActuatorModel();
	
	/**
	 * Returns the {@link CompassModel} instance.
	 * @return The {@link CompassModel} instance.
	 */
	CompassModel getCompassModel();
	
	/**
	 * Returns the {@link GPSModel} instance.
	 * @return The {@link GPSModel} instance.
	 */
	GPSModel getGPSModel();
	
	/**
	 * Returns the {@link WindModel} instance.
	 * @return The {@link WindModel} instance.
	 */
	WindModel getWindModel();
	
	/**
	 * Returns the {@link MapModel} instance.
	 * @return The {@link MapModel} instance.
	 */
	MapModel getMapModel();
	
	/**
	 * Setting the system's battery state. <br>
	 * The interpretation of that value depends on the component that's acquiring the battery state.
	 *  
	 * @param batteryState The battery state to set
	 */
	void setBatteryState(int batteryState);
	
	/**
	 * Getting the system's battery state. <br>
	 * The interpretation of that value depends on the component that's acquiring the battery state.
	 * 
	 * @return The system's battery state.
	 */
	int getBatteryState();
	
	/**
	 * Returns the currently running {@link Mission} instance.
	 * @return The currently running {@link Mission} instance.
	 */
	Mission getMission();
	
	/**
	 * Setting the {@link Mission} that's executed.
	 * @param mission The {@link Mission} that's executed.
	 */
	void setMission(Mission mission);
}
