package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/***
 * This class evaluates the aksen board failure rate
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateAksenLog {

	private String logFileName;

	private ArrayList<String> aksenLog;

	public evaluateAksenLog(String pLogFileName) {
		this.logFileName = pLogFileName;
		aksenLog = filterAksenLog();
		aksenLog=filterSendingAksenServoCommand(aksenLog);
	}

	private ArrayList<String> filterSendingAksenServoCommand(ArrayList<String> pList) {
		System.out
				.println("Filtering Logs with sending aksenservocommand from "
						+ this.aksenLog.size() + " logs");
		ArrayList<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < pList.size(); i++) {
			String field = pList.get(i);
			if (field.contains(logTextblocks.sendAksenServoCommand)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(logTextblocks.sendingAksenServoCommandIncorrect)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(logTextblocks.sendingAksenServoCommandCorrect)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(logTextblocks.receivedValueFailure)) {
				tmpList.add(pList.get(i));
			}
		}
		
		System.out.println("Sended " + this.aksenLog.size() + " servocommands");
		for(int i=0;i<tmpList.size();i++){
			System.out.println(tmpList.get(i));
		}
		return tmpList;
	}

	/**
	 * *
	 * This method filters all logfile rows which inform about aksen board.
	 *
	 * @return the array list
	 */
	private ArrayList<String> filterAksenLog() {
		ArrayList<String> aksenLogList = new ArrayList<String>();
		String zeile = null;
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(
					this.logFileName));
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(logTextblocks.aksenlocomotionClassName)) {
					aksenLogList.add(zeile);
					// System.out.println("Found: " + zeile);
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return aksenLogList;
	}

}
