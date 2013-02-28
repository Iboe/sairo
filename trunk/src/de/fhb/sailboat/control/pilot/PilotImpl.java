package de.fhb.sailboat.control.pilot;

import de.fhb.sailboat.serial.actuator.LocomotionSystem;

/**
 * Implementation of {@link Pilot} which supports to hold a certain boat direction according to a given compass value<br> 
 * or according to a given relative wind direction.<br>
 * Control commands are directly forwarded to the underlying {@link LocomotionSystem}. 
 * 
 * @author Michael Kant
 *
 */
public class PilotImpl implements Pilot {

	private final LocomotionSystem locSystem;
	
	private DriveAngleThread driveAngleThread;
	
	/**
	 * Creates a new initialized instance, which uses the {@link LocomotionSystem} handed over
	 * to pass over the calculated positions to the actuators.
	 * 
	 * @param locomotaion the {@link LocomotionSystem} for the further execution of the 
	 * calculated positions
	 */
	public PilotImpl(LocomotionSystem locomotaion){
		driveAngleThread = null;
		locSystem = locomotaion;
	}
	
	@Override
	public void driveAngle(int angle) {
		driveAngle(angle, DriveAngleMode.COMPASS);		
	}

	@Override
	public void holdAngleToWind(int angle) {
		driveAngle(angle, DriveAngleMode.WIND);
	}

    /**
	 * Executes the current command by passing the desired angle to a {@link DriveAngleThread}. Starts 
	 * a new thread if necessary.
	 * 
	 * @param angle the desired angle to hold
	 * @param mode the {@link DriveAngleMode} that determines the way of the thread to work
	 */
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
	
	@Override
	public void stopThread() {
		if (driveAngleThread != null) {
			driveAngleThread.interrupt();
			driveAngleThread = null;
		}
	}
}
