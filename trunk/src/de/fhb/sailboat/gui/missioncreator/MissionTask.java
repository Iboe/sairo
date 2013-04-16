package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.mission.*;
import java.util.ArrayList;

/**
 *  This class serves but one purpose: Holding a Task in a serializable kind of form.
 *  It is mainly used for storing said Task in order to save and load it. How the later is
 *  accomplished does not concern this clas, though it is mainly intended for use with standard
 *  serialization.
 * @author Patrick Rutter
 */
public class MissionTask implements java.io.Serializable {
    
	private static final long serialVersionUID = 1L;

	/**
     * This enmueration holds the simple classname of every Task supported by MissionTask for handling as given via calling getClass().getSimpleClassName.toUpperCase() .
     * Add any newly added Tasks names here to register them as accepted after handling code for them was written in buildTask() and ???. Be wary of spelling errors.
     */
    private static enum supported {
	  REACHCIRCLETASK, REACHPOLYGONTASK, COMPASSCOURSETASK, HOLDANGLETOWINDTASK, STOPTASK, PRIMITIVECOMMANDTASK
    }
    
    /**
     * The name of the Task, as in ClassName - This is needed to identify the parameterrs neeeded and how to create the original Task from raw data.
     */
    private String name = null;
    
    /**
     * This list will hold all parameters of the Task in a suitable form.
     */
    private ArrayList<Object> parameters; //
    
    /**
     * Constructs and initializes the MissionTask.
     * @param task
     */
    public MissionTask(Task task) {
        if (task != null) {
            storeTask(task);
        }
    }
    
    /**
     * Use this to add a Tasks parameters to the MissionTask. Exact handling is depending on the Task to be stored.
     * As a general rule parameters are added in the order of their declaration in the Tasks Constructor.
     * @param param  The parameter to be added
     */
    private void addParameter(Object param) {
        this.parameters.add(param);
    }

    /**
     * Stores a given Task by decomposing it into the primitve data types needed to construct it.
     * @param task
     */
    private void storeTask(Task task) {
        this.name = task.getClass().getSimpleName();
        parameters = new ArrayList<Object>();
        
        switch (supported.valueOf(name.toUpperCase())) {
            case REACHCIRCLETASK: {
                /*
                 * Store a ReachCircleTask. Its GPS value is stored in parameters at
                 * index 0 (latitude) and 1 (longitude) as Double each, its radius as Integer
                 * at index 2.
                 */
                //System.out.println("Storing CircleTask...");
                parameters.add((Double)((ReachCircleTask)task).getCenter().getLatitude());
                //System.out.println("Stored " + ((Double)((ReachCircleTask)task).getCenter().getLatitude()).toString());
                parameters.add((Double)((ReachCircleTask)task).getCenter().getLongitude());
                //System.out.println("Stored " + ((Double)((ReachCircleTask)task).getCenter().getLongitude()).toString());
                parameters.add((Integer)((ReachCircleTask)task).getRadius());
                //System.out.println("Stored " + ((Integer)((ReachCircleTask)task).getRadius()).toString());

                break;
            }
            case REACHPOLYGONTASK: {
                /*
                 * Store a ReachPolygonTask. Its GPS values are stored as pais of Double-Objects
                 * starting with index 0 (latitude of point 1) and 1 (longitude for point 1).
                 * As per current state of the ReachPolygonTask-Class it is assumed there are 
                 * at least 3 GPS values stored and so there is no handling for OutOfBounds cases.
                 * 
                 * Take note that one additional point (to close the polygon) is always created!!
                 */
                for (int i = 0; i < (((ReachPolygonTask)task).getPoints().size()); i++) {
                    parameters.add((Double)((ReachPolygonTask)task).getPoints().get(i).getLatitude());
                    parameters.add((Double)((ReachPolygonTask)task).getPoints().get(i).getLongitude());
                }
                

                break;
            }
            case COMPASSCOURSETASK: {
                /*
                 * Store a CompassCourseTask. The angle will be stored as Integer as index 0 of paramters.
                 */
                parameters.add((Integer)((CompassCourseTask)task).getAngle());
                
                break;
            }
            case HOLDANGLETOWINDTASK: {
                /*
                 * Store a HoldAngleToWindTask. The angle will be stored as Integer as index 0 of paramters.
                 */
                parameters.add((Integer)((HoldAngleToWindTask)task).getAngle());
                
                break;
            }
            case STOPTASK: {
                /*
                 * Store a StopTask. The isPropellorOff value is stored as Boolean at index 0 of parameters.
                 */
                parameters.add((Boolean)((StopTask)task).isTurnPropellorOff());
                
                break;
            }
        }
    }
    
