/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public interface TransmissionModule {

	public void objectReceived(DataInputStream stream);
	public void requestObject(DataOutputStream stream);
	
	public int getTransmissionInterval();
}
