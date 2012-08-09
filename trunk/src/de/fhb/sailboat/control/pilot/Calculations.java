package de.fhb.sailboat.control.pilot;

public class Calculations {

	
	/**
	 * Calculate the atmospheric Wind Direction out of the relative Wind Direction and the speed of the boat
	 * 
	 * @param k course of the boat
	 * @param r relative wind direction in °
	 * @param v speed of relative wind in m/s
	 * @param vb speed of boat according gps in m/s
	 * @return atmospheric Wind Direction in °
	 * 
	 * Source: http://www.rainerstumpe.de/HTML/body_wind02.html
	 * 
	 * TODO untested and not really good working
	 */

	public double calculateAtmosphericWind(double k, double r, double b, double c) {
		double lambda, gamma, alpha, beta, t1, t2, cosa, a2, a, sinb;
		gamma = 0d;
		
		alpha = (360 - r) + k;
		// a2 = (b + c)2 - 4·b·c·cos2(α/2)
		t1 = (b + c) * (b + c);
		cosa = Math.cos(alpha/2); // [-Pi/2, Pi/2] TODO in °
		t2 = 4 * b * c * (cosa * cosa);
		a2 = t1 - t2;
		a = Math.sqrt(a2);
		
		// sin β = b/a · sin α
		sinb = b/a * Math.sin(alpha);
		beta = Math.asin(sinb);  // 
		lambda = 180 - alpha - beta;
		
		gamma = r - lambda;
		
		return gamma;
	}
}
