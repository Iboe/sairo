package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.GPS;

/**
 * Concrete implementation of the {@link GPSModel}.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @see {@link GPSModel}
 */
public class GPSModelImpl implements GPSModel {

	private GPS position;
	private History<GPS> history;
	
	/**
	 * Default constructor, which sets an initial default {@link GPS} value into the {@link GPSModel}.
	 */
	public GPSModelImpl() {
		this.history = new History<GPS>(System.getProperty(
				GPSModel.HISTORY_SIZE_PROPERTY));
		position = new GPS(0d, 0d, 0);
	}

	@Override
	public synchronized void setPosition(GPS position) {
		this.position = position;
		history.addElement(position);
	}

	@Override
	public GPS getPosition() {
		return position;
	}
	
	@Override
	public List<GPS> getHistory() {
		return history.getHistory();
	}
}
