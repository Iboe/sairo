package de.fhb.sairo.data.Task;

public class StopTask extends Task {

	public StopTask(String pTaskDescription, String pTaskArguments) {
		super(pTaskDescription, pTaskArguments);
	}

	@Override
	public String toString(){
		return "StopTask: " + super.getTaskDescription();
	}
}
