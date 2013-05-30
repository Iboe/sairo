/**
 * 
 */
package de.fhb.sailboat.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.fhb.sailboat.communication.*;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPClient;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPServer;
import de.fhb.sailboat.communication.clientModules.CompassReceiver;
import de.fhb.sailboat.communication.clientModules.GPSReceiver;
import de.fhb.sailboat.communication.serverModules.CompassTransmitter;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Dedicated testing class for the communication component in the de.fhb.sailboat.communication package.
 * 
 * @author Michael Kant
 */
@Deprecated
public class RCommTest {

	/**
	 * Entry point of the test application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) throws InterruptedException{
		
		System.out.println("Test of the communications component starting.\n");
		
		Thread.sleep(2000);
		System.out.println("Creating Server endpoint, listening on port 6699..");
		CommunicationBase server=new CommTCPServer(6699);
		System.out.println("..done.");
		Thread.sleep(1000);
		System.out.println("Registering modules...");
		//server.registerModule(new SimpleClientTModule());
		//server.registerModule(new FastServerTModule());
		
		WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(52.246555,12.323096,System.currentTimeMillis()));
		server.registerModule(new GPSTransmitter());
		WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(-17.76,0,0,System.currentTimeMillis()));
		server.registerModule(new CompassTransmitter());
		
		System.out.println("..done.");
		Thread.sleep(1000);
		System.out.println("Initializing Server endpoint..");
		if(server.initialize())
			System.out.println("..done.");
		else{
			System.err.println("..FAILED.");
			System.exit(1);
		}
		System.out.println("-------------------------");
		Thread.sleep(2000);
		System.out.println("Creating Client endpoint, connecting to localhost:6699..");
		CommunicationBase client = new CommTCPClient("127.0.0.1", 6699);
		System.out.println("..done.");
		Thread.sleep(1000);
		System.out.println("Registering modules...");
		//client.registerModule(new SimpleServerTModule());
		//client.registerModule(new FastClientTModule());
		client.registerModule(new GPSReceiver());
		client.registerModule(new CompassReceiver());
		System.out.println("..done.");
		Thread.sleep(1000);
		System.out.println("Initializing Client endpoint..");
		if(client.initialize())
			System.out.println("..done.");
		else{
			System.err.println("..FAILED.");
			System.exit(1);
		}
		System.out.println("-------------------------");
		System.out.println("Waiting 10 seconds for transmissions.");
		Thread.sleep(10000);
		System.out.println("-------------------------");
		System.out.println("Shutting down the client endpoint..");
		client.shutdown();
		System.out.println("..done.");
		Thread.sleep(1000);
		System.out.println("Shutting down the server endpoint..");
		server.shutdown();
		System.out.println("..done.");
	}
	
	
	
	public static class SimpleClientTModule implements TransmissionModule{

		@Override
		public void receivedObject(DataInputStream stream) throws IOException {
			
			int number=stream.readInt();
			
			System.out.println("Received integer value: "+number);
		}

		@Override
		public void requestObject(DataOutputStream stream) {
			
			System.out.println("Client NOOP");
		}

		@Override
		public int getTransmissionInterval() {
			
			return 0;
		}

		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public boolean skipNextCycle() {
			return false;
		}

		@Override
		public void connectionReset() {
			
		}
		
	}

	public static class SimpleServerTModule implements TransmissionModule{
		
		@Override
		public void receivedObject(DataInputStream stream) throws IOException {
			
			System.out.println("An object was received but not expected");
		}

		@Override
		public void requestObject(DataOutputStream stream) throws IOException {
			
			System.out.println("Sending requested value (1337)");
			stream.writeInt(1337);
		}

		@Override
		public int getTransmissionInterval() {
			
			return 2000;
		}
		
		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public boolean skipNextCycle() {
			return false;
		}

		@Override
		public void connectionReset() {
			
		}
	}
	
	public static class FastClientTModule implements TransmissionModule{

		@Override
		public void receivedObject(DataInputStream stream) throws IOException {
			
			float number=stream.readFloat();
			
			System.out.println("Received float value: "+number);
		}

		@Override
		public void requestObject(DataOutputStream stream) {
			
			System.out.println("Client NOOP");
		}

		@Override
		public int getTransmissionInterval() {
			
			return 0;
		}
		
		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public boolean skipNextCycle() {
			return false;
		}

		@Override
		public void connectionReset() {
			
		}
		
	}

	public static class FastServerTModule implements TransmissionModule{
		
		@Override
		public void receivedObject(DataInputStream stream) throws IOException {
			
			System.out.println("An object was received but not expected");
		}

		@Override
		public void requestObject(DataOutputStream stream) throws IOException {
			
			System.out.println("Sending requested value (2710.1987)");
			stream.writeFloat(2710.1987f);
		}

		@Override
		public int getTransmissionInterval() {
			
			return 500;
		}
		
		@Override
		public int getPriority() {
			return 0;
		}

		@Override
		public boolean skipNextCycle() {
			return false;
		}

		@Override
		public void connectionReset() {
			
		}
	}
}
