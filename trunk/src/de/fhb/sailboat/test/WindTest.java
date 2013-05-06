package de.fhb.sailboat.test;

import org.apache.log4j.Logger;

import de.fhb.sailboat.serial.sensor.WindSensor;

/**
 * Testing the {@link WindSensor} class.
 * 
 * @author Unknown
 *
 */
public class WindTest {
	private static Logger LOG = Logger.getLogger(WindTest.class);

	public static void main(String args[]) {
		WindSensor myWind = new WindSensor();
	
		// WorldModelImpl.getInstance().getGPSModel().setPosition(new
		// GPS(52.246555,12.323096));
	}
}
