package de.fhb.sailboat.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.gui.map.MapPolygon;
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
public class GUIControllerImpl implements GUIController {

	// Constants
	final static int PROPELLOR_MAX = Integer.parseInt(System.getProperty("AKSENLocomotion.PROPELLOR_MAX"));
	final static int PROPELLOR_NORMAL = Integer.parseInt(System.getProperty("AKSENLocomotion.PROPELLOR_NORMAL"));
	final static int RUDDER_NORMAL = Integer.parseInt(System.getProperty("AKSENLocomotion.RUDDER_NORMAL"));
	final static int SAIL_NORMAL = Integer.parseInt(System.getProperty("AKSENLocomotion.SAIL_SHEET_NORMAL"));
	
	
	// Variables
	private GUIModelImpl model;

	private WorldModel worldModel;

	public GUIControllerImpl() {
		this.model = new GUIModelImpl();
		this.worldModel = WorldModelImpl.getInstance();
	}

	// Committer (used for sending data to other sailbot classes)

	/**
	 * For the milestone. Stores MapMarkers set in View and stored in Model as a
	 * collection of CircleTasks in Mission.
	 */
	@Override
	public void commitCircleMarkerList(Planner planner) {
		List<GPS> markerList = this.model.getCircleMarkerList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		for (int i = 0; i < markerList.size(); i++) {
			tasks.add(new ReachCircleTask(new GPS(markerList.get(i)
					.getLatitude(), markerList.get(i).getLongitude()), 3));
		}
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_NORMAL));

		mission.setTasks(tasks);
		System.out.println(mission.getTasks().toString());
		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Override
	public void commitPolyList(Planner planner) {
		List<MapPolygon> polyList = this.model.getPolyList();

		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		for (int i = 0; i < polyList.size(); i++) {
			tasks.add(new ReachPolygonTask(polyList.get(i).getPoints()));
		}
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_NORMAL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Override
	public void commitReachCompassTask(int angle, Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		tasks.add(new CompassCourseTask(angle));
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_NORMAL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Override
	public void commitHoldAngleToWind(int angle, Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_MAX));
		tasks.add(new HoldAngleToWindTask(angle));
		if (!isSailMode())
			tasks.add(new PrimitiveCommandTask(null, null, PROPELLOR_NORMAL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	@Override
	public void commitStopTask(Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new StopTask());

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}
	
	@Override
	public void resetActorsTask(Planner planner) {
		MissionImpl mission = new MissionImpl();
		List<Task> tasks = new ArrayList<Task>();

		tasks.add(new PrimitiveCommandTask(SAIL_NORMAL, RUDDER_NORMAL, PROPELLOR_NORMAL));

		mission.setTasks(tasks);

		this.model.setCurrentWholeMission(mission); // Store send mission in
													// model for reporting of
													// advancements

		planner.doMission(mission);
	}

	// Updater (used to update a sensor reading and store it in model, kind of
	// like a more sophisticated setter)
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to
	 * get the most recent values possible at once.
	 */
	@Override
	public void updateAll() {
		updateWind();
		updateCompass();
		updateGps();
	}

	@Override
	public void updateWind() {
		this.model.setWind(worldModel.getWindModel());
	}

	@Override
	public void updateCompass() {
		this.model.setCompass(worldModel.getCompassModel());
	}

	@Override
	public void updateGps() {
		this.model.setGps(worldModel.getGPSModel());
	}

	@Override
	public void updateMission() {
		if (worldModel.getMission() != this.model.getMissionTasksLeft()) {
			this.model.setMissionTasksLeft(worldModel.getMission());
			generateMissionReport();
		}
	}

	@Override
	public void generateMissionReport() {
		if ((this.model.getCurrentWholeMission().getTasks().size() > 0)
				&& (this.model.getMissionTasksLeft().getTasks().size() > 0)) {
			Mission currentWholeMission = this.model.getCurrentWholeMission();
			Mission missionTasksLeft = this.model.getMissionTasksLeft();
			StringBuffer missionReport = new StringBuffer();
			Task task;
			int crossPoint = currentWholeMission.getTasks().size()
					- missionTasksLeft.getTasks().size();

			missionReport.append("Mission Status:\n");
			for (int i = 0; i < currentWholeMission.getTasks().size(); i++) {
				task = currentWholeMission.getTasks().get(i);

				missionReport.append("Task " + i + " ");
				if (task == null) {
					throw new NullPointerException();
				} else if (task instanceof ReachCircleTask) {
					missionReport.append("ReachCircleTask");
					missionReport.append(" zu "
							+ ((ReachCircleTask) task).getCenter().toString());
				} else if (task instanceof ReachPolygonTask) {
					missionReport.append("ReachPolygonTask");
					missionReport.append(" zu "
							+ ((ReachPolygonTask) task).getPoints().size()
							+ " Punkten");
				} else if (task instanceof PrimitiveCommandTask) {
					missionReport.append("PrimitiveCommandTask");
					missionReport.append(" bei P"
							+ ((PrimitiveCommandTask) task).getPropellor()
							+ " R" + ((PrimitiveCommandTask) task).getRudder()
							+ " S" + ((PrimitiveCommandTask) task).getSail());
				} else if (task instanceof CompassCourseTask) {
					missionReport.append("CompassCourseTask");
					missionReport.append(" zu "
							+ ((CompassCourseTask) task).getAngle() + "°");
				} else if (task instanceof HoldAngleToWindTask) {
					missionReport.append("HoldAngleToWindTask");
					missionReport.append(" zu "
							+ ((HoldAngleToWindTask) task).getAngle() + "°");
				} else if (task instanceof RepeatTask) {
					missionReport.append("RepeatTask");
				} else if (task instanceof StopTask) {
					missionReport.append("StopTask");
				} else if (task instanceof BeatTask) {
					missionReport.append("BeatTask");
				}
				if (i < crossPoint) {
					missionReport.append(" FERTIG\n");
				} else {
					missionReport.append("\n");
				}
			}
		}
	}

	// Setter (values given by View to store in Model)
	@Override
	public void setCircleMarkerList(List<GPS> pointList) {
		this.model.setCircleMarkerList(pointList);
		System.out.println("Set passed.");
	}

	@Override
	public void setPolyList(List<MapPolygon> polyList) {
		this.model.setPolyList(polyList);
		System.out.println("Set passed.");
	}

	// Getter ("tunneled" from Model)
	@Override
	public CompassModel getCompass() {
		return this.model.getCompass();
	}

	@Override
	public WindModel getWind() {
		return this.model.getWind();
	}

	@Override
	public GPSModel getGps() {
		return this.model.getGps();
	}

	@Override
	public int getGpsSatelites() {
		return this.model.getGps().getPosition().getSatelites();
	}

	@Override
	public boolean isSailMode() {
		return this.model.isSailMode();
		
	}

	@Override
	public void setSailMode(boolean sailMode) {
		this.model.setSailMode(sailMode);
	}

}
