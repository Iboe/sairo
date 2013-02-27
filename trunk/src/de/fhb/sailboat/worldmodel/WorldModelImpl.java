package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;

/**
 * Concrete implementation of the {@link WorldModel}.<br>
 * Due to its singleton implementation, there can just exist <b>one</b> instance of the {@link WorldModel} within this application.<br>
 * That instance is shared by all objects. Therefore all objects share the same world model.<br>
 * The world model itself is <b>not</b> thread safe. That must be ensured by the referenced sub-models.<br>
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link WorldModel}
 */
public class WorldModelImpl implements WorldModel {

	private static final WorldModelImpl instance = new WorldModelImpl();
	
	private ActuatorModel actuatorModel;
	private CompassModel compassModel;
	private GPSModel gpsModel;
	private WindModel windModel;
	private MapModel mapModel;
	private int batteryState;
	private Mission mission;
	
	/**
	 * Default constructor, which creates instances of all sub-models to be referenced.
	 */
	private WorldModelImpl() {
		actuatorModel = new ActuatorModelImpl();
		compassModel = new CompassModelImpl();
		gpsModel = new GPSModelImpl();
		windModel = new WindModelImpl();
		mapModel = new MapModelImpl();
		mission = new MissionImpl();
	}

	/**
	 * Getting the one instance that exists within this application.
	 * 
	 * @return The {@link WorldModel} instance.
	 */
	public static WorldModelImpl getInstance() {
		return instance;
	}
	
	@Override
	public ActuatorModel getActuatorModel() {
		return actuatorModel;
	}

	@Override
	public CompassModel getCompassModel() {
		return compassModel;
	}

	@Override
	public GPSModel getGPSModel() {
		return gpsModel;
	}

	@Override
	public WindModel getWindModel() {
		return windModel;
	}
	
	@Override
	public MapModel getMapModel() {
		return mapModel;
	}

	@Override
	public void setBatteryState(int batteryState) {
		this.batteryState = batteryState;
	}

	@Override
	public int getBatteryState() {
		return batteryState;
	}

	@Override
	public Mission getMission() {
		return mission;
	}

	@Override
	public void setMission(Mission mission) {
		this.mission = mission;
	}
}
