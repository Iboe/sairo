package de.fhb.sailboat.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.carrierAdapters.CommTCPServer;
import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.serverModules.CompassTransmitter;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.communication.serverModules.WindTransmitter;
import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.control.PlannerImpl;
import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.GUI;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.serial.sensor.CompassSensor;
import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.serial.sensor.WindSensor;
import de.fhb.sailboat.ufer.prototyp.View;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class Initializier {

	private static final Logger LOG = LoggerFactory.getLogger(Initializier.class);
	
	private static final boolean TEST = false;
	private Planner planner;
	//private View view;
	private GUI view;
	private LocomotionSystem loco=null;
	
	public static void main(String[] args) {
		Initializier init = new Initializier();
		PropertiesInitializer propsInit = new PropertiesInitializer();
		
		propsInit.initializeProperties();
		init.initializeControl();
		
		if (TEST) {
			init.setSensorDummyValues();
			init.initializeCommunication();
			init.initializeView();
		/*	
			new Thread(){

				@Override
				public void run() {
					
					String line;
					String[] token;
					BufferedReader read=new BufferedReader(new InputStreamReader(System.in));
					
					
					while(!isInterrupted()){
						
						System.out.print(">");
						try {
							line=read.readLine();
							token=line.split(" ");
							
							if(token[0].compareTo("c")==0){
								
								int compass=Integer.parseInt(token[1]);
								System.out.println("setting compass to "+compass);
								WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(compass,0,0));
							}
							else if(token[0].compareTo("g")==0){
								
								double lat=Double.parseDouble(token[1]);
								double lon=Double.parseDouble(token[2]);
								int sat=Integer.parseInt(token[3]);
								System.out.println("setting gps to ("+lat+","+lon+") - "+sat);
								WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(lat,lon,sat));
							}
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}
				
			}.start();
			*/
			init.createDummyMission();
			
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
		GpsSensor gps=new GpsSensor(12);
		WindSensor wind=new WindSensor(4);
		
		CompassSensor compassSensor=new CompassSensor(); //zzt. COM17
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
			loco=new DummyLoco();
		} else {
			loco=new AKSENLocomotion();
		}
		pilot = new PilotImpl(loco);
		Navigator navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	private void initializeCommunication() {
		
		CommunicationBase server=new CommTCPServer(6699);
		server.registerModule(new GPSTransmitter());
		server.registerModule(new CompassTransmitter());
		server.registerModule(new WindTransmitter());
		if(!server.initialize())
			LOG.warn("Unable to start the communications TCP server on port 6699");
	}
	
	private void initializeView() {
		view = new GUI(planner);
		view.setVisible(true);
	}
	
	private void createDummyMission() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		final GPS position = worldModel.getGPSModel().getPosition();
		Mission mission = new MissionImpl();
		List<Task> tasks = new LinkedList<Task>();
		
		if (position != null) {
			GPS goal = new GPS(200, 100);
			GPS goal2 = new GPS(position.getLatitude() + 300, 
					position.getLongitude());
			GPS goal3 = new GPS(52.24615, 12.32274); //mensa
			
			List<GPS> polygon = new ArrayList<GPS>();
			polygon.add(new GPS(0,0));
			polygon.add(new GPS(-2,0));
			polygon.add(new GPS(-2,2));
			polygon.add(new GPS(0,2));
			
			tasks.add(new ReachCircleTask(goal, 5));
			//tasks.add(new ReachPolygonTask(polygon));
			//tasks.add(new ReachCircleTask(goal2, 5));
			//tasks.add(new ReachCircleTask(goal3, 5));
			mission.setTasks(tasks);
			planner.doMission(mission);
		}
		/*try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		tasks.remove(0);
		planner.doMission(mission);*/
	}
	
	/**
	 * Class for loading the properties. This is separated from the {@link Initializier} to be
	 * accessible from tests. 
	 *  
	 * @author hscheel
	 *
	 */
	public static class PropertiesInitializer {
		
		private static final String CONFIG_FILE = "config.properties";
		
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
