package de.fhb.sailboat.test;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.serial.sensor.CompassSensor;

/**
 * Testing the functionality of the {@link CompassSensor} class.<br>
 * The test includes creating a {@link CompassSensor} object and <br>
 * retrieving four times the current compass values as {@link Compass} object.<br>
 * The result is printed on the console.
 * 
 * @author S. Schmidt
 */
public class compasstest {

	/**
	 * Entry point of the test application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) {
		
		CompassSensor compass = new CompassSensor();
		try {
			System.out.println("Abfrage1: ");

			compass.getCompass();
			System.out.println("Abfrage2: ");
			Thread.sleep(1000);
			compass.getCompass();
			System.out.println("Abfrage3: ");
			Thread.sleep(1000);
			compass.getCompass();
			System.out.println("Abfrage4: ");
			Thread.sleep(1000);
			compass.getCompass();
			System.out.println("Abfrage5: ");
			Thread.sleep(1000);
			compass.getCompass();
			System.out.println("----");
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0); 
		}

	}

}
