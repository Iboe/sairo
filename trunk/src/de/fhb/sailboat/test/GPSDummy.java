/**
 * 
 */
package de.fhb.sailboat.test;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.worldmodel.WorldModelImpl;

/**
 * @author Micha
 *
 */
public class GPSDummy extends Thread{ 

	public GPSDummy()
	{
		WorldModelImpl.getInstance().getGPSModel().setPosition(new GPS(52.246555,12.323096));
	}
}
