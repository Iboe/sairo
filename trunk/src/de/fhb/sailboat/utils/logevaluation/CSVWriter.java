package de.fhb.sailboat.utils.logevaluation;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import de.fhb.sailboat.data.CompassCourse;
import de.fhb.sailboat.data.RudderPosition;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class CSVWriter {

	private static ArrayList<RudderPosition> rudderPositionList;
	private static ArrayList<CompassCourse> compassCoursesList;
	
	public static void CSVWriterWriteRudderPositions(String pFileName , ArrayList<RudderPosition> pList){
		rudderPositionList = pList;
		try {
			FileWriter fWriter = new FileWriter(pFileName);
			for(int i=0;i<rudderPositionList.size();i++){
				StringBuilder sb=new StringBuilder();
				sb.append(rudderPositionList.get(i).getTimeStamp());
				sb.append(";");
				sb.append(rudderPositionList.get(i).getAngle());
				sb.append("\n");
				fWriter.write(sb.toString());
			}
			fWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void CSVWriterWriteCompassCourses(String pFileName , ArrayList<CompassCourse> pList){
		compassCoursesList = pList;
		try {
			FileWriter fWriter = new FileWriter(pFileName);
			for(int i=0;i<compassCoursesList.size();i++){
				StringBuilder sb=new StringBuilder();
				sb.append(compassCoursesList.get(i).getTimeStamp());
				sb.append(";");
				sb.append(compassCoursesList.get(i).getCompassCourseAzimuth());
				sb.append("\n");
				fWriter.write(sb.toString());
			}
			fWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
