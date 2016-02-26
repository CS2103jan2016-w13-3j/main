import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Storage {
	private static final String DIRECTORY_STORAGE = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	
	private static final String CHARACTER_NEW_LINE = "\n";
	
	private static final String STRING_EMPTY = "";
	private static final String STRING_TASK_TYPE_EVENT = "events";
	private static final String STRING_TASK_TYPE_DEADLINE = "deadlines";
	private static final String STRING_TASK_TYPE_FLOATING = "tasks";
	private static final String STRING_TASK_TYPE_OVERDUE = "overdue";
	private static final String STRING_TASK_TYPE_DONE = "done";

	private static final int SIZE_EMPTY = 0;
	private static final int INDEX_START_FOR_ARRAY = 0;
	
	private static final String MESSAGE_LOCATION_SET = "Storage location of task data is set sucessfully.";
	private static final String MESSAGE_LOCATION_NOT_SET = "Storage location of task data has not been set yet.";
	private static final String MESSAGE_NOT_DIRECTORY = "Provided storage location is not a valid directory";
	private static final String MESSAGE_ADDED = "%1$s is added successfully.";
	private static final String MESSAGE_RESTORED = "System is successfully restored to previous state.";
	private static final String MESSAGE_INVALID_TASK_TYPE = "Unrecognized task type";
	private static final String MESSAGE_DELETED = "%1$s is deleted successfully.";
	private static final String MESSAGE_MARKED_DONE = "%1$s is marked as done successfully.";
	private static final String MESSAGE_UPDATED = "%1$s is updated successfully.";
	
	private File storage, todo, todoBackup;
	private ArrayList<Task> tasks;
	
	public Storage() {
		new File(DIRECTORY_STORAGE).mkdirs();
		this.storage = new File(DIRECTORY_STORAGE+FILENAME_STORAGE);
		this.tasks = new ArrayList<Task>();
		if(isLocationSet()) {
			setupFiles();
		}
	}
	
	public boolean isLocationSet() {
		return !isEmptyFile(storage);
	}
	
	private boolean isEmptyFile(File file) {
		return file.length() == SIZE_EMPTY;
	}
	
	public String setLocation(String location) {
		if(!new File(location).isDirectory()) {
			return MESSAGE_NOT_DIRECTORY;
		} else {	
			try {
				writeToFile(storage, location);
				return MESSAGE_LOCATION_SET;
			} catch (Exception e) {
				return getErrorMessage(e);
			} 
		}
	}
	
	private void writeToFile(File file, String content) throws Exception {
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(content);
		fileWriter.write(CHARACTER_NEW_LINE);
		fileWriter.close();	
	}
	
	private String getErrorMessage(Exception e) {
		return e.getMessage();
	}
	
	public String getLocation() {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			try {
				String location = readFile(storage).get(INDEX_START_FOR_ARRAY);
				return location;
			} catch (Exception e) {
				return getErrorMessage(e);
			}
		}
	}
	
	private ArrayList<String> readFile(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();
			
		while ((line = bufferedReader.readLine()) != null) {	
			if (line != STRING_EMPTY) {
				lines.add(line);
			}
		}
		bufferedReader.close();
		return lines;	 
	}
	
	public String addTask(Task task) {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			setupFiles();					
			try {
				createBackup(todo, todoBackup);
				addTaskToList(task);
				writeToFile(tasks);
				return String.format(MESSAGE_ADDED, task.toFilteredString());
			} catch (Exception e) {
				return getErrorMessage(e);
			}
		}
	}

	private void setupFiles() {
		todo = new File(getLocation()+FILENAME_TODO);
		todoBackup = new File(getLocation()+FILENAME_TODO_BACKUP);
	}

	private void addTaskToList(Task task) {
		updateTaskList();
		tasks.add(task);
		Collections.sort(tasks);
	}
	
	private void updateTaskList() {
		if(!isEmptyFile(todo)) {
			if(tasks.size() == SIZE_EMPTY) {
				tasks = load(STRING_EMPTY);
			}
		}
	}

	private void createBackup(File file, File backupFile) throws Exception {
		cleanFile(todoBackup);
		Files.copy(file.toPath(), backupFile.toPath());
	}
	
	private void cleanFile(File file) throws Exception {
		FileOutputStream writer = new FileOutputStream(file);
		writer.close();
	}
	
	private void writeToFile(ArrayList<Task> tasks) throws Exception {
		cleanFile(todo);
		for (int i = 0; i < tasks.size(); i++) {
			writeToFile(todo, tasks.get(i).toString());
		}	
	}
	
	public String editTask(Task task, Task editedTask) {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			setupFiles();
			try {
				createBackup(todo, todoBackup);
				removeTaskFromList(task);
				if (!task.getDescription().matches(editedTask.getDescription())) {
					task.setDescription(editedTask.getDescription());
				}
				if (task.getStartTime().compareTo(editedTask.getStartTime()) != 0) {
					task.setStartTime(editedTask.getStartTime());
				}
				if (task.getEndTime().compareTo(editedTask.getEndTime()) != 0) {
					task.setEndTime(editedTask.getEndTime());
				}
				if (task.getPriority() != editedTask.getPriority()) {
					task.setPriority(editedTask.getPriority());;
				}
				addTaskToList(task);
				writeToFile(tasks);
				return String.format(MESSAGE_UPDATED, task.toFilteredString());
			} catch (Exception e) {
				return getErrorMessage(e);
			}
		}
	}
	
	public String deleteTask(Task task) {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			setupFiles();
			try {
				createBackup(todo, todoBackup);
				removeTaskFromList(task);
				writeToFile(tasks);
				return String.format(MESSAGE_DELETED, task.toFilteredString());
			} catch (Exception e) {
				return getErrorMessage(e);
			}
		}
	}
	
	private void removeTaskFromList(Task task) {
		updateTaskList();
		tasks.remove(task);
		Collections.sort(tasks);
	}
	
	public ArrayList<Task> load(String taskType) {
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
			Date endTime = task.getEndTime();
			if (!task.isDone() && endTime.after(now)) {
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
			if (!task.isDone() && (startTime == Task.DEFAULT_DATE_VALUE && endTime == Task.DEFAULT_DATE_VALUE)) {
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
	
	public ArrayList<Task> searchTasks(String keyword) {
		switch(keyword) {
			case STRING_EMPTY :
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
	
	public String markTaskDone(Task task) {
		setupFiles();
		try {
			createBackup(todo, todoBackup);
			removeTaskFromList(task);
			task.setDone(true);
			addTaskToList(task);
			writeToFile(tasks);
			return String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
		} catch (Exception e) {
			return getErrorMessage(e);
		}
	}
	
	public String restore() {
		setupFiles();
		try {
			cleanFile(todo);
			Files.copy(todoBackup.toPath(), todo.toPath());
			return MESSAGE_RESTORED;
		} catch (Exception e) {
			return getErrorMessage(e);
		}
	}
}