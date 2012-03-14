package de.fhb.sailboat.data;

import javax.vecmath.Vector3d;

/**
 * Data object representing a compass, containing the information about the 
 * inclination of the boat in different directions.
 * 
 * @author hscheel
 *
 */
//@Deprecated
//public class Compass {
//
//	private final double yaw;
//	private final double pitch;
//	private final double roll;
//
//	/**
//	 * Constructs a new instance with the values handed over.
//	 * 
//	 * @param yaw the value for yaw
//	 * @param pitch the value for pitch
//	 * @param roll the value for roll
//	 */
//	public Compass(double yaw, double pitch, double roll) {
//		this.yaw = yaw;
//		this.pitch = pitch;
//		this.roll = roll;
//	}
//
//	/**
//	 * Getter for the angle that describes the direction of the boat. The angle is relative to a fixed origin direction.<br>
//	 * Directions:
//	 * North:	   0°
//	 * East:	   90°
//	 * South:	[-]180°
//	 * West:	  -90°
//	 *   
//	 * @return the direction
//	 */
//	public double getYaw() {
//		return yaw;
//	}
//	
//	/**
//	 * Getter for the pitch angle. It describes the angle between bow of the boat and the water surface.
//	 *        |__|                   ^
//	 *   --   |  |            ---    |  pitch angle
//	 *     \--|--|-----------/       |
//	 *      \_______________/        V   
//	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//	 *      side view
//	 * 
//	 * @return the pitch angle
//	 */
//	public double getPitch() {
//		return pitch;
//	}
//	
//	/**
//	 * Getter for the roll angle. It describes heeling of the boat.
//	 *               ^ 
//	 *       |__|     \
//	 *       |  |      |   roll angle
//	 *    \--|--|--/   |
//	 *     \______/   /
//	 * ~~~~~~~~~~~~~ v 
//	 *   front view
//	 *   
//	 * @return the heeling angle
//	 */
//	public double getRoll() {
//		return roll;
//	}
//}

public class Compass {
	private final double azimuth;
	private final double pitch;
	private final double roll;
	private final double temperature;
	private final double magnatic;
	private final Vector3d magnaticField;
	private final double acceleration;
	private final Vector3d accelerationField;
	private final double usefullsamplerate;

	
	public Compass(double yaw, double pitch, double roll) {
		this.azimuth = yaw;
		this.pitch = pitch;
		this.roll = roll;
		
		this.temperature = 0;
		this.magnatic = 0;
		this.magnaticField = new Vector3d(0, 0, 0);
		this.acceleration = 0;
		this.accelerationField = new Vector3d(0, 0, 0);
		this.usefullsamplerate = 0;
	}
	
	public double getYaw() {
	
		if( azimuth > 180)
			return azimuth - 360;
		else
			return azimuth;
	}
/**
 * 
 * @param a for azimuth value
 * @param p for pitch value
 * @param r for roll value
 * @param t for temperature value
 * @param m for magnatic value
 * @param mx for magnatic-x value
 * @param my for magnatic-y value
 * @param mz for magnatic-z value
 * @param ac for acceleration value
 * @param acx for acceleration-x value
 * @param acy for acceleration-y value
 * @param acz for acceleration-z value
 * @param u for usefullsamplerate value
 * @return 
 */
	
	public Compass(double a, double p, double r, double t, double m, Vector3d mvect, double ac, Vector3d acvect, double u) {
		this.azimuth = a;
		this.pitch = p;
		this.roll = r;
		this.temperature = t;
		this.magnatic = m;
		this.magnaticField = mvect;
		this.acceleration = ac;
		this.accelerationField = acvect;
		this.usefullsamplerate = u;
	}

	/**
	 * Getter for the angle that describes the direction of the boat. The angle is relative to a fixed origin direction.<br>
	 * Directions:
	 * North:	   0°
	 * East:	   90°
	 * South:	[-]180°
	 * West:	  -90°
	 *   
	 * @return the direction
	 */
	public double getAzimuth() {
		return azimuth;
	}
	
	/**
	 * Getter for the pitch angle. It describes the angle between bow of the boat and the water surface.
	 *        |__|                   ^
	 *   --   |  |            ---    |  pitch angle
	 *     \--|--|-----------/       |
	 *      \_______________/        V   
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *      side view
	 * 
	 * @return the pitch angle
	 */
	public double getPitch() {
		return pitch;
	}
	
	/**
	 * Getter for the roll angle. It describes heeling of the boat.
	 *               ^ 
	 *       |__|     \
	 *       |  |      |   roll angle
	 *    \--|--|--/   |
	 *     \______/   /
	 * ~~~~~~~~~~~~~ v 
	 *   front view
	 *   
	 * @return the heeling angle
	 */
	public double getRoll() {
		return roll;
	}
	
	/**
	 * Getter for the temperature. If not calibrated, this is the temperature
	 * inside the compass-box
	 * 
	 * @return temperature in °C celcius degrees
	 */
	public double getTemperature() {
		return temperature;
	}

	public double getMagnatic() {
		return magnatic;
	}

	public Vector3d getMagnaticField() {
		return magnaticField;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public Vector3d getAccelerationField() {
		return accelerationField;
	}
	
	/**
	 * Returns quality/goodness of the value
	 * e.g. 6 out of 10 sample per clock-run where usefull:
	 * 	60% = 0.6 
	 * 
	 * 
	 * @return rate in percent
	 */
	public double getUsefullsamplerate() {
		return usefullsamplerate;
	}

	@Override
	public String toString() {
		return "Compass [azimuth=" + azimuth + ", pitch=" + pitch + ", roll="
				+ roll + "]";
	}
}
