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
import de.fhb.sailboat.mission.CompassCourseTask;
import de.fhb.sailboat.mission.Task;

/**
 * Implements the concrete de-/serialization protocol for {@link Task}s of the type {@link CompassCourseTask}.
 * @see de.fhb.sailboat.mission.CompassCourseTask
 * 
 * @author Michael Kant
 */
public class SerializedCompassCourseTask extends SerializedTaskBase<CompassCourseTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedCompassCourseTask.class);
	
	/**
	 * Initialization constructor.
	 * 
	 * @param serializedData The {@link CompassCourseTask} data to deserialize.
	 */
	public SerializedCompassCourseTask(byte[] serializedData) {
		super(serializedData);
	}

	/**
	 * Initialization constructor.
	 * 
	 * @param task The {@link CompassCourseTask} to serialize.
	 */
	public SerializedCompassCourseTask(CompassCourseTask task) {
		super(task);
	}

	/**
	 * Turning the given {@link CompassCourseTask} into a sequence of bytes.
	 * It serializes the desired angle that comes with the given {@link CompassCourseTask} and returns the byte array.
	 * @param t The {@link CompassCourseTask} to serialize.
	 * 
	 * @return The byte sequence, representing the given {@link CompassCourseTask}.
	 */
	@Override
	protected byte[] serializeTask(CompassCourseTask t) {

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
	 * Turning the given sequence of bytes into a {@link CompassCourseTask} object.
	 * @param data The data to retrieve the {@link CompassCourseTask} from.
	 * @return {@link CompassCourseTask} object that was produced out of the given byte sequence, or <code>null</code> if the byte sequence was invalid.
	 */
	@Override
	protected CompassCourseTask deserializeTask(byte[] data) {

		CompassCourseTask task=null;
		int angle;
		
		if(data != null){
						
			ByteArrayInputStream in=new ByteArrayInputStream(data);
			try
			{	
				angle=CommunicationBase.readCompactIndex(in);
				task=new CompassCourseTask(angle);
			}
			catch (IOException e) {
				
				LOG.warn("Deserializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
		}
			
		return task;
	}
}
