package de.fhb.sailboat.control;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

import de.fhb.sailboat.serial.actuator.AKSENLocomotionImpl;
import de.fhb.sailboat.serial.serialAPI.COMPort;

public class AksenTest {

	private static COMPort comPort;
	private static final Logger LOG = Logger.getLogger(AKSENLocomotionImpl.class);
	private static final String COM_PORT = System.getProperty(AKSENLocomotionImpl.class.getSimpleName() + ".comPort");
	private static final String BAUDRATE = System.getProperty(AKSENLocomotionImpl.class.getSimpleName() + ".baudrate");

	private final String TAG = this.getClass().getSimpleName() + ".";
	
	public static void main (String[] args){
		init();
	}
	
	private static void init(){
		comPort = new COMPort(2, 9600, 0);
	}
	
	@Test
	public void testConnection(){
		String methodName = "testConnection | ";
		Byte send,receive;
		send = 0x73;
		try {
			LOG.info(TAG + methodName + "Sende s an AKSEN Board ...");
			this.comPort.writeByte(send);
			LOG.info(TAG + methodName + "Lese von AKSEN Board ...");
			receive = (byte)this.comPort.readByte();
			LOG.info(TAG + methodName + "Gelesenes Zeichen von AKSEN: " + receive); 
			assertEquals(send, receive);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSendOneCommand(){
		String methodName = "testSendOneCommand | ";
		Byte send,receive;
		send = 0x61; // Damit Abarbeitungen mit vorigen Verbindungen abgearbeitet werden und Verbindungen geschlossen werden
		LOG.info(TAG + methodName + "Sende s am AKSEN Board um aktuelle Verbindungen zu beenden ...");
		try {
			this.comPort.writeByte(send);
			LOG.info(TAG + methodName + "Lese von AKSEN Board ...");
			receive = (byte) this.comPort.readByte();
			LOG.info(TAG + methodName + "Gelesenes Zeichen von AKSEN: " + receive);
			ak
		} catch (IOException e) {
			e.printStackTrace();
		}
		send = 0x73;
	}
	
	@Test
	public void testSendMassiveCommands(){
		
	}
	
}
