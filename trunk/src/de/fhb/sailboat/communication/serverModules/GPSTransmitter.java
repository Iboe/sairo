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
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Transmitter module for the {@link GPSModel}'s {@link GPS} data.<br>
 * The latitude and longitude of a {@link GPS} object is being transformed into integer values<br>
 * and written to the output stream as compact indices.<br>
 * This is an active module with a transmission interval of 1000ms.
 * 
 * @author Michael Kant
 *
 */
public class GPSTransmitter implements TransmissionModule {

	private static final Logger LOG = LoggerFactory.getLogger(GPSTransmitter.class);
	
	private GPS lastGPS;
	private WorldModel worldModel;
	
	public GPSTransmitter() {
	
		lastGPS=null;
		worldModel=WorldModelImpl.getInstance();
	}
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 */
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		
		LOG.warn("Received an unexpected incoming transmission.");

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#skipNextCycle()
	 */
	@Override
	public boolean skipNextCycle() {
		
		return lastGPS == worldModel.getGPSModel().getPosition();
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#requestObject(java.io.DataOutputStream)
	 */
	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		
		int lon=0,lat=0;
		GPS current=worldModel.getGPSModel().getPosition();
		
		if(current != null){
			
			lat=(int)(current.getLatitude()*10000);
			lon=(int)(current.getLongitude()*10000);	
		}
		
		CommunicationBase.writeCompactIndex(stream, lat);
		CommunicationBase.writeCompactIndex(stream, lon);
		
		lastGPS=current;
	}

	/**
	 * Setting an interval of 1000ms
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		return 1000;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		
		return 0;
	}
	
	/**
	 * Setting the last sent {@link GPS} to null, so it will transmit the new value when a new connection was established.
	 */
	@Override
	public void connectionReset() {
		
		lastGPS=null;
	}

}
