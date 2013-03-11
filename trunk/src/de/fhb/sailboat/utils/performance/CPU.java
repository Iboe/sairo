package de.fhb.sailboat.utils.performance;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/***
 * This class represents an object, which is monitoring the cpu performance and write it to the given logger
 * @author Tobias Koppe
 *
 */
public class CPU {

	private static final Logger LOG = Logger.getLogger(CPU.class);
	
	private Sigar sigar;
	private CPU cpu;
	
	private boolean monitoring=false;
	
	private final double warningLimit=90;
	
	public CPU(){
		sigar = new Sigar();
	}
	
	/***
	 * starts the monitoring thread
	 */
	public void startMonitoringCpu(){
		new Thread(){
			public void run(){
				startMonitoringCpuThread();
			}
		}.start();
	}
	
	//TODO Exception oder Warnung wenn CPU Auslastung zu hoch
	private void startMonitoringCpuThread(){
		CpuPerc cpuPerc;
		monitoring=true;
		while(monitoring){
			try {
				cpuPerc = sigar.getCpuPerc();
				LOG.info("Used combined (system and user) cpu performance: "+CpuPerc.format(cpuPerc.getCombined())+" %");
//				if(cpuPerc>=1){
//					
//				}
			} catch (SigarException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * stops the monitoring thread
	 */
	public void stopMonitoringCpu(){
		monitoring=false;
	}
	
}
