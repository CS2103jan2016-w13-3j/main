//@@author A0125136N 
package test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import simplyamazing.logic.Logic;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicTest {
	private static Logic logicObj= new Logic();
	
	private static String UNDO_NOTHING_FEEDBACK = "Error: There is no previous command to undo";
	private static String REDO_NOTHING_FEEDBACK = "Error: There is no previous command to redo";
	private static String ADD_INVALID_FEEDBACK = "Error: Please ensure the fields are correct";
	private static String ADD_INVALID = "add ";
	private static String UNRECOGNIZED_COMMAND = "hello";
	private static String UNRECOGNIZED_COMMAND_STRING_EMPTY = "";
	private static String UNRECOGNIZED_COMMAND_FEEDBACK = "Error: Invalid command entered. Please enter \"help\" to view all commands and their format";
	
		
	private static final String LOCATION_COMMAND_FAIL = "location just a placeholder";
	private static final String LOCATION_COMMAND_PASS = "location C:\\Users\\Ishpal\\Desktop\\Task Data";
	private static final String LOCATION_FEEDBACK_FAIL = "Error: Not a valid directory";
	private static final String LOCATION_FEEDBACK_PASS = "Storage location of task data has been sucessfully set as C:\\Users\\Ishpal\\Desktop\\Task Data.";
	private static final String LOCATION_EMPTY_STRING = "location ";
	private static final String LOCATION_EMPTY_STRING_FEEDBACK = "Error: Location provided is invalid";
	
	
	
	private static final String ADD_TASK_PASS = "add hello world";
	private static final String ADD_TASK_PASS_FEEDBACK = "Task [hello world] has been added.";
	private static final String ADD_DEADLINE_PASS = "add cs2103 peer review by 23:59 25 May 2016";
	private static final String ADD_DEADLINE_PASS_FEEDBACK = "Task [cs2103 peer review by 23:59 25 May 2016] has been added.";
	private static final String ADD_EVENT_PASS = "add hackathon in SOC from 09:30 26 May 2016 to 10:00 27 May 2016";
	private static final String ADD_EVENT_PASS_FEEDBACK = "Task [hackathon in SOC from 09:30 26 May 2016 to 10:00 27 May 2016] has been added.";
	private static final String ADD_TASK_WITH_STARTIME_ONLY = "add sleep from 03:00 24 May 2016";
	private static final String ADD_ERROR_MESSAGE = "Error: Please ensure the fields are correct";
	private static final String ADD_TASK_ENDTIME_BEFORE_STARTIME = "add play fifa from 14:00 30 May 2016 to 13:00 30 May 2016";
	private static final String ADD_TASK_ENDTIME_BEFORE_STARTIME_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	private static final String ADD_END_BEFORE_CURRENT = "add visit the dentist by 22:00 2 Apr 2016";
	private static final String ADD_START_BEFORE_CURRENT = "add visit the dentist from 22:00 2 Apr 2016 to 23:00 2 Apr 2016";
	private static final String ADD_BEFORE_CURRENT_FEEDBACK = "Error: Time provided must be after the current time";
	
	
	
	private static final String VIEW_INVALID = "view nothing";
	private static final String VIEW_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help view\" command to see all the valid keywords" ;
	private static final String VIEW_EMPTY_LIST = "List is empty";
	private static final String VIEW_DONE = "view done";
	private static final String VIEW_OVERDUE = "view overdue";
	private static final String VIEW_VALID = "view";
	private static final String VIEW_VALID_FEEDBACK = "1,cs2103 peer review, ,23:59 25 May 2016, ,incomplete\n2,hackathon in SOC,09:30 26 May 2016,10:00 27 May 2016, ,incomplete\n"
			+ "3,hello world, , , ,incomplete\n";
	
	
	
	private static final String DELETE_VALID_INDEX = "delete 2";
	private static final String DELETE_VALID_INDEX_FEEDBACK = "Task [hackathon in SOC from 09:30 26 May 2016 to 10:00 27 May 2016] has been successfully deleted.";
	private static final String DELETE_INVALID_INDEX_LARGER = "delete 5";
	private static final String DELETE_INVALID_INDEX_FEEDBACK = "Error: The Index entered is invalid";
	private static final String DELETE_INVALID_INDEX_ZERO = "delete 0";
	private static final String DELETE_INVALID_INDEX_NEGATIVE = "delete -1";
	private static final String DELETE_STRING = "delete hello";
	private static final String DELETE_STRING_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String DELETE_VALID_MULTIPLE = "delete 1 2";
	private static final String DELETE_VALID_MULTIPLE_FEEDBACK = "Provided tasks have been successfully deleted.";
	private static final String DELETE_INVALID_MULTIPLE = "delete 1 2 5";
	private static final String DELETE_INVALID_MULTIPLE_FEEDBACK = "Error: One of the given indexes is invalid";
	
	
	
	
	private static final String EDIT_INDEX_INVALID_FEEDBACK = "Error: The Index entered is invalid";
	private static final String EDIT_INDEX_LARGER = "edit 10 priority high";
	private static final String EDIT_INDEX_NEGATIVE = "edit -1 priority high";
	private static final String EDIT_INDEX_ZERO = "edit 0 priority high";
	private static final String EDIT_INDEX_STRING = "edit abc priority high";
	private static final String EDIT_INDEX_STRING_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String EDIT_INVALID_FIELD = "edit 1 anyfield anyvalue";
	private static final String EDIT_INVALID_FIELD_FEEDBACK = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	
	private static final String EDIT_TIME_BEFORE_CURRENT_FEEDBACK = "Error: Time provided must be after the current time";
	private static final String EDIT_START_BEFORE_CURRENT = "edit 1 start 22:00 2 apr 2016";
	private static final String EDIT_END_BEFORE_CURRENT = "edit 1 end 22:00 2 apr 2016";
	private static final String EDIT_START_AFTER_END = "edit 1 start 00:00 26 may 2016";
	private static final String EDIT_START_AFTER_END_FEEDBACK = "Error: New start time cannot be after the end time";
	private static final String EDIT_START_EQUALS_END = "edit 1 start 23:59 25 may 2016";
	private static final String EDIT_START_EQUALS_END_FEEDBACK = "Error: New start time cannot be the same as the end time";
	private static final String EDIT_START_NO_END= "edit 3 start 17:00 20 may 2016";
	private static final String EDIT_START_NO_END_FEEDBACK= "Error: Unable to allocate a start time when the task has no end time";
	
	private static final String EDIT_END_BEFORE_START = "edit 2 end 09:00 26 may 2016";
	private static final String EDIT_END_BEFORE_START_FEEDBACK = "Error: New end time cannot be before the start time";
	private static final String EDIT_END_EQUALS_START = "edit 2 end 09:30 26 may 2016";
	private static final String EDIT_END_EQUALS_START_FEEDBACK = "Error: New end time cannot be the same as the start time";
	
	private static final String EDIT_EVENT_START_AFTER_END= "edit 3 start 11:00 20 may 2016, end 10:00 20 may 2016";
	private static final String EDIT_EVENT_START_EQUAL_END= "edit 3 start 11:00 20 may 2016, end 11:00 20 may 2016";
	private static final String EDIT_EVENT_START_BEFORE_CURRENT = "edit 3 start 12:00 3 apr 2016, end 10:00 20 may 2016";
	private static final String EDIT_EVENT_START_AFTER_END_FEEDBACK= "Error: Start date and time cannot be after the End date and time";
	
	private static final String EDIT_VALID = "edit 3 description hello world";
	private static final String EDIT_VALID_FEEDBACK = "Task [hello world] has been successfully updated.";
	
	private static final String EDIT_PRIORITY_HIGH = "Task [cs2103 peer review by 23:59 25 May 2016 with high priority] has been successfully updated."; 
	private static final String EDIT_PRIORITY_HIGH_FEEDBACK = "edit 1 priority high"; 
	private static final String EDIT_PRIORITY_MEDIUM =  "edit 1 priority medium";
	private static final String EDIT_PRIORITY_MEDIUM_FEEDBACK = "Task [cs2103 peer review by 23:59 25 May 2016 with medium priority] has been successfully updated.";
	private static final String EDIT_PRIORITY_LOW = "edit 1 priority low"; 
	private static final String EDIT_PRIORITY_LOW_FEEDBACK = "Task [cs2103 peer review by 23:59 25 May 2016 with low priority] has been successfully updated.";
	private static final String EDIT_PRIORITY_NONE = "edit 1 priority none";
	private static final String EDIT_PRIORITY_NONE_FEEDBACK = "Task [cs2103 peer review by 23:59 25 May 2016] has been successfully updated.";
	private static final String EDIT_PRIORITY_INVALID = "edit 1 priority nothing"; 
	private static final String EDIT_PRIORITY_INVALID_FEEDBACK = "Error: Priority level can be only high, medium, low or none."; 

	
	
	private static final String DONE_INVALID_INDEX_NEGATIVE = "done -1";
	private static final String DONE_INVALID_INDEX_ZERO = "done 0";
	private static final String DONE_INVALID_INDEX_LARGER = "done 30";
	private static final String DONE_INVALID_INDEX_FEEDBACK = "Error: The Index entered is invalid";
	private static final String DONE_INVALID_INDEX_STRING = "done abcd";
	private static final String DONE_INVALID_COMMAND_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String DONE_INVALID_INDEX_MULTIPLE = "done 1 2 -1";
	private static final String DONE_INVALID_INDEX_MULTIPLE_FEEDBACK = "Error: One of the given indexes is invalid";
	private static final String DONE_VALID_INDEX_MULTIPLE = "done 1 2";
	private static final String DONE_VALID_INDEX_MULTIPLE_FEEDBACK = "Provided tasks have been marked as done.";
	private static final String DONE_VALID_INDEX  = "done 2";
	private static final String DONE_VALID_FEEDBACK = "Task [hello world] has been marked as done.";
	
	
	private static final String UNMARK_INVALID_INDEX_FEEDBACK = "Error: The Index entered is invalid";
	private static final String UNMARK_INVALID_INDEX_NEGATIVE = "unmark -1";
	private static final String UNMARK_INVALID_INDEX_ZERO = "unmark 0";
	private static final String UNMARK_INVALID_INDEX_LARGER = "unmark 3";
	private static final String UNMARK_INVALID_INDEX_STRING = "unmark abc";
	private static final String UNMARK_INVALID_COMMAND_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String UNMARK_INVALID_MULTIPLE = "unmark 1 2 3";
	private static final String UNMARK_INVALID_MULTIPLE_FEEDBACK = "Error: One of the given indexes is invalid";
	private static final String UNMARK_VALID_MULTIPLE = "unmark 1 2";
	private static final String UNMARK_VALID_MULTIPLE_FEEDBACK = "Provided tasks have been marked as incomplete.";
	private static final String UNMARK_VALID = "unmark 2";
	private static final String UNMARK_VALID_FEEDBACK = "Task [hello world] has been marked as incomplete.";
	
	
	private static final String SEARCH_VALID_KEYWORD = "search hello";
	private static final String SEARCH_VALID_FEEDBACK = "1,hello world, , , ,incomplete\n";
	private static final String SEARCH_INVALID_KEYWORD = "search joke";
	private static final String SEARCH_NOT_FOUND_FEEDBACK = "There are no tasks containing the given keyword";
	private static final String SEARCH_WRONG_DATE = "search 25 Dec 2016";
	private static final String SEARCH_VALID_DATE = "search 25 May 2016";
	private static final String SEARCH_VALID_DATE_FEEDBACK = "1,cs2103 peer review, ,23:59 25 May 2016, ,incomplete\n2,hello world, , , ,incomplete\n";
	private static final String SEARCH_EMPTY_STRING = "search ";
	private static final String SEARCH_EMPTY_STRING_FEEDBACK = "1,cs2103 peer review, ,23:59 25 May 2016, ,incomplete\n2,hackathon in SOC,09:30 26 May 2016,10:00 27 May 2016, ,incomplete"
			+ "\n3,hello world, , , ,incomplete\n";

	private static final String UNDO = "undo";
	private static final String UNDO_FEEDBACK = "\"unmark 2\" command has been successfully undone.";
	
	
	private static final String REDO = "redo";
	private static final String REDO_FEEDBACK = "\"unmark 2\" command has been successfully undone.";
	
	
	private static final String HELP_INVALID = "help 1";
	private static final String HELP_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";
	
	private static final String HELP_VALID = "help";
	private static final String HELP_VALID_FEEDBACK = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help search \n6. help mark\n"
			+ "7. help unmark\n8. help undo\n9. help redo\n10. help location \n11. help exit\n";

	
	private static final String HELP_VALID_UNDO = "help undo";
	private static final String HELP_VALID_UNDO_FEEDBACK = "Undo the most recent command\nCommand: undo\n";
	
	private static final String HELP_VALID_REDO = "help redo";
	private static final String HELP_VALID_REDO_FEEDBACK = "Redo the most recent command\nCommand: redo\n";
	
	private static final String HELP_VALID_UNDONE = "help undone";
	private static final String HELP_VALID_UNDONE_FEEDBACK =  "Unmarks a completed task\nCommand: undone <task index>\n\nExample:\nundone 2\n\n\n"
			+ "Note: You may also use the keyword \"unmark\" instead of \"undone\"";
	
	private static final String HELP_VALID_DONE = "help done";
	private static final String HELP_VALID_DONE_FEEDBACK = "Marks task as completed\nCommand: done <task index>\n\nExample:\ndone 2\n\n\n"
			+ "Note: You may also use the keywords \"mark\", \"complete\" or \"finish\" instead of \"done\"";
	
	private static final String HELP_VALID_DELETE = "help delete";
	private static final String HELP_VALID_DELETE_FEEDBACK = "Delete task from list\nCommand: delete <task index>\n\nExample:\ndelete 1\n\n\n"
			+ "Note: You may also use the keywords \"-\", \"del\", \"remove\" or \"cancel\" instead of \"delete\"";
			
	private static final String HELP_VALID_SEARCH = "help search";
	private static final String HELP_VALID_SEARCH_FEEDBACK = "Search for tasks containing the given keyword or date \nCommand: search <keyword> or search<date>\n\nExample:\nsearch meeting\n\n\n"
			+ "Note: You may also use the keyword \"find\" instead of \"search\"";
	
	private static final String HELP_VALID_EXIT = "help exit";
	private static final String HELP_VALID_EXIT_FEEDBACK = "Exits SimplyAmazing\nCommand: exit\n\n\nNote: You may also use \"logout\" or \"quit\" instead of \"exit\"";
	
	private static final String HELP_VALID_VIEW = "help view";
	private static final String HELP_VALID_VIEW_FEEDBACK = "1.Display all tasks\n Command: view\n\n2.Display tasks with deadlines\n"
			+ "Command: view deadlines\n\n3.Display all events\nCommand: view events\n\n4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n6.Display overdue tasks\nCommand: view overdue\n\n\n"
			+ "Note: You may also use the keywords \"display\", \"show\" or \"list\" instead of \"view\"";
	
	private static final String HELP_VALID_EDIT = "help edit";
	private static final String HELP_VALID_EDIT_FEEDBACK = "Edit content in a task\nCommand: edit <task index> <task header> <updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n2. edit 3 start 22:00 26 may 2016, end 22:40 26 may 2016\n\n"
			+ "3. edit 1 priority high\n\n\nNote: You may also use the keywords \"change\" or \"update\" instead of \"edit\"";
	
	private static final String HELP_VALID_ADD = "help add";
	private static final String HELP_VALID_ADD_FEEDBACK =  "1.Add a task to the list\nCommand: add <task description>\n\nExample: add Prepare presentation\n\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Submit marketing report by 17:00 20 Dec 2016\n\n\n"
			+ "Note: You may use the keyword \"+\" instead of \"add\"";
	
	private static final String HELP_VALID_LOCATION = "help location";
	private static final String HELP_VALID_LOCATION_FEEDBACK = "Sets the storage location or folder for application data\n"
			+ "Command: location <path>\n" + "\nExample:\nlocation C:\\Users\\Jim\\Desktop\\Task Data\n\n\nNote: You may also use the keywords \"path\" or \"address\""
			+ " instead of \"location\"";
	

	
	@Test
	public void test1ValidCommandTypes() throws Exception{
		assertEquals(UNDO_NOTHING_FEEDBACK, logicObj.executeCommand(UNDO));  
		assertEquals(REDO_NOTHING_FEEDBACK, logicObj.executeCommand(REDO));
		assertEquals(ADD_INVALID_FEEDBACK,logicObj.executeCommand(ADD_INVALID));
		assertEquals(UNRECOGNIZED_COMMAND_FEEDBACK, logicObj.executeCommand(UNRECOGNIZED_COMMAND));
		assertEquals(UNRECOGNIZED_COMMAND_FEEDBACK, logicObj.executeCommand(UNRECOGNIZED_COMMAND_STRING_EMPTY));
		
	}
	
	@Test
	/*
	 * This test case has 2 partitions, namely valid and invalid. These refer to the validity of the command input
	 * The correct partition encompasses all valid file paths while the incorrect encompasses all invalid file paths
	 */
	public void testSetLocation() throws Exception {
		// valid partition
		assertEquals(LOCATION_FEEDBACK_PASS, logicObj.executeCommand(LOCATION_COMMAND_PASS));

		// invalid partition
		assertEquals(LOCATION_EMPTY_STRING_FEEDBACK, logicObj.executeCommand(LOCATION_EMPTY_STRING));
		assertEquals(LOCATION_FEEDBACK_FAIL, logicObj.executeCommand(LOCATION_COMMAND_FAIL));
	
	}
	

	
	
	@Test
	/*
	 * The following test has 2 equivalent partitions, valid and invalid commands.
	 */
	public void test2AddCommand() throws Exception{
		assertEquals(ADD_TASK_PASS_FEEDBACK, logicObj.executeCommand(ADD_TASK_PASS));
		assertEquals(ADD_DEADLINE_PASS_FEEDBACK, logicObj.executeCommand(ADD_DEADLINE_PASS));
		assertEquals(ADD_EVENT_PASS_FEEDBACK, logicObj.executeCommand(ADD_EVENT_PASS));		
		assertEquals(ADD_ERROR_MESSAGE, logicObj.executeCommand(ADD_TASK_WITH_STARTIME_ONLY));
		assertEquals(ADD_TASK_ENDTIME_BEFORE_STARTIME_FEEDBACK, logicObj.executeCommand(ADD_TASK_ENDTIME_BEFORE_STARTIME));
		assertEquals(ADD_BEFORE_CURRENT_FEEDBACK, logicObj.executeCommand(ADD_END_BEFORE_CURRENT));
		assertEquals(ADD_BEFORE_CURRENT_FEEDBACK, logicObj.executeCommand(ADD_START_BEFORE_CURRENT));
	}
	
	
	@Test
	public void test31HelpCommand() throws Exception{
		assertEquals(HELP_VALID_FEEDBACK, logicObj.executeCommand(HELP_VALID));
		assertEquals(HELP_INVALID_FEEDBACK, logicObj.executeCommand(HELP_INVALID));
		assertEquals(HELP_VALID_UNDO_FEEDBACK, logicObj.executeCommand(HELP_VALID_UNDO));
		assertEquals(HELP_VALID_DONE_FEEDBACK, logicObj.executeCommand(HELP_VALID_DONE));
		assertEquals(HELP_VALID_DELETE_FEEDBACK, logicObj.executeCommand(HELP_VALID_DELETE));
		assertEquals(HELP_VALID_VIEW_FEEDBACK, logicObj.executeCommand(HELP_VALID_VIEW));
		assertEquals(HELP_VALID_EDIT_FEEDBACK, logicObj.executeCommand(HELP_VALID_EDIT));
		assertEquals(HELP_VALID_SEARCH_FEEDBACK, logicObj.executeCommand(HELP_VALID_SEARCH));
		assertEquals(HELP_VALID_LOCATION_FEEDBACK, logicObj.executeCommand(HELP_VALID_LOCATION));
		assertEquals(HELP_VALID_UNDONE_FEEDBACK, logicObj.executeCommand(HELP_VALID_UNDONE));
		assertEquals(HELP_VALID_REDO_FEEDBACK, logicObj.executeCommand(HELP_VALID_REDO));
		assertEquals(HELP_VALID_ADD_FEEDBACK, logicObj.executeCommand(HELP_VALID_ADD));
		assertEquals(HELP_VALID_EXIT_FEEDBACK, logicObj.executeCommand(HELP_VALID_EXIT));
	}

	
	/*
	 * The following test case has 2 partitions, based on whether the search results can be found or not.
	 * It also checks the corner case where the user does not give a keyword after the search command
	 */
	@Test
	public void test3SearchCommand() throws Exception{
		//results not found partition
		assertEquals(SEARCH_NOT_FOUND_FEEDBACK,logicObj.executeCommand(SEARCH_INVALID_KEYWORD));
		logicObj.executeCommand("edit 3 end 19:00 1 Jun 2016");
		assertEquals(SEARCH_NOT_FOUND_FEEDBACK, logicObj.executeCommand(SEARCH_WRONG_DATE));
		logicObj.executeCommand(UNDO);
		
		// results found partition
		assertEquals(SEARCH_VALID_FEEDBACK, logicObj.executeCommand(SEARCH_VALID_KEYWORD));
		assertEquals(SEARCH_EMPTY_STRING_FEEDBACK,logicObj.executeCommand(SEARCH_EMPTY_STRING));
		assertEquals(SEARCH_VALID_DATE_FEEDBACK, logicObj.executeCommand(SEARCH_VALID_DATE));
	}
	
	
	
	@Test
	/*
	 * The following test has 2 partitions, valid and invalid, based on the validity of the command
	 */
	public void test4ViewCommand() throws Exception{
		// invalid partition
		assertEquals(VIEW_INVALID_FEEDBACK, logicObj.executeCommand(VIEW_INVALID));
		// valid partition
		assertEquals(VIEW_EMPTY_LIST, logicObj.executeCommand(VIEW_DONE));
		assertEquals(VIEW_EMPTY_LIST, logicObj.executeCommand(VIEW_OVERDUE));
		assertEquals(VIEW_VALID_FEEDBACK,logicObj.executeCommand(VIEW_VALID));
		
	}	
	
	
	@Test 
	public void test5EditCommand() throws Exception{
		logicObj.executeCommand(VIEW_VALID);
		
		// test indexes first
		assertEquals(EDIT_INDEX_INVALID_FEEDBACK, logicObj.executeCommand(EDIT_INDEX_LARGER));
		assertEquals(EDIT_INDEX_INVALID_FEEDBACK, logicObj.executeCommand(EDIT_INDEX_NEGATIVE));
		assertEquals(EDIT_INDEX_INVALID_FEEDBACK, logicObj.executeCommand(EDIT_INDEX_ZERO));
		assertEquals(EDIT_INDEX_STRING_FEEDBACK, logicObj.executeCommand(EDIT_INDEX_STRING));
		assertEquals(EDIT_INVALID_FIELD_FEEDBACK,logicObj.executeCommand(EDIT_INVALID_FIELD));
		
		
		assertEquals(EDIT_TIME_BEFORE_CURRENT_FEEDBACK,logicObj.executeCommand(EDIT_START_BEFORE_CURRENT));
		assertEquals(EDIT_START_AFTER_END_FEEDBACK,logicObj.executeCommand(EDIT_START_AFTER_END));
		assertEquals(EDIT_TIME_BEFORE_CURRENT_FEEDBACK, logicObj.executeCommand(EDIT_END_BEFORE_CURRENT));
		assertEquals(EDIT_START_EQUALS_END_FEEDBACK,logicObj.executeCommand(EDIT_START_EQUALS_END));
		
		assertEquals(EDIT_START_NO_END_FEEDBACK, logicObj.executeCommand(EDIT_START_NO_END));
		assertEquals(EDIT_EVENT_START_AFTER_END_FEEDBACK, logicObj.executeCommand(EDIT_EVENT_START_AFTER_END));
		assertEquals(EDIT_EVENT_START_AFTER_END_FEEDBACK, logicObj.executeCommand(EDIT_EVENT_START_EQUAL_END));
		assertEquals(EDIT_TIME_BEFORE_CURRENT_FEEDBACK, logicObj.executeCommand(EDIT_EVENT_START_BEFORE_CURRENT));
		
		assertEquals(EDIT_END_BEFORE_START_FEEDBACK, logicObj.executeCommand(EDIT_END_BEFORE_START));
		assertEquals(EDIT_END_EQUALS_START_FEEDBACK, logicObj.executeCommand(EDIT_END_EQUALS_START));
		
		assertEquals(EDIT_PRIORITY_INVALID_FEEDBACK,logicObj.executeCommand(EDIT_PRIORITY_INVALID));
		
		assertEquals(EDIT_VALID_FEEDBACK, logicObj.executeCommand(EDIT_VALID));
		assertEquals(EDIT_PRIORITY_HIGH,logicObj.executeCommand(EDIT_PRIORITY_HIGH_FEEDBACK));
		assertEquals(EDIT_PRIORITY_MEDIUM_FEEDBACK,logicObj.executeCommand(EDIT_PRIORITY_MEDIUM));
		assertEquals(EDIT_PRIORITY_LOW_FEEDBACK,logicObj.executeCommand(EDIT_PRIORITY_LOW));
		assertEquals(EDIT_PRIORITY_NONE_FEEDBACK,logicObj.executeCommand(EDIT_PRIORITY_NONE));
	
	
	}
	
	
	/*
	 * The following test case contains 3 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the final partition where the value is within what is
	 * expected
	 */
	@Test
	
	public void test6DeleteCommand() throws Exception{
		// invalid partition
		logicObj.executeCommand(VIEW_VALID);
		assertEquals(DELETE_STRING_FEEDBACK, logicObj.executeCommand(DELETE_STRING));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_LARGER));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_NEGATIVE));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_ZERO));
		assertEquals(DELETE_INVALID_MULTIPLE_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_MULTIPLE));
		
		// valid partition
		assertEquals(DELETE_VALID_MULTIPLE_FEEDBACK, logicObj.executeCommand(DELETE_VALID_MULTIPLE));
		logicObj.executeCommand(UNDO);
		logicObj.executeCommand(VIEW_VALID);
		assertEquals(DELETE_VALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_VALID_INDEX));

	}

	@Test
	/*
	 * The following test case contains 4 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the third partition where the value is within what is
	 * expected and the last one where a string is given instead of an integer
	 */
	
	public void test71MarkCommand() throws Exception{
		logicObj.executeCommand(VIEW_VALID);
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_NEGATIVE));
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_ZERO));
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_LARGER));
		assertEquals(DONE_INVALID_COMMAND_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_STRING));
		logicObj.executeCommand(VIEW_VALID);
		assertEquals(DONE_INVALID_INDEX_MULTIPLE_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_MULTIPLE));
		assertEquals(DONE_VALID_INDEX_MULTIPLE_FEEDBACK, logicObj.executeCommand(DONE_VALID_INDEX_MULTIPLE));
		logicObj.executeCommand(UNDO);
		logicObj.executeCommand(VIEW_VALID);
		assertEquals(DONE_VALID_FEEDBACK, logicObj.executeCommand(DONE_VALID_INDEX));
	}
	
	
	@Test
	public void test7UnmarkCommand() throws Exception{
		logicObj.executeCommand(VIEW_VALID);
		logicObj.executeCommand("done 1");
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(UNMARK_INVALID_INDEX_NEGATIVE));
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(UNMARK_INVALID_INDEX_ZERO));
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(UNMARK_INVALID_INDEX_LARGER));
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_INVALID_COMMAND_FEEDBACK, logicObj.executeCommand(UNMARK_INVALID_INDEX_STRING));
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_INVALID_MULTIPLE_FEEDBACK, logicObj.executeCommand(UNMARK_INVALID_MULTIPLE));
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_VALID_MULTIPLE_FEEDBACK, logicObj.executeCommand(UNMARK_VALID_MULTIPLE));
		logicObj.executeCommand(UNDO);
		logicObj.executeCommand(VIEW_DONE);
		assertEquals(UNMARK_VALID_FEEDBACK, logicObj.executeCommand(UNMARK_VALID));
	}
	

	/*
	 * The following test case contains 3 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the final partition where the value is within what is
	 * expected
	 */
	
		
	@Test
	public void test91UndoCommand() throws Exception {
		assertEquals(UNDO_FEEDBACK, logicObj.executeCommand(UNDO));
		
	}
	
	@Test
	public void test9RedoCommand() throws Exception {
		assertEquals(REDO_FEEDBACK, logicObj.executeCommand(REDO));
	}
}