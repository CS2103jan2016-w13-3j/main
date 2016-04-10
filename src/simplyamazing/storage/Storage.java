//@@author A0126289W
package simplyamazing.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import simplyamazing.data.Task;
import simplyamazing.data.TaskList;

public class Storage {
	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmazing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	private static final String FILENAME_TEMP = "\\temp.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	private static final String FILENAME_DONE_BACKUP = "\\doneBackup.txt";
	private static final String FILENAME_LOGGER = "\\logFile.txt";
	
	private static final String LOGGER_NAME = "simplyamazing";
	
	private static final String CHARACTER_SPACE = " ";
	private static final String STRING_EMPTY = "";

	private static final String STRING_TASK_TYPE_EVENT = "events";
	private static final String STRING_TASK_TYPE_DEADLINE = "deadlines";
	private static final String STRING_TASK_TYPE_FLOATING = "tasks";
	private static final String STRING_TASK_TYPE_OVERDUE = "overdue";
	private static final String STRING_TASK_TYPE_DONE = "done";

	private static final int INDEX_START_FOR_ARRAY = 0;

	private static final String MESSAGE_LOGGER_FILE_CREATION_FAILED = "Logger creation failed";
	private static final String MESSAGE_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String MESSAGE_NOT_DIRECTORY = "Error: Not a valid directory.";
	private static final String MESSAGE_ADDED = "%1$s has been added.";
	private static final String MESSAGE_RESTORED = "\"%1$s\" command has been successfully undone.";
	private static final String MESSAGE_INVALID_TASK_TYPE = "Unrecognized task type!";
	private static final String MESSAGE_COMPLETED_TASK = "%1$s is a completed task.";
	private static final String MESSAGE_INCOMPLETE_TASK = "%1$s is an incomplete task.";
	private static final String MESSAGE_DELETED = "%1$s has been successfully deleted.";
	private static final String MESSAGE_DELETED_MULTIPLE = "Provided tasks have been successfully deleted.";
	private static final String MESSAGE_MARKED_DONE = "%1$s has been marked as done.";
	private static final String MESSAGE_MARKED_UNDONE = "%1$s has been marked as incomplete.";
	private static final String MESSAGE_MARKED_DONE_MULTIPLE = "Provided tasks have been marked as done.";
	private static final String MESSAGE_MARKED_UNDONE_MULTIPLE = "Provided tasks have been marked as incomplete.";
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
	
	private static Logger logger  = Logger.getLogger("simplyamazing");

	private static boolean isEditing = false;

	private File storage, todo, done, todoBackup, doneBackup;

	private FileManager fileManager;
	private TaskList taskList;

	public Storage() {
		createLogger();
		
		fileManager = new FileManager();
		
		fileManager.createDirectory(DIRECTORY_SYSTEM);
		logger.log(Level.CONFIG, MESSAGE_LOG_DIRECTORY_CREATED);
		
		storage = fileManager.createFile((DIRECTORY_SYSTEM+FILENAME_STORAGE));
		logger.log(Level.CONFIG, MESSAGE_LOG_STORAGE_FILE_CREATED);
		
		taskList = new TaskList();
	}

	private void createLogger() {
		logger = Logger.getLogger(LOGGER_NAME);
		try {
			FileHandler fh = new FileHandler(DIRECTORY_SYSTEM + FILENAME_LOGGER, true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);
		} catch (Exception e){
			System.out.println(MESSAGE_LOGGER_FILE_CREATION_FAILED);
		}
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

	/*
	 * This method sets the storage location of the task data.
	 * It checks the given location is an absolute path.
	 * If it is not, it converts it into an absolute path and checks if it is a valid directory (i.e. exists).
	 * If it is invalid, return an error message.
	 * Otherwise, write to the storage.txt file.
	 * Pre-condition: location is a non-empty string
	 */
	public String setLocation(String location) throws Exception {
		if(!fileManager.isAbsolutePath(location)) {
			location = fileManager.getAbsolutePath(location);
			if (!fileManager.isValidDirectory(location)) {
				logger.log(Level.WARNING, MESSAGE_NOT_DIRECTORY);
				throw new Exception(MESSAGE_NOT_DIRECTORY);
			}
		}
		
		if (!fileManager.isFileExisting(storage)) {
			fileManager.createNewFile(storage);
		}
		if (isLocationSet()) { // When storage location has been set before
			setupFiles();

			todo = fileManager.createTempFile(todo, location+FILENAME_TODO);
			done = fileManager.createTempFile(done, location+FILENAME_DONE);

			fileManager.cleanFile(storage);
		}
		fileManager.writeToFile(storage, location);

		String feedback = String.format(MESSAGE_LOCATION_SET, location);
		logger.log(Level.INFO, feedback);
		return feedback;
	}

	public String getLocation() throws Exception {
		if(!isLocationSet()) {
			setLocation(DIRECTORY_SYSTEM); // set to default location
		} 
		String location = fileManager.readFile(storage).get(INDEX_START_FOR_ARRAY);
		return location;
	}

	/*
	 * This method adds the given task to the to-do list.
	 * Before adding, setup files and update the list if required and create backup file.
	 * Pre-condition: task is not null
	 */
	public String addTask(Task task) throws Exception {
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
			taskList.updateTaskList(taskData, taskList.getTasks());
			assert(taskList.getTasks().size() > 0);
		}
		if(!fileManager.isEmptyFile(done)) {
			ArrayList<String> completedTaskData = fileManager.readFile(done);
			taskList.updateTaskList(completedTaskData, taskList.getCompletedTasks());
			assert(taskList.getCompletedTasks().size() > 0);
		}
		logger.log(Level.INFO, MESSAGE_LOG_TASK_LIST_UPDATED);
	}

