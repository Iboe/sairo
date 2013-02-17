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
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Receiver module for the {@link WindModel}'s {@link Wind} data.<br>
 * The wind direction and speed are being read from the input stream and put into a new {@link Wind} object.<br>
 * This is a passive module.
 * 
 * @author Michael Kant
 *
 */
public class WindReceiver implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(WindReceiver.class);
	
	private WorldModel worldModel;
	
	public WindReceiver() {
	
		worldModel=WorldModelImpl.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#receivedObject(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int dir=0,speed=0;
		Wind newWind;
		
		//Reading the Wind direction and speed, encoded as compact indices.
		dir=CommunicationBase.readCompactIndex(stream);
		speed=CommunicationBase.readCompactIndex(stream);
		
		//Creating a new GPS object with the decoded values.
		newWind=new Wind(dir,((double)(speed))/100);
		worldModel.getWindModel().setWind(newWind);
	}

	/* Passive modules got no cycles to skip.
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 */
	@Override
	public boolean skipNextCycle() {
		
		return false;
	}

	/* This module just receives.
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		LOG.warn("An object was requested to be sent, which is not supported.");
	}


	/* Passive modules got no transmission intervals.
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		return 0;
	}

	/* Priority is just relevant for transmitting modules.
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		
		return 0;
	}
	

	@Override
	public void connectionReset() {
		// TODO Auto-generated method stub

	}

}
