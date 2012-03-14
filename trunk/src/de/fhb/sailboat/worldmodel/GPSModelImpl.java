package de.fhb.sailboat.worldmodel;

import java.util.List;

import de.fhb.sailboat.data.GPS;

public class GPSModelImpl implements GPSModel {

	private GPS position;
	private History<GPS> history;
	
	public GPSModelImpl() {
		this.history = new History<GPS>(System.getProperty(
				GPSModel.HISTORY_SIZE_PROPERTY));
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
