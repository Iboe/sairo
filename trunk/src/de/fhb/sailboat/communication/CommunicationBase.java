/**
 * 
 */
package de.fhb.sailboat.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * to be commented
 * @author Michael Kant
 *
 */
public abstract class CommunicationBase {

	protected static final byte START_SIGNATURE=0x40|0x20;
	public static final int MAX_MODULES=16;
	
	private TransmissionModule[] modules;
	private ModuleWorker[] workers;
	private int numModules;
	private ReceiverThread recvThread;
	
	private ObjectOutputStream sender = null;
	private ObjectInputStream receiver = null;
	
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
			numModules++;
			bRegistered=true;
		}
			
		return bRegistered;
	}
	
	protected void setSender(ObjectOutputStream sender){
		
		synchronized(this.sender){
			
			this.sender=sender;
			
			if(this.sender != null){
				
				for(ModuleWorker mw : workers)
					if(mw != null)
						mw.notify();
			}
		}
	}
	
	protected void setReceiver(ObjectInputStream receiver){	
		
		synchronized(this.receiver){
			
			this.receiver=receiver;
			
			if(this.receiver != null)
				if(recvThread != null)
					recvThread.notify();
			
		}
	}
	
	public ObjectOutputStream getSender() {
		return sender;
	}

	public ObjectInputStream getReceiver() {
		return receiver;
	}

	
	/**
	 * To be called in a subclass to initialize the concrete transmission endpoints
	 */
	public void initialize(){
		
		if(recvThread == null){
			recvThread=new ReceiverThread();	
			recvThread.start();
		}
	}
	
	public void shutdown(){
		
		for(int i=0;i<modules.length;i++){
			
			modules[i]=null;
			if(workers[i] != null){
				
				workers[i].interrupt();
				workers[i]=null;
			}
		}
	}
	
	private class ReceiverThread extends Thread {
	
		public void run(){
			
			byte signature=0,keyId=-1;
			while(!isInterrupted()){
				
				try {
					if(receiver != null){
					
						signature=receiver.readByte();
						
						if((signature & START_SIGNATURE) == START_SIGNATURE) {
							
							keyId=(byte)(signature & 0x0F);
							
							if(keyId >= 0 && keyId < MAX_MODULES){
								
								if(modules[keyId] != null ){
									modules[keyId].objectReceived(getReceiver());
								}
							}
						}
						
						//TODO reading first byte to determine and redirect to the desired transmission module 
					} else {
						
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
