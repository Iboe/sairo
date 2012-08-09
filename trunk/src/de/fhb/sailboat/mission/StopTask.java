package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Task that causes the boat to stop. Will never finish.
 * 
 * @author hscheel
 *
 */
public class StopTask implements Task{

	private static final long serialVersionUID = -2985405903689552908L;
	private final boolean turnPropellorOff;
	
	/**
	 * Creates a new instance and turns the propellor off.
	 */
	public StopTask() {
		this(false);
	}
	
	/**
	 * Creates a new instance and turns the propellor off if specified.
	 * 
	 * @param turnPropellorOff specifies if the propellor shall be turned off
	 */
	public StopTask(boolean turnPropellorOff) {
		this.turnPropellorOff = turnPropellorOff;
	}
	
	/**
	 * The task does not finish, so <code>false</code> is returned. 
	 */
	@Override
	public boolean isFinished(GPS position) {
		return false;
	}

	public boolean isTurnPropellorOff() {
		return turnPropellorOff;
	}

	@Override
	public String toString() {
		return "StopTask [turnPropellorOff=" + turnPropellorOff + "]";
	}
}
