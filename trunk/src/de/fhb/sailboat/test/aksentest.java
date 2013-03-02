package de.fhb.sailboat.test;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.start.PropertiesInitializer;

/**
 * Simple test of the {@link LocomotionSystem} using the concrete implementation {@link AKSENLocomotion} by setting the sail to a desired value.<br>
 * 
 * @author Michael Kant
 */
public class aksentest {
	
	private static final Logger LOG = LoggerFactory.getLogger(aksentest.class);
	private static long start;
	private static long end;
	
	/**
	 * Entry point of the test application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) {
		
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		AKSENLocomotion aksen = new AKSENLocomotion();
		
		aksen.setDebug(true);
		
//		int i = 0;
//		int x;
//		while(i < 100) {
//			// Log evere 10th run
//			if (i%10 == 0)
//				LOG.info("Test-Run "+ i);
//			// Alternate Rudder- or Sail-Command
//			
//			switch(i%2) {
//			case 1:
//				x = myRandom(aksen.RUDDER_LEFT, aksen.RUDDER_RIGHT);
//				LOG.trace("Set Rudder to "+ x);
//				start = System.currentTimeMillis();
//				aksen.setRudder(x);
//				end = System.currentTimeMillis();
//				break;
//			case 0:
//			default:
//				
//				x = myRandom(aksen.SAIL_SHEET_IN, aksen.SAIL_SHEET_OUT);
//				LOG.trace("Set Sail to "+ x);
//				
//				aksen.setSail(x);
//			}
//			long t = end - start;
//			LOG.trace("Time elapses this run: "+ t);
//			i++;
//			
//		}
		//aksen.resetRudder();
		//aksen.resetSail();
		aksen.setSail(114);
		//System.out.println("Antwort: "+ aksen.lastAnswer());
		//System.out.println("Frage Ladezustand ab:");
		//System.out.println("IST: "+ aksen.getBatteryState());
		
		aksen.closePort();
	}
	
	public static int myRandom(int low, int high) {  
		
        return (int) (Math.random() * (high - low) + low);  
    }  

}
