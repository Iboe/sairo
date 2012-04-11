package de.fhb.sailboat.serial.sensor;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Observable;

/**
 * Serial Port Factory parallel Version for OutputStream ==> needs to be merged
 * with original sairoComm!
 * 
 * @author schmidst
 * 
 */
public class sairoComm2 extends Observable {

	// Status der Verbindung (0: inaktiv, 1: aktiv)
	private int status;

	// The comPort that is used in this Instance of the Sairo Comm
	private String comPort;
	private int baudrate = 9600;
	private int dataBits = SerialPort.DATABITS_8;
	private int stopBits = SerialPort.STOPBITS_1;
	private int parity = SerialPort.PARITY_NONE;
	private boolean eventreader = false; // Use Events instead of
											// Constant-Stream
	private boolean charReader = true; // Read Bitwise/Charwise instead of
										// Linewise (\n or \r seperated)

	// The String with the data of the Device
	private String dataString;

	private String strInput = "";
	private String strOutput = "";

	// String-Array with x Fields
	private String[] sampleBuffer = new String[10];

	SerialPort serialPort;
	InputStream inputStream;
	OutputStream outputStream;
	Thread inputThread;

	/**
	 * The Constructor that needs a comPort-Name for doing its work
	 * 
	 * @param String
	 *            comPort
	 * @param int baudrate
	 */
	public sairoComm2(String comPort, int baudrate) {
		this(comPort, baudrate, false);
	}

	public sairoComm2(String comPort, int baudrate, boolean eventreader) {
		this(comPort, baudrate, eventreader, true);
	}

	public sairoComm2(String comPort, int baudrate, boolean eventreader,
			boolean charRead) {
		this.comPort = comPort; // ComPort (Win. e.g.: Com3, Unix e.g.:
								// /dev/ttyS0)
		this.baudrate = baudrate;
		this.eventreader = eventreader;
		this.charReader = charRead;

		this.dataBits = SerialPort.DATABITS_8;
		this.stopBits = SerialPort.STOPBITS_1;
		this.parity = SerialPort.PARITY_NONE;
		// den Status aktualisieren
		this.status = 0;
	}

