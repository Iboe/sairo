package de.fhb.sairo.logAnalyze;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhb.sairo.both.LogTextblocks;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class filter {

	public static String filterArgumentOfLogEntry(String pZeile, String pArgument){
		String returnValue = null;
		
		return returnValue;
	}
	
	public static String filterTaskArguments(String pZeile){
		int tmp = pZeile.indexOf(LogTextblocks.taskExecutionSignal) + LogTextblocks.taskExecutionSignal.length();
		return pZeile.substring(pZeile.indexOf("[", tmp), pZeile.indexOf("]", tmp)+1);
	}
	
	/***
	 * This method filters the timestamp from given string
	 * @author Tobias Koppe
	 * @version 1
	 * @param p
	 * @return
	 */
	public static Date filterTimestamp(String p){
		String timeStamp=null;
		Date d=null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		int startDate=0;
		for(int j=0;j<p.length();j++){
			if(Character.isDigit(p.charAt(j))){
				startDate = j;
				j=p.length()+1;
			}
		}
		int untilDate = p.indexOf("[")-1;
		timeStamp=p.substring(startDate, untilDate);
		timeStamp=timeStamp.replace(',',':');
		
		try {
			d = simpleDateFormat.parse(timeStamp);
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
		
	}
}
