package simplyamazing.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import simplyamazing.data.Task;
import simplyamazing.parser.Handler;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class Logic {
	
	private static Logger logger = Logger.getLogger("Logic"); 
	
	
	private static Parser parserObj;
	private static Storage storageObj;
	private static ArrayList<Task> taskList;
	private static String previousCommandString;
	private static String previousCommandKeyword;
	private static Handler commandHandler;
	private static final String STRING_EMPTY = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	private static final String ERROR_INVALID_INDEX = "Error: Invalid index entered";
	private static final String ERROR_INVALID_DATE = "Error: Please make sure that given date and time fields are valid";
	private static final String ERROR_INVALID_COMMAND = "Error: Invalid command entered. Please enter \"help\" to view command format";
	
	private static final String MESSAGE_INPUT_LOCATION = "Directory location not set, please input directory location before running the program";
	private static final String MESSAGE_NO_TASK_FOUND = "No task found.";
	private static final String MESSAGE_PREVIOUS_COMMAND_INVALID = "There is no previous command to undo";
	
	
	private static final String MESSAGE_HELP_EXIT = "Exit SimplyAmazing\ncommand: exit\n";
	private static final String MESSAGE_HELP_SEARCH = "Search for tasks containing the given keyword\ncommand: search <keyword>\n";
	private static final String MESSAGE_HELP_UNDO = "Undo the most recent command\ncommand: undo\n";
	private static final String MESSAGE_HELP_DONE = "Marks task as completed\ncommand: done <task index>\n";
	private static final String MESSAGE_HELP_DELETE = "Delete task from list\ncommand: delete <task index>\n";
	private static final String MESSAGE_HELP_EDIT = "Edit content in a task\ncommand: edit <task index> <task header> <updated content>\n";
	
	private static final String MESSAGE_HELP = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help done\n6. help search\n"
			+ "7. help location\n8. help undo\n9. help exit\n";
	
	private static final String MESSAGE_HELP_LOCATION = "Set storage location or folder for application data\n"
			+ "command: location <path>";
	
	private static final String MESSAGE_HELP_VIEW = "1.Display all tasks\n command: view\n\n2.Display tasks with deadlines\n"
			+ "command: view deadlines\n\n3.Display events\ncommand: view events\n\n4.Display tasks without deadlines\ncommand: view tasks\n\n"
			+ "5.Display completed tasks\ncommand: view done\n\n6.Display overdue tasks\ncommand: view overdue\n\n";

	private static final String MESSAGE_HELP_ADD_TASK = "1.Add a task to the list\ncommand: add <task description>\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>";
	
	
	enum CommandType {
		ADD_TASK, VIEW_LIST, DELETE_TASK,INVALID,
		SEARCH_KEYWORD, UNDO_LAST, EDIT_TASK, SET_LOCATION,
		MARK_TASK, HELP, EXIT;
	};
	
	
	public Logic(){
		parserObj = new Parser();
		storageObj = new Storage();
		taskList = new ArrayList<Task>();
		commandHandler = new Handler();
		previousCommandKeyword = STRING_EMPTY;
		previousCommandString = STRING_EMPTY;
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
		} else if (commandWord.equalsIgnoreCase("help")) {
			return CommandType.HELP;
		} else if (commandWord.equalsIgnoreCase("exit")){
			return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}
	
	
	public String executeCommand(String userCommand) throws Exception {
		commandHandler = parserObj.getHandler(userCommand);
		
		logger.log(Level.INFO, "at going to execute command");	
		assert commandHandler != null;                                     // assert
		logger.log(Level.INFO, "commandHandler not null");
		
		String commandWord = commandHandler.getCommandType();
		CommandType commandType = getCommandType(commandWord);
		
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
			case EXIT :
				System.exit(0);
			default:
				throw new Exception(ERROR_INVALID_COMMAND);
		}
		logger.log(Level.INFO, "about to return to UI");
		previousCommandKeyword = commandWord;
		if (!commandType.equals(CommandType.VIEW_LIST) && !commandType.equals(CommandType.SEARCH_KEYWORD) && !commandType.equals(CommandType.UNDO_LAST)) {
			previousCommandString = userCommand;
		}
		return feedback;
	}
	
	
	private static String executeAddCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in add");
			throw new Exception(commandHandler.getFeedBack());
		} else {
			logger.log(Level.INFO, "no error with add, interacting with storage now");
			Task taskToAdd = commandHandler.getTask();
			return storageObj.addTask(taskToAdd);
		}
	}
	
	
	private static String executeViewCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			logger.log(Level.INFO, "before executing view command");
			String keyWord = commandHandler.getKeyWord();
			assert keyWord != null;
			taskList = storageObj.viewTasks(keyWord);
			return convertListToString(taskList);	
		}
	}
	
	
	private static String executeEditCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else { 
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex());
			
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "index given is invalid");
				throw new Exception(ERROR_INVALID_INDEX);
			} else {
				logger.log(Level.INFO, "index valid, editing now");
				int indexToEdit = Integer.parseInt(commandHandler.getIndex());
				Task fieldsToChange = commandHandler.getTask();
				Task originalTask = taskList.get(indexToEdit - 1);
				boolean isDateValid = checkDateValid(fieldsToChange, originalTask);
				
				if (isDateValid == true) {
					return storageObj.editTask(originalTask, fieldsToChange);
				} else {
					throw new Exception(ERROR_INVALID_DATE);
				}
			}
		}
	}

	
	private static String executeDeleteCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex());
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "index given is invalid");
				throw new Exception(ERROR_INVALID_INDEX);
			} else {
				logger.log(Level.INFO, "index valid, deleting now");
				int indexToDelete = Integer.parseInt(commandHandler.getIndex());
				Task taskToDelete = taskList.get(indexToDelete - 1);
				return storageObj.deleteTask(taskToDelete);
			}
		}
	}

	
	private static String executeSearchCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else{
			String keyword = commandHandler.getKeyWord();
			taskList = storageObj.searchTasks(keyword);
			String listInStringFormat = convertListToString(taskList);
			return listInStringFormat;
		}
	}
	
	
	private static String executeUndoCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			boolean hasPreviousCommand = isPreviousCommandValid();
			
			if (hasPreviousCommand == false) {
				return MESSAGE_PREVIOUS_COMMAND_INVALID;
			} else {
				return storageObj.restore(previousCommandString);
			}
		}
	}
	
	
	private static String executeSetLocationCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			String directoryPath = commandHandler.getKeyWord();
			assert directoryPath != null;
			return storageObj.setLocation(directoryPath);
		}
	}
	
	
	private static String executeMarkCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex());
			if (isIndexValid == false) {
				throw new Exception(ERROR_INVALID_INDEX);
			} else{
				int indexToMark = Integer.parseInt(commandHandler.getIndex());
				Task taskToMark = taskList.get(indexToMark - 1);
				return storageObj.markTaskDone(taskToMark);
			}
		}
	}
	
	
	private static String executeHelpCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			if(commandHandler.getKeyWord().equals("")) {
				return MESSAGE_HELP;
			} else if (commandHandler.getKeyWord().equals("add")) {
				return MESSAGE_HELP_ADD_TASK;
			} else if (commandHandler.getKeyWord().equals("delete")) {
				return MESSAGE_HELP_DELETE;
			} else if (commandHandler.getKeyWord().equals("view")) {
				return MESSAGE_HELP_VIEW;
			} else if (commandHandler.getKeyWord().equals("search")) {
				return MESSAGE_HELP_SEARCH;
			} else if (commandHandler.getKeyWord().equals("edit")) {
				return MESSAGE_HELP_EDIT;
			} else if (commandHandler.getKeyWord().equals("exit")) {
				return MESSAGE_HELP_EXIT;
			} else if (commandHandler.getKeyWord().equals("location")) {
				return MESSAGE_HELP_LOCATION;
			} else if (commandHandler.getKeyWord().equals("undo")) {
				return MESSAGE_HELP_UNDO;
			} else {
				return MESSAGE_HELP_DONE;
			}
		}
	}
	
	
	private static boolean checkIndexValid(String indexStr){
		if (indexStr == null) {
			return false;
		} else {
			int indexToDelete = Integer.parseInt(indexStr);
			
			assert indexToDelete >= 0;
			assert indexToDelete <= taskList.size();
			
			if(indexToDelete <= 0 || indexToDelete > taskList.size()){
				return false;
			} else {
				return true;
			}
		}
	}
	
	
	private static boolean isListShown() {
		if (previousCommandKeyword.toLowerCase().equals("search") || previousCommandKeyword.toLowerCase().equals("view")) {
			return true;
		} else {
			return false;
		}
	}
	
	
	private static boolean checkDateValid(Task fieldsToChange, Task originalTask) throws Exception{
		Date newStartTime = fieldsToChange.getStartTime();
		Date newEndTime = fieldsToChange.getEndTime();
		Date previousStartTime = originalTask.getStartTime();
		Date previousEndTime = originalTask.getEndTime();
		//Date currentTime =  new Date();
		
		if(!(newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0 && newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0)) { // if both start time and end time are not modified
			if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // start time is modified
				if (previousEndTime.compareTo(Task.DEFAULT_DATE_VALUE) == 0) { // no end time => it's a floating task
					return false;
				} else {
					if (newStartTime.after(previousEndTime)) {
						return false;
					}

				}
			} else if (newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // end time is modified
				if (previousStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // has start time => it's an event
					if (newEndTime.before(previousStartTime)) {
						return false;
					}
				}
			} 
		}
		return true;
		/*
		// date fields not modified
		if(newStartTime.equals(Task.DEFAULT_DATE_VALUE) && newEndTime.equals(Task.DEFAULT_DATE_VALUE)){
			return true;
			
		} else if ( (!newStartTime.equals(Task.DEFAULT_DATE_VALUE)) && (!newEndTime.equals(Task.DEFAULT_DATE_VALUE))){
			return true;              // both start and end time modified. This should be checked by parser
			
		} else if (newStartTime.equals(Task.DEFAULT_DATE_VALUE) && !newEndTime.equals(Task.DEFAULT_DATE_VALUE)) {      // start not modified, check the end
			if (newEndTime.before(currentTime)) {						// newEndtime has passed, so its invalid
				return false;
			} else if(newEndTime.before(previousStartTime) && !(previousStartTime.equals(Task.DEFAULT_DATE_VALUE))) {
				return false;                                           // new end time is before old start time, so its invalid
			} else {
				return true;
			}
		} else if (newEndTime.equals(Task.DEFAULT_DATE_VALUE) && !(newStartTime.equals(Task.DEFAULT_DATE_VALUE))) {        // new end time not modified, only check the new start time
			//if( newStartTime.before(currentTime)) {
			//	return false;
			//} else 
			if (previousEndTime.equals(Task.DEFAULT_DATE_VALUE)) {
				return false;
			} else if (newStartTime.after(previousEndTime) && !(previousEndTime.equals(Task.DEFAULT_DATE_VALUE))){
				return false;											// previous end time not default and new start is after previous end
			} else{
				return true;
			}
		
		} else {
			return false;
		}*/
	}
	
	
	private static boolean isPreviousCommandValid() {
		if (previousCommandString.equals(STRING_EMPTY)) {
			return false;
		} else {
			return true;
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