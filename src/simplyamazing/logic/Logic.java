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

/*
 * This class manages the flow of the program execution for SimplyAmazing.
 * It is responsible for breaking down the user's command, with the help of the Parser component and then
 * executing the instruction through interacting with the Storage component.
 * 
 * @author A0125136N
 */

public class Logic {
 
	private static Logger logger;
	private static Parser parserObj;
	private static Storage storageObj;
	private static ArrayList<Task> taskList;
	private static String lastModifyCommand;
	private static String previousCommandString;
	private static Handler commandHandler;
	
	private static final String STRING_EMPTY = "";
	private static final String FILE_PATH = "C:\\Users\\Public\\SimplyAmazing\\logFile.txt";
	private static final int EXIT_WITHOUT_ERROR = 0;
	private static final int EMPTY_LIST_SIZE = 0;
	private static final int ONE_ELEMENT_IN_LIST = 1;
	private static final int FIRST_ELEMENT_INDEX = 0;
	private static final int INVALID_INDEX = -1;
	
	//The following are error messages which will be displayed to the user
	private static final String ERROR_INVALID_DIRECTORY = "Error: Not a valid directory";
	private static final String ERROR_INVALID_INDEX = "Error: The Index entered is invalid";
	private static final String ERROR_INVALID_INDEX_MULTIPLE = "Error: One of the given indexes is invalid";
	private static final String ERROR_PREVIOUS_COMMAND_INVALID = "Error: There is no previous command to undo"; 
	private static final String ERROR_PREVIOUS_COMMAND_INVALID_REDO = "Error: There is no previous command to redo";
	private static final String ERROR_NO_END_TIME = "Error: Unable to allocate a start time when the task has no end time";
	private static final String ERROR_INVALID_END_TIME = "Error: Unable to remove end time for an event";
	private static final String ERROR_START_AFTER_END = "Error: New start time cannot be after the end time";
	private static final String ERROR_START_SAME_AS_END = "Error: New start time cannot be the same as the end time";
	private static final String ERROR_END_BEFORE_START = "Error: New end time cannot be before the start time";
	private static final String ERROR_END_SAME_AS_START = "Error: New end time cannot be the same as the start time";
	private static final String ERROR_INVALID_COMMAND = "Error: Invalid command entered. Please enter \"help\""
			+ " to view all commands and their format";

	// The following are messages which will be displayed to the user
	private static final String MESSAGE_EMPTY_LIST = "List is empty";
	private static final String MESSAGE_NO_TASKS_FOUND = "There are no tasks containing the given keyword";
	private static final String MESSAGE_HELP_REDO = "Redo the most recent command\nCommand: redo\n";
	private static final String MESSAGE_HELP_UNDO = "Undo the most recent command\nCommand: undo\n";
	
	private static final String MESSAGE_HELP_EXIT = "Exits SimplyAmazing\nCommand: exit\n\n\nNote: You may also use "
			+ "\"logout\" or \"quit\" instead of \"exit\"";
	
	private static final String MESSAGE_HELP_UNMARK = "Unmarks a completed task\nCommand: undone <task index>\n\n"
			+ "Example:\nundone 2\n\n\nNote: You may also use the keyword \"unmark\" instead of \"undone\"";
	
	private static final String MESSAGE_HELP_SEARCH = "Search for tasks containing the given keyword or date \n"
			+ "Command: search <keyword> or search<date>\n\nExample:\nsearch meeting\n\n\n"
			+ "Note: You may also use the keyword \"find\" instead of \"search\"";
	
	private static final String MESSAGE_HELP_MARK = "Marks task as completed\nCommand: done <task index>\n\n"
			+ "Example:\ndone 2\n\n\n"
			+ "Note: You may also use the keywords \"mark\", \"complete\" or \"finish\" instead of \"done\"";
	
	private static final String MESSAGE_HELP_DELETE = "Delete task from list\nCommand: delete <task index>\n\n"
			+ "Example:\ndelete 1\n\n\n"
			+ "Note: You may also use the keywords \"-\", \"del\", \"remove\" or \"cancel\" instead of \"delete\"";
	
	private static final String MESSAGE_HELP_EDIT = "Edit content in a task\nCommand: edit <task index> <task header> "
			+ "<updated content>\n\n"
			+ "Example:\n1. edit 4 description send marketing report\n\n2. edit 3 start 22:00 26 may 2016,"
			+ " end 22:40 26 may 2016\n\n3. edit 1 priority high\n\n\n"
			+ "Note: You may also use the keywords \"change\" or \"update\" instead of \"edit\"";

