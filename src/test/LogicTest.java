package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import simplyamazing.data.Task;
import simplyamazing.logic.Logic;

import java.io.IOException;
import java.util.ArrayList;

public class LogicTest {
	private static ArrayList<Task> list = new ArrayList<Task>();
	private static Logic logicObj= new Logic();
	
	
	private static final String LOCATION_COMMAND_FAIL = "location just a placeholder";
	private static final String LOCATION_COMMAND_PASS = "location C:\\Users\\Ishpal\\Desktop\\Task Data";
	private static final String LOCATION_FEEDBACK_FAIL = "Error: Not a valid directory.";
	private static final String LOCATION_FEEDBACK_PASS = "Storage location of task data has been sucessfully set as C:\\Users\\Ishpal\\Desktop\\Task Data.";
	private static final String LOCATION_EMPTY_STRING = "location ";
	private static final String LOCATION_EMPTY_STRING_FEEDBACK = "Error: Location provided is invalid";
	
	
	private static final String ADD_TASK_PASS = "add hello world";
	private static final String ADD_TASK_PASS_FEEDBACK = "Task [hello world] has been added.";
	private static final String ADD_DEADLINE_PASS = "add cs2103 peer review by 23:59 25 Mar 2016";
	private static final String ADD_DEADLINE_PASS_FEEDBACK = "Task[cs2103 peer review by 23:59 25 Mar 2016] has been added.";
	private static final String ADD_EVENT_PASS = "add hackathon in SOC from 09:30 26 Mar 2016 to 10:00 27 Mar 2016";
	private static final String ADD_EVENT_PASS_FEEDBACK = "Task[hahckathon in SOC from 9:30 26 Mar 2016 to 10:00 27 Mar 2016] has been added.";
	private static final String ADD_TASK_WITH_STARTIME_ONLY = "add sleep from 03:00 24 Mar 2016";
	private static final String ADD_ERROR_MESSAGE = "the add command is not correct";
	private static final String ADD_TASK_ENDTIME_BEFORE_STARTIME = "add play fifa from 14:00 30 Mar 2016 to 13:00 30 Mar 2016";
	
	private static final String DELETE_VALID_INDEX = "delete 2";
	private static final String DELETE_VALID_INDEX_FEEDBACK = "Task[hackathon in SOC from 9:30 26 Mar 2016 to 10:00 27 Mar 2016] has been successfully deleted.";
	private static final String DELETE_INVALID_INDEX_LARGER = "delete 5";
	private static final String DELETE_INVALID_INDEX_FEEDBACK = "Error: Invalid index entered";
	private static final String DELETE_INVALID_INDEX_ZERO = "delete 0";
	private static final String DELETE_INVALID_INDEX_NEGATIVE = "delete -1";
	private static final String DELETE_STRING = "delete hello";
	private static final String DELETE_STRING_FEEDBACK = "Error: Index provided is not an Integer.";
	
