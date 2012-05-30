package de.fhb.sailboat.wificonnection;

import org.apache.log4j.Logger;

import com.sun.jmx.mbeanserver.OpenConverter;

import de.fhb.sailboat.serial.sensor.WindSensor.WindSensorThread;
import de.fhb.sailboat.serial.serialAPI.COMPort;

final class XbeeCom {

	private int COMPORT = 0;
	private COMPort myCOM;

	/**
	 * Konstruktor
	 * 
	 * @param comPort
	 *            ModulComport
	 */
	public XbeeCom(int comPort) {
		this.COMPORT = comPort;
		(new XbeeThread(this)).start();
	}

	 void OpenComPort() {
		this.myCOM = new COMPort(COMPORT, 4800, 0);
		myCOM.open();
	}

	static class XbeeThread extends Thread {
		XbeeCom xbeeInstance;
		private static Logger LOG = Logger.getLogger("Xbee: ");
		
		public XbeeThread(XbeeCom xbeeInstance)
		{
			if (xbeeInstance == null) {
				this.xbeeInstance = xbeeInstance;
			}
		}
		
		public void run()
		{
			while(true)
			{
				 
			}
			
		}		
		
	}
}
