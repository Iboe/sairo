/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public class CommuncationClient extends CommunicationBase {

	private static final Logger LOG = LoggerFactory.getLogger(CommuncationClient.class);
	
	private Socket destination;
	private String destinationIP;
	private int destinationPort;
	
	
	
	public CommuncationClient(String destIP, int port){
		
		destinationIP=destIP;
		destinationPort=port;
		destination=null;
	}
	
	@Override
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		if(!isConnected()){
			
			try {
				
				destination=new Socket("destinationIP",destinationPort);
				LOG.debug("Connecting to "+destinationIP+":"+destinationPort+"...");
					
				
				setSender(new DataOutputStream(destination.getOutputStream()));
				setReceiver(new DataInputStream(destination.getInputStream()));
				bInitialized=super.initialize();
				LOG.info("Initialized successful.");
			}
			catch (UnknownHostException e) {
				
				LOG.error("Invalid host - "+destinationIP+": "+e.getMessage());
				
				destination=null;
				
			} catch (IOException e) {
				
				LOG.error("Failed to initialize: "+e.getMessage());
				destination=null;
			}
		}
		
		return bInitialized;
	}
	
	@Override
	public void shutdown(){
		
		LOG.debug("Shutting down...");
		
		setSender(null);
		setReceiver(null);
		if(destination != null){
			
			try {
				destination.close();
				LOG.debug("Remote connection closed.");	
			} 
			catch (IOException e) {
				
				LOG.error("Error closing remote connection: " +e.getMessage());
				e.printStackTrace();
			}
		}
		
		super.shutdown();
	}
	
	@Override
	public boolean isConnected() {
		
		return destination != null && !(destination.isClosed() || destination.isInputShutdown() || destination.isOutputShutdown());
	}

}
