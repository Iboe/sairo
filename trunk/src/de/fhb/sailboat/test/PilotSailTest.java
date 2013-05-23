package de.fhb.sailboat.test;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.start.PropertiesInitializer;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * Testing the correct sail adjustment of the {@link Pilot} layer, <br>
 * by setting specific compass, wind and gps values into the world model,<br>
 * and executing the {@link Pilot#driveAngle(int)} method with an angle of 45°.
 * 
 * @author Michael Kant
 *
 */
public class PilotSailTest {

	/**
	 * Entry point of the test application.
	 * @param args No parameters expected.
	 */
	public static void main(String[] args) {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();		
		//Pilot p=new PilotImpl(new DummyLoco());
		Pilot p=new PilotImpl(new AKSENLocomotion());
		System.out.println("PILOT ERSTELLT"); 
		WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(0,0,0,System.currentTimeMillis()));
		WorldModelImpl.getInstance().getWindModel().setWind(new Wind(0, 1.5,System.currentTimeMillis()));
		WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(52.426923,12.565542, 0.5,System.currentTimeMillis()));
		
		try{
			System.out.println("attempt angle: 45");
			p.driveAngle(45);
			Thread.sleep(2000);
					}
		catch(InterruptedException e){
			
			e.printStackTrace();
		}
	}
}
