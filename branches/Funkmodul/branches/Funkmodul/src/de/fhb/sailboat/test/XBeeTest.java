package de.fhb.sailboat.test;

import com.rapplogic.xbee.api.XBeeException;

import de.fhb.sailboat.wificonnection.IwifiXbee;
import de.fhb.sailboat.wificonnection.WifiXbee;

public class XBeeTest {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws XBeeException
	 */
	public static void main(String[] args) throws XBeeException,
			InterruptedException {
		IwifiXbee xbee = new WifiXbee();
		xbee.OpenXbee();
	}

}
