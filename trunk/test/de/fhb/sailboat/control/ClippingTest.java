package de.fhb.sailboat.control;

public class ClippingTest {

	private static final int RUDDER_NUMBER = 1;
	private static final int RUDDER_LEFT = 34;
	private static final int RUDDER_RIGHT = 108;
	
	private static final int PROPELLOR_NUMBER = 2;
	private static final int PROPELLOR_MIN = 170;
	private static final int PROPELLOR_MAX = 80;
	
	private static final int SAIL_NUMBER = 0;
	private static final int SAIL_SHEET_IN = 31;
	private static final int SAIL_SHEET_OUT = 114;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test Rudder clipping");
		System.out.println("Rudder 34 (MAX LEFT): " + clipActuatorValues(34,1));
		System.out.println("Rudder 108 (MAX RIGHT): " + clipActuatorValues(108, 1));
		System.out.println("Rudder 33 (OVER MAX LEFT): " + clipActuatorValues(33, 1));
		System.out.println("Rudder 109 (OVER MAX RIGHT): " + clipActuatorValues(109, 1));
		System.out.println("\n\n");
		System.out.println("Test Propellor clipping");
		System.out.println("Propellor 170  (MIN): " + clipActuatorValues(170, 2));
		System.out.println("Propellor 80 (MAX): "+ clipActuatorValues(80, 2));
		System.out.println("Propellor 180  (OVER MIN): "+ clipActuatorValues(180, 2));
		System.out.println("Propellor 70 (OVER MAX): "+ clipActuatorValues(70, 2));
		System.out.println("\n\n");
		System.out.println("Test Sail clipping");
		System.out.println("Sail 31 (Sheet in): " + clipActuatorValues(31, 0));
		System.out.println("Sail 114 (Sheet out): "+ clipActuatorValues(114, 0));
		System.out.println("Sail 30 (over sheet in): "+ clipActuatorValues(30, 0));
		System.out.println("Sail 115 (over sheet out): "+ clipActuatorValues(115, 0));
	}
	
	/***
	 * Clipping the actually value if needed, means, if a value is out of the described range
	 * the value will be set to limitation value
	 * @author Tobias Koppe
	 * @param pValue inputs the actually value before clipping
	 * @param pActuator inputs the type of actuator
	 * @return clipped value for actuator
	 */
	private static int clipActuatorValues(int pValue, int pActuator){
		int returnValue=pValue;
		if(pActuator==RUDDER_NUMBER){
			if(pValue<RUDDER_LEFT){
				returnValue=RUDDER_LEFT;
				System.out.println("Clipping  to max rudder left");
			}
			else if(pValue>RUDDER_RIGHT){
				returnValue=RUDDER_RIGHT;
				System.out.println("Clipping to max rudder right");
			}
			System.out.println("clipping value for rudder from " + pValue + " to " + returnValue);
		}
		else if (pActuator==PROPELLOR_NUMBER){
			if(pValue>PROPELLOR_MIN){
				returnValue=PROPELLOR_MIN;
				System.out.println("Clipping to min propellor");
			}
			else if(pValue<PROPELLOR_MAX){
				returnValue=PROPELLOR_MAX;
				System.out.println("Clipping to max propellor");
			}
			System.out.println("clipping value for propellor from " + pValue + " to " + returnValue);
		}
		else if(pActuator==SAIL_NUMBER){
			if(pValue<SAIL_SHEET_IN){
				returnValue=SAIL_SHEET_IN;
			}
			else if(pValue>SAIL_SHEET_OUT){
				returnValue=SAIL_SHEET_OUT;
			}
			System.out.println("clipping value for sail sheet from " + pValue + " to " + returnValue);
		}
		return returnValue;
	}

}
