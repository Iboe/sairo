package de.fhb.sailboat.test;

import de.fhb.sailboat.control.Pilot;
import de.fhb.sailboat.control.PilotImpl;
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

public class PilotTest {

	public static void main(String[] args) {
		
		//Pilot p=new PilotImpl(new DummyLoco());
		Pilot p=new PilotImpl(new AKSENLocomotion());
		System.out.println("PILOT ERSTELLT"); 
		WorldModelImpl.getInstance().getCompassModel().setCompass(new Compass(0,0,0));
		
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
