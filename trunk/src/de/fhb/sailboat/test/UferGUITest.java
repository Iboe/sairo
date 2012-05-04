/**
 * 
 */
package de.fhb.sailboat.test;

import de.fhb.sailboat.communication.CommTCPClient;
import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.clientModules.CompassReceiver;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.test.Initializier.PropertiesInitializer;
import de.fhb.sailboat.ufer.prototyp.View;

/**
 * Class with main method to start the Ufer GUI along with a communication client end node separately. 
 * @author Michael Kant
 *
 */
public class UferGUITest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
		
		CommunicationBase client=new CommTCPClient("127.0.0.1", 6699);
		client.registerModule(new GPSReceiver());
		client.registerModule(new CompassReceiver());
		
		if(!client.initialize())
			System.out.println("Error connecting to BOAT!");
		
		View view=new View(null);
		view.setVisible(true);

	}

}
