package de.fhb.sailboat.ufer.prototyp.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.fhb.sailboat.mission.Mission;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class ConnectorUfer {

	public static final int UPDATE_RATE = 500; // Sleep in ms between every attempt to listen/ send (loop)
	public static final int PORT = 4444;
	
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	
	private Mission mission;
	private boolean sendMission = false;

	public ConnectorUfer(String server_adress) {
		Socket socket = null;
		InputStream is = null;

		try {
			socket = new Socket(server_adress, PORT);

			is = socket.getInputStream();

			in = new ObjectInputStream(is);
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		startConnection();
	}
	
	public void startConnection() {
    	Runnable updater = new Runnable() { 
			public void run() {
				while(true) {
					receiveWorldModel();
					
					if (sendMission) try {
						out.writeObject(mission);
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					finally {
						sendMission = false;
					}
				}
			}
		};
		new Thread(updater).start();
    }
	
	public WorldModelImpl receiveWorldModel() {
		WorldModelImpl wm = null;
		try {
			wm = (WorldModelImpl)in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return wm;
	}
	
	public void sendMission(Mission mission) {
		if (mission != null) {
			this.mission = mission;
			sendMission = true;
		}
	}
	
}
