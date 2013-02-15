/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.communication.serverModules.MissionReceiver;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.ReachCircleTask;

/**
 * @author Michael Kant
 *
 */
public class SerializedReachCircleTask extends SerializedTaskBase<ReachCircleTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedReachCircleTask.class);

	public SerializedReachCircleTask(ReachCircleTask task) {

		super(task);
	}

	@Override
	protected byte[] serializeTask(ReachCircleTask t) {
		
		byte[] data=null;
		int lon=0,lat=0;
		
		if(t != null){
			
			GPS gps=t.getCenter();
			if(gps != null){
				
				ByteArrayOutputStream out=new ByteArrayOutputStream();
			
				lat=(int)(gps.getLatitude()*10000);
				lon=(int)(gps.getLongitude()*10000);
				
				try {
					
					CommunicationBase.writeCompactIndex(out, lat);
					CommunicationBase.writeCompactIndex(out, lon);
					CommunicationBase.writeCompactIndex(out, t.getRadius());
					data=out.toByteArray();
				} 
				catch (IOException e) {
					
					LOG.warn("Serializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
				}
				
			}
		}
		return data;
	}

	@Override
	protected ReachCircleTask deserializeTask(byte[] data) {
		
		ReachCircleTask task=null;
		
		if(data != null){
			
			float lon=0,lat=0;
			int radius=0;
			
			ByteArrayInputStream in=new ByteArrayInputStream(data);
			try
			{	
				lat=((float)(CommunicationBase.readCompactIndex(in)))/10000;
				lon=((float)(CommunicationBase.readCompactIndex(in)))/10000;
				radius=CommunicationBase.readCompactIndex(in);
				task=new ReachCircleTask(new GPS(lat,lon), radius);
			}
			catch (IOException e) {
				
				LOG.warn("Deserializing caused an " + e.getClass().getSimpleName() +" exteption to be thrown: "+e.getMessage());
			}
		}
			
		return task;
	}


	

}
