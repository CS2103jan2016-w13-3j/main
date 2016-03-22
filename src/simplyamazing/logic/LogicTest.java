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
		assertEquals("the add command is not correct",logicObj.executeCommand("add "));
		assertEquals("Error: Invalid command entered. Please enter \"help\" to view command format", logicObj.executeCommand("hi"));
		assertEquals("there is no previous command to undo", logicObj.executeCommand("undo"));
		assertEquals("the index of deleting is invalid", logicObj.executeCommand("delete"));
	}

}
