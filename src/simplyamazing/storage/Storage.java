package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import simplyamazing.data.Task;
import simplyamazing.data.TaskList;

public class Storage {
	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	private static final String FILENAME_DONE_BACKUP = "\\doneBackup.txt";
	
	private static final String CHARACTER_SPACE = " ";
	private static final String STRING_EMPTY = "";
	
	private static final String STRING_TASK_TYPE_EVENT = "events";
	private static final String STRING_TASK_TYPE_DEADLINE = "deadlines";
	private static final String STRING_TASK_TYPE_FLOATING = "tasks";
	private static final String STRING_TASK_TYPE_OVERDUE = "overdue";
	private static final String STRING_TASK_TYPE_DONE = "done";

	private static final int INDEX_START_FOR_ARRAY = 0;
	
	private static final String MESSAGE_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String MESSAGE_LOCATION_NOT_SET = "Error: Storage location of task data is has not been set. Please enter \"location <directory>\" command to set the storage location.";
	private static final String MESSAGE_NOT_DIRECTORY = "Error: Not a valid directory.";
	private static final String MESSAGE_ADDED = "%1$s has been added.";
	private static final String MESSAGE_RESTORED = "\"%1$s\" command has been successfully undone.";
	private static final String MESSAGE_INVALID_TASK_TYPE = "Unrecognized task type!";
	private static final String MESSAGE_COMPLETED_TASK = "%1$s is a completed task.";
	private static final String MESSAGE_DELETED = "%1$s has been successfully deleted.";
	private static final String MESSAGE_MARKED_DONE = "%1$s has been marked as done.";
	private static final String MESSAGE_UPDATED = "%1$s has been successfully updated.";
	
	private static final String MESSAGE_LOG_DIRECTORY_CREATED = "Directory for storage file is created successfully.";
	private static final String MESSAGE_LOG_STORAGE_FILE_CREATED = "Storage file is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_FILE_SETUP = "Task data file is setup successfully.";
	private static final String MESSAGE_LOG_DONE_TASKS_FILE_SETUP = "Task data file for completed tasks is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_BACKUP_FILE_SETUP = "Task data backup file is setup successfully.";
	private static final String MESSAGE_LOG_DONE_TASKS_BACKUP_FILE_SETUP = "Task data backup file for completed tasks is setup successfully.";
	private static final String MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED = "Task data backup file is successfully updated";
	private static final String MESSAGE_LOG_COMPLETED_TASK_DATA_BACKUP_FILE_UPDATED = "Task data backup file for completed tasks is successfully updated";
	private static final String MESSAGE_LOG_TASK_LIST_UPDATED = "Task data is successfully loaded into the task list.";
	private static final String MESSAGE_LOG_TASK_DATA_READABLE = "Content is readable from task data file.";
	private static final String MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE = "Task list is successfully imported into the task data file.";
	
	private static Logger logger = Logger.getLogger("Storage");
	
	private static boolean isEditing = false;
	
	private File storage, todo, done, todoBackup, doneBackup;
	
	private FileManager fileManager;
	private TaskList taskList;
	
	public Storage() {
		fileManager = new FileManager();
		
		fileManager.createDirectory(DIRECTORY_SYSTEM);
		logger.log(Level.CONFIG, MESSAGE_LOG_DIRECTORY_CREATED);
		
		storage = fileManager.createFile((DIRECTORY_SYSTEM+FILENAME_STORAGE));
		logger.log(Level.CONFIG, MESSAGE_LOG_STORAGE_FILE_CREATED);
		
		taskList = new TaskList();
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}

	public TaskList getTaskList() {
		return taskList;
	}

	private boolean isLocationSet() {
		return !fileManager.isEmptyFile(storage);
	}
	
