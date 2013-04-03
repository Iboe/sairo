package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.SimplePidControllerState;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateSimplePidController {

	private ArrayList<SimplePidControllerState> simplePidControllerStateList;
	
	public evaluateSimplePidController(String pLogfileName, String pCsvFileName){
		simplePidControllerStateList = new ArrayList<SimplePidControllerState>();
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pLogfileName));
			String zeile = null;
			while((zeile=bfReader.readLine())!=null){
				if(zeile.contains(logTextblocks.simplePidControllerClassName)){
				System.out.println("Analyze: " + zeile);
				Date d = filter.filterTimestamp(zeile);
				System.out.println("Found timestamp: " + d.toString());
				int start=zeile.indexOf(logTextblocks.simplePidControllerControllRudderDifference) + logTextblocks.simplePidControllerControllRudderDifference.length()+1;
				int ende =zeile.indexOf("]", start);
				String subString = zeile.substring(start, ende);
				System.out.println("Found difference: " + subString);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
