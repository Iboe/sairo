package de.fhb.sailboat.utils.logevaluation;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class testLogEvaluationStart {

	public static void main(String args[]){
		//evaluateAksenLog test = new evaluateAksenLog("./log/sailboat.log_25_03_2013");
		//evaluateRudderPosistions test = new evaluateRudderPosistions("./log/sailboat.log_25_03_2013");
		evaluateCompassCourse test = new evaluateCompassCourse("./log/sailboat.log_25_03_2013");
	}
}
