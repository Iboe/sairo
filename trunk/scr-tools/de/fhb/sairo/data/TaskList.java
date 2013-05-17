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
		sb.append("[");
		for(int i=0;i<this.size();i++){
			sb.append(this.get(i).toString());
			if(i!=this.size()-1){sb.append(",");}
		}
		sb.append("]");
		return sb.toString();
	}
}
