package de.fhb.sailboat.emulator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegEx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input = "DEBUG 2012-10-17 13:24:44,247 [de.fhb.sailboat.worldmodel.History] element added:" +
				"Compass [azimuth: 263.8, pitch: 7.9, roll: 5.0]";
		Pattern p = Pattern.compile("(azimuth: )(\\d+).(\\d+)");
		Matcher m = p.matcher(input);

		if (m.find()) {
			System.out.println(m.group());
		    System.out.println(m.group(2));
		}
				

	}

}
