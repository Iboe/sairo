package de.fhb.sairo.main;

import de.fhb.sairo.gui.mainTabbed;

public class MainGui {

	private static de.fhb.sairo.gui.main gui;
	private static mainTabbed tabbedGui;
	private static ControllerTabbed controller;
	private static Model model;
	
	public static void main(String args[]){
		model = new Model();
//		gui = new de.fhb.sairo.gui.main();
		tabbedGui = new mainTabbed();
//		controller = new Controller(gui, model);
		controller = new ControllerTabbed(tabbedGui, model);
	}
	
}
