package de.fhb.sairo.main;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

public class waitingDialog extends JDialog {

	public waitingDialog() {
		this.setTitle("Please wait ...");
		this.setSize(200, 200);
		this.setModal(true);
		this.setVisible(true);
	}
	
	public void disposeDialog(){
		this.setVisible(false);
		this.dispose();
	}
}
