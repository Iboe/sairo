package de.fhb.sailboat.missionplayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;

/**
 * This class maps a line of the LOG-File from Text-Format to a Object-format
 * for emulation of past use of the System
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class DataMapper {

	private static Pattern azimuthPattern = Pattern
			.compile("(azimuth: )(\\d+.\\d+)");
	private static Pattern pitchPattern = Pattern
			.compile("(pitch: )(\\d+.\\d+)");
	private static Pattern rollPattern = Pattern.compile("(roll: )(\\d+.\\d+)");

	private static Pattern directionPattern = Pattern
			.compile("(direction: )(\\d+)");
	private static Pattern speedPattern = Pattern
			.compile("(speed: )(\\d+.\\d+)");

	private static Pattern latitudePattern = Pattern
			.compile("(latitude: )(\\d+.\\d+)");
	private static Pattern longitudePattern = Pattern
			.compile("(longitude: )(\\d+.\\d+)");
	private static Pattern satelitesPattern = Pattern
			.compile("(satelites: )(\\d+)");

	/**
	 * maps the information from a Compass-LOG to Object
	 * 
	 * @param line
	 * @return Compass
	 */
	public static Compass createCompass(String line) {

		Double azimuth = 0d;
		Double pitch = 0d;
		Double roll = 0d;

		Matcher matcher = azimuthPattern.matcher(line);
		if (matcher.find()) {
			azimuth = Double.valueOf(matcher.group(2));
		}

		matcher = pitchPattern.matcher(line);
		if (matcher.find()) {
			pitch = Double.valueOf(matcher.group(2));
		}

		matcher = rollPattern.matcher(line);
		if (matcher.find()) {
			roll = Double.valueOf(matcher.group(2));
		}

		return new Compass(azimuth, pitch, roll);
	}

	/**
	 * maps the information from a GPS-LOG to Object
	 * 
	 * @param line
	 * @return GPS
	 */
	public static GPS createGPS(String line) {

		double latitude = 0.0d;
		double longitude = 0.0d;
		int nrSatelites = 0;
		double speed = 0.0d;

		Matcher matcher = latitudePattern.matcher(line);
		if (matcher.find()) {
			latitude = Double.valueOf(matcher.group(2));
		}

		matcher = longitudePattern.matcher(line);
		if (matcher.find()) {
			longitude = Double.valueOf(matcher.group(2));
		}

		matcher = satelitesPattern.matcher(line);
		if (matcher.find()) {
			nrSatelites = Integer.valueOf(matcher.group(2));
		}

		matcher = speedPattern.matcher(line);
		if (matcher.find()) {
			speed = Double.valueOf(matcher.group(2));
		}

		return new GPS(latitude, longitude, nrSatelites, speed);
	}

	/**
	 * maps the information from a Wind-LOG to Object
	 * 
	 * @param line
	 * @return Wind
	 */
	public static Wind createWind(String line) {

		int direction = 0;
		double speed = 0d;

		Matcher matcher = directionPattern.matcher(line);
		if (matcher.find()) {
			direction = Integer.valueOf(matcher.group(2));
		}

		matcher = speedPattern.matcher(line);
		if (matcher.find()) {
			speed = Double.valueOf(matcher.group(2));
		}

		return new Wind(direction, speed);
	}
}