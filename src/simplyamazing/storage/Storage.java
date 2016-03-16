package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import simplyamazing.data.Task;
import simplyamazing.data.TaskList;

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

	private static final int INDEX_START_FOR_ARRAY = 0;
	
	private static final String MESSAGE_LOCATION_SET = "Storage location of task data is set sucessfully.";
	private static final String MESSAGE_LOCATION_NOT_SET = "Storage location of task data is not set yet. Please enter \"location <directory>\" command to set the storage location.";
	private static final String MESSAGE_NOT_DIRECTORY = "Provided storage location is not a valid directory.";
	private static final String MESSAGE_ADDED = "%1$s is added successfully.";
	private static final String MESSAGE_RESTORED = "System is successfully restored to previous state.";
	private static final String MESSAGE_INVALID_TASK_TYPE = "Unrecognized task type!";
	private static final String MESSAGE_DELETED = "%1$s is deleted successfully.";
	private static final String MESSAGE_MARKED_DONE = "%1$s is marked as done successfully.";
	private static final String MESSAGE_UPDATED = "%1$s is updated successfully.";
	
	private static final String MESSAGE_LOG_DIRECTORY_CREATED = "Directory for storage file is created successfully.";
	private static final String MESSAGE_LOG_STORAGE_FILE_SETUP = "Storage file is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_FILE_SETUP = "Task data file is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_BACKUP_FILE_SETUP = "Task data backup file is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED = "Task data backup file is successfully updated";
	private static final String MESSAGE_LOG_TASK_LIST_UPDATED = "Task data is successfully loaded into the task list.";
	private static final String MESSAGE_LOG_TASK_DATA_READABLE = "Content is readable from task data file.";
	
	private static Logger logger = Logger.getLogger("Storage");
	
	private File storage, todo, todoBackup;
	
	private FileManager fileManager;
	private TaskList taskList;
	
	public Storage() {
		fileManager = new FileManager();
		fileManager.createDirectory(DIRECTORY_STORAGE);
		logger.log(Level.CONFIG, MESSAGE_LOG_DIRECTORY_CREATED);
		storage = fileManager.createFile((DIRECTORY_STORAGE+FILENAME_STORAGE));
		logger.log(Level.CONFIG, MESSAGE_LOG_STORAGE_FILE_SETUP);
		taskList = new TaskList();
	}
	
	private boolean isLocationSet() {
		return !fileManager.isEmptyFile(storage);
	}
	
	public String setLocation(String location) throws Exception {
		assert(location != null && location.isEmpty() == false);
		if(!fileManager.isDirectory(location)) {
			logger.log(Level.WARNING, MESSAGE_NOT_DIRECTORY);
			throw new Exception(MESSAGE_NOT_DIRECTORY);
		} else {	
			fileManager.writeToFile(storage, location);
			logger.log(Level.OFF, MESSAGE_LOCATION_SET);
			return MESSAGE_LOCATION_SET;
		}
	}
	
	public String getLocation() throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			String location = fileManager.readFile(storage).get(INDEX_START_FOR_ARRAY);
			assert(location != null && location.isEmpty() == false);
			return location;
		}
	}
	
	public String addTask(Task task) throws Exception {
		assert(task != null);
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 	
			setupFiles();
			assert(todo != null && todo.exists());
			assert(todoBackup != null && todoBackup.exists());
			fileManager.createBackup(todo, todoBackup);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
			updateTaskData();
			
			int taskListSizeBeforeAdding = taskList.getTasks().size();
			taskList.addTaskToList(task);
			int taskListSizeAfterAdding = taskList.getTasks().size();
			assert(taskListSizeAfterAdding == taskListSizeBeforeAdding + 1);
			
			fileManager.importListToFile(taskList.getTasks(), todo);
			assert(todo.length() > 0);
			
			String feedback = String.format(MESSAGE_ADDED, task.toFilteredString());
			assert(feedback != null && feedback.isEmpty() == false);
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}

	private void setupFiles() throws Exception {
		todo = fileManager.createFile(getLocation()+FILENAME_TODO);	
		if (fileManager.isFileExisting(todo)) {
			fileManager.createNewFile(todo);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_TASK_DATA_FILE_SETUP);
		todoBackup = fileManager.createFile(getLocation()+FILENAME_TODO_BACKUP);
		if (fileManager.isFileExisting(todoBackup)) {
			fileManager.createNewFile(todoBackup);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_SETUP);
	}
	
	private void updateTaskData() throws Exception {
		if(!fileManager.isEmptyFile(todo)) {
			ArrayList<String> taskData = fileManager.readFile(todo);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_READABLE);
			taskList.updateTaskList(taskData);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_LIST_UPDATED);
		}
	}
	
	public String editTask(Task task, Task editedTask) throws Exception {
		assert(task != null);
		deleteTask(task);
			
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

		addTask(task);

		String feedback = String.format(MESSAGE_UPDATED, task.toFilteredString());
		assert(feedback != null && feedback.isEmpty() == false);
		logger.log(Level.INFO, feedback);
		return feedback;
	}
	
	public String deleteTask(Task task) throws Exception {
		assert(task != null);
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			setupFiles();
			assert(todo != null && todo.exists());
			assert(todoBackup != null && todoBackup.exists());
			fileManager.createBackup(todo, todoBackup);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
			updateTaskData();
			
			int taskListSizeBeforeRemoving = taskList.getTasks().size();
			taskList.removeTaskFromList(task);
			int taskListSizeAfterRemoving = taskList.getTasks().size();
			assert(taskListSizeAfterRemoving == taskListSizeBeforeRemoving - 1);
			
			fileManager.importListToFile(taskList.getTasks(), todo);
			String feedback = String.format(MESSAGE_DELETED, task.toFilteredString());
			assert(feedback != null && feedback.isEmpty() == false);
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}
	
	public ArrayList<Task> viewTasks(String taskType) throws Exception {
		assert(taskType != null);
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			assert(todo != null && todo.exists());
			assert(todoBackup != null && todoBackup.exists());
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
			updateTaskData();
			switch(taskType) {
				case STRING_EMPTY :
					return viewTasks();
				case STRING_TASK_TYPE_EVENT :
					return viewEvents();
				case STRING_TASK_TYPE_DEADLINE :
					return viewDeadlines();
				case STRING_TASK_TYPE_FLOATING :
					return viewFloatingTasks();
				case STRING_TASK_TYPE_OVERDUE :
					return viewOverdueTasks();
				case STRING_TASK_TYPE_DONE :
					return viewCompletedTasks();
				default :
					//throw an error if the task type is not recognized
					throw new Error(MESSAGE_INVALID_TASK_TYPE);
			}
		}
	}
	
	private ArrayList<Task> viewTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
	
	private ArrayList<Task> viewEvents() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
	
	private ArrayList<Task> viewDeadlines() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
	
	private ArrayList<Task> viewFloatingTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
	
	private ArrayList<Task> viewOverdueTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
	
	private ArrayList<Task> viewCompletedTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		assert(tasks != null);
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
		assert(keyword != null);
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			switch(keyword) {
				case CHARACTER_SPACE :
					return viewTasks();
				default :
					ArrayList<Task> filteredTasks = new ArrayList<Task>();
					logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
					updateTaskData();
					ArrayList<Task> tasks = taskList.getTasks();
					assert(tasks != null);
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
		assert(task != null);
		deleteTask(task);
		task.setDone(true);
		addTask(task);
			
		String feedback = String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
		assert(feedback != null && feedback.isEmpty() == false);
		logger.log(Level.INFO, feedback);
		return feedback;
	}
	
	public String restore() throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			assert(todo != null && todo.exists());
			assert(todoBackup != null && todoBackup.exists());
			if(!fileManager.isEmptyFile(todoBackup) || !fileManager.isEmptyFile(todo)) {
				fileManager.restoreFromBackup(todo, todoBackup);
				assert(taskList != null);
				taskList.resetTaskList();
				assert(taskList.getTasks().size() == 0);
				updateTaskData();
			}
			logger.log(Level.INFO, MESSAGE_RESTORED);
			return MESSAGE_RESTORED;
		}
	}
}
