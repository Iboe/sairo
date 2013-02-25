package de.fhb.sailboat.control.pilot;

/**
 * Helper class that contains methods, that can be used in different classes responsible 
 * for navigation.
 * 
 * @author schmidst
 *
 */
public class Calculations {

	private static final double convertDeg2Rad = (2 * Math.PI) / 360;
	private static final double convertRad2Deg = 360 / (2 * Math.PI);
	private double true_diff;
	private double true_speed;
	
	public double getTrue_diff() {
		return true_diff;
	}

	public double getTrue_speed() {
		return true_speed;
	}

	/**
	 * Calculates the atmospheric wind direction out of the relative wind direction and the speed of the boat.
	 * 
	 * @param angle	angle between boat-heading and apparent wind in degree
	 * @param w_s apparent wind-Speed in m/s
	 * @param b_s boat-speed in m/s
	 * Source: http://www.csgnetwork.com/twscorcalc.html
	 * 
	 * TODO untested and not really good working
	 */
	public void trueWind(double angle, double w_s, double b_s) {
		
		// 1. convert angle to rad
		angle = deg2rad(angle);
		// 2. convert w_s to knots and then to units to boat-speed
		w_s = w_s/(0.51+4/900); // in knots
		b_s = b_s/(0.51+4/900);	// in knots
		w_s = w_s/b_s;  // 

		double tan_alpha = (Math.sin(angle) / w_s - Math.cos(angle));
		double alpha = Math.atan(tan_alpha);
		
		true_diff = rad2deg(angle + alpha);
		double tspeed = Math.sin(angle)/Math.sin(alpha);
		
		true_speed = tspeed * b_s;
		
	}

    /**
	 * Converts an angle from degrees to radian.
	 * 
	 * @param deg the angle in degrees
	 * @return the radian angle
	 */
	public double deg2rad(double deg) {
		return(deg * convertDeg2Rad);
	}

	/**
	 * Converts an angle from radian to degrees.
	 * 
	 * @param rad the radian angle 
	 * @return the angle in degrees
	 */
	public double rad2deg(double rad) {
		return(rad * convertRad2Deg);
	}
}
