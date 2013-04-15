package de.fhb.sailboat.utils.logevaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import de.fhb.sailboat.control.pilot.SimplePIDController;
import de.fhb.sailboat.data.SimplePidControllerState;

/***
 * This class represents the module for evaluate logentries of
 * {@link SimplePIDController}
 * @author Tobias Koppe
 * @version 1
 */
public class evaluateSimplePidController {

	private ArrayList<SimplePidControllerState> simplePidControllerStateList;

	/***
	 * Evaluates the logentries of {@link SimplePIDController} for difference, output and currentValue to in pLogfileName and save
	 * the result in simplePidControllerStateList
	 * @author Tobias Koppe
	 * @version 1
	 * @param pLogfileName
	 */
	public evaluateSimplePidController(String pLogfileName) {
		simplePidControllerStateList = new ArrayList<SimplePidControllerState>();
		try {
			BufferedReader bfReader = new BufferedReader(new FileReader(
					pLogfileName));
			String zeile = null;
			while ((zeile = bfReader.readLine()) != null) {
				if (zeile.contains(logTextblocks.simplePidControllerClassName)
						&& !zeile.contains("init")) {
					//System.out.println("Analyze: " + zeile);
					Date d = filter.filterTimestamp(zeile);
					//System.out.println("Found timestamp: " + d.toString());
					int start = zeile
							.indexOf(logTextblocks.simplePidControllerControllRudderDifference)
							+ logTextblocks.simplePidControllerControllRudderDifference
									.length() + 1;
					int ende = zeile.indexOf("]", start);
					String subString = zeile.substring(start, ende);
					//System.out.println("Found difference: " + subString);
					start = zeile
							.indexOf(logTextblocks.simplePidControllerControllRudderCurrentValue)
							+ logTextblocks.simplePidControllerControllRudderCurrentValue
									.length() + 1;
					ende = zeile.indexOf("]", start);
					String currentValue = zeile.substring(start, ende);
					start = zeile
							.indexOf(logTextblocks.simplePidControllerControllRudderOutput)
							+ logTextblocks.simplePidControllerControllRudderOutput
									.length() + 1;
					ende = zeile.indexOf("]", start);
					String output = zeile.substring(start, ende);
					double rudderPos = Double.valueOf(output)
							+ Double.valueOf(currentValue);
					//System.out.println("Found rudder pos: " + rudderPos);
					this.simplePidControllerStateList
							.add(new SimplePidControllerState(Double
									.valueOf(subString), Double
									.valueOf(rudderPos), d));
				}
			}
			bfReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<SimplePidControllerState> getSimplePidControllerStateList() {
		return simplePidControllerStateList;
	}

	public void setSimplePidControllerStateList(
			ArrayList<SimplePidControllerState> simplePidControllerStateList) {
		this.simplePidControllerStateList = simplePidControllerStateList;
	}
	
}
