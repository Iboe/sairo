/**
 * 
 */
package de.fhb.sailboat.serial.actuator;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import de.fhb.sailboat.serial.serialAPI.COMPort;

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
public class AKSENLocomotion implements LocomotionSystem, Observer {
	COMPort myCOM;
	private static final Logger LOG = Logger.getLogger(AKSENLocomotion.class);
	String lastCom;
	static final String COM_PORT = System.getProperty(AKSENLocomotion.class.getSimpleName() + ".comPort");
	static final String BAUDRATE = System.getProperty(AKSENLocomotion.class.getSimpleName() + ".baudrate");
	boolean keepRunning = true;
	Thread aksenThread;
	
	// Sail
	static final int sailNo = 0;
	static final int sailMin = 31;
	static final int sailMax = 114;
	static final int sailN = 73;
	// Rudder
	static final int rudderNo = 1;
	static final int rudderMin = 34;
	static final int rudderMax = 108;
	static final int rudderN = 68;
	// Propellor
	static final int propellorNo = 2;
	static final int propellorMin = 32;
	static final int propellorMax = 112;
	static final int propellorN = 72;

	int zustand = 0;
	//Debug
	String lastAnswer;
	/**
	 * Constructor
	 * 
	 * initializes new Serial-Port-Connection to AKSEN-BOARD
	 */
	public AKSENLocomotion(){
		
		COMPort myCOM = new COMPort(Integer.parseInt(COM_PORT), Integer.parseInt(BAUDRATE), 0);
		this.myCOM = myCOM;
		myCOM.open();

		keepRunning=true;
		(aksenThread=new Thread(new AKSENLocomotionThread(this))).start();

	}

	static class AKSENLocomotionThread extends Thread {
		AKSENLocomotion aInstance;
		// clock in milliseconds
		long clock = 100;	// default Value
		long start, end;
		private static Logger LOG = Logger.getLogger(AKSENLocomotionThread.class);

		public AKSENLocomotionThread(AKSENLocomotion aInstance) {
			this.aInstance = aInstance;
		}

		public void run() {

			while (aInstance.keepRunning) {
//				try {
//
//						
//					
//				}  catch (InterruptedException e) {
//					// something went wrong, stop the loop, throw an error to the next higher level
//					aInstance.keepRunning = false;
//					
//					LOG.debug("thread interrupted", e);
//				}
			}
		}
		
	}
	
	
	/** 
	 * Send one Rudder-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setRudder(int)
	 */
	public void setRudder(int value) {
		//TODO implement normalize value handling
		int angle = value;
		/*
		if ()
		*/
		// send command-string to AKSEN-Board
		String com = this.buildCommand(rudderNo, angle);
		
		this.zustand = 1;	
		try {
			this.myCOM.writeString(com);
		} catch (IOException e) {
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
//		try {
			this.myCOM.writeString(com);
//			Thread.sleep(1000);
//			System.out.println(this.aksenComm.getStrInput());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
//		try {
			this.myCOM.writeString(com);
//			Thread.sleep(1000);
//			System.out.println(this.aksenComm.getStrInput());
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
		this.myCOM.close();
	}
	
	private String buildCommand(int s, int a) {
		//String str = "s"+ s +"," + a + "ea";
		String str = "s"+ s +"," + a + "ea";
		return str;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		// Bestätigen
		
//			this.aksenComm.writeOutputStream("a");
			System.out.println("test: "+ arg1);

		
		
	}
	
	
	/**
	 * Data-Object for an AKSEN-Board-Command
	 * and the state of transmission via serial Port
	 * @author schmidst
	 *
	 */
	private static class AKSENCommand {
		
	}
}
