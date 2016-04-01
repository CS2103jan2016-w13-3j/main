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
	private static final String LOCATION_COMMAND_PASS = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_FEEDBACK_FAIL = "Provided storage location is not a valid directory.";
	private static final String LOCATION_FEEDBACK_PASS = "storage location of task data has been successfully set";
	
	private static final String ADD_TASK_PASS = "add hello world";
	private static final String ADD_TASK_PASS_FEEDBACK = "Task [hello world] has been added.";
	private static final String ADD_DEADLINE_PASS = "add cs2103 peer review by 23:59 25 Mar 2016";
	private static final String ADD_DEADLINE_PASS_FEEDBACK = "Task[cs2103 peer review by 23:59 25 Mar 2016] has been added.";
	private static final String ADD_EVENT_PASS = "add hackathon in SOC from 09:30 26 Mar 2016 to 10:00 27 Mar 2016";
	private static final String ADD_EVENT_PASS_FEEDBACK = "Task[hackathon in SOC from 9:30 26 Mar 2016 to 10:00 27 Mar 2016] has been added.";
	private static final String ADD_TASK_WITH_STARTIME_ONLY = "add sleep from 03:00 24 Mar 2016";
	private static final String ADD_ERROR_MESSAGE = "the add command is not correct";
	private static final String ADD_TASK_ENDTIME_BEFORE_STARTIME = "add play fifa from 14:00 30 Mar 2016 to 13:00 30 Mar 2016";
	
	
	private static final String HELP_VALID = "help";
	private static final String HELP_VALID_FEEDBACK = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help done\n6. help search\n"
			+ "7. help location\n8. help undo\n9. help exit\n";
	private static final String HELP_INVALID = "help 1";
	private static final String HELP_INVALID_FEEDBACK = "the help command is invalid";
	private static final String HELP_VALID_TASKTYPE = "help undo";
	private static final String HELP_VALID_TASKTYPE_FEEDBACK = "Undo the most recent command\ncommand: undo\n";
	
	
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
	private static final String SEARCH_ALL_TASKS = "1. Task [cs2103 team meeting from 17:00 30 Mar 2016 to 19:00 30 Mar 2016]"
			+ "2. Task [prepare for 2103 tutorial demo by 22:00 30 Mar 2016]"
			+ "3. Task [finish 2107 tutorial by 22:00 31 Mar 2016]"
			+ "4. Task [attend cs2103 lecture from 16:00 01 Apr 2016 to 17:45 01 Apr 2016]"
			+ "5. Task [watch batman vs superman from 19:00 01 Apr 2016 to 21:00 01 Apr 2016]"
			+ "6. Task [football at cage kallang from 22:00 01 Apr 2016 to 01:00 02 Apr 2016]"
			+ "7. Task [submit 2105 assignment by 18:00 02 Apr 2016]"
			+ "8. Task [watch arsenal game from 20:00 02 Apr 2016 to 22:30 02 Apr 2016]"
			+ "9. Task [blue man show at MBS from 13:30 03 Apr 2016 to 15:00 03 Apr 2016]"
			+ "10. Task [2017 presentation by 14:00 16 Apr 2016]"
			+ "11. Task [2103 final exam from 13:00 25 Apr 2016 to 15:00 25 Apr 2016]"
			+ "12. Task [2107 final exam from 13:00 26 Apr 2016 to 15:00 26 Apr 2016]"
			+ "13. Task [2105 final exam from 17:00 27 Apr 2016 to 19:00 27 Apr 2016]"
			+ "14. Task [1302 final exam from 14:30 29 Apr 2016 to 16:00 29 Apr 2016]"
			+ "15. Task [first day of work from 09:00 02 May 2016 to 17:00 02 May 2016]"
			+ "16. Task [freedom from 16:00 29 Apr 2016 to 08:00 06 Aug 2016]"
			+ "17. Task [buy dinner]"
			+ "18. Task [hello world]"
			+ "18. Task [go fishing]"
			+ "19. Task [train for ippt someday]"
			+ "20. Task [visit the gym someday]";
	
	
	
	@Test(expected = Exception.class)
	/*
	 * This test case has 2 partitions, namely correct and incorrect. These refer to the validity of the command input
	 * The correct partition encompasses all valid file paths while the incorrect encompasses all invalid file paths
	 */
	public void testSetLocation() throws Exception{
		
		assertEquals(new Exception(LOCATION_FEEDBACK_FAIL),logicObj.executeCommand(LOCATION_COMMAND_FAIL));
		assertEquals(LOCATION_FEEDBACK_PASS, logicObj.executeCommand(LOCATION_COMMAND_PASS));
	}
	
	
	
	@Test(expected = Exception.class)
	public void testValidCommandTypes() throws Exception {
		assertEquals(new Exception("the add command is not correct"),logicObj.executeCommand("add "));
		assertEquals(new Exception("Error: Invalid command entered. Please enter \"help\" to view command format"), logicObj.executeCommand("hi"));
		assertEquals(new Exception("there is no previous command to undo"), logicObj.executeCommand("undo"));
		assertEquals(new Exception("the index of deleting is invalid"), logicObj.executeCommand("delete"));
	}
	
	
	
	
	@Test (expected = Exception.class)
	/*
	 * The following test has 2 equivalent partitions, valid and invalid commands.
	 */
	public void testAddCommand() throws Exception{
		assertEquals(ADD_TASK_PASS_FEEDBACK, logicObj.executeCommand(ADD_TASK_PASS));
		assertEquals(ADD_EVENT_PASS_FEEDBACK, logicObj.executeCommand(ADD_EVENT_PASS));
		assertEquals(ADD_DEADLINE_PASS_FEEDBACK, logicObj.executeCommand(ADD_DEADLINE_PASS));
		
		assertEquals(new Exception(ADD_ERROR_MESSAGE), logicObj.executeCommand(ADD_TASK_WITH_STARTIME_ONLY));
		assertEquals(new Exception(ADD_ERROR_MESSAGE), logicObj.executeCommand(ADD_TASK_ENDTIME_BEFORE_STARTIME));
	}
	
	
	
	@Test(expected = Exception.class)
	public void testHelpCommand() throws Exception{
		assertEquals(HELP_VALID_FEEDBACK, logicObj.executeCommand(HELP_VALID));
		assertEquals(new Exception(HELP_INVALID_FEEDBACK), logicObj.executeCommand(HELP_INVALID));
		assertEquals(HELP_VALID_TASKTYPE_FEEDBACK, logicObj.executeCommand(HELP_VALID_TASKTYPE));
	}
	
	/*
	 * The following test case has 2 different partitions, based on whether the command is valid or invalid.
	 * It also checks the corner case where the user does not give a keyword after the search command
	 */
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
	}
}