	private static final String MESSAGE_HELP = "Key in the following to view specific command formats:\n"
			+ "1. help add\n2. help delete\n3. help edit\n4. help view\n5. help search \n6. help mark\n"
			+ "7. help unmark\n8. help undo\n9. help redo\n10. help location \n11. help exit\n";

	private static final String MESSAGE_HELP_LOCATION = "Sets the storage location or folder for application data\n"
			+ "Command: location <path>\n" + "\n"
			+ "Example:\nlocation C:\\Users\\Jim\\Desktop\\Task Data\n\n\n"
			+ "Note: You may also use the keywords \"path\" or \"address\" instead of \"location\"";

	private static final String MESSAGE_HELP_VIEW = "1.Display all tasks\n Command: view\n\n"
			+ "2.Display tasks with deadlines\nCommand: view deadlines\n\n"
			+ "3.Display all events\nCommand: view events\n\n"
			+ "4.Display tasks without deadlines\nCommand: view tasks\n\n"
			+ "5.Display completed tasks\nCommand: view done\n\n"
			+ "6.Display overdue tasks\nCommand: view overdue\n\n\n"
			+ "Note: You may also use the keywords \"display\", \"show\" or \"list\" instead of \"view\"";

	private static final String MESSAGE_HELP_ADD_TASK = "1.Add a task to the list\nCommand: add <task description>\n\n"
			+ "Example: add Prepare presentation\n\n\n2.Add an event to the list\n"
			+ "command: add <task description> from <start time hh:mm> <start date dd MMM yyyy> to\n<end time hh:mm> "
			+ "<end date dd MMM yyyy>\n\n"
			+ "Example: add Company annual dinner from 19:00 29 Dec 2016 to 22:00 29 dec 2016\n\n\n"
			+ "3.Add a deadline to the list\ncommand: add <task description> by <end time hh:mm> <end date dd MMM yyyy>\n\n"
			+ "Example: add Submit marketing report by 17:00 20 Dec 2016\n\n\n"
			+ "Note: You may use the keyword \"+\" instead of \"add\"";


	enum CommandType {
		ADD_TASK, VIEW_LIST, DELETE_TASK,INVALID,
		SEARCH_KEYWORD, UNDO_LAST, EDIT_TASK, SET_LOCATION,
		MARK_TASK, UNMARK_TASK, REDO, HELP, EXIT;
	};


	public Logic() {
		parserObj = new Parser();
		storageObj = new Storage();
		taskList = new ArrayList<Task>();
		commandHandler = new Handler();
		lastModifyCommand = STRING_EMPTY;
		previousCommandString = STRING_EMPTY;
		logger = Logger.getLogger("simplyamazing");
		try {
			FileHandler fileHandler = new FileHandler(FILE_PATH, true);
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();  
			fileHandler.setFormatter(formatter);
		} catch (Exception e) {
			System.out.println("Error while setting up filehandler");
		}
	}


	private static CommandType getCommandType(String commandWord) {
		assert commandWord != null;
		
		if (commandWord.equalsIgnoreCase("add")) {
			return CommandType.ADD_TASK;
		} else if (commandWord.equalsIgnoreCase("delete")) {
			return CommandType.DELETE_TASK;
		} else if (commandWord.equalsIgnoreCase("view")) {
			return CommandType.VIEW_LIST;
		} else if (commandWord.equalsIgnoreCase("edit")) {
			return CommandType.EDIT_TASK;
		} else if (commandWord.equalsIgnoreCase("search")) {
			return CommandType.SEARCH_KEYWORD;
		} else if (commandWord.equalsIgnoreCase("undo")) {
			return CommandType.UNDO_LAST;
		} else if (commandWord.equalsIgnoreCase("redo")) {
			return CommandType.REDO;
		} else if (commandWord.equalsIgnoreCase("location")) {
			return CommandType.SET_LOCATION;
		} else if (commandWord.equalsIgnoreCase("done")) {
			return CommandType.MARK_TASK;
		} else if (commandWord.equalsIgnoreCase("undone")) {
			return CommandType.UNMARK_TASK;
		} else if (commandWord.equalsIgnoreCase("help")) {
			return CommandType.HELP;
		} else if (commandWord.equalsIgnoreCase("exit")) {
			return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}

	
	/*
	 * This method controls how the command is executed, based on the different command types given.
	 * It is also responsible for passing on feedback messages, regarding the status of the commands,
	 * to the UI
	 */
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
		case REDO :
			feedback = executeRedoCommand(commandHandler);
			break;
		case SET_LOCATION :
			feedback = executeSetLocationCommand(commandHandler);
			break;
		case MARK_TASK :
			feedback = executeMarkCommand(commandHandler);
			break;
		case UNMARK_TASK :
			feedback = executeUnmarkCommand(commandHandler);
			break;
		case HELP :
			feedback = executeHelpCommand(commandHandler);
			break;
		case EXIT :
			System.exit(EXIT_WITHOUT_ERROR);
		default:
			return ERROR_INVALID_COMMAND;
		}
		
