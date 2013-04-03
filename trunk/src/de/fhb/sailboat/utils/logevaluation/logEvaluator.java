package de.fhb.sailboat.utils.logevaluation;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class logEvaluator {

	private boolean evaluateAksenLog;
	private boolean evaluateCompassCourse;
	private boolean evaluatePilot;
	private boolean evaluateRudderPositions;
	private boolean evaluateSimplePidController;
	
	private String logFileName;
	private String csvFileName;
	
	public logEvaluator(String pLogFileName, String pCsvFileName){
		this.logFileName = pLogFileName;
		this.csvFileName = pCsvFileName;
		initialize();
	}

	private void initialize(){
		this.setEvaluateAksenLog(false);
		this.setEvaluateCompassCourse(false);
		this.setEvaluatePilot(false);
		this.setEvaluateRudderPositions(false);
		this.setEvaluateSimplePidController(false);
	}
	
	public void evaluate(){
		if(this.isEvaluateCompassCourse()){
			evaluateCompassCourse compass = new evaluateCompassCourse(getLogFileName(), getCsvFileName());
		}
		if(this.isEvaluatePilot()){
			evaluatePilot pilot = new evaluatePilot(getLogFileName(), getCsvFileName());
		}
		if(this.isEvaluateRudderPositions()){
			evaluateRudderPosistions rudderPos = new evaluateRudderPosistions(getLogFileName(), getCsvFileName());
		}
		if(this.isEvaluateSimplePidController()){
			evaluateSimplePidController pid = new evaluateSimplePidController(getLogFileName(), getCsvFileName());
		}
	}
	
	public boolean isEvaluateAksenLog() {
		return evaluateAksenLog;
	}

	public void setEvaluateAksenLog(boolean evaluateAksenLog) {
		this.evaluateAksenLog = evaluateAksenLog;
	}

	public boolean isEvaluateCompassCourse() {
		return evaluateCompassCourse;
	}

	public void setEvaluateCompassCourse(boolean evaluateCompassCourse) {
		this.evaluateCompassCourse = evaluateCompassCourse;
	}

	public boolean isEvaluatePilot() {
		return evaluatePilot;
	}

	public void setEvaluatePilot(boolean evaluatePilot) {
		this.evaluatePilot = evaluatePilot;
	}

	public boolean isEvaluateRudderPositions() {
		return evaluateRudderPositions;
	}

	public void setEvaluateRudderPositions(boolean evaluateRudderPositions) {
		this.evaluateRudderPositions = evaluateRudderPositions;
	}

	public boolean isEvaluateSimplePidController() {
		return evaluateSimplePidController;
	}

	public void setEvaluateSimplePidController(boolean evaluateSimplePidController) {
		this.evaluateSimplePidController = evaluateSimplePidController;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getCsvFileName() {
		return csvFileName;
	}

	public void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}
	
}
