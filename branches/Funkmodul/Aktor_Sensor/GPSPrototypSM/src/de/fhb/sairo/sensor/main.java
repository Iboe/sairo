package de.fhb.sairo.sensor;

import java.io.InputStream;

import de.fhb.sairo.sensor.sairoComm.SerialReader;

import gnu.io.*;

public class main {

  static SerialPort sp = null;
  /**
   * @param args
   */
  public static void main(String[] args) {
    
    // Create a new Instance of the sairoCommClass
    sairoComm mySairo = new sairoComm("COM3");
    
    // Start the Threading by using the connect method
    mySairo.connect();
    
    // Endlosschleife zu Demonstrationszwecken
    while(true){
    System.out.println(mySairo.getDataString());
    }
  }  
}
