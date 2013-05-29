package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.GPSData;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class evaluateGPSData {

	private ArrayList<GPSData> gpsDataList = new ArrayList<GPSData>();
	
	public evaluateGPSData(String pLogFileName){
			BufferedReader bfReader=null;
			System.out.println("Loaded logfile: " + pLogFileName);
			try {
				bfReader = new BufferedReader(new FileReader(pLogFileName));
				String zeile=null;
				while((zeile = bfReader.readLine()) != null){
					if(zeile.contains(logTextblocks.gpsSensorThreadName)){
						//System.out.println("Analyze: " + zeile);
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
						
						gpsDataList.add(new GPSData(Double.valueOf(gpsLat), Double.valueOf(gpsLong), Integer.valueOf(gpsSat), Double.valueOf(gpsSpeed), d));
					}
				}
				bfReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		
	}

	public ArrayList<GPSData> getGpsDataList() {
		return gpsDataList;
	}

	public void setGpsDataList(ArrayList<GPSData> gpsDataList) {
		this.gpsDataList = gpsDataList;
	}

}
