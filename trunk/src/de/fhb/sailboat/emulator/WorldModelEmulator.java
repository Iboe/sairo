package de.fhb.sailboat.emulator;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.worldmodel.ActuatorModel;
import de.fhb.sailboat.worldmodel.ActuatorModelImpl;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.CompassModelImpl;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.GPSModelImpl;
import de.fhb.sailboat.worldmodel.MapModel;
import de.fhb.sailboat.worldmodel.MapModelImpl;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WindModelImpl;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class WorldModelEmulator implements WorldModel {
	
	private static final WorldModelEmulator instance = new WorldModelEmulator();
	
	private ActuatorModel actuatorModel;
	private CompassModel compassModel;
	private GPSModel gpsModel;
	private WindModel windModel;
	private MapModel mapModel;
	public GPSModel getGpsModel() {
		return gpsModel;
	}


	public void setGpsModel(GPSModel gpsModel) {
		this.gpsModel = gpsModel;
	}


	public void setCompassModel(CompassModel compassModel) {
		this.compassModel = compassModel;
	}


	public void setWindModel(WindModel windModel) {
		this.windModel = windModel;
	}


	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	private int batteryState;
	private Mission mission;

	public WorldModelEmulator() {
		actuatorModel = new ActuatorModelImpl();
		compassModel = new CompassModelImpl();
		gpsModel = new GPSModelImpl();
		windModel = new WindModelImpl();
		mapModel = new MapModelImpl();
		mission = new MissionImpl();
	}

	
	@Override
	public ActuatorModel getActuatorModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompassModel getCompassModel() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public GPSModel getGPSModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WindModel getWindModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapModel getMapModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBatteryState(int batteryState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBatteryState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Mission getMission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMission(Mission mission) {
		// TODO Auto-generated method stub
		
	}

}
