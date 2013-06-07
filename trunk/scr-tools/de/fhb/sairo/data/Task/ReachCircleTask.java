package de.fhb.sairo.data.Task;

public class ReachCircleTask extends Task {

	public ReachCircleTask(String pTaskDescription, String pTaskArguments) {
		super(pTaskDescription, pTaskArguments);
	}

	@Override
	public String toString(){
		return "ReachCircleTask: " + super.getTaskDescription();
	}
}
