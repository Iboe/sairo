package de.fhb.sailboat.serial.serialAPI;

import java.util.ArrayList;

/***
 * Klasse zur automatischen Suche nach der COMPort Belegung
 * 
 * @author Tobias Koppe
 * @version 0.1
 */
public class COMPortFinder {
	COMPort aksenComPort;
	COMPort compassComPort;
	
	private ArrayList<COMPort> comPortList;
	
	public COMPortFinder(){
		this.comPortList = new ArrayList<COMPort>();
	}
	
	private void findConnectedComPorts(){
		
	}
	
	private void searchAksenComPort(){
		
	}
}
