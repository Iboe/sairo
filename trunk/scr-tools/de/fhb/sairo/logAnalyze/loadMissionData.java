package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import de.fhb.sairo.data.MissionList;
import de.fhb.sairo.data.TaskList;
import de.fhb.sairo.data.LogData.LogMission;
import de.fhb.sairo.data.Task.CompassCourseTask;
import de.fhb.sairo.data.Task.Task;
import de.fhb.sairo.fileio.FileLoader;

public class loadMissionData{

	private static String fileName;
	private static MissionList returnList;

	static class worker extends Thread{

		@Override
		public void run() {
			MissionList list = new MissionList(); //Instance new Missionlist to return it
			LogMission mission = null; //Instance temporaray LogMission
			BufferedReader reader = FileLoader.openLogfile(fileName); // Get instancee of bufferedReader for File
			String zeile=null;
			int taskNo=0;
			String taskArguments = null;
//			log.trace("Begin to read logfile: " + fileName + " to scan for missions and tasks");
			try {
				//Go trough complete logfile
				while((zeile=reader.readLine())!=null){
					//If found mission execution and no stopTaskMark
					if(zeile.contains(logTextblocks.missionExecuteSignal) && !zeile.contains(logTextblocks.stopTaskMark)){
//					if(zeile.contains(logTextblocks.missionExecuteSignal)){	
					//If not mission variable instanced, then do it
						if(mission!=null){
						list.add(mission);
						taskNo=0;
						}
						//Found new mission, create new, find and set startTime of mission and add all 
						//log rows to mission
						
						//Log that new mission is founded and the length of the row
//						System.out.println("Found new mission: " + zeile + " string length: " + zeile.length());
						
						//Create new mission with mission number
						mission = new LogMission("Mission " + (list.size()+1));
						//Filter the timestamp for startime of mission
						mission.setStartTime(filter.filterTimestamp(zeile));
						//Add this row to mission log
						mission.getLogFromMission().add(zeile);
						
						/**
						 * Search for tasks in this mission and create a task list 
						 */
						int startTaskDescription = zeile.indexOf("MissionImpl [")+13;
						int endTaskDescription = zeile.indexOf("]", startTaskDescription)-1;
						String subString = zeile.substring(startTaskDescription, endTaskDescription);
						String[] taskList = subString.split(",");
						//
						
						//Iterate trough the founded task list 
						for(int i=0;i<taskList.length;i++){
							//Log that a task is founded
//							log.info("Mission:" + list.size() + "\t Found Task : " + taskList[i].trim());
							//Add the task to mission tasklist with instance of task type
							if(taskList[i].trim().equals(logTextblocks.compassCourseTaskMark)){
								mission.getTaskList().add(new CompassCourseTask(taskList[i],0));
							}
							//If task type can't find add standard task
							else{
							mission.getTaskList().add(new Task(taskList[i]));
							}
						}
					}
					else if(zeile.contains("execute task:")  && !zeile.contains(logTextblocks.stopTaskMark)){
//					else if(zeile.contains("execute task:")){
						System.out.println(zeile);
						int tmp = zeile.indexOf(logTextblocks.taskExecutionSignal) + logTextblocks.taskExecutionSignal.length();
						taskArguments = zeile.substring(zeile.indexOf("[", tmp), zeile.indexOf("]", tmp)+1);
						mission.getTaskList().get(taskNo).setTaskArguments(taskArguments);
						mission.getTaskList().get(taskNo).setStartTime(filter.filterTimestamp(zeile));
						taskNo++;
						if(mission!=null){
						mission.getLogFromMission().add(zeile);
						}
					}
					else{
						if(mission!=null){
						mission.getLogFromMission().add(zeile);
						}
					}
				}
				setLogtoTasks(list);
				returnList=list;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}
		
	}
	
