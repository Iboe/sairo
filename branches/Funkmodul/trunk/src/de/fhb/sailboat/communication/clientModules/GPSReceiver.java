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
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Receiver module for the {@link GPSModel}'s {@link GPS} data.<br>
 * The latitude and longitude is being read from the input stream and put into a new {@link GPS} object.<br>
 * This is a passive module.
 * 
 * @author Michael Kant
 *
 */
public class GPSReceiver implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(GPSReceiver.class);
	
	private WorldModel worldModel;
	
	public GPSReceiver() {
	
		worldModel=WorldModelImpl.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 * 
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		int lon=0,lat=0,sat=0;
		GPS newGPS;
		
		lat=CommunicationBase.readCompactIndex(stream);
		lon=CommunicationBase.readCompactIndex(stream);
		sat=CommunicationBase.readCompactIndex(stream);
		
		newGPS=new GPS(((double)(lat))/10000,((double)(lon))/10000,sat);
		worldModel.getGPSModel().setPosition(newGPS);
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 */
	@Override
	public boolean skipNextCycle() {
		
		return false;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		LOG.warn("An object was requested to be sent, which is not supported.");

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void connectionReset() {
		// TODO Auto-generated method stub
		
	}

}