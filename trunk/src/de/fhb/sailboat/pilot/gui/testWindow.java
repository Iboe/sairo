package de.fhb.sailboat.pilot.gui;

import java.util.Random;

import de.fhb.sailboat.control.pilot.PIDController;

public class testWindow {

	private static PIDController controller;
	
	public static void main(String args[]){
		controller = new PIDController();
		Random random = new Random();
		for(int i=0;i<1000;i++){
			int max=180;
			int min=0;
			controller.controll(random.nextInt(max - min + 1) + min);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
