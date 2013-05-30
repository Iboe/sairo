package de.fhb.sailboat.test;

import java.io.IOException;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import de.fhb.sailboat.communication.carrierAdapters.xBee.XBeeInputForwarder;
import de.fhb.sailboat.communication.carrierAdapters.xBee.XBeeOutputForwarder;
import de.fhb.sailboat.start.PropertiesInitializer;
import de.fhb.sailboat.wificonnection.IwifiXbee;
import de.fhb.sailboat.wificonnection.WifiXbee;

/**
 * Testing the wifi XBee API wrapper classes {@link XBeeInputForwarder} for receiving data<br>
 * and {@link XBeeOutputForwarder} for sending data over an XBee radio modem connection.<br><br>
 * 
 * This class also contains the test method {@link #xBeeAPITest()} for testing the deprecated classes of the package de.fhb.sailboat.wificonnection.
 * 
 * @author Michael Kant
 */
public class XBeeTest {

	private static final String CONFIG_FILE = "config.properties";

	/**
	 * Entry point of the test application.
	 * 
	 * @param args No parameters expected.
	 * @throws InterruptedException
	 * @throws XBeeException
	 * @throws IOException 
	 */
	public static void main(String[] args){
		
		XBeeOutputForwarderTest();
	}
	
	/**
	 * Tests the {@link XBeeOutputForwarder} by sending the string 'Hallo Welt!!'.
	 */
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
				e.printStackTrace();
			}
		} catch (XBeeException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Tests the {@link XBeeInputForwarder} by trying to receive an input.
	 */
	public static void XBeeInputForwarderTest(){
		
		XBee xb=new XBee();
		try {
			xb.open("COM6", 9600);
		
			XBeeInputForwarder in=new XBeeInputForwarder(xb);
			
			System.out.println("Reading attempt...");
			
			int b=0;
			while((b=in.read()) != -1)
				System.out.println("RECEIVED: "+(char)b+"("+b+")");
		
			
			in.close();
		} catch (XBeeException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		xb.close();
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
