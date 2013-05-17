package de.fhb.sairo.fileio;

import java.io.File;
import java.io.FileNotFoundException;

import de.fhb.sairo.data.gpsDataList;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.KmlFactory;
import de.micromata.opengis.kml.v_2_2_0.LineString;

public class saveGpsListAsKmlFile {

	/***
	 * 
	 * @param pFileName
	 * @param pList
	 * @author Tobias Koppe
	 * @version 1
	 */
	public static void save(String pFileName, gpsDataList pList){
		Kml kml = KmlFactory.createKml();
		Document doc = kml.createAndSetDocument();
		LineString lineString = new LineString();
		int listSize = pList.size();
		for(int i=0;i<listSize;i++){
			lineString.addToCoordinates(pList.get(i).getLongitude(), pList.get(i).getLatitude()).withId(String.valueOf(i));
		}
		try {
			doc.createAndAddPlacemark().setGeometry(lineString);
			kml.marshal(new File(pFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
