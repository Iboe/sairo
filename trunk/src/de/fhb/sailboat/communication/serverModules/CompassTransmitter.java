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
 * The yaw/azimuth a {@link Compass} object is being transformed into an integer value<br>
 * and written to the output stream as compact index.<br>
 * This is an active module with a transmission interval of 500ms.
 * 
 * @author Michael Kant
 *
 */
public class CompassTransmitter implements TransmissionModule{

	private static final Logger LOG = LoggerFactory.getLogger(CompassTransmitter.class);
	
	private Compass lastCompass;
	private WorldModel worldModel;
	
	public CompassTransmitter() {
	
		lastCompass=null;
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

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		return 500;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		
		return 0;
	}
}
