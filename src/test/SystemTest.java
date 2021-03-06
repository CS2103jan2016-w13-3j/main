//@@author A0126289W
package test;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import simplyamazing.logic.Logic;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class SystemTest { 

	private static final String INDEX_THREE = "3";
	private static final String INDEX_TWO = "2";
	private static final String CHARACTER_SEPARATOR = ",";
	private static final String INDEX_ONE = "1";
	private static final String CHARACTER_NEW_LINE = "\n";
	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmazing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	
	private static final String CHARACTER_SPACE = " ";
	
	private static final String COMMAND_NULL = null;
	private static final String COMMAND_EMPTY = "";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";
	private static final String COMMAND_SET_LOCATION = "location";
	private static final String COMMAND_MARK_AS_DONE = "done";
	private static final String COMMAND_UNMARK = "unmark";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_INVALID = "abcd";
	
	private static final String TASK_TYPE_EVENTS = "events";
	private static final String TASK_TYPE_DEADLINES = "deadlines";
	private static final String TASK_TYPE_FLOATING = "tasks";
	private static final String TASK_TYPE_OVERDUE = "overdue";
	private static final String TASK_TYPE_DONE = "done";
	
	private static final String COMMAND_SET_LOCATION_EMPTY = COMMAND_SET_LOCATION;
	private static final String COMMAND_SET_LOCATION_NOT_DIRECTORY = COMMAND_SET_LOCATION + CHARACTER_SPACE 
			+ "C:\\Users\\Public\\Documents\\SimplyAmazing";
	private static final String COMMAND_SET_LOCATION_DIRECTORY = COMMAND_SET_LOCATION + CHARACTER_SPACE 
			+ "C:\\Users\\Public\\Documents";
	
	private static final String COMMAND_ADD_TASK_EMPTY = COMMAND_ADD;
	private static final String COMMAND_ADD_TASK_WITH_PASSED_DEADLINE = COMMAND_ADD + CHARACTER_SPACE 
			+ "sleep by 3:00 24 Mar 2016";;
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_ONLY = COMMAND_ADD + CHARACTER_SPACE 
			+ "sleep from 13:00 24 May 2017";
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME = COMMAND_ADD + CHARACTER_SPACE 
			+ "sleep from 10:00 24 May 2017 to 8:00 24 May 2017";
	private static final String COMMAND_ADD_FLOATING_TASK = COMMAND_ADD + CHARACTER_SPACE + "hello world";
	private static final String COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE 
			+ "drop by post office to deliver parcel received from Lndon";
	private static final String COMMAND_ADD_DEADLINE = COMMAND_ADD + CHARACTER_SPACE 
			+ "cs2103 peer review by 23:59 25 May 2017";
	private static final String COMMAND_ADD_DEADLINE_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE 
			+ "drop by post office to deliver parcel received from Landon by 23:59 25 May 2017";
	private static final String COMMAND_ADD_EVENT = COMMAND_ADD + CHARACTER_SPACE 
			+ "hackathon in SOC from 09:30 26 May 2017 to 10:00 27 May 2017";
	private static final String COMMAND_ADD_EVENT_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE 
			+ "drop by post office to deliver parcel received from Landon "
			+ "from 09:30 26 May 2017 to 10:00 27 May 2017";
	
	private static final String COMMAND_VIEW_TASKS_EMPTY = COMMAND_VIEW;
	private static final String COMMAND_VIEW_TASKS_EVENTS = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_EVENTS;
	private static final String COMMAND_VIEW_TASKS_DEADLINES = COMMAND_VIEW + CHARACTER_SPACE 
			+ TASK_TYPE_DEADLINES;
	private static final String COMMAND_VIEW_TASKS_FLOATING = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_FLOATING;
	private static final String COMMAND_VIEW_TASKS_OVERDUE = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_OVERDUE;
	private static final String COMMAND_VIEW_TASKS_DONE = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_DONE;
	private static final String COMMAND_VIEW_TASKS_OTHERS = COMMAND_VIEW + CHARACTER_SPACE + "other";
	
	private static final String COMMAND_SEARCH_TASKS_EMPTY = COMMAND_SEARCH;
	private static final String COMMAND_SEARCH_TASKS_KEYWORD =  COMMAND_SEARCH + CHARACTER_SPACE + "hello";
	private static final String COMMAND_SEARCH_TASKS_OTHER_KEYWORD =  COMMAND_SEARCH + CHARACTER_SPACE + "other";
	
	private static final String COMMAND_HELP_INTEGER = COMMAND_HELP + CHARACTER_SPACE + INDEX_ONE;
	private static final String COMMAND_HELP_INVALID = COMMAND_HELP + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_HELP_ALL = COMMAND_HELP;
	private static final String COMMAND_HELP_LOCATION = COMMAND_HELP + CHARACTER_SPACE + COMMAND_SET_LOCATION;
	private static final String COMMAND_HELP_ADD = COMMAND_HELP + CHARACTER_SPACE + COMMAND_ADD;
	private static final String COMMAND_HELP_VIEW = COMMAND_HELP + CHARACTER_SPACE + COMMAND_VIEW;
	private static final String COMMAND_HELP_EDIT = COMMAND_HELP + CHARACTER_SPACE + COMMAND_EDIT;
	private static final String COMMAND_HELP_DELETE = COMMAND_HELP + CHARACTER_SPACE + COMMAND_DELETE;
	private static final String COMMAND_HELP_UNDO = COMMAND_HELP + CHARACTER_SPACE + COMMAND_UNDO;
	private static final String COMMAND_HELP_MARK = COMMAND_HELP + CHARACTER_SPACE + "mark";
	private static final String COMMAND_HELP_SEARCH = COMMAND_HELP + CHARACTER_SPACE + COMMAND_SEARCH;
	private static final String COMMAND_HELP_EXIT = COMMAND_HELP + CHARACTER_SPACE + COMMAND_EXIT;
	private static final String COMMAND_HELP_REDO = COMMAND_HELP + CHARACTER_SPACE + COMMAND_REDO;
	private static final String COMMAND_HELP_UNMARK = COMMAND_HELP + CHARACTER_SPACE + COMMAND_UNMARK;
	
	private static final String COMMAND_DELETE_NEGATIVE_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "-1";
	private static final String COMMAND_DELETE_ZERO_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "0";
	private static final String COMMAND_DELETE_LARGER_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "4";
	private static final String COMMAND_DELETE_STRING = COMMAND_DELETE + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_DELETE_MULTIPLE_INVALID = COMMAND_DELETE + CHARACTER_SPACE + "1 2 4";
	private static final String COMMAND_DELETE_SINGLE = COMMAND_DELETE + CHARACTER_SPACE + INDEX_ONE;
	private static final String COMMAND_DELETE_MULTIPLE = COMMAND_DELETE + CHARACTER_SPACE + "1 2";
	
	private static final String COMMAND_MARK_NEGATIVE_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "-1";
	private static final String COMMAND_MARK_ZERO_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "0";
	private static final String COMMAND_MARK_LARGER_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "4";
	private static final String COMMAND_MARK_STRING = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_MARK_MULTIPLE_INVALID = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "1 2 4";
	private static final String COMMAND_MARK_SINGLE = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + INDEX_ONE;
	private static final String COMMAND_MARK_MULTIPLE = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "1 2";
	
	private static final String PARAM_SET_LOCATION_DIRECTORY = "C:\\Users\\Public\\Documents";
	private static final String PARAM_VIEW_TASKS_EMPTY = "";
	private static final String PARAM_VIEW_TASKS_EVENTS = "events";
	private static final String PARAM_VIEW_TASKS_DEADLINES = "deadlines";
	private static final String PARAM_VIEW_TASKS_FLOATING = "tasks";
	private static final String PARAM_VIEW_TASKS_OVERDUE = "overdue";
	private static final String PARAM_VIEW_TASKS_DONE = "done";
	
	private static final String FEEDBACK_LOCATION_SET = "Storage location of task data "
			+ "has been sucessfully set as %1$s.";
	private static final String FEEDBACK_LOCATION_INVALID = "Error: Location provided is invalid";
	private static final String FEEDBACK_LOCATION_NOT_DIRECTORY = "Error: Not a valid directory";
	
	private static final String FEEDBACK_ADDED = "%1$s has been added.";
	private static final String FEEDBACK_ADD_TASK_FIELDS_NOT_CORRECT = "Error: Please ensure the fields "
			+ "are correct";
	private static final String FEEDBACK_ADD_TASK_START_AFTER_END ="Error: Start date and time cannot be "
			+ "after the End date and time";
	private static final String FEEDBACK_ADD_TASK_DATE_BEFORE_CURRENT ="Error: Time provided must be "
			+ "after the current time";
	
	private static final String FEEDBACK_MARKED_DONE = "%1$s has been marked as done.";
	private static final String FEEDBACK_DELETED = "%1$s has been successfully deleted.";
	private static final String FEEDBACK_UNDO = "\"%1$s\" command has been successfully undone.";
	private static final String FEEDBACK_REDO = "\"%1$s\" command has been successfully executed again.";
	private static final Object FEEDBACK_EMPTY_LIST = "List is empty";
	private static final Object FEEDBACK_NO_TASK_FOUND = "There are no tasks containing the given keyword";
	private static final String FEEDBACK_INVALID_INDEX= "Error: The Index entered is invalid";
	private static final String FEEDBACK_INDEX_IS_STRING = "Error: Index provided is not an Integer.";
	private static final String FEEDBACK_MULTIPLE_INVALID = "Error: One of the given indexes is invalid";
	private static final String FEEDBACK_MULTIPLE_DELETE_VALID = "Provided tasks have been successfully deleted.";
	private static final String FEEDBACK_MULTIPLE_DONE_VALID = "Provided tasks have been marked as done.";
	private static final String FEEDBACK_NOTHING_TO_UNDO = "Error: There is no previous command to undo";
	private static final String FEEDBACK_NOTHING_TO_REDO = "Error: There is no previous command to redo";
	
	//@@author A0125136N
	private static final String FEEDBACK_HELP_UNDO = "Undo the most recent command\nCommand: undo\n";;
	private static final String FEEDBACK_HELP_REDO = "Redo the most recent command\nCommand: redo\n";
	private static final String FEEDBACK_HELP_UNMARK = "Unmarks a completed task\n"
			+ "Command: undone <task index>\n\nExample:\nundone 2\n\n\n"
			+ "Note: You may also use the keyword \"unmark\" instead of \"undone\"";
	private static final String FEEDBACK_HELP_MARK = "Marks task as completed\n"
			+ "Command: done <task index>\n\nExample:\ndone 2\n\n\n"
			+ "Note: You may also use the keywords \"mark\", \"complete\" or \"finish\" instead of \"done\"";
	
	private static final String FEEDBACK_HELP_DELETE = "Delete task from list\n"
			+ "Command: delete <task index>\n\nExample:\ndelete 1\n\n\n"
			+ "Note: You may also use the keywords \"-\", \"del\", \"remove\" or \"cancel\" "
			+ "instead of \"delete\"";
	
	private static final String FEEDBACK_HELP_SEARCH = "Search for tasks containing the given keyword or date \n"
			+ "Command: search <keyword> or search<date>\n\nExample:\nsearch meeting\n\n\n"
			+ "Note: You may also use the keyword \"find\" instead of \"search\"";
	
	private static final String FEEDBACK_HELP_EXIT = "Exits SimplyAmazing\nCommand: exit\n\n\nNote: "
			+ "You may also use \"logout\" or \"quit\" instead of \"exit\"";
	
	private static final String FEEDBACK_HELP_ALL = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help search \n6. help mark\n"
			+ "7. help unmark\n8. help undo\n9. help redo\n10. help location \n11. help exit\n";
	
	private static final String FEEDBACK_HELP_VIEW = "1.Display all tasks\n Command: view\n\n"
			+ "2.Display tasks with deadlines\n"
			+ "Command: view deadlines\n\n3.Display all events\nCommand: view events\n\n"
			+ "4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n6.Display overdue tasks\n"
			+ "Command: view overdue\n\n\n"
			+ "Note: You may also use the keywords \"display\", \"show\" or \"list\" instead of \"view\"";
	
	private static final String FEEDBACK_HELP_EDIT =  "Edit content in a task\n"
			+ "Command: edit <task index> <task header> <updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n"
			+ "2. edit 3 start 22:00 26 may 2016, end 22:40 26 may 2016\n\n"
			+ "3. edit 1 priority high\n\n\n"
			+ "Note: You may also use the keywords \"change\" or \"update\" instead of \"edit\"";
	
	private static final String FEEDBACK_HELP_ADD =  "1.Add a task to the list\n"
			+ "Command: add <task description>\n\nExample: add Prepare presentation\n\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> "
			+ "from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> "
			+ "by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Submit marketing report by 17:00 20 Dec 2016\n\n\n"
			+ "Note: You may use the keyword \"+\" instead of \"add\"";
	
	
	private static final String FEEDBACK_HELP_LOCATION = "Sets the storage location or folder "
			+ "for application data\nCommand: location <path>\n" + CHARACTER_NEW_LINE
			+ "Example:\nlocation C:\\Users\\Jim\\Desktop\\Task Data\n\n\n"
			+ "Note: You may also use the keywords \"path\" or \"address\" instead of \"location\"";

	//@@author A0126289W
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
			/* This is for the �null� partition */
			logic.executeCommand(COMMAND_NULL);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		try {
			/* This is a boundary case for the �not null� partition */
			logic.executeCommand(COMMAND_EMPTY);
		} catch (AssertionError ae) {
			throw new Exception();
		}
		
		/* This is for the �not valid� partition */
		logic.executeCommand(COMMAND_INVALID);
	}
	
	@Test
	public void testSetLocationCommand() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		/* These are for the �not valid� partition */
		assertEquals(true, parser.getHandler(COMMAND_SET_LOCATION_EMPTY).getHasError());
		assertEquals(COMMAND_SET_LOCATION, parser.getHandler(COMMAND_SET_LOCATION_EMPTY).getCommandType());
		assertEquals(FEEDBACK_LOCATION_INVALID, logic.executeCommand(COMMAND_SET_LOCATION_EMPTY));
		
		assertEquals(COMMAND_SET_LOCATION, parser.getHandler(COMMAND_SET_LOCATION_NOT_DIRECTORY).getCommandType());
		assertEquals(FEEDBACK_LOCATION_NOT_DIRECTORY, logic.executeCommand(COMMAND_SET_LOCATION_NOT_DIRECTORY));
		
		/* This is for the �valid� partition */
		assertEquals(false, parser.getHandler(COMMAND_SET_LOCATION_DIRECTORY).getHasError());
		assertEquals(COMMAND_SET_LOCATION, parser.getHandler(COMMAND_SET_LOCATION_DIRECTORY).getCommandType());
		assertEquals(String.format(FEEDBACK_LOCATION_SET, PARAM_SET_LOCATION_DIRECTORY), 
				logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY));
		assertEquals(PARAM_SET_LOCATION_DIRECTORY, storage.getLocation());
	}
	
	@Test
	public void testAddTaskCommand() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		/* This is for the first launch of program where user hasn't set the storage location */
		File location = new File(DIRECTORY_SYSTEM+FILENAME_STORAGE);
		storage.getFileManager().createNewFile(location);
		File todo = new File(DIRECTORY_SYSTEM+FILENAME_TODO);
		File done = new File(DIRECTORY_SYSTEM+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		assertEquals(0, storage.getFileManager().getLineCount(todo));
		
		/* These are for the �invalid� partition */
		assertEquals(true, parser.getHandler(COMMAND_ADD_TASK_EMPTY).getHasError());
		assertEquals(FEEDBACK_ADD_TASK_FIELDS_NOT_CORRECT, logic.executeCommand(COMMAND_ADD_TASK_EMPTY));
		
		assertEquals(true, parser.getHandler(COMMAND_ADD_TASK_WITH_PASSED_DEADLINE).getHasError());
		assertEquals(FEEDBACK_ADD_TASK_DATE_BEFORE_CURRENT, 
				logic.executeCommand(COMMAND_ADD_TASK_WITH_PASSED_DEADLINE));
		
		assertEquals(true, parser.getHandler(COMMAND_ADD_TASK_WITH_STARTIME_ONLY).getHasError());
		assertEquals(FEEDBACK_ADD_TASK_FIELDS_NOT_CORRECT, 
				logic.executeCommand(COMMAND_ADD_TASK_WITH_STARTIME_ONLY));
		
		assertEquals(true, parser.getHandler(COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME).getHasError());
		assertEquals(FEEDBACK_ADD_TASK_START_AFTER_END, 
				logic.executeCommand(COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME));
		
		/* These are for the �valid� partition */
		assertEquals(false, parser.getHandler(COMMAND_ADD_FLOATING_TASK).getHasError());
		assertEquals(COMMAND_ADD, parser.getHandler(COMMAND_ADD_FLOATING_TASK).getCommandType());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_FLOATING_TASK));
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		
		assertEquals(false, parser.getHandler(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS).getHasError());
		assertEquals(COMMAND_ADD, parser.getHandler(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS).getCommandType());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS));
		assertEquals(2, storage.getFileManager().getLineCount(todo));
		
		assertEquals(false, parser.getHandler(COMMAND_ADD_DEADLINE).getHasError());
		assertEquals(COMMAND_ADD, parser.getHandler(COMMAND_ADD_DEADLINE).getCommandType());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_DEADLINE));
		assertEquals(3, storage.getFileManager().getLineCount(todo));
		
		assertEquals(false, parser.getHandler(COMMAND_ADD_DEADLINE_WITH_KEYWORDS).getHasError());
		assertEquals(COMMAND_ADD, parser.getHandler(COMMAND_ADD_DEADLINE_WITH_KEYWORDS).getCommandType());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_DEADLINE_WITH_KEYWORDS).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_DEADLINE_WITH_KEYWORDS));
		assertEquals(4, storage.getFileManager().getLineCount(todo));
		
		assertEquals(false, parser.getHandler(COMMAND_ADD_EVENT).getHasError());
		assertEquals(COMMAND_ADD, parser.getHandler(COMMAND_ADD_EVENT).getCommandType());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_EVENT).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_EVENT));
		assertEquals(5, storage.getFileManager().getLineCount(todo));
		
		assertEquals(false, parser.getHandler(COMMAND_ADD_EVENT_WITH_KEYWORDS).getHasError());
		assertEquals(String.format(FEEDBACK_ADDED, 
				parser.getHandler(COMMAND_ADD_EVENT_WITH_KEYWORDS).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_ADD_EVENT_WITH_KEYWORDS));
		assertEquals(6, storage.getFileManager().getLineCount(todo));
	}
	
	@Test
	public void testViewTasksMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		logic.executeCommand(COMMAND_ADD_FLOATING_TASK); // add floating task
		logic.executeCommand(COMMAND_ADD_DEADLINE); // add deadline
		logic.executeCommand(COMMAND_ADD_EVENT); // add event
		assertEquals(3, storage.getFileManager().getLineCount(todo));
		
		/* This is for the �invalid� partition */
		assertEquals(true, parser.getHandler(COMMAND_VIEW_TASKS_OTHERS).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_OTHERS).getCommandType());
		assertEquals(parser.getHandler(COMMAND_VIEW_TASKS_OTHERS).getFeedBack(), 
				logic.executeCommand(COMMAND_VIEW_TASKS_OTHERS));
		
		// These are for the �valid� partition 
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_EMPTY).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_EMPTY).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toString()+CHARACTER_NEW_LINE
				+INDEX_TWO+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_EVENT).getTask().toString()+CHARACTER_NEW_LINE
				+INDEX_THREE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_VIEW_TASKS_EMPTY));	
		assertEquals(3, storage.viewTasks(PARAM_VIEW_TASKS_EMPTY).size());	
		
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_EVENTS).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_EVENTS).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_EVENT).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_VIEW_TASKS_EVENTS));
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_EVENTS).size());
		
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_DEADLINES).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_DEADLINES).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_VIEW_TASKS_DEADLINES));
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_DEADLINES).size());
		
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_FLOATING).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_FLOATING).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_VIEW_TASKS_FLOATING));	
		assertEquals(1, storage.viewTasks(PARAM_VIEW_TASKS_FLOATING).size());
		
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_OVERDUE).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_OVERDUE).getCommandType());
		assertEquals(FEEDBACK_EMPTY_LIST, logic.executeCommand(COMMAND_VIEW_TASKS_OVERDUE));
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_OVERDUE).size());
		
		assertEquals(false, parser.getHandler(COMMAND_VIEW_TASKS_DONE).getHasError());
		assertEquals(COMMAND_VIEW, parser.getHandler(COMMAND_VIEW_TASKS_DONE).getCommandType());
		assertEquals(FEEDBACK_EMPTY_LIST, logic.executeCommand(COMMAND_VIEW_TASKS_DONE));	
		assertEquals(0, storage.viewTasks(PARAM_VIEW_TASKS_DONE).size());	
	}
	
	@Test
	public void testSearchTasksMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		logic.executeCommand(COMMAND_ADD_FLOATING_TASK); // add floating task
		assertEquals(1, storage.getFileManager().getLineCount(todo));
		
		// These are for the �valid� partition 
		assertEquals(false, parser.getHandler(COMMAND_SEARCH_TASKS_EMPTY).getHasError());
		assertEquals(COMMAND_SEARCH, parser.getHandler(COMMAND_SEARCH_TASKS_EMPTY).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_SEARCH_TASKS_EMPTY));
		
		assertEquals(false, parser.getHandler(COMMAND_SEARCH_TASKS_KEYWORD).getHasError());
		assertEquals(COMMAND_SEARCH, parser.getHandler(COMMAND_SEARCH_TASKS_KEYWORD).getCommandType());
		assertEquals(INDEX_ONE+CHARACTER_SEPARATOR+parser.getHandler(COMMAND_ADD_FLOATING_TASK).getTask().toString()+CHARACTER_NEW_LINE, 
				logic.executeCommand(COMMAND_SEARCH_TASKS_KEYWORD));
	
		assertEquals(false, parser.getHandler(COMMAND_SEARCH_TASKS_OTHER_KEYWORD).getHasError());
		assertEquals(COMMAND_SEARCH, parser.getHandler(COMMAND_SEARCH_TASKS_OTHER_KEYWORD).getCommandType());
		assertEquals(FEEDBACK_NO_TASK_FOUND, logic.executeCommand(COMMAND_SEARCH_TASKS_OTHER_KEYWORD));
	}
	
	
	//@@author A0125136N
	@Test 
	public void testHelpMethod() throws Exception  {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		
		assertEquals(true, parser.getHandler(COMMAND_HELP_INVALID).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_INVALID).getCommandType());
		assertEquals(parser.getHandler(COMMAND_HELP_INVALID).getFeedBack(), 
				logic.executeCommand(COMMAND_HELP_INVALID));
		
		assertEquals(true, parser.getHandler(COMMAND_HELP_INTEGER).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_INTEGER).getCommandType());
		assertEquals(parser.getHandler(COMMAND_HELP_INTEGER).getFeedBack(), 
				logic.executeCommand(COMMAND_HELP_INTEGER));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_ALL).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_ALL).getCommandType());
		assertEquals(FEEDBACK_HELP_ALL, logic.executeCommand(COMMAND_HELP_ALL));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_ADD).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_ADD).getCommandType());
		assertEquals(FEEDBACK_HELP_ADD, logic.executeCommand(COMMAND_HELP_ADD));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_VIEW).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_VIEW).getCommandType());
		assertEquals(FEEDBACK_HELP_VIEW, logic.executeCommand(COMMAND_HELP_VIEW));

		assertEquals(false, parser.getHandler(COMMAND_HELP_DELETE).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_DELETE).getCommandType());
		assertEquals(FEEDBACK_HELP_DELETE, logic.executeCommand(COMMAND_HELP_DELETE));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_EDIT).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_EDIT).getCommandType());
		assertEquals(FEEDBACK_HELP_EDIT, logic.executeCommand(COMMAND_HELP_EDIT));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_MARK).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_MARK).getCommandType());
		assertEquals(FEEDBACK_HELP_MARK, logic.executeCommand(COMMAND_HELP_MARK));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_UNDO).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_UNDO).getCommandType());
		assertEquals(FEEDBACK_HELP_UNDO, logic.executeCommand(COMMAND_HELP_UNDO));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_SEARCH).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_SEARCH).getCommandType());
		assertEquals(FEEDBACK_HELP_SEARCH, logic.executeCommand(COMMAND_HELP_SEARCH));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_LOCATION).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_LOCATION).getCommandType());
		assertEquals(FEEDBACK_HELP_LOCATION, logic.executeCommand(COMMAND_HELP_LOCATION));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_EXIT).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_EXIT).getCommandType());
		assertEquals(FEEDBACK_HELP_EXIT, logic.executeCommand(COMMAND_HELP_EXIT));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_UNMARK).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_UNMARK).getCommandType());
		assertEquals(FEEDBACK_HELP_UNMARK, logic.executeCommand(COMMAND_HELP_UNMARK));
		
		assertEquals(false, parser.getHandler(COMMAND_HELP_REDO).getHasError());
		assertEquals(COMMAND_HELP, parser.getHandler(COMMAND_HELP_REDO).getCommandType());
		assertEquals(FEEDBACK_HELP_REDO, logic.executeCommand(COMMAND_HELP_REDO));
	}
	
	
	@Test
	public void testDeleteMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		logic.executeCommand(COMMAND_ADD_FLOATING_TASK);
		logic.executeCommand(COMMAND_ADD_DEADLINE);
		logic.executeCommand(COMMAND_ADD_EVENT);
		
		// test for invalid indexes
		assertEquals(false, parser.getHandler(COMMAND_DELETE_NEGATIVE_INDEX).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_NEGATIVE_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_DELETE_NEGATIVE_INDEX));
		
		assertEquals(false, parser.getHandler(COMMAND_DELETE_ZERO_INDEX).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_ZERO_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_DELETE_ZERO_INDEX));
		
		assertEquals(false, parser.getHandler(COMMAND_DELETE_LARGER_INDEX).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_LARGER_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_DELETE_LARGER_INDEX));
		
		assertEquals(true, parser.getHandler(COMMAND_DELETE_STRING).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_STRING).getCommandType());
		assertEquals(FEEDBACK_INDEX_IS_STRING, logic.executeCommand(COMMAND_DELETE_STRING));
		
		assertEquals(false, parser.getHandler(COMMAND_DELETE_MULTIPLE_INVALID).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_MULTIPLE_INVALID).getCommandType());
		assertEquals(FEEDBACK_MULTIPLE_INVALID, logic.executeCommand(COMMAND_DELETE_MULTIPLE_INVALID));
		
		// valid deletion
		assertEquals(3, storage.getFileManager().getLineCount(todo));
		logic.executeCommand(COMMAND_VIEW_TASKS_EMPTY);
		assertEquals(false, parser.getHandler(COMMAND_DELETE_SINGLE).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_SINGLE).getCommandType());
		assertEquals(String.format(FEEDBACK_DELETED, 
				parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_DELETE_SINGLE));
		
		assertEquals(false, parser.getHandler(COMMAND_DELETE_MULTIPLE).getHasError());
		assertEquals(COMMAND_DELETE, parser.getHandler(COMMAND_DELETE_MULTIPLE).getCommandType());
		assertEquals(FEEDBACK_MULTIPLE_DELETE_VALID, logic.executeCommand(COMMAND_DELETE_MULTIPLE));
	}
	
	@Test
	public void testDoneMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		logic.executeCommand(COMMAND_ADD_FLOATING_TASK);
		logic.executeCommand(COMMAND_ADD_DEADLINE);
		logic.executeCommand(COMMAND_ADD_EVENT);
		
		// test for invalid indexes
		assertEquals(false, parser.getHandler(COMMAND_MARK_NEGATIVE_INDEX).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_NEGATIVE_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_MARK_NEGATIVE_INDEX));
		
		assertEquals(false, parser.getHandler(COMMAND_MARK_ZERO_INDEX).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_ZERO_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_MARK_ZERO_INDEX));
		
		assertEquals(false, parser.getHandler(COMMAND_MARK_LARGER_INDEX).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_LARGER_INDEX).getCommandType());
		assertEquals(FEEDBACK_INVALID_INDEX, logic.executeCommand(COMMAND_MARK_LARGER_INDEX));
		
		assertEquals(true, parser.getHandler(COMMAND_MARK_STRING).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_STRING).getCommandType());
		assertEquals(FEEDBACK_INDEX_IS_STRING, logic.executeCommand(COMMAND_MARK_STRING));
		
		assertEquals(false, parser.getHandler(COMMAND_MARK_MULTIPLE_INVALID).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_MULTIPLE_INVALID).getCommandType());
		assertEquals(FEEDBACK_MULTIPLE_INVALID, logic.executeCommand(COMMAND_MARK_MULTIPLE_INVALID));
		
		// valid done
		assertEquals(3, storage.getFileManager().getLineCount(todo));
		
		logic.executeCommand(COMMAND_VIEW_TASKS_EMPTY);
		assertEquals(false, parser.getHandler(COMMAND_MARK_SINGLE).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_SINGLE).getCommandType());
		assertEquals(String.format(FEEDBACK_MARKED_DONE, 
				parser.getHandler(COMMAND_ADD_DEADLINE).getTask().toFilteredString()), 
				logic.executeCommand(COMMAND_MARK_SINGLE));
		
		logic.executeCommand(COMMAND_VIEW_TASKS_EMPTY);
		assertEquals(false, parser.getHandler(COMMAND_MARK_MULTIPLE).getHasError());
		assertEquals(COMMAND_MARK_AS_DONE, parser.getHandler(COMMAND_MARK_MULTIPLE).getCommandType());
		assertEquals(FEEDBACK_MULTIPLE_DONE_VALID, logic.executeCommand(COMMAND_MARK_MULTIPLE));
	}
	
	@Test
	public void testUndoMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		assertEquals(false, parser.getHandler(COMMAND_UNDO).getHasError());
		assertEquals(COMMAND_UNDO, parser.getHandler(COMMAND_UNDO).getCommandType());
		assertEquals(FEEDBACK_NOTHING_TO_UNDO, logic.executeCommand(COMMAND_UNDO));
		
		logic.executeCommand(COMMAND_ADD_EVENT);
		
		assertEquals(false, parser.getHandler(COMMAND_UNDO).getHasError());
		assertEquals(COMMAND_UNDO, parser.getHandler(COMMAND_UNDO).getCommandType());
		assertEquals(String.format(FEEDBACK_UNDO, COMMAND_ADD_EVENT), logic.executeCommand(COMMAND_UNDO));
	}
	
	@Test
	public void testRedoMethod() throws Exception {
		Logic logic = new Logic();
		Parser parser = new Parser();
		Storage storage = new Storage();
		
		logic.executeCommand(COMMAND_SET_LOCATION_DIRECTORY);
		
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
		
		assertEquals(false, parser.getHandler(COMMAND_REDO).getHasError());
		assertEquals(COMMAND_REDO, parser.getHandler(COMMAND_REDO).getCommandType());
		assertEquals(FEEDBACK_NOTHING_TO_REDO, logic.executeCommand(COMMAND_REDO));
		
		logic.executeCommand(COMMAND_ADD_EVENT);
		logic.executeCommand(COMMAND_UNDO);
		
		assertEquals(false, parser.getHandler(COMMAND_REDO).getHasError());
		assertEquals(COMMAND_REDO, parser.getHandler(COMMAND_REDO).getCommandType());
		assertEquals(String.format(FEEDBACK_REDO, COMMAND_ADD_EVENT), logic.executeCommand(COMMAND_REDO));
	}
}
