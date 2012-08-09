package de.fhb.sailboat.control.pilot;

import de.fhb.sailboat.worldmodel.ActuatorModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Simple implementation of a PID controller for the rudder.
 * 
 * @author hscheel
 *
 */
public class SimplePIDController {

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
	
	/**
	 * Creates a new initialized instance which uses the constants from {@link Pilot}.
	 */
	public SimplePIDController() {
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
	public double control(double inputSignal) {
		double currentValue = actuatorModel.getRudder().getValue();
		double difference = inputSignal - currentValue;
		double output = lastOutput + q1 * difference + q2 * lastInput + q3 * nextToLastInput;
		
		nextToLastInput = lastInput;
		lastInput = difference;
		lastOutput = output;
		
		return output + currentValue;
	}
}
