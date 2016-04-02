package test;

import static org.junit.Assert.*;
import org.junit.Test;
import simplyamazing.parser.Parser;

public class ParserTest {
	private static final String EDIT_COMMAND_VALID_DESCRIPTION = "edit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_ENDTIME = "edit 1 end 22:00 05 Apr 2016";
	private static final String EDIT_COMMAND_VALID_STARTTIME = "edit 1 start 29:00 05 Apr 2016";
	private static final String EDIT_COMMAND_VALID_PRIORITY = "edit 1 priority high";
	private static final String EDIT_COMMAND_VALID_FEEDBACK = "";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_BEFORE_CURRENT = "edit 2 start 12:00 30 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_BEFORE_CURRENT = "edit 3 end 14:00 01 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_TWO_KEYWORDS = "edit 3 start 12:00 24 Mar 2016,end 15:00 26 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_WRONG_KEYWORD = "edit 2 drink";	
	private static final String EDIT_COMMAND_INVALID_WITHOUT_INDEX = "edit startTime 12:00 20 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_AFTER_ENDTIME = "edit 2 start 23:00 18 Apr 2017, end 21:00 19 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_EQUAL_ENDTIME = "edit 1 start 11:00 20 Apr 2016, end 11:00 20 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_INDEX_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK ="Error: Time provided must be after the current time";
	private static final String EDII_COMMAND_INVALID_START_AFTER_END_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	
	private static final String ADD_COMMAND_FLOATING_VALID = "add go home";
	private static final String ADD_COMMAND_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_DEADLINE_VALID = "add finish homework by 22:00 20 Apr 2017";
	private static final String ADD_COMMAND_DEADLINE_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_EVENT_VALID = "add have a dinner from 19:00 28 Apr 2016 to 21:00 28 Apr 2016";
	private static final String ADD_COMMAND_EVENT_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_WITH_ONLY_STARTTIME_INVALID = "add walk from 14:00 10 May 2017";
	private static final String ADD_COMMAND_STARTTIME_BEFORE_CURRENTTIME ="add test from 13:00 12 Mar 2016 to 14:00 11 Apr 2016";
	private static final String ADD_COMMAND_INVALID_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	private static final String ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK = "Error: Please ensure the fields are correct";
	private static final String ADD_COMMAND_ERROR_MESSAGE_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	private static final String ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME = "add swim from 21:00 22 Mar 2017 to 17:00 10 Mar 2016";
     
	private static final String HELP_COMMAND_VALID = "help";
	private static final String HELP_COMMAND_ADD_VALID = "help add";
	private static final String HELP_COMMAND_DELETE_VALID = "help delete";
	private static final String HELP_COMMAND_EDIT_VALID = "help edit";
	private static final String HELP_COMMAND_VIEW_VALID = "help view";
	private static final String HELP_COMMAND_DONE_VALID = "help done";
	private static final String HELP_COMMAND_SEARCH_VALID = "help search";
	private static final String HELP_COMMAND_UNDO_VALID = "help undo";
	private static final String HELP_COMMAND_LOCATION_VALID = "help location";
	private static final String HELP_COMMAND_EXIT_VALID = "help exit";
	private static final String HELP_COMMAND_VALID_FEEDBACK = "";
	private static final String HELP_COMMAND_INVALID_WITH_STRING = "help abc";
	private static final String HELP_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";

	private static final String DELETE_COMMAND_VALID_INDEX = "delete 1";
	private static final String DELETE_COMMAND_VALID_INDEX_FEEDBACK = "";
	private static final String DELETE_COMMAND_INVALID_DUMMY_STRING = "delete cba";
	private static final String DELETE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";

	private static final String LOCATION_COMMAND_INVALID = "location ";
	private static final String LOCATION_COMMAND_VALID = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_INVALID_FEEDBACK = "Error: Location provided is invalid";
	private static final String LOCATION_COMMAND_VALID_FEEDBACK = "";

	private static final String DONE_COMMAND_VALID = "done 1";
	private static final String DONE_COMMAND_VALID_FEEDBACK = "";
	private static final String DONE_COMMAND_INVALID_WRONG_KEYWORD = "done home";
	private static final String DONE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";
	
	private static final String VIEW_COMMAND_VALID = "view";
	private static final String VIEW_COMMAND_EVENT_VALID = "view events";
	private static final String VIEW_COMMAND_DEADLINES_VALID = "view deadlines";
	private static final String VIEW_COMMAND_TASKS_VALID = "view tasks";
	private static final String VIEW_COMMAND_DONE_VALID = "view done";
	private static final String VIEW_COMMAND_OVERDUE_VALID = "view overdue";
	private static final String VIEW_COMMAND_VALID_FEEDBACK = "";
	private static final String VIEW_COMMAND_INVALID_WRONG_KEYWORD = "view abc";
	private static final String VIEW_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help view\" command to see all the valid keywords";

    
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

		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_WITH_ONLY_STARTTIME_INVALID).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FEEDBACK,
				parser.getHandler(ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_MESSAGE_DATE_BEFORE_CURRENT, parser.getHandler(ADD_COMMAND_STARTTIME_BEFORE_CURRENTTIME).getFeedBack());
		
	}

	@Test//(expected = Exception.class)
	public void testLocationCommand() throws Exception {
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID).getFeedBack());
		assertEquals(LOCATION_COMMAND_INVALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_INVALID).getFeedBack());
	}
	
    @Test
	public void testEditCommand() throws Exception {
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_ENDTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_STARTTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_PRIORITY).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_INDEX_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WITHOUT_INDEX).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_BEFORE_CURRENT).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_BEFORE_CURRENT).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_TWO_KEYWORDS).getFeedBack());
		assertEquals(EDII_COMMAND_INVALID_START_AFTER_END_FEEDBACK ,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_AFTER_ENDTIME).getFeedBack());
		assertEquals(EDII_COMMAND_INVALID_START_AFTER_END_FEEDBACK ,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_EQUAL_ENDTIME).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testHelpCommand() throws Exception {
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_ADD_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_DELETE_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VIEW_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_LOCATION_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_DONE_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_EXIT_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_SEARCH_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_EDIT_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_UNDO_VALID).getFeedBack());
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
	
	@Test
	public void testViewCommand() throws Exception {
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK, parser.getHandler(VIEW_COMMAND_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_DEADLINES_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_TASKS_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_DONE_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_OVERDUE_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_INVALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());	
	}

}
