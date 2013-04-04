package de.fhb.sailboat.serial.actuator;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

import de.fhb.sailboat.data.Actuator;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.serial.serialAPI.SerialSystemTextEnum;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * 
 * AKSEN-Board Communication ------------------------- - open Com-Portd - Send
 * Data in following expression: 1) (S)end: s - acquire Connection (R)eceive: a
 * - Acknowledge 2) S: <servo>,<angle> (e.g. 1,90) - Instruction set, comma
 * separated (Number of Servomotor and Angle ==> see range for each servo= More
 * commands separated by comma (e.g. 1,90,2,45,0,73) R: + - for every correct
 * command 3) S: e - end of Instruction set R: a - Ack 4) S: a - execute
 * Instruction on AKSEN R: e - executed (=ACK)
 * 
 * ---
 * 
 * 
 * @author schmidst
 * 
 */
public class AKSENLocomotion implements LocomotionSystem {

	private final String TAG = this.getClass().getName() + " | ";

	private final WorldModel worldModel;
	private COMPort myCOM; // changed to private , not checked because of
							// threads
	private static final Logger LOG = Logger.getLogger(AKSENLocomotion.class);
	private String lastCom; // changed to private , not checked because of
							// threads
	static final String COM_PORT = System.getProperty(AKSENLocomotion.class
			.getSimpleName() + ".comPort");
	static final String BAUDRATE = System.getProperty(AKSENLocomotion.class
			.getSimpleName() + ".baudrate");
	static long wait_sleep = 3; // sleep for x milliseconds between each byte
								// send to comport

	private String aksenState = "null";
	private ArrayList<String> aksenStateList = new ArrayList<String>();

	// DEBUG-Mode with 3-Way-Command-Handshake w/ AKSEN
	boolean debug = true;

	int status;

