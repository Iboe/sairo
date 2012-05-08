package de.fhb.sailboat.mission;

import java.util.List;

import de.fhb.sailboat.data.GPS;

public class RepeatTask implements Task {

	private final List<Task> tasks;
	private final int repetitions;
	private Task currentTask;
	private int currentTaskPos;
	private int currentRepetition;
	
	public RepeatTask(List<Task> tasks, int repetitions) {
		if (tasks == null || tasks.size() == 0 || repetitions == 0) {
			throw new IllegalArgumentException();
		}
			
		this.tasks = tasks;
		this.repetitions = repetitions;
		this.currentTaskPos = 0;
		this.currentTask = tasks.get(0);
	}
	
	@Override
	public boolean isFinished(GPS position) {
		if (currentTask.isFinished(position)) {
			//change task if current one is finished
			currentTaskPos++;
			currentTask = tasks.get(currentTaskPos);
			if (currentTaskPos == tasks.size()) {
				//all tasks have finished ->
				//increase number of repetitions and set current task to the first of the list 
				currentRepetition++;
				currentTaskPos = 0;
				currentTask = tasks.get(0);
				
				if (currentRepetition == repetitions) {
					//all tasks have been repeated the specified time -> this task is finished
					return true;
				}
			}
		}
		return false;
	}

	public Task getCurrentTask() {
		return currentTask;
	}
}
