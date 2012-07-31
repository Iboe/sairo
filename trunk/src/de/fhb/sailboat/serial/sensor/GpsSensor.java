package de.fhb.sailboat.serial.sensor;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.CASTORE;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class GpsSensor {

	double latitude;
	double longitude;
	Integer satelites = null;
	COMPort myCOM;
	private static Logger LOG = Logger.getLogger(GpsSensor.class);

	public GpsSensor(int port) {
		// create an instance of the sairoComm Class
		COMPort myCOM = new COMPort(port, 0, 0);
		this.myCOM = myCOM;
		myCOM.open();

		(new GpsSensorThread(this)).start();
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	private int getSatelites() {
		return this.satelites;
	}

	static class GpsSensorThread extends Thread {
		GpsSensor gpsInstance;
		int satelites;
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
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// wenn Werte gelesen wurden
				if (valueArray != null) {

					// alle Werte abarbeiten
					for (String value : valueArray) {
						String myNmea[] = nmea.stringToArray(value);
						if (myNmea != null) {
							if (myNmea[0].equals("$GPGGA")) {
								if (!myNmea[1].equals("0.0")
										&& !myNmea[3].equals("0.0")) {
									// Schreiben in Weltmodell
									double latitude = Double
											.parseDouble(myNmea[2]);
									double longitude = Double
											.parseDouble(myNmea[4]);

									this.satelites = Integer
											.parseInt(myNmea[7]);

									int gradLat = (int) (latitude / 100);
									int gradLong = (int) (longitude / 100);
									double minLat = latitude - gradLat * 100;
									double minLong = longitude - gradLong * 100;

									minLat *= 10. / 600.;
									minLong *= 10. / 600.;

									// To show values
									// LOG.debug(MessageFormat.format(
									// "Latitude: {0},"
									// + " Longtitude: {1},"
									// + " Satelites: {2}",
									// minLat, minLong, satelites));

									GPS myGps = new GPS(gradLat + minLat,
											gradLong + minLong, this.satelites, lastSpeed); //Micha: here assigning lastSpeed of the other parse operation
									WorldModelImpl.getInstance().getGPSModel()
											.setPosition(myGps);

									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										LOG.fatal("Error Occured:" + e);
										e.printStackTrace();
									}
								}
							} else if (myNmea[0].equals("$GPGGA")) {

								if (!myNmea[1].equals("0.0")
										&& !myNmea[3].equals("0.0")) {
									{
										double speed = Double
												.parseDouble(myNmea[7]) * 0.51444;
										DecimalFormat f = new DecimalFormat(
												"#0.00");
										//GPS myGps = new GPS(
										//		Double.parseDouble(f
										//				.format(speed)));
										
										//Micha: Just parsing the speed and saving it in lastSpeed. 
										//		 That's assigned to the GPS Object generated for latitude and longitude in the first if-clause 
										lastSpeed=Double.parseDouble(f.format(speed));
										
										// WorldModelImpl.getInstance().getGPSModel()
										// .setPosition(myGps);

									}
								}
							}

						}
					}
				}
			}
		}
	}
}
