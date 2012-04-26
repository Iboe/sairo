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
	
	private WorldModel worldModel;
	
	public CompassReceiver() {
	
		worldModel=WorldModelImpl.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#objectReceived(java.io.DataInputStream)
	 * 
	 */
	@Override
	public void objectReceived(DataInputStream stream) throws IOException {
		
		int yaw=0;
		Compass newCompass;
		
		yaw=CommunicationBase.readCompactIndex(stream);
		
		newCompass=new Compass(((double)(yaw))/10,0,0);
		worldModel.getCompassModel().setCompass(newCompass);
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

}
