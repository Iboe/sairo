package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import de.fhb.sailboat.data.CompassCourse;
import de.fhb.sailboat.data.PilotDriveAngleRudderCommand;
import de.fhb.sailboat.data.RudderPosition;
import de.fhb.sailboat.data.SimplePidControllerState;

/***
 * 
 * @author Tobias Koppe
 * @version 2
 */
public class CSVWriter {

	private static ArrayList<RudderPosition> rudderPositionList;
	private static ArrayList<CompassCourse> compassCoursesList;
	private static ArrayList<PilotDriveAngleRudderCommand> pilotDriveAngleRudderCommandList;
	
	private static ArrayList<CSVEntry> csvEntries = new ArrayList<CSVEntry>();
	
	private static DateFormat dFormate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");
	
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
	
	public static void  CSVWriterWrite(String pFileName, ArrayList<CompassCourse> pCompassCourseList,ArrayList<SimplePidControllerState> pSimplePidControllerStateList, ArrayList<PilotDriveAngleRudderCommand> pPilotDriveAngleRudderCommandList, ArrayList<RudderPosition> pRudderPositionList){
		try {
			//Zeitstempel,Kompasskurs Azimuth,Differenz SimplePIDController, Rudderposition SimplePIDController,Pilot Rudderposition, AKSEN Winkel Rudderposition
			//System.out.println("Write: " + "Zeitstempel;Zeit in ms seit 01.01.1970;Kompasskurs Azimuth;Differenz SimplePIDController;Rudderposition SimplePIDController;Pilot Rudderposition;AKSEN Winkel Rudderposition"+System.getProperty("line.separator"));
			for(int i=0;i<pCompassCourseList.size();i++){
				csvEntries.add(new CSVEntry(pCompassCourseList.get(i).getTimeStamp(),pCompassCourseList.get(i).getTimeStamp().getTime(), String.valueOf(pCompassCourseList.get(i).getCompassCourseAzimuth()), "", "", "", ""));
			}
			for(int i=0;i<pSimplePidControllerStateList.size();i++){
				csvEntries.add(new CSVEntry(pSimplePidControllerStateList.get(i).getTimeStamp(),pSimplePidControllerStateList.get(i).getTimeStamp().getTime(), "", String.valueOf(pSimplePidControllerStateList.get(i).getDifference()), "", "", ""));
			}
			for(int i=0;i<pSimplePidControllerStateList.size();i++){
				csvEntries.add(new CSVEntry(pSimplePidControllerStateList.get(i).getTimeStamp(),pSimplePidControllerStateList.get(i).getTimeStamp().getTime(), "","", String.valueOf(pSimplePidControllerStateList.get(i).getRudderPos()), "", ""));
			}
			for(int i=0;i<pPilotDriveAngleRudderCommandList.size();i++){
				csvEntries.add(new CSVEntry(pPilotDriveAngleRudderCommandList.get(i).getTimeStamp(),pPilotDriveAngleRudderCommandList.get(i).getTimeStamp().getTime(), "", "", "", String.valueOf(pPilotDriveAngleRudderCommandList.get(i).getRudderPosition()), ""));
			}
			for(int i=0;i<pRudderPositionList.size();i++){
				csvEntries.add(new CSVEntry(pRudderPositionList.get(i).getTimeStamp(),pRudderPositionList.get(i).getTimeStamp().getTime(), "", "", "", "", String.valueOf(pRudderPositionList.get(i).getAngle())));
			}
			//Sortiere Liste
			//TODO Sortieralgorithmus tauschen
			boolean unsortet=true;
			while(unsortet){
				System.out.println("Sorting list");
				unsortet=false;
				for(int i=0;i<csvEntries.size()-1;i++){
					if(csvEntries.get(i).getTime()>csvEntries.get(i+1).getTime()){
						CSVEntry first = csvEntries.get(i);
						CSVEntry second = csvEntries.get(i+1);
						csvEntries.set(i, second);
						csvEntries.set(i+1,first );
						unsortet=true;
					}
				}
			}
			FileWriter fWriter = new FileWriter(pFileName,false);
			fWriter.write("Zeitstempel;Zeit in ms seit 01.01.1970;Kompasskurs Azimuth;Differenz SimplePIDController;Rudderposition SimplePIDController;Pilot Rudderposition;AKSEN Winkel Rudderposition"+System.getProperty("line.separator"));
			System.out.println("Write sorted CSV-entries list: ");
			for(int i=0;i<csvEntries.size();i++){
				fWriter.write(csvEntries.get(i).toString());
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