	private static final String DONE_INVALID_INDEX_NEGATIVE = "done -1";
	private static final String DONE_INVALID_INDEX_ZERO = "done 0";
	private static final String DONE_INVALID_INDEX_LARGER = "done 30";
	private static final String DONE_INVALID_INDEX_FEEDBACK = "Error: Invalid index entered";
	private static final String DONE_INVALID_INDEX_STRING = "done abcd";
	private static final String DONE_INVALID_COMMAND_FEEDBACK = "Error: Invalid command entered. Please enter \"help\" to view command format";
	private static final String DONE_VALID_INDEX  = "done 19";
	private static final String DONE_VALID_FEEDBACK = "Task [hello world] has been marked as done.";
	
	
	private static final String SEARCH_VALID_KEYWORD = "search hello";
	private static final String SEARCH_VALID_FEEDBACK = "1. Task[hello world]";
	private static final String SEARCH_INVALID_KEYWORD = "search joke";
	private static final String SEARCH_INVALID_FEEDBACK = "Error: No tasks found";
	private static final String SEARCH_EMPTY_STRING = "search ";
	private static final String SEARCH_ALL_TASKS = "";
	
	
	private static final String HELP_VALID = "help";
	private static final String HELP_VALID_FEEDBACK = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help done\n6. help search\n"
			+ "7. help location\n8. help undo\n9. help exit\n";
	private static final String HELP_INVALID = "help 1";
	private static final String HELP_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";
	private static final String HELP_VALID_UNDO = "help undo";
	private static final String HELP_VALID_UNDO_FEEDBACK = "Undo the most recent command\nCommand: undo\n";
	private static final String HELP_VALID_DONE = "help done";
	private static final String HELP_VALID_DONE_FEEDBACK = "Marks task as completed\nCommand: done <task index>\n\nExample:\ndone 2\n";
	private static final String HELP_VALID_DELETE = "help delete";
	private static final String HELP_VALID_DELETE_FEEDBACK = "Delete task from list\nCommand: delete <task index>\n\nExample:\ndelete 1";
	private static final String HELP_VALID_SEARCH = "help search";
	private static final String HELP_VALID_SEARCH_FEEDBACK = "Search for tasks containing the given keyword\nCommand: search <keyword>\n\nExample:\nsearch meeting\n";
	private static final String HELP_VALID_VIEW = "help view";
	private static final String HELP_VALID_VIEW_FEEDBACK = "1.Display all tasks\n Command: view\n\n2.Display tasks with deadlines\n"
			+ "Command: view deadlines\n\n3.Display all events\nCommand: view events\n\n4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n6.Display overdue tasks\nCommand: view overdue\n\n";
	private static final String HELP_VALID_EDIT = "help edit";
	private static final String HELP_VALID_EDIT_FEEDBACK = "Edit content in a task\nCommand: edit <task index> <task header> <updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n2. edit 3 start 22:00 26 may 2016, end 22:40 26 may 2016\n\n"
			+ "3. edit 1 priority high";
	private static final String HELP_VALID_ADD = "help add";
	private static final String HELP_VALID_ADD_FEEDBACK = "1.Add a task to the list\nCommand: add <task description>\n\nExample: add prepare presentation\n\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: submit marketing report by 17:00 20 Dec 2016\n";
	private static final String HELP_VALID_LOCATION = "help location";
	private static final String HELP_VALID_LOCATION_FEEDBACK = "Sets the storage location or folder for application data\n"
			+ "Command: location <path>\n" + "\nExample:\nlocation C:\\Users\\Jim\\Desktop\\Task Data";
	
	
	@Test
	public void testValidCommandTypes() throws Exception{
		assertEquals("Error: There is no previous command to undo", logicObj.executeCommand("undo"));      // prob
		assertEquals("Error: Please ensure the fields are correct",logicObj.executeCommand("add "));
		assertEquals("Error: Invalid command entered. Please enter \"help\" to view all commands and their format", logicObj.executeCommand("hi"));
		assertEquals("Error: Index provided is not an Integer.", logicObj.executeCommand("delete"));
		assertEquals("Error: Invalid command entered. Please enter \"help\" to view all commands and their format", logicObj.executeCommand(""));
		
	}
	
	
	@Test
	public void testSetLocationPass() throws Exception {
		assertEquals(LOCATION_FEEDBACK_PASS, logicObj.executeCommand(LOCATION_COMMAND_PASS));
	}
	
