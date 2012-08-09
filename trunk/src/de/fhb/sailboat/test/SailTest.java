package de.fhb.sailboat.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SailTest {

	File file;
	FileWriter writer;

	public static void main(String[] args) {

		File file = new File("FileWriterTest.txt");
		try {
			FileWriter writer = new FileWriter(file ,false);
			writer.write(String.format("Windgeschw.\tWindrichtung\tk\tdesired Heeling\n"));
		
		
		for (int l = 0; l <= 10; l++)
		{
			for (int i = 0; i <= 15; i++) {
				for (int j = 0; j <= 360;) {
					double k = (double) l/10;
					String str = String.format("%d\t%d\t%s\t%f\n", i, j, k, desiredHeeling(k, i, j)); 
					writer.write(str);
					j+=10;
				}
			writer.flush();
			}
		}
		
		writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static double desiredHeeling(double k, double v, double a)
	{
		// h = max(0,(h{max} - k*|a|)* (min(v,v{max})/v{max}))
		double h = 0d;
		// v actual windspeed
		//double v = windModel.getWind().getSpeed();
		// a = atmospheric wind in degree ( -180 to 180° )
		//double a = calculateAtmosphericWind(compassModel.getCompass().getYaw(), windModel.getWind().getDirection(), v, gpsModel.getPosition().getSpeed());
		// for now, lets take the rel Wind Direction
		//double a = windModel.getWind().getDirection();
		
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
