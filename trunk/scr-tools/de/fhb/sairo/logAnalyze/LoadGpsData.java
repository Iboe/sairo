package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.GpsDataList;
import de.fhb.sairo.data.LogData.LogGPSCoordinate;
import de.fhb.sairo.fileio.FileLoader;

public class LoadGpsData {
	
	public static GpsDataList load(String pFileName){
		GpsDataList list = new GpsDataList();
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		try {
			String zeile=null;
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(LogTextblocks.gpsSensorThreadName)){
					Date d=filter.filterTimestamp(zeile);
					int startGpsLong = zeile.indexOf(LogTextblocks.gpsSensorLongitude)+LogTextblocks.gpsSensorLongitude.length()+1;
					int startGpsLat = zeile.indexOf(LogTextblocks.gpsSensorLatitude)+LogTextblocks.gpsSensorLatitude.length()+1;
					int startGpsSat = zeile.indexOf(LogTextblocks.gpsSensorSatelites)+LogTextblocks.gpsSensorSatelites.length()+1;
					int startGpsSpeed = zeile.indexOf(LogTextblocks.gpsSensorSpeed)+LogTextblocks.gpsSensorSpeed.length()+1;
					int endGpsLong = zeile.indexOf(",", startGpsLong);
					int endGpsLat = zeile.indexOf(",",startGpsLat);
					int endGpsSat = zeile.indexOf(",",startGpsSat);
					int endGpsSpeed = zeile.length()-1;
					String gpsLong = zeile.substring(startGpsLong,endGpsLong).trim();
					String gpsLat = zeile.substring(startGpsLat, endGpsLat).trim();
					String gpsSat = zeile.substring(startGpsSat,endGpsSat).trim();
					//String gpsSpeed = zeile.substring(startGpsSpeed,endGpsSpeed).trim();
					String gpsSpeed = "0";
					list.add(new LogGPSCoordinate(Double.valueOf(gpsLat), Double.valueOf(gpsLong), Integer.valueOf(gpsSat), Double.valueOf(gpsSpeed), d));
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
