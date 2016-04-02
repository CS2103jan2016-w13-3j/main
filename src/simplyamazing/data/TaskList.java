package simplyamazing.data;

import java.util.ArrayList;
import java.util.Collections;

import simplyamazing.data.Task;

public class TaskList {
	
	private static final String CHARACTER_SPACE = " ";
	private static final int SIZE_EMPTY = 0;
	
	private ArrayList<Task> tasks, completedTasks;
	
	public TaskList() {
		tasks = new ArrayList<Task>();
		completedTasks = new ArrayList<Task>();
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public ArrayList<Task> getCompletedTasks() {
		return completedTasks;
	}

	public void resetTaskList() {
		tasks = new ArrayList<Task>();
		assert(tasks.size()==0);
	}

	public void resetCompletedTaskList() {
		completedTasks = new ArrayList<Task>();
		assert(completedTasks.size()==0);
	}
	
	public void createTaskList(ArrayList<String> lines, ArrayList<Task> taskList) throws Exception {
		for (int i = 0; i < lines.size(); i++) {
			String[] fields = lines.get(i).split(Task.FIELD_SEPARATOR);
			assert(fields.length == 5);
			
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
			addTaskToList(task, taskList);
		}
	}
	
	public void updateTaskList(ArrayList<String> lines, ArrayList<Task> taskList) throws Exception {
		if (taskList.size() == SIZE_EMPTY) {
			createTaskList(lines, taskList);
		}
	}
	
	public void addTaskToList(Task task, ArrayList<Task> taskList) throws Exception {
		int taskListSizeBeforeAdding = taskList.size();
		taskList.add(task);
		int taskListSizeAfterAdding = taskList.size();
		assert(taskListSizeAfterAdding == taskListSizeBeforeAdding + 1);
		Collections.sort(taskList);
	}
	
	public void removeTaskFromList(Task task) throws Exception {
		int taskListSizeBeforeRemoving = 0, taskListSizeAfterRemoving = 0;
		
		if (tasks.contains(task)) {
			taskListSizeBeforeRemoving = tasks.size();
			tasks.remove(task);
			taskListSizeAfterRemoving = tasks.size();
			Collections.sort(tasks);
		} else {
			taskListSizeBeforeRemoving = completedTasks.size();
			completedTasks.remove(task);
			taskListSizeAfterRemoving = completedTasks.size();
			Collections.sort(completedTasks);
		}
		assert(taskListSizeAfterRemoving == taskListSizeBeforeRemoving - 1);
	}
}
