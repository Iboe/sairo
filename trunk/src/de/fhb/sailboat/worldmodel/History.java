package de.fhb.sailboat.worldmodel;

import java.util.LinkedList;
import java.util.List;
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
*/
public class History<T> {

	public static final int DEFAULT_MAX_SIZE = 10;
	public static final String MAX_SIZE_PROPERTY_NAME = ".historySize";
	//private static final Logger LOG = LoggerFactory.getLogger(History.class);
	
	private LinkedList<T> history;
	private int maxSize; //final, can just be altered by init method, which
						 //is just called by constructor
	
	public History() {
		this(DEFAULT_MAX_SIZE);
		
	}
	
	public History (String maxSize) {
		if (maxSize == null) {
			init(DEFAULT_MAX_SIZE);
		} else {
			try {
				init(Integer.parseInt(maxSize));
			} catch (NumberFormatException ne) {
				//LOG.warn("could not parse configuration value for history maximum size of history");
				init(DEFAULT_MAX_SIZE);
			}
		}
	}
	
	public History(int maxSize) {
		init(maxSize);
	}

	public void addElement(T element) {
		if (history.size() == maxSize) {
			history.removeLast();
		}
		history.addFirst(element);
	}
	
	public int getMaxSize() {
		return maxSize;
	}

	public List<T> getHistory() {
		return history;
	}
	
	private void init(int maxSize) {
		this.maxSize = maxSize;
		this.history = new LinkedList<T>();
	}
}
