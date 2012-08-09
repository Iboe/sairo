package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task for setting values for the actuators directly. It is finished after the values have been set.
 * 
 * @author hscheel
 *
 */
public class PrimitiveCommandTask implements Task {

	private static final long serialVersionUID = -5072098346843293620L;
	private final Integer sail;
	private final Integer rudder;
	private final Integer propellor;
	private boolean executed;
	
	/**
	 * Creates a new instance based on the values for actuators to be set. Each value can be set to 
	 * <code>null</code>, so the current value of the related actuator is not changed.
	 * 
	 * @param sail the length of the rope controlling the sail
	 * @param rudder the position of the rudder
	 * @param propellor the speed and direction of the propellor
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

	public Integer getSail() {
		return sail;
	}

	public Integer getRudder() {
		return rudder;
	}

	public Integer getPropellor() {
		return propellor;
	}

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
