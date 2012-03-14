package de.fhb.sailboat.serial.sensor;

public class nmea {



	public static String[] stringToArray(String dataString) {

		String[] dataArray = null;
		
		if(dataString != null){

			// Die "*" durch "," ersetzen, damit diese auch abgetrennt werden k�nnen
			dataString = dataString.replace('*', ',');

			// Den String an den "," trennen
			dataArray = dataString.split(",");

			return dataArray;
		}else{
			return dataArray;
		}
	}

	public static String getDevice(String dataString) {
		String[] data = null;

		data = stringToArray(dataString);

		String device = data[0];

		data = null;

		device = device.replace("$", "");

		return device;
	}
	/*
	 * Added some stuff for NMEA String-Handling
	 * 
	 * Put the String to Class via Constructor, 
	 * call verifyString to check Checksum and split into pieces (StringArray)
	 */
	
	String dataString = null;
	String[] dataArray = null;
	
	public nmea() {
		
	}
	/**
	 * Constructor with dataString
	 * @param dataString
	 */
	public nmea(String dataString) {
		this.dataString = dataString;
	}


	public String getDataString() {
		return dataString;
	}


	public void setDataString(String dataString) {
		this.dataString = dataString;
	}


	public String[] getDataArray() {
		return dataArray;
	}


	public void setDataArray(String[] dataArray) {
		this.dataArray = dataArray;
	}


	/** calculates checksum of NMEA message and compares it
     * 
     * 
     * @param msg
     * @return true if the checksum is valid, false if it is invalid
     */
    public boolean verifyString() {

        boolean valid = false;
        //calculate message length and subtract one (the carriage return)
        int msglen = this.dataString.length();
        //if the message length is greater than 4 characters
        if (msglen > 4) {
            //check if there is a checksum in this message by seeing where the asterisk is
            //(it should be at the third last position in the message)
            if (this.dataString.charAt(msglen - 3) == '*') {
                String s = this.dataString.substring(1, msglen - 3);
                String c = this.dataString.substring(msglen - 2, msglen);
            	// perform NMEA checksum calculation, Strip the $
                String chk_s = GetCheckSum(s);
                // compare checksum to encoded checksum in msg
                valid = c.equals(chk_s);
                if (valid) {
                	// split to dataArray
                	this.dataArray = s.split(",");
                }
                return (valid);
            } else {
                return false;
            }
        }
        return false;
    }


   /**
     * Trims the checksum off an NMEA message, then
     * recalculates the checksum
     * to compare it with the one passed along with the message later
     * 
     * @param msg String containing the full NMEA message (including checksum)
     * @return String containing the checksum
     */
    private static String GetCheckSum(String msg) {
        // perform NMEA checksum calculation
        int chk = 0;
        //run through each character of the message length
        //and XOR the value of chk with the byte value
        //of the character that is being evaluated
        for (int i = 0; i < msg.length(); i++) {
            chk ^= msg.charAt(i);
        }

        //convert the retreived integer to a HexString in uppercase
        String chk_s = Integer.toHexString(chk).toUpperCase();

        // checksum must be 2 characters!
        // if it falls short, add a zero before the checksum
        while (chk_s.length() < 2) {
            chk_s = "0" + chk_s;
        }

        //show the calculated checksum
        // System.out.println("    calculated checksum : " + chk_s);

        //return the calculated checksum
        return chk_s;
    }

}
