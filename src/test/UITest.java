package test;

import static org.junit.Assert.*;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Action;

import org.junit.BeforeClass;
import org.junit.Test;

import simplyamazing.data.Task;
import simplyamazing.logic.Logic;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;
import simplyamazing.ui.CommandBarController;
import simplyamazing.ui.UI;

public class UITest {
	
	private static final String DIRECTORY_SYSTEM = "C:\\Users\\Public\\SimplyAmzing";
	private static final String FILENAME_STORAGE = "\\storage.txt";
	private static final String FILENAME_TODO = "\\todo.txt";
	private static final String FILENAME_DONE = "\\done.txt";
	
	private static final String CHARACTER_SPACE = " ";
	private static final String CHARACTER_NEW_LINE = "\n";
	
	
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
	private static final String COMMAND_UNDONE = "undone";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_INVALID = "abcd";
	
	private static final String TASK_TYPE_EVENTS = "events";
	private static final String TASK_TYPE_DEADLINES = "deadlines";
	private static final String TASK_TYPE_FLOATING = "tasks";
	private static final String TASK_TYPE_OVERDUE = "overdue";
	private static final String TASK_TYPE_DONE = "done";
	private static final String TASK_TYPE_OTHER = "other";
	
	private static final String COMMAND_SET_LOCATION_EMPTY = COMMAND_SET_LOCATION;
	private static final String COMMAND_SET_LOCATION_NOT_DIRECTORY = COMMAND_SET_LOCATION + CHARACTER_SPACE + "C:\\Users\\Public\\Documents\\SimplyAmazing";
	private static final String COMMAND_SET_LOCATION_DIRECTORY = COMMAND_SET_LOCATION + CHARACTER_SPACE + "C:\\Users\\Public\\Documents";
	private static final String COMMAND_ADD_TASK_EMPTY = COMMAND_ADD;
	private static final String COMMAND_ADD_TASK_WITH_PASSED_DEADLINE = COMMAND_ADD + CHARACTER_SPACE + "sleep by 3:00 24 Mar 2016";;
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_ONLY = COMMAND_ADD + CHARACTER_SPACE + "sleep from 13:00 24 May 2017";
	private static final String COMMAND_ADD_TASK_WITH_STARTIME_AFTER_ENDTIME = COMMAND_ADD + CHARACTER_SPACE + "sleep from 10:00 24 May 2017 to 8:00 24 May 2017";
	private static final String COMMAND_ADD_FLOATING_TASK = COMMAND_ADD + CHARACTER_SPACE + "hello world";
	private static final String COMMAND_ADD_FLOATING_TASK_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE + "drop by post office to deliver parcel received from Lndon";
	private static final String COMMAND_ADD_DEADLINE = COMMAND_ADD + CHARACTER_SPACE + "cs2103 peer review by 23:59 25 May 2017";
	private static final String COMMAND_ADD_DEADLINE_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE + "drop by post office to deliver parcel received from Landon by 23:59 25 May 2017";
	private static final String COMMAND_ADD_EVENT = COMMAND_ADD + CHARACTER_SPACE + "hackathon in SOC from 09:30 26 May 2017 to 10:00 27 May 2017";
	private static final String COMMAND_ADD_EVENT_WITH_KEYWORDS = COMMAND_ADD + CHARACTER_SPACE + "drop by post office to deliver parcel received from Landon from 09:30 26 May 2017 to 10:00 27 May 2017";
	private static final String COMMAND_VIEW_TASKS_EMPTY = COMMAND_VIEW;
	private static final String COMMAND_VIEW_TASKS_EVENTS = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_EVENTS;
	private static final String COMMAND_VIEW_TASKS_DEADLINES = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_DEADLINES;
	private static final String COMMAND_VIEW_TASKS_FLOATING = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_FLOATING;
	private static final String COMMAND_VIEW_TASKS_OVERDUE = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_OVERDUE;
	private static final String COMMAND_VIEW_TASKS_DONE = COMMAND_VIEW + CHARACTER_SPACE + TASK_TYPE_DONE;
	private static final String COMMAND_VIEW_TASKS_OTHERS = COMMAND_VIEW + CHARACTER_SPACE + "other";
	private static final String COMMAND_SEARCH_TASKS_EMPTY = COMMAND_SEARCH;
	private static final String COMMAND_SEARCH_TASKS_KEYWORD =  COMMAND_SEARCH + CHARACTER_SPACE + "hello";
	private static final String COMMAND_SEARCH_TASKS_OTHER_KEYWORD =  COMMAND_SEARCH + CHARACTER_SPACE + "other";
	private static final String COMMAND_RESTORE_EMPTY = "";
	private static final String COMMAND_RESTORE = "restore";
	private static final String COMMAND_HELP_INTEGER = COMMAND_HELP + CHARACTER_SPACE + "1";
	private static final String COMMAND_HELP_INVALID = COMMAND_HELP + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_HELP_ALL = COMMAND_HELP;
	private static final String COMMAND_HELP_LOCATION = COMMAND_HELP + CHARACTER_SPACE + COMMAND_SET_LOCATION;
	private static final String COMMAND_HELP_ADD = COMMAND_HELP + CHARACTER_SPACE + COMMAND_ADD;
	private static final String COMMAND_HELP_VIEW = COMMAND_HELP + CHARACTER_SPACE + COMMAND_VIEW;
	private static final String COMMAND_HELP_EDIT = COMMAND_HELP + CHARACTER_SPACE + COMMAND_EDIT;
	private static final String COMMAND_HELP_DELETE = COMMAND_HELP + CHARACTER_SPACE + COMMAND_DELETE;
	private static final String COMMAND_HELP_UNDO = COMMAND_HELP + CHARACTER_SPACE + COMMAND_UNDO;
	private static final String COMMAND_HELP_DONE = COMMAND_HELP + CHARACTER_SPACE + COMMAND_MARK_AS_DONE;
	private static final String COMMAND_HELP_SEARCH = COMMAND_HELP + CHARACTER_SPACE + COMMAND_SEARCH;
	private static final String COMMAND_HELP_EXIT = COMMAND_HELP + CHARACTER_SPACE + COMMAND_EXIT;
	private static final String COMMAND_HELP_REDO = COMMAND_HELP + CHARACTER_SPACE + COMMAND_REDO;
	private static final String COMMAND_HELP_UNDONE = COMMAND_HELP + CHARACTER_SPACE + COMMAND_UNDONE;
	private static final String COMMAND_DELETE_NEGATIVE_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "-1";
	private static final String COMMAND_DELETE_ZERO_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "0";
	private static final String COMMAND_DELETE_LARGER_INDEX = COMMAND_DELETE + CHARACTER_SPACE + "4";
	private static final String COMMAND_DELETE_STRING = COMMAND_DELETE + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_DELETE_MULTIPLE_INVALID = COMMAND_DELETE + CHARACTER_SPACE + "1 2 4";
	private static final String COMMAND_DELETE_SINGLE = COMMAND_DELETE + CHARACTER_SPACE + "1";
	private static final String COMMAND_DELETE_MULTIPLE = COMMAND_DELETE + CHARACTER_SPACE + "1 2";
	
