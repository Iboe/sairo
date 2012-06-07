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
import com.rapplogic.xbee.api.zigbee.ZNetRxBaseResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;
import com.rapplogic.xbee.api.zigbee.ZNetTxStatusResponse;
import com.rapplogic.xbee.examples.zigbee.ZNetSenderExample;
import com.rapplogic.xbee.util.ByteUtils;

public class WifiXbee implements IwifiXbee {

	//Class Instance
	private WifiXbee instance = null;
	
	// Instance for Data Maipulation
	private XbeeDataManipulation myManiPu = new XbeeDataManipulation();

	public String resp;

	public String getResp() {
		return resp;
	}

	private void setResp(String resp) {
		this.resp = resp;
	}

	// Comport Xbee
	private final String COM_PORT = System.getProperty(WifiXbee.class
			.getSimpleName()
			+ ".comPort");

	private char[] dataToSend = new char[] {};
	// Baudrate XBee
	private final int BAUD_RATE = Integer.parseInt(System
			.getProperty(WifiXbee.class.getSimpleName() + ".baudrate"));

	// for the Eventlistener
	private Queue<XBeeResponse> queue = new ConcurrentLinkedQueue<XBeeResponse>();

	private final String XBEE_RECEIVER_ADRESS = System
			.getProperty(WifiXbee.class.getSimpleName() + ".receiverID");

	// Response from other Xbee
	private XBeeResponse response;

	// XBee
	static final XBee xbee = new XBee();

	// Logger
	private static final Logger LOG = Logger.getLogger(WifiXbee.class);

	/*
	 * To Start the Xbee Module starts a Paketlistener vor incomming Pakets
	 */
	public void initializeXbee() {

		try {
			
			if (instance != null) {
				
			} else {
				xbee.open(COM_PORT, BAUD_RATE);
				xbee.addPacketListener(new PacketListener() {
					public void processResponse(XBeeResponse response) {
						queue.offer(response);
					}
				});
				//instance = WifiXbee.newInstance();
			}
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
				if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {

					// XBeeResponse ioSample = response;
					ZNetRxResponse ioSample = (ZNetRxResponse) response;

					int[] intArray = ioSample.getData();
					setResp(this.myManiPu.IntArryToString(intArray));
				} else {
					// not the right respons delivered (ignoring)
					LOG.debug("Xbee lieferte nicht das erwartete Signal.");

				}
			} catch (ClassCastException e) {
				// not an IO Sample
				LOG.error("Some shit happens with the response");
				e.printStackTrace();
			}
		}
	}

	/*
	 * Sends Data to other xbee
	 */
	public void sendDataXbee(String data) throws XBeeException,
			InterruptedException, IOException {

		// replace with SH + SL of your end device
		XBeeAddress64 address = new XBeeAddress64(XBEE_RECEIVER_ADRESS);

		int[] arryToSend = this.myManiPu.StringToIntArry(data);

		try {
			// Sends the array to other Xbee
			xbee.sendSynchronous(new ZNetTxRequest(address, arryToSend), 5000);

			// Shows the send Attributes
			LOG.debug(xbee.sendSynchronous(
					new ZNetTxRequest(address, arryToSend), 5000).toString());

		} catch (XBeeTimeoutException e) {

			LOG.warn("Send Timed out: " + e.toString());
		}
	}
}
