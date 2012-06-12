package de.fhb.sailboat.gui;

import de.fhb.sailboat.control.Planner;

/**
 * SlimGUI for an autonomous sailboat. Requires a compliant GUILogic class to be
 * assigned in constructor as well as a Planner.
 *
 * @author Patrick Rutter
 */
public class GUInterface extends javax.swing.JFrame {

    private GUILogic guiLogic;

    /**
     * Initializes the GUInterface form, connects to a chosen Planner and
     * prepares the MissionMap.
     *
     * @param planner A Planner-Instance which Missions are committed to.
     */
    public GUInterface(Planner planner) {
        initComponents();
        guiLogic = new GUILogicImpl(planner);           // GUILogic classes may be switched here
        guiLogic.initializeMissionMap(missionMapPanel); // initialize the mission map for display
        guiLoop();
    }

    @SuppressWarnings("unchecked")
    /**
     * Initializes the form.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        infoDialog = new javax.swing.JDialog();
        infoTabPlane = new javax.swing.JTabbedPane();
        systemTabPanel = new javax.swing.JPanel();
        systemScrollPane = new javax.swing.JScrollPane();
        systemTabTextArea = new javax.swing.JTextArea();
        missionTabPanel = new javax.swing.JPanel();
        missionScrollPanel = new javax.swing.JScrollPane();
        missionTabTextArea = new javax.swing.JTextArea();
        infoDialogMenuBar = new javax.swing.JMenuBar();
        infoDialogFileMenu = new javax.swing.JMenu();
        infoDialogMenuAutoScroll = new javax.swing.JCheckBoxMenuItem();
        infoDialogMenuClose = new javax.swing.JMenuItem();
        remoteDialog = new javax.swing.JDialog();
        sailPanel = new javax.swing.JPanel();
        sailSlider = new javax.swing.JSlider();
        sailLabelRight = new javax.swing.JLabel();
        sailLabelLeft = new javax.swing.JLabel();
        rudderPanel = new javax.swing.JPanel();
        rudderSlider = new javax.swing.JSlider();
        rudderLabelRight = new javax.swing.JLabel();
        rudderLabelLeft = new javax.swing.JLabel();
        propellorPanel = new javax.swing.JPanel();
        propellorMinRadioButton = new javax.swing.JRadioButton();
        propellorNullRadioButton = new javax.swing.JRadioButton();
        propellorMaxRadioButton = new javax.swing.JRadioButton();
        propellorRadioGroup = new javax.swing.ButtonGroup();
        gpsPanel = new javax.swing.JPanel();
        gpsLongitudeLabel = new javax.swing.JLabel();
        gpsLongitudeDisplayLabel = new javax.swing.JLabel();
        gpsLatitudeLabel = new javax.swing.JLabel();
        gpsLatitudeDisplayLabel = new javax.swing.JLabel();
        gpsSatelitesLabel = new javax.swing.JLabel();
        gpsSatelitesDisplayLabel = new javax.swing.JLabel();
        compassPanel = new javax.swing.JPanel();
        compassAzimuthLabel = new javax.swing.JLabel();
        compassAzimuthDisplayLabel = new javax.swing.JLabel();
        compassPitchLabel = new javax.swing.JLabel();
        compassPitchDisplayLabel = new javax.swing.JLabel();
        compassRollLabel = new javax.swing.JLabel();
        compassRollDisplayLabel = new javax.swing.JLabel();
        windPanel = new javax.swing.JPanel();
        windVelocityLabel = new javax.swing.JLabel();
        windVelocityDisplayLabel = new javax.swing.JLabel();
        windDirectionLabel = new javax.swing.JLabel();
        windDirectionDisplayLabel = new javax.swing.JLabel();
        missionMapPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        fileMenuClose = new javax.swing.JMenuItem();
        windowMenu = new javax.swing.JMenu();
        windowMenuInfo = new javax.swing.JCheckBoxMenuItem();
        windowMenuRemote = new javax.swing.JCheckBoxMenuItem();
        missionTestMenu = new javax.swing.JMenu();
        missionTestMenuReachCircle = new javax.swing.JMenuItem();
        missionTestMenuReachPolygon = new javax.swing.JMenuItem();
        missionTestMenuCompassCourse = new javax.swing.JMenuItem();
        missionTestMenuHoldAngleToWind = new javax.swing.JMenuItem();
        missionTestMenuStopTask = new javax.swing.JMenuItem();
        missionTestMenuResetActors = new javax.swing.JMenuItem();
        missionTestMenuResetMap = new javax.swing.JMenuItem();
        missionTestMenuSailMode = new javax.swing.JCheckBoxMenuItem();

        infoDialog.setTitle("SaiilboatGUI Info");
        infoDialog.setMinimumSize(new java.awt.Dimension(405, 305));
        infoDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                infoDialogWindowClosing(evt);
            }
        });

        systemScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        systemScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        systemTabTextArea.setColumns(20);
        systemTabTextArea.setEditable(false);
        systemTabTextArea.setRows(5);
        systemScrollPane.setViewportView(systemTabTextArea);

        javax.swing.GroupLayout systemTabPanelLayout = new javax.swing.GroupLayout(systemTabPanel);
        systemTabPanel.setLayout(systemTabPanelLayout);
        systemTabPanelLayout.setHorizontalGroup(
            systemTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(systemScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        systemTabPanelLayout.setVerticalGroup(
            systemTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(systemScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        infoTabPlane.addTab("System", systemTabPanel);

        missionScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        missionScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        missionTabTextArea.setColumns(20);
        missionTabTextArea.setEditable(false);
        missionTabTextArea.setRows(5);
        missionScrollPanel.setViewportView(missionTabTextArea);

        javax.swing.GroupLayout missionTabPanelLayout = new javax.swing.GroupLayout(missionTabPanel);
        missionTabPanel.setLayout(missionTabPanelLayout);
        missionTabPanelLayout.setHorizontalGroup(
            missionTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(missionScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
        );
        missionTabPanelLayout.setVerticalGroup(
            missionTabPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(missionScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
        );

        infoTabPlane.addTab("Mission", missionTabPanel);

        infoDialogFileMenu.setText("Fenster");

        infoDialogMenuAutoScroll.setText("AutoScroll");
        infoDialogFileMenu.add(infoDialogMenuAutoScroll);

        infoDialogMenuClose.setText("Schlie�en");
        infoDialogMenuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoDialogMenuCloseActionPerformed(evt);
            }
        });
        infoDialogFileMenu.add(infoDialogMenuClose);

        infoDialogMenuBar.add(infoDialogFileMenu);

        infoDialog.setJMenuBar(infoDialogMenuBar);

        javax.swing.GroupLayout infoDialogLayout = new javax.swing.GroupLayout(infoDialog.getContentPane());
        infoDialog.getContentPane().setLayout(infoDialogLayout);
        infoDialogLayout.setHorizontalGroup(
            infoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoTabPlane)
                .addContainerGap())
        );
        infoDialogLayout.setVerticalGroup(
            infoDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoTabPlane)
                .addContainerGap())
        );

        remoteDialog.setTitle("Fernsteuerung");
        remoteDialog.setMinimumSize(new java.awt.Dimension(355, 240));
        remoteDialog.setPreferredSize(new java.awt.Dimension(355, 240));
        remoteDialog.setResizable(false);
        remoteDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                remoteDialogWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                remoteDialogWindowClosing(evt);
            }
        });

        sailPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Segel", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        sailSlider.setMajorTickSpacing(25);
        sailSlider.setMinimum(-100);
        sailSlider.setMinorTickSpacing(1);
        sailSlider.setSnapToTicks(true);
        sailSlider.setValue(0);
        sailSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sailStateChanged(evt);
            }
        });

        sailLabelRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sailLabelRight.setText("Au�en");
        sailLabelRight.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        sailLabelLeft.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sailLabelLeft.setText("Innen");
        sailLabelLeft.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout sailPanelLayout = new javax.swing.GroupLayout(sailPanel);
        sailPanel.setLayout(sailPanelLayout);
        sailPanelLayout.setHorizontalGroup(
            sailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sailPanelLayout.createSequentialGroup()
                .addComponent(sailLabelLeft)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sailLabelRight))
            .addComponent(sailSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        sailPanelLayout.setVerticalGroup(
            sailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sailPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(sailPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sailLabelLeft)
                    .addComponent(sailLabelRight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sailSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        rudderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ruder", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        rudderSlider.setMajorTickSpacing(25);
        rudderSlider.setMinimum(-100);
        rudderSlider.setMinorTickSpacing(1);
        rudderSlider.setSnapToTicks(true);
        rudderSlider.setValue(0);
        rudderSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rudderStateChanged(evt);
            }
        });

        rudderLabelRight.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rudderLabelRight.setText("Rechts");
        rudderLabelRight.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        rudderLabelLeft.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rudderLabelLeft.setText("Links");
        rudderLabelLeft.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout rudderPanelLayout = new javax.swing.GroupLayout(rudderPanel);
        rudderPanel.setLayout(rudderPanelLayout);
        rudderPanelLayout.setHorizontalGroup(
            rudderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rudderPanelLayout.createSequentialGroup()
                .addComponent(rudderLabelLeft)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rudderLabelRight))
            .addComponent(rudderSlider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        rudderPanelLayout.setVerticalGroup(
            rudderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rudderPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(rudderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rudderLabelLeft)
                    .addComponent(rudderLabelRight))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rudderSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        propellorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Motor", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        propellorRadioGroup.add(propellorMinRadioButton);
        propellorMinRadioButton.setText("Volle Fahrt zur�ck");
        propellorMinRadioButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        propellorMinRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                propellorMinStateChanged(evt);
            }
        });

        propellorRadioGroup.add(propellorNullRadioButton);
        propellorNullRadioButton.setText("Aus");
        propellorNullRadioButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        propellorNullRadioButton.setMaximumSize(new java.awt.Dimension(107, 23));
        propellorNullRadioButton.setMinimumSize(new java.awt.Dimension(107, 23));
        propellorNullRadioButton.setPreferredSize(new java.awt.Dimension(107, 23));
        propellorNullRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                propellorNullStateChanged(evt);
            }
        });

        propellorRadioGroup.add(propellorMaxRadioButton);
        propellorMaxRadioButton.setText("Volle Fahrt vor");
        propellorMaxRadioButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        propellorMaxRadioButton.setMaximumSize(new java.awt.Dimension(107, 23));
        propellorMaxRadioButton.setMinimumSize(new java.awt.Dimension(107, 23));
        propellorMaxRadioButton.setPreferredSize(new java.awt.Dimension(107, 23));
        propellorMaxRadioButton.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                propellorMaxStateChanged(evt);
            }
        });

        javax.swing.GroupLayout propellorPanelLayout = new javax.swing.GroupLayout(propellorPanel);
        propellorPanel.setLayout(propellorPanelLayout);
        propellorPanelLayout.setHorizontalGroup(
            propellorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propellorPanelLayout.createSequentialGroup()
                .addComponent(propellorMinRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(propellorNullRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(propellorMaxRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
        );
        propellorPanelLayout.setVerticalGroup(
            propellorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(propellorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(propellorMinRadioButton)
                .addComponent(propellorNullRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(propellorMaxRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout remoteDialogLayout = new javax.swing.GroupLayout(remoteDialog.getContentPane());
        remoteDialog.getContentPane().setLayout(remoteDialogLayout);
        remoteDialogLayout.setHorizontalGroup(
            remoteDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rudderPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(propellorPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(sailPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        remoteDialogLayout.setVerticalGroup(
            remoteDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, remoteDialogLayout.createSequentialGroup()
                .addComponent(propellorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sailPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rudderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SailboatGUI");
        setMinimumSize(new java.awt.Dimension(600, 500));
        setName("sailboatGUI");

        gpsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("GPS"));
        gpsPanel.setName("");
        gpsPanel.setPreferredSize(new java.awt.Dimension(200, 80));

        gpsLongitudeLabel.setText("Longitude:");
        gpsLongitudeLabel.setFocusable(false);
        gpsLongitudeLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsLongitudeLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsLongitudeLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        gpsLongitudeDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gpsLongitudeDisplayLabel.setText("00.000000");
        gpsLongitudeDisplayLabel.setFocusable(false);
        gpsLongitudeDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsLongitudeDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsLongitudeDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        gpsLatitudeLabel.setText("Latitude:");
        gpsLatitudeLabel.setFocusable(false);
        gpsLatitudeLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsLatitudeLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsLatitudeLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        gpsLatitudeDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gpsLatitudeDisplayLabel.setText("00.000000");
        gpsLatitudeDisplayLabel.setFocusable(false);
        gpsLatitudeDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsLatitudeDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsLatitudeDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        gpsSatelitesLabel.setText("Sateliten:");
        gpsSatelitesLabel.setFocusable(false);
        gpsSatelitesLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsSatelitesLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsSatelitesLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        gpsSatelitesDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gpsSatelitesDisplayLabel.setText("0");
        gpsSatelitesDisplayLabel.setFocusable(false);
        gpsSatelitesDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        gpsSatelitesDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        gpsSatelitesDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        javax.swing.GroupLayout gpsPanelLayout = new javax.swing.GroupLayout(gpsPanel);
        gpsPanel.setLayout(gpsPanelLayout);
        gpsPanelLayout.setHorizontalGroup(
            gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gpsPanelLayout.createSequentialGroup()
                .addGroup(gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gpsPanelLayout.createSequentialGroup()
                        .addComponent(gpsLongitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gpsLongitudeDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gpsPanelLayout.createSequentialGroup()
                        .addComponent(gpsLatitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gpsLatitudeDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gpsPanelLayout.createSequentialGroup()
                        .addComponent(gpsSatelitesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(gpsSatelitesDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        gpsPanelLayout.setVerticalGroup(
            gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gpsPanelLayout.createSequentialGroup()
                .addGroup(gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpsLongitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gpsLongitudeDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpsLatitudeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gpsLatitudeDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(gpsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gpsSatelitesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gpsSatelitesDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        compassPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Kompass"));
        compassPanel.setName("");
        compassPanel.setPreferredSize(new java.awt.Dimension(200, 80));

        compassAzimuthLabel.setText("Azimuth:");
        compassAzimuthLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassAzimuthLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassAzimuthLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        compassAzimuthDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        compassAzimuthDisplayLabel.setText("0�");
        compassAzimuthDisplayLabel.setFocusable(false);
        compassAzimuthDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassAzimuthDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassAzimuthDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        compassPitchLabel.setText("Pitch:");
        compassPitchLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassPitchLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassPitchLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        compassPitchDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        compassPitchDisplayLabel.setText("0�");
        compassPitchDisplayLabel.setFocusable(false);
        compassPitchDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassPitchDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassPitchDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        compassRollLabel.setText("Roll:");
        compassRollLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassRollLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassRollLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        compassRollDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        compassRollDisplayLabel.setText("0�");
        compassRollDisplayLabel.setFocusable(false);
        compassRollDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        compassRollDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        compassRollDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        javax.swing.GroupLayout compassPanelLayout = new javax.swing.GroupLayout(compassPanel);
        compassPanel.setLayout(compassPanelLayout);
        compassPanelLayout.setHorizontalGroup(
            compassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compassPanelLayout.createSequentialGroup()
                .addComponent(compassAzimuthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compassAzimuthDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(compassPanelLayout.createSequentialGroup()
                .addComponent(compassPitchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compassPitchDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(compassPanelLayout.createSequentialGroup()
                .addComponent(compassRollLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(compassRollDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        compassPanelLayout.setVerticalGroup(
            compassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(compassPanelLayout.createSequentialGroup()
                .addGroup(compassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compassAzimuthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compassAzimuthDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(compassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compassPitchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compassPitchDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(compassPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(compassRollLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compassRollDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        windPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Wind"));
        windPanel.setName("");
        windPanel.setPreferredSize(new java.awt.Dimension(200, 80));

        windVelocityLabel.setText("Geschwind.:");
        windVelocityLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        windVelocityLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        windVelocityLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        windVelocityDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        windVelocityDisplayLabel.setText("0 m/s");
        windVelocityDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        windVelocityDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        windVelocityDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        windDirectionLabel.setText("Richtung:");
        windDirectionLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        windDirectionLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        windDirectionLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        windDirectionDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        windDirectionDisplayLabel.setText("0�");
        windDirectionDisplayLabel.setMaximumSize(new java.awt.Dimension(70, 14));
        windDirectionDisplayLabel.setMinimumSize(new java.awt.Dimension(70, 14));
        windDirectionDisplayLabel.setPreferredSize(new java.awt.Dimension(70, 14));

        javax.swing.GroupLayout windPanelLayout = new javax.swing.GroupLayout(windPanel);
        windPanel.setLayout(windPanelLayout);
        windPanelLayout.setHorizontalGroup(
            windPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(windPanelLayout.createSequentialGroup()
                .addComponent(windVelocityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(windVelocityDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(windPanelLayout.createSequentialGroup()
                .addComponent(windDirectionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(windDirectionDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        windPanelLayout.setVerticalGroup(
            windPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(windPanelLayout.createSequentialGroup()
                .addGroup(windPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(windVelocityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(windVelocityDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(windPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(windDirectionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(windDirectionDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        missionMapPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Missionskarte"));
        missionMapPanel.setName("");
        missionMapPanel.setPreferredSize(new java.awt.Dimension(435, 405));

        javax.swing.GroupLayout missionMapPanelLayout = new javax.swing.GroupLayout(missionMapPanel);
        missionMapPanel.setLayout(missionMapPanelLayout);
        missionMapPanelLayout.setHorizontalGroup(
            missionMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );
        missionMapPanelLayout.setVerticalGroup(
            missionMapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        fileMenu.setText("Datei");

        fileMenuClose.setText("Beenden");
        fileMenuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuCloseActionPerformed(evt);
            }
        });
        fileMenu.add(fileMenuClose);

        menuBar.add(fileMenu);

        windowMenu.setText("Fenster");

        windowMenuInfo.setText("Info Fenster");
        windowMenuInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowMenuInfoActionPerformed(evt);
            }
        });
        windowMenu.add(windowMenuInfo);

        windowMenuRemote.setText("Fernsteuerung");
        windowMenuRemote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowMenuRemoteActionPerformed(evt);
            }
        });
        windowMenu.add(windowMenuRemote);

        menuBar.add(windowMenu);

        missionTestMenu.setText("Testmissionen");

        missionTestMenuReachCircle.setText("ReachCircleTask(s) senden");
        missionTestMenuReachCircle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuReachCircleActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuReachCircle);

        missionTestMenuReachPolygon.setText("ReachPolygonTask senden");
        missionTestMenuReachPolygon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuReachPolygonActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuReachPolygon);

        missionTestMenuCompassCourse.setText("CompassCourseTask senden");
        missionTestMenuCompassCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuCompassCourseActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuCompassCourse);

        missionTestMenuHoldAngleToWind.setText("HoldAngleToWindTask senden");
        missionTestMenuHoldAngleToWind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuHoldAngleToWindActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuHoldAngleToWind);

        missionTestMenuStopTask.setText("StopTask senden");
        missionTestMenuStopTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuStopTaskActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuStopTask);

        missionTestMenuResetActors.setText("Aktoren zur�cksetzen");
        missionTestMenuResetActors.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuResetActorsActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuResetActors);

        missionTestMenuResetMap.setText("Karte zur�cksetzen");
        missionTestMenuResetMap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuResetMapActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuResetMap);

        missionTestMenuSailMode.setText("Segelmodus");
        missionTestMenuSailMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                missionTestMenuSailModeActionPerformed(evt);
            }
        });
        missionTestMenu.add(missionTestMenuSailMode);

        menuBar.add(missionTestMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gpsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(compassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(windPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missionMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gpsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(compassPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(windPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 228, Short.MAX_VALUE))
                    .addComponent(missionMapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void fileMenuCloseActionPerformed(java.awt.event.ActionEvent evt) {                                              
        guiLogic.closeGUI();
    }                                             

    private void windowMenuInfoActionPerformed(java.awt.event.ActionEvent evt) {                                               
        infoDialog.setVisible(windowMenuInfo.isSelected());
    }                                              

    private void infoDialogWindowClosing(java.awt.event.WindowEvent evt) {                                         
        windowMenuInfo.setSelected(false);
    }                                        

    private void infoDialogMenuCloseActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        windowMenuInfo.setSelected(false);
        infoDialog.setVisible(false);
    }                                                   

    private void windowMenuRemoteActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        remoteDialog.setVisible(windowMenuRemote.isSelected());
    }                                                

    private void missionTestMenuReachCircleActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        guiLogic.sendCircleMarkers();
    }                                                          

    private void missionTestMenuReachPolygonActionPerformed(java.awt.event.ActionEvent evt) {                                                            
        guiLogic.sendPolyMapMarkers();
    }                                                           

    private void missionTestMenuCompassCourseActionPerformed(java.awt.event.ActionEvent evt) {                                                             
        guiLogic.sendReachCompass();
    }                                                            

    private void missionTestMenuHoldAngleToWindActionPerformed(java.awt.event.ActionEvent evt) {                                                               
        guiLogic.sendHoldAngleToWind();
    }                                                              

    private void missionTestMenuStopTaskActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        guiLogic.sendStop();
    }                                                       

    private void missionTestMenuResetActorsActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        guiLogic.sendResetActors();
    }                                                          

    private void missionTestMenuSailModeActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        guiLogic.setSailMode(missionTestMenuSailMode.isSelected());
    }                                                       

    private void missionTestMenuResetMapActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        guiLogic.sendResetMissionMap();
    }                                                       

    private void remoteDialogWindowClosing(java.awt.event.WindowEvent evt) {                                           
        windowMenuRemote.setSelected(false);
    }                                          

    private void remoteDialogWindowActivated(java.awt.event.WindowEvent evt) {                                             
        int propellor = guiLogic.getController().getPropellor();

        if (propellor > GUIControllerImpl.PROPELLOR_NORMAL) {
            propellorMinRadioButton.setSelected(true);
        } else if (propellor == GUIControllerImpl.PROPELLOR_NORMAL) {
            propellorNullRadioButton.setSelected(true);
        } else {
            propellorMaxRadioButton.setSelected(true);
        }
        
        int rudder = guiLogic.getController().getRudder();
        rudderSlider.setValue(convertFromNativeValue(rudder, GUIControllerImpl.RUDDER_LEFT, GUIControllerImpl.RUDDER_NORMAL, GUIControllerImpl.RUDDER_RIGHT));
        
        int sail = guiLogic.getController().getSail();
        sailSlider.setValue(convertFromNativeValue(sail, GUIControllerImpl.SAIL_IN, GUIControllerImpl.SAIL_NORMAL, GUIControllerImpl.SAIL_OUT));
    }                                            

    private void sailStateChanged(javax.swing.event.ChangeEvent evt) {
        this.guiLogic.getController().commitPrimitiveCommand(guiLogic.getPlanner(), null, null, convertToNativeValue(sailSlider.getValue(), GUIControllerImpl.SAIL_IN, GUIControllerImpl.SAIL_NORMAL, GUIControllerImpl.SAIL_OUT));
    }

    private void rudderStateChanged(javax.swing.event.ChangeEvent evt) {
        this.guiLogic.getController().commitPrimitiveCommand(guiLogic.getPlanner(), null, convertToNativeValue(rudderSlider.getValue(), GUIControllerImpl.RUDDER_LEFT, GUIControllerImpl.RUDDER_NORMAL, GUIControllerImpl.RUDDER_RIGHT), null);
    }

    private void propellorMinStateChanged(javax.swing.event.ChangeEvent evt) {
        propellorStateUpdate();
    }

    private void propellorNullStateChanged(javax.swing.event.ChangeEvent evt) {
        propellorStateUpdate();
    }

    private void propellorMaxStateChanged(javax.swing.event.ChangeEvent evt) {
        propellorStateUpdate();
    }

    private void propellorStateUpdate() {
        if (propellorMinRadioButton.isSelected()) {
            this.guiLogic.getController().commitPrimitiveCommand(guiLogic.getPlanner(), GUIControllerImpl.PROPELLOR_MIN, null, null);
        }
        else if (propellorNullRadioButton.isSelected()) {
            this.guiLogic.getController().commitPrimitiveCommand(guiLogic.getPlanner(), GUIControllerImpl.PROPELLOR_NORMAL, null, null);
        }
        else {
            this.guiLogic.getController().commitPrimitiveCommand(guiLogic.getPlanner(), GUIControllerImpl.PROPELLOR_MAX, null, null);
        }
    }
    
    /**
     * Converts a given value from -100 to +100 into the target range. Created for
     * converting the range of the slider values to a concrete value to be used
     * as instruction value for the planner. Requires of the values to be
     * following a general trend (min < nul < max).
     *
     * @param value to be converted
     * @param min minimum target value (equals -100 slider)
     * @param nul neutral target value (equals 0 slider)
     * @param max maximum target value (equals +100 slider)
     * @return converted value
     */
    private int convertToNativeValue(int value, int min, int nul, int max) {
        int myReturn;

        if (value == 0) {
            myReturn = nul;
        } else {
            int nativeTick;
            if (value > 0) {
                nativeTick = (max - nul);
            } else {
                nativeTick = (nul - min);
            }
            myReturn = ((value * nativeTick) / 100) + nul;
        }

        return myReturn;
    }
    