	/*
	 * This method edits the task by comparing the differences between the given task and edited task.
	 * Before editing, setup files and update the list if required and create backup file.
	 * Pre-condition: task and editedTask are not null
	 */
	public String editTask(Task task, Task editedTask) throws Exception {
		isEditing = true;	
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		throwExceptionIfCompletedTask(tasks);

		deleteTask(task);

		if (!task.getDescription().matches(editedTask.getDescription()) && !editedTask.getDescription().matches(CHARACTER_SPACE)) {
			task.setDescription(editedTask.getDescription());
		}
		
		if (editedTask.getStartTime().compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)==0) { // start time set as none
			task.setStartTime(Task.DEFAULT_DATE_VALUE);
		} else if (task.getStartTime().compareTo(editedTask.getStartTime()) != 0 && editedTask.getStartTime().compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
			task.setStartTime(editedTask.getStartTime());
		}
		
		if (editedTask.getEndTime().compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)==0) { // end time set as none
			task.setEndTime(Task.DEFAULT_DATE_VALUE);
		} else if (task.getEndTime().compareTo(editedTask.getEndTime()) != 0 && editedTask.getEndTime().compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
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

	private void throwExceptionIfCompletedTask(ArrayList<Task> tasks) throws Exception {
		for (int i = 0; i < tasks.size(); i++) {
			if(taskList.getCompletedTasks().contains(tasks.get(i))) {
				String exceptionMessage = String.format(MESSAGE_COMPLETED_TASK, tasks.get(i).toFilteredString());
				logger.log(Level.WARNING, exceptionMessage);
				throw new Exception(exceptionMessage);
			}
		}
	}

	/*
	 * This method deletes the given task from either to-do list or completed task list.
	 * Before deleting, setup files and update the list if required and create backup file.
	 * Pre-condition: task is not null
	 */
	public String deleteTask(Task task) throws Exception {
		setupFiles();

		fileManager.createBackup(todo, todoBackup);
		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
		
		fileManager.createBackup(done, doneBackup);
		logger.log(Level.INFO, MESSAGE_LOG_COMPLETED_TASK_DATA_BACKUP_FILE_UPDATED);

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
	
	/*
	 * This method deletes the given tasks from either to-do list or completed task list.
	 * Before deleting, setup files and update the list if required and create backup file.
	 * Pre-condition: tasks are not null
	 */
	public String deleteMultipleTasks(ArrayList<Task> tasks) throws Exception {
		setupFiles();

		fileManager.createBackup(todo, todoBackup);
		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_BACKUP_FILE_UPDATED);
		
		fileManager.createBackup(done, doneBackup);
		logger.log(Level.INFO, MESSAGE_LOG_COMPLETED_TASK_DATA_BACKUP_FILE_UPDATED);

		updateTaskData();
		
		for (int i = 0; i < tasks.size(); i++) {
			taskList.removeTaskFromList(tasks.get(i));
	
			String feedback = String.format(MESSAGE_DELETED, tasks.get(i).toFilteredString());
			logger.log(Level.INFO, feedback);
		}
		int lineCountBeforeAdding = fileManager.getLineCount(todo);
		fileManager.importListToFile(taskList.getTasks(), todo);
		int lineCountAfterAdding = fileManager.getLineCount(todo);
		assert(lineCountAfterAdding <= lineCountBeforeAdding);

		lineCountBeforeAdding = fileManager.getLineCount(done);
		fileManager.importListToFile(taskList.getCompletedTasks(), done);
		lineCountAfterAdding = fileManager.getLineCount(done);
		assert(lineCountAfterAdding <= lineCountBeforeAdding);

		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
		return MESSAGE_DELETED_MULTIPLE;
	}

	/*
	 * This method retrieves tasks according to the given task type.
	 * Pre-condition: taskType is not null
	 */
	public ArrayList<Task> viewTasks(String taskType) throws Exception {

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

	/*
	 * This method retrieves all pending tasks.
	 */
	private ArrayList<Task> viewTasks() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> overdueTasks = viewOverdueTasks();
		ArrayList<Task> currentTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			if (!overdueTasks.contains(task)) {
				currentTasks.add(task);
			}
		}
		return currentTasks;
	}

	private ArrayList<Task> viewEvents() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> overdueTasks = viewOverdueTasks();
		ArrayList<Task> currentEvents = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date startTime = task.getStartTime();
			if (!overdueTasks.contains(task) && startTime != Task.DEFAULT_DATE_VALUE) {
				currentEvents.add(task);
			}
		}
		return currentEvents;
	}

	private ArrayList<Task> viewDeadlines() {
		ArrayList<Task> tasks = taskList.getTasks();
		ArrayList<Task> overdueTasks = viewOverdueTasks();
		ArrayList<Task> currentDeadlines = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Date startTime = task.getStartTime();
			Date endTime = task.getEndTime();
			if (!overdueTasks.contains(task) && startTime == Task.DEFAULT_DATE_VALUE && endTime != Task.DEFAULT_DATE_VALUE) {
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
	
	/*
	 * This method retrieves all tasks with end time already passed.
	 */
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

	/*
	 * This method retrieves all tasks having the given keyword.
	 * If there is more than one keyword, returns the result for the exact match (AND) followed by partial match (OR).
	 * Pre-condition: keyword is not null
	 */
	public ArrayList<Task> searchTasks(String keyword) throws Exception {
		keyword = keyword.toLowerCase();
		ArrayList<Task> tasks = viewTasks(STRING_EMPTY);
		assert(tasks != null);
		tasks.addAll(viewCompletedTasks());
		
		ArrayList<Task> filteredTasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).toString().toLowerCase().contains(keyword)) {
				filteredTasks.add(tasks.get(i));
			}
		}
		String[] keywords = keyword.split(CHARACTER_SPACE);
		for (int i = 0; i < tasks.size(); i++) {
			for (int j = 0; j < keywords.length; j++) {
				if (tasks.get(i).toString().toLowerCase().contains(keywords[j]) && !filteredTasks.contains(tasks.get(i))) {
					filteredTasks.add(tasks.get(i));
				}
			}
		}
		return filteredTasks;
	}
	
	/*
	 * This method retrieves all tasks that can be done on the given date.
	 * When the user provides only the month and/or year, it will give the same result as searchByKeyword 
	 * Time provided will be ignored.
	 * Pre-condition: date is not null
	 */
	public ArrayList<Task> searchTasksByDate(Date date) throws Exception {
		String[] datetimes = Task.convertDateToString(date).split(CHARACTER_SPACE);
		String dateString = datetimes[1] + CHARACTER_SPACE + datetimes[2] + CHARACTER_SPACE + datetimes[3];
		ArrayList<Task> tasks = viewTasks(STRING_EMPTY);
		assert(tasks != null);

		ArrayList<Task> filteredTasks = new ArrayList<Task>();
		
		// put the tasks with the exact match of the specified date first
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).toString().contains(dateString)) { 
				filteredTasks.add(tasks.get(i));
			}	
		}
		
		// put the tasks which has end time after the specified date
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getStartTime().before(date) && tasks.get(i).getEndTime().after(date) && !filteredTasks.contains(tasks.get(i))) {
				filteredTasks.add(tasks.get(i));
			}
		}
		
		// put floating tasks lastly
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getStartTime() == Task.DEFAULT_DATE_VALUE && tasks.get(i).getEndTime() == Task.DEFAULT_DATE_VALUE && !filteredTasks.contains(tasks.get(i))) {
				filteredTasks.add(tasks.get(i));
			}
		}
		return filteredTasks;
	}

	/*
	 * This method moves the given task from to-do list to completed task list.
	 * Pre-condition: task is not null
	 */
	public String markTaskDone(Task task) throws Exception {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		throwExceptionIfCompletedTask(tasks);
		
		deleteTask(task);
		
		task.setDone(true);
		moveToDoneFile(task);

		String feedback = String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
		logger.log(Level.INFO, feedback);
		return feedback;
	}
	
	private void moveToDoneFile(Task task) throws Exception {
		taskList.addTaskToList(task, taskList.getCompletedTasks());

		int lineCountBeforeAdding = fileManager.getLineCount(done);
		fileManager.importListToFile(taskList.getCompletedTasks(), done);
		int lineCountAfterAdding = fileManager.getLineCount(done);
		assert(lineCountAfterAdding == lineCountBeforeAdding + 1);
		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
	}
	
	/*
	 * This method moves the given tasks from completed task list to to-do list.
	 * Pre-condition: tasks are not null
	 */
	public String markTaskUndone(Task task) throws Exception {
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		throwExceptionIfNotCompletedTask(tasks);
		
		deleteTask(task);
		
		task.setDone(false);
		moveToToDoFile(task);

		String feedback = String.format(MESSAGE_MARKED_UNDONE, task.toFilteredString());
		logger.log(Level.INFO, feedback);
		return feedback;
	}
	
	private void moveToToDoFile(Task task) throws Exception {
		taskList.addTaskToList(task, taskList.getTasks());

		int lineCountBeforeAdding = fileManager.getLineCount(todo);
		fileManager.importListToFile(taskList.getTasks(), todo);
		int lineCountAfterAdding = fileManager.getLineCount(todo);
		assert(lineCountAfterAdding == lineCountBeforeAdding + 1);
		logger.log(Level.INFO, MESSAGE_LOG_TASK_DATA_WRITTEN_TO_FILE);
	}
	
	private void throwExceptionIfNotCompletedTask(ArrayList<Task> tasks) throws Exception {
		for (int i = 0; i < tasks.size(); i++) {
			if(taskList.getTasks().contains(tasks.get(i))) {
				String exceptionMessage = String.format(MESSAGE_INCOMPLETE_TASK, tasks.get(i).toFilteredString());
				logger.log(Level.WARNING, exceptionMessage);
				throw new Exception(exceptionMessage);
			}
		}
	}

	/*
	 * This method moves the given tasks from to-do list to completed task list.
	 * Pre-condition: tasks are not null
	 */
	public String markMultipleTasksDone(ArrayList<Task> tasks) throws Exception {
		
		throwExceptionIfCompletedTask(tasks);

		deleteMultipleTasks(tasks);
		
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setDone(true);
			moveToDoneFile(tasks.get(i));
	
			String feedback = String.format(MESSAGE_MARKED_DONE, tasks.get(i).toFilteredString());
			logger.log(Level.INFO, feedback);
		}
		return MESSAGE_MARKED_DONE_MULTIPLE;
	}
	
	/*
	 * This method moves the given tasks from completed task list to to-do list.
	 * Pre-condition: tasks are not null
	 */
	public String markMultipleTasksUndone(ArrayList<Task> tasks) throws Exception {
		
		throwExceptionIfNotCompletedTask(tasks);
		
		deleteMultipleTasks(tasks);
		
		for (int i = 0; i < tasks.size(); i++) {
			tasks.get(i).setDone(false);
			moveToToDoFile(tasks.get(i));
	
			String feedback = String.format(MESSAGE_MARKED_UNDONE, tasks.get(i).toFilteredString());
			logger.log(Level.INFO, feedback);
		}
		return MESSAGE_MARKED_UNDONE_MULTIPLE;
	}

	public String restore(String previousCommand) throws Exception {
		setupFiles();

		if(!fileManager.isEmptyFile(todoBackup) || !fileManager.isEmptyFile(todo)) {
			File temp = fileManager.createTempFile(todo, DIRECTORY_SYSTEM+FILENAME_TEMP);	
			fileManager.restoreFromBackup(todo, todoBackup);
			fileManager.restoreFromBackup(todoBackup, temp);
			temp.delete();
			taskList.resetTaskList();
			assert(taskList.getTasks().size() == 0);
			updateTaskData();
		}
		if(!fileManager.isEmptyFile(doneBackup) || !fileManager.isEmptyFile(done)) {
			File temp = fileManager.createTempFile(done, DIRECTORY_SYSTEM+FILENAME_TEMP);;
			fileManager.restoreFromBackup(done, doneBackup);
			fileManager.restoreFromBackup(doneBackup, temp);
			temp.delete();
			taskList.resetCompletedTaskList();
			assert(taskList.getCompletedTasks().size() == 0);
			updateTaskData();
		}
		String feedback = String.format(MESSAGE_RESTORED, previousCommand);
		logger.log(Level.INFO, feedback);
		return feedback;
	}
}
