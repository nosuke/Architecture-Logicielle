package ca.ulaval.glo4003.architecture_logicielle.model;

import java.util.List;
import java.util.ArrayList;

public class EmployeeEntry extends UserEntry {
	private List<TaskEntry> tasks = new ArrayList<TaskEntry>();
	
	public EmployeeEntry() {
		this.role = "EMPLOYEE";
	}
	
	public List<TaskEntry> getTasks() {
		return tasks;
	}
	
	public void assignTask(TaskEntry task) {
		if (tasks.contains(task) != true) {
			tasks.add(task);
		}
	}
	
	public void removeTask(TaskEntry task) {
		if (tasks.contains(task) == true) {
			tasks.remove(task);
		}
	}
	
	public void updateTasks(List<TaskEntry> tasks) {
		this.tasks = tasks;
	}
}