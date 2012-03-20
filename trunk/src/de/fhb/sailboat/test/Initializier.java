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
import de.fhb.sailboat.serial.sensor.OS500sensor;
import de.fhb.sailboat.ufer.prototyp.View;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class Initializier {

	private Planner planner;
	private View view;
	
	private OS500sensor compassSensor;
	
	public static void main(String[] args) {
		Initializier init = new Initializier();
		
		init.initializeSensors();
		init.initializeControl();
		init.initializeView();
		init.createDummyMission();
		//init.waitForShutdown();
	}
	
	private void initializeSensors() {
		GPSDummy gps = new GPSDummy();
		System.out.println("-----init sensors-----");
		//GpsSensor gps=new GpsSensor("COM8");
		//compassSensor=new OS500sensor(); //zzt. COM17
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("-----init sensors done-----");
		//WorldModelImpl.getInstance().getCompassModel()
		//.setCompass(new Compass(170, 0, 0));
	}
	
	private void initializeControl() {
		//Pilot pilot = new PilotImpl(new AKSENLocomotion());
		Pilot pilot = new PilotImpl(new DummyLoco());
		Navigator navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	private void initializeView() {
		view = new View(planner);
		view.setVisible(true);
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
				
				System.out.println("current: "+WorldModelImpl.getInstance().getCompassModel().getCompass());
				System.out.println("current: "+position);
				System.out.println("desired: "+goal);
				
				tasks.add(new ReachCircleTask(goal, 50));
				mission.setTasks(tasks);
				planner.doMission(mission);
				compassSensor.shutdown();
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
