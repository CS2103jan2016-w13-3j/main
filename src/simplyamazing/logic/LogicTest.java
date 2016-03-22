package simplyamazing.logic;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import simplyamazing.data.Task;
import java.io.IOException;
import java.util.ArrayList;

public class LogicTest {
	private static ArrayList<Task> list = new ArrayList<Task>();;
	private static Logic logicObj= new Logic();;
	
	
	private static final String LOCATION_COMMAND_FAIL = "location just a placeholder";
	private static final String LOCATION_COMMAND_PASS = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_FEEDBACK_FAIL = "Provided storage location is not a valid directory.";
	private static final String LOCATION_FEEDBACK_PASS = "storage location of task data has been successfully set";
	
	
	
	
	@Test
	public void testSetLocation() throws Exception{
		assertEquals(new Exception(LOCATION_FEEDBACK_FAIL),logicObj.executeCommand(LOCATION_COMMAND_FAIL));
		assertEquals(new Exception(LOCATION_FEEDBACK_PASS), logicObj.executeCommand(LOCATION_COMMAND_PASS));
	}
	
	@Test
	public void testValidCommandTypes() throws Exception {
		assertEquals(new Exception("the add command is not correct"),logicObj.executeCommand("add "));
		assertEquals(new Exception("Error: Invalid command entered. Please enter \"help\" to view command format"), logicObj.executeCommand("hi"));
		assertEquals(new Exception("there is no previous command to undo"), logicObj.executeCommand("undo"));
		assertEquals(new Exception("the index of deleting is invalid"), logicObj.executeCommand("delete"));
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
