package de.fhb.sailboat.test;

import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.test.Initializier.PropertiesInitializer;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class PilotSailTest {

	public static void main(String[] args) {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();		
		//Pilot p=new PilotImpl(new DummyLoco());
		Pilot p=new PilotImpl(new AKSENLocomotion());
		System.out.println("PILOT ERSTELLT"); 
		WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(0,0,0));
		WorldModelImpl.getInstance().getWindModel().setWind(new Wind(0, 1.5));
		WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(52.426923,12.565542, 0.5));
		
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
