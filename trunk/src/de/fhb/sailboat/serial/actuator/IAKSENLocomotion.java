package de.fhb.sailboat.serial.actuator;

/***
 * Definiert die Schnitstelle zur Ansteuerung des AKSEN-Board
 * 
 * 
 * @author Tobias Koppe
 *
 * @version 1
 */

public interface IAKSENLocomotion {

	boolean openConnection();
	boolean sendCommand(int pServo, int pValue);
	boolean sendCommandSet(String[] pCommandSet);
	String readState();
	void writeData(String data);
	void writeData(Byte data);
	void incWaitingTime();
	void decWaitingTime();
}