	public String setLocation(String location) throws Exception {
		if(!fileManager.isDirectory(location)) {
			logger.log(Level.WARNING, MESSAGE_NOT_DIRECTORY);
			throw new Exception(MESSAGE_NOT_DIRECTORY);
		} else {	
			if (!fileManager.isFileExisting(storage)) {
				fileManager.createNewFile(storage);
			}
			if(isLocationSet()) { // When storage location has been set before
				setupFiles();

				File todoNew = fileManager.createFile(location+FILENAME_TODO);
				fileManager.createBackup(todo, todoNew);
				todo.delete();
				todo = todoNew;

				File doneNew = fileManager.createFile(location+FILENAME_DONE);
				fileManager.createBackup(done, doneNew);
				done.delete();
				done = doneNew;
				
				fileManager.cleanFile(storage);
			}
			fileManager.writeToFile(storage, location);
			
			String feedback = String.format(MESSAGE_LOCATION_SET, location);
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}
	
	public String getLocation() throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			String location = fileManager.readFile(storage).get(INDEX_START_FOR_ARRAY);
			return location;
		}
	}
	
	public String addTask(Task task) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 	
			setupFiles();
			if(!isEditing) {
				fileManager.createBackup(todo, todoBackup);
				logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
			}
			updateTaskData();
			
			taskList.addTaskToList(task, taskList.getTasks());
			
			int lineCountBeforeAdding = fileManager.getLineCount(todo);
			fileManager.importListToFile(taskList.getTasks(), todo);
			int lineCountAfterAdding = fileManager.getLineCount(todo);
			assert(lineCountAfterAdding == lineCountBeforeAdding + 1);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
			
			String feedback = String.format(MESSAGE_ADDED, task.toFilteredString());
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}

	private void setupFiles() throws Exception {
		todo = fileManager.createFile(getLocation()+FILENAME_TODO);	
		if (!fileManager.isFileExisting(todo)) {
			fileManager.createNewFile(todo);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_TASK_DATA_FILE_SETUP);
		
		todoBackup = fileManager.createFile(DIRECTORY_SYSTEM+FILENAME_TODO_BACKUP);
		if (!fileManager.isFileExisting(todoBackup)) {
			fileManager.createNewFile(todoBackup);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_SETUP);
		
		done = fileManager.createFile(getLocation()+FILENAME_DONE);
		if (!fileManager.isFileExisting(done)) {
			fileManager.createNewFile(done);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_DONE_TASKS_FILE_SETUP);
		
		doneBackup = fileManager.createFile(DIRECTORY_SYSTEM+FILENAME_DONE_BACKUP);
		if (!fileManager.isFileExisting(doneBackup)) {
			fileManager.createNewFile(doneBackup);
		}
		logger.log(Level.CONFIG, MESSAGE_LOG_DONE_TASKS_BACKUP_FILE_SETUP);
	}
	
	private void updateTaskData() throws Exception {
		if(!fileManager.isEmptyFile(todo)) {
			ArrayList<String> taskData = fileManager.readFile(todo);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_READABLE);
			taskList.updateTaskList(taskData, taskList.getTasks());
			assert(taskList.getTasks().size() > 0);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_LIST_UPDATED);
		}
		if(!fileManager.isEmptyFile(done)) {
			ArrayList<String> completedTaskData = fileManager.readFile(done);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_READABLE);
			taskList.updateTaskList(completedTaskData, taskList.getCompletedTasks());
			assert(taskList.getCompletedTasks().size() > 0);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_LIST_UPDATED);
		}
	}
	
	public String editTask(Task task, Task editedTask) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			isEditing = true;			
			throwExceptionIfCompletedTask(task);
			
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
				task.setPriority(editedTask.getPriority());
			}
	
			addTask(task);
			
			String feedback = String.format(MESSAGE_UPDATED, task.toFilteredString());
			logger.log(Level.INFO, feedback);
			isEditing = false;
			return feedback;
		}
	}

	private void throwExceptionIfCompletedTask(Task task) throws Exception {
		if(taskList.getCompletedTasks().contains(task)) {
			String exceptionMessage = String.format(MESSAGE_COMPLETED_TASK, task.toFilteredString());
			logger.log(Level.WARNING, exceptionMessage);
			throw new Exception(exceptionMessage);
		}
	}
	
	public String deleteTask(Task task) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else { 
			setupFiles();
			
			fileManager.createBackup(todo, todoBackup);
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
			
			updateTaskData();
			
			taskList.removeTaskFromList(task);
			
			int lineCountBeforeAdding = fileManager.getLineCount(todo);
			fileManager.importListToFile(taskList.getTasks(), todo);
			int lineCountAfterAdding = fileManager.getLineCount(todo);
			assert(lineCountAfterAdding <= lineCountBeforeAdding);
			
			lineCountBeforeAdding = fileManager.getLineCount(done);
			fileManager.importListToFile(taskList.getCompletedTasks(), done);
			lineCountAfterAdding = fileManager.getLineCount(done);
			assert(lineCountAfterAdding <= lineCountBeforeAdding);
			
			logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
			
			String feedback = String.format(MESSAGE_DELETED, task.toFilteredString());
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}
	
	public ArrayList<Task> viewTasks(String taskType) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			
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
					throw new Exception(MESSAGE_INVALID_TASK_TYPE);
			}
		}
	}
	
	private ArrayList<Task> viewTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> currentTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if ((startTime.after(now) || startTime == Task.DEFAULT_DATE_VALUE) && (endTime.after(now) || endTime == Task.DEFAULT_DATE_VALUE)) {
				currentTasks.add(task);
			}
		}
		return currentTasks;
	}
	
	private ArrayList<Task> viewEvents() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> currentEvents = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if ((startTime.after(now) && endTime.after(now)) && startTime != Task.DEFAULT_DATE_VALUE) {
				currentEvents.add(task);
			}
		}
		return currentEvents;
	}
	
	private ArrayList<Task> viewDeadlines() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> currentDeadlines = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (endTime.after(now) && startTime == Task.DEFAULT_DATE_VALUE) {
				currentDeadlines.add(task);
			}
		}
		return currentDeadlines;
	}
	
	private ArrayList<Task> viewFloatingTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> currentFloatingTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (startTime == Task.DEFAULT_DATE_VALUE && endTime == Task.DEFAULT_DATE_VALUE) {
				currentFloatingTasks.add(task);
			}
		}
		return currentFloatingTasks;
	}
	
	private ArrayList<Task> viewOverdueTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> overdueTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date now = new Date();
			Date endTime = task.getEndTime();
			if (endTime.before(now) && endTime != Task.DEFAULT_DATE_VALUE) {
				overdueTasks.add(task);
			}
		}
		return overdueTasks;		
	}
	
	private ArrayList<Task> viewCompletedTasks() {
		return taskList.getCompletedTasks();
	}
	
	public ArrayList<Task> searchTasks(String keyword) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			ArrayList<Task> tasks = viewTasks(STRING_EMPTY);
			assert(tasks != null);
			tasks.addAll(taskList.getCompletedTasks());
		
			ArrayList<Task> filteredTasks = new ArrayList<Task>();
			for (int i = 0; i < tasks.size(); i++) {
				if (tasks.get(i).toString().contains(keyword)) {
					filteredTasks.add(tasks.get(i));
				}
			}
			return filteredTasks;
		}
	}
	
	public String markTaskDone(Task task) throws Exception {
		throwExceptionIfCompletedTask(task);
		
		deleteTask(task);
		task.setDone(true);
		moveToDoneFile(task);
			
		String feedback = String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
		logger.log(Level.INFO, feedback);
		return feedback;
	}

	private void moveToDoneFile(Task task) throws Exception {
		fileManager.createBackup(done, doneBackup);
		logger.log(Level.INFO, MESSAGE_LOG_COMPLETED_TASK_DATA_BACKUP_FILE_UPDATED);
		
		taskList.addTaskToList(task, taskList.getCompletedTasks());
		
		int lineCountBeforeAdding = fileManager.getLineCount(done);
		fileManager.importListToFile(taskList.getCompletedTasks(), done);
		int lineCountAfterAdding = fileManager.getLineCount(done);
		assert(lineCountAfterAdding == lineCountBeforeAdding + 1);
		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
	}
	
	public String restore(String previousCommand) throws Exception {
		if(!isLocationSet()) {
			logger.log(Level.WARNING, MESSAGE_LOCATION_NOT_SET);
			throw new Exception(MESSAGE_LOCATION_NOT_SET);
		} else {
			setupFiles();
			
			if(!fileManager.isEmptyFile(todoBackup) || !fileManager.isEmptyFile(todo)) {
				fileManager.restoreFromBackup(todo, todoBackup);
				taskList.resetTaskList();
				assert(taskList.getTasks().size() == 0);
				updateTaskData();
			}
			if(!fileManager.isEmptyFile(doneBackup) || !fileManager.isEmptyFile(done)) {
				fileManager.restoreFromBackup(done, doneBackup);
				taskList.resetCompletedTaskList();
				assert(taskList.getCompletedTasks().size() == 0);
				updateTaskData();
			}
			String feedback = String.format(MESSAGE_RESTORED, previousCommand);
			logger.log(Level.INFO, feedback);
			return feedback;
		}
	}
}
