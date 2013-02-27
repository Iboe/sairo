package de.fhb.sailboat.worldmodel;

import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic class which maintains a finite list (history) of elements. <br>
 * When the list capacity is reached, the oldest element will be removed, when adding a new one.<br>
 * The class of the elements to store is specified by the template parameter T.
 * 
 * @author Helge Scheel, Michael Kant
 *
 * @param <T> The class of the elements to store in the history.
 */
public class History<T> {

	private static final Logger LOG = LoggerFactory.getLogger(History.class);
	
	/**
	 * Default maximum size of 10 elements.
	 */
	public static final int DEFAULT_MAX_SIZE = 10;
	
	/**
	 * Property key that's used for specifying the desired history size in the configuration file.
	 */
	public static final String MAX_SIZE_PROPERTY_NAME = ".historySize";

	/**
	 * Actual maximum size at runtime.
	 */
	private final int maxSize;
	
	/**
	 * List that stores the added elements.
	 */
	private LinkedList<T> history;
	//used for not logging too much
	private int debugCounter = 0;
	
	/**
	 * Default constructor, which initializes the history with the {@link #DEFAULT_MAX_SIZE}.
	 */
	public History() {
		
		this(DEFAULT_MAX_SIZE);
	}
	
	/**
	 * Initialization constructor, which creates the history with the given history size.
	 * 
	 * @param maxSize String representation of an integer value, which defines the desired history size.
	 */
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
	
	/**
	 * Initialization constructor, which creates the history with the given history size.
	 * 
	 * @param maxSize Defines the desired history size.
	 */
	public History(int maxSize) {
		
		this.maxSize = maxSize;
		this.history = new LinkedList<T>();
	}

	/**
	 * Adds an element to the top of the history. Removes the last element, if the maximum history size has reached.  
	 * 
	 * @param element The element to add.
	 */
	public void addElement(T element) {
		if (history.size() == maxSize) {
			history.removeLast();
		}
		history.addFirst(element);
		
		if (++debugCounter == maxSize) {
			LOG.debug("element added: {}", element);
			debugCounter = 0;
		}
	}
	
	/**
	 * Returns the maximum amount of elements, this history can carry.
	 * @return The maximum amount of elements, this history can carry.
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * Returns the history as {@link List} of its elements. 
	 * @return The history as {@link List} of its elements.
	 */
	public List<T> getHistory() {
		return history;
	}
}
