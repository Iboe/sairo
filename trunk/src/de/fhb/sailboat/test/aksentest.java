package de.fhb.sailboat.test;

import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
public class aksentest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AKSENLocomotion aksen = new AKSENLocomotion();
		
		// Ruder setzen auf "voll nach backbord 
		System.out.println("Setze fehlerhaften Befehl ab:");
		aksen.setRudder(4);
		//System.out.println("Antwort: "+ aksen.lastAnswer());
		System.out.println("\n Setze korrekten Befehl ab:");
		aksen.setSail(73);
		//System.out.println("Antwort: "+ aksen.lastAnswer());
		
		//aksen.closePort();
	}

}