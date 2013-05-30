/**
 * 
 */
package de.fhb.sailboat.communication.clientModules;

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
 * Receiver module for the {@link CompassModel}'s {@link Compass} data.<br>
 * The yaw/azimuth is being read from the input stream and put back into a new {@link Compass} object.<br>
 * This is a passive module.
 * 
 * @author Michael Kant
 *
 */
public class CompassReceiver implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(CompassReceiver.class);
	
	/**
	 * Reference to the global world model.
	 */
	private WorldModel worldModel;
	
	/**
	 * Default constructor.
	 */
	public CompassReceiver() {
	
		worldModel=WorldModelImpl.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 * 
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int yaw=0;
		Compass newCompass;
		
		//Reading the compass yaw value, encoded as compact index.
		//The other compass values are not transmitted for the time being.
		yaw=CommunicationBase.readCompactIndex(stream);
		
		//Creating a new Compass object with the decoded yaw value.
		newCompass=new Compass(((double)(yaw))/10,0,0,0);
		//Adding it to the world model.
		worldModel.getCompassModel().setCompass(newCompass);
	}

	/**
	 * Passive modules got no cycles to skip.
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 * 
	 * @return Always false.
	 */
	@Override
	public boolean skipNextCycle() {
		
		return false;
	}

	/** 
	 * This module just receives.
	 * @param stream The {@link DataOutputStream} to write into.
	 * @throws IOException Shouldn't occur.
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		LOG.warn("An object was requested to be sent, which is not supported.");

	}

	/** 
	 * Passive modules got no transmission intervals.
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 * 
	 * @return Always 0;
	 */
	@Override
	public int getTransmissionInterval() {
		return 0;
	}

	/** 
	 * Priority is just relevant for transmitting modules.
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 * 
	 * @return Always 0;
	 */
	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public void connectionReset() {
		
	}

}
