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

	private String pLogfileName;
	
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
	
	public static void  CSVWriterWrite(String pFileName, ArrayList<CompassCourse> pCompassCourseList,ArrayList<SimplePidControllerState> pSimplePidControllerStateList, ArrayList<PilotDriveAngleRudderCommand> pPilotDriveAngleRudderCommandList, ArrayList<RudderPosition> pRudderPositionList){
		try {
			FileWriter fWriter = new FileWriter(pFileName);
			//Zeitstempel,Kompasskurs Azimuth,Differenz SimplePIDController, Rudderposition SimplePIDController,Pilot Rudderposition, AKSEN Winkel Rudderposition
			fWriter.append("Zeitstempel;Zeit in ms seit 01.01.1970;Kompasskurs Azimuth;Differenz SimplePIDController;Rudderposition SimplePIDController;Pilot Rudderposition;AKSEN Winkel Rudderposition"+System.getProperty("line.separator"));
			System.out.println("Write: " + "Zeitstempel;Zeit in ms seit 01.01.1970;Kompasskurs Azimuth;Differenz SimplePIDController;Rudderposition SimplePIDController;Pilot Rudderposition;AKSEN Winkel Rudderposition"+System.getProperty("line.separator"));
			for(int i=0;i<pCompassCourseList.size();i++){
				System.out.println("Write: " + pCompassCourseList.get(i).getTimeStampString()+";"+pCompassCourseList.get(i).getTimeStamp().getTime()+";"+pCompassCourseList.get(i).getCompassCourseAzimuth());
				fWriter.append(pCompassCourseList.get(i).getTimeStampString()+";"+pCompassCourseList.get(i).getTimeStamp().getTime()+";"+pCompassCourseList.get(i).getCompassCourseAzimuth()+System.getProperty("line.separator"));
			}
			for(int i=0;i<pSimplePidControllerStateList.size();i++){
				System.out.println("Write: " + pSimplePidControllerStateList.get(i).getTimeStampString()+";" + pSimplePidControllerStateList.get(i).getTimeStamp().getTime() + ";;"+pSimplePidControllerStateList.get(i).getDifference());
				fWriter.append(pSimplePidControllerStateList.get(i).getTimeStampString()+";"+pSimplePidControllerStateList.get(i).getTimeStamp().getTime() + ";;"+pSimplePidControllerStateList.get(i).getDifference()+System.getProperty("line.separator"));
			}
			for(int i=0;i<pSimplePidControllerStateList.size();i++){
				System.out.println("Write: " + pSimplePidControllerStateList.get(i).getTimeStampString()+";"+pSimplePidControllerStateList.get(i).getTimeStamp().getTime() + ";;;"+pSimplePidControllerStateList.get(i).getRudderPos());
				fWriter.append(pSimplePidControllerStateList.get(i).getTimeStampString()+";"+pSimplePidControllerStateList.get(i).getTimeStamp().getTime()+";;;"+pSimplePidControllerStateList.get(i).getRudderPos()+System.getProperty("line.separator"));
			}
			for(int i=0;i<pPilotDriveAngleRudderCommandList.size();i++){
				System.out.println("Write: " + pPilotDriveAngleRudderCommandList.get(i).getTimeStampString()+";"+pPilotDriveAngleRudderCommandList.get(i).getTimeStamp().getTime()+";;;;"+pPilotDriveAngleRudderCommandList.get(i).getRudderPosition());
				fWriter.append(pPilotDriveAngleRudderCommandList.get(i).getTimeStampString()+";"+pPilotDriveAngleRudderCommandList.get(i).getTimeStamp().getTime()+";;;;"+pPilotDriveAngleRudderCommandList.get(i).getRudderPosition()+System.getProperty("line.separator"));
			}
			for(int i=0;i<pRudderPositionList.size();i++){
				System.out.println("Write: " + pRudderPositionList.get(i).getTimeStampString()+";"+pRudderPositionList.get(i).getTimeStamp().getTime()+";;;;;"+pRudderPositionList.get(i).getAngle());
				fWriter.append(pRudderPositionList.get(i).getTimeStampString()+";"+pRudderPositionList.get(i).getTimeStamp().getTime()+";;;;;"+pRudderPositionList.get(i).getAngle()+System.getProperty("line.separator"));
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void sortTimestamps(String pFileName){
		String zeile="";
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pFileName));
			while((zeile=bfReader.readLine())!=null){
				int endColoumnOne=zeile.indexOf(";");
				String subString=zeile.substring(0, endColoumnOne);
				System.out.println("Found: " + subString);
				Date d = dFormate.parse(subString);
				System.out.println("Parsed date: " + d.getTime());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void calcTimestampDifferences(String pFileName){
		
	}
	
}
