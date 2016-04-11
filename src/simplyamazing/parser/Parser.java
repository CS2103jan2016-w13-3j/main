//@@author A0112659A
package simplyamazing.parser;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class Parser {
	private static Logger logger;
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_ADD_ALT = "+";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_DELETE_ALT = "del";
	private static final String COMMAND_DELETE_ALT_2 = "-";
	private static final String COMMAND_DELETE_ALT_3 = "remove";
	private static final String COMMAND_DELETE_ALT_4 = "cancel";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_EDIT_ALT = "change";
	private static final String COMMAND_EDIT_ALT_2 = "update";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_VIEW_ALT = "display";
	private static final String COMMAND_VIEW_ALT_2 = "show";
	private static final String COMMAND_VIEW_ALT_3 = "list";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_SEARCH_ALT = "find";
	private static final String COMMAND_SEARCH_ALT_2 = "get";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_HELP_ALT = "?";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_REDO = "redo";
	private static final String COMMAND_SET_LOCATION = "location";
	private static final String COMMAND_SET_LOCATION_ALT = "path";
	private static final String COMMAND_SET_LOCATION_ALT_2 = "address";
	private static final String COMMAND_MARK_AS_DONE = "done";
	private static final String COMMAND_MARK_AS_DONE_ALT = "mark";
	private static final String COMMAND_MARK_AS_DONE_ALT_2 = "finish";
	private static final String COMMAND_MARK_AS_DONE_ALT_3 = "complete";
	private static final String COMMAND_UNMARK = "undone";
	private static final String COMMAND_UNMARK_ALT = "unmark";
	private static final String COMMAND_EXIT = "exit";
	private static final String COMMAND_EXIT_ALT = "quit";
	private static final String COMMAND_EXIT_ALT_2 = "logout";
	private static final String COMMAND_INVALID = "Error: Invalid command entered. Please enter \"help\" to view command format";

	private static final String STRING_EMPTY = "";
	private static final String CHARACTER_SPACE = "\\s";

	private String firstWord = STRING_EMPTY;
	private String taskIndex = STRING_EMPTY;
	private String taskInfoWithoutIndex = STRING_EMPTY;
	private String removeFirstWord = STRING_EMPTY;

	public Parser() {
		logger = Logger.getLogger("simplyamazing");

		try {
			FileHandler fh = new FileHandler("C:\\Users\\Public\\SimplyAmazing\\logFile.txt", true);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (Exception e) {
			logger.log(Level.WARNING, "the address fro file handler is invalid");
		}
		;
	}

	public static String removeFirstWord(String userCommand) {
		final String replace = Pattern.quote(getFirstWord(userCommand));
		return userCommand.replaceFirst(replace, STRING_EMPTY).trim();
	}

	public static String getFirstWord(String userCommand) {
		return userCommand.trim().split(CHARACTER_SPACE)[0];
	}

	private Handler parserAddCommand(Handler handler, String taskInfo) throws Exception {
		ParserAdd parserAdd = new ParserAdd();
		return parserAdd.parseAddCommand(handler, taskInfo, logger);

	}

	private Handler parserEditCommand(Handler handler, String taskInfo) throws Exception {
		ParserEdit parserEdit = new ParserEdit();
		taskIndex = getFirstWord(taskInfo);
		taskInfoWithoutIndex = removeFirstWord(taskInfo);

		return parserEdit.parseEditCommand(handler, taskIndex, taskInfoWithoutIndex, logger);
	}

	private Handler parserDeleteCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserDelete parserDelete = new ParserDelete();
		return parserDelete.parserDeleteCommand(handler, removeFirstWord, logger);
	}

	private Handler parserViewCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserView parserView = new ParserView();
		return parserView.parserViewCommand(handler, removeFirstWord, logger);
	}

	private Handler parserLocationCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserLocation parseLocation = new ParserLocation();
		return parseLocation.parseLocationCmd(handler, removeFirstWord, logger);
	}

	private Handler parserDoneOrUndoneCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserDoneOrUndone parserDoneOrUndone = new ParserDoneOrUndone();
		return parserDoneOrUndone.parserDoneOrUndoneCommand(handler, removeFirstWord, logger);
	}

	private Handler parserHelpCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserHelp parserHelp = new ParserHelp();
		return parserHelp.parserHelpCommand(handler, removeFirstWord, logger);
	}

	private Handler parserSearchCommand(Handler handler, String removeFirstWord) throws Exception {
		ParserSearch parserSearch = new ParserSearch();
		return parserSearch.parserSearchCommand(handler, removeFirstWord, logger);
	}

	private Handler parserFirstWord(Handler handler, String firstWord, String removeFirstWord) throws Exception {
		logger.log(Level.INFO, "start to process the First word");
		switch (firstWord.toLowerCase()) {
		case COMMAND_ADD :
		case COMMAND_ADD_ALT :
			handler.setCommandType(COMMAND_ADD);
			handler = parserAddCommand(handler, removeFirstWord);
			break;
		case COMMAND_DELETE :
		case COMMAND_DELETE_ALT :
		case COMMAND_DELETE_ALT_2 :
		case COMMAND_DELETE_ALT_3 :
		case COMMAND_DELETE_ALT_4 :
			handler.setCommandType(COMMAND_DELETE);
			handler = parserDeleteCommand(handler, removeFirstWord);
			break;
		case COMMAND_EDIT :
		case COMMAND_EDIT_ALT :
		case COMMAND_EDIT_ALT_2 :
			handler.setCommandType(COMMAND_EDIT);
			handler = parserEditCommand(handler, removeFirstWord);
			break;
		case COMMAND_VIEW :
		case COMMAND_VIEW_ALT :
		case COMMAND_VIEW_ALT_2 :
		case COMMAND_VIEW_ALT_3 :
			handler.setCommandType(COMMAND_VIEW);
			handler = parserViewCommand(handler, removeFirstWord);
			break;
		case COMMAND_SEARCH :
		case COMMAND_SEARCH_ALT :
		case COMMAND_SEARCH_ALT_2 :
			handler.setCommandType(COMMAND_SEARCH);
			handler = parserSearchCommand(handler, removeFirstWord);
			break;
		case COMMAND_HELP :
		case COMMAND_HELP_ALT :
			handler.setCommandType(COMMAND_HELP);
			handler = parserHelpCommand(handler, removeFirstWord);
			break;
		case COMMAND_UNDO :
			handler.setCommandType(COMMAND_UNDO);
			break;
		case COMMAND_REDO :
			handler.setCommandType(COMMAND_REDO);
			break;
		case COMMAND_SET_LOCATION :
		case COMMAND_SET_LOCATION_ALT :
		case COMMAND_SET_LOCATION_ALT_2 :
			handler.setCommandType(COMMAND_SET_LOCATION);
			handler = parserLocationCommand(handler, removeFirstWord);
			break;
		case COMMAND_MARK_AS_DONE :
		case COMMAND_MARK_AS_DONE_ALT :
		case COMMAND_MARK_AS_DONE_ALT_2 :
		case COMMAND_MARK_AS_DONE_ALT_3 :
			handler.setCommandType(COMMAND_MARK_AS_DONE);
			handler = parserDoneOrUndoneCommand(handler, removeFirstWord);
			break;
		case COMMAND_UNMARK :
		case COMMAND_UNMARK_ALT :
			handler.setCommandType(COMMAND_UNMARK);
			handler = parserDoneOrUndoneCommand(handler, removeFirstWord);
			break;
		case COMMAND_EXIT :
		case COMMAND_EXIT_ALT :
		case COMMAND_EXIT_ALT_2 :
			handler.setCommandType(COMMAND_EXIT);
			break;
		default :
			logger.log(Level.WARNING, "the entered command type is invalid");
			handler.setHasError(true);
			handler.setFeedBack(COMMAND_INVALID);
		}
		return handler;
	}

	public Handler getHandler(String input) throws Exception {
		logger.log(Level.INFO, "before starting on getHandler");
		Handler handler = new Handler();
		firstWord = getFirstWord(input);
		removeFirstWord = removeFirstWord(input);
		handler = parserFirstWord(handler, firstWord, removeFirstWord);
		logger.log(Level.INFO, "ready to return the handler");
		return handler;
	}
}
