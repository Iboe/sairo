package de.fhb.sailboat.test;

import java.io.IOException;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPClient;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPServer;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.communication.clientModules.MissionTransmitter;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.communication.serverModules.MissionReceiver;
import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.PrimitiveCommandTask;
import de.fhb.sailboat.mission.Task;
import de.fhb.sailboat.test.Initializier.PropertiesInitializer;

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
			int[] arr1={1,4,7,8,4,3,5};
			int[] arr2={0,0,0,0,0,0,0};
			
			for(int i=0;i<arr1.length;System.out.print(arr1[i++]+" "));
			System.out.println();
			for(int i=0;i<arr1.length;System.out.print(arr2[i++]+" "));
			System.out.println();
			for(int i=0;i<arr1.length;arr2[i]=arr1[i++]);
			
			for(int i=0;i<arr1.length;System.out.print(arr1[i++]+" "));
			System.out.println();
			for(int i=0;i<arr1.length;System.out.print(arr2[i++]+" "));
			System.out.println();
			
			
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
		plannerProxy.doMission(new MissionImpl());
		
	}
	

	public static class PlannerDummy implements Planner {

		@Override
		public void doMission(Mission mission) {
			
			System.out.println("Received Mission:");
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
