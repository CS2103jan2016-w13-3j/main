package test;

import static org.junit.Assert.assertEquals;
import junit.framework.Assert;

import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.storage.Storage;

public class StorageTest {
	
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
	private static final String PARAM_SEARCH_TASKS_KEYWORD = "swimming";
	private static final String PARAM_RESTORE_NULL = null;
	private static final String PARAM_RESTORE_EMPTY = "";
	private static final String PARAM_RESTORE_COMMAND = "delete 1";
	
	private static final String FEEDBACK_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String FEEDBACK_LOCATION_NOT_SET = "Storage location of task data is has not been set. Please enter \"location <directory>\" command to set the storage location.";
	private static final String FEEDBACK_NOT_DIRECTORY = "Provided storage location is not a valid directory.";
	private static final String FEEDBACK_ADDED = "%1$s has been added.";
	private static final String FEEDBACK_UPDATED = "%1$s has been successfully updated.";
	private static final String FEEDBACK_INVALID_TASK = "Provided task is invalid.";
	private static final String FEEDBACK_INVALID_TASK_TYPE = "Unrecognized task type!";
	private static final String FEEDBACK_INVALID_KEYWORD = "Provided keyword is invalid.";
	private static final String FEEDBACK_MARKED_DONE = "%1$s has been marked as done.";
	private static final String FEEDBACK_COMPLETED_TASK = "%1$s is a completed task.";
	private static final String FEEDBACK_DELETED = "%1$s has been successfully deleted.";
	private static final String FEEDBACK_RESTORED = "\"%1$s\" command has been successfully undone.";
	private static final String FEEDBACK_NOT_RESTORED = "Undo operation failed.";
	
	Storage storage = new Storage();
	
	/*
	 * Operation to test: setLocation(String location): String
	 * Equivalence partition: 
	 * location: [null] [not null] [not a valid directory] [a valid directory] 
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testSetLocationMethod() throws Exception {
		
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_LOCATION_NOT_SET), storage.setLocation(PARAM_SET_LOCATION_NULL));
		
		/* This is a boundary case for the ‘not null’ partition */
		assertEquals(new Exception(FEEDBACK_LOCATION_NOT_SET), storage.setLocation(PARAM_SET_LOCATION_EMPTY));
		
		/* This is for the ‘not a valid directory’ partition */
		assertEquals(new Exception(FEEDBACK_NOT_DIRECTORY), storage.setLocation(PARAM_SET_LOCATION_NOT_DIRECTORY));
	}
	
	@Test
	public void testSetLocationMethodWithoutException() throws Exception {
		/* This is for the ‘a valid directory’ partition */
		assertEquals(String.format(FEEDBACK_LOCATION_SET, PARAM_SET_LOCATION_DIRECTORY), storage.setLocation(PARAM_SET_LOCATION_DIRECTORY));
	}
	
	/*
	 * Operation to test: addTask(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null] 
	 */	
	@Test(expected = Exception.class) 
	public void testAddTaskMethod() throws Exception {
		
		Task task = null;
		
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK), storage.addTask(task));
	}
	
	@Test
	public void testAddTaskMethodWithoutException() throws Exception {
		
		Task task = new Task("go swimming");
		/* This is for the ‘not null’ partition */
		assertEquals(String.format(FEEDBACK_ADDED, task.toFilteredString()), storage.addTask(task));
	}
	
	/*
	 * Operation to test: editTask(Task task, Task editedTask): String
	 * Equivalence partition: 
	 * task: [null] [not null] 
	 * editedTask: [null] [not null]
	 */	
	@Test(expected = Exception.class) 
	public void testEditTaskMethod() throws Exception {
		
		Task task = null, editedTask = null;
		
		/* This is for the ‘task_null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK), storage.editTask(task, editedTask));
		
		task = new Task("go swimming");
		
		/* This is for the ‘editedTask_null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK), storage.editTask(task, editedTask));	
	}
	
	/*
	@Test
	public void testEditTaskMethodWithoutException() throws Exception {
		
		Task task = new Task("go swimming"), editedTask = new Task("go gym");
		
		// This is for the ‘not null’ partition 
		assertEquals(String.format(FEEDBACK_UPDATED, editedTask.toFilteredString()), storage.editTask(task, editedTask));
	}*/
	
	/*
	 * Operation to test: viewTasks(String taskType): ArrayList<Task>
	 * Equivalence partition: 
	 * taskType: [null] [empty String] ["events"] ["deadlines"] ["tasks"] ["overdue"] ["done"] [any other string] 
	 */	
	@Test(expected = Exception.class) 
	public void testViewTasksMethod() throws Exception {
		
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK_TYPE), storage.viewTasks(PARAM_VIEW_TASKS_NULL));
		
		/* This is for the ‘any other String’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK_TYPE), storage.viewTasks(PARAM_VIEW_TASKS_OTHERS));
	}
	
	@Test
	public void testViewTasksMethodWithoutException() throws Exception {
		
		/* This is for the ‘empty String’ partition */
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_EMPTY).size());	
		
		/* This is for the ‘events’ partition */
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_EVENTS).size());
		
		/* This is for the ‘deadlines’ partition */
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DEADLINES).size());
		
		/* This is for the ‘tasks’ partition */
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_FLOATING).size());	
		
		/* This is for the ‘overdue’ partition */
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_OVERDUE).size());
		
		/* This is for the ‘done’ partition */
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DONE).size());		
	}
	
	/*
	 * Operation to test: searchTasks(String keyword): ArrayList<Task>
	 * Equivalence partition: 
	 * keyword: [null] [not null]
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testSearchTasksMethod() throws Exception {
		
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_KEYWORD), storage.searchTasks(PARAM_SEARCH_TASKS_NULL));
	}
	
	@Test
	public void testSearchTasksMethodWithoutException() throws Exception {

		/* This is a boundary case for the ‘not null’ partition */
		assertEquals(1, storage.searchTasks(PARAM_SEARCH_TASKS_EMPTY).size());
		
		/* This is for the ‘not null’ partition */
		assertEquals(1, storage.searchTasks(PARAM_SEARCH_TASKS_KEYWORD).size());
	}
	
	/*
	 * Operation to test: markTaskDone(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null] [already done] 
	 */	
	@Test(expected = Exception.class) 
	public void testMarkTaskDoneMethod() throws Exception {
		
		Task task = null;
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK), storage.markTaskDone(task));
		
		/* This is for the ‘already done’ partition */
		assertEquals(new Exception(String.format(FEEDBACK_COMPLETED_TASK, task.toFilteredString())), storage.markTaskDone(task));
	}
	
	/*
	@Test
	public void testMarkTaskDoneMethodWithoutException() throws Exception {
		Task task = new Task("go gym");
		
		// This is for the ‘not null’ partition 
		assertEquals(String.format(FEEDBACK_MARKED_DONE, task.toFilteredString()), storage.markTaskDone(task));	
	}*/
	
	/*
	 * Operation to test: deleteTask(Task task): String
	 * Equivalence partition: 
	 * task: [null] [not null]
	 */	
	@Test(expected = Exception.class) 
	public void testDeleteTaskMethod() throws Exception {
		
		Task task = null;
		/* This is for the ‘null’ partition */
		assertEquals(new Exception(FEEDBACK_INVALID_TASK), storage.deleteTask(task));
	}	
	
	@Test
	public void testDeleteTaskMethodWithoutException() throws Exception {
		
		Task task = new Task("go gym");
		
		/* This is for the ‘not null’ partition */
		assertEquals(String.format(FEEDBACK_DELETED, task.toFilteredString()), storage.deleteTask(task));
	}
}
