package de.fhb.sailboat.serial.serialAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.apache.log4j.Logger;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * ComPort wrapper Klasse für den vereinfachten Zugriff auf Serielle Schnittstellen
 * @author mstrehse
 * @version 1.2
 */
public class COMPort{
	
	private CommPortIdentifier _identObject = null;
	private SerialPort _serialPortObject = null;
	private int _baud = 9600;
	private static final Logger LOG = Logger.getLogger(COMPort.class);
	private static final int SEND_WAIT = 0;//Integer.parseInt(System.getProperty(COMPort.class.getSimpleName() + ".wait_sleep")); // Wait between each written Byte in Milliseconds
	
	private int port=0;
	/**
	 * Construktor der COM Klasse
	 * @param int port Nr des Com Ports
	 * @param int baud Baudrate wenn 0 dann standartmäßig 9600
	 * @param int timeout Timeout für Verbindungen in ms (noch nicht vollständig implementiert)
	 */
	public COMPort(int port, int baud, int timeout){
		this.port=port;
		// timeout is not implemented
		
		if(baud != 0){
			this._baud = baud;
		}
		
		CommPortIdentifier identObject = getIdentifier(port);
		if(identObject!=null){
		this._identObject = identObject;
		}
		else{
			throw new NullPointerException(this.getClass().getSimpleName() + " identObject is null");
		}
	}
	
	private CommPortIdentifier getIdentifier(int port){
		
		CommPortIdentifier identObject = null;
		
		// Create a port identifier Object
		try {
			identObject = CommPortIdentifier.getPortIdentifier("COM" + port);
			LOG.info("COM" + port + " wurde initialisiert.");
			LOG.info("Port " + port + " initialized.");
		} catch (NoSuchPortException e) {
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
					LOG.info("Da ist etwas schief gelaufen. Fehlercode: 758436");
					System.err.println(ex.getMessage());
				}
				
				this._serialPortObject = serialPortObject;
				
				
			}else{
				// paralleler Port (nicht unterstützt)
			}
			
		} catch (PortInUseException e) {
			LOG.info("PortInUse", e);
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
	public String[] readLine(int rows) throws IOException {
 		String[] rowArray = new String[rows];
		BufferedReader bufReader = new BufferedReader(new InputStreamReader(this._serialPortObject.getInputStream()));
		for(int i = 0; i< rows; i++){
			try{
			rowArray[i] =  bufReader.readLine();
			//System.out.println(rowArray[i]);
			}
			catch(IOException ex)
			{
				i--;
				continue;
			}			
		}
		return rowArray;
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
		this.writeString(text, SEND_WAIT);
	}
	/**
	 * Write bytewise to output-Stream of a given Com-Port
	 * 
	 * @param text
	 * @param wait_sleep
	 * @throws IOException
	 */
	public void writeString(String text,long wait_sleep) throws IOException {
		try {
			OutputStream os = this._serialPortObject.getOutputStream();
			byte[] b = text.getBytes();
			//Byte r, expected = 0x65;
			
			int loopLength=b.length; //T.Koppe : Loop Counter auslagern => schneller
			for (int i = 0; i < loopLength; i++) {
				os.write(b[i]);
				// minimal sleep to give Devices time to handle the data
				Thread.sleep(wait_sleep);

			}
//			r = (byte) readByte();
//			if(r != expected) {
//				LOG.info("Couldn't execute Command: "+ r);
//			} else {
//				// We expect everything went well:
//
//			}		
		} catch (InterruptedException e) {
			LOG.warn("Sleep-Failure");
		}
	}
	

	/**
	 * Write bytewise to OutputStream
	 * @param b
	 * @throws IOException
	 * @author S. Schmidt
	 */
	public void writeByte(byte b) throws IOException {
		
		this._serialPortObject.getOutputStream().write(b);
	}
	
	/**
	 * Write character to Outputstream
	 * @param c
	 * @throws IOException
	 * @author Tobias Koppe
	 * @since 1.2
	 */
	public void writeByte(char c) throws IOException{
		this._serialPortObject.getOutputStream().write((byte)c);
	}

	public CommPortIdentifier get_identObject() {
		return _identObject;
	}

	public void set_identObject(CommPortIdentifier _identObject) {
		this._identObject = _identObject;
	}

	public SerialPort get_serialPortObject() {
		return _serialPortObject;
	}

	public void set_serialPortObject(SerialPort _serialPortObject) {
		this._serialPortObject = _serialPortObject;
	}

	public int get_baud() {
		return _baud;
	}

	public void set_baud(int _baud) {
		this._baud = _baud;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}