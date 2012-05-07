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
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * @author Michael Kant
 *
 */
public class WindTransmitter implements TransmissionModule {

private static final Logger LOG = LoggerFactory.getLogger(WindTransmitter.class);
	
	private Wind lastWind;
	private WorldModel worldModel;
	
	public WindTransmitter() {
		
		lastWind=null;
		worldModel=WorldModelImpl.getInstance();
	}
	
	
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#receivedObject(java.io.DataInputStream)
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

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#connectionReset()
	 */
	@Override
	public void connectionReset() {
		
		lastWind=null;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.TransmissionModule#getTransmissionInterval()
	 */
	@Override
	public int getTransmissionInterval() {
		// TODO Auto-generated method stub
		return 600;
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
