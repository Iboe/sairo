package de.fhb.sailboat.emulator;

import java.util.Date;
import java.util.List;

import de.fhb.sailboat.data.Compass;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.data.Wind;
import de.fhb.sailboat.worldmodel.WorldModel;

/**
 * This class runs the prepared Data from LogConverter for running as a emulation.
 * 
 * @author Andy Klay <klay@fh-brandenburg.de>
 *
 */
public class EmulatorRunner extends Thread{
	
	private WorldModel worldModel;
	private String filePath;
	private LogConverter logFileConverter;
	private long speedInterval=200;
	
	private static long SPEED_INTERVAL_MAX=500;
	private static long SPEED_INTERVAL_MIN=50;
	
	
	
	
	/**
	 * 
	 * @param worldModel2
	 * @param filePath - Path to Mapped-Model-File (prepared Log-Data)
	 */
	public EmulatorRunner(WorldModel worldModel, String filePath) {
		super();
		this.worldModel = worldModel;
		this.filePath= filePath;
		this.logFileConverter= new LogConverter();
	}



	@Override
	public void run() {
		
		if(filePath==null){
			throw new RuntimeException("Keine abzuspielende Datei mitgegeben!");
		}
		
		//loads the prepared data for emulating
		List<Tripel<Date, String, Object>> runnableDataList = this.logFileConverter.openFromBinaryFile(this.filePath);
		
	
		//runs through the datasets
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
				System.out
						.println("Daten sind nicht integer!");
			}
			
			try {
				Thread.sleep(this.speedInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	public void setPause() {

		
	}



	public void setSpeedIntervall(long miliSecs){
		if(miliSecs<=SPEED_INTERVAL_MAX && miliSecs>=SPEED_INTERVAL_MIN){
			this.speedInterval=miliSecs;
		}
	}
	
}