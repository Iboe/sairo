package de.fhb.sailboat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.test.Initializier.PropertiesInitializer;
public class aksentest {

	/**
	 * @param args
	 */
	
	private static final Logger LOG = LoggerFactory.getLogger(aksentest.class);
	
	public static void main(String[] args) {
		
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		AKSENLocomotion aksen = new AKSENLocomotion();
		
		
		int i = 0;
		int x;
		while(i < 1000) {
			if (i%10 == 0)
				LOG.info("Test-Run "+ i);
			switch(i%2) {
			case 1:
				x = myRandom(aksen.RUDDER_LEFT, aksen.RUDDER_RIGHT);
				LOG.trace("Set Rudder to "+ x);
				aksen.setRudder(x);
				
				break;
			case 0:
			default:
				
				x = myRandom(aksen.SAIL_SHEET_IN, aksen.SAIL_SHEET_OUT);
				LOG.trace("Set Sail to "+ x);
				aksen.setSail(x);
			}
			
			i++;
		}
		aksen.resetRudder();
		aksen.resetSail();
		//System.out.println("Antwort: "+ aksen.lastAnswer());
		System.out.println("Frage Ladezustand ab:");
		System.out.println("IST: "+ aksen.getBatteryState());
		
		aksen.closePort();
	}
	
	public static int myRandom(int low, int high) {  
        return (int) (Math.random() * (high - low) + low);  
    }  

}
