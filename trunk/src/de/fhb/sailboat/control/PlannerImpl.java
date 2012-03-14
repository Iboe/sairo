package de.fhb.sailboat.control;

import java.util.List;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.StopTask;
import de.fhb.sailboat.mission.Task;

public class PlannerImpl implements Planner {

	private final Navigator navigator;
	
	public PlannerImpl(Navigator navigator) {
		this.navigator = navigator;
	}
	
	@Override
	public void doMission(Mission mission) {
		Task currentTask;
		List<Task> tasks = mission.getTasks();
		
		while (!tasks.isEmpty()) {
			currentTask = tasks.get(0);
			tasks.remove(0);
			navigator.doTask(currentTask);
			//TODO implement as own thread, check if task is completed
		}
	}

	@Override
	public void stop() {
		navigator.doTask(new StopTask());

	}

}
