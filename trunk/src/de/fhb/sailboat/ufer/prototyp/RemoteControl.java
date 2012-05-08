package de.fhb.sailboat.ufer.prototyp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhb.sailboat.control.navigator.WorkerThread;
import de.fhb.sailboat.serial.actuator.AKSENLocomotion;

/**
* Class for remote controlling a autonomous sailing boat via a minimalistic user interface.
* @author Patrick Rutter
*/
public class RemoteControl extends javax.swing.JFrame {

    public static final int PROPELLOR_MIN = 31;
    public static final int PROPELLOR_NULL = 73;
    public static final int PROPELLOR_MAX = 114;
    
    public static final int SAIL_MIN = 34;
    public static final int SAIL_NULL = 68;
    public static final int SAIL_MAX = 108;
    
    public static final int RUDDER_MIN = 31;
    public static final int RUDDER_NULL = 73;
    public static final int RUDDER_MAX = 114;
    
    AKSENLocomotion locomotion;
    private static final Logger LOG = LoggerFactory.getLogger(RemoteControl.class);
    
    private int lastPropellorSend = -1;  // The last value send for this control, used for new-input determination
    private int lastSailSend = -1;       // The last value send for this control, used for new-input determination
    private int lastRudderSend = -1;     // The last value send for this control, used for new-input determination
    
    /**
     * Creates new form remoteControl
     */
    public RemoteControl() {
        initComponents();
        
        locomotion = new AKSENLocomotion();
        
        menuSettings_AutoSendCommands.setSelected(true); // Auto-Send new values input?
        
        propellorSlider.setValue(0);
        propellorText.setText(convertToNativeValue(propellorSlider.getValue(), PROPELLOR_MIN, PROPELLOR_NULL, PROPELLOR_MAX) + "");
        sailSlider.setValue(0);
        sailText.setText(convertToNativeValue(sailSlider.getValue(), SAIL_MIN, SAIL_NULL, SAIL_MAX) + "");
        rudderSlider.setValue(0);
        rudderText.setText(convertToNativeValue(rudderSlider.getValue(), RUDDER_MIN, RUDDER_NULL, RUDDER_MAX) + "");
        sendCommands(); // initialize sailboat
    }
    
    /**
     * Attempts to send all relevant control commands (propellor, sail and rudder). 
     */
    private void sendCommands() {
        sendPropellorCommand(Integer.parseInt(propellorText.getText()));
        sendSailCommand(Integer.parseInt(sailText.getText()));
        sendRudderCommand(Integer.parseInt(rudderText.getText()));
    }
    
    /**
     * Sends a propellor adjustment command. Will ignore any value equalling the last value send to avoid unnecessary traffic.
     * @param value to be send
     */
    private void sendPropellorCommand(int value) {
        if (value != lastPropellorSend) {
            lastPropellorSend = value;
            LOG.trace("Attempting to send <" + value + "> to propellor.");
            locomotion.setPropellor(value);
        }
    }
    
    /**
     * Sends a sail adjustment command. Will ignore any value equalling the last value send to avoid unnecessary traffic.
     * @param value to be send
     */
    private void sendSailCommand(int value) {
        if (value != lastSailSend) {
            lastSailSend = value;
            LOG.trace("Attempting to send <" + value + "> to sail.");
            locomotion.setSail(value);
        }
    }
    
    /**
     * Sends a rudder adjustment command. Will ignore any value equalling the last value send to avoid unnecessary traffic.
     * @param value to be send
     */
    private void sendRudderCommand(int value) {
        if (value != lastRudderSend) {
            lastRudderSend = value;
            LOG.trace("Attempting to send <" + value + "> to rudder.");
            locomotion.setRudder(value);
        }
    }

