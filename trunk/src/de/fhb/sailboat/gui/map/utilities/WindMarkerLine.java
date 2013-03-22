package de.fhb.sailboat.gui.map.utilities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;

public class WindMarkerLine implements MapMarker{

    private double lat;
    private double lon;
    private int windDirection;
    private double windSpeed;
    private double compassDirection;
    private double realWindDirection;
   
    private Color color;
    private Stroke stroke;

    public WindMarkerLine(GPS gps, Wind wind, Compass compass) {
        this(Color.BLACK, gps.getLatitude(), gps.getLongitude(), wind.getDirection(),wind.getSpeed(), compass.getAzimuth());
    }

    public WindMarkerLine(Color color, double lat, double lon, int windDirection, double windSpeed, double compassDirection) {
        super();
        this.color = color;
        this.lat = lat;
        this.lon = lon;
        this.windDirection=windDirection;
        this.windSpeed=windSpeed;
        this.compassDirection=compassDirection;
        this.realWindDirection=this.calcRealwindDirection();
        
        this.stroke=new BasicStroke(2);
    }
    
    private double calcRealwindDirection(){
    	
    	double real=windDirection -(int)(compassDirection);

    	
    	if(real<0)
    		real=360-real;
//    	System.out.println("Grad: "+real);
    	real= (((2*Math.PI)/360)*real);
//    	System.out.println("Radmaß: "+ real);
    	
    	return real;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void paint(Graphics g, Point position) {
        
        Color oldColor= g.getColor();
        AffineTransform origTransform = ((Graphics2D) g).getTransform();   
        
        g.setColor(color);
        
		Stroke oldStroke = null;
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			oldStroke = g2.getStroke();
			g2.setStroke(stroke);
		}
		
		/*************************************************************************/
		
        AffineTransform transformer= new AffineTransform();
        transformer.translate(position.x, position.y);
        transformer.rotate(this.realWindDirection);
        ((Graphics2D) g).setTransform(transformer);

        //TODO mit Windstaerke?
//        g.drawLine(0 , 0 , 0,-(int)(windSpeed*10));
        g.drawLine(0 , 0 , 0,-20);
        g.fillOval(-3, -22, 5, 5);
        
		/*************************************************************************/
        ((Graphics2D) g).setTransform(origTransform);
        g.setColor(oldColor);
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setStroke(oldStroke);
		}
    }

    @Override
    public String toString() {
        return "WindMapMarker at " + lat + " " + lon +" "+realWindDirection;
    }

}
