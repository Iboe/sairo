package de.fhb.sailboat.control.pilot;

import java.util.ArrayList;
import java.util.Observable;

import org.apache.log4j.Logger;

import de.fhb.sailboat.pilot.gui.Window;

/***
 * This PID-Controller controlls the angle of compass , the target angle of compass and the controlling of rudder
 * 
 * algorithm for pid controller :
 * esum = esum + e
 * y = Kp * e + Ki * Ta * esum + Kd * (e – ealt)/Ta
 * ealt = e
 * @author Tobias Koppe
 * @version 2
 */
public class PIDController extends Observable{

	private static final Logger LOG = Logger.getLogger(PIDController.class);
	
	double Kp=3; //Koeffizient P
	double Ki=0; //Koeffizient I
	double Kd=9; //Koeffizient D
	double Ta=0; //Samplingrate
	
	double lastCall=0;
	
	double esum; //Summe Fehler
	double eold; //Fehler alt
	double deltaAngle=0; //Failure: TargetAngle-Angle=deltaAngle
	
	double targetAngle=0; //Zielwinkel
	double realAngle=0; //Realer WInkel
	
	private double lastOutput=0; //Ausgabe alt
	private double output=0; //Ausgabe
	
	private ArrayList<String> valueList; //List with all values of PID Controller
	
	private Window gui;
	
	/***
	 * Constructor of PID controller
	 */
	public PIDController(){
		valueList = new ArrayList<String>();
		this.gui = new Window(this);
		this.gui.setVisible(true);
		this.addObserver(gui);
		LOG.debug("PIDController init: P(" + Kp + ")I(" + Ki + ")D(" + Kd + ")");
	}
	
	/***
	 * Calculates the sampling time of controller and save it to class variable Ta
	 */
	private void controllSamplingTime(){
		double actCall=System.currentTimeMillis();
		if(lastCall!=0){
			Ta=1/(Math.abs((actCall-lastCall)/1000)); //1000ms -> 1 second -> 1/seconds -> Hz
			Ta=((int)(Ta*100))/100;
			lastCall = actCall;
		}
		else{
			lastCall=System.currentTimeMillis();
		}
		LOG.debug("sampling time shorted: " + Ta);
	}
	
	/***
	 * Calculates the P part of PID
	 * P = Kp*e
	 * @author Tobias Koppe
	 * @return
	 */
	private double calculateP(){
		return Kp*(deltaAngle);
	}
	
	/***
	 * Calculates the I part of PID
	 * I=Ki*esum*Ta
	 * esum = esum + deltaAngle
	 * @return
	 */
	private double calculateI(){
		esum = esum + deltaAngle;
		return Ki * esum * Ta;
	}
	
	/***
	 * Calculates the D part of PID
	 * D=Kd * (e – e_old)/Ta
	 * @return
	 */
	private double calculateD(){
		return Kd*(deltaAngle-eold)/Ta;
	}
	
	/***
	 * Resets the variables of controller which saving data
	 */
	private void resetController(){
		this.setEsum(0);
		this.setEold(0);
	}
	
	/***
	 * Controlls the rudder position in depending the compass course
	 * @param pDealtaAngle
	 * @return
	 */
	public double controll(double pDealtaAngle){
		this.deltaAngle = pDealtaAngle;
		if(deltaAngle==0){
			resetController();
		}
		controllSamplingTime();
		lastOutput=output;
		output=(calculateP()+calculateI()+calculateD());
		LOG.debug("PIDController controlled coefficients: P(" + Kp + ")I(" + Ki + ")D(" + Kd + ")");
		LOG.debug(this.toString());
		packValueToList();
		setChanged();
		notifyObservers(valueList);
		eold = deltaAngle;
		return output;
	}
	
	private void packValueToList(){
		this.valueList.clear();
		this.valueList.add(String.valueOf(Kp));
		this.valueList.add(String.valueOf(Ki));
		this.valueList.add(String.valueOf(Kd));
		this.valueList.add(String.valueOf(realAngle));
		this.valueList.add(String.valueOf(targetAngle));
		this.valueList.add(String.valueOf(deltaAngle));
		this.valueList.add(String.valueOf(output));
		this.valueList.add(String.valueOf(Ta));
		this.valueList.add(String.valueOf(Kp));
		this.valueList.add(String.valueOf(Ki));
		this.valueList.add(String.valueOf(Kd));
	}
	
	/***
	 * @author Tobias Koppe
	 * @since 1
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("PIDController ");
		sb.append("controll rudder: ");
		sb.append("inputSignal[" + realAngle + "]");
		sb.append("targetAngle["+targetAngle+"]");
		sb.append("deltaAngle["+deltaAngle+"]");
		sb.append("output["+output+"]");
		sb.append("samplingTime["+Ta+"]");
		return sb.toString();
	}

	public double getKp() {
		return Kp;
	}

	public void setKp(double kp) {
		Kp = kp;
		packValueToList();
		setChanged();
		notifyObservers(valueList);
	}

	public double getKi() {
		return Ki;
	}

	public void setKi(double ki) {
		Ki = ki;
		packValueToList();
		setChanged();
		notifyObservers(valueList);
	}

	public double getKd() {
		return Kd;
	}

	public void setKd(double kd) {
		Kd = kd;
		packValueToList();
		setChanged();
		notifyObservers(valueList);
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

	public double getTargetAngle() {
		return targetAngle;
	}

	public void setTargetAngle(double targetAngle) {
		this.targetAngle = targetAngle;
	}

	public double getRealAngle() {
		return realAngle;
	}

	public void setRealAngle(double realAngle) {
		this.realAngle = realAngle;
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

	public ArrayList<String> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<String> valueList) {
		this.valueList = valueList;
	}
	
	public void setCoefficients(double pKp, double pKi, double pKd){
		setKp(pKp);
		setKi(pKi);
		setKd(pKd);
	}
	
}
