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
	
	private final int testCrazyRudderCount = 100;
	private final int testCrazySailCount = 100;
	private final int testCrazyPropellorCount = 100;
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
		testSetRudder();
		testSetSail();
		testSetPropellorCrazy();
		testSetRudderCrazy();
		testSetSailCrazy();
		testCrazyAksenBoard();
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
			aksen = new AKSENLocomotion(true);
			
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
			int rndCmd = rndGen.nextInt(2);
			int rnd = rndGen.nextInt(2);
			log.info(TAG + "testrun: " + i + " with generated case: [" + rndCmd + ";" + rnd + "]");
			// rndCmd = 0 - setRudder ; 1 - setSail ; 2 - setPropellor
			switch (rndCmd) {
			case 0: {
				setRndRudder(rnd);
				break;
			}
			case 1: {
				setRndSail(rnd);
				break;
			}
			case 2: {
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
		log.info(TAG + "setting: RUDDER NORMAL");
		aksen.setRudder(RUDDER_NORMAL);
		log.info(TAG + "setting: RUDDER RIGHT");
		aksen.setRudder(RUDDER_RIGHT);
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
			log.info(TAG + "setting: RUDDER NORMAL");
			aksen.setRudder(RUDDER_NORMAL);
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
			rnd = rndGen.nextInt(2);
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
			rnd = rndGen.nextInt(2);
			setRndSail(rnd);
		}
	}

	public void testSetPropellor() {
		aksen.setPropellor(PROPELLOR_MAX);
		aksen.setPropellor(PROPELLOR_NORMAL);
		aksen.setPropellor(PROPELLOR_MIN);
	}

	public void setRndPropellor(int rnd) {
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
			aksen.setPropellor(PROPELLOR_NORMAL);
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
			rnd = rndGen.nextInt(2);
			setRndPropellor(rnd);
		}
	}

}
