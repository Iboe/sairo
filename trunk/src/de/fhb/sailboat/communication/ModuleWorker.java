/**
 * 
 */
package de.fhb.sailboat.communication;

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
	//private int cycleInterval;
	//private boolean bInterruptCycle;
	
	public ModuleWorker(CommunicationBase commBase, TransmissionModule commModule, byte moduleId) {
		
		super();
		
		if(commBase != null && commModule != null){
			
			this.commBase = commBase;
			this.commModule = commModule;
			//cycleInterval=commModule.getTransmissionInterval();
			this.moduleId=moduleId;
			//bInterruptCycle=false;
		}
		//else bInterruptCycle=true;
		
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
				if(cycleInterval > 0)
					wait(cycleInterval);
				else wait();
				
				//skipping the next cycle for the associated TransmissionModule if requested
				if(commModule.skipNextCycle())
					continue;
				
				if(commBase.isConnected() && sender != null){
				
					synchronized(sender){
						
						sender.writeByte(CommunicationBase.START_SIGNATURE|moduleId);
						commModule.requestObject(sender);
						sender.flush();
						errorCount=0;
					}
				}
				else {
					LOG.debug("No sender is set.. waiting.");
					wait();
				}
			}/*	
			catch (SocketException e){
				
				if(errorCount < CommunicationBase.MAX_TRX_COUNT){
					
					errorCount++;
					LOG.warn("Error writing to socket: "+e.getMessage());
				}
				else{
					LOG.warn("Too many transmission attempts.. waiting.");
					try {
						wait();
					} catch (InterruptedException e1) {
						
						break;
					}
				}
			}*/
			catch (InterruptedException e) {
			
				break;
			} 
			catch (IOException e) {
			
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
		LOG.debug("Module Worker Thread finished.");
	}
}
