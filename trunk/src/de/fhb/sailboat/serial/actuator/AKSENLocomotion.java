package de.fhb.sailboat.serial.actuator;

import java.io.IOException;
import org.apache.log4j.Logger;

import de.fhb.sailboat.data.Actuator;
import de.fhb.sailboat.serial.serialAPI.COMPort;
import de.fhb.sailboat.worldmodel.WorldModel;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 *
 * AKSEN-Board Communication
 * -------------------------
 * - open Com-Portd
 * - Send Data in following expression:
 * 		1) (S)end: s	- acquire Connection
 *         (R)eceive: a	- Acknowledge
 *      2) S: <servo>,<angle> (e.g. 1,90)	- Instruction set, comma separated (Number of Servomotor and Angle ==> see range for each servo=
 *                                            More commands separated by comma (e.g. 1,90,2,45,0,73)
 *         R: +			- for every correct command
 *      3) S: e			- end of Instruction set
 *         R: a			- Ack
 *      4) S: a			- execute Instruction on AKSEN
 *         R: e			- executed (=ACK)
 * 
 * ---
 * 
 * 
 * @author schmidst
 *
 */
public class AKSENLocomotion implements LocomotionSystem {
	
	private final WorldModel worldModel;
	COMPort myCOM;
	private static final Logger LOG = Logger.getLogger(AKSENLocomotion.class);
	String lastCom;
	static final String COM_PORT = System.getProperty(AKSENLocomotion.class.getSimpleName() + ".comPort");
	static final String BAUDRATE = System.getProperty(AKSENLocomotion.class.getSimpleName() + ".baudrate");
	static long wait_sleep = 2; //sleep for x milliseconds between each byte send to comport
	
	// DEBUG-Mode with 3-Way-Command-Handshake w/ AKSEN
	boolean debug = false; 
	
	int status;

	public int getStatus() {
		return status;
	}
	/**
	 * Constructor
	 * 
	 * initializes new Serial-Port-Connection to AKSEN-BOARD
	 */
	public AKSENLocomotion(){
		worldModel = WorldModelImpl.getInstance();
		COMPort myCOM = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;
		myCOM.open();

	}
	/**
	 * internal Debug-Mode
	 * @return boolean debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * internal Debug-Mode
	 * Setter
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
	 * Send one Rudder-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setRudder(int)
	 */
	public void setRudder(int angle) {
		lastCom = "setRudder to "+ angle;
		if(isDebug())	
		{
			int status = this.AKSENServoCommand(RUDDER_NUMBER, angle);
			
			if(status == -1) {
				//TODO Exception? Eskalieren?
				LOG.warn(lastCom +": incorrect/not send");
			} else {
				LOG.info(lastCom +": correct");
				worldModel.getActuatorModel().setRudder(new Actuator(angle));
				
			}
		}
		else
		{
			// send command-string to AKSEN-Board
			String com = this.buildCommand(RUDDER_NUMBER, angle);
	
			this.AKSENCommand(com);
			worldModel.getActuatorModel().setRudder(new Actuator(angle));

		}
		lastCom = "";
		
	}

