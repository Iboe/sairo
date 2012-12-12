package de.fhb.sailboat.gui.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

public class WindMarkerLine implements MapMarker{

    double lat;
    double lon;
    int windDirection;
    Color color;
	private Stroke stroke;

    public WindMarkerLine(double lat, double lon, int windDirection) {
        this(Color.BLACK, lat, lon, windDirection);
    }

    public WindMarkerLine(Color color, double lat, double lon, int windDirection) {
        super();
        this.color = color;
        this.lat = lat;
        this.lon = lon;
        this.windDirection=windDirection;
        this.stroke=new BasicStroke(2);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void paint(Graphics g, Point position) {
    	//TODO aendern Berechnung der echten winrichtung darstellung nach Richtung und Staerke
        int size_h = 4;
        int size = size_h * 2;
        Color oldColor= g.getColor();
        g.setColor(color);
        
		Stroke oldStroke = null;
		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			oldStroke = g2.getStroke();
			g2.setStroke(stroke);
		}
		
		/*************************************************************************/
//        g.drawLine(position.x - size_h, position.y - size_h, size, size);
        g.drawLine(position.x , position.y , position.x+10, position.y+10);
        g.setColor(Color.BLACK);
//        g.drawOval(position.x - size_h, position.y - size_h, size, size);
        g.setColor(oldColor);
        
        
        /******************************************************/
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setStroke(oldStroke);
		}
    }

    @Override
    public String toString() {
    	//TODO aendern
        return "MapMarker at " + lat + " " + lon;
    }

}
