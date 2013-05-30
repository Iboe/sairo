package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.fileio.FileLoader;

public class LogAnalyzer {

	public LogAnalyzer() {
		
	}
	
	public static void analyze(String pFileName){
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		String zeile=null;
		try {
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(LogTextblocks.aksenlocomotionClassName)){
					//Analyse AKSEN
				}
				else if(zeile.contains(LogTextblocks.driveAngleThreadName)){
					//Analyse DriveAngleThread
				}
				else if(zeile.contains(LogTextblocks.compassThreadName)){
					//Analyse Compass
				}
				else if(zeile.contains(LogTextblocks.gpsSensorThreadName)){
					//Analyse GPS
				}
				else if(zeile.contains(LogTextblocks.missionExecuteSignal)){
					//Analyse Missionen und Tasks
				}
				else if(zeile.contains(LogTextblocks.windSensorClassName)){
					//Analyse Windsensor
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
