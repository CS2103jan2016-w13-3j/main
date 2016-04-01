package test;

import static org.junit.Assert.*;
import org.junit.Test;
import simplyamazing.parser.Parser;

public class ParserTest {
	private static final String EDIT_COMMAND_VALID = "edit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_FEEDBACK = "";
	private static final String EDIT_COMMAND_INVALID_WRONG_KEYWORD = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String EDIT_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String EDIT_COMMAND_INVALID_WITHOUT_INDEX = "edit startTime 12:00 20 Mar 2016";

	private static final String ADD_COMMAND_FLOATING_VALID = "add go home";
	private static final String ADD_COMMAND_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_DEADLINE_VALID = "add finish homework by 22:00 20 Apr 2017";
	private static final String ADD_COMMAND_DEADLINE_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_EVENT_VALID = "add have a dinner from 19:00 28 Apr 2016 to 21:00 28 Apr 2016";
	private static final String ADD_COMMAND_EVENT_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_WITH_ONLY_STARTTIME = "add walk from 14:00 10 May 2017";
	private static final String ADD_COMMAND_INVALID_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	private static final String ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT = "Error: Please ensure the fields are correct";
	private static final String ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME = "add swim from 21:00 22 Mar 2017 to 17:00 10 Mar 2016";

	private static final String HELP_COMMAND_VALID = "help";
	private static final String HELP_COMMAND_VALID_FEEDBACK = "";
	private static final String HELP_COMMAND_INVALID_WITH_STRING = "help abc";
	private static final String HELP_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";
	private static final String HELP_COMMAND_VALID_TASKTYPE = "help search";

	private static final String DELETE_COMMAND_VALID_INDEX = "delete 1";
	private static final String DELETE_COMMAND_VALID_INDEX_FEEDBACK = "";
	private static final String DELETE_COMMAND_INVALID_DUMMY_STRING = "Error: Invalid command entered. Please enter \"help\" to view command format";
	private static final String DELETE_COMMAND_INVALID_FEEDBACK = "Error: Invalid command entered. Please enter \"help\" to view command format";

	private static final String LOCATION_COMMAND_INVALID = "location ";
	private static final String LOCATION_COMMAND_VALID = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_INVALID_FEEDBACK = "Error: Location provided is invalid";
	private static final String LOCATION_COMMAND_VALID_FEEDBACK = "";

	private static final String DONE_COMMAND_VALID = "done 1";
	private static final String DONE_COMMAND_VALID_FEEDBACK = "";
	private static final String DONE_COMMAND_INVALID_WRONG_KEYWORD = "done home";
	private static final String DONE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";

	private static Parser parser = new Parser();

	@Test//(expected = Exception.class)
	/*
	 * The following test uses the equivalence partitioning for heuristic
	 * testing. There are two equivalent partitions which are valid and invalid
	 * commands.
	 */
	public void testAddCommand() throws Exception {
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_DEADLINE_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINE_VALID).getFeedBack());

		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT , parser.getHandler(ADD_COMMAND_WITH_ONLY_STARTTIME).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FEEDBACK,
				parser.getHandler(ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testLocationCommand() throws Exception {
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID).getFeedBack());
		assertEquals(LOCATION_COMMAND_INVALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_INVALID).getFeedBack());
	}

	public void testEditCommand() throws Exception {
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WITHOUT_INDEX).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testHelpCommand() throws Exception {
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID_TASKTYPE).getFeedBack());
		assertEquals(HELP_COMMAND_INVALID_FEEDBACK, parser.getHandler(HELP_COMMAND_INVALID_WITH_STRING).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testDeleteCommand() throws Exception {
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_INDEX).getFeedBack());
		assertEquals(DELETE_COMMAND_INVALID_FEEDBACK,parser.getHandler(DELETE_COMMAND_INVALID_DUMMY_STRING).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testDoneCommand() throws Exception {
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID).getFeedBack());
		assertEquals(DONE_COMMAND_INVALID_FEEDBACK,parser.getHandler(DONE_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
	}

}
