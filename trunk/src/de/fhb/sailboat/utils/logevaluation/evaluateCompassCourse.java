package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
					System.out.println("Analyze: " + zeile);
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
		
	}
	
}
