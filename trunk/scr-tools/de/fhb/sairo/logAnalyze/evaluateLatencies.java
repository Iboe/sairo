package de.fhb.sairo.logAnalyze;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import de.fhb.sairo.data.Latency;
import de.fhb.sairo.data.LatencyList;
import de.fhb.sairo.fileio.FileLoader;

public class evaluateLatencies {

	public static LatencyList evaluateLatencies(String pFileName) {
		LatencyList list = new LatencyList();

		BufferedReader reader = FileLoader.openLogfile(pFileName);
		String zeile = null;
		String zeileOld = null;
		String cmd = null;
		String cmdOld = null;
		Date timeStamp = null;
		Date timeStampOld = null;
		try {
			while ((zeile = reader.readLine()) != null) {
				if (zeile.contains("DEBUG") || zeile.contains("WARN") || zeile.contains("INFO")) {
					//System.out.println("Analyze: " + zeile);
					timeStamp = filter.filterTimestamp(zeile);
					int startSubString = zeile.indexOf("[");
					cmd = zeile.subSequence(startSubString, zeile.length() - 1)
							.toString();
					if (zeileOld != null && cmdOld != null
							&& timeStampOld != null) {
						long time = timeStamp.getTime()
								- timeStampOld.getTime();
						list.add(new Latency(cmdOld, cmd, time));
					}
					zeileOld = zeile;
					cmdOld = cmd;
					timeStampOld = timeStamp;
				}
			}
			reader.close();
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
