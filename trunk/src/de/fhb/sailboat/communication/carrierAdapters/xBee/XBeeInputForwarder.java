/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters.xBee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;

/**
 * @author KI-Lab
 *
 */
public class XBeeInputForwarder extends InputStream implements PacketListener {

	private static final Logger LOG = LoggerFactory.getLogger(XBeeInputForwarder.class);
	
	private XBee xBee;
	private Stack<Integer> buffer;
	
	public XBeeInputForwarder(XBee xbee){

		this.xBee=xbee;
		this.buffer=new Stack<Integer>();
		
		if(xBee != null){
			
			xbee.addPacketListener(this);
		}
	}
	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	@Override
	public int read() throws IOException {
		
		int b=-1;
		synchronized(this){
			
			do{
				
				synchronized(buffer){
				
					if(buffer.size() > 0)
						b=buffer.pop();
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
		}
		return b;
	}
	
	@Override
	public void processResponse(XBeeResponse resp) {
		
		System.out.println("GOT RESPONSE: "+resp);
		synchronized(buffer){
		
			int[] bytes=resp.getRawPacketBytes();
			
			if(bytes.length > 0){
				
				for(int b : bytes)
					buffer.push(b);
				this.notify();
			}
		}
	}
}
