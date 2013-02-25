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
import de.fhb.sailboat.mission.HoldAngleToWindTask;
import de.fhb.sailboat.mission.Task;

/**
 * Implements the concrete de-/serialization protocol for {@link Task}s of the type {@link HoldAngleToWindTask}.
 * @see de.fhb.sailboat.mission.HoldAngleToWindTask
 * @author Michael Kant
 *
 */
public class SerializedHoldAngleToWindTask extends SerializedTaskBase<HoldAngleToWindTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedHoldAngleToWindTask.class);
	
	/**
	 * Initialization constructor.
	 * 
	 * @param task The {@link HoldAngleToWindTask} to serialize.
	 */
	public SerializedHoldAngleToWindTask(HoldAngleToWindTask task) {
		super(task);
		
	}
	
	/**
	 * Initialization constructor.
	 * 
	 * @param task The {@link HoldAngleToWindTask} data to deserialize.
	 */
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
