/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import de.fhb.sailboat.mission.Task;

/**
 * Base class for common {@link Task} serialization. Each {@link Task} to be supported <br>
 * should get a serialization class which derives from {@link SerializedTaskBase}.<br>
 * It provides basic properties and method declarations that are required for de-/serialization.
 *  
 * @author Michael Kant
 */
public abstract class SerializedTaskBase<T extends Task> implements SerializedTask {

	/**
	 * The Task instance, associated to this {@link SerializedTask}.
	 */
	protected T task;
	
	/**
	 * The serialized version of the associated {@link Task}.
	 */
	protected byte[] taskData;
	
	/**
	 * A checksum that ensures the validity of the serialized data.
	 */
	protected int checksum;
	
	/**
	 * Initialization constructor. Takes a {@link Task} to be serialized.
	 * 
	 * @param task The {@link Task} to be serialized.
	 */
	public SerializedTaskBase(T task){
		
		this.task=task;
	}
	
	/**
	 * Initialization constructor. Takes a byte array that represents a serialized {@link Task}, along with its checksum.
	 * 
	 * @param taskData Binary data, respresenting the {@link Task} to be deserialized.
	 * @param checksum Corresponding checksum to validate the serialized data.
	 */
	public SerializedTaskBase(byte[] taskData, int checksum){
		
		if(taskData != null){
			
			this.taskData=new byte[taskData.length];
			for(int i=0;i<taskData.length;this.taskData[i]=taskData[i++]);
			
			this.checksum=checksum;
		}
	}
	
	/**
	 * Serializes the given {@link Task} and returns the respective binary data. <br>
	 * The format of that data depends on the concrete implementation.
	 * 
	 * @param t The {@link Task} to be serialized.
	 * @return The serialized data as byte array.
	 */
	protected abstract byte[] serializeTask(T t);
	
	/**
	 * Deserializes a given array of binary data and creates a {@link Task} object out of it.<br>
	 * If the serialized data is invalid and can't be interpreted, it will return null.
	 * The format of that data depends on the concrete implementation.
	 * 
	 * @param data The data to be deserialized.
	 * @return An instance of the corresponding {@link Task} or null if the serialized data was bad.
	 */
	protected abstract T deserializeTask(byte[] data);

	@Override
	public Class<? extends Task> getTaskType() {
		
		return task.getClass();
	}
	
	/**
	 * Returns the serialized form of the associated {@link Task}. If it doesn't exist yet, it will be generated.
	 */
	@Override
	public byte[] getSerializedData() {
		
		if(taskData == null){
			
			taskData=serializeTask(task);
			checksum=generateChecksum(taskData);
		}
		
		return taskData;
	}

	@Override
	public T getTask() {
		
		if(task == null)
			if(isValid())
				task=deserializeTask(taskData);
			
		return task;
	}

	/**
	 * Generates the checksum of the serialized data and compares it with the given checksum.
	 * @return True, if the checksums match, otherwise false. 
	 */
	@Override
	public boolean isValid() {
		
		return generateChecksum(getSerializedData()) == checksum;
	}

	/**
	 * Generates a simple checksum, that sums up all byte values and caps the result with modulo 255.
	 * @param data The data to generate the checksum for.
	 * @return The checksum of the given data.
	 */
	protected int generateChecksum(byte[] data){
		
		int csum=-1;
		
		if(data != null)
			for(int i=0;i<data.length;i++)
				i+=data[i] & 0xff;
		
		return csum & 0xff;
	}	
}
