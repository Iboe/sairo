package de.fhb.sailboat.gui.missioncreator;

/**
 *
 * @author Patrick Rutter
 */
public class MissionCreatorInterface extends javax.swing.JDialog {

    /**
     * Creates new form MissionCreatorInterface
     */
    public MissionCreatorInterface(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        missionTreeScrollPane = new javax.swing.JScrollPane();
        missionTree = new javax.swing.JTree();
        CategoryLabel = new javax.swing.JLabel();
        CategoryComboBox = new javax.swing.JComboBox();
        TaskLabel = new javax.swing.JLabel();
        TaskComboBox = new javax.swing.JComboBox();
        mapPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        MissionMenu = new javax.swing.JMenu();
        MissionMenuNew = new javax.swing.JMenuItem();
        MissionMenuSave = new javax.swing.JMenuItem();
        MissionMenuLoad = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MissionCreator");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Mission");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tonne anfahren");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ReachCircleTask 1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("BreakCourseTask 1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ReachCircleTask 2");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tonne umsegeln");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("RoundCourseTask 1");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Zur Ziellinie fahren");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ReachLineTask 1");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        missionTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        missionTreeScrollPane.setViewportView(missionTree);

        CategoryLabel.setText("Kategorie");

        CategoryComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eins", "Zwei", "Drei", "Vier", "F�nf" }));

        TaskLabel.setText("Task");

        TaskComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Hund", "Katze", "Maus", "Kauz" }));

        mapPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Karte"));

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        MissionMenu.setText("Mission");

        MissionMenuNew.setText("Neue Mission");
        MissionMenuNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MissionMenuNewActionPerformed(evt);
            }
        });
        MissionMenu.add(MissionMenuNew);

        MissionMenuSave.setText("Speichern unter...");
        MissionMenuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MissionMenuSaveActionPerformed(evt);
            }
        });
        MissionMenu.add(MissionMenuSave);

        MissionMenuLoad.setText("Mission laden");
        MissionMenuLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MissionMenuLoadActionPerformed(evt);
            }
        });
        MissionMenu.add(MissionMenuLoad);

        menuBar.add(MissionMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(missionTreeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TaskLabel)
                    .addComponent(CategoryLabel)
                    .addComponent(TaskComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(missionTreeScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(CategoryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CategoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TaskLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TaskComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
            .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void MissionMenuSaveActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void MissionMenuNewActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void MissionMenuLoadActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(MissionCreatorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MissionCreatorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MissionCreatorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MissionCreatorInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                MissionCreatorInterface dialog = new MissionCreatorInterface(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JComboBox CategoryComboBox;
    private javax.swing.JLabel CategoryLabel;
    private javax.swing.JMenu MissionMenu;
    private javax.swing.JMenuItem MissionMenuLoad;
    private javax.swing.JMenuItem MissionMenuNew;
    private javax.swing.JMenuItem MissionMenuSave;
    private javax.swing.JComboBox TaskComboBox;
    private javax.swing.JLabel TaskLabel;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel mapPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTree missionTree;
    private javax.swing.JScrollPane missionTreeScrollPane;
    // End of variables declaration
}