    /**
     * Works like  convertToNativeValue with the only difference that a native
     * actor value is mapped to the slider range from -100 (min) till 100 (max).
     * 
     * @param to be converted
     * @param min minimum target value (equals -100 slider)
     * @param nul neutral target value (equals 0 slider)
     * @param max maximum target value (equals +100 slider)
     * @return converted value
     */
    private int convertFromNativeValue(int value, int min, int nul, int max) {
        int myReturn = 0;
        
        if (value < nul) {
            int range = nul - min;
            value = value - range;
            myReturn = (int)((100.0 / range) * value);
            myReturn = myReturn - 100;
        }
        else if (value > nul) {
            int range = max - nul;
            value = value - nul;
            myReturn = (int)((100.0 / range) * value);
        }
        
        return myReturn;
    }

    /**
     * Creates and starts up a thread which updates the values to be displayed
     * by the interface. Utilizes the chosen guiLogic class. Tasks beyond mere
     * updating/ assigning values should be delegated to updateLogic() of the
     * GUILogic.
     */
    private void guiLoop() {
        Runnable updater = new Runnable() {

            public void run() {
                
                while (true) {
                    // update logic
                    guiLogic.updateLogic();

                    // update display labels with current values present
                    guiLogic.updateCompassAzimuth(compassAzimuthDisplayLabel);
                    guiLogic.updateCompassPitch(compassPitchDisplayLabel);
                    guiLogic.updateCompassRoll(compassRollDisplayLabel);
                    guiLogic.updateGPSLongitude(gpsLongitudeDisplayLabel);
                    guiLogic.updateGPSLatitude(gpsLatitudeDisplayLabel);
                    guiLogic.updateGPSSatelites(gpsSatelitesDisplayLabel);
                    guiLogic.updateWindDirection(windDirectionDisplayLabel);
                    guiLogic.updateWindVelocity(windVelocityDisplayLabel);

                    // update infoDialog
                    guiLogic.updateSystemInfo(systemTabTextArea);
                    guiLogic.updateMissionInfo(missionTabTextArea);

                    // "autoScroll" for infoDialog
                    if (infoDialogMenuAutoScroll.isSelected()) {
                        systemTabTextArea.setCaretPosition(systemTabTextArea.getDocument().getLength());
                        missionTabTextArea.setCaretPosition(missionTabTextArea.getDocument().getLength());
                    }

                    try {
                        Thread.sleep(guiLogic.UPDATE_RATE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(updater).start();
    }
    // Variables declaration - do not modify
    private javax.swing.JLabel compassAzimuthDisplayLabel;
    private javax.swing.JLabel compassAzimuthLabel;
    private javax.swing.JPanel compassPanel;
    private javax.swing.JLabel compassPitchDisplayLabel;
    private javax.swing.JLabel compassPitchLabel;
    private javax.swing.JLabel compassRollDisplayLabel;
    private javax.swing.JLabel compassRollLabel;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem fileMenuClose;
    private javax.swing.JLabel gpsLatitudeDisplayLabel;
    private javax.swing.JLabel gpsLatitudeLabel;
    private javax.swing.JLabel gpsLongitudeDisplayLabel;
    private javax.swing.JLabel gpsLongitudeLabel;
    private javax.swing.JPanel gpsPanel;
    private javax.swing.JLabel gpsSatelitesDisplayLabel;
    private javax.swing.JLabel gpsSatelitesLabel;
    private javax.swing.JDialog infoDialog;
    private javax.swing.JMenu infoDialogFileMenu;
    private javax.swing.JCheckBoxMenuItem infoDialogMenuAutoScroll;
    private javax.swing.JMenuBar infoDialogMenuBar;
    private javax.swing.JMenuItem infoDialogMenuClose;
    private javax.swing.JTabbedPane infoTabPlane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel missionMapPanel;
    private javax.swing.JScrollPane missionScrollPanel;
    private javax.swing.JPanel missionTabPanel;
    private javax.swing.JTextArea missionTabTextArea;
    private javax.swing.JMenu missionTestMenu;
    private javax.swing.JMenuItem missionTestMenuCompassCourse;
    private javax.swing.JMenuItem missionTestMenuHoldAngleToWind;
    private javax.swing.JMenuItem missionTestMenuReachCircle;
    private javax.swing.JMenuItem missionTestMenuReachPolygon;
    private javax.swing.JMenuItem missionTestMenuResetActors;
    private javax.swing.JMenuItem missionTestMenuResetMap;
    private javax.swing.JCheckBoxMenuItem missionTestMenuSailMode;
    private javax.swing.JMenuItem missionTestMenuStopTask;
    private javax.swing.JRadioButton propellorMaxRadioButton;
    private javax.swing.JRadioButton propellorMinRadioButton;
    private javax.swing.JRadioButton propellorNullRadioButton;
    private javax.swing.JPanel propellorPanel;
    private javax.swing.ButtonGroup propellorRadioGroup;
    private javax.swing.JDialog remoteDialog;
    private javax.swing.JLabel rudderLabelLeft;
    private javax.swing.JLabel rudderLabelRight;
    private javax.swing.JPanel rudderPanel;
    private javax.swing.JSlider rudderSlider;
    private javax.swing.JLabel sailLabelLeft;
    private javax.swing.JLabel sailLabelRight;
    private javax.swing.JPanel sailPanel;
    private javax.swing.JSlider sailSlider;
    private javax.swing.JScrollPane systemScrollPane;
    private javax.swing.JPanel systemTabPanel;
    private javax.swing.JTextArea systemTabTextArea;
    private javax.swing.JLabel windDirectionDisplayLabel;
    private javax.swing.JLabel windDirectionLabel;
    private javax.swing.JPanel windPanel;
    private javax.swing.JLabel windVelocityDisplayLabel;
    private javax.swing.JLabel windVelocityLabel;
    private javax.swing.JMenu windowMenu;
    private javax.swing.JCheckBoxMenuItem windowMenuInfo;
    private javax.swing.JCheckBoxMenuItem windowMenuRemote;
    // End of variables declaration
}
