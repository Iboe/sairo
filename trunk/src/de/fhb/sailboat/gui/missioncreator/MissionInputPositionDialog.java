package de.fhb.sailboat.gui.missioncreator;

import de.fhb.sailboat.data.GPS;
import de.fhb.sailboat.gui.map.MissionCreatingMap;
import java.util.List;
import javax.swing.JOptionPane;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 *
 * This dialog enables easy input of positions (whether it be single GPS coord's or whole polygons).
 * 
 * @author Patrick Rutter
 */
public class MissionInputPositionDialog extends javax.swing.JDialog {

    private final static String ERRORTEXT = "Fehler";
    private final static String ERRORTEXT_ILLEGAL_VALUE = "Illegale Koordinaten - Unzulässige Zeichen!";
    
    /**
     * State that signals a successfull input (using ok-button).
     */
    public static int APPROVE_OPTION = 0;
    
    /**
     * The name given to the task. The chosen angle will ALWAYS be attached to this for overview
     */
    private String chosenName;
    
    /**
     * State in which this dialog was exited.
     */
    private int state;
    
    /**
     * The map used for input and displaying of positions.
     */
    private MissionCreatingMap missionMap;
    
    /**
     * Creates new form MissionInputPositionDialog
     */
    public MissionInputPositionDialog(java.awt.Frame parent, boolean modal, String initialName, String title, boolean singleGPSMode) {
        super(parent, modal);
        initComponents();
        
        this.state = -1;
        this.setTitle(title);
        this.nameTextField.setText(initialName);
        
        this.missionMap = new MissionCreatingMap();
        if (singleGPSMode) {
            this.missionMap.setMarkerMode(0);
        } else {
            this.missionMap.setMarkerMode(1);
        }
        
        missionMap.mapPanel(missionMapPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        missionMapPanel = new javax.swing.JPanel();
        directInputPanel = new javax.swing.JPanel();
        latitudeLabel = new javax.swing.JLabel();
        latitudeTextField = new javax.swing.JTextField();
        longitudeLabel = new javax.swing.JLabel();
        longitudeTextField = new javax.swing.JTextField();
        addCoordButton = new javax.swing.JButton();
        controlPanel = new javax.swing.JPanel();
        removeAllButton = new javax.swing.JButton();
        removeLastButton = new javax.swing.JButton();
        acceptButton = new javax.swing.JButton();
        abortButton = new javax.swing.JButton();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Positionseingabe");
        setResizable(false);

        missionMapPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout missionMapPanelLayout = new javax.swing.GroupLayout(missionMapPanel);
        missionMapPanel.setLayout(missionMapPanelLayout);
        missionMapPanelLayout.setHorizontalGroup(
            missionMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        missionMapPanelLayout.setVerticalGroup(
            missionMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 266, Short.MAX_VALUE)
        );

        directInputPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Direkteingabe"));

        latitudeLabel.setText("Latitude");

        longitudeLabel.setText("Longitude");

        addCoordButton.setText("Hinzufügen");
        addCoordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCoordButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout directInputPanelLayout = new javax.swing.GroupLayout(directInputPanel);
        directInputPanel.setLayout(directInputPanelLayout);
        directInputPanelLayout.setHorizontalGroup(
            directInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(latitudeLabel)
            .addComponent(latitudeTextField)
            .addComponent(longitudeLabel)
            .addComponent(longitudeTextField)
            .addComponent(addCoordButton, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
        );
        directInputPanelLayout.setVerticalGroup(
            directInputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(directInputPanelLayout.createSequentialGroup()
                .addComponent(latitudeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(latitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longitudeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longitudeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCoordButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Kontrollen"));

        removeAllButton.setText("Alle Punkte entfernen");
        removeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllButtonActionPerformed(evt);
            }
        });

        removeLastButton.setText("Letzten Punkt entfernen");
        removeLastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLastButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(removeAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeLastButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(removeAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeLastButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        acceptButton.setText("OK");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        abortButton.setText("Abbrechen");
        abortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abortButtonActionPerformed(evt);
            }
        });

        nameLabel.setText("Taskname");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(directInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(acceptButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(controlPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(abortButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(missionMapPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(nameTextField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acceptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abortButton))
                    .addComponent(directInputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void addCoordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if ((isLegalDoubleString(this.latitudeTextField.getText())) && (isLegalDoubleString(this.longitudeTextField.getText()))) { 
            // if single mode remove all present markers
            if (this.missionMap.getMarkerMode() == 0) this.missionMap.removeEveryObject();
            
            Double lat = Double.parseDouble(this.latitudeTextField.getText());
            Double lon = Double.parseDouble(this.longitudeTextField.getText());
            this.missionMap.addMapMarker(new GPS(lat, lon));
            this.missionMap.invalidate();
        }
        else JOptionPane.showMessageDialog(this, this.ERRORTEXT_ILLEGAL_VALUE, this.ERRORTEXT, JOptionPane.ERROR_MESSAGE);
    }

    private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.missionMap.removeEveryObject();
        this.missionMap.invalidate();
    }

    private void removeLastButtonActionPerformed(java.awt.event.ActionEvent evt) {
        List<MapMarker> markerList = this.missionMap.getMarkerList();
        if (markerList.size() > 0) {
            markerList.remove(markerList.size() - 1);
            this.missionMap.setMarkerList(markerList);
        }
        this.missionMap.invalidate();
    }

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.chosenName = this.nameTextField.getText();
        if (chosenName.isEmpty()) chosenName = "GPSTask";
        
        this.state = APPROVE_OPTION;
        
        this.setVisible(false);
    }

    private void abortButtonActionPerformed(java.awt.event.ActionEvent evt) {
        state = -1;
        
        
        this.setVisible(false);
    }

    /**
     * Returns the number of MapMarkers placed on the map during dialog use,
     * enabling iterating through them via index and getGPSinListAt().
     * @return number of GPS points present in map 
     */
    public int getGPSListSize() {
        return this.missionMap.getMarkerList().size();
    }
    
    /**
     * Returns the GPS object representing the MapMarker in the MapMarker-List of
     * the map at the chosen index. This method will return null if a out-of-bounds
     * index is chosen.
     * 
     * @param index
     * @return 
     */
    public GPS getGPSinListAt(int index) {
        GPS myReturn = null;
        if (index < getGPSListSize()) myReturn = new GPS(this.missionMap.getMarkerList().get(index).getLat(), this.missionMap.getMarkerList().get(index).getLon());
        return myReturn;
    }
    
    /**
     * Returns the name chosen for the task during the dialog.
     * @return chosen name
     */
    public String getChosenName() {
        return this.chosenName;
    }
    
    /**
     * Gets the state of the dialog which it holds right now, or rather (as intended), which it
     * terminated with. This will be -1 if cancelled or equal toAPPROVE_OPTION if exited via OK-Button.
     * @return state
     */
    public int getState() {
        return state;
    }
    
    /**
     * Returns true ONLY if the given string conatins only numbers and a maximum of 1 "." (= is a legal double).
     * @param text
     * @return 
     */
    private boolean isLegalDoubleString(String text) {
        boolean myReturn = true;
        
        if (!text.isEmpty()) {
            // check if string contains more than 1 '.'
            String test = text;
            test = test.replaceFirst("\\.", "#");
            if (test.contains(".")) {
                //System.out.println("Gescheitert an '.'");
                myReturn = false;
            }
            else {
                // check each individual char in string
                for (int i = 0; i < text.length(); i++) {
                    // check if the char is NOT 0..9 or '.'
                    if ((text.charAt(i) < '0') || (text.charAt(i) > '9')) {
                        //System.out.println("Numbers cleared.");
                        if (text.charAt(i) != '.') {
                            //System.out.println("Gescheitert an char: '" + text.charAt(i) + "'");
                            myReturn = false;
                            i = text.length();
                        }
                    }
                }
            }
        } else myReturn = false;
        
        return myReturn;
    }
    
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MissionInputPositionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MissionInputPositionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MissionInputPositionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MissionInputPositionDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        /*java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MissionInputPositionDialog dialog = new MissionInputPositionDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }*/
    // Variables declaration - do not modify
    private javax.swing.JButton abortButton;
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton addCoordButton;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel directInputPanel;
    private javax.swing.JLabel latitudeLabel;
    private javax.swing.JTextField latitudeTextField;
    private javax.swing.JLabel longitudeLabel;
    private javax.swing.JTextField longitudeTextField;
    private javax.swing.JPanel missionMapPanel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton removeAllButton;
    private javax.swing.JButton removeLastButton;
    // End of variables declaration
}