	/**
	 * Constructor
	 * 
	 * initializes new Serial-Port-Connection to AKSEN-BOARD
	 */
	public AKSENLocomotion() {
		LOG.debug("Constructor AKSENLocomotion() called");
		worldModel = WorldModelImpl.getInstance();
		if (worldModel == null) {
			LOG.debug(TAG + "worldModel: haven't got an instance");
			throw new NullPointerException();
		} else {
			LOG.debug(TAG + " worldModel: have got an instance - "
					+ this.worldModel.toString());
		}

		COMPort myCOM = new COMPort(7, Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;

		if (this.myCOM != null) {
			// DEPRECATED: LOG.debug(TAG + " COMPort created with Port: " +
			// this.myCOM.getPort() + " Baudrate: " + this.myCOM.get_baud());
			LOG.debug(TAG + SerialSystemTextEnum.COMPORT_CREATED.toString()
					+ myCOM.getPort() + "," + myCOM.get_baud());
		}

		myCOM.open();
	}

	public AKSENLocomotion(boolean debug) {
		if (!debug) {
			worldModel = WorldModelImpl.getInstance();
		} else {
			worldModel = null;
		}
		COMPort myCOM = new COMPort(7, 9600, 0);
		this.myCOM = myCOM;
		myCOM.open();
	}

	/**
	 * Send one Rudder-Command to AKSEN-Board
	 * 
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setRudder(int)
	 */
	public void setRudder(int angle) {
		lastCom = "setRudder to " + angle;
		if (isDebug()) {
			int status = this.AKSENServoCommand(RUDDER_NUMBER, angle);

			if (status == -1) {
				// TODO Exception? Eskalieren?
				LOG.warn(lastCom + ": incorrect/not send");
			} else {
				LOG.info(lastCom + ": correct");
				worldModel.getActuatorModel().setRudder(new Actuator(angle));
				setAksenState(AksenSystemTextEnum.AKSEN_COMMAND_RUDDER_LEFT
						.toString());
			}
		} else {
			// send command-string to AKSEN-Board
			String com = this.buildCommand(RUDDER_NUMBER, angle);

			this.AKSENCommand(com);
			worldModel.getActuatorModel().setRudder(new Actuator(angle));

		}

		lastCom = "";
	}

	/**
	 * Send one Sail-Command to AKSEN-Board
	 * 
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setSail(int)
	 * @TODO Buffer?
	 */
	@Override
	public void setSail(int angle) {

		try {
			if (!"".equals(lastCom)) {
				Thread.sleep(wait_sleep * 10);
			}

			this.lastCom = "setSail to " + angle;
			// if(isDebug())
			// {
			status = this.AKSENServoCommand(SAIL_NUMBER, angle);

			if (status == -1) {
				// TODO Exception? Eskalieren?
				LOG.warn(lastCom + ": incorrect/not send");
			} else {
				worldModel.getActuatorModel().setSail(new Actuator(angle));
			}
			// }
			// else
			// {
			// // send command-string to AKSEN-Board
			// String com = this.buildCommand(SAIL_NUMBER, angle);
			//
			// this.AKSENCommand(com);
			// }
			// worldModel.getActuatorModel().setSail(new Actuator(angle));

			lastCom = "";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Send one Propellor-Command to AKSEN-Board
	 * 
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setPropeller(int)
	 */
	@Override
	public void setPropellor(int angle) {
		lastCom = "setPropellor to " + angle;
		if (isDebug()) {
			int status = this.AKSENServoCommand(PROPELLOR_NUMBER, angle);

			if (status == -1) {
				// TODO Exception? Eskalieren?
				LOG.warn(lastCom + ": incorrect/not send");
			} else {
				LOG.info(lastCom + ": correct");
			}
		} else {
			// send command-string to AKSEN-Board
			String com = this.buildCommand(PROPELLOR_NUMBER, angle);

			this.AKSENCommand(com);
		}
		worldModel.getActuatorModel().setPropeller(new Actuator(angle));
		lastCom = "";
	}

	/**
	 * Reset all three Servos to Neutral
	 * 
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#reset()
	 */
	@Override
	public void reset() {
		// Rudder
		this.resetRudder();
		// Sail
		this.resetSail();
		// Propellor
		this.resetPropellor();
	}

	/**
	 * Reset Rudder to Neutral
	 * 
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetRudder()
	 */
	@Override
	public void resetRudder() {
		this.setRudder(RUDDER_NORMAL);
		worldModel.getActuatorModel().setRudder(new Actuator(RUDDER_NORMAL));
	}

	/**
	 * Reset Sail to Neutral
	 * 
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetSail()
	 */
	@Override
	public void resetSail() {
		this.setSail(SAIL_NORMAL);
		worldModel.getActuatorModel().setSail(new Actuator(SAIL_NORMAL));
	}

	/**
	 * Reset Propellor to Neutral
	 * 
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetPropeller()
	 */
	@Override
	public void resetPropellor() {
		this.setPropellor(PROPELLOR_NORMAL);
		worldModel.getActuatorModel().setPropeller(
				new Actuator(PROPELLOR_NORMAL));
	}

	public void closePort() {
		this.myCOM.close();
	}

	/***
	 * Builds an correct ServoCommand for sending to AKSEN Board
	 * 
	 * @param s
	 *            number of servo
	 * @param a
	 *            value to send to servo
	 * @return ServoCommand as String
	 */
	private String buildCommand(int s, int a) {
		return s + "," + a;
	}

	/**
	 * Send well defined AKSEN-Command as a complete string to myCom follows the
	 * 'fire and forget' principle no 3-way Handshake, no checking for
	 * completion
	 * 
	 * @param String
	 *            command built by buildCommand()
	 */
	private void AKSENCommand(String com) {
		this.lastCom = com;
		try {
			this.myCOM.writeString(com, wait_sleep);
		} catch (IOException e) {
			LOG.warn("IOException", e);
		}
	}

	// private int AKSENServoCommand(int s, int a){
	// return AKSENCommandServo(s, a);
	// }

	/**
	 * Send Command to AKSEN step by step with response observation and command
	 * validation
	 * 
	 * @param s
	 * @param a
	 * @return Status (-1: fail, 1: correct
	 */
	private int AKSENServoCommand(int s, int a) {
		int status = -1;
		final int maxAttempts = 3; // Number of attempts to
		int attempt = 0;
		int i;
		Byte send, received, expected;
		long time = 0; // If debug is enabled used to measure the executiontime
		try {
			while (attempt < maxAttempts) {
				attempt++;
				// * 1) (S)end: s, hex: 0x73, dec : 115 - acquire Connection
				send = 0x73;
				// * (R)eceive: a, hex: 0x61, dec: 97 - Acknowledge
				expected = 0x61;
				received = 0x00;
				i = 0;

				// This loop sends the character for aquire connection and check
				// the received character if acknowledge gotten
				// Loopconditions, while acknowledge is not received and count
				// is not until maxAttempts
				// Increase the waiting time if actually time is not enought,
				// send the char's, set the state of AKSEN Board,
				// get the answer from AKSEN Board and compare it with the
				// expected char
				while (received != expected) {
					incSleepTime();
					i++;
					if (debug) {
						time = System.currentTimeMillis();
					}
					this.myCOM.writeByte(send);
					setAksenState("send: "
							+ (char) Integer.parseInt(send.toString()));
					Thread.sleep(wait_sleep);
					received = (byte) this.myCOM.readByte();
					setAksenState("received: "
							+ (char) Integer.parseInt(received.toString()));
					if (received == 110) {
						LOG.info("Got n from AKSEN Board, it's possible to break this command");
						return -1;
					}
					if (debug) {
						LOG.debug("Needed time to execute aquire connection and got achknowledge: "
								+ (System.currentTimeMillis() - time) + " ms");
					}
					if (i == maxAttempts) {
						break;
					}
				}

				// recheck, because of broken loop
				if (received != expected) {
					LOG.warn("Run " + attempt
							+ ": Couldn't acquire Connection to AKSEN in "
							+ maxAttempts + " attempts. Received: " + received); // Tobias
																					// Koppe
																					// :
																					// Wenn
																					// Verbindung
																					// nicht
																					// aufgebaut
																					// werden
																					// konnte,
																					// empfangenes
																					// Zeichen
																					// anzeigen
					incSleepTime();
					return -1;
				} else {
					decSleepTime();
					// * 2) S: <servo>,<angle> (e.g. 1,90) - Instruction set,
					// comma separated (Number of Servomotor and Angle ==> see
					// range for each servo=
					String t = s + "," + a;
					setAksenState("will send servo command : " + t);
					byte[] b = buildCommand(s, a).getBytes();
					int loopLength = b.length; // T.Koppe : Loop count auslagern
												// => schneller
					for (int j = 0; j < loopLength; j++) {
						received = 0x00;
						this.myCOM.writeByte(b[j]);
						Thread.sleep(wait_sleep);
						received = (byte) this.myCOM.readByte();
						setAksenState("send: " + (char) b[j] + " -- received: "
								+ (char) Integer.parseInt(received.toString()));
						if (received == 110) {
							LOG.info("Got n from AKSEN Board, it's possible to break this command");
							return -1;
						}
					}
					if (received == 110) {
						continue;
					}
					// * More commands separated by comma (e.g. 1,90,2,45,0,73)
					// TODO Multi-Instructions
					// * R: +, dec: 43, hex: 2b - for every correct command
					// * 3) S: e, dec: 101, hex: 65 - end of Instruction set
					send = 0x65;
					// * R: number of recieved commands; 1
					expected = 0x31;
					received = 0x00;

					this.myCOM.writeByte(send);
					setAksenState("send: "
							+ (char) Integer.parseInt(send.toString()));
					Thread.sleep(wait_sleep);
					received = (byte) this.myCOM.readByte();
					setAksenState("received: "
							+ (char) Integer.parseInt(received.toString()));
					if (received == 110) {
						LOG.info("Got n from AKSEN Board, it's possible to break this command");
						return -1;
					}
					// didn't got the correct answer? try to resend whole
					// command in next loop
					if (received != expected) {
						if (attempt == maxAttempts)
							LOG.warn("Couldn't send InstructionSet on AKSEN in "
									+ maxAttempts
									+ " attempts. Received: "
									+ received); // Tobias Koppe : Wenn nicht
													// gesendet werden konnte,
													// empfangenes Zeichen
													// anzeigen
						incSleepTime();
						continue;
					} else {
						decSleepTime();
						// * 4) S: a, hex: 61, dec 097 - execute Instruction on
						// AKSEN
						send = 0x61;
						// * R: e, hex: 65, dec: 101 - executed (=ACK)
						expected = 0x65;
						received = 0x00;
						this.myCOM.writeByte(send);
						setAksenState("send: "
								+ (char) Integer.parseInt(send.toString()));
						Thread.sleep(wait_sleep * 2);
						received = (byte) this.myCOM.readByte();
						setAksenState("received: "
								+ (char) Integer.parseInt(received.toString()));
						if (received == 110) {
							LOG.info("Got n from AKSEN Board, it's possible to break this command");
							return -1;
						}
						// didn't got the correct answer? try to resend whole
						// command in next loop
						// r might be 110
						if (received != expected) {
							LOG.info("Run " + attempt
									+ ": Couldn't execute Commands on AKSEN "
									+ received.toString() + " vs "
									+ expected.toString() + ". Received: "
									+ received); // Tobias Koppe : Wenn Befehl
													// nicht ausgefuehrt werden
													// konnte, empfangenes
													// Zeichen anzeigen
							status = 0;
							incSleepTime();
							continue;
						} else {
							decSleepTime();
							// We expect everything went well:
							status = 1;
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			LOG.warn("IOException", e);
		} catch (InterruptedException e) {
			LOG.warn("InterruptException", e);
		}

		return status;
	}

	/***
	 * 
	 * @param pToWrite
	 * @param pExpected
	 * @return
	 * @author Tobias Koppe
	 */
	private int writeToAksen(byte pToWrite, byte pExpected) {
		int success = 0;
		byte received = (byte) 0x00;
		int attempts = 3;
		for (int i = 0; i < attempts; i++) {
			try {
				setAksenState("send: " + (char) pToWrite);
				this.myCOM.writeByte(pToWrite);
				Thread.sleep(wait_sleep);
				received = (byte) this.myCOM.readByte();
				setAksenState("received: " + (char) received);
				// Wenn empfangenes Zeichen gleich dem erwarteten
				if (received == pExpected) {
					return success = 1;
				}
				// Wenn kein bestimmtes Zeichen erwartet und empfangenes Zeichen
				// nicht 110 (n) oder 102(f)
				else if (pExpected == (byte) 0x00 && received != 110
						&& received != 102) {
					return success = 1;
				}
				// Wenn empfangenes Zeichen 110(n)
				else if (received == 110) {
					success = -1;
				}
				// Wenn empfangenes Zeichen 102(f)
				else if (received == 102) {
					return success = -1;
				}
				// Wenn empfangenes Zeichen ohne Aussage ist / Sonst
				else {
					success = 0;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return success;
	}

	/**
	 * Send Command to AKSEN step by step with response observation and command
	 * validation
	 * 
	 * @param s
	 * @param a
	 * @return Status (-1: fail, 1: correct)
	 * @author Tobias Koppe
	 */
	// TODO Methode Testen
	private int AKSENCommandServo(int s, int a) {
		Byte send, expected;

		// * 1) (S)end: s, hex: 0x73, dec : 115 - acquire Connection
		// * (R)eceive: a, hex: 0x61, dec: 97 - Acknowledge
		send = 0x73;
		expected = 0x61;
		// Fordere ACK von AKSEN Board an = sende 0x73(s) und erwarte 0x61(a)
		if (writeToAksen(send, expected) == 1) {
			// ACK vom AKSEN Board empfangen
			LOG.info("Got ack from AKSEN Board for connection");
			// Sende ServoCommand
			setAksenState("Will send servo command : " + buildCommand(s, a));
			byte[] b = buildCommand(s, a).getBytes();
			int loopLength = b.length;
			for (int j = 0; j < loopLength; j++) {
				if (writeToAksen(b[j], (byte) 0x00) == 1) {
					// Zeichen erfolgreich übertragen
				} else {
					// Abbruch da Fehler
					LOG.warn("Couldn't send instruction to AKSEN Board");
					return -1;
				}
			}
			// Sende weiter um Befehlsliste abzuschliessen und
			// Auszufuehren
			// Sende Ende der Befehle
			// * 3) S: e, dec: 101, hex: 65 - end of Instruction set
			send = 0x65;
			// * R: number of recieved commands; 1
			expected = 0x00;
			if (writeToAksen(send, expected) == 1) {
				// Erfolgreich uebertragen
			} else {
				return -1;
			}
			// Sende Ausfuehren des Befehls
			// * 4) S: a, hex: 61, dec 097 - execute Instruction on
			// AKSEN
			send = 0x61;
			// * R: e, hex: 65, dec: 101 - executed (=ACK)
			expected = 0x65;
			if (writeToAksen(send, expected) == 1) {
				// Befehl erfolgreich ausgefuehrt
				return 1;
			} else {
				// Abbruch da Fehler bei Senden vom execute instruction
				LOG.warn("Couldn't execute command on AKSEN Board");
				return -1;
			}
		}
		return 0;
	}

	// Getter and Setter Methods

	public int getStatus() {
		return status;
	}

	/**
	 * internal Debug-Mode
	 * 
	 * @return boolean debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * internal Debug-Mode Setter
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * 
	 * @return int wait_sleep
	 */
	@SuppressWarnings("static-access")
	public long getWait_Sleep() {
		return this.wait_sleep;
	}

	/**
	 * Setter Wait_Sleep
	 */
	@SuppressWarnings("static-access")
	public void setWait_Sleep(int wait_sleep) {
		this.wait_sleep = wait_sleep;
	}

	/**
	 * Returns the Battery-State given by AKSENBoard
	 * 
	 * @return int state
	 */
	public int getBatteryState() {
		int state = -1;
		try {
			byte b = 118; // decimal for String "v"
			this.myCOM.writeByte(b);
			setAksenState("v");
			state = this.myCOM.readByte();
		} catch (IOException e) {
			LOG.warn("Couldn't get BatteryState", e);
		}
		return state;
	}

	private void incSleepTime() {
	}

	private void decSleepTime() {
	}

	public String getAksenState() {
		return aksenState;
	}

	public void setAksenState(String aksenState) {
		this.aksenState = aksenState;
		LOG.debug("new aksenboard state: " + aksenState);
		aksenStateList.add("new aksenboard state: " + aksenState);
	}

	public ArrayList<String> getAksenStateList() {
		return aksenStateList;
	}
}
