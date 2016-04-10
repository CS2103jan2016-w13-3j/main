//@@author A0112659A
package test;

import static org.junit.Assert.*;
import org.junit.Test;
import simplyamazing.parser.Parser;

public class ParserTest {
	private static final String EDIT_COMMAND_VALID_DESCRIPTION = "edit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_ALT = "change 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_ALT_2 = "update 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_CAP = "EDIT 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_CAP_FIRSTCHAR = "Edit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_CAP_SECONDCHAR = "eDit 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_CAP_THIRDCHAR = "edIt 1 description dancing";
	private static final String EDIT_COMMAND_VALID_DESCRIPTION_CAP_FOURTHCHAR = "ediT 1 description dancing";
	private static final String EDIT_COMMAND_VALID_ENDTIME = "edit 1 end 22:00 05 MAY 2016";
	private static final String EDIT_COMMAND_VALID_ENDTIME_FLEX = "EDIT 1 END 8pm next Friday";
	private static final String EDIT_COMMAND_VALID_ENDTIME_NONE = "edit 1 EnD nONE";
	private static final String EDIT_COMMAND_VALID_STARTTIME = "edit 1 start 23:00 20 Apr 2016";
	private static final String EDIT_COMMAND_VALID_STARTTIME_FLEX = "edit 1 staRT 9am tomorrow";
	private static final String EDIT_COMMAND_VALID_STARTTIME_NONE = "edit 1 START NoNe";
	private static final String EDIT_COMMAND_VALID_PRIORITY = "edit 1 priority high";
	private static final String EDIT_COMMAND_VALID_FEEDBACK = "";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_BEFORE_CURRENT = "edit 2 start 12:00 30 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_BEFORE_CURRENT = "edit 3 end 14:00 01 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_TWO_KEYWORDS = "edit 3 start 12:00 24 Mar 2016,end 15:00 26 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_WRONG_KEYWORD = "edit 2 drink";	
	private static final String EDIT_COMMAND_INVALID_WITHOUT_INDEX = "edit startTime 12:00 20 Mar 2016";
	private static final String EDIT_COMMAND_INVALID_STARTTIME = "edit 1 STart 19:00 30 Feb 2017";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_DUMMY = "edit 1 StArT aBCD";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_AFTER_ENDTIME = "edit 2 start 23:00 18 Apr 2017, end 21:00 19 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_EQUAL_ENDTIME = "edit 1 start 11:00 20 Apr 2016, end 11:00 20 Apr 2016";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_NONE_ENDTIME_BEFORE_TODAY = "edit 2 STarT none, END 22:00 05 APR 2016";
	private static final String EDIT_COMMAND_INVALID_STARTTIME_NOTNONE_ENDTIME_NONE = "EDIT 2 stART 11:00 30 Apr 2017, EnD NOne";
	private static final String EDIT_COMMAND_INVALID_ENDTIME = "EdIT 1 eND 21:00 45 MaR 2017";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_DUMMY = "EDit 1 ENd abCDEF";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_DATE = "eDIT 1 eND 23:59 AA MAy 2018";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_MONTH = "EDit 1 ENd 21:00 23 ABC 2019";
	private static final String EDIT_COMMAND_INVALID_ENDTIME_YEAR = "EDIT 1 eND 20:00 23 OCT ABCD";
	private static final String EDIT_COMMAND_INVALID_PRIORITY = "edit 1 priority abc";
	private static final String EDIT_COMMAND_INVALID_INDEX_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK ="Error: Time provided must be after the current time";
	private static final String EDIT_COMMAND_INVALID_START_AFTER_END_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	private static final String EDIT_COMMAND_ERROR_MESSAGE_PRIORITY_LEVEL_FEEDBACK = "Error: Priority level can be only high, medium, low or none.";
	private static final String EDIT_COMMAND_ERROR_MESSAGE_NO_END_TIME_FEEDBACK = "Error: Unable to allocate a start time when the task has no end time";
	private static final String EDIT_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK = "Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String EDIT_COMMAND_INVALID_NATTY_FEEDBACK = "";
	
