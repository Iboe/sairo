/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oscompass;
import oscompass.OSCompassComm;
/**
 *
 * @author schmidst
 */
public class Position {
        public static void main ( String[] args )
    {
        OSCompassComm test;
        try
        {
             test = new OSCompassComm();
             test.connect("COM3");
            
            System.out.println(test.getSensor());
            System.out.println(test.getSensor());
            System.out.println(test.getSensor());
            
                test.disconnect();
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
