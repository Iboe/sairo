package de.fhb.sailboat.serial.actuator;

/***
 * This class provides representative strings for the character to communicate
 * with aksen board
 * 
 * @author Tobias Koppe
 * @version 1.0
 *
 */
public class AKSENCommand {

	public static final char REQUEST_CONNECTION = 's';
	public static final char ACKNOWLEDGE = 'a';
	public static final char COMMAND_RECEIVED = '+';
	public static final char END_TRANSFER = 'e';
	public static final char SUCCESSFULL_TRANSFER = 'a';
	public static final char COMMANDS_EXECUTED = 'e';
}
