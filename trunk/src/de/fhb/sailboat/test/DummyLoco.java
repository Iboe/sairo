package de.fhb.sailboat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;

public class DummyLoco implements LocomotionSystem {

	private static final Logger LOG = LoggerFactory.getLogger(DummyLoco.class);
	
	@Override
	public void setSail(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRudder(int value) { 
		
		LOG.trace("LOCO: attempt to set: "+value);

	}

	@Override
	public void setPropellor(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetRudder() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetSail() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPropellor() {
		// TODO Auto-generated method stub

	}

}
