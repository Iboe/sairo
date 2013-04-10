package de.fhb.sailboat.utils.logevaluation;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class testLogEvaluationStart {

	public static void main(String args[]){
		//evaluateAksenLog test = new evaluateAksenLog("./log/sailboat.log_25_03_2013");
//		System.out.println("Start evaluate rudder posistions");
//		evaluateRudderPosistions testRudder = new evaluateRudderPosistions("./log/sailboat.log_27_03_2013_Labor_01");
//		System.out.println("Start evaluate compass courses");
//		evaluateCompassCourse testCompassCourse = new evaluateCompassCourse("./log/sailboat.log_27_03_2013_Labor_01");
//		System.out.println("Start evaluate pilot.driveangle rudder commands");
//		evaluatePilot testPilot = new evaluatePilot("./log/sailboat.log_27_03_2013_Labor_02");
		logEvaluator evaluator = new logEvaluator("./log/sailboat.log_27_03_2013_Labor_02","");
		evaluator.setEvaluateCompassCourse(true);
		evaluator.setEvaluatePilot(true);
		evaluator.setEvaluateRudderPositions(true);
		evaluator.setEvaluateSimplePidController(true);
		evaluator.evaluate();
		evaluator.writeAllEvaluationsToCsv();
		logEvaluator evaluator1 = new logEvaluator("./log/sailboat.log_27_03_2013_Labor_01","");
		evaluator1.setEvaluateCompassCourse(true);
		evaluator1.setEvaluatePilot(true);
		evaluator1.setEvaluateRudderPositions(true);
		evaluator1.setEvaluateSimplePidController(true);
		evaluator1.evaluate();
		evaluator1.writeAllEvaluationsToCsv();
	}
}
