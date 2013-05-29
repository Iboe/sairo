package de.fhb.sairo.data.Data;

public class PidControllerState {

	private double Kp; //Koeffizient P
	private double Ki; //Koeffizient I
	private double Kd; //Koeffizient D
	private double Ta; //Samplingrate
	
	private double esum; //Summe Fehler
	private double eold; //Fehler alt
	private double deltaAngle; //Failure: TargetAngle-Angle=deltaAngle
	
	private double lastOutput=0; //Ausgabe alt
	private double output=0; //Ausgabe
	
	public PidControllerState(double kp, double ki, double kd, double ta,
			double esum, double eold, double deltaAngle, double lastOutput,
			double output) {
		super();
		Kp = kp;
		Ki = ki;
		Kd = kd;
		Ta = ta;
		this.esum = esum;
		this.eold = eold;
		this.deltaAngle = deltaAngle;
		this.lastOutput = lastOutput;
		this.output = output;
	}

	public double getKp() {
		return Kp;
	}

	public void setKp(double kp) {
		Kp = kp;
	}

	public double getKi() {
		return Ki;
	}

	public void setKi(double ki) {
		Ki = ki;
	}

	public double getKd() {
		return Kd;
	}

	public void setKd(double kd) {
		Kd = kd;
	}

	public double getTa() {
		return Ta;
	}

	public void setTa(double ta) {
		Ta = ta;
	}

	public double getEsum() {
		return esum;
	}

	public void setEsum(double esum) {
		this.esum = esum;
	}

	public double getEold() {
		return eold;
	}

	public void setEold(double eold) {
		this.eold = eold;
	}

	public double getDeltaAngle() {
		return deltaAngle;
	}

	public void setDeltaAngle(double deltaAngle) {
		this.deltaAngle = deltaAngle;
	}

	public double getLastOutput() {
		return lastOutput;
	}

	public void setLastOutput(double lastOutput) {
		this.lastOutput = lastOutput;
	}

	public double getOutput() {
		return output;
	}

	public void setOutput(double output) {
		this.output = output;
	}
	
}
