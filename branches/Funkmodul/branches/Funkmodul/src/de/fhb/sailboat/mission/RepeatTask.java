package de.fhb.sailboat.mission;

import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Task for repeated execution of other tasks. The subtasks are executed in the
 * order of their position in the list. Tasks can be repeated for a specified
 * number of times or infinite.
 * 
 * @author hscheel
 *
 */
public class RepeatTask implements Task {

	private final List<Task> tasks;
	private final int repetitions;
	private Task currentTask;
	private int currentTaskPos;
	private int currentRepetition;
	
	/**
	 * Creates a new instance with the given tasks, which are repeated n times,
	 * where repetitions specifies n. A number smaller than 0 will result in
	 * infinite repetition.
	 * Will throw an IllegalArgumentException if the task list is null, empty or 
	 * repetitions is 0.
	 * 
	 * @param tasks the tasks to be executed
	 * @param repetitions repeats the task n times, or infinite if < 0
	 */
	public RepeatTask(List<Task> tasks, int repetitions) {
		if (tasks == null || tasks.isEmpty() || repetitions == 0) {
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
			//change task if the current one is finished
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
