package de.fhb.sailboat.emulator;
/**
 *  Exception zur Fehlerbehandlung im OperationPlayer
 * 
 * @author  Andy Klay <klay@fh-brandenburg.de>
 */
public class OperationPlayerException extends Exception {

	/**
	 * Konstruktor
	 */
	public OperationPlayerException() {
		super();
	}
	
	/**
	 * Konstruktor mit Parameter fuer die Message
	 * @param string
	 */
	public OperationPlayerException(String string) {
		super(string);
	}
	
}
