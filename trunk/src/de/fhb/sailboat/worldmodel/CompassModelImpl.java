package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Compass;

public class CompassModelImpl implements CompassModel {

	private Compass compass;
	private History<Compass> history;
	
	public CompassModelImpl() {
		this.history = new History<Compass>(System.getProperty(
				CompassModel.HISTORY_SIZE_PROPERTY));
	}

	@Override
	public synchronized void setCompass(Compass compass) {
		this.compass = compass;
		history.addElement(compass);
	}

	@Override
	public Compass getCompass() {
		return compass;
	}
	
	@Override
	public List<Compass> getHistory() {
		return history.getHistory();
	}
}
