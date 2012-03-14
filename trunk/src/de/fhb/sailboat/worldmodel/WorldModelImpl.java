package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;

public class WorldModelImpl implements WorldModel {

	private static final WorldModelImpl instance = new WorldModelImpl();
	
	private ActuatorModel actuatorModel;
	private CompassModel compassModel;
	private GPSModel gpsModel;
	private WindModel windModel;
	private MapModel mapModel;
	private int batteryState;
	private Mission mission;
	
	private WorldModelImpl() {
		actuatorModel = new ActuatorModelImpl();
		compassModel = new CompassModelImpl();
		gpsModel = new GPSModelImpl();
		windModel = new WindModelImpl();
		mapModel = new MapModelImpl();
		mission = new MissionImpl();
	}

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
	
}
