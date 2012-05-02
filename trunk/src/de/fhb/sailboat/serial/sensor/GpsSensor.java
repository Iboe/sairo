package de.fhb.sailboat.serial.sensor;



import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.CASTORE;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class GpsSensor{
	
	double latitude;
	double longitude;
	Integer satelites = null;
	sairoComm mySairo;
	private static Logger LOG = Logger.getLogger(GpsSensor.class);
	
	public GpsSensor(String port){
		// create an instance of the sairoComm Class
		sairoComm mySairo = new sairoComm(port);
		this.mySairo = mySairo;
		
		try {
			mySairo.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error in GPS: " +  e);			
		}
		
		(new GpsSensorThread(this)).start();
	}
	
	public double getLatitude(){
		return this.latitude;
	}
	public double getLongitude(){
		return this.longitude;
	}
	private void setLatitude(double latitude){
		this.latitude = latitude;
	}
	private void setLongitude(double longitude){
		this.longitude = longitude;
	}
	
	private int getSatelites()
	{
		return this.satelites;
	}
	
	static class GpsSensorThread extends Thread{
		GpsSensor gpsInstance;
		int satelites;
		private static Logger LOG = Logger.getLogger(GpsSensorThread.class);
		
		public GpsSensorThread(GpsSensor sensorInstance) {
			this.gpsInstance = sensorInstance;
		}
		
		public void run() {
	
		while(!isInterrupted()){
			
			String myNmea[] = nmea.stringToArray(gpsInstance.mySairo.getDataString());
			if(myNmea != null){
				if(myNmea.length == 15 && myNmea[0] == "GPGGA"){
					if(myNmea[1] != "0.0" && myNmea[3] != "0.0"){
						// Schreiben in Weltmodell
						double latitude = Double.parseDouble(myNmea[1]);
						double longitude = Double.parseDouble(myNmea[3]);
						//TODO Check the Number
						this.satelites = Integer.parseInt(myNmea[7]);
						
						int gradLat = (int)(latitude / 100);
						int gradLong = (int)(longitude / 100);
						double minLat = latitude - gradLat * 100;
						double minLong = longitude - gradLong * 100;
						
						
						minLat*=10./600.;
						minLong*=10./600.;
						
						//To show values
						LOG.debug(MessageFormat.format("Latitude: {0}," +
								" Longtitude: {1}," +
								" Satelites: {2}"
								,minLat,minLong,satelites));
						
						GPS myGps = new GPS(gradLat+minLat,gradLong+minLong,this.satelites);
						WorldModelImpl.getInstance().getGPSModel().setPosition(myGps);
						
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							LOG.fatal("Error Occured:" + e);
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	}
}
