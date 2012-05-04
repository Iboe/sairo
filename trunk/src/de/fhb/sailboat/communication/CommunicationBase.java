/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The class of the communication component.<br>
 * It manages and schedules {@link TransmissionModule}s. <br>
 * The remote communication takes place over the classes DataOutputStream (sender) and DataInputStream (receiver).<br>
 * The underlying protocol of the connection has to be implemented in concrete subclasses.
 * 
 * @author Michael Kant
 *
 */
public abstract class CommunicationBase {

	private static final Logger LOG = LoggerFactory.getLogger(CommunicationBase.class);
	
	/**
	 * Signature that indicates the begin of a data chunk.
	 */
	public static final byte START_SIGNATURE=0x40|0x20;
	
	/**
	 * Maximum number of modules that can be added.
	 */
	public static final int MAX_MODULES=16;
	/**
	 * Maximum re-try attempts on a failed transmission. 
	 */
	public static final int MAX_TRX_COUNT=3;
	
	private TransmissionModule[] modules;
	private ModuleWorker[] workers;
	private int numModules;
	private Thread recvThread;
	
	private DataOutputStream sender = null;
	private DataInputStream receiver = null;
	
	/**
	 * Default constructor.
	 */
	public CommunicationBase(){
		
		modules=new TransmissionModule[MAX_MODULES];
		workers=new ModuleWorker[MAX_MODULES];
		numModules=0;
		recvThread=null;
	}
	
	/**
	 * Registers a module to the {@link CommunicationBase}, if the max number of registered modules hasn't been reached.<br>
	 * In the process of adding a {@link TransmissionModule}, a {@link ModuleWorker} is being created that requests data from the associated {@link TransmissionModule} within a given interval of milliseconds.<br>
	 * The interval is defined in the {@link TransmissionModule}.<br>
	 * If the given interval is 0 or less, then no data will be requested for transmission until explicitly requested.<br>
	 * Receiving incoming data is still possible of course. 
	 * 
	 * @param tm The {@link TransmissionModule} to be added.
	 * @return True, if the module was added successfully, otherwise False.
	 */
	public boolean registerModule(TransmissionModule tm) {
		
		boolean bRegistered=false;
		if(tm != null && numModules < modules.length){
			modules[numModules]=tm; 
		   (workers[numModules]=new ModuleWorker(this,tm,(byte)numModules)).start();
		    LOG.info("Registering Transmission-Module: "+tm.getClass().getSimpleName());
			numModules++;
			bRegistered=true;
		}
			
		return bRegistered;
	}
	
