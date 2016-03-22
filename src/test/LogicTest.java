package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import simplyamazing.data.Task;
import simplyamazing.logic.Logic;

import java.io.IOException;
import java.util.ArrayList;

public class LogicTest {
	private static ArrayList<Task> list = new ArrayList<Task>();;
	private static Logic logicObj= new Logic();;
	
	
	private static final String LOCATION_COMMAND_FAIL = "location just a placeholder";
	private static final String LOCATION_COMMAND_PASS = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_FEEDBACK_FAIL = "Provided storage location is not a valid directory.";
	private static final String LOCATION_FEEDBACK_PASS = "storage location of task data has been successfully set";
	
	private static final String ADD_TASK_PASS = "add hello world";
	private static final String ADD_TASK_PASS_FEEDBACK = "Task[hello world] has been added.";
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
	
	
	
	@Test(expected = Exception.class)
	/*
	 * This test case has 2 partitions, namely correct and incorrect. These refer to the validity of the command input
	 * The correct partition encompasses all valid file paths while the incorrect encompasses all invalid file paths
	 */
	public void testSetLocation() throws Exception{
		
		assertEquals(new Exception(LOCATION_FEEDBACK_FAIL),logicObj.executeCommand(LOCATION_COMMAND_FAIL));
		assertEquals(new Exception(LOCATION_FEEDBACK_PASS), logicObj.executeCommand(LOCATION_COMMAND_PASS));
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
		
		assertEquals(ADD_ERROR_MESSAGE, logicObj.executeCommand(ADD_TASK_WITH_STARTIME_ONLY));
		assertEquals(ADD_ERROR_MESSAGE, logicObj.executeCommand(ADD_TASK_ENDTIME_BEFORE_STARTIME));
	}
	
	
	
	@Test(expected = Exception.class)
	public void testHelpCommand() throws Exception{
		assertEquals(HELP_VALID_FEEDBACK, logicObj.executeCommand(HELP_VALID));
		assertEquals(new Exception(HELP_INVALID_FEEDBACK), logicObj.executeCommand(HELP_INVALID));
		assertEquals(HELP_VALID_TASKTYPE_FEEDBACK, logicObj.executeCommand(HELP_VALID_TASKTYPE));
	}
	
	
	
	@Test
	/*
	 * The following test case contains 3 equivalent partitions, a negative partition where the value is below what is expected,
	 * a positive partition, where the value is larger than expected and the final partition where the value is withing what is
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
	
	
	
	public void addItemsToList(int numItemsToAdd) {
		for(int i=0; i<numItemsToAdd; i++) {
			list.add(new Task());
		}
	}
}