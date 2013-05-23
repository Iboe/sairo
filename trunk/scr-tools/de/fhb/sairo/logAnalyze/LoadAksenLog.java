package de.fhb.sairo.logAnalyze;

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
public class LoadAksenLog {

	private String logFileName;

	private ArrayList<String> aksenLog;

	public LoadAksenLog(String pLogFileName) {
		this.logFileName = pLogFileName;
		aksenLog=filterSendingAksenServoCommand(aksenLog);
	}

	private ArrayList<String> filterSendingAksenServoCommand(ArrayList<String> pList) {
		System.out
				.println("Filtering Logs with sending aksenservocommand from "
						+ this.aksenLog.size() + " logs");
		ArrayList<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < pList.size(); i++) {
			String field = pList.get(i);
			if (field.contains(LogTextblocks.sendAksenServoCommand)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(LogTextblocks.sendingAksenServoCommandIncorrect)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(LogTextblocks.sendingAksenServoCommandCorrect)) {
				tmpList.add(pList.get(i));
			}
			if (field.contains(LogTextblocks.receivedValueFailure)) {
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
	public static ArrayList<String> filterAksenLog(String pFileName) {
		ArrayList<String> aksenLogList = new ArrayList<String>();
		String zeile = null;
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(
					pFileName));
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(LogTextblocks.aksenlocomotionClassName)) {
					aksenLogList.add(zeile);
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
