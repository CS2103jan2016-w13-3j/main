package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import simplyamazing.parser.Handler;
import simplyamazing.parser.Parser;
import simplyamazing.data.Task;
import simplyamazing.logic.Logic;

import java.io.IOException;
import java.util.ArrayList;

public class ParserTest {
	private static final String EDIT_COMMAND_VALID = "edit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_FEEDBACK = "";
	private static final String EDIT_COMMAND_INVALID_WRONG_KEYWORD = "edit 2 attribute flying";
	private static final String EDIT_COMMAND_INVALID_FEEDBACK = "The command of input's field is invalid";
	private static final String EDIT_COMMAND_INVALID_WITHOUT_INDEX = "edit startTime 12:00 20 Mar 2016";
	
	private static final String ADD_COMMAND_FLOATING_VALID = "add go home";
	private static final String ADD_COMMAND_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_DEADLINE_VALID = "add finish homework by 22:00 20 Apr 2017";
	private static final String ADD_COMMAND_DEADLINE_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_EVENT_VALID = "add have a dinner from 19:00 28 Mar 2016 to 21:00 28 Mar 2016";
	private static final String ADD_COMMAND_EVENT_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_WITH_ONLY_STARTTIME = "add walk from 14:00 10 May 2017";
	private static final String ADD_COMMAND_INVALID_FEEDBACK = "the add command is not correct";
	private static final String ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME = "add swim from 21:00 22 Mar 2017 to 17:00 10 Mar 2016";
	
	private static final String HELP_COMMAND_VALID = "help";
	private static final String HELP_COMMAND_VALID_FEEDBACK = "";
	private static final String HELP_COMMAND_INVALID_WITH_STRING = "help abc";
	private static final String HELP_COMMAND_INVALID_FEEDBACK = "the help command is invalid";
	private static final String HELP_COMMAND_VALID_TASKTYPE = "help search";
	
	private static final String DELETE_COMMAND_VALID_INDEX = "delete 1";
	private static final String DELETE_COMMAND_VALID_INDEX_FEEDBACK = "";
	private static final String DELETE_COMMAND_INVALID_DUMMY_STRING = "delete go home";
	private static final String DELETE_COMMAND_INVALID_INDEX_FEEDBACK = "the index of deleting is invalid";
    
	private static final String LOCATION_COMMAND_INVALID = "location ";
	private static final String LOCATION_COMMAND_VALID = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_FEEDBACK_INVALID = "The location for storing is invalid";
	private static final String LOCATION_COMMAND_FEEDBACK_VALID = "";
	
	private static final String DONE_COMMAND_VALID = "done 1";
	private static final String DONE_COMMAND_VALID_FEEDBACK = "";
	private static final String DONE_COMMAND_INVALID_WRONG_KEYWORD = "done home";
	private static final String DONE_COMMAND_INVALID_FEEDBACK = "the index of done function is invalid.";
	
	private static Parser parser = new Parser();
	
	@Test (expected = Exception.class)
	/*
	 * The following test uses the equivalence partitioning for heuristic testing. 
	 * There are two equivalent partitions which are valid and invalid commands.
	 */
	public void testAddCommand() throws Exception{
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINE_VALID).getFeedBack());
		
		assertEquals(ADD_COMMAND_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_WITH_ONLY_STARTTIME).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME).getFeedBack());
	}
	
	
	
	@Test(expected = Exception.class)
	public void testHelpCommand() throws Exception{
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID_TASKTYPE).getFeedBack());
		assertEquals(HELP_COMMAND_INVALID_FEEDBACK, parser.getHandler(HELP_COMMAND_INVALID_WITH_STRING).getFeedBack());
	}
	
	
	

}
