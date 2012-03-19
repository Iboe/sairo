package de.fhb.sailboat.ufer.prototyp;


import java.util.Random;

/**
 * This class manages the data pipe between view, model and the sensors.
 * @author Patrick Rutter
 * @lastUpdate 03.02.2012
 *
 */
public class Controller {

	// Variables
	private Model model;
	
	public Controller() {
		this.model = new Model();
	}
	
	// Updater (used to update a sensor reading and store it in model, kind of like a more sophisticated setter)
	/**
	 * As the name suggests, this method calls ALL (existing) update methods to get the most recent values possible at once.
	 */
	public void updateAll() {
		updateGpsLongitude();
		updateGpsLatitude();
		updateShipGpsLongitude();
		updateShipGpsLatitude();
	}
	
	/**
	 * This method is used to create an wide array of pseudo-random values
	 * for testing the GUI's ability to display values.
	 */
	public void updateRandom() {
		Random dice = new Random();

		this.model.setCompDirection(dice.nextInt(361));
		
		this.model.setCompTemperature(dice.nextInt(15)
				+ Math.round((dice.nextDouble() * 100.0)) / 100.0);
		
		this.model.setWindDirection(dice.nextInt(361));
		
		this.model.setWindVelocity(dice.nextInt(3000));
		
		this.model.setGpsLongitude(dice.nextInt(1000)
				+ Math.round((dice.nextDouble() * 1000.0)) / 100.0);
		
		this.model.setGpsLatitude(dice.nextInt(1000)
				+ Math.round((dice.nextDouble() * 1000.0)) / 100.0);
		
		this.model.setGpsPrecision((float) dice.nextInt(2));
	}
	
	public void updateGpsLongitude() {
		int myValue = 0;
		
		//pseudocode: myValue = boat.gps.getLongitude();
		
		this.model.setGpsLongitude(myValue);
	}
	
	public void updateGpsLatitude() {
		int myValue = 0;
		
		//pseudocode: myValue = boat.gps.getLatitude();
		
		this.model.setGpsLatitude(myValue);
	}
	
	public void updateShipGpsLongitude() {
		int myValue = 0;
		
		//pseudocode: myValue = boat.gps.getLongitude();
		
		this.model.setGpsLongitude(myValue);
	}
	
	public void updateShipGpsLatitude() {
		int myValue = 0;
		
		//pseudocode: myValue = boat.gps.getLatitude();
		
		this.model.setGpsLatitude(myValue);
	}
	
	// Getter ("tunneled" from Model)
	public double getCompTemperature() {
		return this.model.getCompTemperature();
	}

	public int getCompDirection() {
		return this.model.getCompDirection();
	}

	public int getWindDirection() {
		return this.model.getWindDirection();
	}

	public int getWindVelocity() {
		return this.model.getWindVelocity();
	}

	public double getGpsLongitude() {
		return this.model.getGpsLongitude();
	}

	public double getGpsLatitude() {
		return this.model.getGpsLatitude();
	}

	public double getGpsPrecision() {
		return this.model.getGpsPrecision();
	}

	public double getGpsTargetLongitude() {
		return this.model.getGpsTargetLongitude();
	}

	public double getGpsTargetLatitude() {
		return this.model.getGpsTargetLatitude();
	}
}
