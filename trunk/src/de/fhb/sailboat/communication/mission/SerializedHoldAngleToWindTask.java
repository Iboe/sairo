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
 * 
 * @author Michael Kant
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
	 * @param serializedData The {@link HoldAngleToWindTask} data to deserialize.
	 */
	public SerializedHoldAngleToWindTask(byte[] serializedData) {
		super(serializedData);
		
	}

	/**
	 * Turning the given {@link HoldAngleToWindTask} into a sequence of bytes.
	 * It serializes the desired angle that comes with the given {@link HoldAngleToWindTask} and returns the byte array.
	 * @param t The {@link HoldAngleToWindTask} to serialize.
	 * 
	 * @return The byte sequence, representing the given {@link HoldAngleToWindTask}.
	 */
	@Override
	protected byte[] serializeTask(HoldAngleToWindTask t) {

		byte[] data=null;
		
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

	/**
	 * Turning the given sequence of bytes into a {@link HoldAngleToWindTask} object.
	 * @param data The data to retrieve the {@link HoldAngleToWindTask} from.
	 * @return {@link HoldAngleToWindTask} object that was produced out of the given byte sequence, or <code>null</code> if the byte sequence was invalid.
	 */
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
