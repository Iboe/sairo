package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.CompassCourseList;
import de.fhb.sairo.data.LogData.LogCompassCourse;
import de.fhb.sairo.fileio.FileLoader;

public class LoadCompassData {
	
	public static CompassCourseList load(String pFileName){
	CompassCourseList list = new CompassCourseList();
	BufferedReader reader = FileLoader.openLogfile(pFileName);
	try {
		String zeile=null;
		while((zeile = reader.readLine()) != null){
			if(zeile.contains(LogTextblocks.compassThreadName)){
				Date d=filter.filterTimestamp(zeile);
				int startAzimuth = zeile.indexOf(LogTextblocks.compassAzimuthMark)+LogTextblocks.compassAzimuthMark.length();
				int endAzimuth = zeile.indexOf(LogTextblocks.compassPitchMark)-1;
				String azimuth = zeile.substring(startAzimuth,endAzimuth);
				list.add(new LogCompassCourse(Float.valueOf(azimuth), d));
			}
		}
		reader.close();
		return list;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
	}
}
