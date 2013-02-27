package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.data.Actuator;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
/**
 * Concrete implementation of the {@link ActuatorModel}.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link ActuatorModel}
 */
public class ActuatorModelImpl implements ActuatorModel {

	private Actuator sail;
	private Actuator rudder;
	private Actuator propeller;
	
	public ActuatorModelImpl() {
		sail = new Actuator(LocomotionSystem.SAIL_NORMAL);
		rudder = new Actuator(LocomotionSystem.RUDDER_NORMAL);
		propeller = new Actuator(LocomotionSystem.PROPELLOR_NORMAL);
	}
	
	@Override
	public Actuator getSail() {
		return sail;
	}
	
	@Override
	public Actuator getRudder() {
		return rudder;
	}
	
	@Override
	public Actuator getPropeller() {
		return propeller;
	}
	
	@Override
	public synchronized void setSail(Actuator sail) {
		this.sail = sail;
	}
	
	@Override
	public synchronized void setRudder(Actuator rudder) {
		this.rudder = rudder;
	}
	
	@Override
	public synchronized void setPropeller(Actuator propeller) {
		this.propeller = propeller;
	}
}
