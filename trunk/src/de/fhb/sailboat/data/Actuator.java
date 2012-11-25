package de.fhb.sailboat.data;

import java.io.Serializable;

/**
 * Data object representing an actuator, containing one value for the current
 * state of the actuator.
 * 
 * @author hscheel
 *
 */
public class Actuator{

	private final int value;

	/**
	 * Constructs a new instance with the value handed over.
	 * 
	 * @param value the current value of the actuator
	 */
	public Actuator(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
