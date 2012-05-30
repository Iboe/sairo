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
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
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
		
		dir=CommunicationBase.readCompactIndex(stream);
		speed=CommunicationBase.readCompactIndex(stream);
		
		newWind=new Wind(dir,((double)(speed))/100);
		worldModel.getWindModel().setWind(newWind);
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
	 * @see de.fhb.sailboat.communication.TransmissionModule#connectionReset()
	 */
	@Override
	public void connectionReset() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		
		return 0;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getPriority()
	 */
	@Override
	public int getPriority() {
		
		return 0;
	}

}
