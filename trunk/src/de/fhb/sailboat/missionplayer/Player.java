package de.fhb.sailboat.missionplayer;

import java.util.Date;
import java.util.List;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.worldmodel.WorldModel;

/**
 * This class runs the prepared Data from LogConverter for running as a
 * emulation.
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 * 
 */
public class Player extends Thread {

	private WorldModel worldModel;
	private String filePath;
	private LogConverter logFileConverter;
	private int speedInterval = SPEED_INTERVAL_MIN;
	private boolean isPaused = false;

	private static int SPEED_INTERVAL_MAX = 500;
	private static int SPEED_INTERVAL_MIN = 50;
	public static final String EMULATION_FILES = "sem";

	/**
	 * creates a Emulator
	 * 
	 * @param worldModel
	 * @param filePath
	 *            - Path to Mapped-Model-File (prepared Log-Data)
	 */
	public Player(WorldModel worldModel, String filePath) {
		super();
		this.worldModel = worldModel;
		this.filePath = filePath;
		this.logFileConverter = new LogConverter();
	}

	@Override
	public void run() {

		if (filePath == null) {
			throw new RuntimeException("Keine abzuspielende Datei mitgegeben!");
		}

		// loads the prepared data for emulating
		List<Tripel<Date, String, Object>> runnableDataList = this.logFileConverter
				.openFromBinaryFile(this.filePath);

		// runs through the datasets
		for (Tripel<Date, String, Object> tripel : runnableDataList) {

			System.out.print("Original-LOG: " + tripel.getO2());

			Object actualData = tripel.getO3();

			if (actualData instanceof Compass) {
				worldModel.getCompassModel().setCompass((Compass) actualData);

			} else if (actualData instanceof GPS) {
				worldModel.getGPSModel().setPosition((GPS) actualData);

			} else if (actualData instanceof Wind) {
				worldModel.getWindModel().setWind((Wind) actualData);
			} else {
				System.out.println("Daten sind nicht integer!");
			}

			try {
				Thread.sleep(this.speedInterval);
				while (isPaused) {
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * sets the speed from SPEED_INTERVAL_MAX to SPEED_INTERVAL_MIN for incoming
	 * data from a recording
	 * 
	 * @param miliSecs
	 */
	public void setSpeedIntervall(int miliSecs) {
		if (miliSecs <= SPEED_INTERVAL_MAX && miliSecs >= SPEED_INTERVAL_MIN) {
			this.speedInterval = miliSecs;
		}
	}

	/**
	 * pause the Emulation
	 */
	public void pause() {
		this.isPaused = true;
	}

	/**
	 * continues the emulation
	 */
	public void play() {
		this.isPaused = false;
	}

}
