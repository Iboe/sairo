/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.mission.Task;

/**
 * Manages the serialization and deserialization of {@link Task}s. The supported {@link Task}s can be configured in the configuration file,<br>
 * by defining mappings between the desired {@link Task}s and corresponding {@link SerializedTask}s for the de-/serialization.<br>
 * Before adding new {@link Task}s to the mapping list, an, appropriate de-/serialization class must be defined, which implements {@link SerializedTask}. 
 * 
 * @author Michael Kant
 *
 */
public class TaskSerializer {

	private static final Logger LOG = LoggerFactory.getLogger(TaskSerializer.class);
	
	static final String TASK_AMOUNT_PROPERTY = TaskSerializer.class.getSimpleName() + ".taskAmount";
	static final String TASKS_PROPERTY = TaskSerializer.class.getSimpleName() + ".tasks";
	static final String SERIALIZED_TASKS_PROPERTY = TaskSerializer.class.getSimpleName() + ".serializedTasks";
	
	
	private List< Class<? extends Task> > taskClasses;
	private List< Class<? extends SerializedTask> > serializedTaskClasses;
	
	/**
	 * Default constructor.<br>
	 * Initializes the class lists with the {@link Task} - {@link SerializedTask} mappings of the config file.
	 */
	public TaskSerializer(){
	
		Class<? extends Task> taskClass=null;
		Class<? extends SerializedTask> serializedTaskClass=null;
		int amount=0,loaded=0;
		String classString=null;
		
		taskClasses=new ArrayList< Class<? extends Task> >();
		serializedTaskClasses=new ArrayList< Class<? extends SerializedTask> >();
		
		LOG.info("Loading mapping list of Tasks and Serialized Tasks..");
		amount=Integer.parseInt(System.getProperty(TASK_AMOUNT_PROPERTY));
		LOG.debug("Size: "+amount);
		
		
		if(amount > 0){
			
			for(int i=0;i<amount;i++){
				
				try {
					
					classString=null;
					classString=System.getProperty(TASKS_PROPERTY+"."+i);
					taskClass=(Class<? extends Task>) Class.forName(classString);
					
					classString=System.getProperty(SERIALIZED_TASKS_PROPERTY+"."+i);
					serializedTaskClass=(Class<? extends SerializedTask>) Class.forName(classString);
					
					taskClasses.add(taskClass);
					serializedTaskClasses.add(serializedTaskClass);
					LOG.info("Loaded: "+taskClass.getSimpleName()+ " <-> " +serializedTaskClass.getSimpleName());
					loaded++;	
				} 
				catch (ClassNotFoundException e) {
					
					LOG.warn("Unable to load class '"+classString+"' at index "+i);
				}
			}
		}
		LOG.info(loaded+" task mappings loaded.");
		
	}
	
	/**
	 * Serializes a given {@link Task} and returns the serialized data as byte array.<br>
	 * First it looks for a valid mapping between the given {@link Task} and a respective {@link SerializedTask}.<br>
	 * If a mapping was found, an instance of the corresponding {@link SerializedTask} class will be created and the given {@link Task} object will be supplied.
	 * A call of the method {@link SerializedTask#getSerializedData()} will instigate the serialization of the given {@link Task} object.
	 * A leading byte will be added to the array, which describes the {@link Task} that was serialized.<br>
	 * This information is important for the subsequent deserialization, so it's aware of, which kind of {@link Task} needs to be deserialized.<br>
	 * If any of those steps fail, this method will return null.
	 * 
	 * @param t The task to be serialized.
	 * @return The serialized data, describing the {@link Task}, or null if the procedure encountered a problem.
	 */
	public byte[] serializeTask(Task t){
		
		byte[] serializedData,data=null;
		Class<? extends SerializedTask> sTaskClass=null;
		Constructor<? extends SerializedTask> sTaskConstructor=null;
		SerializedTask sTask;
		
		int i;
		
		if(t != null){
			
			for(i=0;i<taskClasses.size() && taskClasses.get(i) != t.getClass();i++);
			
			if(i < taskClasses.size()){
				
				sTaskClass=serializedTaskClasses.get(i);
				try {
					
					sTaskConstructor=sTaskClass.getConstructor(taskClasses.get(i));
					sTask=sTaskConstructor.newInstance(t);
					
					serializedData=sTask.getSerializedData();
					data=new byte[serializedData.length+1];
					data[0]=(byte)i;
					for(i=0;i<serializedData.length;data[i+1]=serializedData[i++]);
				} 
				catch (SecurityException e) {
					e.printStackTrace();
				} 
				catch (NoSuchMethodException e) {
					e.printStackTrace();
				} 
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				} 
				catch (InstantiationException e) {
					e.printStackTrace();
				} 
				catch (IllegalAccessException e) {
					e.printStackTrace();
				} 
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
			
		return data;
	}
	
	/**
	 * Deserializes the given data and returns a {@link Task} object.<br>
	 * First it reads the first byte of the data, which tells what {@link Task} was serialized. Then it looks for a valid mapping, based on the read byte value.<br>
	 * If a mapping was found, an instance of the corresponding {@link SerializedTask} class will be created and the given data will be supplied.
	 * A call of the method {@link SerializedTask#getTask()} will instigate the deserialization of the given data.
	 * If any of those steps fail, this method will return null.
	 * 
	 * @param data The serialized task data
	 * @return The Task, which was obtained out of the data, or null if the procedure encountered a problem.
	 */
	public Task deserializeTask(byte[] data){
		
		Task task=null;
		byte[] serializedData;
		int index=-1;
		Class<? extends SerializedTask> sTaskClass=null;
		Constructor<? extends SerializedTask> sTaskConstructor=null;
		SerializedTask sTask;
		
		if(data != null){
			
			index=(data[0] & 0xff);
			
			if(index < serializedTaskClasses.size()){
				
				serializedData=new byte[data.length-1];
				for(int i=0;i<serializedData.length;serializedData[i]=data[++i]);
				
				sTaskClass=serializedTaskClasses.get(index);
				
				try {
					
					sTaskConstructor=sTaskClass.getConstructor(byte[].class);
					sTask=sTaskConstructor.newInstance(serializedData);
					
					if(sTask.isValid()){
						
						task=sTask.getTask();
					}
					else {
						
						LOG.warn("deserializeTask() - Invalid task data received for desired task: "+ taskClasses.get(index).getSimpleName());
					}
						
				} 
				catch (SecurityException e) {
					e.printStackTrace();
				} 
				catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					e.printStackTrace();
				} 
				catch (InstantiationException e) {
					e.printStackTrace();
				} 
				catch (IllegalAccessException e) {
					e.printStackTrace();
				} 
				catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return task;
	}
}
