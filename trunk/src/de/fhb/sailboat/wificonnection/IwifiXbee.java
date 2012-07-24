package de.fhb.sailboat.wificonnection;

import java.io.IOException;

import com.rapplogic.xbee.api.XBeeException;

public interface IwifiXbee {

	/*
	 * Receives data from another Xbee
	 */
	public void read(boolean receiveInt);
	
	/*
	 * To Start the Xbee Module
	 */
	public void initializeXbee();
	
	/*
	 * gets the Xbee Response 
	 * Null if nothing happend
	 */
	public String getResp();
	
	/*
	 * Sends Data to other xbee 
	 */
	public void sendDataXbee(String data) throws XBeeException,
	InterruptedException, IOException;
	
	
	/*
	 * Sends Data to other xbee 
	 */
	public void sendDataXbee(int[] data) throws XBeeException,
	InterruptedException, IOException;
}
