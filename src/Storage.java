import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;

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
				return String.format(MESSAGE_ADDED, task.toString());
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
		}
		return null;
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
				return String.format(MESSAGE_DELETED, task.toString());
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
		return null;
		
	}
	
	private ArrayList<Task> loadEvents() {
		return null;
		
	}
	
	private ArrayList<Task> loadDeadlines() {
		return null;
		
	}
	
	private ArrayList<Task> loadFloatingTasks() {
		return null;
		
	}
	
	private ArrayList<Task> loadOverdueTasks() {
		return null;
	}
	
	private ArrayList<Task> loadCompletedTasks() {
		return null;
		
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
		return null;
	}
	
	public String restore() {
		try {
			cleanFile(todo);
			Files.copy(todoBackup.toPath(), todo.toPath());
			return MESSAGE_RESTORED;
		} catch (Exception e) {
			return getErrorMessage(e);
		}
	}
}