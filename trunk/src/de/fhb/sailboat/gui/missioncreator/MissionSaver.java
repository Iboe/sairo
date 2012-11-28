package de.fhb.sailboat.gui.missionCreator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * This class enables the user to save missions (lists of tasks) to disk as files.
 *
 * @author Patrick Rutter
 */
public class MissionSaver {
    
    /***
     * Default path for saving missions.
     */
    final public static String DEFAULT_SAVE_PATH = "C:/mission";
    
    /***
     * Saves the given MissionObject to the given Filename
     * @param filename
     * @param object 
     */
    static void save(String filename, MissionObject object) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream o = new ObjectOutputStream(file);
            o.writeObject(object);
            o.close();
        }
        catch (IOException e) { 
            System.err.println(e);
        }
    }
    
}
