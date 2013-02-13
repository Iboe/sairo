package de.fhb.sailboat.serial.sensor;

import java.io.IOException;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class WindSensor {

	private double airSpeed;
	private int airDirection;
	COMPort myCOM;
	private static Logger LOG = Logger.getLogger(GpsSensor.class);

	
	public WindSensor(int port) {
		// create an instance of the sairoComm Class
		COMPort myCOM = new COMPort(port,4800, 0);
		this.myCOM = myCOM;
		myCOM.open();

		(new WindSensorThread(this)).start();
	}

	public double getAirSpeed() {
		return this.getAirSpeed();
	}

	public double getAirDirection() {
		return this.airDirection;
	}

	static class WindSensorThread extends Thread {
		WindSensor windInstance;
		int satelites;
		private static Logger LOG = Logger.getLogger("Windsensor: ");

		public WindSensorThread(WindSensor sensorInstance) {
			this.windInstance = sensorInstance;
		}

		public void run() {

			while (!isInterrupted()) {
				
				// create empty value array
				String[] valueArray = null;
				
				// read the values (7 for 7 rows)
				try {
					valueArray = windInstance.myCOM.readLine(1);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				// wenn Werte gelesen wurden
				if (valueArray != null) {
					
					// alle Werte abarbeiten
					for (String value : valueArray) {
						String myNmea[] = nmea.stringToArray(value);
						if (myNmea != null) {
							if (myNmea[0].equals("$WIMWV")) {
								
									LOG.debug("WindDirection: " + Integer.parseInt(myNmea[1]) +
											" WindSpeed: "+ Double.parseDouble(myNmea[3]));								
									
									Wind wind = new Wind(Integer.parseInt(myNmea[1]), 
											Double.parseDouble(myNmea[3]));
									
									WorldModelImpl.getInstance().getWindModel().setWind(wind);

									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
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
}
