/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public interface TransmissionModule {

	public void objectReceived(ObjectInputStream stream);
	public void requestObject(ObjectOutputStream stream);
	
	public int getTransmissionInterval();
}
