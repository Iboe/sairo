package de.fhb.sailboat.gui.map;

import de.fhb.sailboat.data.GPS;

public class Constants {
	public static final int NO_MARK = 0;
	public static final int MARKER = 1;
	public static final int RECTANGLE = 2;
	public static final int POLYGON = 3;
	
	public static final int MAXIMUM_COUNT_LAST_POSITION = 30;
	
	public static final int PIXEL_TO_CALCULATE_SCALE = 80;
	
	public static final int EARTH_CIRCUMFERENCE = 40074000;
	
	public static final GPS FH_BRANDENBURG = new GPS(52.410771, 12.538745);
	public static final GPS REGATTASTRECKE = new GPS(52.426458, 12.56414);
}
