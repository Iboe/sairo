package de.fhb.sailboat.mission;

import org.apache.log4j.Logger;

import de.fhb.sailboat.data.GPS;

/**
 * Task for a beating behavior. This is needed, when the goal of the boat can not be reached directly 
 * under the current wind condition. The task is finished when the goal can be reached directly again.
 * Afterwards, the original task containing the goal can be continued.  
 * 
 * @author hscheel
 *
 */
public class BeatTask implements Task {

	private static final Logger LOG = Logger.getLogger(BeatTask.class);
	
	private final GPS goal;
	private final Task continueTask;
	
	/**
	 * Creates a new instance with the {@link GPS} point that is to be reached.
	 * 
	 * @param goal the goal that can not be reached directly
	 */
	public BeatTask(GPS goal) {
		this(null, goal);
		LOG.debug(this.toString());
	}

	/**
	 * Creates a new instance with the {@link GPS} point to be reached and the {@link Task}, 
	 * that was executed before and is to be continued after the beating.
	 * 
	 * @param continueTask the {@link Task} to be continued afterwards, must not be another {@link BeatTask}
	 * @param goal the goal that can not be reached directly
	 */
	public BeatTask(Task continueTask, GPS goal) {
		if (continueTask instanceof BeatTask) {
			throw new IllegalArgumentException("must not execute a BeatTask after a BeatTask");
		}
		this.continueTask = continueTask;
		this.goal = goal;
		LOG.debug(this.toString());
	}
	
	/*
	 * Always returns <code>false</code>, since the task should just be canceled by the {@link BeatWorker}
	 * that executes this task. This is needed to continue the original task. 
	 */
	@Override
	public boolean isFinished(GPS position) {
		//TODO this implementation causes a high coupling between BeatTask and BeatWorker and breaks up the
		//use of the isFinished method, so a better solution should be found
		LOG.debug(this.toString() + " finished");
		return false;
	}

    /**
	 * Getter for the {@link Task} to be continued after the beating.
	 * 
	 * @return the {@link Task} whose execution should be continued
	 */
	public Task getContinueTask() {
		return continueTask;
	}

    /**
	 * Getter for the {@link GPS} position of the current goal.
	 * 
	 * @return the position of the current goal 
	 */
	public GPS getGoal() {
		return goal;
	}

	@Override
	public String toString() {
		return MissionSystemTextEnum.BEATTASK + " [continueTask=" + continueTask + ", goal=" + goal + "]";
	}
}
