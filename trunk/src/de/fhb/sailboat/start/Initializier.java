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
import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
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
	
	/**
	 * Entry point for the application. Loads properties and initializes all control layers, all 
	 * sensors, the user interface and the communication between boat application and user interface.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Initializier init = new Initializier();
		
		initializeProperties();
		init.initializeControl();
		init.initializeSensors();
		init.initializeView();
	}
	
	/**
	 * 
	 */
	public static void initializeProperties() {
		PropertiesInitializer propsInit = new PropertiesInitializer();
		propsInit.initializeProperties();
	}
	
	/**
	 * Initializes the communication between boat application and user interface. Starts the server
	 * and registers modules for transmitting {@link GPS}, {@link Compass} and {@link Wind} data.
	 */
	protected void initializeCommunication() {
		CommunicationBase server = new CommTCPServer(CommTCPServer.LISTEN_PORT);
		
		server.registerModule(new GPSTransmitter());
		server.registerModule(new CompassTransmitter());
		server.registerModule(new WindTransmitter());
		
		if (!server.initialize()) {
			LOG.warn("Unable to start the communications TCP server on port " + CommTCPServer.LISTEN_PORT);
		}
	}
	
	/**
	 * Initializes the {@link Planner}, the {@link Navigator}, the {@link Pilot} and the
	 * {@link LocomotionSystem}.
	 */
	protected void initializeControl() {
		loco = new AKSENLocomotion();
		pilot = new PilotImpl(loco);
		navigator = new NavigatorImpl(pilot);
		planner = new PlannerImpl(navigator);
	}
	
	/**
	 * Initializes the sensors for {@link GPS}, {@link Compass} and {@link Wind} data. Waits for
	 * two seconds to get the sensors time to get ready.
	 */
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
	
	/**
	 * Initializes the user interface and connects it with the {@link Planner}.
	 */
	protected void initializeView() {
		view = new GUI(planner);
		view.setVisible(true);
	}
}
