package de.fhb.sailboat.data;

/**
 * Data object representing the wind conditions on a certain moment, 
 * containing information about speed and direction of the wind.
 * 
 * @author hscheel
 *
 */
public class Wind {

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

	public int getDirection() {
		return direction;
	}
	
	public double getSpeed() {
		return speed;
	}
}
