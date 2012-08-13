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
 * An object for storing an NMEA 0183 GGA (GPS fix data) packet
 * <p>
 * GGA packets are expected to start with <code>$GPGGA</code>,
 * and are expected to have <code>15</code> parameters.
 * <p>
 * Checksums should be tested and removed from sentences before being passed to this class.
 * Malformed packets may throw exceptions or exhibit undefined behavior.
 * 
 * @author Joey Gannon
 * @version 1.00, 08/11/06
 * @since OpenNMEA v0.1
 */
public class PacketGGA extends Packet implements ContainsPosition, ContainsTime
{
	/**
	 * Constructs a GGA packet
	 * @param s Array of string parameters
	 */
	public PacketGGA(String[] s)
	{
		super(9);
		if(!s[0].equals("$GPGGA"))
			throw new IllegalArgumentException("Cannot make GGA packet from "+s[0]);
		if(s.length==15)
		{
			data[0]=new Time(s[1]);
			data[1]=new Geocoordinate(s[2],s[3],s[4],s[5]);
			data[2]=new WrapperInt(s[6],"Fix quality");    
			data[3]=new WrapperInt(s[7],"Number of satellites");    
			data[4]=new WrapperDouble(s[8],"Horizontal dilution");    
			data[5]=new WrapperDouble(s[9],"Altitude above sea level (m)");    
			data[6]=new WrapperDouble(s[11],"Height of geoid");
			data[7]=new WrapperInt(s[13],"Time since update");
			data[8]=new WrapperInt(s[14],"DGPS station ID");
		}
		else
			throw new IllegalArgumentException("Wrong number of parameters: "+s.length);
	}

	/**
	 * Returns the time represented by this packet
	 * @return the time
	 * @see gnu.nmea.ContainsTime#getTime()
	 */
	public Time getTime()
	{
		return (Time)data[0];
	}

	/**
	 * Returns the geographic position represented by this packet
	 * @return a geographic position
	 * @see gnu.nmea.ContainsPosition#getPosition()
	 */
	public Geocoordinate getPosition()
	{
		return (Geocoordinate)data[1];
	}

	/**
	 * Returns the satellite fix quality
	 * <p>
	 * Fix quality can take the following values:<br>
	 * <code>
	 * 0 = Invalid<br>
	 * 1 = GPS fix (SPS)<br>
	 * 2 = DGPS fix<br>
	 * 3 = PPS fix<br>
	 * 4 = Real Time Kinematic<br>
	 * 5 = Float RTK<br>
	 * 6 = Estimated (dead reckoning)<br>
	 * 7 = Manual input mode<br>
	 * 8 = Simulation mode
	 * </code>
	 * @return the fix quality
	 */
	public int getFixQuality()
	{
		return ((WrapperInt)data[2]).getValue();
	}
	
	/**
	 * Returns the number of satellites being tracked
	 * @return the number of satellites
	 */
	public int getNumberOfSatellites()
	{
		return ((WrapperInt)data[3]).getValue();
	}
	
	/**
	 * Returns the horizontal dilution of precision
	 * @return the horizontal dilution
	 */
	public double getDilution()
	{
		return ((WrapperDouble)data[4]).getValue();
	}
	
	/**
	 * Returns the altitude, in meters
	 * @return the altitude
	 */
	public double getAltitude()
	{
		return ((WrapperDouble)data[5]).getValue();
	}
	
	/**
	 * Returns the height of the geoid (mean sea level) above
	 * the WGS84 ellipsoid, in meters
	 * @return the height of the geoid
	 */
	public double getGeoidHeight()
	{
		return ((WrapperDouble)data[6]).getValue();
	}
	
	/**
	 * Returns the time since the last DGPS update, in seconds
	 * @return the time since the last update
	 */
	public int getTimeSinceUpdate()
	{
		return ((WrapperInt)data[7]).getValue();
	}
	
	/**
	 * Returns the DGPS station ID number
	 * @return the station ID number
	 */
	public int getStationID()
	{
		return ((WrapperInt)data[8]).getValue();
	}
}