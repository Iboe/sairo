package de.fhb.sailboat.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.carrierAdapters.CommTCPServer;
import de.fhb.sailboat.communication.serverModules.CompassTransmitter;
import de.fhb.sailboat.communication.serverModules.GPSTransmitter;
import de.fhb.sailboat.communication.serverModules.WindTransmitter;
import de.fhb.sailboat.control.navigator.Navigator;
import de.fhb.sailboat.control.navigator.NavigatorImpl;
import de.fhb.sailboat.control.pilot.Pilot;
import de.fhb.sailboat.control.pilot.PilotImpl;
import de.fhb.sailboat.control.planner.Planner;
import de.fhb.sailboat.control.planner.PlannerImpl;
import de.fhb.sailboat.gui.GUI;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;
import de.fhb.sailboat.serial.actuator.LocomotionSystem;
import de.fhb.sailboat.serial.sensor.CompassSensor;
import de.fhb.sailboat.serial.sensor.GpsSensor;
import de.fhb.sailboat.serial.sensor.WindSensor;

/**
 * Initializes the application when the main method is started. Loads properties and initializes all 
 * control layers, all sensors, the user interface and the communication between boat application
 * and user interface.
 * 
 * @author hscheel
 *
 */
public class Initializier {

	protected static final Logger LOG = LoggerFactory.getLogger(Initializier.class);
	
	protected Planner planner;
	protected Navigator navigator;
	protected Pilot pilot;
	protected LocomotionSystem loco;
	protected GUI view;
	
	public static void main(String[] args) {
		Initializier init = new Initializier();
		PropertiesInitializer propsInit = new PropertiesInitializer();
		
		propsInit.initializeProperties();
		init.initializeControl();
		init.initializeSensors();
		init.initializeView();
	}
	
	protected void initializeCommunication() {
		CommunicationBase server=new CommTCPServer(6699);
		
		server.registerModule(new GPSTransmitter());
		server.registerModule(new CompassTransmitter());
		server.registerModule(new WindTransmitter());
		
		if (!server.initialize()) {
			LOG.warn("Unable to start the communications TCP server on port 6699");
		}
	}
	
	protected void initializeControl() {
		loco = new AKSENLocomotion();
		pilot = new PilotImpl(loco);
		navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	protected void initializeSensors() {
		LOG.info("-----init sensors-----");
		
		GpsSensor gps = new GpsSensor(12);
		WindSensor wind = new WindSensor(4);
		CompassSensor compassSensor = new CompassSensor(); //zzt. COM17
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		LOG.info("-----init sensors done-----");
	}
	
	protected void initializeView() {
		view = new GUI(planner);
		view.setVisible(true);
	}
}
