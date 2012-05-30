package de.fhb.sailboat.wificonnection;

import java.io.InputStream;

import javax.xml.bind.ParseConversionEvent;

import com.rapplogic.xbee.api.AtCommand;
import com.rapplogic.xbee.api.RemoteAtRequest;
import com.rapplogic.xbee.api.RemoteAtResponse;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;

public class WifiXbee implements IwifiXbee {

	// private final int COMPORT =
	// Integer.parseInt(System.getProperty("Xbee.comPort"));
	private final String BAUDRATE = System.getProperty("Xbee.baudrate");

	public void OpenXbee() throws XBeeException, InterruptedException {
		XBee xbee = new XBee();
		xbee.open("COM5", 9600);

		// replace with SH + SL of your end device
		XBeeAddress64 address = new XBeeAddress64(0, 0x13, 0xa2, 0, 0x40, 0x33,
				0xf1, 0xfb);

		// pin 20 corresponds to D0, and 5 activates the output (Digital output
		// high)
		RemoteAtRequest request = new RemoteAtRequest(address, "D0",
				new int[] { 5 });

		// turn on LED
		RemoteAtResponse response = (RemoteAtResponse) xbee.sendSynchronous(
				request, 10000);

		if (response.isOk()) {
			// done
		}

		// pause for 2 seconds
		Thread.sleep(2000);

		// command to turn off pin 20
		request = new RemoteAtRequest(address, "D0", new int[] { 4 });

		// send the command
		response = (RemoteAtResponse) xbee.sendSynchronous(request, 10000);

		while (true) {
			XBeeResponse response2 = xbee.getResponse();
			System.out.println(response2);
			ZNetRxResponse bla = new ZNetRxResponse();

			// success
			System.out.println("Listen...");
			Thread.sleep(5000);
		}

	}

	public void SendData(InputStream DataToSend) {

	}

	public void ReceiveData() {

	}

}
