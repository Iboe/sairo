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
 * An object for storing an NMEA 0183 GSV (GPS Satellites in View) packet
 * <p>
 * GSV packets are expected to start with <code>$GPGSV</code>,
 * and are expected to have <code>8, 12, 16, or 20</code> parameters.
 * <p>
 * Checksums should be tested and removed from sentences before being passed to this class.
 * Malformed packets may throw exceptions or exhibit undefined behavior.
 * 
 * @author Joey Gannon
 * @version 1.00, 08/11/06
 * @since OpenNMEA v0.1
 */
public class PacketGSV extends Packet
{
	/**
	 * Constructs a GSV packet
	 * @param s Array of string parameters
	 */
	public PacketGSV(String[] s)
	{
		super(7);
		if(!s[0].equals("$GPGSV"))
			throw new IllegalArgumentException("Cannot make GSV packet from "+s[0]);
		if((s.length==8)||(s.length==12)||(s.length==16)||(s.length==20))
		{
			for(int i=0;i<3;i++)
				data[i]=new WrapperInt(s[i+1]);
			for(int i=0;i<getNumberOfSatellites();i++)
			{
				WrapperInt[] array=new WrapperInt[4];
				for(int j=0;j<4;j++)
					array[j]=new WrapperInt(s[4*i+j+4]);
				data[i+3]=new SatelliteGSV(array);
			}
		}
		else
			throw new IllegalArgumentException("Wrong number of parameters: "+s.length);
	}
	
	/**
	 * Returns the number of sentences necessary to get data for all
	 * satellites in view
	 * @return the total number of sentences
	 */
	public int getNumberOfSentences()
	{
		return ((WrapperInt)data[0]).getValue();
	}
	
	/**
	 * Returns the number of the sentence represented by this packet,
	 * out of the total number
	 * @return the number of this sentence
	 */
	public int getSentenceNumber()
	{
		return ((WrapperInt)data[1]).getValue();
	}
	
	/**
	 * Returns the total number of satellites in view
	 * @return the total number of satellites
	 */
	public int getTotalNumberOfSatellites()
	{
		return ((WrapperInt)data[2]).getValue();
	}
	
	/**
	 * Returns the number of satellites represented by this packet
	 * @return the number of satellites represented by this packet
	 */
	public int getNumberOfSatellites()
	{
		return Math.min(((WrapperInt)data[2]).getValue()-4*(((WrapperInt)data[1]).getValue()-1),4);
	}
	
	/**
	 * Returns parameters for all of the satellites represented by
	 * this packet
	 * @return parameters for all of the satellites
	 */
	public SatelliteGSV[] getSatellites()
	{
		SatelliteGSV[] array=new SatelliteGSV[getNumberOfSatellites()];
		for(int i=0;i<array.length;i++)
			array[i]=(SatelliteGSV)data[i+3];
		return array;
	}
	
	/**
	 * Returns parameters for a specific satellite represented by
	 * this packet
	 * @param index the index of the specific satellite
	 * @return parameters for a specific satellite
	 */
	public SatelliteGSV getSatellite(int index)
	{
			return (SatelliteGSV)data[index+3];
	}
}