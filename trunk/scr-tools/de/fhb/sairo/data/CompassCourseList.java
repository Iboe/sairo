package de.fhb.sairo.data;

import java.util.ArrayList;

import de.fhb.sairo.data.LogData.LogCompassCourse;

public class CompassCourseList extends ArrayList<LogCompassCourse> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.size();i++){
			sb.append(this.get(i).toString());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
}
