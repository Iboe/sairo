package de.fhb.sairo.data;

import java.util.ArrayList;

import de.fhb.sairo.data.Task.Task;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public class TaskList extends ArrayList<Task>{

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<this.size();i++){
			sb.append(this.get(i).toString());
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
}
