package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class PrimitiveCommandTask implements Task {

	private final Integer sail;
	private final Integer rudder;
	private final Integer propellor;
	private boolean executed;
	
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
	 * Describes if task was already executed.
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
