package de.fhb.sailboat.operationPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;

public class DataMapper {
	
	private static Pattern azimuthPattern = Pattern.compile("(azimuth: )(\\d+.\\d+)");
	private static Pattern pitchPattern = Pattern.compile("(pitch: )(\\d+.\\d+)");
	private static Pattern rollPattern = Pattern.compile("(roll: )(\\d+.\\d+)");

	public static Compass createCompass(String line) {
		
		Double azimuth=0d;
		Double pitch=0d;
		Double roll=0d;

		
		Matcher matcher = azimuthPattern.matcher(line);
		if (matcher.find()) {
			System.out.println(matcher.group());
			azimuth=Double.valueOf(matcher.group(2));
		}
		
		matcher = pitchPattern.matcher(line);
		if (matcher.find()) {
			System.out.println(matcher.group());
			pitch=Double.valueOf(matcher.group(2));
		}
		
		matcher = rollPattern.matcher(line);
		if (matcher.find()) {
			System.out.println(matcher.group());
			roll=Double.valueOf(matcher.group(2));
		}
		
		return	new Compass(azimuth, pitch, roll);
	}

	public static GPS createGPS(String line) {
		// TODO in progress
		
		return	new GPS(0.0d, 0.0d);
	}

	public static Wind createWind(String line) {
		// TODO in progress

		return	new Wind(0, 0.0d);
	}
	
	
	
	

}
