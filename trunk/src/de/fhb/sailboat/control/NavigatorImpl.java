package de.fhb.sailboat.control;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.StopTask;
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
		} else if (PrimitiveCommandTask.class.equals(task.getClass() )) {
			handlePrimitiveCommands((PrimitiveCommandTask) task);
		} else if (StopTask.class.equals(task.getClass() )) {
			stop((StopTask) task);
		} else {
			throw new UnsupportedOperationException("can not handle task: " + task);
		}
	}
	
	/**
	 * Calculates relative angle from current position to goal defined in task and 
	 * hands it over to pilot.
	 * 
	 * @param task the task to be executed
	 */
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
		
		if(diff.getLatitude() >= 0 && diff.getLongitude() < 0)
			angle+=180;
		else if(diff.getLatitude() < 0 && diff.getLongitude() < 0)
			angle+=180;
		else if(diff.getLatitude() < 0 && diff.getLongitude() >= 0)
			angle+=360;
		
		System.out.println("navigator - calculated angle: "+angle);
		
		//if (diff.getLatitude() < 0) {
		//	angle+=180;
		//} 
		
		//angle between current and goal position = 
		//north (== y-axis) - current angle (direction of the bow as difference to north)
		//- angle between difference vector and x-axis 
		angle = 90 - angle - worldModel.getCompassModel().getCompass().getYaw();
		pilot.driveAngle((int) angle);
	}
	
	private void handlePrimitiveCommands(PrimitiveCommandTask task) {
		if (task.getPropellor() != null) {
			pilot.setPropellor(task.getPropellor().intValue());
		}
		
		if (task.getRudder() != null) {
			pilot.setRudder(task.getRudder().intValue());
		}
		
		if (task.getSail() != null) {
			pilot.setSail(task.getSail().intValue());
		}
		
		task.setExecuted(true);
	}
	
	private void stop(StopTask task) {
		
	}
	
	private double toDegree(double radian) {
		return (radian / Math.PI * 180);
	}

}
