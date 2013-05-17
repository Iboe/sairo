package de.fhb.sairo.fileio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class FileLoader {

	public static BufferedReader openLogfile(String pFilename){
		try {
			return new BufferedReader(new FileReader(new File(pFilename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
