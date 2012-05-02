/**
 * 
 */
package de.fhb.sailboat.test;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * @author Micha
 *
 */
public class GPSDummy extends Thread{ 

	private static Logger LOG = Logger.getLogger(GPSDummy.class);
	
	public static void main(String args[])
	{
		GpsSensor myGPS = new GpsSensor("COM4");
		
		//WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(52.246555,12.323096));
	}
}