	private static final String ADD_COMMAND_TYPE_VALID = "add";
	private static final String ADD_COMMAND_FLOATING_VALID = "add go home";
	private static final String ADD_COMMAND_FLOATING_VALID_CAP = "ADD go home";
	private static final String ADD_COMMAND_FLOATING_VALID_ALT = "+ go home";
	private static final String ADD_COMMAND_FLOATING_VALID_CAP_FIRSTCHAR = "Add go home";
	private static final String ADD_COMMAND_FLOATING_VALID_CAP_SECONDCHAR = "aDd go home";
	private static final String ADD_COMMAND_FLOATING_VALID_CAP_THIRDCHAR = "adD go home";
	private static final String ADD_COMMAND_FLOATING_VALID_COMPLEX = "add watch nba to 18:00 29 Apr 2016 by me from 19:00 29 Apr 2016"; 
	private static final String ADD_COMMAND_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_DEADLINE_VALID = "add finish homework by 22:00 20 Apr 2017";
	private static final String ADD_COMMAND_DEADLINE_VALID_NATTY = "add buy pens by next Monday 9pm";
	private static final String ADD_COMMAND_DEADLINE_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_EVENT_VALID = "add have a dinner from 19:00 28 Apr 2016 to 21:00 28 Apr 2016";
	private static final String ADD_COMMAND_EVENT_VALID_SPECIAL_STRING = "add walk *from two days from now to next Friday 9pm";
	private static final String ADD_COMMAND_EVENT_VALID_SPECIAL_STRING_2 = "add *camping from 11am tomorrow to 3pm tomorrow";
	private static final String ADD_COMMAND_EVENT_VALID_STDSTART_NATTYEND = "add watch movies from 20:00 30 APr 2016 to 2016-04-30 9pm";
	private static final String ADD_COMMAND_EVENT_VALID_STDEND_NATTYSTART = "add pary from next Tuesday 6pm to 21:00 25 Apr 2016";
	private static final String ADD_COMMAND_WITH_ONLY_STARTTIME_INVALID = "add walk from 14:00 10 May 2017";
	private static final String ADD_COMMAND_STARTTIME_BEFORE_CURRENTTIME ="add test from 13:00 12 Mar 2016 to 14:00 11 Apr 2016";
	private static final String ADD_COMMAND_DEADLINE_NO_DESCRIPTION = "add by 23:00 01 May 2016";
	private static final String ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME = "add swim from 21:00 22 Mar 2017 to 17:00 10 Mar 2016";
	private static final String ADD_COMMAND_EVENT_NO_DESCRIPTION = "add from 13:00 02 May 2016 to 16:00 02 May 2016";
	private static final String ADD_COMMAND_EVENT_FROM_AFTER_TO = "add go to nus to 12:00 04 May 2016 from 13:00 05 May 2016";
	private static final String ADD_COMMAND_EVENT_WRONG_TIME_FORMAT = "add testing from e:rr to b:ca";
	private static final String ADD_COMMAND_EVENT_INVALID_STANDARD_TIMEFORMAT = "add go fishing from 12:00 28 Apr 2016 to 13:00 29 Feb 2017";
	private static final String ADD_COMMAND_EVENT_INVALID_STANDARD_START_NATTY_END = "add dinner from 13:00 50 APr 2016 to 4pm next Wednesday";
	private static final String ADD_COMMAND_EVENT_INVALID_STANDARD_START_NATTY_END_2 = "add lunch from 13:00 28 Apr 2016 to ABCD";
	private static final String ADD_COMMAND_EVENT_INVALID_STANDARD_END_NATTY_START = "add talk from 5pm tomorrow to 12:00 40 May 2016";
	private static final String ADD_COMMAND_EVENT_INVALID_STANDARD_END_NATTY_START_2 = "add discuss from Dummy to 15:00 19 Apr 2017";
	private static final String ADD_COMMAND_DEADLINES_ENDTIME_BEFORE_CURRENT = "add demo by 14:00 23 Mar 2016";
	private static final String ADD_COMMAND_DEADLINES_INVALID_STANDARD_END = "add report by 23:59 60 Jun 2016";
	private static final String ADD_COMMAND_DEADLINES_INVALID_STANDARD_END_DATE = "add going by 23:00 AA May 2017"; 
	private static final String ADD_COMMAND_DEADLINES_INVALID_STANDARD_END_MONTH = "add hair cut by 21:00 23 BCA 2017";
	private static final String ADD_COMMAND_DEADLINES_WRONG_ENDTIME = "add study by ww:aa";
	private static final String ADD_COMMAND_NONE = "add ";
	private static final String ADD_COMMAND_EVENT_VALID_FEEDBACK = "";
	private static final String ADD_COMMAND_INVALID_FEEDBACK = "Error: Start date and time cannot be after the End date and time";
	private static final String ADD_COMMAND_INVALID_NATTY_FEEDBACK = "";
	private static final String ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK = "Error: Please ensure the fields are correct";
	private static final String ADD_COMMAND_ERROR_MESSAGE_DATE_BEFORE_CURRENT_FEEDBACK ="Error: Time provided must be after the current time";
	private static final String ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
     
