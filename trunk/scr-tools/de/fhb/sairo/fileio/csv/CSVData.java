package de.fhb.sairo.fileio.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * This class represents an entry in an CSVEntry, means
 * a cell in a row of the csv data table.
 *
 * @author Tobias Koppe
 */
public class CSVData {

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
	
	/** The data. */
	private String[] data;
	
	/**
	 * Instantiates a new CSV data.
	 */
	public CSVData() {
		data = new String[3];
	}
	
	/**
	 * Instantiates a new CSV data.
	 *
	 * @param pHeader the header
	 */
	public CSVData(String pHeader){
		data = new String[2];
		this.data[0]=pHeader;
	}
	
	/**
	 * Instantiates a new CSV data.
	 *
	 * @param pHeader the header
	 * @param pData the data
	 */
	public CSVData(String pHeader, String pData, String pTimestamp){
		data = new String[2];
		this.data[0]=pHeader;
		this.data[1]=pData;
		this.data[2]=pTimestamp;
	}

	
	/**
	 * Adds the header , if existing it will be overwritten!
	 *
	 * @param pHeader the header
	 */
	public void addHeader(String pHeader){
		this.data[0]=pHeader;
	}
	
	/**
	 * Adds the data , if existing it will be overwritten!
	 *
	 * @param pData the data
	 */
	public void addData(String pData, String pTimestamp){
		this.data[1]=pData;
		this.data[2]=pTimestamp;
	}
	
	/**
	 * Adds the header and data , if existing it will be overwritten!
	 *
	 * @param pHeader the header
	 * @param pData the data
	 */
	public void add(String pHeader, String pData, String pTimestamp){
		this.data[0]=pHeader;
		this.data[1]=pData;
		this.data[2]=pTimestamp;
	}
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData(){
		return this.data[1];
	}
	
	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public String getHeader(){
		return this.data[0];
	}
	
	public Date getTimestamp(){
		try {
			return simpleDateFormat.parse(this.data[2]);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
