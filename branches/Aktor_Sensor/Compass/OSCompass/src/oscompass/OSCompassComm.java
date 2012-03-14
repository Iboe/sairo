package oscompass;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ocss.nmea.api.NMEAParser;
import ocss.nmea.api.NMEAReader;
import ocss.nmea.parser.StringParsers;

public class OSCompassComm
{
        int baudrate = 19200;
	int dataBits = SerialPort.DATABITS_8;
	int stopBits = SerialPort.STOPBITS_1;
	int parity = SerialPort.PARITY_NONE;
        SerialReader input;
        public SerialPort serialPort;
    public OSCompassComm()
    {
        super();
    }
    
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                this.serialPort = (SerialPort) commPort;
                this.serialPort.setSerialPortParams(baudrate,dataBits,stopBits,parity);
                
                InputStream in = this.serialPort.getInputStream();
                OutputStream out = this.serialPort.getOutputStream();
                
                //(new Thread(new SerialReader(in))).start();
                //(new Thread(new SerialWriter(out))).start();
                input = new SerialReader(in);
                this.serialPort.addEventListener(input);
                this.serialPort.notifyOnDataAvailable(true);
                Thread.sleep(3000);
            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }     
    }
        void disconnect() {
            //System.out.println("Schließe Serialport");
            this.serialPort.close();
    }
    
   
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        String buffer_string;
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }

        public void serialEvent(SerialPortEvent arg0) {
            int data;

            try
            {
                int len = 0;
                while ( ( data = in.read()) > -1 )
                {
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                //System.out.print(new String(buffer,0,len));
                buffer_string = new String(buffer,0,len);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }

    }    
    
    public String getSensor(){
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
       return (input.buffer_string).trim();

   }
}