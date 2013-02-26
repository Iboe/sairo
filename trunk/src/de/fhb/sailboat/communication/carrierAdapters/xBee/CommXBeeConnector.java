/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;


import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeException;

import de.fhb.sailboat.communication.CommunicationBase;

/**
 * Provides an XBee radio modem connection end point as {@link CommunicationBase}.<br>
 * The data stream wrappers to the XBee API are implemented by {@link XBeeInputForwarder} and {@link XBeeOutputForwarder}.<br>
 * 
 * @author Michael Kant
 *
 */
public class CommXBeeConnector extends CommunicationBase{

	private static final Logger LOG = LoggerFactory.getLogger(CommXBeeConnector.class);
	
	/**
	 * COM port to connect to.
	 */
	private String comPort;
	
	/**
	 * Baud rate to be used for the desired COM port. 
	 */
	private int baudRate;

	private String destinationAddress;
	/**
	 * Reference to the {@link XBee} object, which provides the interface for bidirectional data transmission.
	 */
	private XBee xBee;
	
	/**
	 * Initialization constructor.<br>
	 * 
	 * @param comPort The COM port to connect to.
	 * @param baudRate The baud rate to use.
	 * @param destinationAddress the destination address of the remote end point. A 64 bit value as a string in the following format (hexadecimal): xx xx xx xx xx xx xx xx 
	 */
	public CommXBeeConnector(String comPort, int baudRate, String destinationAddress){
		
		this.comPort=comPort;
		this.baudRate=baudRate;
		this.destinationAddress=destinationAddress;
		this.xBee=null;
	}
	
	/**
	 * Creates a new {@link XBee} object and opens a connection to the desired COM port.<br>
	 * Then it provides the respective input and output wrapper objects to the {@link CommunicationBase as base} class.
	 * 
	 * @return true, if the {@link XBee#open(String, int)} method didn't cause an {@link XBeeException}, otherwise false.
	 */
	@Override
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		if(xBee == null){
			
			xBee=new XBee();
			
			try {
				xBee.open(comPort, baudRate);
				setReceiver(new DataInputStream(new XBeeInputForwarder(xBee)));
				setSender(new DataOutputStream(new XBeeOutputForwarder(xBee,destinationAddress)));
				bInitialized=true;
			} 
			catch (XBeeException e) {
				
				LOG.warn("XBeeException on opening XBee: "+e.getMessage());
			}
		}
		return bInitialized ? super.initialize() : false;
	}
	/**
	 * Gives information about the connectivity state of the XBee modem.
	 * @see de.fhb.sailboat.communication.CommunicationBase#isConnected()
	 * 
	 * @return true, if there's an XBee object present and its {@link XBee#isConnected()} method returns true, otherwise this method returns false.
	 */
	@Override
	public boolean isConnected() {
		
		return xBee != null && xBee.isConnected();
	}

	/**
	 * Closing the XBee connection, if one was established.
	 * @see de.fhb.sailboat.communication.CommunicationBase#closeConnection()
	 * 
	 * @return true, if there was a connection established and it was closed, otherwise false. 
	 */
	@Override
	public boolean closeConnection() {
		
		boolean bClosed=false;
		
		if(isConnected()){
			
			xBee.close();
			xBee=null;
			bClosed=true;
		}
		
		return bClosed;
	}
}
