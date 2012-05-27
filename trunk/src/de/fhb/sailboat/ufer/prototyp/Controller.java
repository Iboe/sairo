package de.fhb.sailboat.ufer.prototyp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.mission.BeatTask;
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.RepeatTask;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.ufer.prototyp.utility.MapPolygon;
import de.fhb.sailboat.worldmodel.CompassModel;
import de.fhb.sailboat.worldmodel.GPSModel;
import de.fhb.sailboat.worldmodel.WindModel;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * This class manages the data pipe between view, model and the sensors.
 * 
 * @author Patrick Rutter
 * 
 */
public class Controller {

	// Variables
	private Model model;

	private WorldModel worldModel;

	public Controller() {
		this.model = new Model();
		worldModel = WorldModelImpl.getInstance();
	}

	// Committer (used for sending data to other sailbot classes)

	/**
	 * For the milestone. Stores MapMarkers set in View and stored in Model as a
	 * collection of CircleTasks in Mission.
	 */
	public void commitCircleMarkerList(Planner planner) {
		List<GPS> markerList = this.model.getCircleMarkerList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_MAX));
		for (int i = 0; i < markerList.size(); i++) {
			tasks.add(new ReachCircleTask(new GPS(markerList.get(i)
					.getLatitude(), markerList.get(i).getLongitude()), 3));
		}
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_NULL));

		mission.setTasks(tasks);
		System.out.println(mission.getTasks().toString());
		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	public void commitPolyList(Planner planner) {
		List<MapPolygon> polyList = this.model.getPolyList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_MAX));
		for (int i = 0; i < polyList.size(); i++) {
			tasks.add(new ReachPolygonTask(polyList.get(i).getPoints()));
		}
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_NULL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	public void commitReachCompassTask(int angle, Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_MAX));
		tasks.add(new CompassCourseTask(angle));
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_NULL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	public void commitHoldAngleToWind(int angle, Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_MAX));
		tasks.add(new HoldAngleToWindTask(angle));
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null,
					RemoteControl.PROPELLOR_NULL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	public void commitStopTask(Planner planner) {
		// TODO Implement proper stop mission task beyond resetting motor
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new StopTask());

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	public void stopMotorTask(Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(null, null,
				RemoteControl.PROPELLOR_NULL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in model for reporting of advancements
		
		planner.doMission(mission);
	}

	// Updater (used to update a sensor reading and store it in model, kind of
	// like a more sophisticated setter)
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 */
	public void updateAll() {
		updateWind();
		updateCompass();
		updateGps();
	}

	/**
	 * This method is used to create an wide array of pseudo-random values for
	 * testing the GUI's ability to display values.
	 */
	public void updateRandom() {
		Random dice = new Random();

		// this.model.setCompDirection(dice.nextInt(361));

		this.model.getWind().setWind(
				new Wind(dice.nextInt(361), dice.nextInt(3000)));

		this.model.setGpsPosition(new GPS(dice.nextDouble() + dice.nextInt(12),
				dice.nextDouble() + dice.nextInt(12), dice.nextInt(10)));
	}

	public void updateWind() {
		this.model.setWind(worldModel.getWindModel());
	}

	public void updateCompass() {
		this.model.setCompass(worldModel.getCompassModel());
	}

	public void updateGps() {
		this.model.setGps(worldModel.getGPSModel());
	}
	
	public void updateMission() {
		if (worldModel.getMission() != this.model.getMissionTasksLeft()) {
			this.model.setMissionTasksLeft(worldModel.getMission());
			generateMissionReport();
		}
	}
	
	public void generateMissionReport() {
		if ((this.model.getCurrentWholeMission().getTasks().size() > 0) && (this.model.getMissionTasksLeft().getTasks().size() > 0)) {
			Mission currentWholeMission = this.model.getCurrentWholeMission();
			Mission missionTasksLeft = this.model.getMissionTasksLeft();
			StringBuffer missionReport = new StringBuffer();
			Task task;
			int crossPoint = currentWholeMission.getTasks().size() - missionTasksLeft.getTasks().size();
			
			missionReport.append("Mission Status:\n");
			for (int i = 0; i < currentWholeMission.getTasks().size(); i++) {
				task = currentWholeMission.getTasks().get(i);
				
				missionReport.append("Task " + i + " ");
				if (task == null) {
					throw new NullPointerException();
				} else if (task instanceof ReachCircleTask) {
					missionReport.append("ReachCircleTask");
					missionReport.append(" zu " + ((ReachCircleTask)task).getCenter().toString());
				} else if (task instanceof ReachPolygonTask) {
					missionReport.append("ReachPolygonTask");
					missionReport.append(" zu " + ((ReachPolygonTask)task).getPoints().size() + " Punkten");
				} else if (task instanceof PrimitiveCommandTask) {
					missionReport.append("PrimitiveCommandTask");
					missionReport.append(" bei P" + ((PrimitiveCommandTask)task).getPropellor() + " R" + ((PrimitiveCommandTask)task).getRudder() + " S" + ((PrimitiveCommandTask)task).getSail());
				} else if (task instanceof CompassCourseTask) {
					missionReport.append("CompassCourseTask");
					missionReport.append(" zu " + ((CompassCourseTask)task).getAngle() + "°");
				} else if (task instanceof HoldAngleToWindTask) {
					missionReport.append("HoldAngleToWindTask");
					missionReport.append(" zu " + ((HoldAngleToWindTask)task).getAngle() + "°");
				} else if (task instanceof RepeatTask) {
					missionReport.append("RepeatTask");
				} else if (task instanceof StopTask) {
					missionReport.append("StopTask");
				} else if (task instanceof BeatTask) {
					missionReport.append("BeatTask");
				}
				if (i < crossPoint) {
					missionReport.append(" FERTIG\n");
				}
				else {
					missionReport.append("\n");
				}
			}
		}
	}

	// Setter (values given by View to store in Model)
	public void setCircleMarkerList(List<GPS> pointList) {
		this.model.setCircleMarkerList(pointList);
		System.out.println("Set passed.");
	}

	public void setPolyList(List<MapPolygon> polyList) {
		this.model.setPolyList(polyList);
		System.out.println("Set passed.");
	}

	// Getter ("tunneled" from Model)
	public CompassModel getCompass() {
		return this.model.getCompass();
	}

	public WindModel getWind() {
		return this.model.getWind();
	}

	public GPSModel getGps() {
		return this.model.getGps();
	}

	public int getGpsSatelites() {
		return this.model.getGps().getPosition().getSatelites();
	}

	public boolean isSailMode() {
		return this.model.isSailMode();
	}

	public void setSailMode(boolean sailMode) {
		this.model.setSailMode(sailMode);
	}

}
