//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserView {
	private final String STRING_EVENT = "events";
	private final String STRING_DEADLINE = "deadlines";
	private final String STRING_EMPTY = "";
	private final String STRING_DONE = "done";
	private final String STRING_TASKS = "tasks";
	private final String STRING_OVERDUE = "overdue";
	private final String COMMAND_INVALID = "Error: Please input a valid keyword. Use the \"help view\" command to see all the valid keywords";

	public Handler parserViewCommand(Handler handler, String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO, "start to parse the search command");
		switch (taskInfo.toLowerCase()) {
		case STRING_EVENT :
			handler.setKeyWord(STRING_EVENT);
			break;
		case STRING_DEADLINE :
			handler.setKeyWord(STRING_DEADLINE);
			break;
		case STRING_EMPTY :
			handler.setKeyWord(STRING_EMPTY);
			break;
		case STRING_TASKS :
			handler.setKeyWord(STRING_TASKS);
			break;
		case STRING_OVERDUE :
			handler.setKeyWord(STRING_OVERDUE);
			break;
		case STRING_DONE :
			handler.setKeyWord(STRING_DONE);
			break;
		default :
			logger.log(Level.WARNING, "the entered command is invalid");
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}
}