    /**
     * Converts a given value from -100 to +100 into the target range.
     * Meant for converting the range of the slider values to a concrete value to
     * be used as instruction value for the locomotion.
     * Requires of the values to be following a general trend (min < nul < max).
     * @param value to be converted
     * @param min minimum target value (equals -100 slider)
     * @param nul neutral target value (equals 0 slider)
     * @param max maximum target value (equals +100 slider)
     * @return converted value
     */
    private int convertToNativeValue(int value, int min, int nul, int max){
         int myReturn;
         
         if (value == 0) {
             myReturn = nul;
         }
         else {
             int nativeTick;
             if (value > 0) {
                 nativeTick = (max - nul);
             }
             else {
                 nativeTick = (nul - min);
             }
             myReturn = ((value * nativeTick) / 100) + nul;
         }
         
         return myReturn;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        propellorLabel = new javax.swing.JLabel();
        sailLabel = new javax.swing.JLabel();
        propellorText = new javax.swing.JTextField();
        sailText = new javax.swing.JTextField();
        propellorSlider = new javax.swing.JSlider();
        sailSlider = new javax.swing.JSlider();
        rudderSlider = new javax.swing.JSlider();
        rudderText = new javax.swing.JTextField();
        rudderLabel = new javax.swing.JLabel();
        buttonSendCommands = new javax.swing.JButton();
        rC_MenuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuFile_Close = new javax.swing.JMenuItem();
        menuSettings = new javax.swing.JMenu();
        menuSettings_ResetMotor = new javax.swing.JMenuItem();
        menuSettings_ResetSail = new javax.swing.JMenuItem();
        menuSettings_ResetRudder = new javax.swing.JMenuItem();
        menuSettings_AutoSendCommands = new javax.swing.JCheckBoxMenuItem();
        menuSettings_Seperator = new javax.swing.JPopupMenu.Separator();
        menuSettings_SendCommands = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RemoteControl");
        setResizable(false);

        propellorLabel.setText("Motor");
        propellorLabel.setFocusable(false);

        sailLabel.setText("Segel");
        sailLabel.setFocusable(false);

        propellorText.setEditable(false);
        propellorText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        propellorText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        propellorText.setText("0");
        propellorText.setFocusable(false);

        sailText.setEditable(false);
        sailText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        sailText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        sailText.setText("0");
        sailText.setFocusable(false);

        propellorSlider.setMinimum(-100);
        propellorSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        propellorSlider.setValue(0);
        propellorSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                propellorSliderReleased(evt);
            }
        });
        propellorSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                propellorUpdated(evt);
            }
        });

        sailSlider.setMinimum(-100);
        sailSlider.setOrientation(javax.swing.JSlider.VERTICAL);
        sailSlider.setValue(0);
        sailSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sailSliderReleased(evt);
            }
        });
        sailSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sailUpdated(evt);
            }
        });

        rudderSlider.setMinimum(-100);
        rudderSlider.setValue(0);
        rudderSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                rudderSliderReleased(evt);
            }
        });
        rudderSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rudderUpdated(evt);
            }
        });

        rudderText.setEditable(false);
        rudderText.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rudderText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rudderText.setText("0");
        rudderText.setFocusable(false);

        rudderLabel.setText("Ruder");
        rudderLabel.setFocusable(false);

        buttonSendCommands.setText("Steuerbefehle senden");
        buttonSendCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendCommandsActionPerformed(evt);
            }
        });

        menuFile.setText("Datei");

        menuFile_Close.setText("Beenden");
        menuFile_Close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFile_CloseActionPerformed(evt);
            }
        });
        menuFile.add(menuFile_Close);

        rC_MenuBar.add(menuFile);

        menuSettings.setText("Einstellungen");

        menuSettings_ResetMotor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        menuSettings_ResetMotor.setText("Motor nullstellen");
        menuSettings_ResetMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettings_ResetMotorActionPerformed(evt);
            }
        });
        menuSettings.add(menuSettings_ResetMotor);

        menuSettings_ResetSail.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        menuSettings_ResetSail.setText("Segel nullstellen");
        menuSettings_ResetSail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettings_ResetSailActionPerformed(evt);
            }
        });
        menuSettings.add(menuSettings_ResetSail);

        menuSettings_ResetRudder.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        menuSettings_ResetRudder.setText("Ruder nullstellen");
        menuSettings_ResetRudder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettings_ResetRudderActionPerformed(evt);
            }
        });
        menuSettings.add(menuSettings_ResetRudder);

        menuSettings_AutoSendCommands.setSelected(true);
        menuSettings_AutoSendCommands.setText("Steuerbefehle automatisch senden");
        menuSettings_AutoSendCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettings_AutoSendCommandsActionPerformed(evt);
            }
        });
        menuSettings.add(menuSettings_AutoSendCommands);
        menuSettings.add(menuSettings_Seperator);

        menuSettings_SendCommands.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        menuSettings_SendCommands.setText("Steuerbefehle senden");
        menuSettings_SendCommands.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSettings_SendCommandsActionPerformed(evt);
            }
        });
        menuSettings.add(menuSettings_SendCommands);

        rC_MenuBar.add(menuSettings);

        setJMenuBar(rC_MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(propellorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sailLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(propellorText, javax.swing.GroupLayout.DEFAULT_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sailText, javax.swing.GroupLayout.DEFAULT_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(propellorSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rudderText, javax.swing.GroupLayout.DEFAULT_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(82, 82, 82))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(rudderLabel)
                                        .addGap(90, 90, 90))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rudderSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonSendCommands, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(sailSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propellorLabel)
                    .addComponent(sailLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(propellorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(propellorSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sailSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonSendCommands)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rudderLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rudderText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rudderSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void menuSettings_ResetMotorActionPerformed(java.awt.event.ActionEvent evt) {
        propellorSlider.setValue(0);
        propellorText.setText(convertToNativeValue(propellorSlider.getValue(), PROPELLOR_MIN, PROPELLOR_NULL, PROPELLOR_MAX) + "");
    }

    private void menuSettings_ResetSailActionPerformed(java.awt.event.ActionEvent evt) {
        sailSlider.setValue(0);
        sailText.setText(convertToNativeValue(sailSlider.getValue(), SAIL_MIN, SAIL_NULL, SAIL_MAX) + "");
    }

    private void menuSettings_ResetRudderActionPerformed(java.awt.event.ActionEvent evt) {
        rudderSlider.setValue(0);
        rudderText.setText(convertToNativeValue(rudderSlider.getValue(), RUDDER_MIN, RUDDER_NULL, RUDDER_MAX) + "");
    }

    private void propellorUpdated(javax.swing.event.ChangeEvent evt) {
        //propellorText.setText(propellorSlider.getValue() + "");
        propellorText.setText(convertToNativeValue(propellorSlider.getValue(), PROPELLOR_MIN, PROPELLOR_NULL, PROPELLOR_MAX) + "");
    }

    private void sailUpdated(javax.swing.event.ChangeEvent evt) {
        //sailText.setText(sailSlider.getValue() + "");
        sailText.setText(convertToNativeValue(sailSlider.getValue(), SAIL_MIN, SAIL_NULL, SAIL_MAX) + "");
    }

    private void rudderUpdated(javax.swing.event.ChangeEvent evt) {
        //rudderText.setText(rudderSlider.getValue() + "");
        rudderText.setText(convertToNativeValue(rudderSlider.getValue(), RUDDER_MIN, RUDDER_NULL, RUDDER_MAX) + "");
    }

    private void menuFile_CloseActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void menuSettings_AutoSendCommandsActionPerformed(java.awt.event.ActionEvent evt) {

    }

    private void menuSettings_SendCommandsActionPerformed(java.awt.event.ActionEvent evt) {
        sendCommands();
    }

    private void buttonSendCommandsActionPerformed(java.awt.event.ActionEvent evt) {
        sendCommands();
    }

    private void propellorSliderReleased(java.awt.event.MouseEvent evt) {
        if (menuSettings_AutoSendCommands.isSelected()) sendPropellorCommand(Integer.parseInt(propellorText.getText()));
    }

    private void sailSliderReleased(java.awt.event.MouseEvent evt) {
        if (menuSettings_AutoSendCommands.isSelected()) sendSailCommand(Integer.parseInt(sailText.getText()));
    }

    private void rudderSliderReleased(java.awt.event.MouseEvent evt) {
        if (menuSettings_AutoSendCommands.isSelected()) sendRudderCommand(Integer.parseInt(rudderText.getText()));
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RemoteControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RemoteControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RemoteControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RemoteControl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new RemoteControl().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton buttonSendCommands;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem menuFile_Close;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JCheckBoxMenuItem menuSettings_AutoSendCommands;
    private javax.swing.JMenuItem menuSettings_ResetMotor;
    private javax.swing.JMenuItem menuSettings_ResetRudder;
    private javax.swing.JMenuItem menuSettings_ResetSail;
    private javax.swing.JMenuItem menuSettings_SendCommands;
    private javax.swing.JPopupMenu.Separator menuSettings_Seperator;
    private javax.swing.JLabel propellorLabel;
    private javax.swing.JSlider propellorSlider;
    private javax.swing.JTextField propellorText;
    private javax.swing.JMenuBar rC_MenuBar;
    private javax.swing.JLabel rudderLabel;
    private javax.swing.JSlider rudderSlider;
    private javax.swing.JTextField rudderText;
    private javax.swing.JLabel sailLabel;
    private javax.swing.JSlider sailSlider;
    private javax.swing.JTextField sailText;
    // End of variables declaration
}
