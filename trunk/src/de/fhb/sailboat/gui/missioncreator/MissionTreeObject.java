package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.mission.Task;
import java.io.Serializable;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 * To be used as a userLoadObject within JTree. Enables storing tasks within the missionTree while using DefaultMutableTreeNode.
 * 
 * @author Patrick Rutter
 */
public class MissionTreeObject implements Serializable {
	private static final long serialVersionUID = 1L;

	// The "name" of the MissionTreeObject, will be returned by the toString()-method, and thus, be displayed within the missionTree
    // this has NO getter, since toString() pretty much equals that
    private String name;
    
    // The task contained by this UserLoadObject
    private MissionTask task;
    
    // If this node just represents an obstacle, this variable stores it
    private MapMarker obstacle;
    
    /**
     * Constructs and initializes the MissionTreeObject.
     * @param name
     * @param task
     */
    public MissionTreeObject(String name, Task task) {
        this.name = name;
        this.task = new MissionTask(task);
    }
    
    /**
     * Constructs and initializes the MissionTreeObject.
     * @param name
     * @param obstacle
     */
    public MissionTreeObject(String name, MapMarker obstacle) {
        this.name = name;
        this.obstacle = obstacle;
    }
    
    /**
     * Constructs and initializes the MissionTreeObject.
     * @param name
     */
    public MissionTreeObject(String name) {
        this.name = name;
        this.task = new MissionTask(null);
        this.obstacle = null;
    }

    /**
     * Returns the Task stored in this MissionTreeObject.
     * @return
     */
    public Task getTask() {
        return task.getTask();
    }

    /**
     * Serts the Task to be stored in this MissionTreeObject.
     * @param task
     */
    public void setTask(Task task) {
        this.task = new MissionTask(task);
    }

    /**
     * Sets the name displayed for this MissionTreeObject, normally equaling the name of the Task stored within.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the obstacle stored in this MissionTreeObject, if any.
     * @return obstacle
     */
    public MapMarker getObstacle() {
        return obstacle;
    }
    
    @Override
    /**
     * String-representation of this class.
     */
    public String toString() {
        return this.name;
    }
}
