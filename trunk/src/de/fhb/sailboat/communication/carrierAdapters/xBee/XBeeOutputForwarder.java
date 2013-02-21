/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;


/**
 * @author Michael Kant
 *
 */
public class XBeeOutputForwarder extends OutputStream {

	private static final Logger LOG = LoggerFactory.getLogger(XBeeOutputForwarder.class);
	
	private XBee xBee;
	private XBeeAddress64 destinationAddress;
	private List<Integer> buffer;
	
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
	
		buffer.add(b);
	}
	
	@Override
	 public void flush() throws IOException {
		
		
		int[] bytes;
		int i=0;
		
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
