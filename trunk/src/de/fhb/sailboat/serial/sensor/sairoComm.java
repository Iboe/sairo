package de.fhb.sailboat.serial.sensor;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import gnu.io.*;

@Deprecated
public class sairoComm {

  // Status der Verbindung (0: inaktiv, 1: aktiv)
  private int status;
	
  // The comPort that is used in this Instance of the Sairo Comm
  private String comPort;

  // The String with the data of the Device
  private String dataString;
 
  private static Logger LOG = Logger.getLogger(GpsSensor.class);
  
  /**
   * The Constructor that needs a comPort-Name for doing its work
   * @param comPort
   */
  public sairoComm(String comPort) {
    this.comPort = comPort;
    
    // den Status aktualisieren
    this.status = 0;
  }

  /**
   * The Conect method, that starts the writing of the dataString and does all the
   * connecting stuff
 * @throws Exception 
   */
  public void connect() throws Exception {
	  
	if(this.status == 0){
    // First create a new Serial Port Object, because its not possible 
    // to make this inside the try-cath area
    SerialPort sp = null;
    
    // try to create a serialPort Object by calling the sairoConn.getSerialPort 
    // method. Variables are stored inside the Class itself
    try {
      sp = this.getSerialPort();
    } catch (Exception e) {
    	LOG.error(e);
    }
    if (sp != null) {
      // Teststuff that can be removed or used for debugging
      /* System.out.println(sp.getName());*/
    }

    // Get an Imput Stream by calling the sairoConn.getInputStream method
    InputStream myStream = this.getInputStream(sp);

    // Start the Thread with the Stream and an Instance of the active object type sairoConn
    (new Thread(new SerialReader(myStream, this))).start();
    
    this.status = 1;
	}else{
		throw new Exception("Verbindung bereits geöffnet.");
	}
  }

  /**
   * Returns a serial object by a given portname
   * 
   * @param portName
   *          name of the port (e.g. COM3)
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
    CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

    // Check if the objekt is a serial port object
    if (commPort instanceof SerialPort) {
      
      // Cast the CommPort to a serialPort if it is a serialPort
      serialPort = (SerialPort) commPort;
      
      // This is some custom stuff, that can be outsourced into the constructor of the sairoComm
      serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8,
          SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
    } else {
      throw new Exception("Kein serieller Port Exception");
    }

    return serialPort;
  }

  public InputStream getInputStream(SerialPort sp) {

    InputStream in = null;

    try {
      in = sp.getInputStream();
    } catch (IOException e) {
    	LOG.error(e);      
    }

    return in;
  }

  public String getDataString(){
	  
	if(this.status == 0){  
		try {
			connect();
		} catch (Exception e) {
			LOG.error(e);
		}
	}
    return dataString;
  }

  private void setDataString(String dataString) {
	if(dataString != "" && dataString != "null"){
		this.dataString = dataString;
	}
  }

  public static class SerialReader implements Runnable {
    InputStream in;
    sairoComm sairoInstance;

    public SerialReader(InputStream in, sairoComm sairoInstance) {
      this.in = in;
      this.sairoInstance = sairoInstance;
    }

    @Override
	public void run() {
      byte[] buffer = new byte[1024];
      int len = -1;
      try {
        // Create a new String Parser Instance
        //StringParsers parser = new StringParsers();

        while ((len = this.in.read(buffer)) > -1) {
          // System.out.println(new String(buffer,0,len));
          if(!new String(buffer, 0, len).isEmpty()){
          sairoInstance.setDataString(new String(buffer, 0, len));
          }
        }
      } catch (IOException e) {
    	  LOG.error(e);
      }
    }
  }
}
