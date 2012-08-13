package de.fhb.sailboat.sensors.lib;

public class SerialCommPort {
	private int portNr;
	private int baudRate;
	
	public SerialCommPort(int portNr){
		this.portNr = portNr;
	}

	public int getPortNr() {
		return portNr;
	}

	public void setPortNr(int portNr) {
		this.portNr = portNr;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
}
