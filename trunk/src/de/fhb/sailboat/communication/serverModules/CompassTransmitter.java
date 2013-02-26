/**
 * 
 */
package de.fhb.sailboat.communication.serverModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Transmitter module for the {@link CompassModel}'s {@link Compass} data.<br>
 * The yaw/azimuth value of the recent {@link CompassModel}'s {@link Compass} object is being transformed into an integer value<br>
 * and written to the output stream as compact index.<br>
 * This is an active module with a transmission interval of 500ms.
 * 
 * @author Michael Kant
 *
 */
public class CompassTransmitter implements TransmissionModule{

	private static final Logger LOG = LoggerFactory.getLogger(CompassTransmitter.class);
	
	/**
	 * The last {@link Compass} object that was sent.
	 */
	private Compass lastCompass;
	
	/**
	 * Reference to the global world model.
	 */
	private WorldModel worldModel;
	
	/**
	 * Default constructor.
	 */
	public CompassTransmitter() {
	
		lastCompass=null;
		worldModel=WorldModelImpl.getInstance();
	}
	
	/** 
	 * Does nothing.
	 * @param stream The {@link DataInputStream} to read from.
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		LOG.warn("Received an unexpected incoming transmission.");

	}

	/**
	 * Skipping the transmission cycle if the {@link Compass} object in the {@link WorldModel} wasn't changed.
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 * 
	 * @return true, if we're about to transmit the exact same {@link Compass} data again, otherwise false.
	 */
	@Override
	public boolean skipNextCycle() {
		
		return lastCompass == worldModel.getCompassModel().getCompass();
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		int yaw=0;
		Compass current=worldModel.getCompassModel().getCompass();
		
		
		if(current != null){
			
			yaw=(int)(current.getAzimuth()*10);	
		}
		CommunicationBase.writeCompactIndex(stream, yaw);
		
		lastCompass=current;
	}

	/**
	 * Setting an interval of 500ms
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		return 500;
	}

	/*
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		
		return 0;
	}
	
	/**
	 * Setting the last sent {@link Compass} to <code>null</code>, so it will transmit the new value when a new connection was established.
	 */
	@Override
	public void connectionReset() {
		
		lastCompass=null;
	}
}
