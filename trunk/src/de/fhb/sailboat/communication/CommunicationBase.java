/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * to be commented
 * @author Michael Kant
 *
 */
public abstract class CommunicationBase {

	private static final Logger LOG = LoggerFactory.getLogger(CommunicationBase.class);
	
	protected static final byte START_SIGNATURE=0x40|0x20;
	public static final int MAX_MODULES=16;
	
	private TransmissionModule[] modules;
	private ModuleWorker[] workers;
	private int numModules;
	private Thread recvThread;
	
	private DataOutputStream sender = null;
	private DataInputStream receiver = null;
	
	public CommunicationBase(){
		
		modules=new TransmissionModule[MAX_MODULES];
		workers=new ModuleWorker[MAX_MODULES];
		numModules=0;
		recvThread=null;
	}
	
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
	
	protected void setSender(DataOutputStream sender){
		
		synchronized(this.sender){
			
			this.sender=sender;
			
			if(this.sender != null){
				
				for(ModuleWorker mw : workers)
					if(mw != null)
						mw.notify();
			}
		}
	}
	
	protected void setReceiver(DataInputStream receiver){	
		
		synchronized(this.receiver){
			
			this.receiver=receiver;
			
			if(this.receiver != null)
				if(recvThread != null)
					recvThread.notify();
			
		}
	}
	
	public DataOutputStream getSender() {
		return sender;
	}

	public DataInputStream getReceiver() {
		return receiver;
	}

	
	/**
	 * To be called in a subclass to initialize the concrete transmission endpoints
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
	
	public void shutdown(){
		
		LOG.debug("Shutting down..");
		for(int i=0;i<modules.length;i++){
			
			LOG.debug("Removing: "+modules[i].getClass().getSimpleName());
			modules[i]=null;
			if(workers[i] != null){
				
				workers[i].interrupt();
				workers[i]=null;
			}
		}
		if(recvThread != null){
			
			recvThread.interrupt();
			recvThread=null;
		}
	}
	
	public abstract boolean isConnected();
	
	private class ReceiverThread extends Thread {
	
		public void run(){
			
			byte signature=0,keyId=-1;
			while(!isInterrupted()){
				
				try {
					if(isConnected() && receiver != null){
					
						signature=receiver.readByte();
						
						if((signature & START_SIGNATURE) == START_SIGNATURE) {
							
							keyId=(byte)(signature & 0x0F);
							
							LOG.debug("Incoming object data..");
							
							if(keyId >= 0 && keyId < MAX_MODULES && modules[keyId] != null){
								
								LOG.debug("Forwarding data to module: "+modules[keyId].getClass().getSimpleName());
								modules[keyId].objectReceived(getReceiver());
							}
							else{
								
								LOG.warn("Unknown object code received: "+keyId);
							}
						}
						else{
							LOG.warn("Invalid byte received: 0x"+Integer.toHexString(signature));
						}
						
					} else {
						
						LOG.debug("No receiver is set.. waiting.");
						wait();
					} 		
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
	//public abstract void trans
}
