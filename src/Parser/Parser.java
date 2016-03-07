package Parser;
import Data.Task;

public class Parser {

	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_SET_LOCATION = "location";
	private static final String COMMAND_MARK_AS_DONE = "done";
	private static final String COMMAND_INVALID = "the command is invalid";
	
	private static final String STRING_EMPTY = "";
	private static final String CHARACTER_FIELD_VALUE_SEPARATOR = ":";
	private static final String CHARACTER_SPACE = "\\s";
	
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String KEYWORD_PRIORITY = "priority";
	
	
	private static final String[] KEYWORDS = {KEYWORD_PRIORITY,KEYWORD_DEADLINE, KEYWORD_SCHEDULE_FROM, KEYWORD_SCHEDULE_TO};
	

	public Parser() {
		
	}

	public String removeFirstWord(String userCommand) {
		return userCommand.replaceFirst(getFirstWord(userCommand), STRING_EMPTY).trim();
	}
	
	public String getFirstWord(String userCommand) {
		return userCommand.trim().split(CHARACTER_SPACE)[0];
	}
    
	public Task parseAddCommand(String taskInfo) throws Exception {
	    ParserAdd parserAdd = new ParserAdd();
	    return parserAdd.parseAddCommand(taskInfo);
	    
	}	
	public Task parseEditCommand(String taskInfo) throws Exception {
		ParserEdit parserEdit = new ParserEdit();
		Parser parser = new Parser();
	    return parserEdit.parserEditCommand(parser,taskInfo);
	}
	public boolean parserCheckAddCommand(String taskInfo) throws Exception{
		ParserCheckAdd parserAdd = new ParserCheckAdd();
		return parserAdd.isAddingValid(taskInfo);
	}
	public boolean parserCheckEditCommand(String taskInfo) throws Exception{
		ParserCheckEdit parserEdit = new ParserCheckEdit();
		return parserEdit.isEditingValid(taskInfo);
	}
}
