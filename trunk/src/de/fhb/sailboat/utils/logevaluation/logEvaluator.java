package de.fhb.sailboat.utils.logevaluation;

/***
 * This class represents the evaluator for logfiles,
 * he hold the configuration and modules for evaluation
 * @author Tobias Koppe
 * @version 1
 */
public class logEvaluator {

	private boolean evaluateAksenLog;
	private boolean evaluateCompassCourse;
	private boolean evaluatePilot;
	private boolean evaluateRudderPositions;
	private boolean evaluateSimplePidController;
	private boolean evaluateWindData;
	private boolean evaluateGPSData;
	
	private String logFileName;
	private String csvFileName;
	
	private evaluateCompassCourse compass;
	private evaluatePilot pilot;
	private evaluateRudderPosistions rudderPos;
	private evaluateSimplePidController pid;
	private evaluateWindData wind;
	private evaluateGPSData gps;
	
	public logEvaluator(String pLogFileName, String pCsvFileName){
		this.logFileName = pLogFileName;
		if(this.csvFileName==null){
			this.csvFileName = pLogFileName+".csv";
		}
		else{
		this.csvFileName = pCsvFileName;
		}
		initialize();
	}

	private void initialize(){
		this.setEvaluateAksenLog(false);
		this.setEvaluateCompassCourse(false);
		this.setEvaluatePilot(false);
		this.setEvaluateRudderPositions(false);
		this.setEvaluateSimplePidController(false);
		this.setEvaluateWindData(false);
		this.setEvaluateGPSData(false);
	}
	
	/***
	 * This method write all evaluated data to configured csv file
	 * @author Tobias Koppe
	 * @version 1
	 */
	public void writeAllEvaluationsToCsv(){
		CSVWriter.CSVWriterWrite(getCsvFileName(), compass.getCompassCourseList(), pid.getSimplePidControllerStateList(), pilot.getCommandList(), rudderPos.getRudderPositions(),gps.getGpsDataList(),wind.getWindDataList());
	}
	
	/***
	 * This method start the evaluation process for configured modules
	 * @author Tobias Koppe
	 * @version 1
	 */
	public void evaluate(){
		if(this.isEvaluateCompassCourse()){
			compass = new evaluateCompassCourse(getLogFileName());
		}
		if(this.isEvaluatePilot()){
			pilot = new evaluatePilot(getLogFileName());
		}
		if(this.isEvaluateRudderPositions()){
			rudderPos = new evaluateRudderPosistions(getLogFileName());
		}
		if(this.isEvaluateSimplePidController()){
			pid = new evaluateSimplePidController(getLogFileName());
		}
		if(this.isEvaluateWindData()){
			wind = new evaluateWindData(getLogFileName());
		}
		if(this.isEvaluateGPSData()){
			gps = new evaluateGPSData(getLogFileName());
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

	public boolean isEvaluateWindData() {
		return evaluateWindData;
	}

	public void setEvaluateWindData(boolean evaluateWindData) {
		this.evaluateWindData = evaluateWindData;
	}

	public boolean isEvaluateGPSData() {
		return evaluateGPSData;
	}

	public void setEvaluateGPSData(boolean evaluateGPSData) {
		this.evaluateGPSData = evaluateGPSData;
	}
	
}
