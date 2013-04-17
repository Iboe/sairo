package de.fhb.sailboat.utils.logevaluation;

import java.io.File;

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
		
		logEvaluator evaluator = new logEvaluator("./log/sailboat.log_15_04_2013_04_Labor_CompassCourse180_NewPIDTest","");
		evaluator.setEvaluateCompassCourse(true);
		evaluator.setEvaluatePilot(true);
		evaluator.setEvaluateRudderPositions(true);
		evaluator.setEvaluateSimplePidController(true);
		evaluator.setEvaluateGPSData(true);
		evaluator.setEvaluateWindData(true);
		evaluator.evaluate();
		evaluator.writeAllEvaluationsToCsv();
		
//		logEvaluator log2 = new logEvaluator("./log/sailboat.log_15_04_2013_02_Outside", "");
//		log2.setEvaluateCompassCourse(true);
//		log2.setEvaluatePilot(true);
//		log2.setEvaluateRudderPositions(true);
//		log2.setEvaluateSimplePidController(true);
//		log2.setEvaluateGPSData(true);
//		log2.setEvaluateWindData(true);
//		log2.evaluate();
//		log2.writeAllEvaluationsToCsv();
//		
//		File dir =new File("./log/");
//		File[] fileArray =dir.listFiles(); 
//		for(int i=0;i<fileArray.length;i++){
//			if(fileArray[i].isFile()){
//			System.out.println("Found: " + fileArray[i].getAbsolutePath());
//			logEvaluator log2 = new logEvaluator(fileArray[i].getAbsolutePath(), "");
//			log2.setEvaluateCompassCourse(true);
//			log2.setEvaluatePilot(true);
//			log2.setEvaluateRudderPositions(true);
//			log2.setEvaluateSimplePidController(true);
//			log2.setEvaluateGPSData(true);
//			log2.setEvaluateWindData(true);
//			log2.evaluate();
//			log2.writeAllEvaluationsToCsv();
//			}
//		}
	}
}
