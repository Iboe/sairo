package de.fhb.sailboat.sensors.lib.nmeaSensors.gps;

import de.fhb.sailboat.sensors.lib.OpenNMEA.SentenceHandler;
import de.fhb.sailboat.sensors.lib.nmeaSensors.NMEAGpsParser;
import de.fhb.sailboat.sensors.lib.nmeaSensors.NMEAGpsParser.GPSPosition;
import de.fhb.sailboat.sensors.lib.nmeaSensors.NmeaSensor;

public class NL402U extends NmeaSensor{

	SentenceHandler parser = new SentenceHandler();
	
	public String[] parseNmea(String nmeaString) {
		//System.out.println(nmeaString);
		String[] data = parser.split(nmeaString, true);
		return data;
	}

	@Override
	public void run() {
		
		String[] data= null;
		
		while(true){
			
			try{
				data = this.parseNmea(this.readLine());
				if(data[0].equals("$GPGGA")){
					System.out.println(data[1]+" - "+data[2]+" - "+data[4]);
				}
			}catch(Exception e){
				
			}
		}
	}
	
}
