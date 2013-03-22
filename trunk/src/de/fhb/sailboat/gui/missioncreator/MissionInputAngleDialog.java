package de.fhb.sailboat.gui.missioncreator;

/**
 * This dialog enables easy input for angle tasks.
 * 
 * @author Patrick Rutter
 */
public class MissionInputAngleDialog extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;

	/**
     * State that signals a successfull input (using ok-button).
     */
    public static int APPROVE_OPTION = 0;
    
    /**
     * The name given to the task. The chosen angle will ALWAYS be attached to this for overview.
     */
    private String chosenName;
    
    /**
     * The chosen angle.
     */
    private int angle;
    
    /**
     * State in which this dialog was exited.
     */
    private int state;
    
    /**
     * Creates new form MissionInputAngleDialog
     * @param parent
     * @param modal
     * @param initialName
     * @param title
     */
    public MissionInputAngleDialog(java.awt.Frame parent, boolean modal, String initialName, String title) {
        super(parent, modal);
        initComponents();
        
        this.state = -1;
        this.angleSlider.setValue(180);
        this.inputNameTextField.setText(initialName);
        this.setTitle(title);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        inputNameLabel = new javax.swing.JLabel();
        inputNameTextField = new javax.swing.JTextField();
        inputAngleLabel = new javax.swing.JLabel();
        angleSlider = new javax.swing.JSlider();
        angleSliderLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));

        inputNameLabel.setText("Taskname");

        inputAngleLabel.setText("Winkel");

        angleSlider.setMajorTickSpacing(90);
        angleSlider.setMaximum(360);
        angleSlider.setMinorTickSpacing(45);
        angleSlider.setPaintLabels(true);
        angleSlider.setPaintTicks(true);
        angleSlider.setValue(180);
        angleSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                angleSliderStateChanged(evt);
            }
        });

        angleSliderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleSliderLabel.setText("180�");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Abbrechen");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputNameLabel)
                            .addComponent(inputAngleLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(angleSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputNameTextField)
                            .addComponent(angleSliderLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputAngleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(angleSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(angleSliderLabel)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(okButton)
                            .addComponent(cancelButton)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
     * Updates Slider state.
     * @param evt
     */
    private void angleSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        this.angleSliderLabel.setText(this.angleSlider.getValue() + "�");
    }

    /**
     * Completes the dialog while setting the name of the resulting task to a default one if none was chosen.
     * @param evt
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.chosenName = this.inputNameTextField.getText();
        if (chosenName.isEmpty()) chosenName = "AngleTask";
        this.angle = angleSlider.getValue();
        chosenName = chosenName + " " + angle + "�"; // per default, add the chosen angle to the name
        
        state = APPROVE_OPTION;
        
        this.setVisible(false);
    }

    /**
     * Cancels the dialog.
     * @param evt
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {
        state = -1;
        
        this.setVisible(false);
    }

    /**
     * Returns the angle chosen during the dialog.
     * 
     * @return chosen angle 
     */
    public int getAngle() {
        return angle;
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
     * Returns the name chosen for the task during the dialog.
     * @return chosen name
     */
    public String getChosenName() {
        return this.chosenName;
    }
    
    // Variables declaration - do not modify
    private javax.swing.JSlider angleSlider;
    private javax.swing.JLabel angleSliderLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel inputAngleLabel;
    private javax.swing.JLabel inputNameLabel;
    private javax.swing.JTextField inputNameTextField;
    private javax.swing.JButton okButton;
    // End of variables declaration
}
