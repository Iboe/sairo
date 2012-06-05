/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.DataOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public class ModuleWorker extends Thread {
	
	private static final Logger LOG = LoggerFactory.getLogger(ModuleWorker.class);
	
	private CommunicationBase commBase;
	private TransmissionModule commModule;
	
	private byte moduleId;
	
	//private byte[] sendBuffer;
	
	public ModuleWorker(CommunicationBase commBase, TransmissionModule commModule, byte moduleId) {
		
		super();
		
		if(commBase != null && commModule != null){
			
			this.commBase = commBase;
			this.commModule = commModule;
			this.moduleId=moduleId;
	//		this.sendBuffer=new byte[CommunicationBase.MAX_PACKET_SIZE];
		}
	}
	
	public synchronized void run(){
		
		int errorCount=0;
		int cycleInterval=0;
		
		while(!isInterrupted() || this.commBase == null || this.commModule != null){
			
			DataOutputStream sender=commBase.getSender();
			
			try {
				cycleInterval=commModule.getTransmissionInterval();
				
				//sleeping a defined amount of time before starting the new loop cycle
				//if the given time amount is 0, it will just wait and start a loop cycle on explicit notification
				if(cycleInterval > 0){
					
					wait(cycleInterval);
					//ensuring that no execution happens if the interval was set to 0 while sleeping
					if(cycleInterval <= 0)
						wait();
				}
				else wait();
				
				
				if(commBase.isConnected() && sender != null){
				
					//skipping the next cycle for the associated TransmissionModule if requested
					if(commModule.skipNextCycle())
						continue;
					
					ByteArrayOutputStream dataForwarder=new ByteArrayOutputStream(CommunicationBase.MAX_PACKET_SIZE);
					
					commModule.requestObject(new DataOutputStream(dataForwarder));
					
					byte[] sendBuffer=dataForwarder.toByteArray();
					
					while(errorCount < CommunicationBase.MAX_TRX_COUNT){
						
						try{
						
							synchronized(sender){
								
								LOG.debug("Sending packet ("+sendBuffer.length+" Bytes) from module: "+commModule.getClass().getSimpleName());
								sender.writeByte(CommunicationBase.START_SIGNATURE|moduleId);
								CommunicationBase.writeCompactIndex(sender, sendBuffer.length);								
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
