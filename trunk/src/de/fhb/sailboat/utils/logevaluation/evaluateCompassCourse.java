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
	
	/***
	 * Evaluates the logs of compasscourses
	 * @author Tobias Koppe
	 * @version 1
	 * @param pLogfileName
	 * @param pCsvFileName
	 */
	public evaluateCompassCourse(String pLogfileName, String pCsvFileName){
		System.out.println("Loaded logfile: " + pLogfileName);
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pLogfileName));
			String zeile=null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.compassThreadName)){
					System.out.println("Analyze: " + zeile);
					Date d=filter.filterTimestamp(zeile);
					int startAzimuth = zeile.indexOf(logTextblocks.compassAzimuthMark)+logTextblocks.compassAzimuthMark.length();
					int endAzimuth = zeile.indexOf(logTextblocks.compassPitchMark)-1;
					String azimuth = zeile.substring(startAzimuth,endAzimuth);
					compassCourseList.add(new CompassCourse(Float.valueOf(azimuth), d));
					if(pCsvFileName!=null){
					CSVWriter.CSVWriterWriteCompassCourses(pCsvFileName, compassCourseList);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
