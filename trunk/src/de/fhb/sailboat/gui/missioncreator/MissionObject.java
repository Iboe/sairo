package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.mission.MissionImpl;
import de.fhb.sailboat.mission.Task;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 * This class represents a mission (list of tasks) as a serializable object suited for saving/ loading.
 * 
 * @author Patrick Rutter
 */
public class MissionObject implements Serializable{
    
    /***
     * Extension for files containing this class serialized. Used for FileChoosers.
     */
    final public static transient String FILE_EXTENSION = ".fhsm";
    
    /***
     * Description for the file extension. Used for FileChoosers.
     */
    final public static transient String FILE_DESCRIPTION = "Missionsdateien, erstellt mit dem MissionCreator des FHB-Sairo-Projektes.";
    
    private DefaultMutableTreeNode root;
    
    public MissionObject(JTree missionTree) {
        root = ((DefaultMutableTreeNode)((DefaultTreeModel)missionTree.getModel()).getRoot());
    }
    
    private MissionImpl makeMission() {
        MissionImpl mission = null;
        
        JTree tree = new JTree(root);
        
        List<Task> tasklist = new ArrayList();
        TreeModel model = tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
        
        parseChilds(root, tasklist);
        
        mission = new MissionImpl();
        mission.setTasks(tasklist);
        
        //debug
        //System.out.println("Created mission with " + tasklist.size() + " entries.");
        
        return mission;
    }
    
    private void parseChilds(DefaultMutableTreeNode parent, List<Task> tasklist) {
        DefaultMutableTreeNode child;
        // debug
        //System.out.println("Found " + parent.getChildCount() + " children at " + ((MissionTreeObject)parent.getUserObject()).toString());
        for (int i = 0; i < parent.getChildCount(); i++) {
            // get the child node at index i
            child = (DefaultMutableTreeNode)parent.getChildAt(i);
            // check if the child is a task
            if (((MissionTreeObject)child.getUserObject()).getTask() != null) {
                // add task to tasklist
                tasklist.add(((MissionTreeObject)child.getUserObject()).getTask());
                
                // debug output
                //System.out.println("Adding task named <" + ((MissionTreeObject)child.getUserObject()).toString() + "> to mission...");
            }
            // check if child has children itself, parsing it if so
            if (child.getChildCount() > 0) parseChilds(child, tasklist);
        }
    }

    public MissionImpl getMission() {
        return makeMission();
    }
    
    public DefaultMutableTreeNode getRoot() {
        return root;
    }
}
