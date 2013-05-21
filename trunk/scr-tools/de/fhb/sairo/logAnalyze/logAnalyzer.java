package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;

import de.fhb.sairo.fileio.FileLoader;

public class logAnalyzer {

	public logAnalyzer() {
		
	}
	
	public static void analyze(String pFileName){
		BufferedReader reader = FileLoader.openLogfile(pFileName);
		String zeile=null;
		try {
			while((zeile = reader.readLine()) != null){
				if(zeile.contains(logTextblocks.aksenlocomotionClassName)){
					//Analyse AKSEN
				}
				else if(zeile.contains(logTextblocks.driveAngleThreadName)){
					//Analyse DriveAngleThread
				}
				else if(zeile.contains(logTextblocks.compassThreadName)){
					//Analyse Compass
				}
				else if(zeile.contains(logTextblocks.gpsSensorThreadName)){
					//Analyse GPS
				}
				else if(zeile.contains(logTextblocks.missionExecuteSignal)){
					//Analyse Missionen und Tasks
				}
				else if(zeile.contains(logTextblocks.windSensorClassName)){
					//Analyse Windsensor
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
