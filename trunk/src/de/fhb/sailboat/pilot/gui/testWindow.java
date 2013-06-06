package de.fhb.sailboat.pilot.gui;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import opencsv.CSVReader;
import de.fhb.sailboat.control.pilot.PIDController;

public class testWindow {

	private static PIDController controller;
	
	public static void main(String args[]){
		String fileName="C:\\Users\\Tobias\\Desktop\\Projekt Autonomes Segelboot Eclipse\\Segelboot2\\log\\sailboat_15_05_2013_wasser.log_1_1.csv";
		CSVReader csvReader = null;
		List<String[]> csvEntries=null;
		ArrayList<Double> compassDatas = new ArrayList<Double>();
		ArrayList<Double> desiredData = new ArrayList<Double>();
		try {
			csvReader = new CSVReader(new FileReader(fileName),';');
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		  csvEntries = csvReader.readAll();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		for(int i=1;i<csvEntries.size();i++){
			compassDatas.add(Double.valueOf(csvEntries.get(i)[1]));
			desiredData.add(Double.valueOf(csvEntries.get(i)[2]));	
		}
		controller = new PIDController();
		Random random = new Random();
		for(int i=0;i<compassDatas.size();i++){
			double deltaAngle=desiredData.get(i)-compassDatas.get(i);
			controller.controll(transformAngle((int) deltaAngle));
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static int transformAngle(int pAngle) {
		int angle = pAngle % 360;
		
		if (angle > 180) {
			angle -= 360;
		} else if (angle < -180) {
			angle += 360;
		}
		return angle;
	}
	
}
