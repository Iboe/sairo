package de.fhb.sailboat.wificonnection;

import com.rapplogic.xbee.api.XBeeException;

public interface IwifiXbee {

	public void OpenXbee() throws XBeeException, InterruptedException;
	
}
