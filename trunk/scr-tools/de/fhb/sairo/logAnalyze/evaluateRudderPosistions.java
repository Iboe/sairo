package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.LogData.LogRudderPosition;

/***
 * This class represents the module for evaluate logentries for
 * command setting rudder to in {@link AKSENLocomotion}
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateRudderPosistions {
	
	private ArrayList<LogRudderPosition> rudderPositions;
	
	/***
	 * Evaluates the logentries of aksen command setting rudder to in pLogfileName and save
	 * the result in rudderPositions
	 * @author Tobias Koppe
	 * @version 1
	 * @param pLogfileName
	 */
	public evaluateRudderPosistions(String pLogFileName){
		rudderPositions = new ArrayList<LogRudderPosition>();
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
					this.rudderPositions.add(new LogRudderPosition(Integer.parseInt(logAngle), d));			
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LogRudderPosition> getRudderPositions() {
		return rudderPositions;
	}

	public void setRudderPositions(ArrayList<LogRudderPosition> rudderPositions) {
		this.rudderPositions = rudderPositions;
	}
	
	
	
}
