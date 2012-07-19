package de.fhb.sailboat.gui.missioncreator;

import java.util.ArrayList;

/**
 *
 * @author Patrick Rutter
 */
public class MissionCreatorLogic {
    
    /**
     * Copied/ Cut items from missionTree are saved to this list
     */
    private ArrayList missionTreeClipboard;
    
    public MissionCreatorLogic() {
        this.missionTreeClipboard = new ArrayList();    // initialize clipboard
    }

    public ArrayList getMissionTreeClipboard() {
        return missionTreeClipboard;
    }

    public void setMissionTreeClipboard(ArrayList missionTreeClipboard) {
        this.missionTreeClipboard = missionTreeClipboard;
    }
}
