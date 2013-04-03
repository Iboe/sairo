package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.RudderPosition;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateRudderPosistions {
	
	private ArrayList<RudderPosition> rudderPositions;
	
	public evaluateRudderPosistions(String pLogFileName, String pCsvFileName){
		rudderPositions = new ArrayList<RudderPosition>();
		BufferedReader bfReader=null;
		try {
			bfReader = new BufferedReader( new FileReader(pLogFileName));
			String zeile=null;
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(logTextblocks.settingRudderTo)) {
					System.out.println("Analyze: " + zeile);
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
			if(pCsvFileName!=null){
			CSVWriter.CSVWriterWriteRudderPositions(pCsvFileName, rudderPositions);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
