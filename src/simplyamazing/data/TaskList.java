//@@author A0126289W
package simplyamazing.data;

import java.util.ArrayList;
import java.util.Arrays;
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
		assert (tasks.size() == 0);
	}

	public void resetCompletedTaskList() {
		completedTasks = new ArrayList<Task>();
		assert (completedTasks.size() == 0);
	}

	public void updateTaskList(ArrayList<String> lines, ArrayList<Task> taskList) throws Exception {
		if (taskList.size() == SIZE_EMPTY) {
			createTaskList(lines, taskList);
		}
	}

	/*
	 * This method converts the content of the file to an array list of tasks.
	 * The format is description,startTime,endTime,priority,status. If any of
	 * the fields is default value, it will be indicated by a space. Status is
	 * "done" for tasks marked as completed, "overdue" when its end time was
	 * already passed, and the rest regarded as "incomplete".
	 */
	public void createTaskList(ArrayList<String> lines, ArrayList<Task> taskList) throws Exception {
		for (int i = 0; i < lines.size(); i++) {
			String[] fields = lines.get(i).split(Task.FIELD_SEPARATOR);
			assert (fields.length == Task.NUM_FIELDS_STORED);

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
			if (status.matches(Task.STRING_STATUS_DONE)) {
				task.setDone(true);
			}
			addTaskToList(task, taskList);
		}
	}

	/*
	 * This method adds the given task to the given task list. After adding, it
	 * ensures tasks are sorted in order.
	 */
	public void addTaskToList(Task task, ArrayList<Task> taskList) throws Exception {
		int taskListSizeBeforeAdding = taskList.size();
		taskList.add(task);
		int taskListSizeAfterAdding = taskList.size();
		assert (taskListSizeAfterAdding == taskListSizeBeforeAdding + 1);
		Collections.sort(taskList);
	}

	/*
	 * This method removes the given task from either to-do list or completed
	 * task list. After removing, it ensures tasks are sorted in order.
	 */
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
		assert (taskListSizeAfterRemoving == taskListSizeBeforeRemoving - 1);
	}
}
