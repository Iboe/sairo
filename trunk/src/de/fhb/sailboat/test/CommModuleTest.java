package de.fhb.sailboat.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPClient;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPServer;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.communication.clientModules.MissionTransmitter;
import de.fhb.sailboat.communication.mission.SerializedReachCircleTask;
import de.fhb.sailboat.communication.mission.SerializedTask;
import de.fhb.sailboat.communication.mission.TaskSerializer;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.communication.serverModules.MissionReceiver;
import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.start.PropertiesInitializer;

/**
 * Dedicated (non-junit) test class for the components of the communication system.
 * 
 * @author Michael Kant
 *
 */
public class CommModuleTest {

	private CommunicationBase base;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CommModuleTest cmt;
		if(args.length > 0){
		
			PropertiesInitializer propInit=new PropertiesInitializer();
			propInit.initializeProperties();
			
			cmt=new CommModuleTest();
			if(args[0].compareToIgnoreCase("--server") == 0)
				cmt.startServer();
			else if(args[0].compareToIgnoreCase("--client") == 0)
				cmt.startClient();
			else
				System.out.println("Invalid mode specified!");
			
		}
		else{
			
			PropertiesInitializer propInit=new PropertiesInitializer();
			propInit.initializeProperties();
			
			cmt=new CommModuleTest();
			
			//testSerialTasks();
			cmt.testTaskSerializer();
			
			System.out.println("No mode specified!");
		}
	}
	
	public void startServer(){
		
		base=new CommTCPServer(6699);
		base.registerModule(new GPSTransmitter());
		base.registerModule(new MissionReceiver(base, new PlannerDummy()));
		
		if(!base.initialize())
			System.out.println("Unable to start the communications TCP server on port 6699");
		else 
			System.out.println("Server started.");
	}
	
	public void startClient(){
		
		MissionTransmitter plannerProxy;
		base=new CommTCPClient("127.0.0.1",6699);
		plannerProxy=new MissionTransmitter(base);
		base.registerModule(new GPSReceiver());
		base.registerModule(plannerProxy);
		
		if(!base.initialize())
			System.out.println("Unable to establish the TCP communication to the server.");
		else 
			System.out.println("Client started.");
			
		System.out.println("Press enter to continue..");
		System.out.println();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Now instigating the mission transmission...");
		//Task t=new ReachCircleTask(new GPS(52.34567,24.56789), 1337);
		Mission m=new MissionImpl();
		List<Task> taskList=new ArrayList<Task>();
		taskList.add(new ReachCircleTask(new GPS(52.34567,24.56789), 1337));
		taskList.add(new HoldAngleToWindTask(27));
		taskList.add(new ReachCircleTask(new GPS(12.345,54.321), 2710));
		taskList.add(new ReachCircleTask(new GPS(10.1101,13.337), 7353));
		taskList.add(new ReachPolygonTask(getTestPolygon()));
		m.setTasks(taskList);
		
		for(Task t : taskList)
			System.out.println("Attempting to transmit task: "+t);
		
		plannerProxy.doMission(m);
		
	}
	
	private List<GPS> getTestPolygon(){
		
		List<GPS> polygon=new LinkedList<GPS>();
		
		polygon.add(new GPS(12.345,23.456));
		polygon.add(new GPS(34.567,45.678));
		polygon.add(new GPS(9.8765,8.7654));
		polygon.add(new GPS(6.5432,5.4321));
		return polygon;
	}
	
	public void testSerialTasks(){
		
		ReachCircleTask t=new ReachCircleTask(new GPS(52.12453,24.84726),1337);
		SerializedTask st=new SerializedReachCircleTask(t);
		System.out.println("Task and serialized task instance created.");
		System.out.println("TASK: "+t);
		byte[] data=st.getSerializedData();
		
		if(data != null){
			
			System.out.println("-Serialized Data is("+data.length+"):");
			for(int i=0;i<data.length;System.out.print((data[i++]&0xff)+" "));
		}
		System.out.println();
		System.out.println("-Task valid: "+st.isValid());
		
		System.out.println("deserializing with correct data..");
		//int checksum=SerializedTaskBase.generateChecksum(st.getSerializedData());
		SerializedTask st2=new SerializedReachCircleTask(st.getSerializedData());
		data=st2.getSerializedData();
		System.out.println("-Serialized Data is("+data.length+"):");
		//System.out.println("-checksum: "+checksum);
		for(int i=0;i<data.length;System.out.print((data[i++]&0xff)+" "));
		System.out.println();
		System.out.println("-Task valid: "+st2.isValid());
		System.out.println("-Task type is: "+st2.getTaskType().getSimpleName());
		System.out.println("-Task: "+st2.getTask());
		
		System.out.println("now passing invalid data");
		data=st.getSerializedData();
		data[2]=27;
		SerializedTask st3=new SerializedReachCircleTask(data);
		data=st3.getSerializedData();
		System.out.println("-Serialized Data is("+data.length+"):");
		for(int i=0;i<data.length;System.out.print((data[i++]&0xff)+" "));
		System.out.println("-Task valid: "+st3.isValid());
		System.out.println("-Task type is: "+st3.getTaskType().getSimpleName());
		System.out.println("-Task: "+st3.getTask());
	}
	
	public void testTaskSerializer(){
		
		TaskSerializer ts;
		Task task=new ReachCircleTask(new GPS(52.123456, 24.56789), 1337);
		Task dTask;
		byte[] serializedData;
		
		System.out.println("Given task: "+task);
		ts=new TaskSerializer();
		
		System.out.println("Serializing..");
		serializedData=ts.serializeTask(task);
		
		if(serializedData != null){
			
			System.out.print("Task serialized: ");
			for(int i=0;i<serializedData.length;System.out.print((serializedData[i++]&0xff)+" "));
			System.out.println();
			
			System.out.println("De-Serializing correct data..");
			
			dTask=ts.deserializeTask(serializedData);
			
			if(dTask != null)
				System.out.println("DeserializedTask: "+dTask);
			
			System.out.println("De-Serializing incorrect data..");
			
			serializedData[4]=27;
			dTask=ts.deserializeTask(serializedData);
			
			if(dTask != null)
				System.out.println("DeserializedTask: "+dTask);
			
		}
	}

	
	public static class PlannerDummy implements Planner {

		@Override
		public void doMission(Mission mission) {
			
			System.out.println("Received Mission("+mission.getTasks().size()+" Tasks):");
			for(Task t:mission.getTasks())
				System.out.println("Task: " + t);
		}

		@Override
		public void stop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void doPrimitiveCommand(PrimitiveCommandTask task) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
