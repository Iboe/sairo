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
	
	public evaluatePilot(String pFileName){
		this.commandList = new ArrayList<PilotDriveAngleRudderCommand>();
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(pFileName));
			String zeile = null;
			while((zeile = bfReader.readLine()) != null){
				if(zeile.contains(logTextblocks.driveAngleThreadName) && zeile.contains(logTextblocks.driverSetRudderTo)){
					System.out.println("Analyze: " + zeile);
					int start = zeile.indexOf(logTextblocks.driverSetRudderTo)+ logTextblocks.driverSetRudderTo.length();
					float pos = Float.valueOf(zeile.substring(start, zeile.length()).trim());
					System.out.println("Found pos: " + pos);
					Date timeStamp = filter.filterTimestamp(zeile);
					System.out.println("Find timeStamp: " + timeStamp.toString());
					this.commandList.add(new PilotDriveAngleRudderCommand(timeStamp, pos));
					System.out.println("Found Pilot.DriveAngle ruddercommand: " + this.commandList.get(this.commandList.size()-1));
					CSVWriter.CSVWriterWritePilotDriveAngleRudderCommands("pilotDriveAngleRudderCommand.csv", commandList);
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
