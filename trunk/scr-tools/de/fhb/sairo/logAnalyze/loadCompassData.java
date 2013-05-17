package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import de.fhb.sairo.data.compassCourseList;
import de.fhb.sairo.data.LogData.LogCompassCourse;
import de.fhb.sairo.fileio.FileLoader;

public class loadCompassData {
	
	public static compassCourseList load(String pFileName){
	compassCourseList list = new compassCourseList();
	BufferedReader reader = FileLoader.openLogfile(pFileName);
	try {
		String zeile=null;
		while((zeile = reader.readLine()) != null){
			if(zeile.contains(logTextblocks.compassThreadName)){
				Date d=filter.filterTimestamp(zeile);
				int startAzimuth = zeile.indexOf(logTextblocks.compassAzimuthMark)+logTextblocks.compassAzimuthMark.length();
				int endAzimuth = zeile.indexOf(logTextblocks.compassPitchMark)-1;
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
