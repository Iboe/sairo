package de.fhb.sailboat.control;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.start.PropertiesInitializer;
import de.fhb.sailboat.test.DummyLoco;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class NavigatorImplTest {

	private static WorldModel worldModel;
	private static Navigator navigator;
	private static LocomotionSystem locomotion;
	private static List<GPS> polygon;
	private static GPS goal;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		worldModel = WorldModelImpl.getInstance();
		locomotion = EasyMock.createMock(LocomotionSystem.class);
		Pilot pilot = new PilotImpl(new DummyLoco());
		GPS position = worldModel.getGPSModel().getPosition();
		
		polygon = new ArrayList<GPS>();
		navigator = new NavigatorImpl(pilot);
		goal = new GPS(position.getLatitude() + 200, 
				position.getLongitude(),System.currentTimeMillis());
		polygon.add(new GPS(0,0,System.currentTimeMillis()));
		polygon.add(new GPS(-2,0,System.currentTimeMillis()));
		polygon.add(new GPS(-2,2,System.currentTimeMillis()));
		polygon.add(new GPS(0,2,System.currentTimeMillis()));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public synchronized void reachCircle() {
		navigator.doTask(new ReachCircleTask(goal, 5));
		locomotion.setRudder(0);
		sleep(3000);
	}
	
	@Test
	public synchronized void reachPolygon() {
		navigator.doTask(new ReachPolygonTask(polygon));
		sleep(3000);
	}

	@Test
	public synchronized void switchTask() {
		navigator.doTask(new ReachCircleTask(goal, 5));
		sleep(500);
		reachPolygon();
	}
	
	private void sleep(long time) {
		try {
			wait(time);
		} catch (InterruptedException e) {
			fail(e.toString());
		}
	}
}
