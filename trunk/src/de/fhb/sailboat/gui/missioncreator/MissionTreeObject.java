package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.mission.Task;

/**
 * To be used as a userLoadObject within JTree. Enables storing tasks within the missionTree while using DefaultMutableTreeNode.
 * 
 * @author Frocean
 */
public class MissionTreeObject {
    // The "name" of the MissionTreeObject, will be returned by the toString()-method, and thus, be displayed within the missionTree
    // this has NO getter, since toString() pretty much equals that
    private String name;
    
    // The task contained by this UserLoadObject
    private Task task;
    
    public MissionTreeObject(String name, Task task) {
        this.name = name;
        this.task = task;
    }
    
    public MissionTreeObject(String name) {
        this(name, null);
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
