package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

public class PrimitiveCommandTask implements Task {

	private final Integer sail;
	private final Integer rudder;
	private final Integer propellor;
	
	public PrimitiveCommandTask(Integer sail, Integer rudder, Integer propellor) {
		this.sail = sail;
		this.rudder = rudder;
		this.propellor = propellor;
	}
	
	@Override
	public boolean isFinished(GPS position) {
		// TODO Auto-generated method stub
		return false;
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
}
