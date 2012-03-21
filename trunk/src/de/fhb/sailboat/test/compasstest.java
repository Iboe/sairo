package de.fhb.sailboat.test;

import de.fhb.sailboat.serial.sensor.OS500sensor;
public class compasstest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String str = "$OHPR,324.7,10.1,9.6,26.7,423.21,-305.19,-260.72,134.14,1.038,0.183,0.174,1.006,206969S1*4F";
//		System.out.println(str);
//		if (str.matches("^[$](.*?)[*]\\w{2}$")) {
//			System.out.println("$: true");
//
//		} else {
//			System.out.println("$: false");
//
//		}

		
		OS500sensor compass = new OS500sensor();
		try {
//			System.out.println("Abfrage1: ");
//			while(true) {
//				Thread.sleep(1000);
//				System.out.println("Takt");
//			}
			compass.getRawString();
			System.out.println("Abfrage2: ");
			Thread.sleep(1000);
			compass.getRawString();
			System.out.println("Abfrage3: ");
			Thread.sleep(1000);
			compass.getRawString();
			System.out.println("Abfrage4: ");
			Thread.sleep(1000);
			compass.getRawString();
			System.out.println("Abfrage5: ");
			Thread.sleep(1000);
			compass.getRawString();
			System.out.println("----");
//			int i = 0;
//			while (i<=10) {
//				Thread.sleep(1000);
//				compass.getArrString();
//				System.out.println("----");
//				i++;
//			}
//			
//			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0); 
		}
		
		
		
		

	}

}
