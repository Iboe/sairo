package de.fhb.sailboat.data;


/**
 * Data object representing an actuator, containing one value for the current
 * state of the actuator.
 * 
 * @author hscheel
 *
 */
public class Actuator{

	private final String id;
	private final int value;

	/**
	 * Constructs a new instance with the value handed over.
	 * 
	 * @param value the current value of the actuator
	 */
	public Actuator(int value, String pId) {
		this.value = value;
		this.id=pId;
	}

    /**
	 * Getter for the current value of the actuator.
	 * 
	 * @return the current value of the actuator
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	@Override
	public String toString(){
		return id+":"+value;
	}
}