	/** 
	 * Send one Sail-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setSail(int)
	 * @TODO Buffer?
	 */
	@Override
	public void setSail(int angle) {
		
		try {
			if (!"".equals(lastCom))
			{
				Thread.sleep(wait_sleep*10);
			}

		
			this.lastCom = "setSail to "+ angle;
	//		if(isDebug())	
	//		{
				status = this.AKSENServoCommand(SAIL_NUMBER, angle);
				
				if(status == -1) {
					//TODO Exception? Eskalieren?
					LOG.warn(lastCom +": incorrect/not send");
				} else {
					worldModel.getActuatorModel().setSail(new Actuator(angle));
				}
//		}
//		else
//		{
//			// send command-string to AKSEN-Board
//			String com = this.buildCommand(SAIL_NUMBER, angle);
//	
//			this.AKSENCommand(com);
//		}	
//		worldModel.getActuatorModel().setSail(new Actuator(angle));
				
				lastCom = "";
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * Send one Propellor-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setPropeller(int)
	 */
	@Override
	public void setPropellor(int angle) {
		lastCom = "setPropellor to "+ angle;
		if(isDebug())	
		{
			int status = this.AKSENServoCommand(PROPELLOR_NUMBER, angle);
			
			if(status == -1) {
				//TODO Exception? Eskalieren?
				LOG.warn(lastCom +": incorrect/not send");
			} else {
				LOG.info(lastCom +": correct");
			}
		}
		else
		{
			// send command-string to AKSEN-Board
			String com = this.buildCommand(PROPELLOR_NUMBER, angle);
	
			this.AKSENCommand(com);
		}	
		worldModel.getActuatorModel().setPropeller(new Actuator(angle));
		lastCom = "";
	}

	/** 
	 * Reset all three Servos to Neutral
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
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetRudder()
	 */
	@Override
	public void resetRudder() {
		this.setRudder(RUDDER_NORMAL);
		worldModel.getActuatorModel().setRudder(new Actuator(RUDDER_NORMAL));
	}

	/** 
	 * Reset Sail to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetSail()
	 */
	@Override
	public void resetSail() {
		this.setSail(SAIL_NORMAL);
		worldModel.getActuatorModel().setSail(new Actuator(SAIL_NORMAL));
	}

	/** 
	 * Reset Propellor to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetPropeller()
	 */
	@Override
	public void resetPropellor() {
		this.setPropellor(PROPELLOR_NORMAL);
		worldModel.getActuatorModel().setPropeller(new Actuator(PROPELLOR_NORMAL));
	}
	
	public void closePort() {
		this.myCOM.close();
	}
	
	private String buildCommand(int s, int a) {
		//String str = "s"+ s +"," + a + "ea";
		String str = "s"+ s +"," + a + "ea";
		return str;
	}

	
	/**
	 * Send well defined AKSEN-Command as a complete string to myCom
	 * follows the 'fire and forget' principle
	 * no 3-way Handshake, no checking for completion
	 * 
	 * @param String command built by buildCommand()
	 */
	private void AKSENCommand(String com) {
		this.lastCom = com;		
		try {
					this.myCOM.writeString(com,wait_sleep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.warn("IOException", e);
		}		
	}
	/**
	 * Send Command to AKSEN step by step
	 * with response observation and command validation
	 * @param s
	 * @param a
	 * @return Status (-1: fail, 1: correct
	 */
	private int AKSENServoCommand(int s, int a) {
		int status = -1;
		final int n = 3; // Number of attempts to
		int k = 0;
		int i;
		Byte send,r, expected;
		try {
			 while(k < n){
				k++;
				//* 1) (S)end: s, hex: 0x73, dec : 115	- acquire Connection
				send = 0x73;
				//*     (R)eceive: a, hex: 0x61, dec: 97	- Acknowledge
				expected = 0x61;
				
				r = 0x00;
				i = 0;
				while (r != expected) {
					incSleepTime();
					i++;
					this.myCOM.writeByte(send);
					Thread.sleep(wait_sleep);
					r = (byte) this.myCOM.readByte();
					if (i == n) {
						break;
					}
				}
				// recheck, because of broken loop
				if ( r != expected) {
					LOG.warn("Run "+ k +": Couldn't acquire Connection to AKSEN in "+ n +" attempts. Received: " + r); // Tobias Koppe : Wenn Verbindung nicht aufgebaut werden konnte, empfangenes Zeichen anzeigen
					incSleepTime();
					return -1;
				} else {
					decSleepTime();
					//* 2) S: <servo>,<angle> (e.g. 1,90)	- Instruction set, comma separated (Number of Servomotor and Angle ==> see range for each servo=
					String t = s + "," + a;
					byte[] b = t.getBytes();
					int loopLength=b.length;  //T.Koppe : Loop count auslagern => schneller
					for (int j = 0; j < loopLength; j++) {
						r=0x00;
						this.myCOM.writeByte(b[j]);
						Thread.sleep(wait_sleep);
						r = (byte) this.myCOM.readByte();
					}
					if (r == 110) {
						continue;
					}
					//*                                        More commands separated by comma (e.g. 1,90,2,45,0,73)
					// TODO Multi-Instructions
					//*         R: +, dec:  43, hex: 2b		- for every correct command
					//*      3) S: e, dec: 101, hex: 65			- end of Instruction set
					send = 0x65;
					//*         R: number of recieved commands; 1
					expected = 0x31;
					r = 0x00;
					
					this.myCOM.writeByte(send);
					Thread.sleep(wait_sleep);
					r = (byte) this.myCOM.readByte();					
					// didn't got the correct answer? try to resend whole command in next loop
					if(r != expected) {
						if(k==n)
							LOG.info("Couldn't send InstructionSet on AKSEN in "+ n +" attempts. Received: " + r); // Tobias Koppe : Wenn nicht gesendet werden konnte, empfangenes Zeichen anzeigen
							incSleepTime();
						continue;
					} else {
						decSleepTime();
						//* 4) S: a, hex: 61, dec 097			- execute Instruction on AKSEN
						send = 0x61;
						//*     R: e, hex: 65, dec: 101			- executed (=ACK)
						expected = 0x65;
						r = 0x00;
						this.myCOM.writeByte(send);
						Thread.sleep(wait_sleep*2);
						r = (byte) this.myCOM.readByte();
						
						// didn't got the correct answer? try to resend whole command in next loop
						// r might be 110
						if(r != expected) {
							LOG.info("Run "+ k +": Couldn't execute Commands on AKSEN "+ r.toString() + " vs "+ expected.toString() +". Received: " + r); // Tobias Koppe : Wenn Befehl nicht ausgefuehrt werden konnte, empfangenes Zeichen anzeigen
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
			// TODO Auto-generated catch block
			LOG.warn("IOException", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			LOG.warn("InterruptException", e);
		}
		
		return status;
	}
	/**
	 * Returns the Battery-State given by AKSENBoard
	 * 
	 * @return int state
	 */
	public int getBatteryState() {
		int state = -1;
		try {
			byte b = 118;	// decimal for String "v"
			this.myCOM.writeByte(b);
			state = this.myCOM.readByte();
		} catch (IOException e) {
			LOG.warn("Couldn't get BatteryState", e);
		}
		return state;
	}

	private void incSleepTime(){
		this.wait_sleep++;
	}
	
	private void decSleepTime(){
		this.wait_sleep--;
	}
	
}
