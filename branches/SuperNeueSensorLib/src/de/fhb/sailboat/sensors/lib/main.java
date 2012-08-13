package de.fhb.sailboat.sensors.lib;

import de.fhb.sailboat.sensors.lib.nmeaSensors.NmeaSensor;
import de.fhb.sailboat.sensors.lib.nmeaSensors.gps.NL402U;
import gnu.io.CommPort;
import gnu.io.SerialPort;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SerialCommPort port = new SerialCommPort(14);
		NL402U gpsSensor = new NL402U();
		gpsSensor.connect(port);
		gpsSensor.run();
		//gpsSensor.close();
	}

}
