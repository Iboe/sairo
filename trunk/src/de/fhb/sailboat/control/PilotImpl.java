package de.fhb.sailboat.control;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Implementation for the Pilot interface.
 * 
 * @author Michael Kant
 *
 */
public class PilotImpl implements Pilot {

	public static final int WAIT_TIME = Integer.parseInt(System.getProperty(
			Pilot.WAIT_TIME_PROPERTY));
	
	/**
	 * The max relevant angle where the boat rudder reaches the maximum deflection. 
	 */
	public static final int MAX_RELEVANT_ANGLE=45;
	
	private final LocomotionSystem locSystem;
	private final CompassModel compassModel;
	
	DriveAngleThread driveAngleThread;
	
	
	
	public PilotImpl(LocomotionSystem ls){
		
		driveAngleThread=null;
		locSystem=ls;
		compassModel=WorldModelImpl.getInstance().getCompassModel();
	}
	
	@Override
	public void driveAngle(int angle) {
		
		double desiredAngle=0;
		
		System.out.println("given relative angle: "+angle);
		angle=angle%360;
		
		//ensuring that the specified angle doesn't exceed +/-180°; if yes, recalculating it
		if(angle > 180) 
			angle-=360;
		else if(angle < -180) 
			angle+=360;
		
		desiredAngle=compassModel.getCompass().getYaw()+angle;
		
		if(driveAngleThread != null && driveAngleThread.isAlive())
			driveAngleThread.setDesiredAngle(desiredAngle);
		else
			(driveAngleThread=new DriveAngleThread(desiredAngle)).start();
		
		System.out.println("desired relative angle: "+angle);
//		
//		rudderPos=Math.min(MAX_RELEVANT_ANGLE, Math.abs(angle)); 
//		System.out.println("relevant relative angle: "+rudderPos);
//		
//		if(angle < 0){
//			
//			//rudder to the very left, assumung very left is the smallest value
//			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_LEFT-LocomotionSystem.RUDDER_NORMAL);
//		}
//		else{
//			
//			//rudder to the very right, assumung very right is the biggest value
//			rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_RIGHT-LocomotionSystem.RUDDER_NORMAL);
//		}
//		
//		System.out.println("desired relative rudderpos: "+rudderPos);
//		
//		//adding offset, to match with the absolute rudder values
//		rudderPos+=LocomotionSystem.RUDDER_NORMAL;
//		
//		//TODO add watch-thread about the process of changing direction
//		
//		locSystem.setRudder((int)rudderPos);
	}

	@Override
	public void driveToGPSPoint(GPS point) {
		// TODO Auto-generated method stub

	}

	@Override
	public void holdAngleToWind(int angle) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropellor(int value) {
		locSystem.setSail(value);
	}

	@Override
	public void setRudder(int value) {
		locSystem.setRudder(value);
	}

	@Override
	public void setSail(int value) {
		locSystem.setSail(value);
	}
	
	
	private class DriveAngleThread extends Thread
	{
		//private CompassModel compassModel;
		private double desiredAngle;
		private boolean bStop;
		
		public DriveAngleThread(double desiredAngle)
		{
			this.bStop=false;
			this.setDesiredAngle(desiredAngle);
			
			//compassModel=WorldModelImpl.getInstance().getCompassModel();
		}
		
		public void run()
		{
			double rudderPos=0;
			double deltaAngle=0;
			
			while(!isInterrupted() && !bStop){
				
				deltaAngle=desiredAngle-compassModel.getCompass().getYaw();
				if(deltaAngle > 180) 
					deltaAngle-=360;
				else if(deltaAngle < -180) 
					deltaAngle+=360;
				
				rudderPos=Math.min(MAX_RELEVANT_ANGLE, Math.abs(deltaAngle)); 
				//System.out.println("[THREAD]relevant relative angle: "+rudderPos);
				
				if(deltaAngle < 0){
					
					//rudder to the very left, assumung very left is the smallest value
					rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_LEFT-LocomotionSystem.RUDDER_NORMAL);
				}
				else{
					
					//rudder to the very right, assumung very right is the biggest value
					rudderPos=(rudderPos/MAX_RELEVANT_ANGLE)*(LocomotionSystem.RUDDER_RIGHT-LocomotionSystem.RUDDER_NORMAL);
				}
				
				//System.out.println("desired relative rudderpos: "+rudderPos);
				
				//adding offset, to match with the absolute rudder values
				rudderPos+=LocomotionSystem.RUDDER_NORMAL;
				
				//TODO add watch-thread about the process of changing direction
				
				locSystem.setRudder((int)rudderPos);
				
				System.out.println("[THREAD]Summarize: angle="+compassModel.getCompass().getYaw()+", desiredAngle="+desiredAngle+", delta="+deltaAngle);
				
				
				try {
					Thread.sleep(WAIT_TIME);
				}
				catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		
		public void abort()
		{
			bStop=true;
		
		}

		public void setDesiredAngle(double desiredAngle) {
			if(desiredAngle > 180) 
				desiredAngle-=360;
			else if(desiredAngle < -180) 
				desiredAngle+=360;
			
			this.desiredAngle = desiredAngle;
		}

		public double getDesiredAngle() {
			return desiredAngle;
		}
	
	}
}
