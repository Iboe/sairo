package de.fhb.sailboat.serial.serialAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.log4j.Logger;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

/**
 * ComPort wrapper Klasse f�r den vereinfachten Zugriff auf Serielle Schnittstellen
 * @author mstrehse
 *
 */
public class COMPort{
	
	private CommPortIdentifier _identObject = null;
	private SerialPort _serialPortObject = null;
	private int _baud = 9600;
	private static final Logger LOG = Logger.getLogger(COMPort.class);
	private static final int SEND_WAIT = 0;//Integer.parseInt(System.getProperty(COMPort.class.getSimpleName() + ".wait_sleep")); // Wait between each written Byte in Milliseconds
	/**
	 * Construktor der COM Klasse
	 * @param int port Nr des Com Ports
	 * @param int baud Baudrate wenn 0 dann standartm��ig 9600
	 * @param int timeout Timeout f�r Verbindungen in ms (noch nicht vollst�ndig implementiert)
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
			//System.out.println("COM" + port + " wurde initialisiert.");
			LOG.info("Port " + port + " initialized.");
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return identObject;
	}
	
	/**
	 * �ffnet den com port
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
							// DATABITS_8 passt eigentlich f�r jedes Ger�t
							SerialPort.DATABITS_8, 
							// STOPBITS_1 ist f�r jedes Ger�t au�er ganz alten Kram geeignet
							SerialPort.STOPBITS_1, 
							// Parit�tsbit wird keines ben�tigt
							SerialPort.PARITY_NONE
							// n�here Informationen: http://en.wikipedia.org/wiki/Serial_port#Data_bits
					);
					
				} catch (UnsupportedCommOperationException ex) {
					// TODO Auto-generated catch block
					System.out.println("Da ist etwas schief gelaufen. Fehlercode: 758436");
					System.err.println(ex.getMessage());
				}
				
				this._serialPortObject = serialPortObject;
				
				
			}else{
				// paralleler Port (nicht unterst�tzt)
			}
			
		} catch (PortInUseException e) {
			LOG.info("PortInUse", e);
		}
	}
	
	/**
	 * schlie�t den com port
	 */
	public void close(){
		this._serialPortObject.close();
	}
	
	/**
	 * Gibt eine komplette Zeile zur�ck
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
	 * Gibt ein einzelnes Byte zur�ck
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
	 * @param sleep
	 * @throws IOException
	 */
	public void writeString(String text,int sleep) throws IOException {
		try {
			OutputStream os = this._serialPortObject.getOutputStream();
			byte[] b = text.getBytes();

			for (int i = 0; i < b.length; i++) {
				os.write(b[i]);
				// minimal sleep to give Devices time to handle the data
				Thread.sleep(sleep);
			}
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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
}