package de.fhb.sairo.sensor;

import java.io.IOException;
import java.io.InputStream;
import ocss.nmea.parser.StringParsers;
import gnu.io.*;

public class sairoComm {

  // The comPort that is used in this Instance of the Sairo Comm
  private String comPort;

  // The String with the data of the Device
  private String dataString;

  /**
   * The Constructor that needs a comPort-Name for doing its work
   * @param comPort
   */
  public sairoComm(String comPort) {
    this.comPort = comPort;
  }

  /**
   * The Conect method, that starts the writing of the dataString and does all the
   * connecting stuff
   */
  public void connect() {

    // First create a new Serial Port Object, because its not possible 
    // to make this inside the try-cath area
    SerialPort sp = null;
    
    // try to create a serialPort Object by calling the sairoConn.getSerialPort 
    // method. Variables are stored inside the Class itself
    try {
      sp = this.getSerialPort();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (sp != null) {
      // Teststuff that can be removed or used for debugging
      /* System.out.println(sp.getName());*/
    }

    // Get an Imput Stream by calling the sairoConn.getInputStream method
    InputStream myStream = this.getInputStream(sp);

    // Start the Thread with the Stream and an Instance of the active object type sairoConn
    (new Thread(new SerialReader(myStream, this))).start();
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return in;
  }

  public String getDataString() {
    return dataString;
  }

  private void setDataString(String dataString) {
    this.dataString = dataString;
  }

  public static class SerialReader implements Runnable {
    InputStream in;
    sairoComm sairoInstance;

    public SerialReader(InputStream in, sairoComm sairoInstance) {
      this.in = in;
      this.sairoInstance = sairoInstance;
    }

    public void run() {
      byte[] buffer = new byte[1024];
      int len = -1;
      try {
        // Create a new String Parser Instance
        StringParsers parser = new StringParsers();

        while ((len = this.in.read(buffer)) > -1) {
          // System.out.println(new String(buffer,0,len));
          sairoInstance.setDataString(new String(buffer, 0, len));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
