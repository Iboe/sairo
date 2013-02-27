package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.Compass;

/**
 * Concrete implementation of the {@link CompassModel}.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link CompassModel}
 */
public class CompassModelImpl implements CompassModel {

	private Compass compass;
	private History<Compass> history;
	
	public CompassModelImpl() {
		this.history = new History<Compass>(System.getProperty(
				CompassModel.HISTORY_SIZE_PROPERTY));
		compass = new Compass(0, 0, 0);
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