	private static final String COMMAND_DONE_NEGATIVE_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "-1";
	private static final String COMMAND_DONE_ZERO_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "0";
	private static final String COMMAND_DONE_LARGER_INDEX = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "4";
	private static final String COMMAND_DONE_STRING = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + COMMAND_INVALID;
	private static final String COMMAND_DONE_MULTIPLE_INVALID = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "1 2 4";
	private static final String COMMAND_DONE_SINGLE = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "1";
	private static final String COMMAND_DONE_MULTIPLE = COMMAND_MARK_AS_DONE + CHARACTER_SPACE + "1 2";
	
	
	private static final String PARAM_SET_LOCATION_DIRECTORY = "C:\\Users\\Public\\Documents";
	private static final String PARAM_DESCRIPTION = "go swimming";
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
	
	private static final String FEEDBACK_COMMAND_EMPTY = "Error: Invalid command entered. Please enter \"help\" to view all commands and their format";
	private static final String FEEDBACK_NO_PREVIOUS_COMMAND = "Error: There is no previous command to undo";
	private static final String FEEDBACK_EMPTY_LIST = "List is empty";
	private static final String FEEDBACK_NO_TASK_FOUND = "There are no tasks containing the given keyword";
	
	private static final String FEEDBACK_LOCATION_SET = "Storage location of task data has been sucessfully set as %1$s.";
	private static final String FEEDBACK_LOCATION_INVALID = "Error: Location provided is invalid";
	private static final String FEEDBACK_LOCATION_NOT_DIRECTORY = "Error: Not a valid directory";
	private static final String FEEDBACK_ADDED = "%1$s has been added.";
	private static final String FEEDBACK_ADD_TASK_FIELDS_NOT_CORRECT = "Error: Please ensure the fields are correct";
	private static final String FEEDBACK_ADD_TASK_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String FEEDBACK_ADD_TASK_START_AFTER_END ="Error: Start date and time cannot be after the End date and time";
	private static final String FEEDBACK_ADD_TASK_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	private static final String FEEDBACK_UPDATED = "%1$s has been successfully updated.";
	private static final String FEEDBACK_MARKED_DONE = "%1$s has been marked as done.";
	private static final String FEEDBACK_DELETED = "%1$s has been successfully deleted.";
	private static final String FEEDBACK_RESTORED = "\"%1$s\" command has been successfully undone.";
	private static final String FEEDBACK_INVALID_INDEX= "Error: The Index entered is invalid";
	private static final String FEEDBACK_INDEX_IS_STRING = "Error: Index provided is not an Integer.";
	private static final String FEEDBACK_MULTIPLE_INVALID = "Error: One of the given indexes is invalid";
	private static final String FEEDBACK_MULTIPLE_DELETE_VALID = "Provided tasks have been successfully deleted.";
	private static final String FEEDBACK_MULTIPLE_DONE_VALID = "Provided tasks have been marked as done.";
	private static final String FEEDBACK_NOTHING_TO_UNDO = "Error: There is no previous command to undo";
	private static final String FEEDBACK_NOTHING_TO_REDO = "Error: There is no previous command to redo";
	

