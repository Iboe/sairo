package de.fhb.sailboat.control.pilot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;

/**
 * Implementation for the Pilot interface.
 * 
 * @author Michael Kant
 *
 */
public class PilotImpl implements Pilot {

	private static final Logger LOG = LoggerFactory.getLogger(PilotImpl.class);
	
	private final LocomotionSystem locSystem;
	
	private DriveAngleThread driveAngleThread;
	
	public PilotImpl(LocomotionSystem ls){
		driveAngleThread=null;
		locSystem=ls;
	}
	
	@Override
	public void driveAngle(int angle) {
		driveAngle(angle, DriveAngleMode.COMPASS);		
	}

	@Override
	public void holdAngleToWind(int angle) {
		driveAngle(angle, DriveAngleMode.WIND);
	}

	private synchronized void driveAngle(int angle, DriveAngleMode mode) {
		if (driveAngleThread != null && driveAngleThread.isAlive()) {
			driveAngleThread.driveAngle(angle, mode);
		} else {
			driveAngleThread = new DriveAngleThread(locSystem);
			driveAngleThread.driveAngle(angle, mode);
			driveAngleThread.start();
		}
	}
	
	@Override
	public void setPropellor(int value) {
		locSystem.setPropellor(value);
	}

	@Override
	public void setRudder(int value) {
		locSystem.setRudder(value);
	}

	@Override
	public void setSail(int value) {
		locSystem.setSail(value);
	}
}
