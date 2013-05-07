package de.fhb.sailboat.serial.sensor;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class GpsSensor {

	private double latitude;
	private double longitude;
	private COMPort myCOM;
	
	// private static Logger LOG = Logger.getLogger(GpsSensor.class);
	public static final String MODEL = System.getProperty(GpsSensor.class.getSimpleName() + ".Model");
	public static final String COM_PORT = System.getProperty(GpsSensor.class.getSimpleName() + "." + MODEL + ".comPort");
	public static final String BAUDRATE = System.getProperty(GpsSensor.class.getSimpleName() + "." + MODEL + ".baudRate");
	public static final String UPDATERATE = System.getProperty(GpsSensor.class.getSimpleName() + "." + MODEL + ".updateRate");
	private static final int useUpdateRate = Integer.parseInt(UPDATERATE);
	private static int satellites = 0;
	
	/**
	 * Creates an object which allows querying the GPS sensor.
	 * An asyncronous thread will be started to update the world model each UpdateRate-cycle.
	 */
	public GpsSensor() {
		COMPort myCOM = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;
		myCOM.open();

		(new GpsSensorThread(this)).start();
	}

	/**
	 * @return the latitude reported by the GPS sensor
	 * @see de.fhb.sailboat.serial.sensor.GpsSensor#getLongitude()
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @return the longitude reported by the GPS sensor
	 * @see de.fhb.sailboat.serial.sensor.GpsSensor#getLatitude()
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @return the number of GPS and GALILEO satellites used for determining the current position (up to 50) 
	 */
	private int getSatellites() {
		return GpsSensor.satellites;
	}

	/**
	 * Background worker which regularly updates the world model. 
	 */
	static class GpsSensorThread extends Thread {
		GpsSensor gpsInstance;
		private static Logger LOG = Logger.getLogger(GpsSensorThread.class);

		public GpsSensorThread(GpsSensor sensorInstance) {
			this.gpsInstance = sensorInstance;
		}

		public void run() {

			double lastSpeed=0;
			
			while (!isInterrupted()) {

				// create empty value array
				String[] valueArray = null;

				// read the values (7 for 7 rows)
				try {
					valueArray = gpsInstance.myCOM.readLine(7);
				} catch (IOException e) {
					e.printStackTrace();
				}

				// wenn Werte gelesen wurden
				if (valueArray != null) {

					// alle Werte abarbeiten
					for (String value : valueArray) {
						String myNmea[] = nmea.stringToArray(value);
						
						if (myNmea != null && myNmea.length!=0) {
							if (myNmea[0].equals("$GPGGA")) {
								if (!myNmea[1].equals("0.0") && !myNmea[3].equals("0.0")) {
									
									// Schreiben in Weltmodell
									double latitude = Double.parseDouble(myNmea[2]);
									double longitude = Double.parseDouble(myNmea[4]);
									satellites = Integer.parseInt(myNmea[7]);

									int gradLat = (int) (latitude / 100);
									int gradLong = (int) (longitude / 100);
									double minLat = latitude - gradLat * 100;
									double minLong = longitude - gradLong * 100;

									minLat *= 10d / 600d;
									minLong *= 10d / 600d;

									// To show values
									// LOG.debug(MessageFormat.format(
									// "Latitude: {0},"
									// + " Longtitude: {1},"
									// + " Satellites: {2}",
									// minLat, minLong, satellites, lastSpeed));

									GPS myGps = new GPS(gradLat + minLat, gradLong + minLong, gpsInstance.getSatellites(), lastSpeed); 
									WorldModelImpl.getInstance().getGPSModel().setPosition(myGps);
									LOG.info("GPS coordinates: " + myGps.toString());
									
									try {
										Thread.sleep(useUpdateRate);
									} catch (InterruptedException e) {
										LOG.fatal("Error Occured:" + e);
										e.printStackTrace();
									}
								}
								
							} else if (myNmea[0].equals("$GPRMC")) {

								if (!myNmea[3].equals("0.0") && !myNmea[5].equals("0.0")) {
									
									//XXX Hier Moegliche Fehlerursache bezgl falscher Gescwhindigkeitswerte
									//umrechnung von Knoten auf m/s
									if(!myNmea[7].isEmpty()){
										
										double speed = Double.parseDouble(myNmea[7]) * 0.51444;
										DecimalFormat f = new DecimalFormat("#0,00");
										
										//Micha: Just parsing the speed and saving it in lastSpeed. 
										//		 That's assigned to the GPS Object generated for latitude and longitude in the first if-clause 
										lastSpeed=Double.parseDouble(f.format(speed));	
										
									} // End If
								} // End If
								
							} // End Else IF
						} // End If
						
					} // End For
				} // End If
				
			} // End while
			
		} // End method run()
		
	} // End thread
}
