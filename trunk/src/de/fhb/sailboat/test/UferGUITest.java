/**
 * 
 */
package de.fhb.sailboat.test;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPClient;
import de.fhb.sailboat.communication.clientModules.CompassReceiver;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.communication.clientModules.MissionTransmitter;
import de.fhb.sailboat.communication.clientModules.WindReceiver;
import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.gui.GUI;
import de.fhb.sailboat.start.PropertiesInitializer;

/**
 * Class with main method to start the Ufer GUI along with a communication client end node separately. 
 * @author Michael Kant
 *
 */
public class UferGUITest {

	/**
	 * Entry point of the application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) {
		
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		//CommunicationBase client=new CommTCPClient("176.16.18.18", 6699); // Client-Modus - WLAN: FBI-Robot
		CommunicationBase client=new CommTCPClient("192.168.178.3", 6699); // Client-Modus - WLAN: Ad hoc
		//CommunicationBase client=new CommTCPServer(6699); // Server-Modus
		TransmissionModule mTrans=new MissionTransmitter(client);
		
		client.registerModule(new GPSReceiver());
		client.registerModule(new CompassReceiver());
		client.registerModule(new WindReceiver());
		client.registerModule(mTrans);
		
		
		if(!client.initialize())
			System.out.println("Error connecting to BOAT!");
		
		GUI view=new GUI((Planner)mTrans);
		view.setVisible(true);

	}

}
