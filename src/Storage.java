import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Storage {
	private static final String DIRECTORY_STORAGE = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	
	private static final String CHARACTER_NEW_LINE = "\n";
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
	}
	
	public boolean isLocationSet() {
		return !isEmptyFile(storage);
	}
	
	private boolean isEmptyFile(File file) {
		return file.length() == SIZE_EMPTY;
	}
	
	public String setLocation(String location) throws Exception {
		if(!new File(location).isDirectory()) {
			return MESSAGE_NOT_DIRECTORY;
		} else {	
			writeToFile(storage, location);
			return MESSAGE_LOCATION_SET;
		}
	}
	
	private void writeToFile(File file, String content) throws Exception {
		FileWriter fileWriter = new FileWriter(file, true);
		fileWriter.write(content);
		fileWriter.write(CHARACTER_NEW_LINE);
		fileWriter.close();	
	}
	
	
	
	public String getLocation() throws Exception {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			String location = readFile(storage).get(INDEX_START_FOR_ARRAY);
			return location;
		}
	}
	
	private ArrayList<String> readFile(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		ArrayList<String> lines = new ArrayList<String>();
			
		while ((line = bufferedReader.readLine()) != null) {	
			lines.add(line);
		}
		bufferedReader.close();
		return lines;	 
	}
	
	public String addTask(Task task) throws Exception {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 					
			createBackup();
			addTaskToList(task);
			writeToFile(tasks);
			return String.format(MESSAGE_ADDED, task.toFilteredString());
		}
	}
	
	private void createBackup() throws Exception {
		setupFiles();
		if(!isEmptyFile(todo)) {
			Files.copy(todo.toPath(), todoBackup.toPath(), REPLACE_EXISTING);
		}
	}

	private void setupFiles() throws Exception {
		todo = new File(getLocation()+FILENAME_TODO);
		if(!todo.exists()) {
			todo.createNewFile();
		}
		todoBackup = new File(getLocation()+FILENAME_TODO_BACKUP);
		if(!todoBackup.exists()) {
			todoBackup.createNewFile();
		}
	}

	private void cleanFile(File file) throws Exception {
		FileOutputStream writer = new FileOutputStream(file);
		writer.close();
	}
	
	private void addTaskToList(Task task) throws Exception {
		updateTaskList();
		tasks.add(task);
		Collections.sort(tasks);
	}
	
	private void updateTaskList() throws Exception {
		if(!isEmptyFile(todo)) {
			if(tasks.size() == SIZE_EMPTY) {
				createTaskList();
			}
		}
	}

	private void createTaskList() throws Exception {
		ArrayList<String> lines = readFile(todo);
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
	
	private void writeToFile(ArrayList<Task> tasks) throws Exception {
		if (!isEmptyFile(todo)) { 
			cleanFile(todo);
		}
		for (int i = 0; i < tasks.size(); i++) {
			writeToFile(todo, tasks.get(i).toString());
		}	
	}
	
	public String editTask(Task task, Task editedTask) throws Exception {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			createBackup();
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
			writeToFile(tasks);
			return String.format(MESSAGE_UPDATED, task.toFilteredString());
		}
	}
	
	public String deleteTask(Task task) throws Exception {
		if(!isLocationSet()) {
			return MESSAGE_LOCATION_NOT_SET;
		} else { 
			createBackup();
			removeTaskFromList(task);
			writeToFile(tasks);
			return String.format(MESSAGE_DELETED, task.toFilteredString());
		}
	}
	
	private void removeTaskFromList(Task task) throws Exception {
		updateTaskList();
		tasks.remove(task);
		Collections.sort(tasks);
	}
	
	public ArrayList<Task> load(String taskType) throws Exception {
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
	
	public String markTaskDone(Task task) throws Exception {
		createBackup();
		removeTaskFromList(task);
		task.setDone(true);
		addTaskToList(task);
		writeToFile(tasks);
		return String.format(MESSAGE_MARKED_DONE, task.toFilteredString());
	}
	
	public String restore() throws Exception {
		setupFiles();
		
		if(!isEmptyFile(todoBackup) || !isEmptyFile(todo)) {
			cleanFile(todo);
			Files.copy(todoBackup.toPath(), todo.toPath(), REPLACE_EXISTING);
			tasks = new ArrayList<Task>();
			updateTaskList();
		}
		return MESSAGE_RESTORED;
	}
}