package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Wind;

/**
 * Concrete implementation of the {@link WindModel}.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link WindModel}
 */
public class WindModelImpl implements WindModel {

	private Wind wind;
	private History<Wind> history;
	
	/**
	 * Default constructor, which sets an initial default {@link Wind} value into the {@link WindModel}.
	 */
	public WindModelImpl() {
		this.history = new History<Wind>(System.getProperty(
				WindModel.HISTORY_SIZE_PROPERTY));
		wind = new Wind(0, 0,System.currentTimeMillis());
	}
	
	@Override
	public synchronized void setWind(Wind wind) {
		this.wind = wind;
		history.addElement(wind);
	}
	
	@Override
	public Wind getWind() {
		return wind;
	}
	
	@Override
	public List<Wind> getHistory() {
		return history.getHistory();
	}
	
	@Override
	public Wind calcAverageWind() {
		List<Wind> history = getHistory();
		
		if (!history.isEmpty()) {
			int averageDirection = 0;
			double averageSpeed = 0d;
			
			
			for (Wind i : history) {
				averageDirection += i.getDirection();
				averageSpeed += i.getSpeed();
			}
			
			averageDirection /= history.size();
			averageSpeed /= history.size();
			
			return new Wind(averageDirection, averageSpeed,System.currentTimeMillis());
		} else {
			return null;
		}
	}
}
