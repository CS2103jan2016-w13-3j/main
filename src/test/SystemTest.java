package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.logic.Logic;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class SystemTest {

	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	
	private static final String COMMAND_NULL = null;
	private static final String COMMAND_EMPTY = "";
	private static final String COMMAND_INVALID = "abcd";
	private static final String COMMAND_SET_LOCATION_EMPTY = "location";
	private static final String COMMAND_SET_LOCATION_NOT_DIRECTORY = "location C:\\Users\\Public\\Documents\\SimplyAmazing";
	private static final String COMMAND_SET_LOCATION_DIRECTORY = "location C:\\Users\\Public\\Documents";
	private static final String COMMAND_ADD_TASK_EMPTY = "add";
	private static final String COMMAND_ADD_TASK_WITH_PASSED_DEADLINE = "add sleep by 3:00 24 Mar 2016";
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_ONLY = "add sleep from 3:00 24 Mar 2017";
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME = "add sleep from 3:00 24 Mar 2017 to 2:00 24 Mar 2017";
	private static final String COMMAND_ADD_FLOATING_TASK = "add hello world";
	private static final String COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS = "add drop by post office to deliver parcel received from Landon";
	private static final String COMMAND_ADD_DEADLINE = "add cs2103 peer review by 23:59 25 Mar 2017";
	private static final String COMMAND_ADD_DEADLINE_WITH_KEYWORDS = "add drop by post office to deliver parcel received from Landon by 23:59 25 Mar 2017";
	private static final String COMMAND_ADD_EVENT = "add hackathon in SOC from 09:30 26 Mar 2017 to 10:00 27 Mar 2017";
	private static final String COMMAND_ADD_EVENT_WITH_KEYWORDS = "add drop by post office to deliver parcel received from Landon from 09:30 26 Mar 2017 to 10:00 27 Mar 2017";
	private static final String COMMAND_VIEW_TASKS_EMPTY = "view";
	private static final String COMMAND_VIEW_TASKS_EVENTS = "view events";
	private static final String COMMAND_VIEW_TASKS_DEADLINES = "view deadlines";
	private static final String COMMAND_VIEW_TASKS_FLOATING = "view tasks";
	private static final String COMMAND_VIEW_TASKS_OVERDUE = "view overdue";
	private static final String COMMAND_VIEW_TASKS_DONE = "view done";
	private static final String COMMAND_VIEW_TASKS_OTHERS = "view other";
	private static final String COMMAND_SEARCH_TASKS_EMPTY = "search";
	private static final String COMMAND_SEARCH_TASKS_KEYWORD = "search gym";
	private static final String COMMAND_RESTORE_EMPTY = "";
	private static final String COMMAND_RESTORE = "restore";
	
	private static final String PARAM_SET_LOCATION_DIRECTORY = "C:\\Users\\Public\\Documents";
	private static final String PARAM_VIEW_TASKS_EMPTY = "";
	private static final String PARAM_VIEW_TASKS_EVENTS = "events";
	private static final String PARAM_VIEW_TASKS_DEADLINES = "deadlines";
	private static final String PARAM_VIEW_TASKS_FLOATING = "tasks";
	private static final String PARAM_VIEW_TASKS_OVERDUE = "overdue";
	private static final String PARAM_VIEW_TASKS_DONE = "done";
	private static final String PARAM_VIEW_TASKS_OTHERS = "other";
	private static final String PARAM_SEARCH_TASKS_EMPTY = "";
	private static final String PARAM_SEARCH_TASKS_KEYWORD = "gym";
	private static final String PARAM_RESTORE_EMPTY = "";
	private static final String PARAM_RESTORE_COMMAND = "delete 1";
	
	private static final String FEEDBACK_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String FEEDBACK_ADDED = "%1$s has been added.";
	private static final String FEEDBACK_UPDATED = "%1$s has been successfully updated.";
	private static final String FEEDBACK_MARKED_DONE = "%1$s has been marked as done.";
	private static final String FEEDBACK_DELETED = "%1$s has been successfully deleted.";
	private static final String FEEDBACK_RESTORED = "\"%1$s\" command has been successfully undone.";
	
	
	/*
	 * Operation to test: executeCommand(String command): String
	 * Equivalence partition: 
	 * command: [null] [not null] [valid] [not valid]
	 * Boundary values: Empty String, a String of some length
	 */	
	@Test(expected = Exception.class) 
	public void testInvalidCommandsForException() throws Exception {
		Logic logic = new Logic();
		try {
			/* This is for the ‘null’ partition */
			logic.executeCommand(COMMAND_NULL);
			
			/* This is a boundary case for the ‘not null’ partition */
			logic.executeCommand(COMMAND_EMPTY);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		/* This is for the ‘not valid’ partition */
		logic.executeCommand(COMMAND_INVALID);
	}
	
	@Test(expected = Exception.class) 
	public void testSetLocationCommandForException() throws Exception {
		Logic logic = new Logic();
		try {
			/* This is a boundary case for the ‘not null’ partition */
			logic.executeCommand(COMMAND_SET_LOCATION_EMPTY);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		/* This is for the ‘not valid’ partition */
		logic.executeCommand(COMMAND_SET_LOCATION_NOT_DIRECTORY);
	}
	
	@Test
	public void testSetLocationCommand() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		/* This is for the ‘valid’ partition */
		assertEquals(String.format(FEEDBACK_LOCATION_SET, PARAM_SET_LOCATION_DIRECTORY), logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY));
		assertEquals(PARAM_SET_LOCATION_DIRECTORY, storage.getLocation());
	}
	
	@Test(expected = Exception.class) 
	public void testAddTaskCommandForException() throws Exception {
		Logic logic = new Logic();
		try {	
			/* These are for the ‘invalid’ partition */
			logic.executeCommand(COMMAND_ADD_TASK_EMPTY);
			logic.executeCommand(COMMAND_ADD_TASK_WITH_PASSED_DEADLINE);
			logic.executeCommand(COMMAND_ADD_TASK_WITH_STARTIME_ONLY);
			logic.executeCommand(COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME);
		} catch (AssertionError ae) {
			throw new Exception();
		}
	}
	
	@Test
	public void testAddTaskCommand() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		assertEquals(0, storage.getFileManager().getLineCount(todo));
		
		/* These are for the ‘not null’ partition */
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_FLOATING_TASK));
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS));
		assertEquals(2, storage.getFileManager().getLineCount(todo));
		
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_DEADLINE));
		assertEquals(3, storage.getFileManager().getLineCount(todo));
		
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_DEADLINE_WITH_KEYWORDS).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_DEADLINE_WITH_KEYWORDS));
		assertEquals(4, storage.getFileManager().getLineCount(todo));
		
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_EVENT).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_EVENT));
		assertEquals(5, storage.getFileManager().getLineCount(todo));
		
		assertEquals(String.format(FEEDBACK_ADDED, parser.getHandler(COMMAND_ADD_EVENT_WITH_KEYWORDS).getTask().toFilteredString()), logic.executeCommand(COMMAND_ADD_EVENT_WITH_KEYWORDS));
		assertEquals(6, storage.getFileManager().getLineCount(todo));
	}
}
