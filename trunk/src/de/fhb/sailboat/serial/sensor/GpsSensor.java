package de.fhb.sailboat.serial.sensor;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class GpsSensor{
	
	double latitude;
	double longitude;
	sairoComm mySairo;
	
	public GpsSensor(String port){
		// create an instance of the sairoComm Class
		sairoComm mySairo = new sairoComm(port);
		this.mySairo = mySairo;
		
		try {
			mySairo.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		(new Thread(new GpsSensorThread(this))).start();
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
	
	static class GpsSensorThread implements Runnable{
		GpsSensor gpsInstance;
		
		public GpsSensorThread(GpsSensor sensorInstance) {
			this.gpsInstance = sensorInstance;
		}
		
		public void run() {
	
		while(true){
			
			String myNmea[] = nmea.stringToArray(gpsInstance.mySairo.getDataString());
			if(myNmea != null){
				if(myNmea.length == 9){
					if(myNmea[1] != "0.0" && myNmea[3] != "0.0"){
						
						// Schreiben in Weltmodell
						GPS myGps = new GPS(Double.parseDouble(myNmea[1])/100, Double.parseDouble(myNmea[3])/100);
						WorldModelImpl.getInstance().getGPSModel().setPosition(myGps);
						//System.out.println(myNmea[1]+","+myNmea[3]);
						//gpsInstance.setLatitude(Double.parseDouble(myNmea[1]));
						//gpsInstance.setLongitude(Double.parseDouble(myNmea[3]));
					}
				}
			}
		}
	}
	}
}
