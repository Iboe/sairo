package de.fhb.sailboat.serial.serialAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * ComPort wrapper Klasse für den vereinfachten Zugriff auf Serielle Schnittstellen
 * @author mstrehse
 *
 */
public class COMPort{
	
	private CommPortIdentifier _identObject = null;
	private SerialPort _serialPortObject = null;
	private int _baud = 9600;

	/**
	 * Construktor der COM Klasse
	 * @param int port Nr des Com Ports
	 * @param int baud Baudrate wenn 0 dann standartmäßig 9600
	 * @param int timeout Timeout für Verbindungen in ms (noch nicht vollständig implementiert)
	 */
	public COMPort(int port, int baud, int timeout){
		
		// timeout is not implemented
		
		if(baud != 0){
			this._baud = baud;
		}
		
		CommPortIdentifier identObject = getIdentifier(port);
		this._identObject = identObject;
	}
	
	private CommPortIdentifier getIdentifier(int port){
		
		CommPortIdentifier identObject = null;
		
		// Create a port identifier Object
		try {
			identObject = CommPortIdentifier.getPortIdentifier("COM" + port);
			System.out.println("COM" + port + " wurde initialisiert.");
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identObject;
	}
	
	/**
	 * öffnet den com port
	 */
	public void open(){
		
		// get a comPort object and cast it to a serial port, if it is one
		try {
			CommPort comPortObject = this._identObject.open("COMPortApi", 1000);
			if(this._identObject.getPortType() == 1){
				// es ist ein serieller Port
				SerialPort serialPortObject = (SerialPort)comPortObject;
				
				try {
				
					serialPortObject.setSerialPortParams(
							this._baud, 
							// DATABITS_8 passt eigentlich für jedes Gerät
							SerialPort.DATABITS_8, 
							// STOPBITS_1 ist für jedes Gerät außer ganz alten Kram geeignet
							SerialPort.STOPBITS_1, 
							// Paritätsbit wird keines benötigt
							SerialPort.PARITY_NONE
							// nähere Informationen: http://en.wikipedia.org/wiki/Serial_port#Data_bits
					);
					
				} catch (UnsupportedCommOperationException ex) {
					// TODO Auto-generated catch block
					System.out.println("Da ist etwas schief gelaufen. Fehlercode: 758436");
					System.err.println(ex.getMessage());
				}
				
				this._serialPortObject = serialPortObject;
			}else{
				// paralleler Port (nicht unterstützt)
			}
			
		} catch (PortInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * schließt den com port
	 */
	public void close(){
		this._serialPortObject.close();
	}
	
	/**
	 * Gibt eine komplette Zeile zurück
	 * @return String line
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return new BufferedReader(new InputStreamReader(this._serialPortObject.getInputStream())).readLine();
	}
	
	/**
	 * Gibt ein einzelnes Byte zurück
	 * @return int Byte
	 * @throws IOException
	 */
	public int readByte() throws IOException {
		return this._serialPortObject.getInputStream().read();
	}
	
	/**
	 * Schreibt einen String in einen comPort
	 * @param String text
	 * @throws IOException
	 */
	public void writeString(String text) throws IOException {
		new OutputStreamWriter(this._serialPortObject.getOutputStream()).write(text);
	}
}