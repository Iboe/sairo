package de.fhb.sailboat.serial.sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.vecmath.Vector3d;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.Compass;

import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModelImpl;
import de.fhb.sairo.both.LogTextblocks;
/**
 * Implementation of a certain Compass-Sensor-Module
 * Opens a new Thread for continously pushing Sensor-Data into Worldmodel
 * (every $updateRate milliseconds)
 * @author schmidst
 *
 */
public class CompassSensor {
	
	private COMPort myCOM;
	private Compass myCompass;
	private boolean keepRunning = true;
	private Thread sensorThread;
	private int errorcount = 0;
	private static final Logger LOG = Logger.getLogger(CompassSensor.class);

	public static final String MODEL = System.getProperty(CompassSensor.class.getSimpleName() + ".Model");
	public static final String COM_PORT = System.getProperty(CompassSensor.class.getSimpleName() + "." + MODEL + ".comPort");
	public static final String BAUDRATE = System.getProperty(CompassSensor.class.getSimpleName() + "." + MODEL + ".baudrate");
	public static final String UPDATERATE = System.getProperty(CompassSensor.class.getSimpleName() + "." + MODEL + ".updateRate");
	private static long useUpdateRate = Long.parseLong(UPDATERATE);
	
	/**
	 * Creates an object which allows querying the compass sensor.
	 * An asyncronous thread will be started to update the world model each UpdateRate-cycle.
	 */
	public CompassSensor(){
		
		// create an instance of the sairoComm Class
		COMPort myCOM = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;
		myCOM.open();

		keepRunning=true;
		(sensorThread=new Thread(new CompassSensorThread(this))).start();
		
	}
	
	/**
	 * Background worker which regularly updates the world model. 
	 */
	static class CompassSensorThread extends Thread {
		CompassSensor compassInstance;
		long start, end;
		private static Logger LOG = Logger.getLogger(CompassSensorThread.class);

		
		public CompassSensorThread(CompassSensor sensorInstance) {
			this.compassInstance = sensorInstance;
		}

		public void run() {

			while (compassInstance.keepRunning) {
				try {
					
					
					start = System.currentTimeMillis();
					
					// create empty value array
					String[] valueArray = null;
					// read the values (7 for 7 rows)
					try {
						valueArray = compassInstance.myCOM.readLine(10);
					} catch (IOException e1) {
						LOG.warn("no sensor data available", e1);
					}
					
					// wenn Werte gelesen wurden
					if (valueArray != null) {
						OS500dataSet dataSet = new OS500dataSet();
						// no of usefull items
						int i = 0;
						
						// Foreach List-Element
						for (String item: valueArray) {
							//Verify String and add to i or throw away
							if (item != null && nmea.verifyString(item)) {
								
								String[] data = (nmea.splitNmea(item));
								// at the moment, we expect at least 12 fields
								if (data.length<12) {
									compassInstance.errorcount++;
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
								compassInstance.errorcount++;
							}
						}
						
						// if there is at least one sample, try to get the average value and set it to Worldmodell
						if (i >= 1) {
							
							// TODO erase aberration; calculate average
							if(dataSet.medianAll()) {
								// set to Worldmodel
								double usefullrate = i/valueArray.length;
								
								this.compassInstance.myCompass = new Compass(dataSet.getAzimuth(),
																	 dataSet.getPitch(), 
																	 dataSet.getRoll(),
																	 dataSet.getTemp(),
																	 dataSet.getMagS(),
																	 dataSet.getMagVect(),
																	 dataSet.getAccS(), 
																	 dataSet.getAccVect(), 
																	 usefullrate,
																	 System.currentTimeMillis());
								WorldModelImpl.getInstance().getCompassModel().setCompass(this.compassInstance.myCompass);
								
								LOG.debug("Azimuth: " + dataSet.getAzimuth() + LogTextblocks.valueSeperator
										+ " Pitch: " + dataSet.getPitch() + LogTextblocks.valueSeperator
										+ " Roll: "+ dataSet.getRoll() + LogTextblocks.valueSeperator
										+ " Temp: "+ dataSet.getTemp() + LogTextblocks.valueSeperator
										+ " out of " + i + " useful samples (" + usefullrate + ")");
								
							}
						}
						
						end = System.currentTimeMillis();
						// Sleep the difference of update rate and calculation time of this run
						long duration = end-start; //Koppe: 2x end-start => 1x duration
						if( duration > useUpdateRate)
							Thread.sleep(useUpdateRate);
						else
							Thread.sleep(useUpdateRate - duration);
						
					}
				}  catch (InterruptedException e) {
					// something went wrong, stop the loop, throw an error to the next higher level
					compassInstance.keepRunning = false;
					
					LOG.debug("thread interrupted", e);
				}
			}
		}
	}

	/**
	 * Stops the background worker thread.
	 */
	public void shutdown()
	{
		if(sensorThread != null && !sensorThread.isInterrupted())
		{
			keepRunning=false;
			try {
				sensorThread.join();
			} catch (InterruptedException e) {
				LOG.fatal(e, e.fillInStackTrace());
			}
			
		}
	}
	
	/**
	 * @return the update rate used in milliseconds
	 * @see de.fhb.sailboat.serial.sensor.CompassSensor#setUpdateRate(long)
	 */
	public long getUpdateRate() {
		return useUpdateRate;
	}

	/**
	 * @param new update rate to use in milliseconds
	 * @see de.fhb.sailboat.serial.sensor.CompassSensor#getUpdateRate()
	 */
	public void setUpdateRate(long updateRate) {
		CompassSensor.useUpdateRate = updateRate;
	}

	/**
	 * @return the compass object which contains the current sensor values
	 * @see de.fhb.sailboat.data.Compass
	 */
	public String getCompass() {
		return myCompass.toString();
	}
	
	/**
	 * @return number of unusable or faulty data send by the compass sensor 
	 */
	public int getErrorcount() {
		return errorcount;
	}

	/**
	 * @param number of unusable or faulty data send by the compass sensor
	 */
	public void setErrorcount(int errorcount) {
		this.errorcount = errorcount;
	}

	/**
	 * Represents the current set of data reported by the compass sensor.
	 * Also calculates the median value of them.
	 * 
	 * @author schmidst
	 *
	 */
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

		/**
		 * Calculates median values of all data lists.
		 * 
		 * @return 
		 */
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
		
		/**
		 * Calculates the median value of a given data list.
		 * 
		 * @param values
		 * @return median value
		 */
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
		    	//double upper = values.get(values.size()/2);
		    	
		    	//return (lower + upper) / 2.0;
		    	return lower;
		    	
		    	/*
		    	 * Median-Rechnung auskommentiert, da sie nicht in jedem Fall richtig die gew�nschten Werte ausgibt.
		    	 * W�hlt er f�r lower und upper z. B. die Werte 0,1� und 359,9�, wird 180� ausgegeben.
		    	 */
		    	
		    }	
		}
	}
}
