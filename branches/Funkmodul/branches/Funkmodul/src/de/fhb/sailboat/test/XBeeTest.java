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

	public static class PropertiesInitializer {

		/**
		 * Loads the properties from the configuration file and adds them to the
		 * system properties.
		 */
		public void initializeProperties() {
			Properties prop = new Properties();
			Properties systemProps = System.getProperties();
			InputStream stream = this.getClass().getClassLoader()
					.getResourceAsStream(CONFIG_FILE);
			Set<Object> keySet;

			try {
				prop.load(stream);
			} catch (IOException e) {
				throw new IllegalStateException("could not load properties", e);
			}

			keySet = prop.keySet();
			for (Object key : keySet) {
				systemProps.put(key, prop.get(key));
			}
		}

	}
}
