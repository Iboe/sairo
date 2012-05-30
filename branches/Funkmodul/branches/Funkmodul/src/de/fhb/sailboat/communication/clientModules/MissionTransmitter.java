/**
 * 
 */
package de.fhb.sailboat.communication.clientModules;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.MissionNegotiationBase;
import de.fhb.sailboat.communication.TransmissionModule;
import de.fhb.sailboat.control.Planner;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.Task;

/**
 * @author Michael Kant
 *
 */
public class MissionTransmitter extends MissionNegotiationBase implements TransmissionModule,Planner{

	private eTransmissionMode mode;
	private CommunicationBase base;
	private List<Task> missionAssembly;
	//private Task pendingTask;
	
	/**
	 * 
	 * @param base
	 */
	public MissionTransmitter(CommunicationBase base){
	
		this.base=base;
		mode=eTransmissionMode.TM_Idle;
		missionAssembly=null;
		//pendingTask=null;
		//error=eErrorType.ET_None;
	}
	
	//Planner methods 
	@Override
	public void doMission(Mission mission) {
		
		synchronized(mode){
				
			missionAssembly.clear();
			missionAssembly=null;
			missionAssembly = mission != null ? mission.getTasks() : null;
			
			if(mode == eTransmissionMode.TM_Idle){
				
				if(mission != null){
				
					mode=eTransmissionMode.TM_MissionBegin;
					
				}
				//pendingTask=null;
				
			}
			else{
				
				
			}
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	
	//Transmission module methods
	@Override
	public void receivedObject(DataInputStream stream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean skipNextCycle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void requestObject(DataOutputStream stream) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionReset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTransmissionInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
