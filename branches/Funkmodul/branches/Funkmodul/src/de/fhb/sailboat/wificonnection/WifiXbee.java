package de.fhb.sailboat.wificonnection;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.AtCommandResponse;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.XBeeTimeoutException;
import com.rapplogic.xbee.api.wpan.RxResponseIoSample;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.util.ByteUtils;

public class WifiXbee implements IwifiXbee {

	// Comport Xbee
	private final String COM_PORT = System.getProperty(WifiXbee.class
			.getSimpleName() + ".comPort");

	// Baudrate XBee
	private final int BAUD_RATE = Integer.parseInt(System
			.getProperty(WifiXbee.class.getSimpleName() + ".baudrate"));

	// for the Eventlistener
	private Queue<XBeeResponse> queue = new ConcurrentLinkedQueue<XBeeResponse>();

	private final String XBEE_RECEIVER_ADRESS = System
			.getProperty(WifiXbee.class.getSimpleName() + ".receiverID");

	//Response from other Xbee
	private XBeeResponse response;

	// XBee
	static final XBee xbee = new XBee();

	// Logger
	private static final Logger LOG = Logger.getLogger(WifiXbee.class);

	/*
	 * To Start the Xbee Module
	 */
	public void initializeXbee() {

		try {
			xbee.open(COM_PORT, BAUD_RATE);
			xbee.addPacketListener(new PacketListener() {
				public void processResponse(XBeeResponse response) {
					queue.offer(response);
				}
			});
		} catch (Exception e) {
			LOG.error("XBee failed to initialize: " + e);
			e.printStackTrace();
		}
	}

	/*
	 * Reads Data from xbee
	 */
	public void read() {
		try {
			receiveDataXbee();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Receives data from another Xbee
	 */
	private void receiveDataXbee() throws XBeeException, InterruptedException {

		while ((response = queue.poll()) != null) { // we got something!

			try {
				RxResponseIoSample ioSample = (RxResponseIoSample) response;

				System.out.println("We received a sample from "
						+ ioSample.getSourceAddress());

				if (ioSample.containsAnalog()) {
					System.out.println("10-bit temp reading (pin 19) is "
							+ ioSample.getSamples()[0].getAnalog1());
				}
			} catch (ClassCastException e) {
				// not an IO Sample
			}
		}
	}

	/*
	 * Sends Data to other xbee module
	 */
	public void sendDataXbee(String data) throws XBeeException,
			InterruptedException, IOException {

		// replace with SH + SL of your end device
		XBeeAddress64 address = new XBeeAddress64(XBEE_RECEIVER_ADRESS);
		
		try {
			// Develop test Case
			xbee.sendSynchronous(new ZNetTxRequest(address, new int[] { 'H',
					'i' }), 5000);

		} catch (XBeeTimeoutException e) {

			LOG.warn("Send Timed out: " + e.toString());
		}
		XBeeResponse response = xbee.getResponse();

		if (response.getApiId() == ApiId.AT_RESPONSE) {
			// since this API ID is AT_RESPONSE, we know to cast to
			// AtCommandResponse
			AtCommandResponse atResponse = (AtCommandResponse) response;

			if (atResponse.isOk()) {
				// command was successful
				System.out.println("Command returned "
						+ ByteUtils.toBase16(atResponse.getValue()));
			} else {

			}
		}

	}

}
