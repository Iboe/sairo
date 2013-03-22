package de.fhb.sailboat.gui;

import de.fhb.sailboat.start.PropertiesInitializer;

/**
 * This class can be used to test the GUI Application. It is advised to use
 * the more advanced de.fhb.sailboat.test.UferGUITest class since it
 * incorporates dummy sensor and connection data.
 *
 * @author Patrick Rutter
 */
public class GUITest {
	
	/**
	 * Main for execution of simple GUI test.
	 * @param args
	 */
	public static void main(String[] args) {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
        GUI view = new GUI(null);
        view.setVisible(true);
    }
	
}
