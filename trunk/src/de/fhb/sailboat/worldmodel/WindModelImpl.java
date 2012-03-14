package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Wind;

public class WindModelImpl implements WindModel {

	private Wind wind;
	private History<Wind> history;
	
	public WindModelImpl() {
		this.history = new History<Wind>(System.getProperty(
				WindModel.HISTORY_SIZE_PROPERTY));
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
}
