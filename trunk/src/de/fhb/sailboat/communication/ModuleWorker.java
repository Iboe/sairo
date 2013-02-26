
package de.fhb.sailboat.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link Thread} class, which schedules the transmissions for the associated {@link TransmissionModule}.<br>
 * For each transmission cycle, it's calling the respective methods of {@link TransmissionModule} in the following order:<br>
 * 1. {@link TransmissionModule#getTransmissionInterval()} is being called and the thread is suspended for the amount of milliseconds returned.<br>
 * 2. {@link TransmissionModule#skipNextCycle()} is called. If it returns true, it starts at step 1 again. If it returns false, step 3 follows.<br>
 * 3. {@link TransmissionModule#requestObject(DataOutputStream)} is called to obtain the data that's supposed to be sent.<br>
 * 4. If the method {@link TransmissionModule#requestObject(DataOutputStream)} returned data, it will be sent over the inherent {@link OutputStream} object of the associated {@link CommunicationBase}. Finally the cycle starts at step 1 again.<br><br> 
 * 
 * A {@link ModuleWorker} instance is created for each registered module.
 * 
 * @author Michael Kant
 *
 */
public class ModuleWorker extends Thread {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModuleWorker.class);
	
	/**
	 * {@link CommunicationBase} instance where the associated {@link TransmissionModule} is registered at.
	 */
	private CommunicationBase commBase;
	
	/**
	 * Associated {@link TransmissionModule} instance.
	 */
	private TransmissionModule commModule;
	
	/**
	 * Id of the associated {@link TransmissionModule} instance.
	 */
	private byte moduleId;
	
	//private byte[] sendBuffer;
	
	/**
	 * Initialization constructor.
	 * @param commBase The {@link CommunicationBase} object where this object was created from.
	 * @param commModule The associated {@link TransmissionModule}, which will be controlled.
	 * @param moduleId The ID of the associated {@link TransmissionModule}. It's added to the outgoing packets, when sending data.
	 */
	public ModuleWorker(CommunicationBase commBase, TransmissionModule commModule, byte moduleId) {
		
		super();
		
		if(commBase != null && commModule != null){
			
			this.commBase = commBase;
			this.commModule = commModule;
			this.moduleId=moduleId;
	//		this.sendBuffer=new byte[CommunicationBase.MAX_PACKET_SIZE];
		}
	}
	
	/**
	 * Thread loop that's responsible for calling the interface methods <br>
	 * on the associated {@link TransmissionModule}.
	 * @see TransmissionModule
	 */
	public synchronized void run(){
		
		int errorCount=0;
		int cycleInterval=0;
		
		//Runs as long as the thread is not interrupted and there's still a reference to the associated CommunicationBase and the TransmissionModule.
		while(!isInterrupted() || this.commBase == null || this.commModule != null){
			
			DataOutputStream sender=commBase.getSender();
			
			try {
				cycleInterval=commModule.getTransmissionInterval();
				
				//Sleeping a defined amount of time before starting the new loop cycle.
				//If the given time amount is 0, it will just wait and start a loop cycle on explicit notification.
				if(cycleInterval > 0){
					
					wait(cycleInterval);
					//Ensuring that no execution happens if the interval was set to 0 while sleeping.
					if(cycleInterval <= 0)
						wait();
				}
				else wait();
				
				//Just transmitting if a connection is established and a sender is set.
				if(commBase.isConnected() && sender != null){
				
					//Skipping the next cycle for the associated TransmissionModule, if requested.
					if(commModule.skipNextCycle())
						continue;
					
					ByteArrayOutputStream dataForwarder=new ByteArrayOutputStream(CommunicationBase.MAX_PACKET_SIZE);
					
					//Calling the TransmissionModules requestObject method to obtain the data to be sent.
					commModule.requestObject(new DataOutputStream(dataForwarder));
					
					byte[] sendBuffer=dataForwarder.toByteArray();
					
					//Just sending data if the TransmissionModule returned bytes to transmit.
					if(sendBuffer.length > 0){
						
						//Just trying to send the data if the error count doesn't exceed the maximum value.
						while(errorCount < CommunicationBase.MAX_TRX_COUNT){
							
							try{
							
								synchronized(sender){
									
									LOG.debug("Sending packet ("+sendBuffer.length+" Bytes) from module: "+commModule.getClass().getSimpleName());
									//Writing the first byte to the stream, which contains the START_SIGNATURE in the four most significant bits
									//and the TransmissionModule id in the four least significant bits
									sender.writeByte(CommunicationBase.START_SIGNATURE|moduleId);
									//Writing the next Byte(s) to the stream, which encodes the size of the data to be sent.
									//The data size is encoded as compact index, which means that the amount of required bytes is determined within the encoding process.
									CommunicationBase.writeCompactIndex(sender, sendBuffer.length);
									//Finally sending the data.
									sender.write(sendBuffer);
									sender.flush();
									errorCount=0;
									break;
								}
							}
							catch (IOException e) {
								
								if(errorCount < CommunicationBase.MAX_TRX_COUNT){
									
									errorCount++;
									LOG.warn("IO error: "+e.getMessage());
								}
								else{
									
									break;
								}
							}
						}
					}
					else 
						LOG.warn("Discarding empty packet from module: "+commModule.getClass().getSimpleName());
					
					if(errorCount >= CommunicationBase.MAX_TRX_COUNT){
						
						LOG.warn("Too many transmission attempts.. waiting.");
						
						try {
							commModule.connectionReset();
							errorCount=0;
							wait();
						} catch (InterruptedException e1) {
							
							break;
						}
					}
					
					
				}
				else {
					LOG.debug("No sender is set.. waiting.");
					commModule.connectionReset();
					wait();
					LOG.debug("Thread was notified, continuing the cycle.");
				}
			}
			catch (IOException e) {
				
				LOG.warn("Failed to request object from module: "+commModule.getClass().getSimpleName());
			}
			catch (InterruptedException e) {
			
				break;
			} 
			
		}
		LOG.debug("Module Worker Thread finished.");
	}
}
