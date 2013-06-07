package de.fhb.sairo.data.Task;

import java.util.ArrayList;

public class PrimitiveCommandTask extends Task{

	private ArrayList<String> primitiveCommandTaskCommands;
	
	public PrimitiveCommandTask(String pTaskDescription, String pTaskArguments) {
		super(pTaskDescription, pTaskArguments);
		primitiveCommandTaskCommands = new ArrayList<String>();
		extractPrimitiveCommandTaskCommandsFromArguments();
	}

	private void extractPrimitiveCommandTaskCommandsFromArguments(){
		String[] tmp = this.getTaskArguments().split(",");
		for (int i=0;i<tmp.length;i++){
			primitiveCommandTaskCommands.add(tmp[i]);
		}
	}
	
	@Override
	public String toString(){
		return "PrimitiveCommandTask: " + super.getTaskDescription();
	}
}
