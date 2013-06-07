package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.util.ArrayList;

import de.fhb.sairo.data.Task.CompassCourseTask;
import de.fhb.sairo.data.Task.PrimitiveCommandTask;
import de.fhb.sairo.data.Task.ReachCircleTask;
import de.fhb.sairo.data.Task.StopTask;

import de.fhb.sailboat.utils.logevaluation.logTextblocks;
import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.TaskList;
import de.fhb.sairo.data.Task.Task;
import de.fhb.sairo.fileio.FileLoader;

public class TaskParser {

	public static TaskList parseTasksFromMission(ArrayList<String> pMissionLog){
		TaskList tmpTaskList = new TaskList();
		for(int i=0;i<pMissionLog.size();i++){
			if(pMissionLog.get(i).contains(LogTextblocks.taskExecutionSignal)){
				System.out.println("Found task at: " + i + " in: " + pMissionLog.get(i) + " with arguments: " + filter.filterTaskArguments(pMissionLog.get(i)));
				if(pMissionLog.get(i).contains(LogTextblocks.compassCourseTaskMark)){
					String taskArguments = filter.filterTaskArguments(pMissionLog.get(i));
					int startAngle = taskArguments.indexOf("[angle=")+7;
					int pAngle = Integer.valueOf(taskArguments.substring(startAngle, taskArguments.indexOf("]", startAngle)));
					CompassCourseTask tmp = new CompassCourseTask(String.valueOf(tmpTaskList.size()),pAngle);
					tmp.setStartTime(filter.filterTimestamp(pMissionLog.get(i)));
					tmpTaskList.add(tmp);
				}
				else if(pMissionLog.get(i).contains(LogTextblocks.reachCircleTaskMark)){
					String taskArguments = filter.filterTaskArguments(pMissionLog.get(i));
					ReachCircleTask tmp = new ReachCircleTask(String.valueOf(tmpTaskList.size()),taskArguments);
					tmp.setStartTime(filter.filterTimestamp(pMissionLog.get(i)));
					tmpTaskList.add(tmp);
				}
				else if(pMissionLog.get(i).contains(LogTextblocks.stopTaskMark)){
					String taskArguments = filter.filterTaskArguments(pMissionLog.get(i));
					StopTask tmp = new StopTask(String.valueOf(tmpTaskList.size()),taskArguments);
					tmp.setStartTime(filter.filterTimestamp(pMissionLog.get(i)));
					tmpTaskList.add(tmp);
				}
				else if(pMissionLog.get(i).contains(LogTextblocks.primitiveCommandTaskMark)){
					String taskArguments = filter.filterTaskArguments(pMissionLog.get(i));
					PrimitiveCommandTask tmp = new PrimitiveCommandTask(String.valueOf(tmpTaskList.size()),taskArguments);
					tmp.setStartTime(filter.filterTimestamp(pMissionLog.get(i)));
					tmpTaskList.add(tmp);
				}
				else{
					String taskArguments = filter.filterTaskArguments(pMissionLog.get(i));
					Task tmp = new Task(String.valueOf(tmpTaskList.size()),taskArguments);
					tmp.setStartTime(filter.filterTimestamp(pMissionLog.get(i)));
					tmpTaskList.add(tmp);
				}
				
			}
		}
		
		
		for (int i=0;i<tmpTaskList.size();i++){
			System.out.println("Search log for task: " + i + " which starts at: " + tmpTaskList.get(i).getStartTimeString());
		boolean start=false;
		boolean end=false;
		for(int j=0;j<pMissionLog.size();j++){
			String zeile=pMissionLog.get(j);
			if(zeile.contains(tmpTaskList.get(i).getStartTimeString()) && zeile.contains(LogTextblocks.taskExecutionSignal)){
				start=true;
				System.out.println("Found log start("+tmpTaskList.get(i).getStartTimeString()+") in task: " + i + " at " + zeile);
			}
			
			if(i+1<=tmpTaskList.size()-1){
				if(zeile.contains(tmpTaskList.get(i+1).getStartTimeString())&& zeile.contains(LogTextblocks.taskExecutionSignal)){
					end=true;
					System.out.println("Found log end in task: " + i + " at " + tmpTaskList.get(i+1).getStartTimeString());
					}
				}
			
			if(start && !end){
				tmpTaskList.get(i).getLog().add(zeile);
			}
			
		}
		System.out.println("Added: " + tmpTaskList.get(i).getLog().size() + " logentries to task");
		
		
	}
		return tmpTaskList;
	}
}
