/**
 * 
 */
package de.fhb.sailboat.communication.carrierAdapters;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;

/**
 * Provides a TCP server endpoint as {@link CommunicationBase}.<br>
 * Although it defines a TCP server, it can be interpreted as peer to peer connection because just one client is accepted at a time.<br>
 * Further connection attempts are discarded.
 * 
 * @author Michael Kant
 *
 */
public class CommTCPServer extends CommunicationBase {

	/**
	 * The port for listen to.
	 */
	public static final int LISTEN_PORT = Integer.parseInt(System.getProperty(
			CommTCPServer.class.getSimpleName() + ".listenPort"));
			
	private static final Logger LOG = LoggerFactory.getLogger(CommTCPServer.class);
	
	/**
	 * ServerSocket which listens on the given listenPort.
	 */
	private ServerSocket listenServer;
	
	/**
	 * Client socket that respresents the connected client.
	 */
	private Socket connectedClient;
	
	/**
	 * Thread that listens for incoming connections and accepts or rejects them.
	 */
	private Thread acceptThread;
	
	/**
	 * Initialization constructor.<br>
	 * Creates a {@link ServerSocket}, listening on the given port.
	 * @param listenPort The port to listen on.
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
	 * @return true, if the {@link ServerSocket} {@link AcceptThread} was started successfully, otherwise false.
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
	 * @return true, if a client connection is established, otherwise false.
	 */
	@Override
	public boolean isConnected(){
		
		return connectedClient != null && !(connectedClient.isClosed() || connectedClient.isInputShutdown() || connectedClient.isOutputShutdown());
	}
	
	/**
	 * Terminates the connection to the remote client.
	 * @return true, if an established connection was closed successfully, otherwise false.
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
	
	/**
	 * The class that implements the accept thread. <br>
	 * It starts an internal thread, that listens for incoming connection requests <br> 
	 * and accepts or rejects them.
	 * @author Michael Kant
	 *
	 */
	private class AcceptThread extends Thread {
		
		/**
		 * Run method where the code of the accept thread is being executed.
		 */
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
					
					LOG.warn("SocketException was caused: "+e.getMessage());
				}
				catch (IOException e) {
					
					LOG.warn("IOException was caused: "+e.getMessage());
				}
			}
			LOG.debug("Accept Thread finished.");
		}
	}
}
