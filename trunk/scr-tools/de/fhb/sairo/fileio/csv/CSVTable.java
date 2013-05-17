package de.fhb.sairo.fileio.csv;

import java.util.ArrayList;

public class CSVTable {

	private ArrayList<CSVEntry> data;
	
	public CSVTable() {
		this.data = new ArrayList<CSVEntry>();
	}
	
	public void add(CSVEntry pData){
		this.data.add(pData);
	}

}
