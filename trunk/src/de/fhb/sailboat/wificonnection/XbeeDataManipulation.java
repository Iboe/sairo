package de.fhb.sailboat.wificonnection;

@Deprecated
class XbeeDataManipulation {

	/*
	 * Parses the String to int[] that it could be send via Xbee
	 */
	int[] StringToIntArry(String data) {
		char[] dataSend = data.toCharArray();
		int[] sendAry = new int[dataSend.length];
		int i = 0;
		// foreach Construkt
		for (char ch : dataSend) {
			sendAry[i] = (int) ch;
			i++;
		}
		return sendAry;
	}

	/*
	 * Parses the String in to int[] that it could be send via Xbee
	 */
	String IntArryToString(int[] data) {
		String response = null;
		// foreach Construkt
		for (int resp : data) {
			response += (char) resp;
		}
		return response;
	}

}
