package test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.storage.Storage;

public class StorageTest {
	
	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	private static final String FILENAME_TODO_BACKUP = "\\todoBackup.txt";
	private static final String FILENAME_DONE_BACKUP = "\\doneBackup.txt";
	
	private static final String PARAM_DESCRIPTION1 = "go gym";
	private static final String PARAM_DESCRIPTION = "go swimming";
	private static final String PARAM_PRIORITY = "high";
	private static final String PARAM_END_TIME = "15:00 16 May 2016";
	private static final String PARAM_END_TIME1 = "15:00 20 May 2016";
	private static final String PARAM_END_TIME2 = "15:00 27 May 2016";
	private static final String PARAM_END_TIME3 = "15:00 31 May 2016";
	private static final String PARAM_START_TIME = "13:00 16 May 2016";
	private static final String PARAM_START_TIME1 = "13:00 20 May 2016";
	private static final String PARAM_SET_LOCATION_NULL = null;
	private static final String PARAM_SET_LOCATION_EMPTY = "";
	private static final String PARAM_SET_LOCATION_NOT_DIRECTORY = "C:\\Users\\Public\\Documents\\SimplyAmazing";
	private static final String PARAM_SET_LOCATION_DIRECTORY = "C:\\Users\\Public\\Documents";
	private static final String PARAM_VIEW_TASKS_NULL = null;
	private static final String PARAM_VIEW_TASKS_EMPTY = "";
	private static final String PARAM_VIEW_TASKS_EVENTS = "events";
	private static final String PARAM_VIEW_TASKS_DEADLINES = "deadlines";
	private static final String PARAM_VIEW_TASKS_FLOATING = "tasks";
	private static final String PARAM_VIEW_TASKS_OVERDUE = "overdue";
	private static final String PARAM_VIEW_TASKS_DONE = "done";
	private static final String PARAM_VIEW_TASKS_OTHERS = "other";
	private static final String PARAM_SEARCH_TASKS_NULL = null;
	private static final String PARAM_SEARCH_TASKS_EMPTY = "";
	private static final String PARAM_SEARCH_TASKS_KEYWORD = "gym";
	private static final String PARAM_SEARCH_TASKS_MORE_KEYWORD = "go gym";
	private static final String PARAM_RESTORE_NULL = null;
	private static final String PARAM_RESTORE_EMPTY = "";
	private static final String PARAM_RESTORE_COMMAND = "done 1";
	
	private static final String FEEDBACK_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String FEEDBACK_ADDED = "%1$s has been added.";
	private static final String FEEDBACK_UPDATED = "%1$s has been successfully updated.";
	private static final String FEEDBACK_MARKED_DONE = "%1$s has been marked as done.";
	private static final String FEEDBACK_DELETED = "%1$s has been successfully deleted.";
	private static final String FEEDBACK_RESTORED = "\"%1$s\" command has been successfully undone.";
	
	/*
	 * Operation to test: setLocation(String location): String
	 * Equivalence partition: 
	 * location: [null] [not null] [not a valid directory] [a valid directory] 
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testSetLocationMethodForException() throws Exception {
		Storage storage = new Storage();
		
		/* This is for the first launch of program where user hasn't set the storage location */
		File location = new File(DIRECTORY_SYSTEM+FILENAME_STORAGE);
		storage.getFileManager().createNewFile(location);
		
