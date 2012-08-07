package de.fhb.sailboat.test;

public class SailTest {

	public static void main(String[] args) {
//		double a;
		System.out.printf("Windgeschw. | Windrichtung | desired Heeling\n\n");
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j <= 360;) {
				System.out.printf("%s | %s | %s\n", i, j, desiredHeeling(i, j));
				j+=10;
			}
			
		}
		
	}
	
	private static double desiredHeeling(double v, double a)
	{
		// h = max(0,(h{max} - k*|a|)* (min(v,v{max})/v{max}))
		double h = 0d;
		// v actual windspeed
		//double v = windModel.getWind().getSpeed();
		// a = atmospheric wind in degree ( -180 to 180° )
		//double a = calculateAtmosphericWind(compassModel.getCompass().getYaw(), windModel.getWind().getDirection(), v, gpsModel.getPosition().getSpeed());
		// for now, lets take the rel Wind Direction
		//double a = windModel.getWind().getDirection();
		double k = 1;
		double Hmax = 45d;  
		double Vmax = 15d;
		
		// v{max}
		double x;
		if ( v <= Vmax) {
			x = v/Vmax;
		} else {
			x = 1;
		}
		
		h = ((Hmax - k*Math.abs(a)) * x );
		
		if (h > 0) 
			return h;
		else
			return 0;
	}
}
