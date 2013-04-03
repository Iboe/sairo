package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.data.PilotDriveAngleRudderCommand;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class evaluatePilot {

	private ArrayList<PilotDriveAngleRudderCommand> commandList;
	
	public evaluatePilot(String pLogfileName, String pCsvFileName){
		this.commandList = new ArrayList<PilotDriveAngleRudderCommand>();
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pLogfileName));
			String zeile = null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.driveAngleThreadName) && zeile.contains(logTextblocks.driverSetRudderTo)){
					System.out.println("Analyze: " + zeile);
					int start = zeile.indexOf(logTextblocks.driverSetRudderTo)+ logTextblocks.driverSetRudderTo.length();
					float pos = Float.valueOf(zeile.substring(start, zeile.length()).trim());
					Date timeStamp = filter.filterTimestamp(zeile);
					this.commandList.add(new PilotDriveAngleRudderCommand(timeStamp, pos));
					if(pCsvFileName!=null){
					CSVWriter.CSVWriterWritePilotDriveAngleRudderCommands(pCsvFileName, commandList);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String zeile=null;
	}
}
