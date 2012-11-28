package de.fhb.sailboat.emulator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;

/**
 * This class converts the LOG-Files from Text-Format to a Object-format
 * for emulation of past use of the System
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class LogConverter {

	/**
	 * converts a LOG-File with Text to a Object Format for Emulation
	 * 
	 * @param path
	 *            - Filepath
	 * @return List<Tripel<Date, String, Object>>
	 */
	public List<Tripel<Date, String, Object>> convertLogToBinaryFormat(
			String path) {

		String logFileInput = loadLogFile(path);
		List<Tupel<String, Date>> eventList = stripDownToLines(logFileInput);
		List<Tripel<Date, String, Object>> finalFormatList = convertLinesToObjects(eventList);

		return finalFormatList;
	}

	/**
	 * saves the finalList in a binary File
	 * 
	 * @param finalFormatList
	 * @param path
	 */
	public void saveToBinaryFile(List<Tripel<Date, String, Object>> finalList,
			String path) {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					fileOutputStream);
			objectOutputStream.writeObject(finalList);
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * reads in a Emulatorfile to List<Tripel<Date, String, Object>>
	 * 
	 * @param path
	 * @return List<Tripel<Date, String, Object>>
	 */
	public List<Tripel<Date, String, Object>> openFromBinaryFile(String path) {

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path);

			ObjectInputStream objectInputStream = new ObjectInputStream(
					fileInputStream);
			@SuppressWarnings("unchecked")
			List<Tripel<Date, String, Object>> input = ((List<Tripel<Date, String, Object>>) objectInputStream
					.readObject());

			objectInputStream.close();
			return input;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * converts a Line to Date List to a List with mapped Model-Objects
	 * 
	 * @param eventList
	 * @return
	 */
	private List<Tripel<Date, String, Object>> convertLinesToObjects(
			List<Tupel<String, Date>> eventList) {

		List<Tripel<Date, String, Object>> modelTimeList = new ArrayList<Tripel<Date, String, Object>>();

		for (Tupel<String, Date> dataSet : eventList) {
			String line = dataSet.getO1();
			Date date = dataSet.getO2();

			if (line.contains("GPS")) {
				GPS gps = DataMapper.createGPS(line);
				modelTimeList.add(new Tripel<Date, String, Object>(date, line,
						gps));
			} else if (line.contains("Compass")) {
				Compass compass = DataMapper.createCompass(line);
				modelTimeList.add(new Tripel<Date, String, Object>(date, line,
						compass));
			} else if (line.contains("Wind")) {
				Wind wind = DataMapper.createWind(line);
				modelTimeList.add(new Tripel<Date, String, Object>(date, line,
						wind));
			} else {
				modelTimeList.add(new Tripel<Date, String, Object>(date, line,
						null));
			}
		}

		return modelTimeList;
	}

	/**
	 * strips the LOG down into tupel of one line and timestamp
	 * 
	 * @param logFileInput
	 * @return List<Tupel<String, Date>>
	 */
	private List<Tupel<String, Date>> stripDownToLines(String logFileInput) {

		List<Tupel<String, Date>> eventList = new ArrayList<Tupel<String, Date>>();
		StringTokenizer linehacker = new StringTokenizer(logFileInput, "\n");

		while (linehacker.hasMoreElements()) {
			String nextLine = (String) linehacker.nextElement();
			Date lineDate = new Date();

			try {
				lineDate = paseDateFromLine(nextLine);

				eventList.add(new Tupel<String, Date>(nextLine, lineDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		return eventList;
	}

	/**
	 * extracts the time-stamp from a line
	 * 
	 * @param line
	 * @return Date
	 * @throws ParseException
	 */
	private Date paseDateFromLine(String line) throws ParseException {

		StringTokenizer spacehacker = new StringTokenizer(line);
		SimpleDateFormat dateProducer = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,S");

		spacehacker.nextElement();
		String date = (String) spacehacker.nextElement();
		String time = (String) spacehacker.nextElement();

		return dateProducer.parse(date + " " + time);
	}

	/**
	 * loads the Log from a File into a String
	 * 
	 * @param datname
	 * @return
	 */
	private String loadLogFile(String datname) {
		String content = "";
		try {
			FileReader inputStream = new FileReader(datname);
			StringBuffer buffer = new StringBuffer();
			int read = 0;

			while (read != -1) {
				read = inputStream.read();
				if (read != -1) {
					buffer.append((char) read);
				}
			}

			inputStream.close();
			content = buffer.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

}