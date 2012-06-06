/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface declaring a {@link TransmissionModule}. Clases implementing the {@link TransmissionModule} are responsible<br>
 * for defining a serialization and deserialization algorithm for a certain type of data.<br>
 * Transmission modules are to be registered at a {@link CommunicationBase} instance.<br>
 * Since {@link TransmissionModule}s on the server and client usually have different tasks to perform,<br>
 * there must be created a pair of {@link TransmissionModule} classes; one for the client and one for the server.<br>
 * If just one {@link TransmissionModule} type were to be added at the server and the client, it would result in a cyclic<br>
 * ping-pong like transmission, which is usually not intended. <br> 
 * <b>In order to ensure a successful transmission, all {@link TransmissionModule} pairs must be registered at the<br>
 * end-nodes (server and client) in the SAME order!!</b><br>
 * 
 *   
 * @author Michael Kant
 *
 */
public interface TransmissionModule {

	/**
	 * Called on a registered {@link TransmissionModule}, if the related {@link CommunicationBase} received a transmission<br>
	 * that's intended for this {@link TransmissionModule}.<br>
	 * The given {@link TransmissionModule} is supposed to decode the data on its own.
	 * 
	 * @param stream Stream that provides the data.
	 * @throws IOException IOException that's back-propagated to the underlying {@link CommunicationBase}, if this {@link TransmissionModule} doesn't handle the exception itself.
	 */
	public void receivedObject(DataInputStream stream) throws IOException;
	
	/**
	 * This can be used to get the underlying {@link ModuleWorker} to skip the next pending transmission request.<br>
	 * A useful method for bandwidth optimization.
	 * 
	 * @return True if the next cycle shall be skipped, otherwise false.
	 */
	public boolean skipNextCycle();
	
	/**
	 * Called by the underlying {@link ModuleWorker}, if the {@link TransmissionModule}'s transmission interval has been reached
	 * and a new object or bunch of data is supposed to be sent.<br>
	 * The given {@link TransmissionModule} is supposed to encode the data on its own.<br>
	 * Mind, this method is just called if the given transmission interval is greater than 0. (see {@link #getTransmissionInterval()})
	 * 
	 * @param stream Stream that takes the data.
	 * @throws IOException IOException that's back-propagated to the underlying {@link ModuleWorker}, if this {@link TransmissionModule} doesn't handle the exception itself.
	 */
	public void requestObject(DataOutputStream stream) throws IOException;

	/**
	 * Called by the underlying {@link ModuleWorker}, if the connection was closed or interrupted.
	 */
	public void connectionReset();
	
	/**
	 * Defines the transmission interval of this {@link TransmissionModule}.<br>
	 * The return value is interpreted as milliseconds.<br>
	 * Setting a transmission interval of 0 or less will cause the {@link TransmissionModule} to be declared as passive.<br>
	 * That means it can just receive incoming objects by {@link #receivedObject(DataInputStream)} because {@link #requestObject(DataOutputStream)} is never being called automatically.<br>
	 * However, {@link #requestObject(DataOutputStream)} can still be called by explicit request over the communication base.
	 * 
	 * @return The transmission interval value.
	 */
	public int getTransmissionInterval();
	
	/**
	 * Defines the priority of this {@link TransmissionModule}. The higher the priority, the bigger the chance of outgoing data to be preferred on low bandwidth.<br>
	 * The estimated priority is descending with an ascending priority value. e.g. The highest priority is 1. <br>
	 * A priority value of 0 or less is however interpreted as no priority at all, which causes given packets to be added to the very end of the priority chain.<br>
	 * Note: This method is NOT taken into account in the current version of the communication component yet!
	 * 
	 * @return The priority value of the {@link TransmissionModule}
	 */
	public int getPriority();
}
