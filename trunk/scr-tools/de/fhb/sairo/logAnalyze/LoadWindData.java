package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.WindDatalist;
import de.fhb.sairo.data.LogData.LogWindData;
import de.fhb.sairo.fileio.FileLoader;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class LoadWindData {

	private ArrayList<LogWindData> windDataList = new ArrayList<LogWindData>();
	
	public static WindDatalist loadWindData(String pFileName){
		WindDatalist list = new WindDatalist();
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		System.out.println("Loaded logfile: " + pFileName);
		try {
			reader = new BufferedReader(new FileReader(pFileName));
			String zeile=null;
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(LogTextblocks.windSensorClassName)){
					Date d=filter.filterTimestamp(zeile);
					int startWindDir = zeile.indexOf(LogTextblocks.windSensorWindDirection)+LogTextblocks.windSensorWindDirection.length()+1;
					int endWindDir = zeile.indexOf(LogTextblocks.windSensorWindSpeed)-1;
					String windDir = zeile.substring(startWindDir,endWindDir).trim();
					int startWindSpeed = zeile.indexOf(LogTextblocks.windSensorWindSpeed)+LogTextblocks.windSensorWindSpeed.length()+1;
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
