package de.fhb.sailboat.serial.sensor;

import java.util.ArrayList;
import java.util.Collections;

import javax.vecmath.Vector3d;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.serial.sensor.sairoComm2;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class OS500sensor {
	sairoComm2 sensorComm;
	String lastCom;
	// Servo-Config; ==> TODO outsourcing!
	//String comPort = "COM1";
	/*
	 * Configuration Variables
	 */
	boolean keepRunning = true;
	Thread sensorThread;
	String comPort = "COM17";
	int baudrate = 115200;
	long clock = 1000;			// clock-rate in milliseconds; Compass sends actually 10 Samples/s, Sample-Buffer stores 10 Samples
	
	// intern
	int errorcount = 0;
	
	
	public OS500sensor(){
		
		this.sensorComm = new sairoComm2(comPort,baudrate,true,false);
		
		try {
			this.sensorComm.connect();
			//TODO sensorThread= new Thread...
			//TODO zum beenden des threads durch separate methode:
			//     keepRunning=false
			//     sensorThread.join() //warten auf thread beendung
			//     com port schlieﬂen
			keepRunning=true;
			(sensorThread=new Thread(new OS500sensorThread(this))).start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutdown()
	{
		if(sensorThread != null && !sensorThread.isInterrupted())
		{
			keepRunning=false;
			try {
				sensorThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sensorComm.closePort();
		}
	}
	public long getClock() {
		return clock;
	}


	public void setClock(long clock) {
		this.clock = clock;
	}


	public void getRawString() {
		System.out.println(this.sensorComm.getStrInput());
	}
	
//	public void getArrString() {
//		String[] list = this.sensorComm.getSampleBuffer();
//		for (String item: list) {
//			System.out.println(item);
//		}
//		
//	}
	
	public int getErrorcount() {
		return errorcount;
	}

	public void setErrorcount(int errorcount) {
		this.errorcount = errorcount;
	}



    
	//Thread
	
	static class OS500sensorThread implements Runnable {

		OS500sensor sensorInstance;
		// clock in milliseconds
		long clock = 1000;	// default Value
		long start, end;
		
		/**
		 * @param sensorInstance
		 */
		public OS500sensorThread(OS500sensor sensorInstance) {
			this.sensorInstance = sensorInstance;
			this.clock = sensorInstance.getClock();
		}

		@Override
		public void run() {
			 
			//TODO keepRunning setzbar machen von auﬂen um thread  kontrolliert zu beenden
			while (sensorInstance.keepRunning) {
				try {
					start = System.currentTimeMillis();
					// Get List of the last 10 Elements
					String[] list = sensorInstance.sensorComm.getSampleBuffer();
					OS500dataSet dataSet = new OS500dataSet();
					// no of usefull items
					int i = 0;
					// Foreach List-Element
					for (String item: list) {
						//Verify String and add to i or throw away
						if (item != null && nmea.verifyString(item)) {
							String[] data = (nmea.splitNmea(item));
							// at the moment, we expect at least 12 fields
							if (data.length<12) {
								sensorInstance.errorcount++;
							} else {
								// azimuth = data[1]
								// pitch =  data[2]
								// roll = data[3]
								// temp = data[4]
								// magS = data[5]
								// magVect = data[6-8]
								// accS = data[9]
								// accVect = data[10-12]
								dataSet.azimuthList.add(Double.valueOf(data[1]));
								dataSet.pitchList.add(Double.valueOf(data[2]));
								dataSet.rollList.add(Double.valueOf(data[3]));
								dataSet.tempList.add(Double.valueOf(data[4]));
								dataSet.magSList.add(Double.valueOf(data[5]));
								dataSet.magVectXList.add(Double.valueOf(data[6]));
								dataSet.magVectYList.add(Double.valueOf(data[7]));
								dataSet.magVectZList.add(Double.valueOf(data[8]));
								dataSet.accSList.add(Double.valueOf(data[9]));
								dataSet.accVectXList.add(Double.valueOf(data[10]));
								dataSet.accVectYList.add(Double.valueOf(data[11]));
								dataSet.accVectZList.add(Double.valueOf(data[12]));								
								// expect, everything went correct, increment the usefull-item-counter
								i++;
							}
						} else {
							// increment Errorcount
							sensorInstance.errorcount++;
						}
					}		
					// if there is at least one sample, try to get the average value and set it to Worldmodell
					if (i >= 1) {
						
						// TODO errase aberration; calculate average
						if(dataSet.medianAll()) {
							// set to Worldmodel
							double usefullrate = i/list.length;
							Compass c = new Compass(dataSet.getAzimuth(),
															dataSet.getPitch(), 
															dataSet.getRoll(),
															dataSet.getTemp(),
															dataSet.getMagS(),
															dataSet.getMagVect(),
															dataSet.getAccS(), 
															dataSet.getAccVect(), 
															usefullrate);
							WorldModelImpl.getInstance().getCompassModel().setCompass(c);
							
//							System.out.println("Azimuth: "+ dataSet.getAzimuth() +" Pitch: "+ dataSet.getPitch() +" Roll: "+ dataSet.getRoll() 
//									+" Temp: "+ dataSet.getTemp()
//									+" out of "+ i +" useful samples ("+ usefullrate +")");
							
						}
					}
					
					end = System.currentTimeMillis();
					// Sleep the difference of clock-rate and calculation-time of this run
					Thread.sleep(clock-((end - start)));
					
				} catch (InterruptedException e) {
					// something went wrong, stop the loop, throw an error to the next higher level
					sensorInstance.keepRunning = false;
					
					e.printStackTrace();
				}
			}

			
		}
		
	}
	
	public static class OS500dataSet {


		Double azimuth, pitch, roll, temp, magS, accS;
		Vector3d magVect, accVect;
		public ArrayList<Double> azimuthList = new ArrayList<Double>(); 	// data[1]
		public ArrayList<Double> pitchList = new ArrayList<Double>();	// data[2]
		public ArrayList<Double> rollList = new ArrayList<Double>();		// data[3]
		public ArrayList<Double> tempList = new ArrayList<Double>();		// data[4]
		public ArrayList<Double> magSList = new ArrayList<Double>();		// data[5]
		public ArrayList<Double> magVectXList = new ArrayList<Double>();	// data[6]
		public ArrayList<Double> magVectYList = new ArrayList<Double>();	// data[7]
		public ArrayList<Double> magVectZList = new ArrayList<Double>();	// data[8]
		public ArrayList<Double> accSList = new ArrayList<Double>();		// data[9]
		public ArrayList<Double> accVectXList = new ArrayList<Double>();	// data[10]
		public ArrayList<Double> accVectYList = new ArrayList<Double>();	// data[11]
		public ArrayList<Double> accVectZList = new ArrayList<Double>();	// data[12]
		
		public Double getAzimuth() {
			return this.azimuth;
		}
		public Double getPitch() {
			return this.pitch;
		}
		public Double getRoll() {
			return this.roll;
		}
		public Double getTemp() {
			return this.temp;
		}
		public Double getMagS() {
			return this.magS;
		}
		public Vector3d getMagVect() {
			return this.magVect;
		}
		public Double getAccS() {
			return this.accS;
		}
		public Vector3d getAccVect() {
			return this.accVect;
		}

		public boolean medianAll() {
			this.azimuth = Median(this.azimuthList);
			this.pitch = Median(this.pitchList);
			this.roll = Median(this.rollList);
			this.temp = Median(this.tempList);
			this.magS = Median(this.magSList);
			Double mx = Median(this.magVectXList);
			Double my = Median(this.magVectYList);
			Double mz = Median(this.magVectZList);
			this.magVect = new Vector3d(mx, my, mz);
			this.accS = Median(this.accSList);
			Double acx = Median(this.accVectXList);
			Double acy = Median(this.accVectYList);
			Double acz = Median(this.accVectZList);
			this.accVect = new Vector3d(acx, acy, acz);
			
			return true;
		}
		
		public double Median(ArrayList<Double> values)
		{
		    
			if(values.size() == 1) {
		    	return values.get(0);
		    }
			Collections.sort(values);
		 
		    if (values.size() % 2 == 1)
			return values.get((values.size()+1)/2-1);
		    else
		    {
		    	double lower = values.get(values.size()/2-1);
		    	double upper = values.get(values.size()/2);
		    	
			return (lower + upper) / 2.0;
		    }	
		}
	
	
	}
}
