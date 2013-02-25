package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task for setting values for the actuators directly. It is finished after the values have been set.
 * 
 * @author hscheel
 *
 */
public class PrimitiveCommandTask implements Task {

	private final Integer sail;
	private final Integer rudder;
	private final Integer propellor;
	private boolean executed;
	
	/**
	 * Creates a new instance based on the values for actuators to be set. Each value can be set to 
	 * <code>null</code>, so the current value of the related actuator is not changed.
	 * 
	 * @param sail the desired position for the sail
	 * @param rudder the desired position for the rudder
	 * @param propellor the desired speed and direction for the propellor
	 */
	public PrimitiveCommandTask(Integer sail, Integer rudder, Integer propellor) {
		this.sail = sail;
		this.rudder = rudder;
		this.propellor = propellor;
	}
	
	@Override
	public boolean isFinished(GPS position) {
		return isExecuted();
	}

	/**
	 * Getter for the desired position for the sail.
	 * 
	 * @return the desired position for the sail
	 */
	public Integer getSail() {
		return sail;
	}

	/**
	 * Getter for the desired position for the rudder.
	 * 
	 * @return the desired position for the rudder
	 */
	public Integer getRudder() {
		return rudder;
	}

    /**
	 * Getter for the value indicating the desired speed and direction for the propellor.
	 * 
	 * @return the desired speed and direction for the propellor
	 */
	public Integer getPropellor() {
		return propellor;
	}

    /**
	 * Setter for the flag, which indicates if this {@link Task} was executed.
	 * 
	 * @param <code>true</code> if this {@link Task} was executed, <code>false</code> otherwise
	 */
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	/**
	 * Describes if the values were already set.
	 * 
	 * @return execution state of this task
	 */
	public boolean isExecuted() {
		return executed;
	}

	@Override
	public String toString() {
		return "PrimitiveCommandTask [executed=" + executed + ", propellor="
				+ propellor + ", rudder=" + rudder + ", sail=" + sail + "]";
	}
}
