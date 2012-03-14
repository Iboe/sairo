/**
 * 
 */
package de.fhb.sailboat.serial.actuator;

import de.fhb.sailboat.serial.sensor.sairoComm2;

/**
 *
 * AKSEN-Board Communication
 * -------------------------
 * - open Com-Port
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
 * @author schmidst
 *
 */
public class AKSENLocomotion implements LocomotionSystem {
	sairoComm2 aksenComm;
	String lastCom;
	// Servo-Config; ==> TODO outsourcing!
	//String comPort = "COM1";
	String comPort = "COM4";
	int baudrate = 9600;
	// Sail
	int sailNo = 0;
	int sailMin = 31;
	int sailMax = 114;
	int sailN = 73;
	// Rudder
	int rudderNo = 1;
	int rudderMin = 34;
	int rudderMax = 108;
	int rudderN = 68;
	// Propellor
	int propellorNo = 2;
	int propellorMin = 32;
	int propellorMax = 112;
	int propellorN = 72;

	//Debug
	String lastAnswer;
	/**
	 * Constructor
	 * 
	 * initializes new Serial-Port-Connection to AKSEN-BOARD
	 */
	public AKSENLocomotion(){
		
		this.aksenComm = new sairoComm2(comPort,baudrate, true);
		
		try {
			this.aksenComm.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Debug Output
		System.out.println("Status: " + this.aksenComm.getStatus() + "\n");
	}
	
	/** 
	 * Send one Rudder-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setRudder(int)
	 */
	@Override
	public void setRudder(int value) {
		//TODO implement normalize value handling
		int angle = value;
		// send command-string to AKSEN-Board
		String com = this.buildCommand(rudderNo, angle);
		System.out.println(com);
		try {
			this.aksenComm.writeOutputStream(com);
			Thread.sleep(1000);
			System.out.println(this.aksenComm.getStrInput());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * Send one Sail-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setSail(int)
	 */
	@Override
	public void setSail(int value) {
		//TODO implement normalize value handling
		int angle = value;
		String com = this.buildCommand(sailNo, angle);
		System.out.println(com);
		try {
			this.aksenComm.writeOutputStream(com);
			Thread.sleep(1000);
			System.out.println(this.aksenComm.getStrInput());
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
	public void setPropellor(int value) {
		//TODO implement normalize value handling
		int angle = value;
		String com = this.buildCommand(propellorNo, angle);
		// send command-string to AKSEN-Board
		System.out.println(com);
		try {
			this.aksenComm.writeOutputStream(com);
			Thread.sleep(1000);
			System.out.println(this.aksenComm.getStrInput());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * Reset all three Servos to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#reset()
	 */
	@Override
	public void reset() {
		// Rudder
		this.setRudder(rudderN);
		// Sail
		this.setSail(sailN);
		// Propellor
		this.setPropellor(propellorN);
	}

	/** 
	 * Reset Rudder to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetRudder()
	 */
	@Override
	public void resetRudder() {
		this.setRudder(rudderN);
	}

	/** 
	 * Reset Sail to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetSail()
	 */
	@Override
	public void resetSail() {
		this.setSail(sailN);
	}

	/** 
	 * Reset Propellor to Neutral
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#resetPropeller()
	 */
	@Override
	public void resetPropellor() {
		this.setPropellor(propellorN);
	}
	
	public void closePort() {
		this.aksenComm.closePort();
	}
	
	private String buildCommand(int s, int a) {
		String str = "s"+ s +"," + a + "ea";
		return str;
	}
}