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
	
	public evaluateRudderPosistions(String pLogFileName){
		rudderPositions = new ArrayList<RudderPosition>();
		BufferedReader bfReader=null;
		try {
			bfReader = new BufferedReader( new FileReader(pLogFileName));
			String zeile=null;
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(logTextblocks.settingRudderTo)) {
					String timeStamp="";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
					int startDate=0;
					for(int j=0;j<zeile.length();j++){
						if(Character.isDigit(zeile.charAt(j))){
							startDate = j;
							j=zeile.length()+1;
						}
					}
					int untilDate = zeile.indexOf("[")-1;
					timeStamp=zeile.substring(startDate, untilDate);
					timeStamp=timeStamp.replace(',',':');
					
					Date d = simpleDateFormat.parse(timeStamp);
					int startSubString=zeile.indexOf(logTextblocks.settingRudderTo);
					int countSubString=logTextblocks.settingRudderTo.length()+1;
					int from=startSubString+countSubString;
					int until = zeile.lastIndexOf(":");
					String logAngle=zeile.subSequence(from,until).toString();

					this.rudderPositions.add(new RudderPosition(Integer.parseInt(logAngle), d));
					
				}
			}
			bfReader.close();
			CSVWriter.CSVWriterWriteRudderPositions("sailboat_data.log_27_03_2013_Labor_01_rudderpositions.csv", rudderPositions);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
