package simplyamazing.parser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
	private static Logger logger = Logger.getLogger("Parser");
	
	
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_SET_LOCATION = "location";
	private static final String COMMAND_MARK_AS_DONE = "done";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_INVALID = "Error: Invalid command entered. Please enter \"help\" to view command format";
	
	private static final String STRING_EMPTY = "";
	private static final String CHARACTER_SPACE = "\\s";
	
	private String firstWord = STRING_EMPTY;
	private String taskIndex = STRING_EMPTY;
	private String taskInfoWithoutIndex= STRING_EMPTY;
	private String removeFirstWord = STRING_EMPTY;

	public Parser() {
		
	}

	public static String removeFirstWord(String userCommand) {
		return userCommand.replaceFirst(getFirstWord(userCommand), STRING_EMPTY).trim();
	}
	
	public static String getFirstWord(String userCommand) {
		return userCommand.trim().split(CHARACTER_SPACE)[0];
	}	
    
	private Handler parserAddCommand(Handler handler, String taskInfo) throws Exception {
	    ParserAdd parserAdd = new ParserAdd();
	    return parserAdd.parseAddCommand(handler,taskInfo);
	    
	}	
	private Handler parserEditCommand(Handler handler, String taskInfo) throws Exception {
		ParserEdit parserEdit = new ParserEdit();
		//assert parserEdit !=  null;					//assert
		taskIndex =  getFirstWord(taskInfo);
		taskInfoWithoutIndex = removeFirstWord(taskInfo);
		
		return parserEdit.parseEditCommand(handler, taskIndex, taskInfoWithoutIndex);
	}
	private Handler parserDeleteCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserDelete parserDelete = new ParserDelete();
		//assert parserDelete != null;					// assert
		return parserDelete.parserDeleteCommand(handler,removeFirstWord);
	}
	private Handler parserViewCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserView parserView = new ParserView();
		//assert parserView != null;
		return parserView.parserViewCommand(handler,removeFirstWord);
	}
	private Handler parserLocationCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserLocation parseLocation = new ParserLocation();
		//assert parseLocation != null;
		return parseLocation.parseLocationCmd(handler,removeFirstWord);
	}
	private Handler parserDoneCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserDone parserDone = new ParserDone();
		//assert parserDone != null;
		return parserDone.parserDoneCommand(handler,removeFirstWord);
	}
	private Handler parserHelpCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserHelp parserHelp = new ParserHelp();
		return parserHelp.parserHelpCommand(handler,removeFirstWord);
	}
	
	
	private Handler parserFirstWord(Handler handler, String firstWord,String removeFirstWord) throws Exception{
		switch(firstWord.toLowerCase()){
		 case COMMAND_ADD:
			 handler.setCommandType(COMMAND_ADD);
			 handler = parserAddCommand(handler, removeFirstWord);
			 break;
		 case COMMAND_DELETE:
			 handler.setCommandType(COMMAND_DELETE);
			 handler = parserDeleteCommand(handler, removeFirstWord);
			 break;
		 case COMMAND_EDIT:
			 handler.setCommandType(COMMAND_EDIT);
			 handler = parserEditCommand(handler, removeFirstWord);
			 break;
		 case COMMAND_VIEW:
			 handler.setCommandType(COMMAND_VIEW);
			 handler = parserViewCommand(handler, removeFirstWord);
			 break;
		 case COMMAND_SEARCH:
			 handler.setCommandType(COMMAND_SEARCH);
			 handler.setKeyWord(removeFirstWord);
			 break;
		 case COMMAND_HELP:
			 handler.setCommandType(COMMAND_HELP);
			 handler = parserHelpCommand(handler,removeFirstWord);
			 break;
		 case COMMAND_UNDO:
			 handler.setCommandType(COMMAND_UNDO);
			 break;
		 case COMMAND_SET_LOCATION:
			 handler.setCommandType(COMMAND_SET_LOCATION);
			 handler = parserLocationCommand(handler, removeFirstWord);
			 break;
		 case COMMAND_MARK_AS_DONE:
			 handler.setCommandType(COMMAND_MARK_AS_DONE);
			 handler = parserDoneCommand(handler, removeFirstWord);
		     break;
		 case COMMAND_EXIT:
			 handler.setCommandType(COMMAND_EXIT);
		     break;
		 default:
			 logger.log(Level.WARNING, "invalid command");
			 handler.setHasError(true);
			 handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}
	
	public Handler getHandler(String input) throws Exception{
		//logger.log(Level.INFO, "before starting on getHandler");
		Handler handler = new Handler();
		firstWord = getFirstWord(input);
		removeFirstWord = removeFirstWord(input);
		handler = parserFirstWord(handler,firstWord,removeFirstWord);
		//logger.log(Level.INFO, "about to return to storage");
		return handler;
	}
}
