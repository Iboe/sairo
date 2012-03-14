package de.fhb.sailboat.control;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class NavigatorImpl implements Navigator{

	private final Pilot pilot;
	private final WorldModel worldModel;
	
	public NavigatorImpl(Pilot pilot) {
		this.pilot = pilot;
		this.worldModel = WorldModelImpl.getInstance();
	}
	
	@Override
	public void doTask(Task task) {
		if (task == null) {
			throw new NullPointerException();
		} else if (ReachCircleTask.class.equals(task.getClass() )) {
			navigateToCircle((ReachCircleTask) task);
		} else {
			throw new UnsupportedOperationException("can not handle task");
		}
	}
	
	//calculates relative angle from current position to goal
	//hands over angle to pilot
	private void navigateToCircle(ReachCircleTask task) {
		GPS gpsPosition = worldModel.getGPSModel().getPosition();
		GPS diff;
		double angle;
		
		//calculate difference vector between current and goal position, 
		//as new origin of coordinates
		diff = new GPS(task.getCenter().getLatitude() - gpsPosition.getLatitude(), 
				task.getCenter().getLongitude() - gpsPosition.getLongitude());
		//calculate angle between difference vector and x-axis
		angle = Math.atan(diff.getLatitude() / diff.getLongitude());
		//convert to degrees
		angle = toDegree(angle);
		//angle between current and goal position = 
		//north (== y-axis) - current angle (direction of the bow as difference to north)
		//- angle between difference vector and x-axis 
		angle = 90 - angle - worldModel.getCompassModel().getCompass().getYaw();
		pilot.driveAngle((int) angle);
	}
	
	private double toDegree(double radian) {
		return (radian / Math.PI * 180);
	}

}
