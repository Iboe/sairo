package de.fhb.sailboat.worldmodel;

import de.fhb.sailboat.data.Actuator;

public interface ActuatorModel {

	Actuator getSail();
	Actuator getRudder();
	Actuator getPropeller();
	void setSail(Actuator sail);
	void setRudder(Actuator rudder);
	void setPropeller(Actuator propeller);
}
