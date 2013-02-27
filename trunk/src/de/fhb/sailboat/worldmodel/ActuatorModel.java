package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.data.Actuator;

/**
 * Sub-model which holds the states of the boat's actuators: sail, rudder and propellor.
 *  
 * @author Helge Scheel, Michael Kant
 *
 */
public interface ActuatorModel {

	Actuator getSail();
	Actuator getRudder();
	Actuator getPropeller();
	void setSail(Actuator sail);
	void setRudder(Actuator rudder);
	void setPropeller(Actuator propeller);
}
