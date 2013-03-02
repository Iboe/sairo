package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.data.Actuator;

/**
 * Sub-model which holds the states of the boat's actuators: sail, rudder and propellor.
 *  
 * @author Helge Scheel, Michael Kant
 *
 */
public interface ActuatorModel {

	/**
	 * Returns the {@link Actuator} object which contains the current sail value.
	 * @return The {@link Actuator} object which contains the current sail value.
	 */
	Actuator getSail();
	
	/**
	 * Returns the {@link Actuator} object which contains the current rudder value.
	 * @return The {@link Actuator} object which contains the current rudder value.
	 */
	Actuator getRudder();
	
	/**
	 * Returns the {@link Actuator} object which contains the current propeller value.
	 * @return The {@link Actuator} object which contains the current propeller value.
	 */
	Actuator getPropeller();
	
	/**
	 * Sets the {@link Actuator} object which contains the current sail value.
	 * @param sail the {@link Actuator} object which contains the current sail value.
	 */
	void setSail(Actuator sail);
	
	/**
	 * Sets the {@link Actuator} object which contains the current rudder value.
	 * @param rudder the {@link Actuator} object which contains the current rudder value.
	 */
	void setRudder(Actuator rudder);
	
	/**
	 * Sets the {@link Actuator} object which contains the current propeller value.
	 * @param propeller the {@link Actuator} object which contains the current propeller value.
	 */
	void setPropeller(Actuator propeller);
}
