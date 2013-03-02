package de.fhb.sailboat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;

/**
 * A plain dummy implementation of the {@link LocomotionSystem} interface, 
 * which prints the values to be set on the console.
 * @author Michael Kant
 *
 */
public class DummyLoco implements LocomotionSystem {

	private static final Logger LOG = LoggerFactory.getLogger(DummyLoco.class);
	
	@Override
	public void setSail(int value) {

		LOG.debug("LOCO: attempt to set sail to: "+value);
	}

	@Override
	public void setRudder(int value) { 
		
		LOG.debug("LOCO: attempt to set rudder to: "+value);
	}

	@Override
	public void setPropellor(int value) {

		LOG.debug("LOCO: attempt to set propellor to: "+value);
	}

	@Override
	public void reset() {
	}

	@Override
	public void resetRudder() {

	}

	@Override
	public void resetSail() {

	}

	@Override
	public void resetPropellor() {

	}

	@Override
	public int getBatteryState() {
		return 0;
	}

	@Override
	public void setDebug(boolean debug) {
		
	}

	@Override
	public long getWait_Sleep() {
		return 0;
	}

	@Override
	public void setWait_Sleep(int wait_sleep) {
		
	}

	@Override
	public int getStatus() {
		return 0;
	}

}
