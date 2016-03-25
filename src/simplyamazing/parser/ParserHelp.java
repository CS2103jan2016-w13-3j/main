package simplyamazing.parser;

public class ParserHelp {
	private final String STRING_ADD = "add";
	private final String STRING_DELETE = "delete";
	private final String STRING_VIEW = "view";
	private final String STRING_UNDO = "undo";
	private final String STRING_DONE = "done";
	private final String STRING_EDIT = "edit";
	private final String STRING_SEARCH = "search";
	private final String STRING_LOCATION = "location";
	private final String STRING_EXIT = "exit";
	private final String STRING_EMPTY="";
	
	private final String COMMAND_INVALID = "Error: Please input a valid keyword. Use the \"help\" command to view all valid keywords";

	public Handler parserHelpCommand(Handler handler, String taskInfo) throws Exception {
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
		case STRING_DONE:
			handler.setKeyWord(STRING_DONE);
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
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}
}
