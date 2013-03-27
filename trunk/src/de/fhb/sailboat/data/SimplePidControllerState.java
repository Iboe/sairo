package de.fhb.sailboat.data;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class SimplePidControllerState {

	private double inputSignal;
	private double currentValue;
	private double difference;
	private double output;
	
	public SimplePidControllerState(double inputSignal, double currentValue,
			double difference, double output) {
		super();
		this.inputSignal = inputSignal;
		this.currentValue = currentValue;
		this.difference = difference;
		this.output = output;
	}
	public double getInputSignal() {
		return inputSignal;
	}
	public void setInputSignal(double inputSignal) {
		this.inputSignal = inputSignal;
	}
	public double getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}
	public double getDifference() {
		return difference;
	}
	public void setDifference(double difference) {
		this.difference = difference;
	}
	public double getOutput() {
		return output;
	}
	public void setOutput(double output) {
		this.output = output;
	}
}
