package de.fhb.sailboat.sensors.lib.nmeaSensors;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import de.fhb.sailboat.sensors.lib.*;

public abstract class NmeaSensor extends RxtxSensor {
	
	public String readLine(){
		
		String dataString = null;
		
		InputStream in = this.getInputStream();
		DataInputStream din = new DataInputStream(in);
		
		try {
			dataString = din.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataString;
	}
}
