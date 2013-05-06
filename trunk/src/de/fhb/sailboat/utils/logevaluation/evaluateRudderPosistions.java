package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.RudderPosition;

/***
 * This class represents the module for evaluate logentries for
 * command setting rudder to in {@link AKSENLocomotion}
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateRudderPosistions {
	
	private ArrayList<RudderPosition> rudderPositions;
	
	/**
	 * *
	 * Evaluates the logentries of aksen command setting rudder to in pLogfileName and save
	 * the result in rudderPositions.
	 *
	 * @param pLogFileName the log file name
	 */
	public evaluateRudderPosistions(String pLogFileName){
		rudderPositions = new ArrayList<RudderPosition>();
		BufferedReader bfReader=null;
		try {
			bfReader = new BufferedReader( new FileReader(pLogFileName));
			String zeile=null;
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(logTextblocks.settingRudderTo)) {
					//System.out.println("Analyze: " + zeile);
					Date d = filter.filterTimestamp(zeile);
					int startSubString=zeile.indexOf(logTextblocks.settingRudderTo);
					int countSubString=logTextblocks.settingRudderTo.length()+1;
					int from=startSubString+countSubString;
					int until = zeile.lastIndexOf(":");
					String logAngle=zeile.subSequence(from,until).toString();
					this.rudderPositions.add(new RudderPosition(Integer.parseInt(logAngle), d));			
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<RudderPosition> getRudderPositions() {
		return rudderPositions;
	}

	public void setRudderPositions(ArrayList<RudderPosition> rudderPositions) {
		this.rudderPositions = rudderPositions;
	}
	
	
	
}
