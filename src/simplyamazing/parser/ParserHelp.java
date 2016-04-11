//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserHelp {
	private final String STRING_ADD = "add";
	private final String STRING_DELETE = "delete";
	private final String STRING_VIEW = "view";
	private final String STRING_UNDO = "undo";
	private final String STRING_REDO = "redo";
	private final String STRING_MARK = "mark";
	private final String STRING_UNMARK = "unmark";
	private final String STRING_EDIT = "edit";
	private final String STRING_SEARCH = "search";
	private final String STRING_LOCATION = "location";
	private final String STRING_EXIT = "exit";
	private final String STRING_EMPTY="";
	
	private final String COMMAND_INVALID = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";

	public Handler parserHelpCommand(Handler handler, String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO,"start to parse the help command");
		switch (taskInfo.toLowerCase()) {
		case STRING_ADD:
			handler.setKeyWord(STRING_ADD);
			break;
		case STRING_DELETE:
			handler.setKeyWord(STRING_DELETE);
			break;
		case STRING_VIEW:
			handler.setKeyWord(STRING_VIEW);
			break;
		case STRING_UNDO:
			handler.setKeyWord(STRING_UNDO);
			break;
		case STRING_REDO:
			handler.setKeyWord(STRING_REDO);
			break;
		case STRING_MARK:
			handler.setKeyWord(STRING_MARK);
			break;
		case STRING_UNMARK:
			handler.setKeyWord(STRING_UNMARK);
			break;
		case STRING_EDIT:
			handler.setKeyWord(STRING_EDIT);
			break;
		case STRING_LOCATION :
			handler.setKeyWord(STRING_LOCATION);
			break;
		case STRING_EXIT:
			handler.setKeyWord(STRING_EXIT);
			break;
		case STRING_SEARCH:
			handler.setKeyWord(STRING_SEARCH);
			break;
		case STRING_EMPTY:
			handler.setKeyWord(STRING_EMPTY);
			break;
		default:
			logger.log(Level.WARNING, "the entered command is invalid");
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}
}
