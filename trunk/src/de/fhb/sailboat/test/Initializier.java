package de.fhb.sailboat.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommTCPServer;
import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.serverModules.CompassTransmitter;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.control.PilotImpl;
import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.control.PlannerImpl;
import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.serial.sensor.OS500sensor;
import de.fhb.sailboat.ufer.prototyp.View;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class Initializier {

	private static final Logger LOG = LoggerFactory.getLogger(Initializier.class);
	
	private static final boolean TEST = true;
	private static final String CONFIG_FILE = "config.properties";
	private Planner planner;
	private View view;
	
	public static void main(String[] args) {
		Initializier init = new Initializier();
		PropertiesInitializer propsInit = new PropertiesInitializer();
		
		propsInit.initializeProperties();
		init.initializeControl();
		
		if (TEST) {
			init.setSensorDummyValues();
			init.initializeCommunication();
			//init.createDummyMission();
			
		} else {
			init.initializeSensors();
			init.initializeView();
		}
	}
	
	private void setSensorDummyValues() {
		
		WorldModel worldModel = WorldModelImpl.getInstance();
		
		worldModel.getGPSModel().setPosition(new GPS(52.246555,12.323096));
		worldModel.getCompassModel().setCompass(new Compass(175, 0, 0));
	}
	
	private void initializeSensors() {
		LOG.info("-----init sensors-----");
		GpsSensor gps=new GpsSensor("COM8");
		OS500sensor compassSensor=new OS500sensor(); //zzt. COM17
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOG.info("-----init sensors done-----");
	}
	
	private void initializeControl() {
		Pilot pilot;
		
		if (TEST) {
			pilot = new PilotImpl(new DummyLoco());
		} else {
			pilot = new PilotImpl(new AKSENLocomotion());
		}
		
		Navigator navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	private void initializeCommunication() {
		
		CommunicationBase server=new CommTCPServer(6699);
		server.registerModule(new GPSTransmitter());
		server.registerModule(new CompassTransmitter());
		if(!server.initialize())
			LOG.warn("Unable to start the communications TCP server on port 6699");
	}
	
	private void initializeView() {
		view = new View(planner);
		view.setVisible(true);
	}
	
	private void createDummyMission() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		final GPS position = worldModel.getGPSModel().getPosition();
		Mission mission = new MissionImpl();
		List<Task> tasks = new LinkedList<Task>();
		
		if (position != null) {
			GPS goal = new GPS(position.getLatitude() + 200, 
					position.getLongitude());
			GPS goal2 = new GPS(position.getLatitude() + 300, 
					position.getLongitude());
			GPS goal3 = new GPS(52.24615, 12.32274); //mensa
			
			List<GPS> polygon = new ArrayList<GPS>();
			polygon.add(new GPS(0,0));
			polygon.add(new GPS(-2,0));
			polygon.add(new GPS(-2,2));
			polygon.add(new GPS(0,2));
			
			//tasks.add(new ReachCircleTask(goal, 5));
			tasks.add(new ReachPolygonTask(polygon));
			tasks.add(new ReachCircleTask(goal2, 5));
			tasks.add(new ReachCircleTask(goal3, 5));
			mission.setTasks(tasks);
			planner.doMission(mission);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tasks.remove(0);
		planner.doMission(mission);
	}
	
	/**
	 * Class for loading the properties. This is separated from the {@link Initializier} to be
	 * accessible from tests. 
	 *  
	 * @author hscheel
	 *
	 */
	public static class PropertiesInitializer {
		
		/**
		 * Loads the properties from the configuration file and adds them to the system properties. 
		 */
		public void initializeProperties() {
			Properties prop = new Properties();
			Properties systemProps = System.getProperties();
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
			Set<Object> keySet;
			
			try	{
				prop.load(stream);
			} catch (IOException e) {
				throw new IllegalStateException("could not load properties", e);
			}
			
			keySet = prop.keySet();
			for (Object key : keySet) {
				systemProps.put(key, prop.get(key));
			}
		}
	}
}
