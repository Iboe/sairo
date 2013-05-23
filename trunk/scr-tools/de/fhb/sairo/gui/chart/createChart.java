package de.fhb.sairo.gui.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import de.fhb.sairo.data.CompassCourseList;

public class createChart {
	
	public static DefaultCategoryDataset createCompassCourseChartDataset(CompassCourseList pList){
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		System.out.println("Create dataset");
		for(int i=0;i<pList.size();i++){
			System.out.println("Add dataset: [" + pList.get(i).getCompassCourseAzimuth()+";"+ "Course" +";"+ pList.get(i).getTimeStampString() +"]");
			dataSet.addValue(pList.get(i).getCompassCourseAzimuth(), "Course", pList.get(i).getTimeStampString());
		}
		System.out.println("dataset created");
		return dataSet;
	}
	
	
	public static JFreeChart createLineChart(DefaultCategoryDataset pDataSet){
		JFreeChart chart = ChartFactory.createLineChart("Compasscourse", "time", "course", pDataSet, PlotOrientation.VERTICAL, true, true, false);
		System.out.println("Chart created");
		return chart;
	}
	
	public static ChartPanel createChartPanel(JFreeChart pChart){
		ChartPanel panel = new ChartPanel(pChart);
		System.out.println("ChartPanel created");
		return panel;
	}
	
}
