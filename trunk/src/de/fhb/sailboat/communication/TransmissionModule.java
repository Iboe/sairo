/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public interface TransmissionModule {

	public void objectReceived(DataInputStream stream) throws IOException;
	public void requestObject(DataOutputStream stream) throws IOException;
	
	public int getTransmissionInterval();
}