	private static final String HELP_COMMAND_VALID = "help";
	private static final String HELP_COMMAND_ADD_VALID = "help add";
	private static final String HELP_COMMAND_ADD_VALID_ALT = "? add";
	private static final String HELP_COMMAND_REDO_VALID_ALT = "? rEdO";
	private static final String HELP_COMMAND_UNDONE_VALID_ALT = "? unDOnE";
	private static final String HELP_COMMAND_ADD_VALID_CAP = "HELP add";
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
	private static final String DELETE_COMMAND_VALID_ALT = "- 1";
	private static final String DELETE_COMMAND_VALID_ALT_2 = "del 1";
	private static final String DELETE_COMMAND_VALID_ALT_3 = "remove 1";
	private static final String DELETE_COMMAND_VALID_ALT_4 = "cancel 1";
	private static final String DELETE_COMMAND_VALID_CAP = "DELETE 1";
	private static final String DELETE_COMMAND_VALID_ALT_2_CAP = "DEL 1";
	private static final String DELETE_COMMAND_VALID_ALT_3_CAP = "REMOVE 1";
	private static final String DELETE_COMMAND_VALID_ALT_4_CAP = "CANCEL 1";
	private static final String DELETE_COMMAND_VALID_INDEX_FEEDBACK = "";
	private static final String DELETE_COMMAND_INVALID_DUMMY_STRING = "delete cba";
	private static final String DELETE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";

	private static final String LOCATION_COMMAND_INVALID = "location ";
	private static final String LOCATION_COMMAND_VALID = "location C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_VALID_ALT = "path C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_VALID_ALT_2 = "address C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_VALID_CAP = "LOCATION C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_VALID_ALT_CAP = "PATH C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_VALID_ALT_2_CAP = "ADDRESS C:\"Users\"Ishpal\"Desktop\"Task Data";
	private static final String LOCATION_COMMAND_INVALID_FEEDBACK = "Error: Location provided is invalid";
	private static final String LOCATION_COMMAND_VALID_FEEDBACK = "";
    private static final boolean LOCATION_COMMAND_ERROR = true;
    
