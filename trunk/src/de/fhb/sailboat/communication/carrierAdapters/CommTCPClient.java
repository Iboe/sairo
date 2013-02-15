/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;

/**
 * Provides a TCP client endpoint as {@link CommunicationBase}.<br>
 * 
 * @author Michael Kant
 *
 */
public class CommTCPClient extends CommunicationBase {

	private static final Logger LOG = LoggerFactory.getLogger(CommTCPClient.class);
	
	private Socket destination;
	private String destinationIP;
	private int destinationPort;
	
	
	/**
	 * Initialization constructor.<br>
	 * Creates a new {@link CommTCPClient} object with the given destination IP address and port.
	 * 
	 * @param destIP IP address of the remote host.
	 * @param port Port of the remote host.
	 */
	public CommTCPClient(String destIP, int port){
		
		destinationIP=destIP;
		destinationPort=port;
		destination=null;
	}
	
	/**
	 * Opens a socket to the given destination IP and port.
	 */
	@Override
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		LOG.debug("Initializing..");
		if(!isConnected()){
			
			try {
				
				destination=new Socket(destinationIP,destinationPort);
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
		else
			LOG.warn("Initialization skipped.");
		
		return bInitialized;
	}
	
	/**
	 * Closes the socket along with the remote connection.
	 */
	@Override
	public void shutdown(){
		
		LOG.debug("Shutting down...");
		
		super.shutdown();
		
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
	}
	
	@Override
	public boolean isConnected() {
		
		return destination != null && !(destination.isClosed() || destination.isInputShutdown() || destination.isOutputShutdown());
	}

	@Override
	public boolean closeConnection(){
		
		boolean bClosed=false;
		
		if(isConnected()){
		
			try {
				destination.close();
				destination=null;
				bClosed=true;
			} catch (IOException e) {
				
			}
		}	
		return bClosed;
	}
}
