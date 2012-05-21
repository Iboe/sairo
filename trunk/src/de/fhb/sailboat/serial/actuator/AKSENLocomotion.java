package de.fhb.sailboat.serial.actuator;

import java.io.IOException;
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
public class AKSENLocomotion implements LocomotionSystem {
	COMPort myCOM;
	private static final Logger LOG = Logger.getLogger(AKSENLocomotion.class);
	String lastCom;
	static final String COM_PORT = "3"; //System.getProperty(AKSENLocomotion.class.getSimpleName() + ".comPort");
	static final String BAUDRATE = "9600"; //System.getProperty(AKSENLocomotion.class.getSimpleName() + ".baudrate");
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

	}

	/** 
	 * Send one Rudder-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setRudder(int)
	 */
	public void setRudder(int value) {
			int angle = value;
			// send command-string to AKSEN-Board
			String com = this.buildCommand(rudderNo, angle);
	
			this.AKSENCommand(com);
			

//		try {
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	/** 
	 * Send one Sail-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setSail(int)
	 */
	@Override
	public void setSail(int value) {

		int angle = value;
		String com = this.buildCommand(sailNo, angle);
		this.AKSENCommand(com);

	}

	/** 
	 * Send one Propellor-Command to AKSEN-Board
	 * @param int angle
	 * @see de.fhb.sailboat.serial.actuator.LocomotionSystem#setPropeller(int)
	 */
	@Override
	public void setPropellor(int value) {

		int angle = value;
		String com = this.buildCommand(propellorNo, angle);
		this.AKSENCommand(com);

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

	

	private void AKSENCommand(String com) {
			try {
				this.myCOM.writeString(com);
				
				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = this.myCOM.readByte()) > -1) {
				 System.out.println(new String(buffer,0,len));
				
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public int getBatteryState() {
		int state = -1;
		try {
			this.myCOM.writeString("v");

			
//			int b = this.myCOM.readByte();
//			
//			System.out.println(b);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = this.myCOM.readByte()) > -1) {
				System.out.println(new String(buffer,0,len));
			}
			
			
			
		} catch (IOException e) {
			LOG.warn("Couldn't get BatteryState", e);
		}

		
		return state;
	}
}
