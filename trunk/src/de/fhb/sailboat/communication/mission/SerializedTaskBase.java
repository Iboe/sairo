/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.mission.Task;

/**
 * Base class for common {@link Task} serialization. Each {@link Task} to be supported <br>
 * should get a serialization class which derives from {@link SerializedTaskBase}.<br>
 * It provides basic properties and method declarations that are required for de-/serialization.
 *  
 * @author Michael Kant
 */
public abstract class SerializedTaskBase<T extends Task> implements SerializedTask {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedTaskBase.class);
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
	 * @param serializedData Binary data, respresenting the {@link Task} to be deserialized, including the checksum.
	 */
	public SerializedTaskBase(byte[] serializedData){
		
		if(serializedData != null){
			
			this.taskData=new byte[serializedData.length-1];
			int i=0;
			for(i=0;i<this.taskData.length;this.taskData[i]=serializedData[i++]);
			
			this.checksum=serializedData[i] & 0xff;
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
		
		Task task=getTask();
		return task != null ? task.getClass() : null;
	}
	
	/**
	 * Returns the serialized form of the associated {@link Task} including the checksum. If it doesn't exist yet, it will be generated.
	 */
	@Override
	public byte[] getSerializedData() {
		
		byte[] data=null,taskData=null;
		int i=0;
		
		taskData=getTaskData();
		
		if(taskData != null){
			
			data=new byte[taskData.length+1];
			for(i=0;i<taskData.length;data[i]=taskData[i++]);
			data[i]=(byte)checksum;
		}
		
		return data;
	}

	@Override
	public T getTask() {
		
		if(task == null){
		
			LOG.trace("getTask() - task is null, de-serializing task.");
			if(isValid())
				task=deserializeTask(taskData);
			else
				LOG.warn("getTask() - taskData is invalid.");
		}	
		return task;
	}
	
	/**
	 * Returns the serialized form of the associated {@link Task}.
	 * @return The serialized form of the associated {@link Task} as byte array.
	 */
	public byte[] getTaskData(){
		
		if(taskData == null){
			
			LOG.trace("getTaskData() - taskData is null, serializing task.");
			
			if(task != null)
				taskData=serializeTask(task);
			else
				LOG.warn("getTaskData() - no Task to serialize!");
			
			if(taskData != null)
				checksum=generateChecksum(taskData);
		}
		return taskData;
	}

	/**
	 * Generates the checksum of the serialized data and compares it with the given checksum.
	 * @return True, if the checksums match, otherwise false. 
	 */
	@Override
	public boolean isValid() {
		
		return generateChecksum(getTaskData()) == checksum && checksum != -1;
	}
	
	

	/**
	 * Generates a simple checksum, that sums up all byte values and caps the result with modulo 255.
	 * @param data The data to generate the checksum for.
	 * @return The checksum of the given data.
	 */
	public static int generateChecksum(byte[] data){
		
		int csum=-1;
		
		if(data != null)
			for(int i=0;i<data.length;i++)
				csum+=data[i] & 0xff;
		
		return csum & 0xff;
	}	
}
