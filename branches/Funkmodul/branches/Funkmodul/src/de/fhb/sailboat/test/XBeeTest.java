package de.fhb.sailboat.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import com.rapplogic.xbee.api.XBeeException;

import de.fhb.sailboat.test.Initializier.PropertiesInitializer;
import de.fhb.sailboat.wificonnection.IwifiXbee;
import de.fhb.sailboat.wificonnection.WifiXbee;

public class XBeeTest {

	private static final String CONFIG_FILE = "config.properties";

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws XBeeException
	 * @throws IOException 
	 */
	public static void main(String[] args) throws XBeeException,
			InterruptedException, IOException {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		IwifiXbee xbee = new WifiXbee();
		xbee.initializeXbee();
		xbee.sendDataXbee("bla");
	}

	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws XBeeException
	 * @throws IOException 
	 
	public static void main(String[] args) throws XBeeException,
			InterruptedException, IOException {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		IwifiXbee xbee = new WifiXbee();
		xbee.initializeXbee();
		xbee.read();
	}
	*/
	
}
