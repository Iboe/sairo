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
import de.fhb.sailboat.mission.ReachCircleTask;
import de.fhb.sailboat.mission.Task;

/**
 * Implements the concrete de-/serialization protocol for {@link Task}s of the type {@link ReachCircleTask}.
 * @see de.fhb.sailboat.mission.ReachCircleTask
 * @author Michael Kant
 *
 */
public class SerializedReachCircleTask extends SerializedTaskBase<ReachCircleTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedReachCircleTask.class);

	/**
	 * Initialization constructor.
	 * 
	 * @param task The {@link ReachCircleTask} to serialize.
	 */
	public SerializedReachCircleTask(ReachCircleTask task) {

		super(task);
	}
	
	/**
	 * Initialization constructor.
	 * 
	 * @param data The {@link ReachCircleTask} data to deserialize.
	 */
	public SerializedReachCircleTask(byte[] data) {

		super(data);
	}

	/**
	 * Turning the given {@link ReachCircleTask} into a sequence of bytes.
	 * First it serializes the desired {@link GPS} position that comes with the given {@link ReachCircleTask}.<br>
	 * Then it serializes the given radius and returns the byte array.
	 * @param t The {@link ReachCircleTask} to serialize.
	 * 
	 * @return The byte sequence, representing the given {@link ReachCircleTask}.
	 */
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

	/**
	 * Turning the given sequence of bytes into a {@link ReachCircleTask} object.
	 * @param data The data to retrieve the {@link ReachCircleTask} from.
	 * @return {@link ReachCircleTask} object that was produced out of the given byte sequence, or <code>null</code> if the byte sequence was invalid.
	 */
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
				task=new ReachCircleTask(new GPS(lat,lon,System.currentTimeMillis()), radius);
			}
			catch (IOException e) {
				
				LOG.warn("Deserializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
		}
			
		return task;
	}
}