		/* This is for the ‘not a valid directory’ partition */
		storage.setLocation(PARAM_SET_LOCATION_NOT_DIRECTORY);
	}
	
	@Test(expected = Exception.class) 
	public void testSetLocationMethodForAssertionError() throws Exception {
		Storage storage = new Storage();
		
		/* This is for the first launch of program where user hasn't set the storage location */
		File location = new File(DIRECTORY_SYSTEM+FILENAME_STORAGE);
		storage.getFileManager().createNewFile(location);
		
		/* This is for the ‘null’ partition */
		storage.setLocation(PARAM_SET_LOCATION_NULL);
			
		/* This is a boundary case for the ‘not null’ partition */
		storage.setLocation(PARAM_SET_LOCATION_EMPTY);
	}
	
	@Test
	public void testSetLocationMethod() throws Exception {
		Storage storage = new Storage();
		
		/* This is for the first launch of program where user hasn't set the storage location */
		File location = new File(DIRECTORY_SYSTEM+FILENAME_STORAGE);
		if (storage.getFileManager().isFileExisting(location)) {
			location.delete();
		}
		assertEquals(false, storage.getFileManager().isFileExisting(location));
		
		/* This is for the ‘a valid directory’ partition */
		assertEquals(String.format(FEEDBACK_LOCATION_SET, PARAM_SET_LOCATION_DIRECTORY), storage.setLocation(PARAM_SET_LOCATION_DIRECTORY));
		assertEquals(PARAM_SET_LOCATION_DIRECTORY, storage.getLocation());
		assertEquals(1, storage.getFileManager().getLineCount(location));
	}
	
	/*
	 * Operation to test: addTask(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null] 
	 */	
	@Test(expected = Exception.class) 
	public void testAddTaskMethodForException() throws Exception {
		Storage storage = new Storage();
		
		/* This is for the successive launch of program where user has set the storage location before */
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = null;
		
		try {	
			/* This is for the ‘null’ partition */
			storage.addTask(task);
		} catch (AssertionError ae) {
			throw new Exception();
		}
	}
	
	@Test
	public void testAddTaskMethod() throws Exception {
		Storage storage = new Storage();
		
		/* This is for the first launch of program where user hasn't set the storage location */
		File location = new File(DIRECTORY_SYSTEM+FILENAME_STORAGE);
		storage.getFileManager().createNewFile(location);
		
		File todo = new File(DIRECTORY_SYSTEM+FILENAME_TODO);
		File done = new File(DIRECTORY_SYSTEM+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = new Task(PARAM_DESCRIPTION);
		
		/* This is for the ‘not null’ partition */
		assertEquals(0, storage.getTaskList().getTasks().size());
		assertEquals(0, storage.getFileManager().getLineCount(todo));
		assertEquals(String.format(FEEDBACK_ADDED, task.toFilteredString()), storage.addTask(task));
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(1, storage.getFileManager().getLineCount(todo));
	}

	/*
	 * Operation to test: editTask(Task task, Task editedTask): String
	 * Equivalence partition: 
	 * task: [null] [not null] 
	 * editedTask: [null] [not null]
	 */	
	@Test(expected = Exception.class) 
	public void testEditTaskMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = null, editedTask = null;
		try {	
			/* This is for the ‘task_null’ partition */
			storage.editTask(task, editedTask);
			
			task = new Task(PARAM_DESCRIPTION);
			
			/* This is for the ‘editedTask_null’ partition */
			storage.editTask(task, editedTask);	
		} catch (AssertionError ae) {
			throw new Exception();
		}
	}
	
	@Test
	public void testEditTaskMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		
		/* This is for program run where user hasn't added any task */
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		todo.delete();
		done.delete();
		storage.addTask(new Task(PARAM_DESCRIPTION));
		Task task = new Task(PARAM_DESCRIPTION), editedTask = new Task(PARAM_DESCRIPTION1, PARAM_START_TIME, PARAM_END_TIME);
		editedTask.setPriority(PARAM_PRIORITY);
		
		// This is for the ‘not null’ partition 
		assertEquals(String.format(FEEDBACK_UPDATED, editedTask.toFilteredString()), storage.editTask(task, editedTask));
	}
	
	/*
	 * Operation to test: viewTasks(String taskType): ArrayList<Task>
	 * Equivalence partition: 
	 * taskType: [null] [empty String] ["events"] ["deadlines"] ["tasks"] ["overdue"] ["done"] [any other string] 
	 */	
	@Test(expected = Exception.class) 
	public void testViewTasksMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		/* This is for the ‘null’ partition */
		storage.viewTasks(PARAM_VIEW_TASKS_NULL);
		
		/* This is for the ‘any other String’ partition */
		storage.viewTasks(PARAM_VIEW_TASKS_OTHERS);
	}
	
	@Test
	public void testViewTasksMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION1)); // floating task
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_END_TIME)); // deadline
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_START_TIME, PARAM_END_TIME)); // event
		storage.addTask(new Task(PARAM_DESCRIPTION1, Task.convertDateToString(new Date()), Task.convertDateToString(new Date()))); // overdue
		assertEquals(4, storage.getFileManager().getLineCount(todo));
		assertEquals(4, storage.getTaskList().getTasks().size());
		
		// This is for the ‘empty String’ partition 
		assertEquals(3, storage.viewTasks(PARAM_VIEW_TASKS_EMPTY).size());	
		
		// This is for the ‘events’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_EVENTS).size());
		
		// This is for the ‘deadlines’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_DEADLINES).size());
		
		// This is for the ‘tasks’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_FLOATING).size());	
		
		// This is for the ‘overdue’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_OVERDUE).size());
		
		// This is for the ‘done’ partition 
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DONE).size());		
	}
	
	/*
	 * Operation to test: searchTasks(String keyword): ArrayList<Task>
	 * Equivalence partition: 
	 * keyword: [null] [not null]
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testSearchTasksMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		try {
			/* This is for the ‘null’ partition */
			storage.searchTasks(PARAM_SEARCH_TASKS_NULL);
		} catch(AssertionError ae) {
			throw new Exception();
		}
	}
	
	@Test
	public void testSearchTasksMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION));
		storage.addTask(new Task(PARAM_DESCRIPTION1));
		assertEquals(2, storage.getTaskList().getTasks().size());
		assertEquals(2, storage.getFileManager().getLineCount(todo));
		
		// This is a boundary case for the ‘not null’ partition 
		assertEquals(2, storage.searchTasks(PARAM_SEARCH_TASKS_EMPTY).size());
		
		// These are for the ‘not null’ partition 
		assertEquals(1, storage.searchTasks(PARAM_SEARCH_TASKS_KEYWORD).size());
		assertEquals(2, storage.searchTasks(PARAM_SEARCH_TASKS_MORE_KEYWORD).size());
	}
	
	@Test
	public void testSearchTasksByDateMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_END_TIME2)); // deadline
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_END_TIME3)); // deadline
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_START_TIME, PARAM_END_TIME)); // event
		storage.addTask(new Task(PARAM_DESCRIPTION1, PARAM_START_TIME1, PARAM_END_TIME1)); // event
		assertEquals(4, storage.getTaskList().getTasks().size());
		assertEquals(4, storage.getFileManager().getLineCount(todo));
		
		// These are for the ‘not null’ partition 
		assertEquals(4, storage.searchTasksByDate(Task.convertStringToDate(PARAM_END_TIME)).size());
		assertEquals(3, storage.searchTasksByDate(Task.convertStringToDate(PARAM_END_TIME1)).size());
		assertEquals(2, storage.searchTasksByDate(Task.convertStringToDate(PARAM_END_TIME2)).size());
		assertEquals(1, storage.searchTasksByDate(Task.convertStringToDate(PARAM_END_TIME3)).size());
	}
	
	/*
	 * Operation to test: markTaskDone(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null] [already done] 
	 */	
	@Test(expected = Exception.class) 
	public void testMarkTaskDoneMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = null;
		
		// This is for the ‘null’ partition 
		storage.markTaskDone(task);
	}
	
	@Test(expected = Exception.class) 
	public void testMarkTaskDoneMethodForDoneTaskException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION1));
		Task task = new Task(PARAM_DESCRIPTION1);
		
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		assertEquals(0, storage.getFileManager().getLineCount(done));
		storage.markTaskDone(task);
		
		// This is for the ‘already done’ partition 
		storage.markTaskDone(task);
	}
	
	@Test
	public void testMarkTaskDoneMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION));
		Task task = new Task(PARAM_DESCRIPTION);
		
		// This is for the ‘not null’ partition
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		assertEquals(0, storage.getTaskList().getCompletedTasks().size());
		assertEquals(0, storage.getFileManager().getLineCount(done));
		assertEquals(String.format(FEEDBACK_MARKED_DONE, task.toFilteredString()), storage.markTaskDone(task));	
		assertEquals(1, storage.getFileManager().getLineCount(done));
		
		task = new Task(PARAM_DESCRIPTION1);
		storage.addTask(new Task(PARAM_DESCRIPTION1));
		assertEquals(String.format(FEEDBACK_MARKED_DONE, task.toFilteredString()), storage.markTaskDone(task));	
		assertEquals(2, storage.getFileManager().getLineCount(done));
	}
	
	/*
	 * Operation to test: deleteTask(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null]
	 */	
	@Test(expected = Exception.class) 
	public void testDeleteTaskMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = null;
		
		/* This is for the 'null' partition */
		storage.deleteTask(task);
	}	
	
	@Test
	public void testDeleteTaskMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		File todoBackup = new File(DIRECTORY_SYSTEM+FILENAME_TODO_BACKUP);
		File doneBackup = new File(DIRECTORY_SYSTEM+FILENAME_DONE_BACKUP);
		todoBackup.delete();
		doneBackup.delete();
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task(PARAM_DESCRIPTION));
		Task task = new Task(PARAM_DESCRIPTION);
		
		// This is for the 'not null' partition 
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(String.format(FEEDBACK_DELETED, task.toFilteredString()), storage.deleteTask(task));
		assertEquals(0, storage.getTaskList().getCompletedTasks().size());
	}
	
	/*
	 * Operation to test: restore(String previousCommand): String
	 * Equivalence partition: 
	 * previousCommand: [null] [not null] 
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testRestoreMethodForException() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		try {
			/* This is for the ‘null’ partition */
			storage.restore(PARAM_RESTORE_NULL);
			
			/* This is a boundary case for the ‘not null’ partition */
			storage.restore(PARAM_RESTORE_EMPTY);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		/* This is for the ‘not a valid directory’ partition */
		storage.setLocation(PARAM_SET_LOCATION_NOT_DIRECTORY);
	}
	
	@Test
	public void testRestoreMethod() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = new Task(PARAM_DESCRIPTION);
		
		assertEquals(0, storage.getTaskList().getTasks().size());
		assertEquals(0, storage.getFileManager().getLineCount(todo));
		assertEquals(0, storage.getFileManager().getLineCount(done));
		storage.addTask(task);
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		assertEquals(0, storage.getFileManager().getLineCount(done));
		
		
		storage.markTaskDone(task);	
		assertEquals(0, storage.getFileManager().getLineCount(todo));
		assertEquals(1, storage.getFileManager().getLineCount(done));
		
		assertEquals(String.format(FEEDBACK_RESTORED, PARAM_RESTORE_COMMAND), storage.restore(PARAM_RESTORE_COMMAND));
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		assertEquals(0, storage.getFileManager().getLineCount(done));
	}
}
