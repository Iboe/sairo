package de.fhb.sailboat.wificonnection;

import java.io.IOException;

import com.rapplogic.xbee.api.XBeeException;

public interface IwifiXbee {

	/*
	 * Receives data from another Xbee
	 */
	public void read();
	
	/*
	 * To Start the Xbee Module
	 */
	public void initializeXbee();
	
	/*
	 * Sends Data to other xbee 
	 */
	public void sendDataXbee(String data) throws XBeeException,
	InterruptedException, IOException;
	
}
