package de.fhb.sailboat.test;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Testing the {@link Pilot} layer with the implementation {@link PilotImpl}.<br>
 * The test includes the execution of the method {@link Pilot#driveAngle(int)} with the following desired compass angles: <br>
 * 30°, 45°, 65°, 200°, -160°, -30°<br>
 * The result is either printed on the console (using {@link DummyLoco}) or sent to the actuator (using {@link AKSENLocomotion}) and must be visually evaluated by the tester.
 * 
 * @author Michael Kant
 *
 */
public class PilotTest {

	/**
	 * Entry point of the test application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) {
		
		//Pilot p=new PilotImpl(new DummyLoco());
		Pilot p=new PilotImpl(new AKSENLocomotion());
		System.out.println("PILOT ERSTELLT"); 
		WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(0,0,0,System.currentTimeMillis()));
		
		try{
			System.out.println("attempt angle: 30");
			p.driveAngle(30);
			Thread.sleep(2000);
			System.out.println("attempt angle: 45");
			p.driveAngle(45);
			Thread.sleep(2000);
			System.out.println("attempt angle: 65");
			p.driveAngle(65);
			Thread.sleep(2000);
			System.out.println("attempt angle: 200");
			p.driveAngle(200);
			Thread.sleep(2000);
			System.out.println("attempt angle: -160");
			p.driveAngle(-160);
			Thread.sleep(2000);
			System.out.println("attempt angle: -30");
			p.driveAngle(-30);
			Thread.sleep(2000);
		}
		catch(InterruptedException e){
			
			e.printStackTrace();
		}
	}
}
