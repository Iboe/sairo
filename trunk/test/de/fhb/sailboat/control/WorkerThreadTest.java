package de.fhb.sailboat.control;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fhb.sailboat.control.navigator.ReachCircleWorker;
import de.fhb.sailboat.control.navigator.WorkerThread;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.test.Initializier.PropertiesInitializer;
import de.fhb.sailboat.worldmodel.History;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class WorkerThreadTest {

	private static WorldModel worldModel;
	private WorkerThread<ReachCircleTask> workerThread;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		worldModel = WorldModelImpl.getInstance();
	}

	@Before
	public void setUp() throws Exception {
		workerThread = new ReachCircleWorker(null, null);
	}
	
	@Test
	public void testIsBeatNecessary() {
		fillWindModel(0);
		
		worldModel.getCompassModel().setCompass(new Compass(0, 0, 0));
		assertTrue(workerThread.isBeatNecessary(WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(WorkerThread.MIN_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(30, 0, 0));
		assertTrue(workerThread.isBeatNecessary(WorkerThread.MAX_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(WorkerThread.MIN_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertTrue(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(-30, 0, 0));
		assertFalse(workerThread.isBeatNecessary(WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(WorkerThread.MIN_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertTrue(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		fillWindModel(-90);
		
		worldModel.getCompassModel().setCompass(new Compass(0, 0, 0));
		assertTrue(workerThread.isBeatNecessary(-90 - WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(-90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertTrue(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(30, 0, 0));
		assertFalse(workerThread.isBeatNecessary(-90 - WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(-90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertTrue(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertTrue(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(-30, 0, 0));
		assertTrue(workerThread.isBeatNecessary(-90 - WorkerThread.MAX_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(-90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(90));
		assertTrue(workerThread.isBeatNecessary(-90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertTrue(workerThread.isBeatNecessary(-135));
		
		fillWindModel(90);
		
		worldModel.getCompassModel().setCompass(new Compass(0, 0, 0));
		assertTrue(workerThread.isBeatNecessary(90 - WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertTrue(workerThread.isBeatNecessary(90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(30, 0, 0));
		assertFalse(workerThread.isBeatNecessary(90 - WorkerThread.MAX_BEAT_ANGLE));
		assertTrue(workerThread.isBeatNecessary(90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertFalse(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertTrue(workerThread.isBeatNecessary(90));
		assertTrue(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
		
		worldModel.getCompassModel().setCompass(new Compass(-30, 0, 0));
		assertTrue(workerThread.isBeatNecessary(90 - WorkerThread.MAX_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(90 - WorkerThread.MIN_BEAT_ANGLE));
		assertFalse(workerThread.isBeatNecessary(0));
		assertFalse(workerThread.isBeatNecessary(180));
		assertTrue(workerThread.isBeatNecessary(45));
		assertFalse(workerThread.isBeatNecessary(-45));
		assertFalse(workerThread.isBeatNecessary(-90));
		assertTrue(workerThread.isBeatNecessary(90));
		assertFalse(workerThread.isBeatNecessary(135));
		assertFalse(workerThread.isBeatNecessary(-135));
	}

	private void fillWindModel(int direction) {
		for (int i = 0; i < History.DEFAULT_MAX_SIZE; i++) {
			worldModel.getWindModel().setWind(new Wind(direction,0));
		}
	}
}
