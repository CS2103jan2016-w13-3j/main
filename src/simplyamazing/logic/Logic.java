//@@author A0125136N 

package simplyamazing.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import simplyamazing.data.Task;
import simplyamazing.parser.Handler;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class Logic {
	
	private static Logger logger;
	private static Parser parserObj;
	private static Storage storageObj;
	private static ArrayList<Task> taskList;
	private static String lastModifyCommand;
	private static String previousCommandString;
	private static String previousCommandKeyword;
	private static Handler commandHandler;
	private static final String STRING_EMPTY = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	private static final String ERROR_INVALID_INDEX = "Error: The Index entered is invalid";
	private static final String ERROR_INVALID_COMMAND = "Error: Invalid command entered. Please enter \"help\" to view all commands and their format";
	private static final String ERROR_EMPTY_LIST = "Error: List is empty";
	private static final String ERROR_NO_TASKS_FOUND = "Error: There are no tasks containing the given keyword";
	private static final String ERROR_PREVIOUS_COMMAND_INVALID = "Error: There is no previous command to undo";
	private static final String ERROR_NO_END_TIME = "Error: Unable to allocate a start time when the task has no end time";
	private static final String ERROR_START_AFTER_END ="Error: New start time cannot be after the end time";
	private static final String ERROR_START_SAME_AS_END ="Error: New start time cannot be the same as the end time";
	private static final String ERROR_END_BEFORE_START = "Error: New end time cannot be before the start time";
	private static final String ERROR_END_SAME_AS_START ="Error: New end time cannot be the same as the start time";
	
	
	private static final String MESSAGE_HELP_EXIT = "Exit SimplyAmazing\nCommand: exit\n";
	private static final String MESSAGE_HELP_SEARCH = "Search for tasks containing the given keyword\nCommand: search <keyword>\n\nExample:\nsearch meeting\n";
	private static final String MESSAGE_HELP_UNDO = "Undo the most recent command\nCommand: undo\n";
	private static final String MESSAGE_HELP_DONE = "Marks task as completed\nCommand: done <task index>\n\nExample:\ndone 2\n";
	private static final String MESSAGE_HELP_DELETE = "Delete task from list\nCommand: delete <task index>\n\nExample:\ndelete 1";
	private static final String MESSAGE_HELP_EDIT = "Edit content in a task\nCommand: edit <task index> <task header> <updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n2. edit 3 start 22:00 26 may 2016, end 22:40 26 may 2016\n\n"
			+ "3. edit 1 priority high";
	
	private static final String MESSAGE_HELP = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help done\n6. help search\n"
			+ "7. help location\n8. help undo\n9. help exit\n";
	
	private static final String MESSAGE_HELP_LOCATION = "Sets the storage location or folder for application data\n"
			+ "Command: location <path>\n" + "\nExample:\nlocation C:\\Users\\Jim\\Desktop\\Task Data";
	
	private static final String MESSAGE_HELP_VIEW = "1.Display all tasks\n Command: view\n\n2.Display tasks with deadlines\n"
			+ "Command: view deadlines\n\n3.Display all events\nCommand: view events\n\n4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n6.Display overdue tasks\nCommand: view overdue\n\n";

	private static final String MESSAGE_HELP_ADD_TASK = "1.Add a task to the list\nCommand: add <task description>\n\nExample: add prepare presentation\n\n\n"
			+ "2.Add an event to the list\ncommand: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: submit marketing report by 17:00 20 Dec 2016\n";
	
	
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
		lastModifyCommand = STRING_EMPTY;
		previousCommandKeyword = STRING_EMPTY;
		previousCommandString = STRING_EMPTY;
		logger = Logger.getLogger("Logic");
		try{
			FileHandler fh = new FileHandler("C:\\Users\\Ishpal\\Desktop\\Task Data\\logFile.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);
		} catch (Exception e){};
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
		logger.log(Level.INFO, "going to execute command");
		commandHandler = parserObj.getHandler(userCommand);
	
		assert commandHandler != null;                      
		logger.log(Level.INFO, "commandHandler is not null");
		
		String commandWord = commandHandler.getCommandType();
		CommandType commandType = getCommandType(commandWord);
		
		String feedback = STRING_EMPTY;
		previousCommandString = userCommand;
		
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
		
		
		previousCommandKeyword = commandWord;
		previousCommandString = userCommand;
		
		assert previousCommandKeyword != null;
		assert previousCommandString != null;
		
		boolean hasListBeenModified = isListModified(commandType);
		
		if(hasListBeenModified == true) {
			logger.log(Level.INFO, "command has modified the list, setting new lastModify Command now");
			lastModifyCommand = userCommand;
		}
		logger.log(Level.INFO, "about to return to UI");
		return feedback;
	}


	private static String executeAddCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in add");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {
			logger.log(Level.INFO, "no error with add, interacting with storage now");
			Task taskToAdd = commandHandler.getTask();
			assert taskToAdd != null;
			return storageObj.addTask(taskToAdd);
			
		}
	}
	
	
	private static String executeViewCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in view");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {
			logger.log(Level.INFO, "before executing view command");
			String keyWord = commandHandler.getKeyWord();
			assert keyWord != null;
			taskList = storageObj.viewTasks(keyWord);
			return convertListToString(taskList);
			
		}
	}
	
	public static String getView() throws Exception{
		return convertListToString(storageObj.viewTasks(STRING_EMPTY));
	}
	
	
	private static String executeEditCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			logger.log(Level.WARNING, "list has not been shown previously");
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in edit");
			throw new Exception(commandHandler.getFeedBack());
			
		} else { 
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex(), taskList);
			
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "index given is invalid");
				throw new Exception(ERROR_INVALID_INDEX);
				
			} else {
				logger.log(Level.INFO, "index valid, editing now");
				int indexToEdit = Integer.parseInt(commandHandler.getIndex());
				Task fieldsToChange = commandHandler.getTask();
				Task originalTask = taskList.get(indexToEdit - 1);
				String dateErrorMessage = hasDateError(fieldsToChange, originalTask);
				
				if (dateErrorMessage.equals(STRING_EMPTY)) {
					logger.log(Level.INFO, "date field is valid, interacting with storage now");
					return storageObj.editTask(originalTask, fieldsToChange);
					
				} else {
					logger.log(Level.WARNING, "invalid date field");
					throw new Exception(dateErrorMessage);
					
				}
			}
		}
	}

	
	private static String executeDeleteCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {			
			logger.log(Level.WARNING, "list has not been shown previously");
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
			
		}
		
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in delete");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {		
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex(), taskList);
			
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
			logger.log(Level.WARNING, "handler has reported an error in search");
			throw new Exception(commandHandler.getFeedBack());
			
		} else{			
			String keyword = commandHandler.getKeyWord();
			taskList = storageObj.searchTasks(keyword);
			
			if(taskList.size() == 0){
				logger.log(Level.WARNING, "There are no tasks containing the keyword");
				throw new Exception(ERROR_NO_TASKS_FOUND);
			} else {
				logger.log(Level.INFO, "tasks have been retrieved, converting into a string now");
				String listInStringFormat = convertListToString(taskList);
				return listInStringFormat;
			}
		}
	}
	
	
	private static String executeUndoCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {			
			logger.log(Level.WARNING, "handler has reported an error in edit");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {
			boolean hasPreviousCommand = isPreviousCommandValid();
			
			if (hasPreviousCommand == false) {				
				logger.log(Level.WARNING, "previous command is invalid");
				throw new Exception(ERROR_PREVIOUS_COMMAND_INVALID);
				
			} else {			
				return storageObj.restore(lastModifyCommand);
			}
		}
	}
	
	
	private static String executeSetLocationCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {			
			logger.log(Level.WARNING, "handler has reported an error in location");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {			
			String directoryPath = commandHandler.getKeyWord();
			assert directoryPath != null;
			return storageObj.setLocation(directoryPath);
		
		}
	}
	
	
	private static String executeMarkCommand(Handler commandHandler) throws Exception {
		if (isListShown() == false) {
			logger.log(Level.WARNING, "list has not been shown previously");
			throw new Exception(ERROR_DISPLAY_LIST_BEFORE_EDIT);
		}
		
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in edit");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {
			boolean isIndexValid = checkIndexValid(commandHandler.getIndex(), taskList);
			
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
			logger.log(Level.WARNING, "handler has reported an error in help");
			throw new Exception(commandHandler.getFeedBack());
			
		} else {
			if(commandHandler.getKeyWord().equals(STRING_EMPTY)) {
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
	
	
	private boolean isListModified(CommandType commandType) {
		if (commandType.equals(CommandType.ADD_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.DELETE_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.EDIT_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.MARK_TASK)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean checkIndexValid(String indexStr, ArrayList<Task> list){
		if (indexStr == null) {
			return false;
		} else {
			try {
				int indexToDelete = Integer.parseInt(indexStr);

				assert indexToDelete >= 0;
				assert indexToDelete <= list.size();
				
				if(indexToDelete <= 0 || indexToDelete > list.size()){
					return false;
				} else {
					return true;
				}
			} catch (NumberFormatException e) {
				return false;
			}
		}
	}
	
	
	private static boolean isListShown() {
		if (previousCommandKeyword.toLowerCase().equals("search") || previousCommandKeyword.toLowerCase().equals("view")) {
			logger.log(Level.INFO, "list has been shown");
			return true;
		} else {
			logger.log(Level.WARNING, "list has not been shown ");
			return false;
		}
	}
	
	
	private static String hasDateError(Task fieldsToChange, Task originalTask) throws Exception{
		Date newStartTime = fieldsToChange.getStartTime();
		Date newEndTime = fieldsToChange.getEndTime();
		Date previousStartTime = originalTask.getStartTime();
		Date previousEndTime = originalTask.getEndTime();
		
		if(!(newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0 && newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0)) { // if both start time and end time are not modified
			if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // start time is modified
				if (previousEndTime.compareTo(Task.DEFAULT_DATE_VALUE) == 0) { // no end time => it's a floating task
					return ERROR_NO_END_TIME;
					
				} else {
					if (newStartTime.after(previousEndTime)) {
						return ERROR_START_AFTER_END;
						
					}
					if (newStartTime.equals(previousEndTime)) {
						return ERROR_START_SAME_AS_END;
						
					}

				}
			} else if (newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // end time is modified
				if (previousStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // has start time => it's an event
					if (newEndTime.before(previousStartTime)) {
						return ERROR_END_BEFORE_START;
						
					}
					if (newEndTime.equals(previousStartTime)) {
						return ERROR_END_SAME_AS_START;
						
					}
				}
			} 
		}
		
		return STRING_EMPTY;
	}
	
	
	private static boolean isPreviousCommandValid() {
		if (lastModifyCommand.equals(STRING_EMPTY)) {
			return false;
			
		} else {
			return true;
		}
	}
	
	public String getPreviousCommand(){
		return previousCommandString;
	}
	
	
	private static String convertListToString(ArrayList<Task> list) throws Exception{
		if (list.size() == 0) {
			throw new Exception(ERROR_EMPTY_LIST);
		}
		String convertedList = STRING_EMPTY;
		
		for (int i = 0; i < list.size(); i++) {
			Task taskToPrint = list.get(i);
			convertedList += (i+1) + "," + taskToPrint.toString() + "\n";
		}
		return convertedList;
	}
}