package de.fhb.sailboat.control;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Implementation for the Pilot interface.
 * 
 * @author Michael Kant
 *
 */
public class PilotImpl implements Pilot {

	/**
	 * The max relevant angle where the boat rudder reaches the maximum deflection. 
	 */
	public static final int MAX_RELEVANT_ANGLE=45;
	
	private LocomotionSystem locSystem;
	private CompassModel compassModel;
	
	
	public PilotImpl(LocomotionSystem ls){
		
		locSystem=ls;
		compassModel=WorldModelImpl.getInstance().getCompassModel();
	}
	
	@Override
	public void driveAngle(int angle) {
		
		double desiredAngle=0;
		double rudderPos;
		
		
		angle=angle%360;
		
		//ensuring that the specified angle doesn't exceed +/-180°; if yes, recalculating it
		if(angle > 180) 
			angle-=360;
		else if(angle < -180) 
			angle+=360;
		
		//desiredAngle=compassModel.getCompass().getYaw()+angle;
		
		System.out.println("desired relative angle: "+angle);
		
		rudderPos=Math.min(MAX_RELEVANT_ANGLE, Math.abs(angle)); 
		System.out.println("relevant relative angle: "+rudderPos);
		
		if(angle < 0){
			
			//rudder to the very left, assumung very left is the smallest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_LEFT-LocomotionSystem.RUDDER_NORMAL);
		}
		else{
			
			//rudder to the very right, assumung very right is the biggest value
			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_RIGHT-LocomotionSystem.RUDDER_NORMAL);
		}
		
		System.out.println("desired relative rudderpos: "+rudderPos);
		
		//adding offset, to match with the absolute rudder values
		rudderPos+=LocomotionSystem.RUDDER_NORMAL;
		
		//TODO add watch-thread about the process of changing direction
		
		locSystem.setRudder((int)rudderPos);
	}

	@Override
	public void driveToGPSPoint(GPS point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void holdAngleToWind(int angle) {
		// TODO Auto-generated method stub

	}
}
