package de.fhb.sailboat.test;
import java.io.IOException;
import java.util.Random;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.start.PropertiesInitializer;

/***
 * Testing all functions of given AKSENLocomotion class
 * @author Tobias Koppe
 *
 */
public class testAksenLocomotion {

	private final String TAG = this.getClass().getSimpleName() + " - ";
	
	private final String LogFile="logFile.txt";
	
	private final Logger log = Logger.getLogger("org.apache");
	private final Level logLevel = Level.DEBUG;
	
	private final int testCrazyRudderCount = 20;
	private final int testCrazySailCount = 10;
	private final int testCrazyPropellorCount = 20;
	private final int testCrazyAksenboardCount = 100;

	int SAIL_SHEET_IN = 31;
	int SAIL_SHEET_OUT = 114;
	int SAIL_SHEET_NORMAL = 73;
	int RUDDER_LEFT = 34;
	int RUDDER_NORMAL = 68;
	int RUDDER_RIGHT = 108;
	int PROPELLOR_MIN = 170;
	int PROPELLOR_NORMAL = 125;
	int PROPELLOR_MAX = 80;
	
	private AKSENLocomotion aksen;

	public testAksenLocomotion() {
		init();
		testSetPropellor();
		aksen.resetPropellor();
		testSetRudder();
		aksen.resetRudder();
////		testSetSail();
		testSetPropellorCrazy();
		aksen.resetPropellor();
		testSetRudderCrazy();
		aksen.resetRudder();
////		testSetSailCrazy();
		testCrazyAksenBoard();
		aksen.resetPropellor();
		aksen.resetRudder();
		aksen.closePort();
	}

	public void init(){
		try {
			log.setLevel(logLevel);
			SimpleLayout layout = new SimpleLayout();
			ConsoleAppender consoleAppender = new ConsoleAppender(layout);
			FileAppender fileAppender = new FileAppender(layout,LogFile);
			log.addAppender(consoleAppender);
			log.addAppender(fileAppender);
			log.info(TAG + "logger state: " + log.getLevel().toString());
			PropertiesInitializer propsInit = new PropertiesInitializer();
			propsInit.initializeProperties();
			aksen = new AKSENLocomotion();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testSendOneCommand() {

	}

	/***
	 * This testing the aksenboard with random functions
	 */
	public void testCrazyAksenBoard() {
		log.info(TAG + "started crazy test for aksenboard");
		Random rndGen = new Random();
		for (int i = 0; i <= testCrazyAksenboardCount; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int rndCmd = rndGen.nextInt(3);
			int rnd = rndGen.nextInt(170);
			
			// rndCmd = 0 - setRudder ; 1 - setSail ; 2 - setPropellor
			switch (rndCmd) {
			case 0: {
				log.info(TAG + "testrun: " + i + " with generated case: [" + rndCmd + "(Rudder);" + rnd + "]");
				setRndRudder(rnd);
				break;
			}
			case 1: {
				log.info(TAG + "testrun: " + i + " with generated case: [" + rndCmd + "(Sail);" + rnd + "]");
//				setRndSail(rnd);
				break;
			}
			case 2: {
				log.info(TAG + "testrun: " + i + " with generated case: [" + rndCmd + "(Propellor);" + rnd + "]");
				setRndPropellor(rnd);
				break;
			}
			default: {
				// Nichts weil keine weitere Funktion definiert
				break;
			}
			}
		}

	}

	public void testSetRudder() {
		log.info(TAG + "started test for setting rudder");
		log.info(TAG + "setting: RUDDER LEFT");
		aksen.setRudder(RUDDER_LEFT);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		log.info(TAG + "setting: RUDDER NORMAL");
		aksen.setRudder(RUDDER_NORMAL);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		log.info(TAG + "setting: RUDDER RIGHT");
		aksen.setRudder(RUDDER_RIGHT);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Reset RDDER");
		aksen.resetRudder();
	}

	public void setRndRudder(int rnd) {
		log.info(TAG + "setRndRudder(" + rnd + ") called");
		switch (rnd) {
		case 0: {
			log.info(TAG + "setting: RUDDER LEFT");
			aksen.setRudder(RUDDER_LEFT);
			break;
		}
		case 1: {
			log.info(TAG + "setting: RUDDER NORMAL");
			aksen.setRudder(RUDDER_NORMAL);
			break;
		}
		case 2: {
			log.info(TAG + "setting: RUDDER RIGHT");
			aksen.setRudder(RUDDER_RIGHT);
			break;
		}
		default: {
			log.info(TAG + "setting: RUDDER TO ANGLE: " + rnd);
			aksen.setRudder(rnd);
			break;
		}
		}
	}

	public void testSetRudderCrazy() {
		// 0 - RUDDER LEFT ; 1 - RUDDER NORMAL ; 2 - RUDDER RIGHT ; default -
		// RUDDER NORMAL
		log.info(TAG + "started crazy test for setting rudder");
		Random rndGen = new Random();
		int rnd = 0;
		for (int i = 0; i <= testCrazyRudderCount; i++) {
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rnd = rndGen.nextInt(108);
			log.info(TAG + " testrun: " + i + " with rnd case: " + rnd);
			setRndRudder(rnd);
		}
	}

	public void testSetSail() {
		aksen.setSail(SAIL_SHEET_IN);
		aksen.setSail(SAIL_SHEET_NORMAL);
		aksen.setSail(SAIL_SHEET_OUT);
	}

	public void setRndSail(int rnd) {
		switch (rnd) {
		case 0: {
			aksen.setSail(SAIL_SHEET_IN);
			break;
		}
		case 1: {
			aksen.setSail(SAIL_SHEET_NORMAL);
			break;
		}
		case 2: {
			aksen.setSail(SAIL_SHEET_OUT);
			break;
		}
		default: {
			aksen.setSail(SAIL_SHEET_NORMAL);
			break;
		}
		}
	}

	public void testSetSailCrazy() {
		// 0 - SAIL SHEET IN ; 1 - SAIL NORMAL ; 2 - SAIL SHEET OUT ; default -
		// SAIL NORMAL
		Random rndGen = new Random();
		int rnd = 0;
		for (int i = 0; i <= testCrazySailCount; i++) {
			rnd = rndGen.nextInt(3);
			setRndSail(rnd);
		}
	}

	public void testSetPropellor() {
		aksen.setPropellor(PROPELLOR_MAX);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aksen.setPropellor(PROPELLOR_NORMAL);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aksen.setPropellor(PROPELLOR_MIN);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aksen.setPropellor(PROPELLOR_NORMAL);
	}

	public void setRndPropellor(int rnd) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (rnd) {
		case 0: {
			aksen.setPropellor(PROPELLOR_MIN);
			break;
		}
		case 1: {
			aksen.setPropellor(PROPELLOR_NORMAL);
			break;
		}
		case 2: {
			aksen.setPropellor(PROPELLOR_MIN);
			break;
		}
		default: {
			aksen.setPropellor(rnd);
			break;
		}
		}
	}

	public void testSetPropellorCrazy() {
		// 0 - PROPELLOR MAX ; 1 - PROPELLOR NORMAL ; 2 - PROPELLOR MIN ;
		// default -
		// PROPELLOR NORMAL
		Random rndGen = new Random();
		int rnd = 0;
		for (int i = 0; i <= testCrazyPropellorCount; i++) {
			rnd = rndGen.nextInt(3);
			setRndPropellor(rnd);
		}
	}

}
