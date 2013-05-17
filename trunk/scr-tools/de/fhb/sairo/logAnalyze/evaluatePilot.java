package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sairo.data.LogData.LogPilotDriveAngleRudderCommand;

/***
 * This class represents the module for evaluate the rudder commands from the pilot module
 * @author Tobias Koppe
 * @version 1
 */
public class evaluatePilot {

	private ArrayList<LogPilotDriveAngleRudderCommand> commandList;
	
	/***
	 * Evaluates the logentries of pilot rudder command in pLogfileName and save
	 * the result in commandList
	 * @author Tobias Koppe
	 * @version 1
	 * @param pLogfileName
	 */
	public evaluatePilot(String pLogfileName){
		this.commandList = new ArrayList<LogPilotDriveAngleRudderCommand>();
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pLogfileName));
			String zeile = null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.driveAngleThreadName) && zeile.contains(logTextblocks.driverSetRudderTo)){
					//System.out.println("Analyze: " + zeile);
					int start = zeile.indexOf(logTextblocks.driverSetRudderTo)+ logTextblocks.driverSetRudderTo.length();
					float pos = Float.valueOf(zeile.substring(start, zeile.length()).trim());
					Date timeStamp = filter.filterTimestamp(zeile);
					this.commandList.add(new LogPilotDriveAngleRudderCommand(timeStamp, pos));
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<LogPilotDriveAngleRudderCommand> getCommandList() {
		return commandList;
	}

	public void setCommandList(ArrayList<LogPilotDriveAngleRudderCommand> commandList) {
		this.commandList = commandList;
	}
	
	
}
