package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import simplyamazing.data.Task;

public class Storage {
	private static final String DIRECTORY_STORAGE = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	
	private static final String CHARACTER_SPACE = " ";
	private static final String STRING_EMPTY = "";
	
	private static final String STRING_TASK_TYPE_EVENT = "events";
	private static final String STRING_TASK_TYPE_DEADLINE = "deadlines";
	private static final String STRING_TASK_TYPE_FLOATING = "tasks";
	private static final String STRING_TASK_TYPE_OVERDUE = "overdue";
	private static final String STRING_TASK_TYPE_DONE = "done";

	private static final int SIZE_EMPTY = 0;
	private static final int INDEX_START_FOR_ARRAY = 0;
	
	private static final String MESSAGE_LOCATION_SET = "Storage location of task data is set sucessfully.";
	private static final String MESSAGE_LOCATION_NOT_SET = "Storage location of task data is not set yet.\nPlease enter \"location <directory>\" to input the directory location.";
	private static final String MESSAGE_NOT_DIRECTORY = "Provided storage location is not a valid directory.";
	private static final String MESSAGE_ADDED = "%1$s is added successfully.";
	private static final String MESSAGE_RESTORED = "System is successfully restored to previous state.";
	private static final String MESSAGE_INVALID_TASK_TYPE = "Unrecognized task type!";
	private static final String MESSAGE_DELETED = "%1$s is deleted successfully.";
	private static final String MESSAGE_MARKED_DONE = "%1$s is marked as done successfully.";
	private static final String MESSAGE_UPDATED = "%1$s is updated successfully.";
	
	private File storage, todo, todoBackup;
	private ArrayList<Task> tasks;
	
	private FileManager fileManager;
	
	public Storage() {
		fileManager = new FileManager();
		fileManager.createDirectory(DIRECTORY_STORAGE);
		storage = fileManager.createFile((DIRECTORY_STORAGE+FILENAME_STORAGE));
		tasks = new ArrayList<Task>();
	}
	
	public boolean isLocationSet() {
		return !fileManager.isEmptyFile(storage);
	}
	
	public String setLocation(String location) throws Exception {
		if(!fileManager.isDirectory(location)) {
			throw new Exception(MESSAGE_NOT_DIRECTORY);
		} else {	
			fileManager.writeToFile(storage, location);
			return MESSAGE_LOCATION_SET;
		}
	}
	
