package de.fhb.sairo.main;

import de.fhb.sairo.data.LatencyList;
import de.fhb.sairo.logAnalyze.evaluateLatencies;

/***
 * 
 * @author Tobias Koppe
 *
 */
public class MainConsole {

	public static void main (String args[]){
		System.out.println("Start evaluate latency");
		LatencyList list = evaluateLatencies.evaluateLatencies("C:\\Users\\Tobias\\Desktop\\sailboat.log_17_04_2013_Wasser");
		System.out.println("End evaluate latency");
		System.out.println("Average latency: " + list.getAverageLatency());
		System.out.println("Max latency: " + list.getMaxLatency());
		System.out.println("Min latency: " + list.getMinLatency());
	}
	
}
