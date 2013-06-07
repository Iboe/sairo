package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.MissionList;
import de.fhb.sairo.data.LogData.LogMission;
import de.fhb.sairo.fileio.FileLoader;

public class MissionParser {

	private static String logFileName;
	private static MissionList missionList;
	
	public static void MissionParser(String pLogFileName){
		logFileName=pLogFileName;
		missionList = new MissionList();
		System.out.println("Start parsing ...");
		parseLogFileForMissions();
	}
	
	private static void parseLogFileForMissions(){
		BufferedReader reader = FileLoader.openLogfile(logFileName); // Get instancee of bufferedReader for File
		String zeile=null;
		int missionCounter=1;
		try {
			while((zeile=reader.readLine())!=null){
				if(zeile.contains(LogTextblocks.missionExecuteSignal)){
					System.out.println("Found Mission at: " + zeile + " which starting at: " + filter.filterTimestamp(zeile));
					missionList.add(new LogMission(String.valueOf(missionCounter),filter.filterTimestamp(zeile)));
					missionCounter++;
				}
			}
			System.out.println("Missionlist content: ");
			System.out.println(missionList.toString());
			reader.close();
			//Push logfile to mission
			for (int i=0;i<missionList.size();i++){
				System.out.println("Search log for mission: " + i + " which starts at: " + missionList.get(i).getStartTimeString());
			boolean start=false;
			boolean end=false;
			BufferedReader reader2 = FileLoader.openLogfile(logFileName); // Get instancee of bufferedReader for File
			while((zeile=reader2.readLine())!=null){
				
				if(zeile.contains(missionList.get(i).getStartTimeString()) && zeile.contains(LogTextblocks.missionExecuteSignal)){
					start=true;
					System.out.println("Found log start("+missionList.get(i).getStartTimeString()+") in mission: " + i + " at " + zeile);
				}
				
				if(i+1<=missionList.size()-1){
					if(zeile.contains(missionList.get(i+1).getStartTimeString())&& zeile.contains(LogTextblocks.missionExecuteSignal)){
						end=true;
						System.out.println("Found log end in mission: " + i + " at " + missionList.get(i+1).getStartTimeString());
						}
					}
				
				if(start && !end){
					missionList.get(i).getLogFromMission().add(zeile);
				}
				
			}
			System.out.println("Added: " + missionList.get(i).getLogFromMission().size() + " logentries to mission");
			reader2.close();
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the missionList
	 */
	public static MissionList getMissionList() {
		return missionList;
	}

	/**
	 * @param missionList the missionList to set
	 */
	public static void setMissionList(MissionList missionList) {
		MissionParser.missionList = missionList;
	}
	
	
}
