package de.fhb.sailboat.utils.performance;

import org.apache.log4j.Logger;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/***
 * This class represents an object, which is monitoring the used memory space and write it to the given logger
 * @author Tobias Koppe
 *
 */

public class Memory {

	private static final Logger LOG = Logger.getLogger(Memory.class);
	
	private boolean monitoring;
	
	private Sigar sigar;
	private Mem memory;
	
	public Memory(){
		sigar = new Sigar();
	}
	
	public void startMemoryMonitoring(){
		monitoring=true;
		new Thread(){
			public void run(){
				while(monitoring){
				try {
					memory = sigar.getMem();
					LOG.info("Used total memory" + memory.getUsedPercent() +" %");
				} catch (SigarException e) {
					e.printStackTrace();
				}
			}
			}
		}.start();
	}
	
	public void stopMemoryMonitoring(){
		monitoring=false;
	}
	
}
