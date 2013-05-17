package de.fhb.sairo.data.Task;

import de.fhb.sairo.logAnalyze.logTextblocks;


/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class CompassCourseTask extends Task {

	private double compassCourseAngle;
	
	public CompassCourseTask(String pTaskDescription, double pAngle){
		super(pTaskDescription);
		this.compassCourseAngle=pAngle;
	}

	@Override
	public String toString(){
		return "CompassCourseTask: " + super.getTaskDescription() + " - Angle: " + getCompassCourseAngle();
	}
	
	/**
	 * @return the compassCourseAngle
	 */
	public double getCompassCourseAngle() {
		return compassCourseAngle;
	}

	/**
	 * @param compassCourseAngle the compassCourseAngle to set
	 */
	public void setCompassCourseAngle(double compassCourseAngle) {
		this.compassCourseAngle = compassCourseAngle;
	}

	@Override
	public void setTaskArguments(String taskArguments) {
		int startAngle = taskArguments.indexOf(logTextblocks.compassCourseTaskParamMark) + logTextblocks.compassCourseTaskParamMark.length();
		int endAngle = taskArguments.indexOf("]", startAngle);
		String taskArgumentAngle = taskArguments.substring(startAngle, endAngle);
//		System.out.println(this.getClass().getName() + " setTaskArguments("+taskArguments+") = " + taskArgumentAngle);
		this.setCompassCourseAngle(Double.valueOf(taskArgumentAngle));
	}
	
	
}
