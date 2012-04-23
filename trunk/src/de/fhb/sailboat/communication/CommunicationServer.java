/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public class CommunicationServer extends CommunicationBase{

	private static final Logger LOG = LoggerFactory.getLogger(CommunicationServer.class);
	
	private ServerSocket listenServer;
	private Socket connectedClient;
	
	private Thread acceptThread;
	
	public CommunicationServer(int listenPort){
		
		try {
			
			listenServer=new ServerSocket(listenPort);
			connectedClient=null;
			acceptThread=null;
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		if(acceptThread == null){
			
			(acceptThread=new AcceptThread()).start();
			bInitialized=super.initialize();
		}
		return bInitialized;
	}
	
	public void shutdown(){
		
		LOG.debug("Shutting down...");
		
		super.shutdown();
		
		if(acceptThread != null){
			
			try {
				acceptThread.interrupt();
				LOG.debug("Connected client server closed.");
				
			} 
			catch (SecurityException e) {
				
				e.printStackTrace();
				LOG.error("Error closing accept thread: " +e.getMessage());
			}
			finally {
				acceptThread=null;
			}
		}
		
		if(connectedClient != null){
			
			try {
				setSender(null);
				setReceiver(null);
				connectedClient.close();
				LOG.debug("Connected client server closed.");
			} 
			catch (IOException e) {
				
				e.printStackTrace();
				LOG.error("Error closing client connection: " +e.getMessage());
			}
			finally {
				connectedClient=null;
			}
		}
		
		if(listenServer != null){
			
			try {
				listenServer.close();
				LOG.debug("Listen server closed.");
			} 
			catch (IOException e) {
				
				e.printStackTrace();
				LOG.error("Error closing server: " +e.getMessage());
			}
			finally {
				listenServer=null;
			}
		}	
		
		
	}
	
	@Override
	public boolean isConnected(){
		
		return connectedClient != null && !(connectedClient.isClosed() || connectedClient.isInputShutdown() || connectedClient.isOutputShutdown());
	}
	
	private class AcceptThread extends Thread {
		
		public void run()
		{
			Socket client;
			while(!isInterrupted()){

				try {
					client=listenServer.accept();
					if(client != null){
						
						if(connectedClient == null ){
							
							connectedClient=client;
							LOG.debug("Accepted incoming client from: "+connectedClient.getRemoteSocketAddress().toString());
							setSender(new DataOutputStream(connectedClient.getOutputStream()));
							setReceiver(new DataInputStream(connectedClient.getInputStream()));
							
						}
						else{
							
							LOG.debug("Rejected second incoming client from: "+client.getRemoteSocketAddress().toString());
							client.close();
						}
					}
					
				} 
				catch (SocketException e){
					
					
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			LOG.debug("Accept Thread finished.");
		}
	}
}
