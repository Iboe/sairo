package de.fhb.sailboat.start;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.control.planner.PlannerImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.MissionVO;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.test.DummyLoco;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Initializes the application in a test mode when the main method is started. Loads properties and 
 * initializes all control layers, no sensors, the user interface and the communication between boat 
 * application. 
 * Unlike {@link Initializier}, a dummy implementation of the {@link LocomotionSystem} is used. This does
 * not has access to the real actuators and does nothing. Additionally, initial dummy values for the 
 * sensors are prepared and a dummy mission is started. 
 * 
 * @author hscheel
 *
 */
public class TestInitializier extends Initializier {

	/**
	 * Entry point for the application. Loads properties and initializes all control layers, no 
	 * sensors, the user interface and the communication between boat application and user interface.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		TestInitializier init = new TestInitializier();
		
		initializeProperties();
		init.initializeControl();
		init.setSensorDummyValues();
		init.initializeCommunication();
		init.initializeView();
		init.createDummyMission();
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.fhb.sailboat.start.Initializier#initializeControl()
	 * 
	 * Uses a dummy implementation of de.fhb.sailboat.serial.actuator.LocomotionSystem, which does not
	 * have access to the real actuators and does nothing.
	 */
	@Override
	protected void initializeControl() {
		loco = new DummyLoco();
		pilot = new PilotImpl(loco);
		navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	/**
	 * Creates a dummy mission for testing.
	 */
	protected void createDummyMission() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		final GPS position = worldModel.getGPSModel().getPosition();
		MissionVO mission = new MissionVO();
		List<Task> tasks = new LinkedList<Task>();
		
		if (position != null) {
			GPS goal = new GPS(200, 100,System.currentTimeMillis());
			GPS goal2 = new GPS(position.getLatitude() + 300, 
					position.getLongitude(),System.currentTimeMillis());
			GPS goal3 = new GPS(52.24615, 12.32274,System.currentTimeMillis()); //mensa
			
			List<GPS> polygon = new ArrayList<GPS>();
			polygon.add(new GPS(0,0,System.currentTimeMillis()));
			polygon.add(new GPS(-2,0,System.currentTimeMillis()));
			polygon.add(new GPS(-2,2,System.currentTimeMillis()));
			polygon.add(new GPS(0,2,System.currentTimeMillis()));
			
			tasks.add(new ReachCircleTask(goal, 5));
			tasks.add(new ReachPolygonTask(polygon));
			tasks.add(new ReachCircleTask(goal2, 5));
			tasks.add(new ReachCircleTask(goal3, 5));
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
	 * Sets initial dummy values for the {@link GPS} and the {@link Compass} to avoid 
	 * {@link NullPointerException}s.
	 */
	protected void setSensorDummyValues() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		
		worldModel.getGPSModel().setPosition(new GPS(52.246555,12.323096,System.currentTimeMillis()));
		worldModel.getCompassModel().setCompass(new Compass(175, 0, 0,System.currentTimeMillis()));
	}
}
