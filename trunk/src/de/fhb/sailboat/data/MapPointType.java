package de.fhb.sailboat.data;

/**
 * Enumeration of types describing a map point. The unique serialID is used for 
 * easier serialization.
 * 
 * @author hscheel
 *
 */
public enum MapPointType {

	Obstacle(1), 
	Boat(2),
	Goal(3);
	
	private int serialID;
	
	/**
	 * Private constructor for adding the serialization ID.
	 * @param serialID
	 */
	private MapPointType(int serialID) {
		this.serialID = serialID;
	}
	
	/**
	 * Getter for the serialization ID.
	 * @return the ID used for serialization
	 */
	public int getSerialID() {
		return serialID;
	}
}
