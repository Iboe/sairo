package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import de.fhb.sailboat.utils.logevaluation.logTextblocks;
import de.fhb.sairo.fileio.FileLoader;

public class LoadPidController {

	private static ArrayList<String> pidLogList;
	private static String fileName;
	
	static class worker extends Thread{

		@Override
		public void run() {
			BufferedReader bfreader = FileLoader.openLogfile(fileName);
			String zeile=null;
			try {
				while((zeile=bfreader.readLine())!=null){
					if(zeile.contains(logTextblocks.pidControllerClassName)){
						pidLogList.add(zeile);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static ArrayList<String> loadPidControllerLog(String pFileName){
		fileName = pFileName;
		return pidLogList;
	}

}
