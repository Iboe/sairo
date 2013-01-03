/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fhb.sailboat.gui;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import de.fhb.sailboat.missionplayer.Player;


/**
 *
 * @author Andy Klay
 * Dialag represents the Player-control-panel
 */
public class PlayerDialog extends RootDialog{
	
	private  GUIController controller;

	/**
	 * Creates new form NewJDialog
	 */
	public PlayerDialog(java.awt.Frame parent, boolean modal, GUIController controller) {
		super(parent, modal);
		initComponents();
		setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.controller=controller;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        speedSilder = new javax.swing.JSlider();
        speedSilder.setMaximum(20);
        speedSilder.setMaximum(200);
        
        
        openButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        loadTestButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });

        speedSilder.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSilderStateChanged(evt);
            }
        });

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Playing-Speed");

        loadTestButton.setText("loadTest");
        loadTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTestButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(speedSilder, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addComponent(openButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(playButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pauseButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(stopButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(loadTestButton))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(speedSilder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playButton)
                    .addComponent(pauseButton)
                    .addComponent(openButton)
                    .addComponent(stopButton)
                    .addComponent(loadTestButton))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void loadTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTestButtonActionPerformed
		this.controller.startPlayer("TestSave.sem");
	}//GEN-LAST:event_loadTestButtonActionPerformed

	private void speedSilderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSilderStateChanged
		this.controller.setPlayingSpeed(speedSilder.getValue());
	}//GEN-LAST:event_speedSilderStateChanged

	private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.controller.playPlayer();
	}

	private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
		JFileChooser fileChooser;	
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed( false );
		
		FileFilter emulationFileFilter = new FileFilter() {
			@Override
			public boolean accept(File filePath) {
				String name = filePath.getName().toLowerCase();
				if (filePath.isDirectory())
					return true;
				if (name.endsWith("." + Player.EMULATION_FILES))
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				return  "." + Player.EMULATION_FILES;
			}
		};
		
		fileChooser.setFileFilter(emulationFileFilter);
		if ( fileChooser.showOpenDialog( this ) == JFileChooser.APPROVE_OPTION ) {
			if ( fileChooser.getSelectedFile().toString()  != null ) {
				this.controller.startPlayer(fileChooser.getSelectedFile().toString());
			}
		}	
	}

	private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {
		this.controller.pausePlaying();
	}

	private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
		this.controller.stopPlaying();
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loadTestButton;
    private javax.swing.JButton openButton;
    private javax.swing.JButton pauseButton;
    private javax.swing.JButton playButton;
    private javax.swing.JSlider speedSilder;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