	/**
	 * Instigating an object transmission request on the given {@link TransmissionModule} manually. <br>
	 * This is useful for getting a passive {@link TransmissionModule} (which got no active transmission cycle) to send data on demand.<br>
	 * It can also be used to instigate a manual object transmission request within an active {@link TransmissionModule} outside of its usual transmission cycle.<br>
	 * If the given requester is not reqistered on the {@link CommunicationBase}, the method will return false.
	 * 
	 * @param requester The {@link TransmissionModule} where to instigate the request.
	 * @return True, if the request was instigated successfully, otherwise false. 
	 */
	public boolean requestTransmission(TransmissionModule requester){
		
		boolean bSuccesful=false;
		int moduleId=-1;
		
		if(requester != null){
			
			for(int i=0;i<MAX_MODULES;i++)
				if(modules[i] == requester)
					moduleId=i;
			
			if(moduleId != -1){
				
				if(workers[moduleId]!= null){
					
					synchronized(workers[moduleId]){
						
						workers[moduleId].notify();
						bSuccesful=true;
					}
					
				}
					
			}
		}
		return bSuccesful;
	}
	/**
	 * Setting the sender that shall be used to transmit data.<br>
	 * The sender is supposed to be set by a derived class, which defines the concrete communication protocol.<br>
	 * If no sender was set, the {@link ModuleWorker}s of all registered {@link TransmissionModule} are going into a sleep state.<br>
	 * If a new sender was set, all {@link ModuleWorker}s are being notified and continue their work. 
	 * 
	 * @param sender The sender to be used.
	 */
	protected void setSender(DataOutputStream sender){
		
		this.sender=sender;
		if(this.sender != null){
			
			synchronized(this.sender){
			
				for(ModuleWorker mw : workers){
					if(mw != null){
						
						synchronized(mw){
							
							mw.notify();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Setting the receiver that shall be used to receive data.<br>
	 * The receiver is supposed to be set by a derived class, which defines the concrete communication protocol.<br>
	 * If no receiver was set, internal receive thread of the {@link CommunicationBase} is going into a sleep state.<br>
	 * If a new receiver was set, the receive thread is notified and continues its work. 
	 * 
	 * @param receiver The receiver to be used.
	 */
	protected void setReceiver(DataInputStream receiver){	
		
		this.receiver=receiver;
		if(this.receiver != null){
			
			synchronized(this.receiver){
			
				if(recvThread != null){
				
					synchronized(recvThread){
					
						recvThread.notify();
					}
				}
			}
			
		}
	}
	
	/**
	 * Getting the used sender.
	 * 
	 * @return The used sender.
	 */
	public DataOutputStream getSender() {
		return sender;
	}

	/**
	 * Getting the used receiver.
	 * @return The used receiver.
	 */
	public DataInputStream getReceiver() {
		return receiver;
	}

	
	/**
	 * Initializes the receive thread that redirects the incoming data to the corresponding {@link TransmissionModule}s.
	 */
	public boolean initialize(){
		
		boolean bInitialized=false;
		if(recvThread == null){
			LOG.debug("Initializing recv thread.");
			recvThread=new ReceiverThread();	
			recvThread.start();
			
			bInitialized=true;
		}
		return bInitialized;
	}
	
	/**
	 * Interrupts all {@link ModuleWorker}s, removes the registered {@link TransmissionModule}s and interrupts the internal receive thread.
	 */
	public void shutdown(){
		
		LOG.debug("Shutting down..");
		for(int i=0;i<modules.length;i++){
			
			if(modules[i] != null)
				LOG.debug("Removing: "+modules[i].getClass().getSimpleName());
			modules[i]=null;
			if(workers[i] != null){
				
				workers[i].interrupt();
				try {
					workers[i].join(1000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				workers[i]=null;
			}
		}
		if(recvThread != null){
			
			recvThread.interrupt();
			try {
				recvThread.join(2000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			recvThread=null;
		}
	}
	
	/**
	 * Checks whether the underlying connection is still active.<br>
	 * This method must be implemented in a derived class, since it's responsible for implementing the concrete connection.
	 * @return True, if the underlying connection is establishes, otherwise False.
	 */
	public abstract boolean isConnected();
	
	/**
	 * Reads a compact index out of the given {@link InputStream} and returns the resulting integer value.<br>
	 * A detailed explanation of compact indices follows later.
	 * @param i The {@link InputStream} to read the compact index from
	 * @return The resulting integer value.
	 * @throws IOException Thrown, if an error occurred while reading from the given {@link InputStream}
	 */
	public static int readCompactIndex(InputStream i) throws IOException {
		
		int value=0;
		int b=0;
		boolean bNegative=false;
		
		if((b=i.read()) != -1){
			
			bNegative=((b & 0x80) != 0);
			value += (b & 0x3f);
			
			if((b & 0x40) != 0){
			
				if((b=i.read()) != -1){
				
					value += (b & 0x7f) << 6;
					if((b & 0x80) != 0){
					
						if((b=i.read()) != -1){
							
							value += (b & 0x7f) << (6+7);
							if((b & 0x80) != 0){
								
								if((b=i.read()) != -1){
									
									value += (b & 0x7f) << (6+7+7);
									if((b & 0x80) != 0){
										
										if((b=i.read()) != -1){
											
											value += (b & 0xff) << (6+7+7+7);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		if(bNegative)
			value = -value;
		
		return value;
	}
	
	/**
	 * Writes an integer value as compact index into the given {@link OutputStream}.<br>
	 * A detailed explanation of compact indices follows later.
	 * @param o The {@link OutputStream} to write the compact index to
	 * @param value integer value to write as compact index
	 * @throws IOException Thrown, if an error occurred while writing on the given {@link OutputStream}
	 */
	public static void writeCompactIndex(OutputStream o, int value) throws IOException {
		
		int v=value;
		int b=0;
		
		if(v < 0)
			v = -v;
		b = ((value >=0) ? 0 : 0x80) + ((v < 0x40) ? v : ((v & 0x3f) + 0x40));
		
		o.write(b);
		
		if( (b & 0x40) != 0){
			
			v = v >> 6;
			b = (v < 0x80) ? v : ((v & 0x7f)+0x80);
			o.write(b);
			
			if((b & 0x80) != 0){
				
				v = v >> 7;
				b = (v < 0x80) ? v : ((v & 0x7f)+0x80);
				o.write(b);
				
				if((b & 0x80) != 0){
				
					v = v >> 7;
					b = (v < 0x80) ? v : ((v & 0x7f)+0x80);
					o.write(b);
					
					if((b & 0x80) != 0){
					
						v = v >> 7;
						b = v;
						o.write(b);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * The class that implements the receive thread.
	 * 
	 * @author Michael Kant
	 *
	 */
	private class ReceiverThread extends Thread {
	
		/**
		 * Defines the receive loop.
		 */
		public synchronized void run(){
			
			int errorCount=0;
			byte signature=0,keyId=-1;
			while(!isInterrupted()){
				
				try {
					if(isConnected() && receiver != null){
					
						synchronized(receiver){
							
							signature=receiver.readByte();
						
							if((signature & ~0x0f) == START_SIGNATURE) {
								
								keyId=(byte)(signature & 0x0F);
								
								LOG.debug("Incoming object data..");
								
								if(keyId >= 0 && keyId < MAX_MODULES && modules[keyId] != null){
									
									LOG.debug("Forwarding data to module: "+modules[keyId].getClass().getSimpleName());
									modules[keyId].receivedObject(receiver);
									errorCount=0;
								}
								else{
									
									LOG.warn("Unknown object code received: "+keyId);
								}
							}
							else{
								LOG.warn("Invalid byte received: 0x"+Integer.toHexString(signature));
							}
						}
						
						
					} else {
						
						LOG.debug("No receiver is set.. waiting.");
						wait();
					} 		
				}
				catch (InterruptedException e) {
						break;
				} 
				catch (EOFException e){
					
					LOG.warn("Stream elapsed: "+e.getMessage());
					receiver=null;
				}
				catch (IOException e) {
					
					LOG.warn("Error reading socket: "+e.getMessage());
					if(errorCount < CommunicationBase.MAX_TRX_COUNT){
						
						errorCount++;
						LOG.warn("IO error: "+e.getMessage());
					}
					else{
						LOG.warn("Too many transmission attempts.. waiting.");
						try {
							wait();
						} catch (InterruptedException e1) {
							
							break;
						}
					}
				}
			}
			LOG.debug("Receiver Thread finished.");
		}
	}
	//public abstract void trans
}
