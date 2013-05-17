package de.fhb.sairo.main;

public class MainGui {

	private static de.fhb.sairo.gui.main gui;
	private static Controller controller;
	private static Model model;
	
	public static void main(String args[]){
		model = new Model();
		gui = new de.fhb.sairo.gui.main();
		controller = new Controller(gui, model);
	}
	
}
