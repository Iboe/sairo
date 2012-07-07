package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.data.Actuator;

public class ActuatorModelImpl implements ActuatorModel {

	private Actuator sail;
	private Actuator rudder;
	private Actuator propeller;
	
	public ActuatorModelImpl() {
		sail = new Actuator(0);
		rudder = new Actuator(0);
		propeller = new Actuator(0);
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
