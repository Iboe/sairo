package de.fhb.sailboat.control.pilot;

import org.apache.log4j.Logger;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.worldmodel.ActuatorModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Simple implementation of a PID controller for the rudder.
 * 
 * @author hscheel
 * @version 2
 */
public class SimplePIDController {

	private static final Logger LOG = Logger.getLogger(SimplePIDController.class);
	
	public static final double P = Double.parseDouble(System.getProperty(Pilot.P_PROPERTY));
	public static final double I = Double.parseDouble(System.getProperty(Pilot.I_PROPERTY));
	public static final double D = Double.parseDouble(System.getProperty(Pilot.D_PROPERTY));
	public static final double SAMPLING_TIME = Integer.parseInt(System.getProperty(
			Pilot.WAIT_TIME_PROPERTY)) / 1000d;
	
	private final ActuatorModel actuatorModel = WorldModelImpl.getInstance().getActuatorModel();
	private final double q1;
	private final double q2;
	private final double q3;
	
	private double lastInput;
	private double nextToLastInput;
	private double lastOutput;
	
	private double esum;
	
	//Fields for toString
	double inputSignal;
	double currentValue;
	double difference;
	double output;
	
	/**
	 * Creates a new initialized instance which uses the constants from {@link Pilot}.
	 */
	public SimplePIDController() {
		LOG.debug("SimplePIDController init: P(" + P + ")I(" + I + ")D(" + D + ")");
		q1 = P * (1 + D / SAMPLING_TIME);
		q2 = -P * (1 - SAMPLING_TIME / I + 2 * D / SAMPLING_TIME);
		q3 = P * (D / SAMPLING_TIME);
	}
	
	/**
	 * Calculates an output signal for the rudder, based on the current position and the goal.
	 * 
	 * @param inputSignal the position of the rudder, that it should be set to finally
	 * @return the position of the the rudder to set (calculated value + current position)
	 */
	public double control(double pInputSignal) {
		//PID-Controller
		//nextToLastInput = (Differenz - Differenz_alt)/Abtastrate
		inputSignal = pInputSignal;
		currentValue = actuatorModel.getRudder().getValue();
		difference = inputSignal - currentValue; // Differenz IstWert-SollWert
		output = lastOutput + q1 * difference + q2 * lastInput + q3 * nextToLastInput;
		
		//Berechnung P Teil
		double pPart = P*difference;
		//Berechnung I Teil
		esum = esum+difference;
		double iPart = I*esum*SAMPLING_TIME;
		//Berechnung D Teil
		//Aenderung nextToLast muesste (Differenz-Differenz_alt sein)
		//nextToLastInput = lastInput; -> falsch ?
		nextToLastInput=(difference-lastInput)/SAMPLING_TIME;
		double dPart = D*nextToLastInput;
		//Wert ausgeben
		output=pPart+iPart+dPart;
		lastInput = difference;
		lastOutput = output;
		LOG.debug(this.toString());
		return output + currentValue;
	}
	
	/***
	 * @author Tobias Koppe
	 * @since 2
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("SimplePIDController ");
		sb.append("controll rudder: ");
		sb.append("inputSignal[" + inputSignal + "]");
		sb.append("currentValue["+currentValue+"]");
		sb.append("difference["+difference+"]");
		sb.append("output["+output+"]");
		return sb.toString();
	}
}