    /**
     * Attempts to create a Task according to the data stored in this MissionTask. Will throw Exceptions relating to
     * faulty data if such a thing is encountered. As of its current state, NO SPECIAL handling is implemented to
     * detect incorrect setups of parameters for the Task expected.
     * @return The Task as defined by name and parameters, otherwise null if no name is specified/ supported.
     */
    public Task getTask() throws IllegalArgumentException {
        Task myTask = null;
        
        if (this.name != null) {
            //System.out.println("Name was <not> NULL");
            try {
                switch (supported.valueOf(name.toUpperCase())) {
                    case REACHCIRCLETASK: {
                        /*
                         * Try building a ReachCircleTask considering of 1.) a
                         * GPS value and 2.) a radius. It is suspected that GPS
                         * is stored as doubles for latitude and longitude in
                         * parameters at index 0 and 1. Furthermore the radius
                         * should be stored as Integer at index 2 of parameters.
                         */
                        //System.out.println("Retrieving CircleTask...");
                        GPS myGPS = new GPS((Double) parameters.get(0), (Double) parameters.get(1));
                        int myRadius = (Integer) parameters.get(2);
                        myTask = new ReachCircleTask(myGPS, myRadius);
                        //System.out.println("Gotten " + myTask.toString());

                        break;
                    }
                    case REACHPOLYGONTASK: {
                        /*
                         * Try building a ReachPolygonTask expecting all values
                         * in parameters to be GPS coordinates as pairs of
                         * latitude and longitude. Therefore it is also assumed
                         * there is no odd number of objects present.
                         * 
                         * Take note that one additional point (to close the polygon) is always created!!
                         */
                        ArrayList<GPS> myPolygon = new ArrayList<GPS>();
                        for (int i = 0; i < parameters.size(); i = i + 2) {
                            myPolygon.add(new GPS((Double)parameters.get(i), (Double)parameters.get(i+1)));
                        }
                        myTask = new ReachPolygonTask(myPolygon);

                        break;
                    }
                    case COMPASSCOURSETASK: {
                        /*
                         * Try building a CompassCourseTask. It is expected that
                         * the angle is stored as int at index 0 of parameters.
                         */
                        int myAngle = (Integer) parameters.get(0);
                        myTask = new CompassCourseTask(myAngle);
                        
                        break;
                    }
                    case HOLDANGLETOWINDTASK: {
                        /*
                         * Try building a HoldAngleToWindTask. It is expected
                         * that the angle is stored as int at index 0 of
                         * parameters.
                         */
                        int myAngle = (Integer) parameters.get(0);
                        myTask = new HoldAngleToWindTask(myAngle);
                        
                        break;
                    }
                    case STOPTASK: {
                        /*
                         * Try building a StopTask. It is expected to find a
                         * boolean at paramters index 0 which represents
                         * isPropellorOff.
                         */
                        boolean myIsPropellorOff = (Boolean) parameters.get(0);
                        myTask = new StopTask(myIsPropellorOff);
                        
                        break;
                    }
                }

                return myTask;
            } catch (IllegalArgumentException e) {
                throw e;
            }
        } 
        else {
            //System.out.println("Name was NULL");
            return null;
        }
    }
    
    /**
     * This method is used to export the Task represented by name and parameters in
     * to a XML view easily editable by hand.
     * @return 
     */
    public StringBuffer exportToXML() {
        return null;
    }
    
    /**
     * Use this method to import Tasks saved in XML view as retrieved by exportToXML.
     * The variables name and parameters will be setup accordingly.
     * @param xmlStatement 
     */
    public void importFromXML(StringBuffer xmlStatement) {
        
    }
    
    /**
     * Returns the name of this Task.
     * @return
     */
    public String getName() {
        return name;
    }
    
}
