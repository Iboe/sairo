package de.fhb.sairo.fileio.csv;

import java.util.ArrayList;

/***
 * This class represents an CSV entry which means an row with his cells in the CSV data table
 * @author Tobias Koppe
 *
 */
public class CSVEntry {

	private ArrayList<CSVData> data;
	
	public CSVEntry() {
		this.data = new ArrayList<CSVData>();
	}
	
	public void add(CSVData pData){
		this.data.add(pData);
	}
	
	//private boolean checkTimestamp();

	public ArrayList<CSVData> getData() {
		return data;
	}

	public void setData(ArrayList<CSVData> data) {
		this.data = data;
	}

}
