package de.fhb.sailboat.operationPlayer;

import java.io.Serializable;

public class Tripel<T,U,V> implements Serializable{
	 private T object1;
	 private U object2;
	 private V object3;

	  public Tripel(T o1, U o2, V o3) {
	    object1 = o1;
	    object2 = o2;
	    object3 = o3;
	  }

	   public T getO1() {
	     return (T)object1;
	   }

	   public U getO2() {
	     return (U) object2;
	   }
	   
	   public V getO3() {
		     return (V) object3;
		   }
}