package de.fhb.sailboat.serial.sensor;

import java.io.IOException;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class WindSensor {

	private double airSpeed;
	private int airDirection;
	private COMPort myCOM;
	
	//private static Logger LOG = Logger.getLogger(WindSensor.class);
	public static final String MODEL = System.getProperty(WindSensor.class.getSimpleName() + ".Model");
	public static final String COM_PORT = System.getProperty(WindSensor.class.getSimpleName() + "." + MODEL + ".comPort");
	public static final String BAUDRATE = System.getProperty(WindSensor.class.getSimpleName() + "." + MODEL + ".baudRate");
	public static final String UPDATERATE = System.getProperty(WindSensor.class.getSimpleName() + "." + MODEL + ".updateRate");
	private static final int useUpdateRate = Integer.parseInt(UPDATERATE);
	
	/**
	 * Creates an object which allows querying the wind sensor.
	 * An asyncronous thread will be started to update the world model each UpdateRate-cycle.
	 */
	public WindSensor() {
		COMPort myCOM = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;
		myCOM.open();

		(new WindSensorThread(this)).start();
	}

	/**
	 * @return the air speed reported by the sensor
	 * @see de.fhb.sailboat.serial.sensor.WindSensor#getAirDirection()
	 */
	public double getAirSpeed() {
		return this.airSpeed;
	}

	/**
	 * @return the direction where the wind blows to
	 * @see de.fhb.sailboat.serial.sensor.WindSensor#getAirSpeed()
	 */
	public double getAirDirection() {
		return this.airDirection;
	}

	/**
	 * Background worker which regularly updates the world model. 
	 */
	static class WindSensorThread extends Thread {
		WindSensor windInstance;
		int satelites;
		private static Logger LOG = Logger.getLogger(WindSensor.class);

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
									
									Wind wind = new Wind(Integer.parseInt(myNmea[1]), Double.parseDouble(myNmea[3]));
									
									WorldModelImpl.getInstance().getWindModel().setWind(wind);

									try {
										Thread.sleep(useUpdateRate);
									} catch (InterruptedException e) {
										LOG.fatal("Error Occured:" + e);
										e.printStackTrace();
									}
							} // End If
						} // End If						
					} // End For
					
				} // End If				
			} // End While
			
		} // End method run()
	} // End thread
}
