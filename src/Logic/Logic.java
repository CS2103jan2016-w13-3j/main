package Logic;

import Parser.Parser;
import Data.Task;
import Storage.Storage;
import java.util.ArrayList;

public class Logic {
	
	private static Parser parser;
	private static Storage storage;
	private static ArrayList<Task> list;
	private static AddHandler addHandler;
	private static String previousCommand;
	
	private static final String STRING_EMPTY = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command entered. Please enter \"help\" to view command format";
	private static final String MESSAGE_INPUT_LOCATION = "Directory location not set, please input directory location before running the program";
	private static final String MESSAGE_NO_TASK_FOUND = "No task found.";
	
	enum CommandType {
		ADD_TASK, VIEW_LIST, DELETE_TASK,INVALID,
		SEARCH_KEYWORD, UNDO_LAST, EDIT_TASK, SET_LOCATION,
		MARK_TASK, HELP, 
		;
	};
	
	public Logic(){
		parser = new Parser();
		storage = new Storage();
		list = new ArrayList<Task>();
		addHandler = new AddHandler();
		previousCommand = "";
	}
	
	private static CommandType getCommandType(String commandWord) {
		if (commandWord.equalsIgnoreCase("add")) {
			return CommandType.ADD_TASK;
		} else if (commandWord.equalsIgnoreCase("delete")) {
			return CommandType.DELETE_TASK;
		} else if (commandWord.equalsIgnoreCase("undo")) {
			return CommandType.UNDO_LAST;
		} else if (commandWord.equalsIgnoreCase("view")) {
			return CommandType.VIEW_LIST;
		} else if (commandWord.equalsIgnoreCase("done")) {
		 	return CommandType.MARK_TASK;
		} else if (commandWord.equalsIgnoreCase("location")) {
			return CommandType.SET_LOCATION;
		} else if (commandWord.equalsIgnoreCase("search")) {
			return CommandType.SEARCH_KEYWORD;
		} else if (commandWord.equalsIgnoreCase("edit")) {
			return CommandType.EDIT_TASK;
		} else if(commandWord.equalsIgnoreCase("help")) {
			return CommandType.HELP;
		} else {
			return CommandType.INVALID;
		}
	}
	
	public String executeCommand(String userCommand) throws Exception {
		String commandWord = parser.getFirstWord(userCommand);
		CommandType commandType = getCommandType(commandWord);
		if(commandType != CommandType.SET_LOCATION) {
			if (!storage.isLocationSet()) {
				throw new Exception(MESSAGE_INPUT_LOCATION);
			}
		}
		String feedback = STRING_EMPTY;
		switch (commandType) {
			case ADD_TASK :
				feedback = executeAddCommand(userCommand);
				break;
			case DELETE_TASK :
				feedback = executeDeleteCommand(userCommand);
				break;
			case VIEW_LIST :
				feedback = executeViewCommand(userCommand);
				break;
			case EDIT_TASK :
				feedback = executeEditCommand(userCommand);
				break;
			case SEARCH_KEYWORD :
				feedback = executeSearchCommand(userCommand);
				break;
			case UNDO_LAST :
				feedback = executeUndoCommand(userCommand);
				break;
			case SET_LOCATION :
				feedback = executeSetLocationCommand(userCommand);
				break;
			case MARK_TASK :
				feedback = executeMarkCommand(userCommand);
				break;
			case HELP :
				feedback = executeHelpCommand(userCommand);
				break;
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
		
		previousCommand = commandWord;
		return feedback;
	}
	
	private static String executeAddCommand(String userCommand) throws Exception {
		String taskContent = parser.removeFirstWord(userCommand);
		addHandler.setContent(taskContent);
		
		if(addHandler.checkContentValid() == false) {
			return MESSAGE_INVALID_COMMAND;
		} else {
			return addHandler.addTask();
		}
	}
	
	private static String executeViewCommand(String userCommand) throws Exception {
		String keyWord = parser.removeFirstWord(userCommand);
		list = storage.load(keyWord);
		return convertListToString(list);
	}
	
	private static String executeEditCommand(String userCommand) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		String userCommandWithoutCommandType = parser.removeFirstWord(userCommand);
		int index = Integer.parseInt(parser.getFirstWord(userCommandWithoutCommandType));
		String fieldValues = parser.removeFirstWord(userCommandWithoutCommandType);
		Task updatedContent = parser.parseEditCommand(fieldValues);
		Task originalTask = list.get(index - 1);
		return storage.editTask(originalTask, updatedContent);
	}
	
	private static String executeDeleteCommand(String userCommand) throws Exception {
		
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		String userCommandWithoutCommandType = parser.removeFirstWord(userCommand);
		int indexToDelete = Integer.parseInt(userCommandWithoutCommandType);
		Task taskToDelete = list.get(indexToDelete - 1);
		list.remove(indexToDelete - 1);
		return storage.deleteTask(taskToDelete);
	}

	private static String executeSearchCommand(String userCommand) throws Exception {
		String keyword = parser.removeFirstWord(userCommand);
		list = storage.searchTasks(keyword);
		String listInStringFormat = convertListToString(list);
		return listInStringFormat;
	}
	
	private static String executeUndoCommand(String userCommand) throws Exception {
		return storage.restore();
	}
	
	private static String executeSetLocationCommand(String userCommand) throws Exception {
		String directoryPath = parser.removeFirstWord(userCommand);
		return storage.setLocation(directoryPath);
	}
	
	private static String executeMarkCommand(String userCommand) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		String userCommandWithoutCommandType = parser.removeFirstWord(userCommand);
		int indexToMark = Integer.parseInt(userCommandWithoutCommandType);
		Task taskToMark = list.get(indexToMark -1);
		return storage.markTaskDone(taskToMark);
	}
	
	private static String executeHelpCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void displayFeedback(String feedback){
		System.out.println(feedback);
	}
	
	private static boolean isListShown() {
		if (previousCommand.toLowerCase().equals("search") || previousCommand.toLowerCase().equals("view")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String convertListToString(ArrayList<Task> list){
		if (list.size() == 0) {
			return MESSAGE_NO_TASK_FOUND;
		}
		String convertedList = STRING_EMPTY;
		for (int i = 0; i < list.size(); i++) {
			Task taskToPrint = list.get(i);
			convertedList += (i+1) + ". " + taskToPrint.toFilteredString() + "\n";
		}
		return convertedList;
	}
}