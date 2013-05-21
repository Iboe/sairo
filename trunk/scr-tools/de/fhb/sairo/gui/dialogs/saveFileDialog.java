package de.fhb.sairo.gui.dialogs;

import java.io.File;

import javax.swing.JFileChooser;

public class saveFileDialog{

	public static File openFileDialog(){
		File returnFile = null;
		JFileChooser fcChooser = new JFileChooser();
		int returnVal = fcChooser.showSaveDialog(null);
		if(returnVal==JFileChooser.APPROVE_OPTION){
			returnFile = fcChooser.getSelectedFile();
		}
		return returnFile;
	}
	
}