	private static final String DONE_COMMAND_VALID = "done 1";
	private static final String DONE_COMMAND_VALID_ALT = "finish 1";
	private static final String DONE_COMMAND_VALID_ALT_2 = "complete 1";
	private static final String DONE_COMMAND_VALID_ALT_3 = "mark 1";
	private static final String DONE_COMMAND_VALID_CAP = "DONE 1";
	private static final String DONE_COMMAND_VALID_ALT_CAP = "FINISH 1";
	private static final String DONE_COMMAND_VALID_ALT_2_CAP = "COMPLETE 1";
	private static final String DONE_COMMAND_VALID_ALT_3_CAP = "MARK 1";
	private static final String DONE_COMMAND_VALID_FEEDBACK = "";
	private static final String DONE_COMMAND_INVALID_WRONG_KEYWORD = "done home";
	private static final String DONE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";
	private static final String DONE_COMMAND_VALID_INDEX = "1";
	
	private static final String VIEW_COMMAND_VALID = "view";
	private static final String VIEW_COMMAND_EVENT_VALID = "view events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT = "display events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT_2 = "show events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT_3 = "list events";
	private static final String VIEW_COMMAND_EVENT_VALID_CAP = "VIEW events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT_CAP = "DISPLAY events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT_2_CAP = "SHOW events";
	private static final String VIEW_COMMAND_EVENT_VALID_ALT_3_CAP = "LIST events";
	private static final String VIEW_COMMAND_DEADLINES_VALID = "view deadlines";
	private static final String VIEW_COMMAND_TASKS_VALID = "view tasks";
	private static final String VIEW_COMMAND_DONE_VALID = "view done";
	private static final String VIEW_COMMAND_OVERDUE_VALID = "view overdue";
	private static final String VIEW_COMMAND_VALID_FEEDBACK = "";
	private static final String VIEW_COMMAND_INVALID_WRONG_KEYWORD = "view abc";
	private static final String VIEW_COMMAND_INVALID_FEEDBACK = "Error: Please input a valid keyword. Use the \"help view\" command to see all the valid keywords";
    
	private static final String SEARCH_COMMAND_VALID_EMPTY = "SEARCH";
	private static final String SEARCH_COMMAND_VALID_FLEXICMD = "sEaRch this Sunday";
	private static final String SEARCH_COMMAND_VALID_STANDARDFMT = "searCH 22:00 01 May 2017";
	private static final String SEARCH_COMMAND_VALID_MONTH = "Search Apr";
	private static final String SEARCH_COMMAND_VALID_MONTH_AND_YEAR = "Search MAY 2017";
	private static final String SEARCH_COMMAND_VALID = "search dinner";
	private static final String SEARCH_COMMAND_VALID_ALT = "find dinner";
	private static final String SEARCH_COMMAND_VALID_CAP = "SEARCH dinner";
	private static final String SEARCH_COMMAND_VALID_ALT_CAP = "FIND dinner";
	private static final String SEARCH_COMMAND_VALID_KEYWORD = "dinner";
	private static final String SEARCH_COMMAND_INVALID_STANDARDFMT ="seARch 21:00 30 Feb 2016";
	private static final String SEARCH_COMMAND_INVALID_STANDARDFMT_2 = "SEarCh 20:00 Ab May 2016";
	private static final String SEARCH_COMMAND_INVALID_MONTH = "SeARCH 18:00 23 UUU 2017";
	private static final String SEARCH_COMMAND_INVALID_YEAR = "SEaRcH 18:00 23 MAY ABCD";
	private static final String SEARCH_COMMAND_VALID_FEEDBACK = "";
	private static final String SEARCH_COMMAND_INVALID_FEEDBACK ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String SEARCH_COMMAND_INVALID_FEEDBACK_TO_NATTY ="";
	
	private static final String UNDO_COMMAND_VALID = "undo";
	private static final String UNDO_COMMAND_VALID_FEEDBACK = "";
	
	private static final String REDO_COMMAND_VALID = "redo";
	private static final String REDO_COMMAND_VALID_FEEDBACK = "";
	
