package de.fhb.sairo.logAnalyze;

import de.fhb.sairo.data.MissionList;

public class testMissionParser {

	public static void main (String args[]){
		MissionParser.MissionParser("G:\\sailboat_05_06_2013_wasser.log");
		MissionList tmpMissionList = MissionParser.getMissionList();
		for(int i=0;i<tmpMissionList.size();i++){
			tmpMissionList.get(i).setTaskList(TaskParser.parseTasksFromMission(tmpMissionList.get(i).getLogFromMission()));
		}
		tmpMissionList.toString();
	}
	
}
