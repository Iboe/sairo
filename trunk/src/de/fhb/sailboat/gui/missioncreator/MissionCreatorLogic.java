package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.MainControllerImpl;
import de.fhb.sailboat.gui.GUILogic;
import de.fhb.sailboat.mission.Task;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 * This class holds the logic behind the workings of the MissionCreator dialog.
 * 
 * @author Patrick Rutter
 */
public class MissionCreatorLogic {
    
    /**
     * Used as interface to send missions
     */
    private GUILogic guiLogic;
    
    /**
     * Copied/ Cut items from missionTree are saved to this list
     */
    private ArrayList<Object> missionTreeClipboard;
    
    public MissionCreatorLogic(GUILogic guiLogic) {
        this.guiLogic = guiLogic;
        this.missionTreeClipboard = new ArrayList<Object>();    // initialize clipboard
    }
    
    public void missionTreeInitialize(JTree missionTree) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new MissionTreeObject("Mission"));
        
        /*DefaultMutableTreeNode start = new DefaultMutableTreeNode(new MissionTreeObject("Start"));
        root.add(start);
        
        DefaultMutableTreeNode end = new DefaultMutableTreeNode(new MissionTreeObject("Ende"));
        root.add(end);*/
        
        JTree tree = new JTree(root);
        missionTree.setModel(tree.getModel());
    }
    
    /**
     * Adds a new list to the missionTree (child of root with no task attached).
     * @param missionTree
     * @param name 
     */
    public void missionTreeNew_List(JTree missionTree, String name) {
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(new MissionTreeObject(name), true);
        
        // get current selection
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)missionTree.getLastSelectedPathComponent();
        
        // insert new leaf (list)
        ((DefaultTreeModel)missionTree.getModel()).insertNodeInto(item, selected, selected.getChildCount());
        
        // expand selection
        missionTree.expandPath(missionTree.getSelectionPath());
    }
    
    /**
     * Adds a new task to the missionTree (child of the current list OR root).
     * @param missionTree
     * @param name
     * @param load the concrete task object
     */
    private void missionTreeNew_Task(JTree missionTree, String name, Task load) {
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(new MissionTreeObject(name, load), false);
        
        // get current selection
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)missionTree.getLastSelectedPathComponent();
        
        // insert new leaf (list)
        ((DefaultTreeModel)missionTree.getModel()).insertNodeInto(item, selected, selected.getChildCount());
        
        // expand selection
        missionTree.expandPath(missionTree.getSelectionPath());
    }
    
    /**
     * Checks if the currently selected node is a legal node for inserting (meaning its a list or root)
     * @param missionTree
     * @return true only if a list or root
     */
    public boolean isLegalInsertNode(JTree missionTree) {
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)missionTree.getLastSelectedPathComponent();
        return (((MissionTreeObject)selected.getUserObject()).getTask() == null);
    }
    
    public void missionTree_NewPropellorFullForward_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, null, MainControllerImpl.PROPELLOR_MAX);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewPropellorStop_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, null, MainControllerImpl.PROPELLOR_NORMAL);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewPropellorFullBackward_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, null, MainControllerImpl.PROPELLOR_MIN);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewRudderRight_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, MainControllerImpl.RUDDER_RIGHT, null);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewRudderNeutral_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, MainControllerImpl.RUDDER_NORMAL, null);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewRudderLeft_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.PrimitiveCommandTask(null, MainControllerImpl.RUDDER_LEFT, null);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewCompassCourse_Task(JTree missionTree, String name, int angle) {
        Task load = new de.fhb.sailboat.mission.CompassCourseTask(angle);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewHoldAngleToWind_Task(JTree missionTree, String name, int angle) {
        Task load = new de.fhb.sailboat.mission.HoldAngleToWindTask(angle);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_Stop_Task(JTree missionTree, String name) {
        Task load = new de.fhb.sailboat.mission.StopTask();
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewReachCircle_Task(JTree missionTree, String name, GPS position, int radius) {
        // TODO Radius festsetzen
        Task load = new de.fhb.sailboat.mission.ReachCircleTask(position, 3);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewReachPolygon_Task(JTree missionTree, String name, List<GPS> polygon) {
        Task load = new de.fhb.sailboat.mission.ReachPolygonTask(polygon);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewCrossLine_Task(JTree missionTree, String name, GPS start, GPS end) {
        Task load = new de.fhb.sailboat.mission.CrossLineTask(start, end);
        missionTreeNew_Task(missionTree, name, load);
    }
    
    public void missionTree_NewObstacle(JTree missionTree, String name, MapMarker obstacle) {
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(new MissionTreeObject(name, obstacle), false);
        
        // get current selection
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)missionTree.getLastSelectedPathComponent();
        
        // insert new leaf (list)
        ((DefaultTreeModel)missionTree.getModel()).insertNodeInto(item, selected, selected.getChildCount());
        
        // expand selection
        missionTree.expandPath(missionTree.getSelectionPath());
    }

    /**
     * Copies the current seleceted node in the missionTree to the "clipboard", overwriting previously copied entries.
     * @param missionTree 
     */
    public void missionTreeCopy(JTree missionTree) {
        this.missionTreeClipboard.clear();
        this.missionTreeClipboard.add(missionTree.getSelectionModel().getSelectionPath().getLastPathComponent());
    }
    
    /**
     * Pastes, if present, the currently copied node into the next relevant list (or root).
     * @param missionTree 
     */
    public void missionTreePaste(JTree missionTree) {
        if (!this.missionTreeClipboard.isEmpty()) {
            // get node from clipboard
            DefaultMutableTreeNode pasted = (DefaultMutableTreeNode)((DefaultMutableTreeNode)missionTreeClipboard.get(0)).clone();
            
            // get current selection
            DefaultMutableTreeNode selected = (DefaultMutableTreeNode)missionTree.getLastSelectedPathComponent();
            
            // insert new leaf (list)
            ((DefaultTreeModel)missionTree.getModel()).insertNodeInto(pasted, selected, selected.getChildCount());
            
            // expand selection
            missionTree.expandPath(missionTree.getSelectionPath());
            
            /*// check if insertion is allowed, if not put out an error message BUT attach the node to root instead
            if (((MissionTreeObject)selected.getUserObject()).getTask() == null) {
                // insert new leaf (list)
                ((DefaultTreeModel)missionTree.getModel()).insertNodeInto(pasted, selected, selected.getChildCount());
            }
            else {
                JOptionPane.showMessageDialog(null, "Listen/ Tasks können nur in Listen eingefügt werden!\nFüge unter Wurzel ein.", "Fehler", JOptionPane.ERROR_MESSAGE);
                // A little overcomplicated aproach for adding an node to rootr itself...
                DefaultMutableTreeNode root = ((DefaultMutableTreeNode)((DefaultTreeModel)missionTree.getModel()).getRoot());
                root.add(pasted);
                missionTree.setModel(new JTree(root).getModel());
            }*/
        }
    }
    
    /**
     * Does both copy and delete the selected node, the later only, of course, if not root
     * @param missionTree 
     */
    public void missionTreeCut(JTree missionTree) {
        missionTreeCopy(missionTree);
        missionTreeDelete(missionTree);
    }
    
    /**
     * Deletes the currently selected node.
     * @param missionTree 
     */
    public void missionTreeDelete(JTree missionTree) {
        //TODO add a "are you really REALLY really sure?"-popup
        
        // check if selection is NOT root (illegal)
        DefaultMutableTreeNode root = ((DefaultMutableTreeNode)((DefaultTreeModel)missionTree.getModel()).getRoot());
        DefaultMutableTreeNode selection = (DefaultMutableTreeNode)missionTree.getSelectionModel().getSelectionPath().getLastPathComponent();        
        if (root != selection) {
            // remove current selection
            ((DefaultTreeModel)missionTree.getModel()).removeNodeFromParent(selection);
        }
        else {
            JOptionPane.showMessageDialog(null, "Wurzel der Mission kann nicht entfernt werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    /**
     * Edits the currently selected node. Currently reduced to changing the name.
     * @param missionTree 
     */
    public void missionTreeEdit(JTree missionTree, String name) {
        //TODO do a proper edit option for ALL tasks (changing positions etc)
        
        // alter the MissionTreeObject as requested by recreating it with the new values
        MissionTreeObject item = new MissionTreeObject(name, ((MissionTreeObject)((DefaultMutableTreeNode)missionTree.getSelectionModel().getSelectionPath().getLastPathComponent()).getUserObject()).getTask());
        
        // Add recreted MissionTreeObject to selected node (kinda unelegant, but works)
        // TODO do this properly to fire events accordingly
        ((DefaultMutableTreeNode)missionTree.getSelectionModel().getSelectionPath().getLastPathComponent()).setUserObject(item);
        DefaultTreeModel model = (DefaultTreeModel)missionTree.getModel(); model.reload();
    }
    
    /**
     * Used to check the status of the clipboard.
     * @return true if no object is in the clipboard 
     */
    public boolean isEmptyClipboard() {
        return this.missionTreeClipboard.isEmpty();
    }
    
    /**
     * Commits a mission to the boat via GUILogic interface.
     * @param instructions MissionObject containing the mission
     */
    public void commitMission(MissionObject instructions) {
        this.guiLogic.commitMission(instructions.getMission());
    }
    
    /**
     * Elegant way to notify the user of missing functions/ lazy programmer.
     */
    public void nope() {
        JOptionPane.showMessageDialog(null, "Funktion wurde noch nicht implementiert.", "Nope", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Saves the current mission contained by the missionTree to the given filename.
     * @param missionTree
     * @param filename 
     */
    public void saveMission(JTree missionTree, String filename) {
        MissionObject mission = new MissionObject(missionTree);
        MissionSaver.save(filename, mission);
    }
    
    /**
     * Loads the file with the given filename and sets the missionTree to the mission contained within that file, if possible.
     * @param missionTree
     * @param filename 
     */
    public void loadMission(JTree missionTree, String filename) {
        MissionObject mission = MissionLoader.load(filename);
        if (mission != null) {
            JTree tree = new JTree(mission.getRoot());
            missionTree.setModel(tree.getModel());
        }
    }
}
