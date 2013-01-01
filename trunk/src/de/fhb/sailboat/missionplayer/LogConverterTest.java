package de.fhb.sailboat.missionplayer;

import java.util.Date;
import java.util.List;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;

/**
 * This class tests the LOG-Converter-Class
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class LogConverterTest {

	public static void main(String[] args) {

		LogConverter converter = new LogConverter();
		String readInPath = "collectedLogs/sailboat_data_12.10.17.log";
		String savePath = "TestSave.sem";

		// Konvertieren
		List<Tripel<Date, String, Object>> convertedLOG = converter
				.convertLogToBinaryFormat(readInPath);

		// Speichern
		converter.saveToBinaryFile(convertedLOG, savePath);

		// wieder oeffnen
		List<Tripel<Date, String, Object>> reopendList = converter
				.openFromBinaryFile(savePath);

		// ausprinten
		printOutLog(reopendList);

	}

	private static void printOutLog(List<Tripel<Date, String, Object>> input) {
		// Ausgabe-Test
		System.out
				.println("**********************************************************************************");

		for (Tripel<Date, String, Object> tripel : input) {

			System.out.print("Original: " + tripel.getO2());

			Object object = tripel.getO3();

			if (tripel.getO3() instanceof Compass) {
				System.out.println("Compass" + " ,azimuth: "
						+ ((Compass) object).getAzimuth() + " ,pitch: "
						+ ((Compass) object).getPitch() + " ,roll: "
						+ ((Compass) object).getRoll());

			} else if (object instanceof GPS) {
				System.out.println("GPS" + " ,latitude: "
						+ ((GPS) object).getLatitude() + " ,longitude: "
						+ ((GPS) object).getLongitude() + " ,speed: "
						+ ((GPS) object).getSpeed() + " ,satelites: "
						+ ((GPS) object).getSatelites());
			} else if (object instanceof Wind) {
				System.out.println("Wind" + " ,direction: "
						+ ((Wind) object).getDirection() + " ,speed: "
						+ ((Wind) object).getSpeed());
			} else {
				System.out
						.println("Ein Fehler bei der umwandlung des LOGs ist aufgetreten!");
			}

		}

		System.out
				.println("**********************************************************************************");
	}

}
