package de.fhb.sailboat.wificonnection;

import java.io.IOException;

import com.rapplogic.xbee.api.XBeeException;

public interface IwifiXbee {

	
	public void read();
	
	public void initializeXbee();
	
	public void sendDataXbee(String data) throws XBeeException,
	InterruptedException, IOException;
	
}
