import java.util.ArrayList;
import java.util.Scanner;

public class Logic {
	
	private static Parser parser;
	private static Storage storage;
	private static ArrayList<Task> list;
	public static Logic logicObject;
	private static Scanner sc;
	private static String previousCommand;
	
	private static final String EMPTY_STRING = "";
	
	private static final String ERROR_DISPLAY_LIST_BEFORE_EDIT = "Error: Please view or search the list before marking, editing or deleting";
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command entered. Please enter \"help\" to view command format";
	private static final String MESSAGE_INPUT_LOCATION = "Directory location not set, please input directory location before running the program"; 
	
	enum CommandType {
		ADD_TASK, VIEW_LIST, DELETE_TASK,INVALID,
		SEARCH_KEYWORD, UNDO_LAST, EDIT_TASK, SET_LOCATION,
		MARK_TASK, HELP, 
		;
	};
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		logicObject = new Logic();
		runProgram();
	}
	
	public Logic(){
		parser = new Parser();
		storage = new Storage();
		list = new ArrayList();
		previousCommand = "";
		sc = new Scanner(System.in);
	}
	
	private static void runProgram() throws Exception{
		while (true){
			bootstrap();
			String userCommand = getUserCommand();
			String commandWord = parser.getFirstWord(userCommand);
			CommandType cType = getCommandType(commandWord);
			String feedback = executeCommand(cType, userCommand);
			displayFeedback(feedback);
			previousCommand = commandWord;
		}
	}

	private static void bootstrap() {
		Boolean isLocationSet = storage.isLocationSet();
		if (isLocationSet == true) {
			return;
		} else{
			displayFeedback(MESSAGE_INPUT_LOCATION);
		}
		
	}

	public static String getUserCommand(){
		return sc.nextLine();
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
		} else if (commandWord.equalsIgnoreCase("mark")) {
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
	
	public static String executeCommand(CommandType comType, String userCommand) throws Exception {
		switch (comType) {
			case ADD_TASK :
				return executeAddCommand(userCommand);
			case DELETE_TASK :
				return executeDeleteCommand(userCommand);
			case VIEW_LIST :
				return executeViewCommand(userCommand);
			case EDIT_TASK :
				return executeEditCommand(userCommand);
			case SEARCH_KEYWORD :
				return executeSearchCommand(userCommand);
			case UNDO_LAST :
				return executeUndoCommand(userCommand);
			case SET_LOCATION :
				return executeSetLocationCommand(userCommand);
			case MARK_TASK :
				return executeMarkCommand(userCommand);
			case HELP :
				return executeHelpCommand(userCommand);
			default:
				return MESSAGE_INVALID_COMMAND;
		}
	}
	
	private static String executeAddCommand(String userCommand) throws Exception {
		Task taskToAdd = parser.parseAddCommand(userCommand);
		return storage.addTask(taskToAdd);
	}
	
	private static String executeViewCommand(String userCommand) throws Exception {
		String keyWord = parser.removeFirstWord(userCommand);
		list = storage.load(keyWord);
		return convertListToString(list);
	}
	
	private static String executeEditCommand(String userCommand) throws Exception {
		if(checkListShown() == false) {
			return ERROR_DISPLAY_LIST_BEFORE_EDIT;
		}
		
		String inputText = parser.removeFirstWord(userCommand);
		int index = Integer.parseInt(parser.getFirstWord(inputText));
		String fieldValues = parser.removeFirstWord(inputText);
		Task updatedContent = parser.parseEditCommand(fieldValues);
		Task originalTask = list.get(index);
		return storage.editTask(originalTask, updatedContent);
	}
	
	private static String executeDeleteCommand(String userCommand) throws Exception {
		
		if(checkListShown() == false) {
			return ERROR_DISPLAY_LIST_BEFORE_EDIT;
		}
		
		int indexToDelete = Integer.parseInt(parser.removeFirstWord(userCommand));
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
	
	private static String executeUndoCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeSetLocationCommand(String userCommand) throws Exception {
		String directoryPath = parser.parseSetLocationCommand(userCommand);
		return storage.setLocation(directoryPath);
	}
	
	private static String executeMarkCommand(String userCommand) throws Exception {
		if(checkListShown() == false) {
			return ERROR_DISPLAY_LIST_BEFORE_EDIT;
		}
		int indexToMark = Integer.parseInt(parser.removeFirstWord(userCommand));
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
	
	private static boolean checkListShown() {
		if (previousCommand.toLowerCase().equals("search") || previousCommand.toLowerCase().equals("view")) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String convertListToString(ArrayList<Task> list){
		String convertedList = EMPTY_STRING;
		for (int i = 0; i < list.size(); i++) {
			Task taskToPrint = list.get(i);
			convertedList += (i+1) +". " + taskToPrint.toString() + "\n";
		}
		return convertedList;
	}
	
}
