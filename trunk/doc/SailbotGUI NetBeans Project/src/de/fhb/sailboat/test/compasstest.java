package de.fhb.sailboat.test;

import de.fhb.sailboat.serial.sensor.CompassSensor;
public class compasstest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		CompassSensor compass = new CompassSensor();
		try {
			System.out.println("Abfrage1: ");

			compass.getC();
			System.out.println("Abfrage2: ");
			Thread.sleep(1000);
			compass.getC();
			System.out.println("Abfrage3: ");
			Thread.sleep(1000);
			compass.getC();
			System.out.println("Abfrage4: ");
			Thread.sleep(1000);
			compass.getC();
			System.out.println("Abfrage5: ");
			Thread.sleep(1000);
			compass.getC();
			System.out.println("----");
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0); 
		}
		
		
		
		

	}

}
