package de.fhb.sailboat.gui.missionCreator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class enables the user to load Missions from files saved to disk.
 *
 * @author Patrick Rutter
 */
public class MissionLoader {
    /***
     * Loads the given filename and returns its content as a MissionObject, if possible.
     * @param filename
     * @return MissionObject serialized in filename
     */
    static MissionObject load(String filename) {
        MissionObject myReturn = null;
        
        try {
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream o = new ObjectInputStream(file);
            myReturn = (MissionObject) o.readObject();
            o.close();
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        
        return myReturn;
    }
    
}
