package de.fhb.sailboat.missionplayer;

import java.io.Serializable;

/**
 * This class joins two different data to a data-tupel
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class Tupel<T,V> implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T object1;
	 private V object2;

	  public Tupel(T o1, V o2) {
	    object1 = o1;
	    object2 = o2;
	  }

	   public T getO1() {
	     return (T)object1;
	   }

	   public V getO2() {
	     return (V) object2;
	   }
	}