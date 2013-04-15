package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.WindData;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class evaluateWindData {

	private ArrayList<WindData> windDataList = new ArrayList<WindData>();
	
	public evaluateWindData(String pLogFileName){
		BufferedReader bfReader=null;
		System.out.println("Loaded logfile: " + pLogFileName);
		try {
			bfReader = new BufferedReader(new FileReader(pLogFileName));
			String zeile=null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.windSensorClassName)){
					//System.out.println("Analyze: " + zeile);
					Date d=filter.filterTimestamp(zeile);
					int startWindDir = zeile.indexOf(logTextblocks.windSensorWindDirection)+logTextblocks.windSensorWindDirection.length()+1;
					int endWindDir = zeile.indexOf(logTextblocks.windSensorWindSpeed)-1;
					String windDir = zeile.substring(startWindDir,endWindDir).trim();
					int startWindSpeed = zeile.indexOf(logTextblocks.windSensorWindSpeed)+logTextblocks.windSensorWindSpeed.length()+1;
					int endWindSpeed = zeile.length();
					String windSpeed = zeile.substring(startWindSpeed,endWindSpeed).trim();
					windDataList.add(new WindData(d, Integer.valueOf(windDir), Double.valueOf(windSpeed)));
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<WindData> getWindDataList() {
		return windDataList;
	}

	public void setWindDataList(ArrayList<WindData> windDataList) {
		this.windDataList = windDataList;
	}
	
}
