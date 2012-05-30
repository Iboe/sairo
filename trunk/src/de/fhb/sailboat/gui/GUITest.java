package de.fhb.sailboat.gui;

import de.fhb.sailboat.test.Initializier.PropertiesInitializer;

public class GUITest {
	
	public static void main(String[] args) {
        // TODO code application logic here
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
        GUInterface view = new GUInterface(null);
        view.setVisible(true);
    }
	
}
