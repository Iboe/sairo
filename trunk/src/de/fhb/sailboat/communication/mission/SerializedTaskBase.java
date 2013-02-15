/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import de.fhb.sailboat.mission.Task;

/**
 * Base class for common Task serialization. Each Task to be supported 
 * should get serialization class which derives from {@link SerializedTaskBase}.
 * It provides basic properties and method declarations that are required for de-/serialization.
 *  
 *  
 * @author Michael Kant
 */
public abstract class SerializedTaskBase<T extends Task> implements SerializedTask {

	protected T task;
	protected byte[] taskData;
	protected int checksum;
	
	public SerializedTaskBase(T task){
		
		this.task=task;
	}
	
	public SerializedTaskBase(byte[] taskData, int checksum){
		
		if(taskData != null){
			
			this.taskData=new byte[taskData.length];
			for(int i=0;i<taskData.length;this.taskData[i]=taskData[i++]);
			
			this.checksum=checksum;
		}
	}
	
	protected abstract byte[] serializeTask(T t);
	protected abstract T deserializeTask(byte[] data);

	@Override
	public Class<? extends Task> getTaskType() {
		
		return task.getClass();
	}
	
	/**
	 * Returns the serialized form of the associated task. If it doesn't exist yet, it will be generated.
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
