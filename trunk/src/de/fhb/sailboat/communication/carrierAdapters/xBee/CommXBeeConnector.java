/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;

import java.io.DataOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;

import de.fhb.sailboat.communication.CommunicationBase;

/**
 * @author KI-Lab
 *
 */
public class CommXBeeConnector extends CommunicationBase implements PacketListener {

	private static final Logger LOG = LoggerFactory.getLogger(CommXBeeConnector.class);
	
	private String comPort;
	private int baudRate;
	private XBee xBee;
	
	private DataOutputStream out;
	
	public CommXBeeConnector(String comPort, int baudRate){
		
		this.comPort=comPort;
		this.baudRate=baudRate;
		this.xBee=null;
	}
	
	
	@Override
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		xBee=new XBee();
		try {
			xBee.open(comPort, baudRate);	
			xBee.addPacketListener(this);
		} 
		catch (XBeeException e) {
			
			LOG.warn("XBeeException on opening XBee: "+e.getMessage());
		}
		
		return bInitialized;
	}
	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.CommunicationBase#isConnected()
	 */
	@Override
	public boolean isConnected() {
		
		return xBee != null;
	}

	/* (non-Javadoc)
	 * @see de.fhb.sailboat.communication.CommunicationBase#closeConnection()
	 */
	@Override
	public boolean closeConnection() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void processResponse(XBeeResponse arg0) {
		// TODO Auto-generated method stub
		
	}

}
