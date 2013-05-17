package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.windDatalist;
import de.fhb.sairo.data.LogData.LogWindData;
import de.fhb.sairo.fileio.FileLoader;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class loadWindData {

	private ArrayList<LogWindData> windDataList = new ArrayList<LogWindData>();
	
	public static windDatalist loadWindData(String pFileName){
		windDatalist list = new windDatalist();
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		System.out.println("Loaded logfile: " + pFileName);
		try {
			reader = new BufferedReader(new FileReader(pFileName));
			String zeile=null;
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(logTextblocks.windSensorClassName)){
					//System.out.println("Analyze: " + zeile);
					Date d=filter.filterTimestamp(zeile);
					int startWindDir = zeile.indexOf(logTextblocks.windSensorWindDirection)+logTextblocks.windSensorWindDirection.length()+1;
					int endWindDir = zeile.indexOf(logTextblocks.windSensorWindSpeed)-1;
					String windDir = zeile.substring(startWindDir,endWindDir).trim();
					int startWindSpeed = zeile.indexOf(logTextblocks.windSensorWindSpeed)+logTextblocks.windSensorWindSpeed.length()+1;
					int endWindSpeed = zeile.length();
					String windSpeed = zeile.substring(startWindSpeed,endWindSpeed).trim();
					list.add(new LogWindData(d, Integer.valueOf(windDir), Double.valueOf(windSpeed)));
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

	public ArrayList<LogWindData> getWindDataList() {
		return windDataList;
	}

	public void setWindDataList(ArrayList<LogWindData> windDataList) {
		this.windDataList = windDataList;
	}
	
}
