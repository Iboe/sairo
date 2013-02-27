package de.fhb.sailboat.start;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.control.planner.PlannerImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.test.DummyLoco;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class TestInitializier extends Initializier {

	public static void main(String[] args) {
		TestInitializier init = new TestInitializier();
		PropertiesInitializer propsInit = new PropertiesInitializer();
		
		propsInit.initializeProperties();
		init.initializeControl();
		init.setSensorDummyValues();
		init.initializeCommunication();
		init.initializeView();
		init.createDummyMission();
	}
	
	protected void initializeControl() {
		loco = new DummyLoco();
		pilot = new PilotImpl(loco);
		navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	protected void createDummyMission() {
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
	
	private void setSensorDummyValues() {
		WorldModel worldModel = WorldModelImpl.getInstance();
		
		worldModel.getGPSModel().setPosition(new GPS(52.246555,12.323096));
		worldModel.getCompassModel().setCompass(new Compass(175, 0, 0));
	}
}
