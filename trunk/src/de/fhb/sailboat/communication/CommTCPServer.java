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
 * Provides a TCP server endpoint as {@link CommunicationBase}.<br>
 * Although it defines a TCP server, it can be interpreted as peer to peer connection because just one client is accepted at a time.<br>
 * Further connection attempts are discarded.
 * 
 * @author Michael Kant
 *
 */
public class CommTCPServer extends CommunicationBase{

	private static final Logger LOG = LoggerFactory.getLogger(CommTCPServer.class);
	
	private ServerSocket listenServer;
	private Socket connectedClient;
	
	private Thread acceptThread;
	
	/**
	 * Initialization constructor.<br>
	 * Creates a {@link ServerSocket}, listening on the given port.
	 * @param listenPort The port to listen on
	 */
	public CommTCPServer(int listenPort){
		
		try {
			
			listenServer=new ServerSocket(listenPort);
			connectedClient=null;
			acceptThread=null;
		} 
		catch (IOException e) {
			
			LOG.error("Error binding to port "+listenPort+":"+e.getMessage());
		}
	}
	
	/**
	 * Creating and starting the accept thread, that's responsible for accepting/rejecting incoming client connections.
	 * @see AcceptThread#run()
	 */
	@Override
	public boolean initialize(){
		
		boolean bInitialized=false;
		
		if(acceptThread == null){
			
			(acceptThread=new AcceptThread()).start();
			bInitialized=super.initialize();
		}
		return bInitialized;
	}
	
	/**
	 * Stops the server socket along with its accept thread and disconnects the connected client.
	 */
	@Override
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
	
	/**
	 * Checks whether a remote client connection is established.
	 * 
	 */
	@Override
	public boolean isConnected(){
		
		return connectedClient != null && !(connectedClient.isClosed() || connectedClient.isInputShutdown() || connectedClient.isOutputShutdown());
	}
	
	/**
	 * Terminates the connection to the remote client.
	 */
	@Override
	public boolean closeConnection(){
		
		boolean bClosed=false;
		
		if(isConnected()){
		
			try {
				connectedClient.close();
				connectedClient=null;
				bClosed=true;
			} catch (IOException e) {
				
			}
		}	
		return bClosed;
	}
	
	private class AcceptThread extends Thread {
		
		public void run()
		{
			Socket client;
			while(!isInterrupted()){

				try {
					client=listenServer.accept();
					if(client != null){
						
						if(isConnected() ){
							
							LOG.debug("Rejected second incoming client from: "+client.getRemoteSocketAddress().toString());
							client.close();
						}
						else{
							
							connectedClient=client;
							LOG.debug("Accepted incoming client from: "+connectedClient.getRemoteSocketAddress().toString());
							setSender(new DataOutputStream(connectedClient.getOutputStream()));
							setReceiver(new DataInputStream(connectedClient.getInputStream()));
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
