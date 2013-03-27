package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.CompassCourse;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateCompassCourse {

	private ArrayList<CompassCourse> compassCourseList = new ArrayList<CompassCourse>();
	
	public evaluateCompassCourse(String pFileName){
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pFileName));
			String zeile=null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.compassThreadName)){
					System.out.println("Found compass log");
//					String timeStamp="";
//					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
//					int startDate=0;
//					for(int j=0;j<zeile.length();j++){
//						if(Character.isDigit(zeile.charAt(j))){
//							startDate = j;
//							j=zeile.length()+1;
//						}
//					}
//					int untilDate = zeile.indexOf("[")-1;
//					timeStamp=zeile.substring(startDate, untilDate);
//					timeStamp=timeStamp.replace(',',':');
//					
//					Date d = simpleDateFormat.parse(timeStamp);
					Date d=filter.filterTimestamp(zeile);
					System.out.println("TimeStap: " + d.toString());
					int startAzimuth = zeile.indexOf(logTextblocks.compassAzimuthMark)+logTextblocks.compassAzimuthMark.length();
					int endAzimuth = zeile.indexOf(logTextblocks.compassPitchMark)-1;
					String azimuth = zeile.substring(startAzimuth,endAzimuth);
					System.out.println("SubString start: " + startAzimuth +" end: " + endAzimuth + " = " + azimuth);
					compassCourseList.add(new CompassCourse(Float.valueOf(azimuth), d));
					CSVWriter.CSVWriterWriteCompassCourses("sailboat_data.log_27_03_2013_Labor_01_compasscourses.csv", compassCourseList);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
	}
	
}
