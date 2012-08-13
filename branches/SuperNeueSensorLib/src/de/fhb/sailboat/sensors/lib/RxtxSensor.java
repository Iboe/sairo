package de.fhb.sailboat.sensors.lib;

import java.io.IOException;
import java.io.InputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;

public abstract class RxtxSensor extends Thread{
	
	private SerialPort serialPort;
	private int portNr;
	
	/**
	 * Connect to a serial Rxtx Sensor
	 * @param port
	 */
	public void connect(SerialCommPort port) {
		
		try {
			// open the port and save it to the local scope
			CommPortIdentifier identObject = getIdentifier(port.getPortNr());
			CommPort comPort = identObject.open("Sailboat", 1000);
			this.serialPort = (SerialPort) comPort;
			
			this.portNr = port.getPortNr();
		} catch (Exception e) {
			//LOG.info("Port " + port + " konnte nicht geöffnet werden.");
			System.out.println("COM " + port.getPortNr()+ " konnte nicht geöffnet werden.");
		}		
	}
	
	private CommPortIdentifier getIdentifier(int port){
		
		CommPortIdentifier identObject = null;
		
		// Create a port identifier Object
		try {
			identObject = CommPortIdentifier.getPortIdentifier("COM" + port);
			System.out.println("COM" + port + " wurde initialisiert.");
			//LOG.info("Port " + port + " initialized.");
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identObject;
	}
	
	protected InputStream getInputStream(){
		
		InputStream in = null;
		
		try {
			in = this.serialPort.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Input Stream von COM" + portNr + " konnte nicht geöffnet werden.");
			//LOG.info("Input Stream von COM" + port + " konnte nicht geöffnet werden.");
		}
		return in;
	}
	
	

	public void close() {
		this.serialPort.close();
	}
	
	public abstract void run();
}
