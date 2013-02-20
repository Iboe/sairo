/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.mission.Task;

/**
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
		return data;
	}
	
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
					
					//System.out.println("constructor:" +sTaskClass.getConstructors()[1].getParameterTypes()[0].getCanonicalName());
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return task;
	}
}
