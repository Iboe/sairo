package de.fhb.sailboat.serial.actuator;

import java.io.IOException;

import org.apache.log4j.Logger;

import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/***
 * Implementiert die Schnitstellen IAKSENLocomotion und Locomotionsystem zur
 * Ansteuerung des AKSEN-Boards
 * 
 * AKSEN-Board
 * 1. openConnection mit einem Com-Port
 * 2. Sende Daten an AKSEN-Board
 * 
 * AKSEN-Board Kommandos :
 * - sende 's' zum Aufbauen einer Com-Port Verbindung
 * - sende 'e' um den Abschluss eines Befehles zu markieren
 * - empfange 'a' als Best�tigung der Datensendung
 * - empfange 'n' bei Fehler auf dem AKSEN-Board
 * - empfange 'e' nach Abarbeitung der Befehle auf dem AKSEN-Board
 * 
 * @author Tobias Koppe
 * @version 1
 * 
 */

//TODO
public class AKSENLocomotionImpl implements IAKSENLocomotion,LocomotionSystem{

	private COMPort comPort;
	private final WorldModel worldModel;
	private static final Logger LOG = Logger.getLogger(AKSENLocomotionImpl.class);
	private static final String COM_PORT = System.getProperty(AKSENLocomotionImpl.class.getSimpleName() + ".comPort");
	private static final String BAUDRATE = System.getProperty(AKSENLocomotionImpl.class.getSimpleName() + ".baudrate");
	private static long waitingTime=0;
	
	private boolean debug = false;
	
	private int status;
	
	public AKSENLocomotionImpl(){
		worldModel = WorldModelImpl.getInstance();
		comPort = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		comPort.open();
	}
	
	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSail(int angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRudder(int angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropellor(int angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRudder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetSail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPropellor() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBatteryState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDebug(boolean debug) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getWait_Sleep() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setWait_Sleep(int wait_sleep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean openConnection() {
		Byte send,receive,expected;
		send = 0x73;
		expected = 0x61;
		return false;
	}

	@Override
	public boolean sendCommand(int pServo, int pValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendCommandSet(String[] pCommandSet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String readState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeData(String data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void writeData(Byte data){
		try {
			this.comPort.writeByte(data);
		} catch (IOException e) {
			//TODO IOException Behandlung hinzufuegen
		}
	}

	@Override
	public void incWaitingTime() {
		this.setWaitingTime(this.getWaitingTime()+1);
	}

	@Override
	public void decWaitingTime() {
		this.setWaitingTime(this.getWaitingTime()-1);
	}

	public COMPort getComPort() {
		return comPort;
	}

	public void setComPort(COMPort comPort) {
		this.comPort = comPort;
	}

	public static long getWaitingTime() {
		return waitingTime;
	}

	public static void setWaitingTime(long waitingTime) {
		AKSENLocomotionImpl.waitingTime = waitingTime;
	}

	public WorldModel getWorldModel() {
		return worldModel;
	}

	public static Logger getLog() {
		return LOG;
	}

	public static String getBaudrate() {
		return BAUDRATE;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}