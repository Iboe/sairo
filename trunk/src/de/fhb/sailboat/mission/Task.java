package de.fhb.sailboat.mission;

import de.fhb.sailboat.data.GPS;

/**
 * Interface for a task that is part of a {@link Mission}. A task is able to recognize if 
 * it is finished.
 * 
 * @author hscheel
 *
 */
public interface Task {

	//Endebedingungen: Zeit, Strecke, Linie erreicht
	
	/**
	 * Returns if the task has reached its goal.
	 * 
	 * @param position the current position 
	 * @return if the task is finished
	 */
	boolean isFinished(GPS position);
}
