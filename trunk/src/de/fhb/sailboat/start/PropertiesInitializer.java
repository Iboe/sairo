package de.fhb.sailboat.start;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Class for loading the properties. Loads the properties from a configuration file and puts them
 * into the {@link System} properties, so every class can access them easily.
 * This is separated from the {@link Initializier} to be accessible from tests. 
 *  
 * @author hscheel
 *
 */
public class PropertiesInitializer {

	/**
	 * The name of the configuration file.
	 */
	private static final String CONFIG_FILE = "config.properties";
	
	/**
	 * Loads the properties from the configuration file and adds them to the system properties. 
	 */
	public void initializeProperties() {
		Properties prop = new Properties();
		Properties systemProps = System.getProperties();
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
		Set<Object> keySet;
		
		try	{
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
