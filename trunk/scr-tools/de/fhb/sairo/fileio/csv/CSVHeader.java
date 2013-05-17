package de.fhb.sairo.fileio.csv;

import java.util.ArrayList;

public class CSVHeader {

	private ArrayList<String> data;
	
	public CSVHeader() {
		this.data = new ArrayList<String>();
	}
	
	public void add(String pData){
		this.data.add(pData);
	}
	
	public void delete(String pData){
		this.data.remove(pData);
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<(this.data.size()-1);i++){
			sb.append(this.data.get(i)+";");
		}
		sb.append(this.data.get(this.data.size()-1));
		return sb.toString();
	}

}