	public String getLocation() throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			String location = fileManager.readFile(storage).get(INDEX_START_FOR_ARRAY);
			return location;
		}
	}
	
	public String addTask(Task task) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 	
			setupFiles();
			fileManager.createBackup(todo, todoBackup);
			addTaskToList(task);
			fileManager.importListToFile(tasks, todo);
			return String.format(MESSAGE_ADDED, task.toFilteredString());
		}
	}

	private void setupFiles() throws Exception {
		todo = fileManager.createFile(getLocation()+FILENAME_TODO);
		fileManager.createFileIfNotExist(todo);
		todoBackup = fileManager.createFile(getLocation()+FILENAME_TODO_BACKUP);
		fileManager.createFileIfNotExist(todoBackup);
	}

	private void addTaskToList(Task task) throws Exception {
		updateTaskList();
		tasks.add(task);
		Collections.sort(tasks);
	}
	
	private void updateTaskList() throws Exception {
		if(!fileManager.isEmptyFile(todo)) {
			if(tasks.size() == SIZE_EMPTY) {
				createTaskList();
			}
		}
	}

	private void createTaskList() throws Exception {
		ArrayList<String> lines = fileManager.readFile(todo);
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
	
	public String editTask(Task task, Task editedTask) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			setupFiles();
			fileManager.createBackup(todo, todoBackup);
			removeTaskFromList(task);
			if (!task.getDescription().matches(editedTask.getDescription()) && !editedTask.getDescription().matches(CHARACTER_SPACE)) {
				task.setDescription(editedTask.getDescription());
			}
			if (task.getStartTime().compareTo(editedTask.getStartTime()) != 0 && editedTask.getStartTime().compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
				task.setStartTime(editedTask.getStartTime());
			}
			if (task.getEndTime().compareTo(editedTask.getEndTime()) != 0 && editedTask.getEndTime().compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
				task.setEndTime(editedTask.getEndTime());
			}
			if (task.getPriority() != editedTask.getPriority()) {
				if (editedTask.getPriority() > 2) {
					task.setPriority(editedTask.getPriority());
				}
			}
			addTaskToList(task);
			fileManager.importListToFile(tasks, todo);
			return String.format(MESSAGE_UPDATED, task.toFilteredString());
		}
	}
	
	public String deleteTask(Task task) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			setupFiles();
			fileManager.createBackup(todo, todoBackup);
			removeTaskFromList(task);
			fileManager.importListToFile(tasks, todo);
			return String.format(MESSAGE_DELETED, task.toFilteredString());
		}
	}
	
	private void removeTaskFromList(Task task) throws Exception {
		updateTaskList();
		tasks.remove(task);
		Collections.sort(tasks);
	}
	
	public ArrayList<Task> load(String taskType) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			updateTaskList();
			switch(taskType) {
				case STRING_EMPTY :
					return loadTasks();
				case STRING_TASK_TYPE_EVENT :
					return loadEvents();
				case STRING_TASK_TYPE_DEADLINE :
					return loadDeadlines();
				case STRING_TASK_TYPE_FLOATING :
					return loadFloatingTasks();
				case STRING_TASK_TYPE_OVERDUE :
					return loadOverdueTasks();
				case STRING_TASK_TYPE_DONE :
					return loadCompletedTasks();
				default :
					//throw an error if the task type is not recognized
					throw new Error(MESSAGE_INVALID_TASK_TYPE);
			}
		}
	}
	
	private ArrayList<Task> loadTasks() {
		ArrayList<Task> currentTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date endTime = task.getEndTime();
			if (!task.isDone() && (endTime.after(now) || endTime == Task.DEFAULT_DATE_VALUE)) {
				currentTasks.add(task);
			}
		}
		return currentTasks;
	}
	
	private ArrayList<Task> loadEvents() {
		ArrayList<Task> currentEvents = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (!task.isDone() && (endTime.after(now) && startTime != Task.DEFAULT_DATE_VALUE)) {
				currentEvents.add(task);
			}
		}
		return currentEvents;
	}
	
	private ArrayList<Task> loadDeadlines() {
		ArrayList<Task> currentDeadlines = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (!task.isDone() && endTime.after(now) && startTime == Task.DEFAULT_DATE_VALUE) {
				currentDeadlines.add(task);
			}
		}
		return currentDeadlines;
	}
	
	private ArrayList<Task> loadFloatingTasks() {
		ArrayList<Task> currentFloatingTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (!task.isDone() && startTime == Task.DEFAULT_DATE_VALUE && endTime == Task.DEFAULT_DATE_VALUE) {
				currentFloatingTasks.add(task);
			}
		}
		return currentFloatingTasks;
	}
	
	private ArrayList<Task> loadOverdueTasks() {
		ArrayList<Task> overdueTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date endTime = task.getEndTime();
			if (!task.isDone() && (endTime.before(now) && endTime != Task.DEFAULT_DATE_VALUE)) {
				overdueTasks.add(task);
			}
		}
		return overdueTasks;		
	}
	
	private ArrayList<Task> loadCompletedTasks() {
		ArrayList<Task> completedTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			if (task.isDone()) {
				completedTasks.add(task);
			}
		}
		return completedTasks;
	}
	
	public ArrayList<Task> searchTasks(String keyword) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			switch(keyword) {
				case CHARACTER_SPACE :
					return loadTasks();
				default :
					ArrayList<Task> filteredTasks = new ArrayList<Task>();
					updateTaskList();
					for (int i = 0; i < tasks.size(); i++) {
						if (tasks.get(i).toString().contains(keyword)) {
							filteredTasks.add(tasks.get(i));
						}
					}
					return filteredTasks;
			}
		}
	}
	
	public String markTaskDone(Task task) throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			fileManager.createBackup(todo, todoBackup);
			removeTaskFromList(task);
			task.setDone(true);
			addTaskToList(task);
			fileManager.importListToFile(tasks, todo);
			return String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
		}
	}
	
	public String restore() throws Exception {
		if(!isLocationSet()) {
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			
			if(!fileManager.isEmptyFile(todoBackup) || !fileManager.isEmptyFile(todo)) {
				fileManager.restoreFromBackup(todo, todoBackup);
				tasks = new ArrayList<Task>();
				updateTaskList();
			}
			return MESSAGE_RESTORED;
		}
	}
}