	@Test
	/*
	 * This test case has 2 partitions, namely correct and incorrect. These refer to the validity of the command input
	 * The correct partition encompasses all valid file paths while the incorrect encompasses all invalid file paths
	 */
	public void testSetLocationFail() throws Exception{
			assertEquals(LOCATION_FEEDBACK_FAIL, logicObj.executeCommand(LOCATION_COMMAND_FAIL));
			assertEquals(LOCATION_EMPTY_STRING_FEEDBACK, logicObj.executeCommand(LOCATION_EMPTY_STRING));
	}
	
	
	//@Test (expected = Exception.class)
	/*
	 * The following test has 2 equivalent partitions, valid and invalid commands.
	 */
	/*
	public void testAddCommand() throws Exception{
		assertEquals(ADD_TASK_PASS_FEEDBACK, logicObj.executeCommand(ADD_TASK_PASS));
		assertEquals(ADD_EVENT_PASS_FEEDBACK, logicObj.executeCommand(ADD_EVENT_PASS));
		assertEquals(ADD_DEADLINE_PASS_FEEDBACK, logicObj.executeCommand(ADD_DEADLINE_PASS));
		
		assertEquals(new Exception(ADD_ERROR_MESSAGE), logicObj.executeCommand(ADD_TASK_WITH_STARTIME_ONLY));
		assertEquals(new Exception(ADD_ERROR_MESSAGE), logicObj.executeCommand(ADD_TASK_ENDTIME_BEFORE_STARTIME));
	}
	*/
	
	
	@Test
	public void testHelpCommand() throws Exception{
		assertEquals(HELP_VALID_FEEDBACK, logicObj.executeCommand(HELP_VALID));
		assertEquals(HELP_INVALID_FEEDBACK, logicObj.executeCommand(HELP_INVALID));
		assertEquals(HELP_VALID_UNDO_FEEDBACK, logicObj.executeCommand(HELP_VALID_UNDO));
		assertEquals(HELP_VALID_DONE_FEEDBACK, logicObj.executeCommand(HELP_VALID_DONE));
		assertEquals(HELP_VALID_DELETE_FEEDBACK, logicObj.executeCommand(HELP_VALID_DELETE));
		assertEquals(HELP_VALID_VIEW_FEEDBACK, logicObj.executeCommand(HELP_VALID_VIEW));
		assertEquals(HELP_VALID_EDIT_FEEDBACK, logicObj.executeCommand(HELP_VALID_EDIT));
		assertEquals(HELP_VALID_SEARCH_FEEDBACK, logicObj.executeCommand(HELP_VALID_SEARCH));
		assertEquals(HELP_VALID_LOCATION_FEEDBACK, logicObj.executeCommand(HELP_VALID_LOCATION));
		assertEquals(HELP_VALID_ADD_FEEDBACK, logicObj.executeCommand(HELP_VALID_ADD));
	}
	
	/*
	 * The following test case has 2 different partitions, based on whether the command is valid or invalid.
	 * It also checks the corner case where the user does not give a keyword after the search command
	 */
	
	/*
	@Test(expected = Exception.class)
	public void testSearchCommand() throws Exception{
		assertEquals(new Exception(SEARCH_INVALID_FEEDBACK),logicObj.executeCommand(SEARCH_INVALID_KEYWORD));
		assertEquals(SEARCH_VALID_FEEDBACK, logicObj.executeCommand(SEARCH_VALID_KEYWORD));
		assertEquals(SEARCH_ALL_TASKS,logicObj.executeCommand(SEARCH_EMPTY_STRING));
	}
	
	
	/*
	 * The following test case contains 3 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the final partition where the value is within what is
	 * expected
	 */
	
	/*
	@Test(expected = Exception.class)
	public void testDeleteCommand() throws Exception{
		assertEquals(DELETE_VALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_VALID_INDEX));
		assertEquals(DELETE_STRING_FEEDBACK, logicObj.executeCommand(DELETE_STRING));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_LARGER));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_NEGATIVE));
		assertEquals(DELETE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DELETE_INVALID_INDEX_ZERO));
	}
	
	
	@Test
	/*
	 * The following test case contains 3 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the final partition where the value is within what is
	 * expected
	 */
	
	/*
	public void testValidIndex(){
		list.clear();
		addItemsToList(5);
		
		// this is the boundary case for negative value partition. Other values include -2, -10, -10000
		assertFalse(Logic.checkIndexValid(new String("-3"), list)); 
		assertFalse(Logic.checkIndexValid(new String("0"), list));
		
		// this is the boundary case for the correct value partition. Values should range from [1,5]
		assertTrue(Logic.checkIndexValid(new String("1"), list));
		
		// this is the boundary case for positive value partition. Other values include 10, 1000, 10000
		assertFalse(Logic.checkIndexValid(new String("6"), list));
	}
	
	
	@Test(expected = Exception.class)
	/*
	 * The following test case contains 4 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the third partition where the value is within what is
	 * expected and the last one where a string is given instead of an integer
	 */
	
	/*
	public void testMarkCommand() throws Exception{
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_NEGATIVE));
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_ZERO));
		assertEquals(DONE_INVALID_INDEX_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_LARGER));
		assertEquals(DONE_INVALID_COMMAND_FEEDBACK, logicObj.executeCommand(DONE_INVALID_INDEX_STRING));
		assertEquals(DONE_VALID_FEEDBACK, logicObj.executeCommand(DONE_VALID_INDEX));
	}
	
	
	public void addItemsToList(int numItemsToAdd) {
		for(int i=0; i<numItemsToAdd; i++) {
			list.add(new Task());
		}
	}*/
}