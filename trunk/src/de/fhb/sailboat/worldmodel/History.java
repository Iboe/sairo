package de.fhb.sailboat.worldmodel;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class History<T> {

	public static final int DEFAULT_MAX_SIZE = 10;
	public static final String MAX_SIZE_PROPERTY_NAME = ".historySize";
	private static final Logger LOG = LoggerFactory.getLogger(History.class);
	
	private final int maxSize;
	private LinkedList<T> history;
	
	public History() {
		this(DEFAULT_MAX_SIZE);
	}
	
	public History (String maxSize) {
		if (maxSize == null) {
			this.maxSize = DEFAULT_MAX_SIZE;
		} else {
			int maxSizeTemp;
			
			try {
				maxSizeTemp = Integer.parseInt(maxSize);
			} catch (NumberFormatException ne) {
				LOG.warn("could not parse configuration value for history maximum size of history");
				maxSizeTemp = DEFAULT_MAX_SIZE;
			}
			this.maxSize = maxSizeTemp;
		}
		this.history = new LinkedList<T>();
	}
	
	public History(int maxSize) {
		this.maxSize = maxSize;
		this.history = new LinkedList<T>();
	}

	public void addElement(T element) {
		if (history.size() == maxSize) {
			history.removeLast();
		}
		history.addFirst(element);
		LOG.debug("element added: {}", element);
	}
	
	public int getMaxSize() {
		return maxSize;
	}

	public List<T> getHistory() {
		return history;
	}
}
