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
 * An object for storing an NMEA 0183 GSA (GPS Satellites Active) packet
 * <p>
 * GSA packets are expected to start with <code>$GPGSA</code>,
 * and are expected to have <code>18</code> parameters.
 * <p>
 * Checksums should be tested and removed from sentences before being passed to this class.
 * Malformed packets may throw exceptions or exhibit undefined behavior.
 * 
 * @author Joey Gannon
 * @version 1.00, 08/11/06
 * @since OpenNMEA v0.1
 */
public class PacketGSA extends Packet
{
	/**
	 * Constructs a GSA packet
	 * @param s Array of string parameters
	 */
	public PacketGSA(String[] s)
	{
		super(6);
		if(!s[0].equals("$GPGSA"))
			throw new IllegalArgumentException("Cannot make GSA packet from "+s[0]);
		if(s.length==18)
		{
			if(s[1].length()!=1)
				throw new IllegalArgumentException();
			data[0]=new WrapperBoolean(s[1].equals("A"),"Auto selection");
			data[1]=new WrapperInt(s[2]);
			WrapperInt[] array=new WrapperInt[12];
			for(int i=0;i<12;i++)
				array[i]=new WrapperInt(s[i+3]);
			data[2]=new WrapperArray(array,"PRNs of satellites");
			for(int i=3;i<6;i++)
				data[i]=new WrapperDouble(s[i+12],"PHV".charAt(i-3)+"DOP");
		}
		else
			throw new IllegalArgumentException("Wrong number of parameters: "+s.length);
	}
	
	/**
	 * Determines if 2D/3D selection is automatic or manual
	 * @return true if automatic, false if manual
	 */
	public boolean isAutomatic()
	{
		return ((WrapperBoolean)data[0]).getValue();
	}
	
	/**
	 * Returns the satellite fix type
	 * <p>
	 * Fix type can take the following values:<br>
	 * <code>
	 * 1 = No fix<br>
	 * 2 = 2D fix<br>
	 * 3 = 3D fix
	 * </code>
	 * @return the fix type
	 */
	public int getFixType()
	{
		return ((WrapperInt)data[1]).getValue();
	}
	
	/**
	 * Returns the PRNs for satellites used for fix
	 * <p>
	 * Note that empty PRN positions are set to <code>-1</code>.
	 * @return an array of PRNs
	 */
	public int[] getPRN()
	{
		int[] array=new int[12];
		for(int i=0;i<12;i++)
			array[i]=((WrapperArray)data[2]).getValue()[i].isValid()?((WrapperArray)data[2]).getValue()[i].intValue():-1;
		return array;
	}
	
	/**
	 * Returns the position dilution of precision (smaller is better)
	 * @return the position dilution of precision
	 */
	public double getPDOP()
	{
		return ((WrapperDouble)data[3]).getValue();
	}
	
	/**
	 * Returns the horizontal dilution of precision (smaller is better)
	 * @return the horizontal dilution of precision
	 */
	public double getHDOP()
	{
		return ((WrapperDouble)data[4]).getValue();
	}
	
	/**
	 * Returns the vertical dilution of precision (smaller is better)
	 * @return the vertical dilution of precision
	 */
	public double getVDOP()
	{
		return ((WrapperDouble)data[5]).getValue();
	}
}