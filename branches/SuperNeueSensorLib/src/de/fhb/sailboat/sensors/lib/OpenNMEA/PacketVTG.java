/*
 * OpenNMEA - A Java library for parsing NMEA 0183 data sentences
 * Copyright (C)2006 Joey Gannon
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package de.fhb.sailboat.sensors.lib.OpenNMEA;

/**
 * An object for storing an NMEA 0183 VTG (Velocity and Track made Good) packet
 * <p>
 * VTG packets are expected to start with <code>$GPVTG</code> or <code>$LCVTG</code>,
 * and are expected to have <code>7</code> parameters.
 * <p>
 * Checksums should be tested and removed from sentences before being passed to this class.
 * Malformed packets may throw exceptions or exhibit undefined behavior.
 * 
 * @author Joey Gannon
 * @version 1.00, 08/11/06
 * @since OpenNMEA v0.1
 */
public class PacketVTG extends Packet implements ContainsSpeed
{
	/**
	 * Constructs a VTG packet
	 * @param s Array of string parameters
	 */
	public PacketVTG(String[] s)
	{
		super(4);
		if(!s[0].equals("$GPVTG")||!s[0].equals("$LCVTG"))
			throw new IllegalArgumentException("Cannot make VTG packet from "+s[0]);
		if(s.length==9)
		{
			for(int i=0;i<4;i++)
				data[i]=new WrapperDouble(s[2*i+1]);
		}
		else
			throw new IllegalArgumentException("Wrong number of parameters: "+s.length);
	}
	
	/**
	 * Returns the true track made good, in degrees
	 * @return the true track
	 */
	public double getTrueTrack()
	{
		return ((WrapperDouble)data[0]).getValue();
	}
	
	/**
	 * Returns the magnetic track made good, in degrees
	 * @return the magnetic track
	 */
	public double getMagneticTrack()
	{
		return ((WrapperDouble)data[1]).getValue();
	}
	
	/**
	 * Returns the ground speed, in knots
	 * @return the ground speed
	 * @see gnu.nmea.ContainsSpeed#getGroundSpeed()
	 */
	public double getGroundSpeed()
	{
		return ((WrapperDouble)data[2]).getValue();
	}
	
	/**
	 * Returns the ground speed, in kilometers per hour
	 * @return the ground speed
	 */
	public double getGroundSpeedKPH()
	{
		return ((WrapperDouble)data[3]).getValue();
	}
}