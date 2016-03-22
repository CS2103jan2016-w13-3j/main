package simplyamazing.logic;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import simplyamazing.data.Task;
import java.io.IOException;
import java.util.ArrayList;

public class LogicTest {
	private static ArrayList<Task> list;
	private static Logic logicObj;
	
	@BeforeClass
	public static void setUpBeforeClass() throws ClassNotFoundException, IOException{
		logicObj = new Logic();
		list = new ArrayList<Task>();
	}
	
	
	@Test
	public void testValidCommandTypes() throws Exception {
		//assertEquals(new Exception("the add command is not correct"),logicObj.executeCommand("add "));
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
