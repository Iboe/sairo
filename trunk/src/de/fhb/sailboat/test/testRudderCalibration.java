package de.fhb.sailboat.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.start.PropertiesInitializer;


public class testRudderCalibration {

	private final String TAG = this.getClass().getSimpleName() + " - ";
	
	private final String LogFile="logFile.txt";
	
	private final Logger log = Logger.getLogger("org.apache");
	private final Level logLevel = Level.DEBUG;
	
	int RUDDER_LEFT = 34;
	int RUDDER_NORMAL = 68;
	int RUDDER_RIGHT = 108;
	
	private AKSENLocomotion aksen;
	
	public testRudderCalibration(){
		init();
		log.debug(TAG+"start test for testing rudder calibration...");
		test();
		aksen.closePort();
		System.exit(0);
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
	
	private void test(){
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		log.debug(TAG + "setting rudder to maximum calibrated right position");
		aksen.setRudder(RUDDER_RIGHT);
		System.out.println("Press Enter to setting rudder to calibrated middle position");
		try {
			br.readLine();
			aksen.setRudder(RUDDER_NORMAL);
			System.out.println("Press Enter to setting rudder to maximum calibrated left position");
			br.readLine();
			aksen.setRudder(RUDDER_LEFT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
