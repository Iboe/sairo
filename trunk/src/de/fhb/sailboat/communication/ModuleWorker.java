/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public class ModuleWorker extends Thread {
	
	private CommunicationBase commBase;
	private TransmissionModule commModule;
	
	private byte moduleId;
	private int cycleInterval;
	private boolean bInterruptCycle;
	
	public ModuleWorker(CommunicationBase commBase, TransmissionModule commModule, byte moduleId) {
		
		super();
		
		if(commBase != null && commModule != null){
			
			this.commBase = commBase;
			this.commModule = commModule;
			cycleInterval=commModule.getTransmissionInterval();
			this.moduleId=moduleId;
			bInterruptCycle=false;
		}
		else bInterruptCycle=true;
		
	}
	
	public void run(){
		
		while(!isInterrupted() && !bInterruptCycle){
			
			ObjectOutputStream sender=commBase.getSender();
			
			try {
				sleep(cycleInterval);
				
				if(sender != null){
				
					synchronized(sender){
						
						sender.writeByte(CommunicationBase.START_SIGNATURE|moduleId);
						commModule.requestObject(sender);
					}
				}
				else wait();
			}	
			catch (InterruptedException e) {
			
				break;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
