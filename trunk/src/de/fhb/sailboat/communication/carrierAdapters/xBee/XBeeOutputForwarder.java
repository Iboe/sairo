/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;


/**
 * Wrapper class to wrap the XBee API functions for sending data by a common {@link OutputStream}.<br>
 * 
 * @author Michael Kant
 *
 */
public class XBeeOutputForwarder extends OutputStream {

	private static final Logger LOG = LoggerFactory.getLogger(XBeeOutputForwarder.class);
	
	/**
	 * Reference to the {@link XBee} object to pass the data to.
	 */
	private XBee xBee;
	
	/**
	 * Destination address of the desired other end.
	 */
	private XBeeAddress64 destinationAddress;
	
	/**
	 * Internal buffer for the data to be sent.
	 */
	private List<Integer> buffer;
	
	/**
	 * Initialization constructor. <br>
	 * 
	 * @param xbee The {@link XBee} reference to use for sending data. 
	 * @param destinationAdr The destination address to send the data to.
	 */
	public XBeeOutputForwarder(XBee xbee, String destinationAdr) {
	
		this.buffer=new LinkedList<Integer>();
		this.destinationAddress=new XBeeAddress64(destinationAdr);
		this.xBee=xbee;
		
	}
	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
	
		synchronized(buffer){
			
			buffer.add(b);
		}
	}
	
	@Override
	 public void flush() throws IOException {
		
		
		int[] bytes;
		int i=0;
		
		synchronized (buffer) {
		
			if(buffer.size() > 0){
				
				bytes=new int[buffer.size()];
				for(int b : buffer)
					bytes[i++]=b;
				
				buffer.clear();
				System.out.print("Attempt to send: ");
				for(int b:bytes) System.out.print(" " + (char)b);
				System.out.println();
				
				try {
					
					xBee.sendAsynchronous(new ZNetTxRequest(destinationAddress, bytes));
				} 
				catch (XBeeException e) {
					
					LOG.warn("XBeeException caused on send attempt.");
					throw new IOException("XBeeException caused on send attempt.",e);
				}
			}
		}
	 }
}
