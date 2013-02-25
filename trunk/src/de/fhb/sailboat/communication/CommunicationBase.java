/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.ByteArrayInputStream;
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
	
	/**
	 * Max number of bytes that can be sent within one single transmission. <br>
	 * One transmission involves a transmission request from one transmission module.
	 */
	public static final int MAX_PACKET_SIZE = 64;
	
	/**
	 * List which contains the registered {@link TransmissionModule}s of this {@link CommunicationBase} instance.
	 */
	private TransmissionModule[] modules;
	
	/**
	 * List which contains the {@link ModuleWorker}s that are associated to the {@link TransmissionModule}s.
	 */
	private ModuleWorker[] workers;
	
	/**
	 * Number of {@link TransmissionModule}s that are registered to this {@link CommunicationBase}.
	 */
	private int numModules;
	
	/**
	 * Reference to the internal {@link ReceiverThread} instance.
	 */
	private Thread recvThread;
	
	private DataOutputStream sender = null;
	private DataInputStream receiver = null;
	
	private byte[] receiveBuffer;
	
	private ByteArrayInputStream dataForwarder;
	
	/**
	 * Default constructor.
	 */
	public CommunicationBase(){
		
		modules=new TransmissionModule[MAX_MODULES];
		workers=new ModuleWorker[MAX_MODULES];
		numModules=0;
		recvThread=null;
		receiveBuffer=new byte[MAX_PACKET_SIZE];
		dataForwarder=new ByteArrayInputStream(receiveBuffer);
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
	 * If the given requester is not registered on the {@link CommunicationBase}, the method will return false.
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
	 * @return true, if the receiver thread was started successfully, otherwise false. 
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
				}
				catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				workers[i]=null;
			}
		}
		if(recvThread != null){
			
			recvThread.interrupt();
			try {
				
				recvThread.join(2000);
			} 
			catch (InterruptedException e) {
				
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
	 * Causes the underlying connection to be closed.<br>
	 * This method must be implemented in a derived class, since it's responsible for implementing the concrete connection.
	 * @return True, if the connection closed successfully, otherwise false.
	 */
	public abstract boolean closeConnection();
	
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
	 * The class that implements the receive thread. <br>
	 * It starts an internal thread, that listens for incoming data on the underlying connection and forwards the data to the corresponding {@link TransmissionModule}. 
	 * 
	 * @author Michael Kant
	 *
	 */
	private class ReceiverThread extends Thread {
	
		/**
		 * Defines the receive loop, which reads and forwards the incomin data to the desired {@link TransmissionModule}.
		 */
		public synchronized void run(){
			
			int errorCount=0;
			byte signature=0,keyId=-1;
			int dataLength=0,readBytes=0;
			
			//Thread loop, runs until the thread is marked as interrupted.
			while(!isInterrupted()){
				
				try {
					//Checks whether the connection is still active, if not the thread will go into sleep mode.
					if(isConnected() && receiver != null){
					
						synchronized(receiver){
							
							//Reading the first byte of the stream.
							//It should contain the signature within the four most significant bits 
							//and the id of the desired TransmissionModule in the four least significant bits.
							signature=receiver.readByte();
						
							//Checks if the four most significant bits contains the START_SIGNATURE.
							if((signature & ~0x0f) == START_SIGNATURE) {
								
								//Extracts the TransmissionModule id out of the least significant bits.
								keyId=(byte)(signature & 0x0F);
								
								LOG.debug("Incoming object data..");
								
								//Reading the next Byte(s) of the stream, which contain the size of the data chunk.
								//The size is encoded as compact index, which means the amount of bytes to be read is determined within the decoding process.
								dataLength=CommunicationBase.readCompactIndex(receiver);
								
								//Ensures that the given TransmissionModule id is valid and that a TransmissionModule is associated with that id.
								if(keyId >= 0 && keyId < MAX_MODULES && modules[keyId] != null){
									
									//Checks that the given packet size doesn't exceed the max valid packet size.
									//Too large packets are being skipped.
									if( dataLength > MAX_PACKET_SIZE)
									{
										LOG.warn("Received too large packet for module: "+modules[keyId].getClass().getSimpleName());
										receiver.skipBytes(dataLength);
									}
									else{
										
										//Finally reads the amount of bytes that was determined with the prior chunk size information.
										readBytes=receiver.read(receiveBuffer, 0, dataLength);
										//Verifies whether the desired amount of bytes was actually read from the stream.
										//If not, it will discard the packet and give out an incomplete packet warning.
										if(readBytes == dataLength)
										{
											//Resetting the dataForwarder to point to the begin of the receive buffer again.
											dataForwarder.reset();
											LOG.debug("Forwarding packet ("+dataLength+" Bytes) to module: "+modules[keyId].getClass().getSimpleName());
											String bytes="";
											
											for(int i=0;i<dataLength;i++)
												bytes+=Integer.toHexString(receiveBuffer[i]&0xff)+ " ";
												
											LOG.debug(bytes);
											//Finally forwards the data to the desired TransmissionModule
											modules[keyId].receivedObject(new DataInputStream(dataForwarder));
											errorCount=0;	
										}
										else
										{
											LOG.warn("Received incomplete packet for module: "+modules[keyId].getClass().getSimpleName());
										}
									}
								}
								else{
									
									LOG.warn("Unknown object code received: "+keyId+". Skipping packet.");
									receiver.skipBytes(dataLength);
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
						LOG.warn("Too many receive attempts.. closing connection.");
						try {
							closeConnection();
							errorCount=0;
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
