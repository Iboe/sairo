package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.CompassCourse;

/***
 * This class represents the evaluation module for evaluate the compass course
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateCompassCourse {

	private ArrayList<CompassCourse> compassCourseList = new ArrayList<CompassCourse>();
	
	/**
	 * *
	 * Evaluates the logentries of compasscourses in pLogfileName and save
	 * the result in compassCourseList.
	 *
	 * @param pLogfileName the logfile name
	 */
	public evaluateCompassCourse(String pLogfileName){
		BufferedReader bfReader=null;
		System.out.println("Loaded logfile: " + pLogfileName);
		try {
			bfReader = new BufferedReader(new FileReader(pLogfileName));
			String zeile=null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.compassThreadName)){
					//System.out.println("Analyze: " + zeile);
					Date d=filter.filterTimestamp(zeile);
					int startAzimuth = zeile.indexOf(logTextblocks.compassAzimuthMark)+logTextblocks.compassAzimuthMark.length();
					int endAzimuth = zeile.indexOf(logTextblocks.compassPitchMark)-1;
					String azimuth = zeile.substring(startAzimuth,endAzimuth);
					compassCourseList.add(new CompassCourse(Float.valueOf(azimuth), d));
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<CompassCourse> getCompassCourseList() {
		return compassCourseList;
	}

	public void setCompassCourseList(ArrayList<CompassCourse> compassCourseList) {
		this.compassCourseList = compassCourseList;
	}
	
}