	/**
	 * The Connect method, that starts the writing of the dataString and does
	 * all the connecting stuff
	 */
	public void connect() throws Exception {

		if (this.status == 0) {
			// try to create a serialPort Object by calling the
			// sairoConn.getSerialPort
			// method. Variables are stored inside the Class itself
			try {
				this.serialPort = this.getSerialPort();
				// Get an OutputStream by calling the sairoConn.getOutputStream
				// method

				this.outputStream = this.serialPort.getOutputStream();

				// Get an InputStream by calling the sairoConn.getInputStream
				// method

				this.inputStream = this.serialPort.getInputStream();

				// Start the Thread with the OutputStream and an Instance of the
				// active object type sairoConn
				// Thread output = (new Thread(new
				// SerialWriter(this.outputStream, this)));
				// output.start();

				// TODO EventListener
				if (this.eventreader) {
					this.serialPort.addEventListener(new SerialEventReader(
							this.inputStream, this));
					this.serialPort.notifyOnDataAvailable(true);

				} else {
					// Start the Thread with the InputStream and an Instance of
					// the active object type sairoConn
					Thread input = (new Thread(new SerialReader(
							this.inputStream, this)));
					input.start();
				}

				this.status = 1;
			} catch (PortInUseException e) {
				System.out.println("Port belegt");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			throw new Exception("Connection already open.");
		}
	}

	public int getStatus() {
		return this.status;
	}

	/**
	 * Returns a serial object by a given portname
	 * 
	 * @param portName
	 *            name of the port (e.g. COM3)
	 * @return
	 */
	public SerialPort getSerialPort() throws Exception {

		// Instanciation and declaration
		SerialPort serialPort = null;
		String portName = this.comPort;

		// generate a commPortIdentifier
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(portName);

		// open the comPort. the method .open returns an object with the type
		// CommPort
		CommPort commPort = portIdentifier
				.open(this.getClass().getName(), 2000);

		// Check if the objekt is a serial port object
		if (commPort instanceof SerialPort) {

			// Cast the CommPort to a serialPort if it is a serialPort
			serialPort = (SerialPort) commPort;

			// This is some custom stuff, that can be outsourced into the
			// constructor of the sairoComm
			serialPort.setSerialPortParams(this.baudrate, this.dataBits,
					this.stopBits, this.parity);
		} else {
			throw new Exception("Kein serieller Port Exception");
		}

		return serialPort;
	}

	/**
	 * Default strInput
	 * 
	 * @return String
	 */
	public String getStrInput() {
		return getStrInput(false);
	}

	/**
	 * Return strInput and clear, if true
	 * 
	 * @param clear
	 * @return
	 */
	public String getStrInput(boolean clear) {
		String strReturn = this.strInput;
		if (clear == true) {
			this.clearStrInput();
		}
		return strReturn;
	}
	/**
	 * 
	 * @param str
	 */
	private void setStrInput(String str) {
		System.out.println(str);
		// �nderung anzeigen
		setChanged();
		this.strInput = str;
		this.addSample(str); // set into StringArray
		// Observer informieren
		notifyObservers(str);
	}

	/**
	 * return SampleBuffer
	 * 
	 * @return
	 */
	public String[] getSampleBuffer() {
		return this.sampleBuffer;
	}

	/**
	 * add a new Entry (Sample) to the Sample-Buffer, kick off First Entry
	 * 
	 * @param str
	 */
	private void addSample(String str) {
		int l = this.sampleBuffer.length;
		for (int i = 0; i < l - 1; i++) {
			this.sampleBuffer[i] = this.sampleBuffer[i + 1];
		}
		this.sampleBuffer[l - 1] = str;
	}

	private void clearStrInput() {
		this.strInput = null;
	}

	/**
	 * 
	 * @param str
	 */
	public void setStrOutput(String str) {
		this.strOutput = str;
	}

	/**
	 * Return the OutputString
	 * 
	 * @return String strOutput
	 */
	public String getStrOutput() {
		return this.strOutput;
	}

	public void close() {
		this.serialPort.close();
	}

	public void closePort() {
		System.out.println("Closing: ");
		new Thread() {
			@Override
			public void run() {
				try {
					inputStream.close();
					System.out.println(serialPort.getName());
					serialPort.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}.start();
	}

	public void writeOutputStream(String message) {

		this.SerialWriter(message);
	}

	/**
	 * Writes bitwise to OutPutStram
	 * 
	 * @param message
	 * @author S. Schmidt
	 */
	private void SerialWriter(String message) {
		byte[] b = message.getBytes();
		try {

			for (int i = 0; i < b.length; i++) {
				this.outputStream.write(b[i]);
				Thread.sleep(2);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static class SerialReader implements Runnable {
		InputStream in;
		sairoComm2 sairoInstance;
		boolean charRead;

		public SerialReader(InputStream in, sairoComm2 sairoInstance) {
			this.in = in;
			this.sairoInstance = sairoInstance;
			this.charRead = this.sairoInstance.charReader;
		}

		public void run() {
			if (!charRead) {

				Boolean keepRunning = true;
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String line;
				while (keepRunning) {
					try {
						while ((line = br.readLine()) != null) {
							sairoInstance.setStrInput(line.toString());
						}
					} catch (IOException e) {
						try {
							// ignore it, the stream is temporarily empty,RXTX's
							// just whining
							Thread.sleep(200);
						} catch (InterruptedException ex) {
							// something interrupted our sleep, exit ...
							// keepRunning = false;
						}
					}
				}

			} else {
				byte[] buffer = new byte[1024];
				int len = -1;
				try {
					while ((len = this.in.read(buffer)) > -1) {
						// System.out.println(new String(buffer,0,len));
						if (!new String(buffer, 0, len).isEmpty()) {
							sairoInstance
									.setStrInput(new String(buffer, 0, len));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class SerialEventReader implements SerialPortEventListener {
		private InputStream inputStream;
		private sairoComm2 sairoInstance;
		boolean charRead;

		public SerialEventReader(InputStream inputStream, sairoComm2 instance) {
			this.inputStream = inputStream;
			this.sairoInstance = instance;
			this.charRead = this.sairoInstance.charReader;
		}

		public void serialEvent(SerialPortEvent arg0) {
			if (!charRead) {
				Boolean keepRunning = true;

				BufferedReader br = new BufferedReader(new InputStreamReader(
						this.inputStream));
				String line;
//				while (keepRunning) {
					try {
						while ((line = br.readLine()) != null) {
							
							sairoInstance.setStrInput(line.toString());
							
						}
					} catch (IOException e) {
						try {
							// ignore it, the stream is temporarily empty,RXTX's
							// just whining
							Thread.sleep(200);
						} catch (InterruptedException ex) {
							// something interrupted our sleep, exit ...
							// keepRunning = false;
						}
					}
//				}
			} else {
				byte[] buffer = new byte[10];
				int len = -1;
				try {
					while ((len = this.inputStream.read(buffer)) > -1) {
						 //System.out.println(new String(buffer,0,len));
						String str = new String(buffer, 0, len);
						if (!str.isEmpty()) {
							sairoInstance.setStrInput(str);
						} else {
							break;
						}
						// Der sitzt in der Schleife fest!
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}