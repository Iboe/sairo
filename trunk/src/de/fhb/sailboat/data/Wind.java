package de.fhb.sailboat.data;

import java.io.Serializable;

/**
 * Data object representing the wind conditions on a certain moment, 
 * containing information about speed and direction of the wind.
 * 
 * @author Helge Scheel, Michael Kant
 *
 */
public class Wind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int direction;
	private final double speed;
	
	/**
	 * Constructs a new instance with the values handed over.
	 * 
	 * @param direction the value for direction
	 * @param speed the value for speed
	 */
	public Wind(int direction, double speed) {
		this.direction = direction;
		this.speed = speed;
	}

	/**
	 * Returns the direction of the wind.
	 * 
	 * @return The direction of the wind.
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Returns the speed of the wind.
	 * 
	 * @return The speed of the wind.
	 */
	public double getSpeed() {
		return speed;
	}
	
	@Override
	public String toString() {
		return "Wind [direction: " + direction + ", speed: " + speed + "]";
	}
}
