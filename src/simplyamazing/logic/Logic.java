package simplyamazing.logic;

import java.util.ArrayList;

import simplyamazing.data.Task;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class Logic {
	
	private static Parser parser;
	private static Storage storage;
	private static ArrayList<Task> list;
	private static String previousCommand;
	private static Handler commandHandler;
	private static final String STRING_EMPTY = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	private static final String ERROR_INVALID_INDEX = "Error: Invalid index entered";
	private static final String ERROR_INVALID_KEYWORD = "Error: Keyword given is invalid";
	private static final String ERROR_INVALID_FIELD_VALUES = "Error: Field Values given are invalid";
	
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
		commandHandler = new commandHandler();
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
		commandHandler = parser.getHandler(userCommand);
		String commandWord = commandHandler.getCommandType();
		CommandType commandType = getCommandType(commandWord);
		
		if (commandType != CommandType.SET_LOCATION) {
			if (!storage.isLocationSet()) {
				throw new Exception(MESSAGE_INPUT_LOCATION);
			}
		}
		
		String feedback = STRING_EMPTY;
		switch (commandType) {
			case ADD_TASK :
				feedback = executeAddCommand(commandHandler);
				break;
			case DELETE_TASK :
				feedback = executeDeleteCommand(commandHandler);
				break;
			case VIEW_LIST :
				feedback = executeViewCommand(commandHandler);
				break;
			case EDIT_TASK :
				feedback = executeEditCommand(commandHandler);
				break;
			case SEARCH_KEYWORD :
				feedback = executeSearchCommand(commandHandler);
				break;
			case UNDO_LAST :
				feedback = executeUndoCommand(commandHandler);
				break;
			case SET_LOCATION :
				feedback = executeSetLocationCommand(commandHandler);
				break;
			case MARK_TASK :
				feedback = executeMarkCommand(commandHandler);
				break;
			case HELP :
				feedback = executeHelpCommand(commandHandler);
				break;
			default:
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
		
		previousCommand = commandWord;
		return feedback;
	}
	
	private static String executeAddCommand(Handler commandHandler) throws Exception {
		if (commandHandler.hasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			Task taskToAdd = commandHandler.getTask();
			return storage.addTask(taskToAdd);
		}
	}
	
	private static String executeViewCommand(Handler commandHandler) throws Exception {
		if (commandHandler.hasError() == true) {
			throw new Exception(commandHandler.getFeedback());
		} else {
			String keyWord = commandHandler.getKeyword();
			list = storage.load(keyWord);
			return convertListToString(list);	
		}
	}
	
	private static String executeEditCommand(String userCommand) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		editHandler.setList(list);
		String commandContent = parser.removeFirstWord(userCommand);
		editHandler.setIndex(commandContent, parser);
		
		if (editHandler.checkIndexValid() == false) {
			return ERROR_INVALID_INDEX;
		} else{
			editHandler.setFieldValues(commandContent, parser);
			
			if (editHandler.checkFieldValid(parser) == false) {
				return ERROR_INVALID_FIELD_VALUES;
			} else{
				return editHandler.editTask(parser, storage);
			}
		}
	}
	
	private static String executeDeleteCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		if(commandHandler.hasError == true) {
			throw new Exception(commandHandler.getFeedback());
		} else {
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex());
			
			if (isIndexValid == false) {
				throw new Exception(ERROR_INVALID_INDEX);
			} else {
				int index = Integer.parseInt(commandHandler.getIndex());
				Task taskToDelete = list.get(index);
				return storage.deleteTask(taskToDelete);
			}
		}
	}

	private static String executeSearchCommand(Handler commandHandler) throws Exception {
		if(commandHandler.hasError == true) {
			throw new Exception(commandHandler.getFeedback());
		} else{
			String keyword = commandHandler.getKeyword();
			list = storage.searchTasks(keyword);
			String listInStringFormat = convertListToString(list);
			return listInStringFormat;
		}
	}
	
	private static String executeUndoCommand(Handler commandHandler) throws Exception {
		if(commandHandler.hasError == true) {
			throw new Exception(commandHandler.getFeedback());
		} else {
			return storage.restore();
		}
	}
	
	private static String executeSetLocationCommand(Handler commandHandler) throws Exception {
		if(commandHandler.hasError == true) {
			throw new Exception(commandHandler.getFeedback());
		} else {
			String directoryPath = commandHandler.getKeyword();
			return storage.setLocation(directoryPath);
		}
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
	
	private static boolean checkIndexValid(String indexStr){
		if (indexStr == null) {
			return false;
		} else {
			int indexToDelete = Integer.parseInt(indexStr);
			if(indexToDelete <= 0 || indexToDelete>list.size()){
				return false;
			} else {
				return true;
			}
		}
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