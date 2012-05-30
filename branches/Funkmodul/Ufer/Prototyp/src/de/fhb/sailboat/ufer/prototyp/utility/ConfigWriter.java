package de.fhb.sailboat.ufer.prototyp.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * This class is used to write configuration files using the ConfigMap-format.
 * @author Patrick Rutter
 *
 */
public class ConfigWriter {
	
	private ConfigMap configMap;
	
	/**
	 * Loads an already existing HashMap (use this constructor in conjunction with ConfigReader.getConfigMap to alter/ append existing files.)
	 * @param configMap
	 */
	public ConfigWriter(ConfigMap configMap) {
		this.configMap = new ConfigMap(configMap.getHashMap());
	}
	
	/**
	 * Writes the current configMap Entries to a textfile. Takes no action if the configMap is empty.
	 * @param path of the file to be written
	 */
	public void writeConfigFile(File path) {
		if (configMap.getHashMap().size() > 0) {
			if (path.exists()) {
				path.delete();
			}
		    try {
				path.createNewFile();
				PrintWriter writer = new PrintWriter(new FileWriter(path));
				Collection<String> config = configMap.getHashMap().values();
				Iterator<String> iterator  = config.iterator();
				//writer.println("#Start;");
				writer.println(ConfigMap.AUTO_GENERATED_TAG + "Filename=" + path.toString() + ConfigMap.STATEMENT_DELIMITER);
				writer.println(ConfigMap.AUTO_GENERATED_TAG + "DateTimeCreated=" + getDateTime() + ConfigMap.STATEMENT_DELIMITER);
				while (iterator.hasNext()) {
					writer.println(iterator.next());
				}
				//writer.println("#End");
				writer.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Writes the current configMap Entries to a file in the current working directory of the application. Takes no action if the configMap is empty.
	 * @param fileName
	 */
	public void writeConfigFile(String fileName) {
		writeConfigFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + fileName));
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
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
