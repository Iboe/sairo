package de.fhb.sailboat.test;

import org.apache.log4j.Logger;

import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.serial.sensor.WindSensor;

public class WindTest {
	private static Logger LOG = Logger.getLogger(WindTest.class);

	public static void main(String args[]) {
		WindSensor myWind = new WindSensor(4);
	
		// WorldModelImpl.getInstance().getGPSModel().setPosition(new
		// GPS(52.246555,12.323096));
	}
}