import java.util.ArrayList;
import java.util.Scanner;

public class Logic {
	
	private static Parser parser;
	private static Storage storage;
	private static ArrayList<Task> list;
	public static Logic logicObject;
	private static Scanner sc;
	
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command entered. Please enter \"help\" to view command format";
	
	
	enum CommandType {
		ADD_TASK, VIEW_LIST, DELETE_TASK,INVALID,
		SEARCH_KEYWORD, UNDO_LAST, EDIT_TASK, SET_LOCATION,
		MARK_TASK, HELP, 
		;
	};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logicObject = new Logic();
		runProgram();
	}
	
	public Logic(){
		Parser parser = new Parser();
		Storage storage = new Storage();
		ArrayList<Task> searchResult = new ArrayList();
		sc = new Scanner(System.in);
	}
	
	private static void runProgram(){
		while (true){
			String userCommand = getUserCommand();
			String commandWord = parser.getFirstWord(userCommand);
			CommandType cType = getCommandType(commandWord);
			String feedback = executeCommand(cType, userCommand);
			displayFeedback(feedback);
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
	
	public static String executeCommand(CommandType comType, String userCommand) {
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
	
	private static String executeAddCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeViewCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeEditCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeDeleteCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeSearchCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeUndoCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeSetLocationCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeMarkCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String executeHelpCommand(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void displayFeedback(String feedback){
		System.out.println(feedback);
	}
	
	private static String convertListToString(ArrayList<Task> list){
		return "";
	}
	
}
