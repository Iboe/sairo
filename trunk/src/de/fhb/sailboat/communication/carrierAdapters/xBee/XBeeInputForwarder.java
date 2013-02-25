/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;

/**
 * Wrapper class to wrap the XBee API events for receiving data by a common {@link IntputStream}.<br>
 * 
 * @author Michael Kant
 *
 */
public class XBeeInputForwarder extends InputStream implements PacketListener {

	private static final Logger LOG = LoggerFactory.getLogger(XBeeInputForwarder.class);
	
	/**
	 * Reference to the {@link XBee} object to get the data from.
	 */
	private XBee xBee;
	
	/**
	 * Internal buffer for the received data.
	 */
	private BlockingQueue<Integer> buffer;
	
	/**
	 * Initialization constructor.
	 * 
	 * @param xbee The {@link XBee} reference to use for receiving data.
	 */
	public XBeeInputForwarder(XBee xbee){

		this.xBee=xbee;
		this.buffer=new LinkedBlockingQueue<Integer>();
		
		if(xBee != null){
			
			xbee.addPacketListener(this);
		}
	}
	/** 
	 * Overriding the read function to read out of the internal byte buffer,<br> 
	 * which is filled by the {@link #processResponse(XBeeResponse)} function of the XBee API.
	 * 
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		
		int b=-1;
		
		try {
			b=buffer.take();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		/*
		synchronized(this){
			
			do{
				
				synchronized(buffer){
				
					if(buffer.size() > 0)
						b=buffer.poll();
				}
				if(b == -1){
					
					try {
						
						LOG.debug("Buffer is empty.. waiting.");
						wait();
						LOG.debug("Buffer was filled.. continuing.");
					}
					catch (InterruptedException e) {
						
						e.printStackTrace();
						break; //aborting the loop if the thread was interrupted
					}
				}
			} 
			while(b == -1);
		}*/
		return b;
	}
	
	@Override
	public void processResponse(XBeeResponse resp) {
		
		if(resp.getApiId() == ApiId.ZNET_RX_RESPONSE){
			
			LOG.debug("GOT RESPONSE: "+resp);
		
			int[] bytes=((ZNetRxResponse) resp).getData();
			
			
			try {
				
				for(Integer b : bytes)
					buffer.put(b);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			synchronized(buffer){
				if(bytes.length > 0){
					
					for(Integer b : bytes)
						buffer.add(b);
					
					synchronized(this){
						
						this.notify();
					}
				}
			}*/
		}
	}
}