	private static final String UNDONE_COMMAND_VALID = "undone 1 2";
	private static final String UNDONE_COMMAND_VALID_ALT = "unMARK 1";
	private static final String UNDONE_COMMAND_VALID_FEEDBACK = "";
	private static final String UNDONE_COMMAND_INVALID_WRONG_KEYWORD = "done WOrk";
	private static final String UNDONE_COMMAND_INVALID_FEEDBACK = "Error: Index provided is not an Integer.";
	
	private static final String EXIT_COMMAND_VALID = "exit";
	private static final String EXIT_COMMAND_VALID_ALT ="quit";
	private static final String EXIT_COMMAND_VALID_ALT_2 ="logout";
	private static final String EXIT_COMMAND_VALID_CAP = "EXIT";
	private static final String EXIT_COMMAND_VALID_ALT_CAP ="QUIT";
	private static final String EXIT_COMMAND_VALID_ALT_2_CAP ="LOGOUT";
	private static final String EXIT_COMMAND_VALID_CAP_FIRSTCHAR = "Exit";
	private static final String EXIT_COMMAND_VALID_CAP_SECONDCHAR = "eXit";
	private static final String EXIT_COMMAND_VALID_CAP_THIRDCHAR = "exIt";
	private static final String EXIT_COMMAND_VALID_CAP_FOURTHCHAR = "exiT";
	private static final String EXIT_COMMAND_VALID_FEEDBACK = "";
	
	private static final String WRONG_COMMAND_TYPE = "redoing";
	private static final String WRONG_COMMAND_TYPE_FEEDBACK = "Error: Invalid command entered. Please enter \"help\" to view command format";
    
	private static Parser parser = new Parser();

