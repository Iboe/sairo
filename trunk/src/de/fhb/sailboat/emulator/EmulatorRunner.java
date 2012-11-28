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
public class EmulatorRunner implements Runnable{
	
	private WorldModel worldModel;
	private String filePath;
	private LogConverter logFileConverter;
	
	
	
	
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
		
		List<Tripel<Date, String, Object>> runnableDataList = this.logFileConverter.openFromBinaryFile(this.filePath);
		
		
		// 2. setzt das Model von den Sensor Klassen im Weltmodell
//		if()
//		
//		
//		
		
		for (Tripel<Date, String, Object> tripel : runnableDataList) {

			System.out.print("Original: " + tripel.getO2());

			Object actualData = tripel.getO3();

			if (actualData instanceof Compass) {
//				System.out.println("Compass" + " ,azimuth: "
//						+ ((Compass) object).getAzimuth() + " ,pitch: "
//						+ ((Compass) object).getPitch() + " ,roll: "
//						+ ((Compass) object).getRoll());
				worldModel.getCompassModel().setCompass((Compass) actualData);

			} else if (actualData instanceof GPS) {
//				System.out.println("GPS" + " ,latitude: "
//						+ ((GPS) object).getLatitude() + " ,longitude: "
//						+ ((GPS) object).getLongitude() + " ,speed: "
//						+ ((GPS) object).getSpeed() + " ,satelites: "
//						+ ((GPS) object).getSatelites());
				worldModel.getGPSModel().setPosition((GPS) actualData);
				
			} else if (actualData instanceof Wind) {
//				System.out.println("Wind" + " ,direction: "
//						+ ((Wind) object).getDirection() + " ,speed: "
//						+ ((Wind) object).getSpeed());
				worldModel.getWindModel().setWind((Wind) actualData);
			} else {
				System.out
						.println("Daten sind nicht integer!");
			}
			
			//do.wait(secs)
		}
	}

	
	
	public WorldModel getWorldModel() {
		return worldModel;
	}



	private Wind prepareWind(){
		return null;
		
	}
	
	
	private GPS prepareGPS(){
		return null;
		
	}
	
	private Compass prepareCompass(){
		return null;
		
	}
	
	
}
