package simplyamazing.logic;

import java.util.ArrayList;

import simplyamazing.data.Task;
import simplyamazing.parser.Handler;
import simplyamazing.parser.Parser;
import simplyamazing.storage.Storage;

public class Logic {
	
	private static Parser parserObj;
	private static Storage storageObj;
	private static ArrayList<Task> taskList;
	private static String previousCommandKeyword;
	private static String previousCommand;
	private static Handler commandHandler;
	private static final String STRING_EMPTY = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	private static final String ERROR_INVALID_INDEX = "Error: Invalid index entered";
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command entered. Please enter \"help\" to view command format";
	private static final String MESSAGE_INPUT_LOCATION = "Directory location not set, please input directory location before running the program";
	private static final String MESSAGE_NO_TASK_FOUND = "No task found.";
	
	private static final String MESSAGE_HELP = "To view specific command formats, key in the following:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help done\n6. help search\n"
			+ "7. help location\n8. help undo\n9. help exit\n";
	private static final String MESSAGE_HELP_EXIT = "Exit SimplyAmazing\ncommand = exit\n";
	private static final String MESSAGE_HELP_SEARCH = "Search tasks for given keyword\ncommand = search <keyword>\n";
	private static final String MESSAGE_HELP_UNDO = "Undo the most recent command\ncommand = undo\n";
	private static final String MESSAGE_HELP_DONE = "Marks task as completed\ncommand = done <task index>\n";
	private static final String MESSAGE_HELP_DELETE = "Delete task from list\ncommand = delete <task index>\n";
	private static final String MESSAGE_HELP_EDIT = "Edit content in a task\ncommand = edit <task index> <task header> <updated content>\n";
	
	private static final String MESSAGE_HELP_LOCATION = "Set storage location or folder for application data\n"
			+ "command = location <path>";
	
	private static final String MESSAGE_HELP_VIEW = "1.Display all tasks\n command = view\n\n2.Display tasks with deadlines\n"
			+ "command = view deadlines\n\n3.Display events\ncommand = view events\n\n4.Display tasks without deadlines\ncommand = view tasks\n\n"
			+ "5.Display completed tasks\ncommand = view done\n\n6.Display overdue tasks\ncommand = view overdue\n\n";

	private static final String MESSAGE_HELP_ADD_TASK = "Add a task to a list\ncommand = add <task description>\n";
	
	
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
		previousCommand = STRING_EMPTY;
		previousCommandKeyword = STRING_EMPTY;
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
		
		assert commandHandler != null;                                     // assert
		
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
				throw new Exception(MESSAGE_INVALID_COMMAND);
		}
		
		previousCommand = commandWord;
		return feedback;
	}
	
	private static String executeAddCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			Task taskToAdd = commandHandler.getTask();
			return storageObj.addTask(taskToAdd);
		}
	}
	
	private static String executeViewCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			String keyWord = commandHandler.getKeyWord();
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
				throw new Exception(ERROR_INVALID_INDEX);
			} else {
				int indexToEdit = Integer.parseInt(commandHandler.getIndex());
				Task fieldsToChange = commandHandler.getTask();
				Task originalTask = taskList.get(indexToEdit - 1);
				return storageObj.editTask(originalTask, fieldsToChange);
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
				throw new Exception(ERROR_INVALID_INDEX);
			} else {
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
		} else{
			return storageObj.restore();
		}
	}
	
	private static String executeSetLocationCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			throw new Exception(commandHandler.getFeedBack());
		} else {
			String directoryPath = commandHandler.getKeyWord();
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
			assert indexToDelete < taskList.size();
			
			if(indexToDelete <= 0 || indexToDelete > taskList.size()){
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