	public static MissionList loadMissions(String pFileName){
		fileName = pFileName;
		Logger log = Logger.getLogger(loadMissionData.class);
		worker t = new worker();
		t.run();
//		MissionList list = new MissionList(); //Instance new Missionlist to return it
//		LogMission mission = null; //Instance temporaray LogMission
//		BufferedReader reader = FileLoader.openLogfile(pFileName); // Get instancee of bufferedReader for File
//		String zeile=null;
//		int taskNo=0;
//		String taskArguments = null;
//		log.trace("Begin to read logfile: " + pFileName + " to scan for missions and tasks");
//		try {
//			//Go trough complete logfile
//			while((zeile=reader.readLine())!=null){
//				//If found mission execution and no stopTaskMark
//				if(zeile.contains(logTextblocks.missionExecuteSignal) && !zeile.contains(logTextblocks.stopTaskMark)){
////				if(zeile.contains(logTextblocks.missionExecuteSignal)){	
//				//If not mission variable instanced, then do it
//					if(mission!=null){
//					list.add(mission);
//					taskNo=0;
//					}
//					//Found new mission, create new, find and set startTime of mission and add all 
//					//log rows to mission
//					
//					//Log that new mission is founded and the length of the row
////					System.out.println("Found new mission: " + zeile + " string length: " + zeile.length());
//					
//					//Create new mission with mission number
//					mission = new LogMission("Mission " + (list.size()+1));
//					//Filter the timestamp for startime of mission
//					mission.setStartTime(filter.filterTimestamp(zeile));
//					//Add this row to mission log
//					mission.getLogFromMission().add(zeile);
//					
//					/**
//					 * Search for tasks in this mission and create a task list 
//					 */
//					int startTaskDescription = zeile.indexOf("MissionImpl [")+13;
//					int endTaskDescription = zeile.indexOf("]", startTaskDescription)-1;
//					String subString = zeile.substring(startTaskDescription, endTaskDescription);
//					String[] taskList = subString.split(",");
//					//
//					
//					//Iterate trough the founded task list 
//					for(int i=0;i<taskList.length;i++){
//						//Log that a task is founded
//						log.info("Mission:" + list.size() + "\t Found Task : " + taskList[i].trim());
//						//Add the task to mission tasklist with instance of task type
//						if(taskList[i].trim().equals(logTextblocks.compassCourseTaskMark)){
//							mission.getTaskList().add(new CompassCourseTask(taskList[i],0));
//						}
//						//If task type can't find add standard task
//						else{
//						mission.getTaskList().add(new Task(taskList[i]));
//						}
//					}
//				}
//				else if(zeile.contains("execute task:")  && !zeile.contains(logTextblocks.stopTaskMark)){
////				else if(zeile.contains("execute task:")){
//					System.out.println(zeile);
//					int tmp = zeile.indexOf(logTextblocks.taskExecutionSignal) + logTextblocks.taskExecutionSignal.length();
//					taskArguments = zeile.substring(zeile.indexOf("[", tmp), zeile.indexOf("]", tmp)+1);
//					mission.getTaskList().get(taskNo).setTaskArguments(taskArguments);
//					mission.getTaskList().get(taskNo).setStartTime(filter.filterTimestamp(zeile));
//					taskNo++;
//					if(mission!=null){
//					mission.getLogFromMission().add(zeile);
//					}
//				}
//				else{
//					if(mission!=null){
//					mission.getLogFromMission().add(zeile);
//					}
//				}
//			}
//			setLogtoTasks(list);
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (IndexOutOfBoundsException e){
//			e.printStackTrace();
//		}
		return returnList;
	}
	
	private static int increaseTaskNo(int pNo){
		int returnValue=pNo+1;
		return returnValue;
	}
	
	private static void setLogtoTasks(MissionList pList){
		TaskList taskList = null;
		LogMission logMission = null;
		int tmpTaskNo=0;
		//For loop to get every mission from list
		for(int i=0;i<pList.size();i++){
			logMission = pList.get(i); // eine Mission auswählen
			taskList = logMission.getTaskList(); // alle Tasks einer Mission heraussuchen
			tmpTaskNo=-1;
			//For loop to iterate mission log
			for(int j=0; j<logMission.getLogFromMission().size(); j++){ // Log einer Mission durchgehen, zeilenweise
				String zeile = logMission.getLogFromMission().get(j);
				if(zeile.contains(logTextblocks.taskExecutionSignal) && !zeile.contains(logTextblocks.stopTaskMark)){ // eine Zeile nehmen, auf ExecutionSignal untersuchen
					tmpTaskNo=increaseTaskNo(tmpTaskNo);
					System.out.println("Next task: " + logMission.getLogFromMission().get(j));
				}
			else if(tmpTaskNo>-1){

			Task tmpTask=taskList.get(tmpTaskNo);

			tmpTask.getLog().add(logMission.getLogFromMission().get(j));

			}
			}
		}
	}
}
