/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import de.fhb.sailboat.mission.Task;

/**
 * Represents a serialized {@link Task as task}. Classes 
 * implementing this interface should provide a serialization instruction
 * and a mechanism to validate the serialized data by {@link #isValid()}.
 * 
 * 
 * @author Michael Kant
 *
 */
public interface SerializedTask {

	/**
	 * Returns the {@link Task} class, associated with this {@link SerializedTask}. 
	 * @return
	 */
	public Class<? extends Task> getTaskType();
	
	/**
	 * Returns the serialized form of a {@link Task}. The concrete serialization is matter of the implementing class.
	 * @return The serialized {@link Task} as byte array.
	 */
	public byte[] getSerializedData();
	
	/**
	 * Returns the {@link Task} object, associated with this {@link SerializedTask} object.
	 * @return The serialized {@link Task} as byte array.
	 */
	public Task getTask();
	
	/**
	 * Tells, whether the {@link SerializedTask} is valid or not. It is valid, 
	 * if the serialized data matches with the unserialized {@link Task}.
	 * How this check is performed depends on the implementation. 
	 * 
	 * @return true, if the {@link SerializedTask} is valid, otherwise false.
	 */
	public boolean isValid();
	
}
