package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.both.LogTextblocks;
import de.fhb.sairo.data.Data.PidControllerState;
import de.fhb.sairo.fileio.FileLoader;

public class LoadPidController {

	private static ArrayList<String> pidLogList;
	private static String fileName;
	
	private static ArrayList<String> taskLog;
	private static ArrayList<String> pidLogFromTask;
	private static ArrayList<PidControllerState> pidControllerDataList;
	
	static class workerFile extends Thread{

		@Override
		public void run() {
			BufferedReader bfreader = FileLoader.openLogfile(fileName);
			String zeile=null;
			try {
				while((zeile=bfreader.readLine())!=null){
					if(zeile.contains(LogTextblocks.pidControllerClassName)){
						pidLogList.add(zeile);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	static class workerList extends Thread{
		
		@Override
		public void run(){
			for(int i=0;i<taskLog.size();i++){
				String zeile=taskLog.get(i);
				if(zeile.contains(LogTextblocks.pidControllerClassName)){
					pidLogList.add(zeile);
				}
			}
		}
	}
	
	
	private static PidControllerState extractPidControllerState(String pZeile){
		PidControllerState state;
		double kp,ki,kd,ta,esum,eold,deltaAngle,lastOutput,output;
		if(pZeile.contains("PIDController controll rudder:")){
		kp = extractKp(pZeile);
		ki = extractKi(pZeile);
		kd = extractKd(pZeile);
		ta = extractSamplingTime(pZeile);
		esum= extractEsum(pZeile);
		eold = extractEold(pZeile);
		deltaAngle = extractDeltaAngle(pZeile);
		lastOutput=extractLastOutput(pZeile);
		output=extractOutput(pZeile);
		state=new PidControllerState(kp, ki, kd, ta, esum, eold, deltaAngle, lastOutput, output);
		state.setTimeStamp(filter.filterTimestamp(pZeile));
		}
		else{
			state = null;
		}
		return state;
		
	}
	
	private static double extractOutput(String pZeile){
		int startOutput=0;
		int endOutput=0;
		return 0.0;
	}
	
	private static double extractLastOutput(String pZeile){
		return 0.0;
	}
	
	private static double extractDeltaAngle(String pZeile){
		int startDeltaAngle=0;
		int endDeltaAngle=0;
		startDeltaAngle = pZeile.indexOf(LogTextblocks.pidControllerDeltaAngleMark)+LogTextblocks.pidControllerDeltaAngleMark.length()+1;
		endDeltaAngle = pZeile.indexOf("]", startDeltaAngle);
		String tmpString = pZeile.substring(startDeltaAngle, endDeltaAngle);
		try{
		Double tmpDouble=Double.valueOf(tmpString);
		return tmpDouble;
		} catch (NumberFormatException e){
			System.out.println(pZeile);
			e.printStackTrace();
			return 0.0;
		}
	}
	
	private static double extractEold(String pZeile){
		return 0.0;
	}
	
	private static double extractEsum(String pZeile){
		return 0.0;
	}
	
	private static double extractSamplingTime(String pZeile){
		int startDeltaAngle=0;
		int endDeltaAngle=0;
		startDeltaAngle = pZeile.indexOf(LogTextblocks.pidControllerSamplingTimeMark)+LogTextblocks.pidControllerSamplingTimeMark.length()+1;
		endDeltaAngle = pZeile.indexOf("]", startDeltaAngle);
		String tmpString = pZeile.substring(startDeltaAngle, endDeltaAngle);
		try{
		Double tmpDouble=Double.valueOf(tmpString);
		return tmpDouble;
		} catch (NumberFormatException e){
			System.out.println(pZeile);
			e.printStackTrace();
			return 0.0;
		}
	}
	
	private static double extractKd(String pZeile){
		return 0.0;
	}
	
	private static double extractKi(String pZeile){
		return 0.0;
	}
	
	private static double extractKp(String pZeile){
		return 0.0;
	}

	static class workerLoader extends Thread{

		@Override
		public void run() {
			ArrayList<PidControllerState> list = new ArrayList<PidControllerState>();
			for(int i=0;i<pidLogFromTask.size();i++){
				if(pidLogFromTask.get(i).contains("PIDController controll rudder:")){
				list.add(extractPidControllerState(pidLogFromTask.get(i)));
				}
			}
			pidControllerDataList = list;
		}
		
	}
	
	public static ArrayList<PidControllerState> extractPidControllerData(ArrayList<String> pPidLog){
		pidLogFromTask = pPidLog;
		workerLoader t = new workerLoader();
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return pidControllerDataList;
	}
	
	public static ArrayList<String> loadPidControllerLog(String pFileName){
		fileName = pFileName;
		pidLogList = new ArrayList<String>();
		workerFile t = new workerFile();
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return pidLogList;
	}
	
	public static ArrayList<String> loadPidControllerLog(ArrayList<String> pTaskLog){
		taskLog = pTaskLog;
		pidLogList = new ArrayList<String>();
		workerList t = new workerList();
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return pidLogList;
	}

}
