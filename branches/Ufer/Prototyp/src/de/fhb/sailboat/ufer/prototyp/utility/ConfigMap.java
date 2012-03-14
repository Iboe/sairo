package de.fhb.sailboat.ufer.prototyp.utility;

import java.util.HashMap;

/**
 * This class represents a basic structure for configuration files.
 * Entries are stored as identifier (for example "DebugMode") and value (for example "0") in string format. Once entered into the
 * HashMap containing all added entries (configMap) the value will extended by (combined) the full config-Entry (for example "0" becomes <identifer>"="<value>";",
 * meaning "DebugMode=0;"). If read, this procedure will be reversed, returning only the plain value associated to an identifier (for "DebugMode" -> "0").
 * @author Patrick Rutter
 *
 */
public class ConfigMap {

	// CONSTANTS
	
	public final static char STATEMENT_DELIMITER = ';';		// all statements within a configuration file need to terminate with this character
															// also this character must not be used in any other way
	public final static char VALUE_DELIMITER = '=';			// this character separates a identifier from its value, it must not be used otherwise
	public final static char AUTO_GENERATED_TAG = '#'; 		// auto-generated statements start with this character per default
	
	// VARIABLES
	private HashMap<String, String> configMap;
	
	public ConfigMap() {
		configMap = new HashMap<String, String>();
	}
	
	public ConfigMap(HashMap<String, String> hashMap) {
		this();
		setHashMap(hashMap);
	}
	
	/**
	 * Gets the currently used HashMap<String, String>.
	 * @return HashMap<String, String>
	 */
	public HashMap<String, String> getHashMap() {
		HashMap<String, String> copyMap = new HashMap<String, String>();
		copyMap.putAll(configMap);
		
		return copyMap;
	}
	
	/**
	 * Sets the currently used HashMap<String, String>.
	 * @param setMap
	 */
	public void setHashMap(HashMap<String, String> setMap) {
		configMap.clear();
		configMap.putAll(setMap);
	}
	
	/**
	 * Adds a new entry to the configMap. Pre-existing identifiers will be updated by value.
	 * If either identifier or value are empty, the command is ignored.
	 * @param identifier
	 * @param value
	 */
	//TODO better way to notify and catch empty identifier/ value
	public void put(String identifier, String value) {
		//System.out.println("Putting " + identifier + "=" + value);
		//System.out.println("Result: " + identifier + "=" + combine(identifier, value));
		if ((identifier.length() > 0) && (value.length() > 0)) configMap.put(identifier, combine(identifier, value));
	}
	
	/**
	 * Returns true if the specified identifier is present within configMap.
	 * @param identifier
	 * @return true only if identifier exists within configMap
	 */
	public boolean isEntryPresent(String identifier) {
		return configMap.containsKey(identifier);
	}
	
	/**
	 * Removes the entry with the specified identifier, if present.
	 * @param identifier
	 */
	public void removeEntry(String identifier) {
		if (isEntryPresent(identifier)) configMap.remove(identifier);
	}
	
	/**
	 * Returns the value of the specified identifier. If such a identifier is not present an empty string will be returned instead.
	 * @param identifier
	 * @return value
	 */
	public String getEntryValue(String identifier) {
		String myReturn = "";
		
		if (isEntryPresent(identifier)) {
			String value = configMap.get(identifier);
			myReturn = deCombine(identifier, value);
		}
		
		return myReturn;
	}
	
	/**
	 * Returns the value of the specified identifier as integer. If such a identifier is not present -1 will be returned instead.
	 * This method requires the value to be in a valid format to work correctly ("1332" instead of "1332%").
	 * @param identifier
	 * @return representation of value as int
	 */
	public int getEntryIntegerValue(String identifier) {
		int myReturn = -1;
		
		if (isEntryPresent(identifier)) {
			String value = configMap.get(identifier);
			value = deCombine(identifier, value);
			myReturn = Integer.parseInt(value);
		}
		
		return myReturn;
	}
	
	/**
	 * Returns the value of the specified identifier as double. If such a identifier is not present -1 will be returned instead.
	 * This method requires the value to be in a valid format to work correctly ("0.1332" instead of "0.1332%").
	 * @param identifier
	 * @return representation of value as int
	 */
	public double getEntryDoubleValue(String identifier) {
		double myReturn = -1;
		
		if (isEntryPresent(identifier)) {
			String value = configMap.get(identifier);
			value = deCombine(identifier, value);
			myReturn = Double.parseDouble(value);
		}
		
		return myReturn;
	}
	
	/**
	 * Returns the value of the specified identifier as boolean. If such a identifier is not present false will be returned instead.
	 * Internally this method simply checks if value is equal to "1" oder "true", and will only then return true.
	 * @param identifier
	 * @return
	 */
	public boolean getEntryBooleanValue(String identifier) {
		boolean myReturn = false;
		
		if (isEntryPresent(identifier)) {
			String value = configMap.get(identifier);
			value = deCombine(identifier, value);
			if ((value.equals("1")) || (value.equals("true"))) myReturn = true;
		}
		
		return myReturn;
	}
	
	/**
	 * Simply converts the value to the format "<identifier>=<value>".
	 * @param identifier
	 * @param value
	 * @return "<identifier><VALUE_DELIMITER><value><STATEMENT_DELIMITER>"
	 */
	private static String combine(String identifier, String value) {
		return (identifier + VALUE_DELIMITER + value + STATEMENT_DELIMITER);
	}
	
	/**
	 * Decombines the identifier+value construct to plain value.
	 * @param identifier
	 * @param value
	 * @return
	 */
	private static String deCombine(String identifier, String value) {
		String myString = "";
		myString = value.replace((identifier + VALUE_DELIMITER), "");
		myString = myString.replace(STATEMENT_DELIMITER + "", "");;
		return myString;
	}
	
}
