package de.fhb.sairo.logAnalyze;

import java.util.ArrayList;

public class LoadCpuPerformance {

	private static ArrayList<Double> cpuPerformanceList;
	
	public static ArrayList<Double> loadCpuPerformanceData(ArrayList<String> pLog){
		cpuPerformanceList = new ArrayList<Double>();
		String tmp = "cpu performance:";
		for(int i=0; i<pLog.size();i++){
			if(pLog.get(i).contains("de.fhb.sailboat.utils.performance.CPU")){
				int start = pLog.get(i).indexOf("cpu performance:") + tmp.length() ;
				int ende = pLog.get(i).indexOf("%");
				cpuPerformanceList.add(Double.valueOf(pLog.get(i).substring(start, ende)));
			}
		}
		return cpuPerformanceList;
	}
}