	@Test//(expected = Exception.class)
	/*
	 * The following test uses the equivalence partitioning for heuristic
	 * testing. There are two equivalent partitions which are valid and invalid
	 * commands.
	 */
	public void testAddCommand() throws Exception {
		
		assertEquals(ADD_COMMAND_TYPE_VALID, parser.getHandler(ADD_COMMAND_FLOATING_VALID).getCommandType());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID_SPECIAL_STRING).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID_SPECIAL_STRING_2).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID_STDSTART_NATTYEND).getFeedBack());
		assertEquals(ADD_COMMAND_EVENT_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_VALID_STDEND_NATTYSTART).getFeedBack());
		assertEquals(ADD_COMMAND_DEADLINE_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINE_VALID).getFeedBack());
		assertEquals(ADD_COMMAND_DEADLINE_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINE_VALID_NATTY).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_CAP).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_ALT).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_CAP_FIRSTCHAR).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_CAP_SECONDCHAR).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_CAP_THIRDCHAR).getFeedBack());
		assertEquals(ADD_COMMAND_VALID_FEEDBACK, parser.getHandler(ADD_COMMAND_FLOATING_VALID_COMPLEX).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_WITH_ONLY_STARTTIME_INVALID).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FEEDBACK,
				parser.getHandler(ADD_COMMAND_STARTIME_BIGGER_THAN_ENDTIME).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_MESSAGE_DATE_BEFORE_CURRENT_FEEDBACK, parser.getHandler(ADD_COMMAND_STARTTIME_BEFORE_CURRENTTIME).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINE_NO_DESCRIPTION).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_NO_DESCRIPTION).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_FROM_AFTER_TO).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_MESSAGE_DATE_BEFORE_CURRENT_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINES_ENDTIME_BEFORE_CURRENT).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_WRONG_TIME_FORMAT).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_INVALID_STANDARD_TIMEFORMAT).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_INVALID_STANDARD_START_NATTY_END).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_INVALID_STANDARD_START_NATTY_END_2).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_INVALID_STANDARD_END_NATTY_START).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_EVENT_INVALID_STANDARD_END_NATTY_START_2).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINES_WRONG_ENDTIME).getFeedBack());
		assertEquals(ADD_COMMAND_ERROR_TIME_FORMAT_INVALID_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINES_INVALID_STANDARD_END).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_FIELDS_NOT_CORRECT_FEEDBACK, parser.getHandler(ADD_COMMAND_NONE).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_NATTY_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINES_INVALID_STANDARD_END_DATE).getFeedBack());
		assertEquals(ADD_COMMAND_INVALID_NATTY_FEEDBACK, parser.getHandler(ADD_COMMAND_DEADLINES_INVALID_STANDARD_END_MONTH).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testLocationCommand() throws Exception {
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID).getFeedBack());
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID_ALT_2).getFeedBack());
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID_CAP).getFeedBack());
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID_ALT_CAP).getFeedBack());
		assertEquals(LOCATION_COMMAND_VALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_VALID_ALT_2_CAP).getFeedBack());
		assertEquals(LOCATION_COMMAND_INVALID_FEEDBACK, parser.getHandler(LOCATION_COMMAND_INVALID).getFeedBack());
		assertEquals(LOCATION_COMMAND_ERROR, parser.getHandler(LOCATION_COMMAND_INVALID).getHasError());
	}
	
    @Test
	public void testEditCommand() throws Exception {
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_ALT).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_ALT_2).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_CAP).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_CAP_FIRSTCHAR).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_CAP_SECONDCHAR).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_CAP_THIRDCHAR).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_DESCRIPTION_CAP_FOURTHCHAR).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_STARTTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_STARTTIME_FLEX).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_STARTTIME_NONE).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_ENDTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_ENDTIME_FLEX).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_ENDTIME_NONE).getFeedBack());
		assertEquals(EDIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EDIT_COMMAND_VALID_PRIORITY).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_DUMMY).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_DUMMY).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_INDEX_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_WITHOUT_INDEX).getFeedBack());
		assertEquals(EDIT_COMMAND_ERROR_MESSAGE_PRIORITY_LEVEL_FEEDBACK ,parser.getHandler(EDIT_COMMAND_INVALID_PRIORITY).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_BEFORE_CURRENT).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_BEFORE_CURRENT).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_TWO_KEYWORDS).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_START_AFTER_END_FEEDBACK ,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_AFTER_ENDTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_START_AFTER_END_FEEDBACK ,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_EQUAL_ENDTIME).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_DATE_BEFORE_CURRENT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_NONE_ENDTIME_BEFORE_TODAY).getFeedBack());
		assertEquals(EDIT_COMMAND_ERROR_MESSAGE_NO_END_TIME_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_STARTTIME_NOTNONE_ENDTIME_NONE).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_NATTY_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_DATE).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_NATTY_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_MONTH).getFeedBack());
		assertEquals(EDIT_COMMAND_INVALID_TIMEFORMAT_FEEDBACK,parser.getHandler(EDIT_COMMAND_INVALID_ENDTIME_YEAR).getFeedBack());
	}
	
    @Test//(expected = Exception.class)
	public void testHelpCommand() throws Exception {
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_ADD_VALID).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_ADD_VALID_ALT).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_REDO_VALID_ALT).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_UNDONE_VALID_ALT).getFeedBack());
		assertEquals(HELP_COMMAND_VALID_FEEDBACK, parser.getHandler(HELP_COMMAND_ADD_VALID_CAP).getFeedBack());
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
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_2).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_3).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_4).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_CAP).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_2_CAP).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_3_CAP).getFeedBack());
		assertEquals(DELETE_COMMAND_VALID_INDEX_FEEDBACK, parser.getHandler(DELETE_COMMAND_VALID_ALT_4_CAP).getFeedBack());
		assertEquals(DELETE_COMMAND_INVALID_FEEDBACK,parser.getHandler(DELETE_COMMAND_INVALID_DUMMY_STRING).getFeedBack());
	}

	@Test//(expected = Exception.class)
	public void testDoneOrUnDoneCommand() throws Exception {
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT_2).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT_3).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_CAP).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT_CAP).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT_2_CAP).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_FEEDBACK, parser.getHandler(DONE_COMMAND_VALID_ALT_3_CAP).getFeedBack());
		assertEquals(DONE_COMMAND_VALID_INDEX, parser.getHandler(DONE_COMMAND_VALID).getIndexList().get(0).toString());
		assertEquals(DONE_COMMAND_INVALID_FEEDBACK,parser.getHandler(DONE_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
	}
	
	@Test
	public void testViewCommand() throws Exception {
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK, parser.getHandler(VIEW_COMMAND_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT_2).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT_3).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_CAP).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT_CAP).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT_2_CAP).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_EVENT_VALID_ALT_3_CAP).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_DEADLINES_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_TASKS_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_DONE_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_VALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_OVERDUE_VALID).getFeedBack());
		assertEquals(VIEW_COMMAND_INVALID_FEEDBACK,parser.getHandler(VIEW_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());	
	}
	
	@Test
	public void testSearchCommand() throws Exception {
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_EMPTY).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_CAP).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_ALT_CAP).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_KEYWORD, parser.getHandler(SEARCH_COMMAND_VALID).getKeyWord());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_MONTH).getFeedBack());
		assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_MONTH_AND_YEAR).getFeedBack());
        assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_FLEXICMD).getFeedBack());	
        assertEquals(SEARCH_COMMAND_VALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_VALID_STANDARDFMT).getFeedBack());	
        assertEquals(SEARCH_COMMAND_INVALID_FEEDBACK, parser.getHandler(SEARCH_COMMAND_INVALID_STANDARDFMT).getFeedBack());	
        assertEquals(SEARCH_COMMAND_INVALID_FEEDBACK_TO_NATTY, parser.getHandler(SEARCH_COMMAND_INVALID_STANDARDFMT_2).getFeedBack());
        assertEquals(SEARCH_COMMAND_INVALID_FEEDBACK_TO_NATTY, parser.getHandler(SEARCH_COMMAND_INVALID_MONTH).getFeedBack());	
        assertEquals(SEARCH_COMMAND_INVALID_FEEDBACK_TO_NATTY, parser.getHandler(SEARCH_COMMAND_INVALID_YEAR).getFeedBack());	
	}
	
	@Test
	public void testUndoCommand() throws Exception {
		assertEquals(UNDO_COMMAND_VALID_FEEDBACK, parser.getHandler(UNDO_COMMAND_VALID).getFeedBack());
	}
	
	@Test
	public void testExitCommand() throws Exception {
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_ALT_2).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_CAP).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_ALT_CAP).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_ALT_2_CAP).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_CAP_FIRSTCHAR).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_CAP_SECONDCHAR).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_CAP_THIRDCHAR).getFeedBack());
		assertEquals(EXIT_COMMAND_VALID_FEEDBACK, parser.getHandler(EXIT_COMMAND_VALID_CAP_FOURTHCHAR).getFeedBack());
	}
	
	@Test
	public void testInvalidCommand() throws Exception {
		assertEquals(WRONG_COMMAND_TYPE_FEEDBACK, parser.getHandler(WRONG_COMMAND_TYPE).getFeedBack());
	}
	@Test
	public void testRedoCommand() throws Exception {
		assertEquals(REDO_COMMAND_VALID_FEEDBACK, parser.getHandler(REDO_COMMAND_VALID).getFeedBack());
	}
	
	@Test
	public void testUndoneCommand() throws Exception {
		assertEquals(UNDONE_COMMAND_VALID_FEEDBACK , parser.getHandler(UNDONE_COMMAND_VALID).getFeedBack());
		assertEquals(UNDONE_COMMAND_VALID_FEEDBACK , parser.getHandler(UNDONE_COMMAND_VALID_ALT).getFeedBack());
		assertEquals(UNDONE_COMMAND_INVALID_FEEDBACK , parser.getHandler(UNDONE_COMMAND_INVALID_WRONG_KEYWORD).getFeedBack());
		
	}

}
