package simplyamazing.data;

import java.util.ArrayList;
import java.util.Collections;

import simplyamazing.data.Task;

public class TaskList {
	
	private static final String CHARACTER_SPACE = " ";
	private static final int SIZE_EMPTY = 0;
	
	private ArrayList<Task> tasks;
	
	public TaskList() {
		tasks = new ArrayList<Task>();
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void resetTaskList() {
		tasks = new ArrayList<Task>();;
	}

	public void createTaskList(ArrayList<String> lines) throws Exception {
		for (int i = 0; i < lines.size(); i++) {
			String[] fields = lines.get(i).split(Task.FIELD_SEPARATOR);
			
			String description = fields[Task.ARRAY_POSITION_FOR_DESCRIPTION];
			String startTimeString = fields[Task.ARRAY_POSITION_FOR_START_TIME];
			String endTimeString = fields[Task.ARRAY_POSITION_FOR_END_TIME];
			String priorityLevel = fields[Task.ARRAY_POSITION_FOR_PRIORITY];
			String status = fields[Task.ARRAY_POSITION_FOR_STATUS];
			
			Task task = new Task();
			if (!description.matches(CHARACTER_SPACE)) {
				task.setDescription(description);
			}
			if (!startTimeString.matches(CHARACTER_SPACE)) {
				task.setStartTime(startTimeString);
			}
			if (!endTimeString.matches(CHARACTER_SPACE)) {
				task.setEndTime(endTimeString);
			}
			if (!priorityLevel.matches(CHARACTER_SPACE)) {
				task.setPriority(priorityLevel);
			}
			if (!status.matches(CHARACTER_SPACE)) {
				task.setDone(true);
			}
			tasks.add(task);
		}
	}
	
	public void updateTaskList(ArrayList<String> lines) throws Exception {
		if(tasks.size() == SIZE_EMPTY) {
			createTaskList(lines);
		}
	}
	
	public void addTaskToList(Task task) throws Exception {
		tasks.add(task);
		Collections.sort(tasks);
	}
	
	public void removeTaskFromList(Task task) throws Exception {
		tasks.remove(task);
		Collections.sort(tasks);
	}
}
