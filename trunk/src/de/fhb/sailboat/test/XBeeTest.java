package de.fhb.sailboat.test;

import java.io.IOException;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import de.fhb.sailboat.communication.carrierAdapters.xBee.XBeeInputForwarder;
import de.fhb.sailboat.communication.carrierAdapters.xBee.XBeeOutputForwarder;
import de.fhb.sailboat.start.PropertiesInitializer;
import de.fhb.sailboat.wificonnection.IwifiXbee;
import de.fhb.sailboat.wificonnection.WifiXbee;

public class XBeeTest {

	private static final String CONFIG_FILE = "config.properties";

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws XBeeException
	 * @throws IOException 
	 */
	public static void main(String[] args){
		
		XBeeOutputForwarderTest();
	}
	
	public static void XBeeOutputForwarderTest(){
		
		XBee xb=new XBee();
		try {
			xb.open("COM6", 9600);
		
			XBeeOutputForwarder out=new XBeeOutputForwarder(xb, "0 13 a2 0 40 33 f1 fb");
			
			String str="Hallo  welt!!";
			
			try {    
				
				System.out.println("Writing: "+str);
				for(char c: str.toCharArray())			
					out.write(c);
				
				System.out.println("Flushing..");
				out.flush();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (XBeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	public static void XBeeInputForwarderTest(){
		
		XBee xb=new XBee();
		try {
			xb.open("COM6", 9600);
		
			XBeeInputForwarder in=new XBeeInputForwarder(xb);
			
			System.out.println("Reading attempt...");
			
			int b=0;
			while((b=in.read()) != -1)
				System.out.println("RECEIVED: "+(char)b+"("+b+")");
		
			
		} catch (XBeeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void xBeeAPITest() throws XBeeException,InterruptedException, IOException{
		
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		IwifiXbee xbee = new WifiXbee();
		xbee.initializeXbee();
		xbee.sendDataXbee("bla");
	}

	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws XBeeException
	 * @throws IOException 
	 
	public static void main(String[] args) throws XBeeException,
			InterruptedException, IOException {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		IwifiXbee xbee = new WifiXbee();
		xbee.initializeXbee();
		xbee.read();
	}
	*/
	
}
