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
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Receiver module for the {@link WindModel}'s {@link Wind} data.<br>
 * The wind direction and speed are being read from the input stream and put back into a new {@link Wind} object.<br>
 * This is a passive module.
 * 
 * @author Michael Kant
 *
 */
public class WindTransmitter implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(WindTransmitter.class);
	
	/**
	 * The last {@link Wind} object that was sent.
	 */
	private Wind lastWind;
	
	/**
	 * Reference to the global world model.
	 */
	private WorldModel worldModel;
	
	/**
	 * Default constructor.
	 */
	public WindTransmitter() {
		
		lastWind=null;
		worldModel=WorldModelImpl.getInstance();
	}
	
	
	/** 
	 * Does nothing.
	 * @param stream The {@link DataInputStream} to read from.
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 * @throws IOException Won't be thrown.
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {

		LOG.warn("Received an unexpected incoming transmission.");
	}

	/**
	 * Skipping the transmission cycle if the {@link Wind} object in the {@link WorldModel} wasn't changed.
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 * 
	 * @return true, if we're about to transmit the exact same {@link Wind} data again, otherwise false.
	 */
	@Override
	public boolean skipNextCycle() {

		return lastWind == worldModel.getWindModel().getWind();
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		int dir=0,speed=0;
		Wind current=worldModel.getWindModel().getWind();
		
		if(current != null){
			
			dir=current.getDirection();
			speed=(int)(current.getSpeed()*100);
			
		}
		
		CommunicationBase.writeCompactIndex(stream, dir);
		CommunicationBase.writeCompactIndex(stream, speed);
		
		
		lastWind=current;
	}

	/**
	 * Setting the last sent {@link Wind} to <code>null</code>, so it will transmit the new value when a new connection was established.
	 */
	@Override
	public void connectionReset() {
		
		lastWind=null;
	}

	/**
	 * Setting an interval of 600ms.
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 * 
	 * @return The interval of 600ms.
	 */
	@Override
	public int getTransmissionInterval() {
		return 600;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		return 0;
	}

}
