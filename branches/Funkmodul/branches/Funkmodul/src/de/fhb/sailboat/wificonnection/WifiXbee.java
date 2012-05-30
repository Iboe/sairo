package de.fhb.sailboat.wificonnection;

import java.io.InputStream;

import javax.xml.bind.ParseConversionEvent;


public class WifiXbee implements IwifiXbee {

	private final int COMPORT = Integer.parseInt(System.getProperty("Xbee.comPort"));
	private final String BAUDRATE = System.getProperty("Xbee.baudrate");
	
	public void OpenXbee()
	{
		
		XbeeCom myldknf = new XbeeCom(COMPORT);
	}

	public void SendData(InputStream DataToSend)
	{
	   
	}
	
	public void ReceiveData()
	{		
		
		
    }
	
}
