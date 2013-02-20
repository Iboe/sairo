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
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.ReachCircleTask;

/**
 * @author Micha
 *
 */
public class SerializedHoldAngleToWindTask extends SerializedTaskBase<HoldAngleToWindTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedHoldAngleToWindTask.class);
	
	
	public SerializedHoldAngleToWindTask(HoldAngleToWindTask task) {
		super(task);
		
	}
	
	public SerializedHoldAngleToWindTask(byte[] serializedData) {
		super(serializedData);
		
	}

	@Override
	protected byte[] serializeTask(HoldAngleToWindTask t) {

		byte[] data=null;
		int lon=0,lat=0;
		
		if(t != null){
			
			int angle=t.getAngle();
			
			ByteArrayOutputStream out=new ByteArrayOutputStream();
		
			try {
				
				CommunicationBase.writeCompactIndex(out, angle);
				data=out.toByteArray();
			} 
			catch (IOException e) {
				
				LOG.warn("Serializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
				
			
		}
		return data;
	}

	@Override
	protected HoldAngleToWindTask deserializeTask(byte[] data) {

		HoldAngleToWindTask task=null;
		int angle;
		
		if(data != null){
						
			ByteArrayInputStream in=new ByteArrayInputStream(data);
			try
			{	
				angle=CommunicationBase.readCompactIndex(in);
				task=new HoldAngleToWindTask(angle);
			}
			catch (IOException e) {
				
				LOG.warn("Deserializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
		}
			
		return task;
	}


}
