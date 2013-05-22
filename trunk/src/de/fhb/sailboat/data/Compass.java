package de.fhb.sailboat.data;

import java.io.Serializable;

import javax.vecmath.Vector3d;

/**
 * Data object representing a compass. It contains information about the 
 * inclination of the boat in different directions, the temperature, the magnetic 
 * values and the acceleration. 
 * 
 * @author Helge Scheel, Michael Kant
 *
 */
public class Compass implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double azimuth;
	private final double pitch;
	private final double roll;
	private final double temperature;
	private final double magnetic;
	private final Vector3d magneticField;
	private final double acceleration;
	private final Vector3d accelerationField;
	private final double usefulSampleRate;
	private final long timeStamp;
	
	/**
	 * Constructs a new partial initialized instance. The other values are set to 0.
	 * 
	 * @param azimuth the direction of the boat
	 * @param pitch the angle between the bow of the boat and the water surface
	 * @param roll the heeling of the boat
	 */
	public Compass(double azimuth, double pitch, double roll, long timeStamp) {
		this.azimuth = azimuth;
		this.pitch = pitch;
		this.roll = roll;
		this.temperature = 0;
		this.magnetic = 0;
		this.magneticField = new Vector3d(0, 0, 0);
		this.acceleration = 0;
		this.accelerationField = new Vector3d(0, 0, 0);
		this.usefulSampleRate = 0;
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Constructs a new fully initialized instance.
	 * 
	 * @param azimuth the direction of the boat
	 * @param pitch the angle between the bow of the boat and the water surface
	 * @param roll the heeling of the boat
	 * @param temperature the temperature inside the compass-box
	 * @param magnetic for magnetic value
	 * @param magneticField for magnetic values in x-, y- and z-direction
	 * @param acceleration for acceleration value
	 * @param accelerationField for acceleration values in x-, y- and z-direction
	 * @param usefulSampleRate for useful sample rate value
	 */
	public Compass(double azimuth, double pitch, double roll, double temperature, double magnetic, 
			Vector3d magneticField, double acceleration, Vector3d accelerationField, double usefulSampleRate , long timeStamp) {
		this.azimuth = azimuth;
		this.pitch = pitch;
		this.roll = roll;
		this.temperature = temperature;
		this.magnetic = magnetic;
		this.magneticField = magneticField;
		this.acceleration = acceleration;
		this.accelerationField = accelerationField;
		this.usefulSampleRate = usefulSampleRate;
		this.timeStamp = timeStamp;
	}

	/**
	 * Getter for the angle that describes the direction of the boat. The angle is relative to a fixed origin direction.<br>
	 * Directions:
	 * North:	   0°
	 * East:	   90°
	 * South:	   360°
	 * West:	   270°
	 *   
	 * @return the direction
	 */
	public double getAzimuth() {
		return azimuth;
	}
	
	/**
	 * Getter for the pitch angle. It describes the angle between the bow of the boat and the water surface.
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
	 * Getter for the roll angle. It describes the heeling of the boat.
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
	 * Transforms the current azimuth value into the range from -180° to 180°.
	 * Directions:
	 * North:	   0°
	 * East:	   90°
	 * South:	[-]180°
	 * West:	  -90°
	 * 
	 * @return the transformed value of azimuth
	 */
	public double getYaw() {
		if (azimuth > 180) {
			return azimuth - 360;
		} else {
			return azimuth;
		}
	}

	/**
	 * Getter for the temperature. If not calibrated, this is the temperature
	 * inside the compass-box.
	 * 
	 * @return temperature in °C (degree Celsius)
	 */
	public double getTemperature() {
		return temperature;
	}

	/**
	 * Getter for the overall magnetic value, measured at the boat.  
	 *
	 * @return the overall magnetic value
	 */
	public double getMagnetic() {
		return magnetic;
	}

	/**
	 * Getter for the magnetic values in x-, y- and z-direction.
	 *
	 * @return the magnetic values as vector with three elements
	 */
	public Vector3d getMagneticField() {
		return magneticField;
	}

    /**
	 * Getter for the overall acceleration of the boat.
	 * 
	 * @return the overall acceleration
	 */
	public double getAcceleration() {
		return acceleration;
	}

    /**
	 * Getter for the acceleration values in x-, y- and z-direction.
	 * 
	 * @return the acceleration values as vector with three elements
	 */
	public Vector3d getAccelerationField() {
		return accelerationField;
	}
	
	/**
	 * Returns quality of the values. E.g. 6 out of 10 samples per clock-run are useful:
	 * 60% = 0.6 
	 * 
	 * @return quality rate in percent
	 */
	public double getUsefulSampleRate() {
		return usefulSampleRate;
	}

	@Override
	public String toString() {
		return "Compass [azimuth: " + azimuth + ", pitch: " + pitch + ", roll: "
			+ roll + "]";
	}
}
