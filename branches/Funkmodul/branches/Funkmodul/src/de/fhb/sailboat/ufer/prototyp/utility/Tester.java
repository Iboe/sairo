package de.fhb.sailboat.ufer.prototyp.utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Multiple-purpose testing main class.
 * @author Patrick Rutter
 *
 */
public class Tester {

	public static void main(String[] args) {
		System.out.println("Starting Test Session at " + getDateTime());
		// START
		
		ConfigMap cFile = new ConfigMap();
		cFile.put("Egal ist 88", "88 ist Egal");
		cFile.put("Bent", "bangt");
		cFile.put("Brandenburg", "Pack dir Essen ein");
		cFile.put("Karoshi", "Das ist ein Smoothie!");
		cFile.put("Faust", "Auf der Sahne schwimmt ein Rollstuhl");
		cFile.put("They might be Giants", "Birdhouse in your Soul");
		cFile.put("Integer", "123");
		cFile.put("Double", "1.23");
		cFile.put("Boolean", "true");
		cFile.removeEntry("They might be Giants");
		System.out.println("Integer Test: " + cFile.getEntryIntegerValue("Integer"));
		System.out.println("Double Test: " + cFile.getEntryDoubleValue("Double"));
		if (cFile.getEntryBooleanValue("Boolean")) {
			System.out.println("Boolean Test: " + "TRUE");
		}
		else System.out.println("Boolean Test: " + "FALSE");
		System.out.println("cFile Size: " + cFile.getHashMap().size());
		System.out.println("Valuetest Jr: " + cFile.getEntryValue("Jr"));
		ConfigWriter cWriter = new ConfigWriter(cFile);
		cWriter.writeConfigFile("TestingThis.ini");
		
		ConfigReader cReader = new ConfigReader();
		cFile = cReader.readConfigFile(new File("C:\\Test.txt"));
		System.out.println("Integer Test: " + cFile.getEntryIntegerValue("Integer"));
		System.out.println("Integer Test: " + cFile.getEntryValue("Integer"));
		
		
		// END
		System.out.println("Terminating Test Session at " + getDateTime());
	}
	
	private static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:ms");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
