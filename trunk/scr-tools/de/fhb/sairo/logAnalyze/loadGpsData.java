package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import de.fhb.sairo.data.gpsDataList;
import de.fhb.sairo.data.LogData.LogGPSCoordinate;
import de.fhb.sairo.fileio.FileLoader;

public class loadGpsData {
	
	public static gpsDataList load(String pFileName){
		gpsDataList list = new gpsDataList();
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		try {
			String zeile=null;
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(logTextblocks.gpsSensorThreadName)){
					System.out.println("Analyze: " + zeile);
					Date d=filter.filterTimestamp(zeile);
					int startGpsLong = zeile.indexOf(logTextblocks.gpsSensorLongitude)+logTextblocks.gpsSensorLongitude.length()+1;
					int startGpsLat = zeile.indexOf(logTextblocks.gpsSensorLatitude)+logTextblocks.gpsSensorLatitude.length()+1;
					int startGpsSat = zeile.indexOf(logTextblocks.gpsSensorSatelites)+logTextblocks.gpsSensorSatelites.length()+1;
					int startGpsSpeed = zeile.indexOf(logTextblocks.gpsSensorSpeed)+logTextblocks.gpsSensorSpeed.length()+1;
					int endGpsLong = zeile.indexOf(",", startGpsLong);
					int endGpsLat = zeile.indexOf(",",startGpsLat);
					int endGpsSat = zeile.indexOf(",",startGpsSat);
					int endGpsSpeed = zeile.length()-1;
					String gpsLong = zeile.substring(startGpsLong,endGpsLong).trim();
					String gpsLat = zeile.substring(startGpsLat, endGpsLat).trim();
					String gpsSat = zeile.substring(startGpsSat,endGpsSat).trim();
					String gpsSpeed = zeile.substring(startGpsSpeed,endGpsSpeed).trim();
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
