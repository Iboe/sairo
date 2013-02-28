/**
 * 
 */
package de.fhb.sailboat.communication.mission;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.communication.CommunicationBase;
import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.ReachPolygonTask;
import de.fhb.sailboat.mission.Task;

/**
 * Implements the concrete de-/serialization protocol for {@link Task}s of the type {@link ReachPolygonTask}.
 * @see de.fhb.sailboat.mission.ReachPolygonTask
 * @author Michael Kant
 *
 */
public class SerializedReachPolygonTask extends SerializedTaskBase<ReachPolygonTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SerializedReachPolygonTask.class);

	/**
	 * Initialization constructor.
	 * 
	 * @param task The {@link ReachPolygonTask} to serialize.
	 */
	public SerializedReachPolygonTask(byte[] serializedData) {
		super(serializedData);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Initialization constructor.
	 * 
	 * @param data The {@link ReachPolygonTask} data to deserialize.
	 */
	public SerializedReachPolygonTask(ReachPolygonTask task) {
		super(task);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Turning the given {@link ReachPolygonTask} into a sequence of bytes.
	 * This is done by serializing each of the {@link GPS} coordinates of the given {@link ReachPolygonTask} which define the polygon. 
	 * @param t The {@link ReachPolygonTask} to serialize.
	 * 
	 * @return The byte sequence, representing the given {@link ReachPolygonTask}.
	 */
	@Override
	protected byte[] serializeTask(ReachPolygonTask t) {

		byte[] data=null;
		List<GPS> polygon;
		int lon=0,lat=0;
		
		if(t != null){
			
			polygon=t.getPoints();
			
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			
			try {
				
				CommunicationBase.writeCompactIndex(out, polygon.size());
				
				for(GPS gps : polygon){
					
					if(gps != null){
						
						lat=(int)(gps.getLatitude()*10000);
						lon=(int)(gps.getLongitude()*10000);
							
						CommunicationBase.writeCompactIndex(out, lat);
						CommunicationBase.writeCompactIndex(out, lon);
					}
				}
				data=out.toByteArray();
			} 
			catch (IOException e) {
				
				LOG.warn("Serializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
		}
		return data;
	}

	/**
	 * Turning the given sequence of bytes into a {@link ReachPolygonTask} object.
	 * @param data The data to retrieve the {@link ReachPolygonTask} from.
	 * @return {@link ReachPolygonTask} object that was produced out of the given byte sequence, or <code>null</code> if the byte sequence was invalid.
	 */
	@Override
	protected ReachPolygonTask deserializeTask(byte[] data) {

		ReachPolygonTask task=null;
		
		if(data != null){
			
			float lon=0,lat=0;
			int size=0;
			List<GPS> polygon=new LinkedList<GPS>(); 
			
			ByteArrayInputStream in=new ByteArrayInputStream(data);
			try
			{	
				size=CommunicationBase.readCompactIndex(in);
				
				if(size > 0){
					
					for(int i=0;i<size;i++){
						
						lat=((float)(CommunicationBase.readCompactIndex(in)))/10000;
						lon=((float)(CommunicationBase.readCompactIndex(in)))/10000;
						polygon.add(new GPS(lat,lon));
					}
				}
				task=new ReachPolygonTask(polygon);
			}
			catch (IOException e) {
				
				LOG.warn("Deserializing caused an " + e.getClass().getSimpleName() +" exception to be thrown: "+e.getMessage());
			}
		}
		return task;
	}
}
