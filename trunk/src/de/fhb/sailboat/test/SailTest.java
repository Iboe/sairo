package de.fhb.sailboat.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import de.fhb.sailboat.control.pilot.Calculations;

public class SailTest {

	File file;
	FileWriter writer;

	public static void main(String[] args) {

		File file = new File("FileWriterTest.txt");
		try {
			FileWriter writer = new FileWriter(file ,false);
			writer.write(String.format("WindAngle.\tWindSpeed\tBoatSpeed\tTrueWindAngle\tTrueWindSpeed\n"));
			Calculations c = new Calculations();
		// Angle
		for (int i = -18; i <= 18; i++)
		{
			// WindSpeed
			for (int j = 0; j <= 10; j++) {
				// BoatSpeed
				for (int k = 0; k <= 10;k++) {
					
					c.trueWind((double) i*10, (double) j, (double) k);
					String str = String.format("%d\t%d\t%d\t%f\t%f\n", i*10, j, k, c.getTrue_diff(), c.getTrue_speed()); 
					writer.write(str);
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
	

}