		previousCommandString = userCommand;
		assert previousCommandString != null;
		boolean isListModified = hasListBeenModified(commandType);

		if (isListModified == true) {
			logger.log(Level.INFO, "command has modified the list, setting new lastModify Command now");
			lastModifyCommand = userCommand;
		}
		logger.log(Level.INFO, "about to return to UI");
		return feedback;
	}

	
	/*
	 * This method is responsible for executing the add command and reporting on its success to the 
	 * UI
	 */
	private static String executeAddCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in add");
			return commandHandler.getFeedBack();

		} else {
			logger.log(Level.INFO, "no error with add, interacting with storage now");
			Task taskToAdd = commandHandler.getTask();
			assert taskToAdd != null;
			return storageObj.addTask(taskToAdd);
		}
	}
	
	
	/*
	 * This method executes the delete command. The status of the command, whether it is successful or
	 * unsuccessful will be returned to the UI which will then display it to the user.
	 */
	private static String executeDeleteCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in delete");
			return commandHandler.getFeedBack();

		} else {

			ArrayList<Integer> listToDelete = commandHandler.getIndexList();
			boolean isIndexValid = true;
			int indexToDelete = INVALID_INDEX;
			
			if (listToDelete.size() == ONE_ELEMENT_IN_LIST) {
				logger.log(Level.INFO, "list contains only one task to delete");
				indexToDelete = listToDelete.get(FIRST_ELEMENT_INDEX);

				isIndexValid = canRetrieveIndex(indexToDelete);
				
				if (isIndexValid == false) {
					logger.log(Level.WARNING, "index given is invalid");
					return ERROR_INVALID_INDEX;
				}
				
				logger.log(Level.INFO, "index valid, interacting with storage now");
				Task taskToDelete = taskList.get(indexToDelete - 1);
				return storageObj.deleteTask(taskToDelete);
				
			} else {
				return deleteMultiple(listToDelete);
			}
		}
	}
	
	
	/*
	 * This method facilitates the deletion of multiple indexes. It checks if all indexes are valid
	 * before deleting.
	 */
	private static String deleteMultiple(ArrayList<Integer> listToDelete) throws Exception {
		boolean isIndexValid = true;
		int indexToDelete = INVALID_INDEX;
		logger.log(Level.INFO, "list contains more than one task to delete");
		ArrayList<Task> tasksToDelete = new ArrayList<Task>();
		
		for (int i = 0; i < listToDelete.size(); i++) {
			indexToDelete = listToDelete.get(i);
			isIndexValid = canRetrieveIndex(indexToDelete);
			
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "one of the indexes given is invalid");
				return ERROR_INVALID_INDEX_MULTIPLE;
			} else {
				tasksToDelete.add(taskList.get(indexToDelete - 1));
			}
		}
		logger.log(Level.INFO, "all indexes valid, interacting with storage now");
		return storageObj.deleteMultipleTasks(tasksToDelete);
	}
	
	
	/*
	 * This method is responsible for executing the view command. It returns a String containing
	 * either all pending tasks, overdue tasks or completed task, to the UI.
	 */
	private static String executeViewCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in view");
			return commandHandler.getFeedBack();

		} else {
			logger.log(Level.INFO, "before executing view command");
			String keyWord = commandHandler.getKeyWord();
			assert keyWord != null;
			taskList = storageObj.viewTasks(keyWord);
			return convertListToString(taskList);
		}
	}


	/*
	 * This method is responsible for the execution of the Edit command. It reports on the success of
	 * the command to the UI. 
	 */
	private static String executeEditCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in edit");
			return commandHandler.getFeedBack();

		} else { 
			// only one task can be edited at a time, hence check the index of the first task
			ArrayList<Integer> listToEdit = commandHandler.getIndexList();
			int indexToEdit = listToEdit.get(FIRST_ELEMENT_INDEX);
			boolean isIndexValid = canRetrieveIndex(indexToEdit);

			if (isIndexValid == false) {
				logger.log(Level.WARNING, "index given is invalid");
				return ERROR_INVALID_INDEX;

			} else {
				logger.log(Level.INFO, "index valid, editing now");
				Task fieldsToChange = commandHandler.getTask();
				Task originalTask = taskList.get(indexToEdit - 1);
				String dateErrorMessage = hasDateError(fieldsToChange, originalTask);

				if (dateErrorMessage.equals(STRING_EMPTY)) {
					logger.log(Level.INFO, "date field is valid, interacting with storage now");
					return storageObj.editTask(originalTask, fieldsToChange);

				} else {
					logger.log(Level.WARNING, "invalid date field");
					return dateErrorMessage;

				}
			}
		}
	}

	
	/*
	 * This method executes the search command which allows the user to search for tasks
	 * based on a given date or an description. It returns a shortlist of tasks containing the specified
	 * keyword/date to the UI and this will then be shown to the user.
	 */
	private static String executeSearchCommand(Handler commandHandler) throws Exception {			
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in search");
			return commandHandler.getFeedBack();
		}

		if (commandHandler.getHasEndDate() == true) {
			Date endDate = commandHandler.getTask().getEndTime();
			taskList = storageObj.searchTasksByDate(endDate);

			if (taskList.size() == EMPTY_LIST_SIZE) {
				logger.log(Level.INFO, "There are no tasks containing the date");
				return MESSAGE_NO_TASKS_FOUND;
			} else {
				logger.log(Level.INFO, "tasks have been retrieved, converting into a string now");
				return convertListToString(taskList);
			}
		} else {

			String keyword = commandHandler.getKeyWord();
			taskList = storageObj.searchTasks(keyword);

			if (taskList.size() == EMPTY_LIST_SIZE) {
				logger.log(Level.INFO, "There are no tasks containing the keyword");
				return MESSAGE_NO_TASKS_FOUND;
			} else {
				logger.log(Level.INFO, "tasks have been retrieved, converting into a string now");
				return convertListToString(taskList);
			}
		}
	}

	
	/*
	 * This method executes the undo command, which allows the user to undo their most
	 * recent command.
	 */
	private static String executeUndoCommand(Handler commandHandler) throws Exception {
		boolean isPreviousCommandValid = hasPreviousCommand();

		if (isPreviousCommandValid == false) {				
			logger.log(Level.WARNING, "nothing to undo");
			return ERROR_PREVIOUS_COMMAND_INVALID;

		} else {			
			logger.log(Level.INFO, "there is a previous command, undoing now");
			return storageObj.restore(lastModifyCommand);
		}
	}
	
	
	/*
	 * This method allows the user to redo an action that has recently been undone.
	 */
	private static String executeRedoCommand(Handler commandHandler) throws Exception {
		boolean isPreviousCommandValid = hasPreviousCommand();

		if (isPreviousCommandValid == false) {				
			logger.log(Level.WARNING, "no previous command to redo");
			return ERROR_PREVIOUS_COMMAND_INVALID_REDO;

		} else {			
			logger.log(Level.INFO, "previous command is valid, redoing now");
			return storageObj.restore(lastModifyCommand);
		}
	}	
		
	
	/*
	 * This method allows the user to set the storage location so that SimplyAmazing would be able
	 * to save the user's data into the specified location
	 */
	private static String executeSetLocationCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {			
			logger.log(Level.WARNING, "handler has reported an error in location");
			return commandHandler.getFeedBack();

		} else {			
			String directoryPath = commandHandler.getKeyWord();
			assert directoryPath != null;
			String feedback = STRING_EMPTY;

			try {
				logger.log(Level.INFO, "setting the location");
				feedback = storageObj.setLocation(directoryPath);
			} catch (Exception e) {
				logger.log(Level.WARNING, "storage has reported an error in location");
				feedback = ERROR_INVALID_DIRECTORY;
			}
			return feedback;
		}
	}


	/*
	 * This method allows the mark command to be executed so that a user would be able to
	 * mark a task as completed.
	 */
	private static String executeMarkCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in mark");
			return commandHandler.getFeedBack();

		} else {

			ArrayList<Integer> listToMark = commandHandler.getIndexList();
			boolean isIndexValid = true;
			int indexToMark = INVALID_INDEX;

			if (listToMark.size() == ONE_ELEMENT_IN_LIST) {
				logger.log(Level.INFO, "only one task to mark as done");
				indexToMark = listToMark.get(FIRST_ELEMENT_INDEX);

				isIndexValid = canRetrieveIndex(indexToMark);
				if (isIndexValid == false) {
					logger.log(Level.WARNING, "index given is invalid");
					return ERROR_INVALID_INDEX;
				}
				
				logger.log(Level.INFO, "index valid, interacting with storage now");
				Task taskToMark = taskList.get(indexToMark - 1);
				return storageObj.markTaskDone(taskToMark);
				
			} else {
				logger.log(Level.INFO, "more than one task to mark as done");
				return markMultiple(listToMark);
			}
		}
	}
	
	
	/*
	 * This method facilitates marking multiple indexes as done. It checks if all indexes are valid
	 * before marking them.
	 */
	private static String markMultiple(ArrayList<Integer> listToMark) throws Exception {
		boolean isIndexValid = true;
		int indexToMark = INVALID_INDEX;
		ArrayList<Task> tasksToMark = new ArrayList<Task>();

		for (int i = 0; i < listToMark.size(); i++) {
			indexToMark = listToMark.get(i);
			isIndexValid = canRetrieveIndex(indexToMark);
			
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "one of the indexes given is invalid");
				return ERROR_INVALID_INDEX_MULTIPLE;
			} else {
				tasksToMark.add(taskList.get(indexToMark - 1 ));
			}
		}
		logger.log(Level.INFO, "indexes valid, interacting with storage now");
		return storageObj.markMultipleTasksDone(tasksToMark);
	}

	
	/*
	 * This method allows the unmark command to be executed so that the user would be able
	 * to unmark a completed task. Before this command can be successfully executed the user
	 * will have to execute the "view done" command.
	 */
	private static String executeUnmarkCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in unmark");
			return commandHandler.getFeedBack();	
		}

		ArrayList<Integer> listToUnmark = commandHandler.getIndexList();
		boolean isIndexValid = true;
		int indexToUnmark = INVALID_INDEX;

		if (listToUnmark.size() == ONE_ELEMENT_IN_LIST) {
			logger.log(Level.INFO, "only 1 index to unmark");
			indexToUnmark = listToUnmark.get(FIRST_ELEMENT_INDEX);

			isIndexValid = canRetrieveIndex(indexToUnmark);
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "index given is invalid");
				return ERROR_INVALID_INDEX;
			}
			logger.log(Level.INFO, "index valid, unmarking now");
			Task taskToUnmark = taskList.get(indexToUnmark - 1);
			return storageObj.markTaskUndone(taskToUnmark);

		} else {
			logger.log(Level.INFO, "more than 1 index to unmark");
			return unmarkMultiple(listToUnmark);
		}
	}

	
	/*
	 * This method facilitates unmarking multiple indexes. It checks if all indexes are valid
	 * before unmarking them.
	 */
	private static String unmarkMultiple(ArrayList<Integer> listToUnmark) throws Exception {
		boolean isIndexValid = true;
		int indexToUnmark = INVALID_INDEX;
		ArrayList<Task> tasksToUnmark = new ArrayList<Task>();

		for (int i = 0; i < listToUnmark.size(); i++) {
			indexToUnmark = listToUnmark.get(i);
			isIndexValid = canRetrieveIndex(indexToUnmark);
			
			if (isIndexValid == false) {
				logger.log(Level.WARNING, "one of the indexes given is invalid");
				return ERROR_INVALID_INDEX_MULTIPLE;
			} else {
				tasksToUnmark.add(taskList.get(indexToUnmark - 1 ));
			}
		}
		logger.log(Level.INFO, "indexes valid, interacting with storage to unmark");
		return storageObj.markMultipleTasksUndone(tasksToUnmark);
	}

	
	/*
	 * This method allows the help command to be executed. It returns the required
	 * help message to the UI.
	 */
	private static String executeHelpCommand(Handler commandHandler) throws Exception {
		if (commandHandler.getHasError() == true) {
			logger.log(Level.WARNING, "handler has reported an error in help");
			return commandHandler.getFeedBack();

		} else {
			logger.log(Level.INFO, "no error, help keyword is valid.");
			
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
			} else if (commandHandler.getKeyWord().equals("redo")) {
				return MESSAGE_HELP_REDO;
			} else if (commandHandler.getKeyWord().equals("unmark")) {
				return MESSAGE_HELP_UNMARK;
			} else {
				return MESSAGE_HELP_MARK;
			}
		}
	}

	
	/*
	 * This method checks if a command has modified the list of tasks. This
	 * ensures that the undo and redo commands function correctly by undoing or 
	 * redoing only commands which have modified the list of tasks.
	 */
	private static boolean hasListBeenModified(CommandType commandType) {
		assert commandType != null;
		
		if (commandType.equals(CommandType.ADD_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.DELETE_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.EDIT_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.MARK_TASK)) {
			return true;
		} else if (commandType.equals(CommandType.UNMARK_TASK)) {
			return true;
		} else {
			return false;
		}
	}

	
	/*
	 * This method checks to see if an index given by the user is valid or invalid.
	 * This ensures that the program is able to detect if the user has given an invalid
	 * index and would allow the program to act accordingly
	 */
	private static boolean canRetrieveIndex(int index) {
		if (index <= EMPTY_LIST_SIZE || index > taskList.size()) {
			return false;
		} else {
			return true;
		}
	}

	
	/*
	 * This method checks if the edited date/dates specified by the user is/are valid
	 * or invalid. If invalid then an error message will be created.
	 */
	private static String hasDateError(Task fieldsToChange, Task originalTask) throws Exception {
		Date newStartTime = fieldsToChange.getStartTime();
		Date newEndTime = fieldsToChange.getEndTime();
		Date previousStartTime = originalTask.getStartTime();
		Date previousEndTime = originalTask.getEndTime();
		
		// if both start time and end time are not modified
		if (!(newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0 && newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0)) { 
			// start time is modified
			if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
				// no end time => it's a floating task 
				if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) !=0 && previousEndTime.compareTo(Task.DEFAULT_DATE_VALUE) == 0) { 
					return ERROR_NO_END_TIME;

				} else { // it's a deadline
					if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) !=0 && newStartTime.after(previousEndTime)) {
						return ERROR_START_AFTER_END;
					}
					if (newStartTime.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) !=0 && newStartTime.equals(previousEndTime)) {
						return ERROR_START_SAME_AS_END;

					}
				}
			} else if (newEndTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { // end time is modified
				// has start time => it's an event
				if (previousStartTime.compareTo(Task.DEFAULT_DATE_VALUE) != 0) { 
					if (newEndTime.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) == 0) {
						return ERROR_INVALID_END_TIME;
					} else {
						if (newEndTime.before(previousStartTime)) {
							return ERROR_END_BEFORE_START;
						}
						if (newEndTime.equals(previousStartTime)) {
							return ERROR_END_SAME_AS_START;
						}
					}
				}
			} 
		}
		return STRING_EMPTY;
	}

	
	private static boolean hasPreviousCommand() {
		if (lastModifyCommand.equals(STRING_EMPTY)) {
			return false;

		} else {
			return true;
		}
	}
	
	
	/*
	 * This method returns the previous command to the UI so that it can be made available for the user
	 */
	public String getPreviousCommand() {
		return previousCommandString;
	}
	
	
	/*
	 * This method returns the a String containing all the tasks in the list so that the 
	 * UI is able to display them to the user
	 */
	public static String getView() throws Exception {
		taskList = storageObj.viewTasks(STRING_EMPTY);
		return convertListToString(taskList);
	}

	
	/*
	 * This method receives a list of tasks and converts it to a String, which can then
	 * be used by the UI to display to the user
	 */
	private static String convertListToString(ArrayList<Task> listToConvert) {
		assert listToConvert != null;
		if (listToConvert.size() == EMPTY_LIST_SIZE) {
			return MESSAGE_EMPTY_LIST;
		}
		String convertedList = STRING_EMPTY;

		for (int i = 0; i < listToConvert.size(); i++) {
			Task taskToPrint = listToConvert.get(i);
			convertedList += (i + 1) + "," + taskToPrint.toString() + "\n";
		}
		return convertedList;
	}
}