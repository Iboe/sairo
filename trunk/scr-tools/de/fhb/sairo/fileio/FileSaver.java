package de.fhb.sairo.fileio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileSaver {

	public static void saveAsTxtFile(String pFilename, ArrayList<String> pContent){
		try {
			FileWriter fWriter = new FileWriter(pFilename);
			for(int i=0;i<pContent.size();i++){
				fWriter.write(pContent.get(i)+System.getProperty("line.separator"));
			}
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
