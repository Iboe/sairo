package de.fhb.sailboat.control.pilot;

import org.apache.log4j.Logger;

/***
 * This PID-Controller controlls the angle of compass , the target angle of compass and the controlling of rudder
 * @author Tobias Koppe
 *
 */
public class PIDController {

	private static final Logger LOG = Logger.getLogger(PIDController.class);
	
	double Kp=0;
	double Ki=0;
	double Kd=0;
	double Ta=0; //Samplingrate
	
	double esum;
	double eold;
	double deltaAngle=0; //Failure: TargetAngle-Angle=deltaAngle
	
	double targetAngle=0;
	double realAngle=0;
	
	private double lastOutput=0;
	private double output=0;
	
	public PIDController(){
		LOG.debug("PIDController init: P(" + Kp + ")I(" + Ki + ")D(" + Kd + ")");
	}
	
	private void controllCoefficientD(){
		//Differenz (lastOutput-output)/Samplingtime dient der Feststellung ob Boot um Kurs schwingt
		//oder stabil ist, Koeffizient D wird solange erhoeht, bis System
		//nicht zuckt
		if((lastOutput-output)/Ta>1){
			Kd=Kd+0.01;
		}
		else if((lastOutput-output)/Ta<1){
			Kp=Kp-0.01;
		}
	}
	
	private void controllCoefficientI(){
		if((lastOutput-output)/Ta!=0){
			Ki=Ki+0.01;
		}
	}
	
	private void controllCoefficientP(){
		//Differenz (lastOutput-output)/Samplingtime dient der Feststellung ob Boot um Kurs schwingt
		//oder stabil ist, Koeffizient P wird solange erhoeht, bis System
		//anfaengt zu schwingen
		if((lastOutput-output)/Ta<1){
			Kp=Kp+0.01;
		}
		else if((lastOutput-output)/Ta>1){
			Kp=Kp-0.01;
		}
	}
	
	private void controllSamplingTime(){
		if(Ta!=0){
			Ta=(System.currentTimeMillis()-Ta)/1000; //1000ms -> 1 second
		}
		else{
			Ta=System.currentTimeMillis();
		}
	}
	
	private void calculateDeltaAngle(){
		eold = deltaAngle;
		deltaAngle = targetAngle-realAngle;
	}
	
	private double calculateP(){
		return Kp*(deltaAngle);
	}
	
	private double calculateI(){
		esum = esum + deltaAngle;
		return Ki * esum * Ta;
	}
	
	private double calculateD(){
		return Kd*(deltaAngle-eold)/Ta;
	}
	
	public double controll(double pRealAngle, double pTargetAngle){
		this.realAngle = pRealAngle;
		this.targetAngle = pTargetAngle;
		controllSamplingTime();
		calculateDeltaAngle();
		lastOutput=output;
		output=calculateP()+calculateI()+calculateD();
		controllCoefficientP();
		controllCoefficientD();
		controllCoefficientI();
		LOG.debug("PIDController controlled coefficients: P(" + Kp + ")I(" + Ki + ")D(" + Kd + ")");
		LOG.debug(this.toString());
		return output;
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
		return sb.toString();
	}
}
