package de.fhb.sailboat.ufer.prototyp.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to load/ read configuration files written by ConfigWriter/ using the ConfigMap-format.
 * @author Patrick Rutter
 *
 */
public class ConfigReader {
	
	private ConfigMap configMap;
	
	public ConfigReader() {
		configMap = new ConfigMap();
	}
	
	/**
	 * Reads the specified file, puts its content into a ContentMap and returns it.
	 * @param path
	 */
	public ConfigMap readConfigFile(File path) {
		configMap = new ConfigMap();
		
		if ((path.exists()) && (path.isFile())) {
			// First of all, read contents of the file into a StringBuilder
			StringBuilder contents = new StringBuilder();
		    try {
		    	BufferedReader input =  new BufferedReader(new FileReader(path));
		    	try {
		    		String line = null;
		    		while (( line = input.readLine()) != null){
		    			contents.append(line);
		        	}
		    	}
		    	finally {
		    		input.close();
		    	}
		    }
		    catch (IOException ex){
		    	ex.printStackTrace();
		    }
		    // Now parse the contents and convert them into the desired HashMap-Format
		    String line = "";
		    while (contents.lastIndexOf(ConfigMap.STATEMENT_DELIMITER + "") != -1) {
		    	line = contents.substring(0, contents.indexOf(ConfigMap.STATEMENT_DELIMITER + ""));
		    	contents.delete(0, contents.indexOf(ConfigMap.STATEMENT_DELIMITER + "") + 1);
		    	if (line.length() > 0) { 
		    		//System.out.println("Adding line: " + line);
		    		addToConfigMap(line);
		    	}
		    }
		    
		}
		
		return configMap;
	}
	
	/**
	 * Reads the file specified name in the current working directory of the application, puts its content into a ContentMap and returns it.
	 * @param fileName
	 * @return
	 */
	public ConfigMap readConfigFile(String fileName) {
		File path = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + fileName);
		return readConfigFile(path);
	}
	
	/**
	 * Returns a reference to the last successfully read ConfigMap. If no such exists, the returned ConfigMap will be empty.
	 * @return ConfigMap
	 */
	public ConfigMap getLastRead() {
		return configMap;
	}
	
	/**
	 * Returns true only if the chosen file already exists
	 * @return
	 */
	public boolean fileExists(File path) {
		return (path.exists());
	}
	
	/**
	 * Returns true only if a file with the chosen name exists already in the working directory of the application.
	 * @return
	 */
	public boolean fileExists(String fileName) {
		File path = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + fileName);
		return (path.exists());
	}
	
	/**
	 * Support method which splits a string in the ConfigMap format and adds it to it.
	 * @param line
	 */
	private void addToConfigMap(String line) {
		String identifier = "";
		String value = "";
		int pos = 0;
		
		// parse for identifier and value
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) != ConfigMap.VALUE_DELIMITER) {
				identifier = identifier + line.charAt(i);
			}
			else {
				pos = i+1;
				i = line.length();
			}
		}
		for (int i = pos; i < line.length(); i++) {
			value = value + line.charAt(i);
		}
		
		// put entry into map
		//System.out.println("Putting in " + identifier + " / " + value);
		configMap.put(identifier, value);
	}
}