	private static final String FEEDBACK_HELP_INTEGER = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";
	private static final String FEEDBACK_HELP_UNDO = "Undo the most recent command\nCommand: undo\n";;
	private static final String FEEDBACK_HELP_REDO = "Redo the most recent command\nCommand: redo\n";
	private static final String FEEDBACK_HELP_UNMARK = "Unmarks a completed task\nCommand: undone <task index>\n\nExample:\nundone 2\n\n\n"
			+ "Note: You may also use the keyword \"unmark\" instead of \"undone\"";
	private static final String FEEDBACK_HELP_DONE = "Marks task as completed\nCommand: done <task index>\n\nExample:\ndone 2\n\n\n"
			+ "Note: You may also use the keywords \"mark\", \"complete\" or \"finish\" instead of \"done\"";
	
	private static final String FEEDBACK_HELP_DELETE = "Delete task from list\nCommand: delete <task index>\n\nExample:\ndelete 1\n\n\n"
			+ "Note: You may also use the keywords \"-\", \"del\", \"remove\" or \"cancel\" instead of \"delete\"";
	
	private static final String FEEDBACK_HELP_SEARCH = "Search for tasks containing the given keyword or date \nCommand: search <keyword> or search<date>\n\nExample:\nsearch meeting\n\n\n"
			+ "Note: You may also use the keyword \"find\" instead of \"search\"";
	
	private static final String FEEDBACK_HELP_EXIT = "Exits SimplyAmazing\nCommand: exit\n\n\nNote: You may also use \"logout\" or \"quit\" instead of \"exit\"";
	
	
	private static final String FEEDBACK_HELP_ALL = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help search \n6. help mark\n"
			+ "7. help unmark\n8. help undo\n9. help redo\n10. help location \n11. help exit\n";
	
	
	private static final String FEEDBACK_HELP_VIEW = "1.Display all tasks\n Command: view\n\n2.Display tasks with deadlines\n"
			+ "Command: view deadlines\n\n3.Display all events\nCommand: view events\n\n4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n6.Display overdue tasks\nCommand: view overdue\n\n\n"
			+ "Note: You may also use the keywords \"display\", \"show\" or \"list\" instead of \"view\"";
	
