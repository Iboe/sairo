package de.fhb.sailboat.test;

import java.util.LinkedList;
import java.util.List;

import de.fhb.sailboat.control.Navigator;
import de.fhb.sailboat.control.NavigatorImpl;
import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.control.PilotImpl;
import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.control.PlannerImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class Initializier {

	private Planner planner;
	
	public static void main(String[] args) {
		Initializier init = new Initializier();
		
		init.initializeSensors();
		init.initializeControl();
		init.createDummyMission();
		//init.waitForShutdown();
	}
	
	private void initializeSensors() {
		GPSDummy gps = new GPSDummy();
		//GpsSensor gps=new GpsSensor("COM8");
		WorldModelImpl.getInstance().getCompassModel()
		.setCompass(new Compass(180, 0, 0));
	}
	
	private void initializeControl() {
		//Pilot pilot = new PilotImpl(new AKSENLocomotion());
		Pilot pilot = new PilotImpl(new DummyLoco());
		Navigator navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	private void createDummyMission() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		boolean gpsExist = false;
		
		while (!gpsExist) {
			final GPS position = worldModel.getGPSModel().getPosition();
			
			if (position != null) {
				Mission mission = new MissionImpl();
				List<Task> tasks = new LinkedList<Task>();
//				GPS goal = new GPS(position.getLatitude() + 200, 
//						position.getLongitude() + 300);
				GPS goal = new GPS(52.24615, //mensa
						12.32274);
				
				System.out.println("current: "+position);
				System.out.println("desired: "+goal);
				
				tasks.add(new ReachCircleTask(goal, 50));
				mission.setTasks(tasks);
				planner.doMission(mission);
				gpsExist = true;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void waitForShutdown() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
