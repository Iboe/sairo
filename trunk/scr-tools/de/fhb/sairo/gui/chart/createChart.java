package de.fhb.sairo.gui.chart;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import de.fhb.sairo.data.CompassCourseList;
import de.fhb.sairo.data.pidControllerStateList;
import de.fhb.sairo.data.Data.PidControllerState;

public class createChart {
	
	public static DefaultCategoryDataset createCompassCourseChartDataset(CompassCourseList pList){
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for(int i=0;i<pList.size();i++){
			System.out.println("Add dataset: [" + pList.get(i).getCompassCourseAzimuth()+";"+ "Course" +";"+ pList.get(i).getTimeStampString() +"]");
			dataSet.addValue(pList.get(i).getCompassCourseAzimuth(), "Course", pList.get(i).getTimeStampString());
		}
		System.out.println("dataset created");
		return dataSet;
	}
	
	public static XYDataset createXyDataSet(CompassCourseList pList , double pDesiredCourse){
		System.out.println("Add " + pList.size() + " elements to chart");
		XYSeriesCollection dataSet = new XYSeriesCollection();
		
		XYSeries drivenCourse = new XYSeries("CompassCourse");
		XYSeries desiredCourse = new XYSeries("desired Angle("+pDesiredCourse+")");
		
		for(int i=0;i<pList.size();i++){
			drivenCourse.add(i,pList.get(i).getCompassCourseAzimuth());
			desiredCourse.add((pList.get(i).getTimeStamp().getTime()-pList.get(0).getTimeStamp().getTime()),pDesiredCourse);
			System.out.println("Add [y,y][x]: [" + pList.get(i).getCompassCourseAzimuth() + "," + pDesiredCourse +"][" + (pList.get(i).getTimeStamp().getTime()-pList.get(0).getTimeStamp().getTime()) +"]");
		}
		dataSet.addSeries(desiredCourse);
		dataSet.addSeries(drivenCourse);
		return dataSet;
	}
	
	public static XYDataset createXyDataSet(CompassCourseList pList , double pDesiredCourse , ArrayList<PidControllerState> pPidList){
		System.out.println("Add " + pList.size() + " elements to chart");
		XYSeriesCollection dataSet = new XYSeriesCollection();
		
		XYSeries drivenCourse = new XYSeries("CompassCourse");
		XYSeries desiredCourse = new XYSeries("desired Angle("+pDesiredCourse+")");
		XYSeries pidCourseDelta = new XYSeries("PID Controller Delta Angle");
		XYSeries pidSamplingTime = new XYSeries("PID Controller sampling time");
		
		for(int i=0;i<pList.size();i++){
			drivenCourse.add((pList.get(i).getTimeStamp().getTime()-pList.get(0).getTimeStamp().getTime())/1000,pList.get(i).getCompassCourseAzimuth());
			desiredCourse.add((pList.get(i).getTimeStamp().getTime()-pList.get(0).getTimeStamp().getTime())/1000,pDesiredCourse);
			System.out.println("Add [y,y][x]: [" + pList.get(i).getCompassCourseAzimuth() + "," + pDesiredCourse +"][" + (pList.get(i).getTimeStamp().getTime()-pList.get(0).getTimeStamp().getTime())/1000 +"]");
		}
		for(int i=0;i<pPidList.size();i++){
			pidCourseDelta.add((pPidList.get(i).getTimeStamp().getTime()-pPidList.get(0).getTimeStamp().getTime())/1000,pPidList.get(i).getDeltaAngle());
			System.out.println("Add [y][x]: [" + pPidList.get(i).getDeltaAngle() + "][" + (pPidList.get(i).getTimeStamp().getTime()-pPidList.get(0).getTimeStamp().getTime())/1000 +"]");
		}
		for(int i=0;i<pPidList.size();i++){
			pidSamplingTime.add((pPidList.get(i).getTimeStamp().getTime()-pPidList.get(0).getTimeStamp().getTime())/1000,pPidList.get(i).getTa());
			System.out.println("Add [y][x]: [" + pPidList.get(i).getTa() + "][" + (pPidList.get(i).getTimeStamp().getTime()-pPidList.get(0).getTimeStamp().getTime())/1000 +"]");
		}
		dataSet.addSeries(desiredCourse);
		dataSet.addSeries(drivenCourse);
		dataSet.addSeries(pidCourseDelta);
		dataSet.addSeries(pidSamplingTime);
		return dataSet;
	}
	
	public static JFreeChart createLineChart(XYDataset pDataSet){
		JFreeChart chart = ChartFactory.createXYLineChart("Compasscourse", "time", "course", pDataSet, PlotOrientation.VERTICAL, true, true, false);
		System.out.println("Chart created");
		return chart;
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