	private static final String FEEDBACK_HELP_EDIT =  "Edit content in a task\nCommand: edit <task index> <task header> <updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n2. edit 3 start 22:00 26 may 2016, end 22:40 26 may 2016\n\n"
			+ "3. edit 1 priority high\n\n\nNote: You may also use the keywords \"change\" or \"update\" instead of \"edit\"";
	
	
	private static final String FEEDBACK_HELP_ADD =  "1.Add a task to the list\nCommand: add <task description>\n\nExample: add Prepare presentation\n\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Submit marketing report by 17:00 20 Dec 2016\n\n\n"
			+ "Note: You may use the keyword \"+\" instead of \"add\"";
	
	
	private static final String FEEDBACK_HELP_LOCATION = "Sets the storage location or folder for application data\n"
			+ "Command: location <path>\n" + "\nExample:\nlocation C:\\Users\\Jim\\Desktop\\Task Data\n\n\nNote: You may also use the keywords \"path\" or \"address\""
			+ " instead of \"location\"";

	@BeforeClass
	public static void setup() throws Exception {
		Storage storage = new Storage();
		storage.setLocation(PARAM_SET_LOCATION_DIRECTORY);
		File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
		File done = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_DONE);
		storage.getFileManager().cleanFile(todo);
		storage.getFileManager().cleanFile(done);
	}
	
	/*
	 * Operation to test: executeUserCommand(String command): String
	 * Equivalence partition: 
	 * command: [null] [not null] [valid] [not valid]
	 * Boundary values: Empty String, a String of some length
	 */	
	
	@Test
	public void testEmptyCommand() {
		UI ui = new UI();
		
		new CommandBarController().clearCommand(ui.commandBar);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_COMMAND_EMPTY, ui.feedbackArea.getText()); 
	}
	
	@Test
	public void testUndoCommandWithoutAnyPreviousCommand() throws Exception {
		UI ui = new UI();
		
		ui.commandBar.setText(COMMAND_UNDO);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_NO_PREVIOUS_COMMAND, ui.feedbackArea.getText());
	}
	
	@Test
	public void testHelpCommand() {
		UI ui = new UI();
		ui.commandBar.setText(COMMAND_HELP);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_HELP_ALL, ui.instructionPanel.getInstrctionPanel().getText());
	}
	
	@Test
	public void testViewCommandForNoTask() {
		UI ui = new UI();
		
		ui.commandBar.setText(COMMAND_VIEW);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
	    assertEquals(FEEDBACK_EMPTY_LIST, ui.feedbackArea.getText());
	}
	
	@Test
	public void testViewCommandWithTasks() throws Exception {
		UI ui = new UI();
		ui.commandBar.setText(COMMAND_ADD_EVENT);
		KeyEvent event = new KeyEvent(ui.commandBar, 
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 
                KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        ui.commandBar.dispatchEvent(event); 
        
        ui.commandBar.setText(COMMAND_ADD_DEADLINE);
        ui.commandBar.dispatchEvent(event); 
	    
        ui.commandBar.setText(COMMAND_VIEW);
        ui.commandBar.dispatchEvent(event); 
        
        File todo = new File(PARAM_SET_LOCATION_DIRECTORY+FILENAME_TODO);
        Storage storage = new Storage();
        ArrayList<String> lines = storage.getFileManager().readFile(todo);
        String tasks = COMMAND_EMPTY;
        for (int i=0; i<lines.size(); i++) {
        	tasks += (i+1) + Task.FIELD_SEPARATOR + lines.get(i) + CHARACTER_NEW_LINE;
        }
        assertEquals(tasks, ui.feedback);
        storage.getFileManager().cleanFile(todo);
	}
}
