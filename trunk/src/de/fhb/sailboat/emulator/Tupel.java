package de.fhb.sailboat.emulator;

import java.io.Serializable;

public class Tupel<T,V> implements Serializable{
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