package de.fhb.sailboat.utils.logevaluation;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import de.fhb.sailboat.data.CompassCourse;
import de.fhb.sailboat.data.PilotDriveAngleRudderCommand;
import de.fhb.sailboat.data.RudderPosition;

/***
 * 
 * @author Tobias Koppe
 * @version 2
 */
public class CSVWriter {

	private static ArrayList<RudderPosition> rudderPositionList;
	private static ArrayList<CompassCourse> compassCoursesList;
	private static ArrayList<PilotDriveAngleRudderCommand> pilotDriveAngleRudderCommandList;
	
	private static DateFormat dFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	private static DateFormat dFormatDifference = new SimpleDateFormat("hh:mm:ss:SS");
	
	public static void CSVWriterWriteRudderPositions(String pFileName , ArrayList<RudderPosition> pList){
		rudderPositionList = pList;
		try {
			FileWriter fWriter = new FileWriter(pFileName);
			for(int i=0;i<rudderPositionList.size();i++){
				StringBuilder sb=new StringBuilder();
				sb.append(dFormate.format(rudderPositionList.get(i).getTimeStamp()));
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
				sb.append(dFormate.format(compassCoursesList.get(i).getTimeStamp()));
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
	
	public static void CSVWriterWritePilotDriveAngleRudderCommands(String pFileName , ArrayList<PilotDriveAngleRudderCommand> pList){
		pilotDriveAngleRudderCommandList = pList;
		try {
			FileWriter fWriter = new FileWriter(pFileName);
			for(int i=0;i<pilotDriveAngleRudderCommandList.size();i++){
				StringBuilder sb=new StringBuilder();
				sb.append(dFormate.format(pilotDriveAngleRudderCommandList.get(i).getTimeStamp()));
				sb.append(";");
				sb.append(pilotDriveAngleRudderCommandList.get(i).getRudderPosition());
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
