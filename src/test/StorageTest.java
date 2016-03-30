package test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.storage.Storage;

public class StorageTest {
	
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	
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
	private static final String PARAM_RESTORE_NULL = null;
	private static final String PARAM_RESTORE_EMPTY = "";
	private static final String PARAM_RESTORE_COMMAND = "delete 1";
	
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
		try {
			/* This is for the ‘null’ partition */
			storage.setLocation(PARAM_SET_LOCATION_NULL);
			
			/* This is a boundary case for the ‘not null’ partition */
			storage.setLocation(PARAM_SET_LOCATION_EMPTY);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		/* This is for the ‘not a valid directory’ partition */
		storage.setLocation(PARAM_SET_LOCATION_NOT_DIRECTORY);
	}
	
	@Test
	public void testSetLocationMethod() throws Exception {
		Storage storage = new Storage();
		/* This is for the ‘a valid directory’ partition */
		assertEquals(String.format(FEEDBACK_LOCATION_SET, PARAM_SET_LOCATION_DIRECTORY), storage.setLocation(PARAM_SET_LOCATION_DIRECTORY));
		assertEquals(PARAM_SET_LOCATION_DIRECTORY, storage.getLocation());
	}
	
	/*
	 * Operation to test: addTask(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null] 
	 */	
	@Test(expected = Exception.class) 
	public void testAddTaskMethodForException() throws Exception {
		Storage storage = new Storage();
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
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		Task task = new Task("go swimming");
		
		/* This is for the ‘not null’ partition */
		assertEquals(0, storage.getTaskList().getTasks().size());
		assertEquals(String.format(FEEDBACK_ADDED, task.toFilteredString()), storage.addTask(task));
		assertEquals(1, storage.getTaskList().getTasks().size());
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
			
			task = new Task("go swimming");
			
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
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task("go swimming"));
		Task task = new Task("go swimming"), editedTask = new Task("go gym");
		
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
		storage.addTask(new Task("go gym"));
		assertEquals(1, storage.getTaskList().getTasks().size());
		
		// This is for the ‘empty String’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_EMPTY).size());	
		
		// This is for the ‘events’ partition 
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_EVENTS).size());
		
		// This is for the ‘deadlines’ partition 
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DEADLINES).size());
		
		// This is for the ‘tasks’ partition 
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_FLOATING).size());	
		
		// This is for the ‘overdue’ partition 
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_OVERDUE).size());
		
		// This is for the ‘done’ partition 
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DONE).size());		
	}
	
	/*
	 * Operation to test: searchTasks(String keyword): ArrayList<Task>
	 * Equivalence partition: 
	 * keyword: [null] [not null]
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test
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
		storage.addTask(new Task("go gym"));
		assertEquals(1, storage.getTaskList().getTasks().size());
		
		// This is a boundary case for the ‘not null’ partition 
		assertEquals(1, storage.searchTasks(PARAM_SEARCH_TASKS_EMPTY).size());
		
		// This is for the ‘not null’ partition 
		assertEquals(1, storage.searchTasks(PARAM_SEARCH_TASKS_KEYWORD).size());
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
	public void testMarkTaskDoneMethodForException1() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task("go gym"));
		Task task = new Task("go gym");
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
		storage.addTask(new Task("go gym"));
		Task task = new Task("go gym");
		
		// This is for the ‘not null’ partition
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(0, storage.getTaskList().getCompletedTasks().size());
		assertEquals(String.format(FEEDBACK_MARKED_DONE, task.toFilteredString()), storage.markTaskDone(task));	
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
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		storage.addTask(new Task("go gym"));
		Task task = new Task("go gym");
		
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
		assertEquals(0, storage.getTaskList().getTasks().size());
		storage.addTask(new Task("go gym"));
		assertEquals(1, storage.getTaskList().getTasks().size());
		assertEquals(String.format(FEEDBACK_RESTORED, PARAM_RESTORE_COMMAND), storage.restore(PARAM_RESTORE_COMMAND));
		//assertEquals(0, storage.getTaskList().getTasks().size());
	}
}
