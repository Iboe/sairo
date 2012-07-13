package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.mission.Task;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Patrick Rutter
 */
public class MissionTreeNode extends DefaultMutableTreeNode {
    private String  name;       // Name of the node
    private boolean isLeaf;     // Is this node a leaf?
    private Task    task;       // Task represented by this node (if not null)
    
    /**
     * Creates a leaf node without any tasks attached to it.
     * @param name name of the node
     */
    public MissionTreeNode(String name) {
        this.name = name;
        this.isLeaf = true;
        this.task = null;
    }
    
    public MissionTreeNode(String name, Task task) {
        this.name = name;
        this.isLeaf = false;
        this.task = task;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public boolean isLeaf() {
        return this.isLeaf;
    }
}
