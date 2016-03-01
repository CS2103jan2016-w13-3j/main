
public class Parser {

	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_EDIT = "edit";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_VIEW = "view";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_HELP = "help";
	private static final String COMMAND_UNDO = "undo";
	private static final String COMMAND_SET_LOCATION = "location";
	private static final String COMMAND_MARK_AS_DONE = "done";
	private static final String COMMAND_INVALID = "the command is invalid";
	private String userInput = "";
	private String operation = "";
	private String theRestText = "";

	public Parser() {
		this.userInput = "";
	}

	public String getFirstWord(String userCommand) {
		userInput = userCommand;
		operation = userInput.trim().split("\\s+")[0];
		return operation;
	}

	public String removeFirstWord(String userCommand) {
		theRestText = userCommand.replace(operation, "").trim();

		switch (operation) {
		case COMMAND_ADD:
			return "";
		case COMMAND_DELETE:
			return theRestText;
		case COMMAND_VIEW:
			return theRestText;
		case COMMAND_EDIT:
			return theRestText;
		case COMMAND_SEARCH:
			return theRestText;
		case COMMAND_HELP:
			return "";
		case COMMAND_UNDO:
			return "";
		case COMMAND_CLEAR:
			return "";
		case COMMAND_SET_LOCATION:
			return theRestText;
		case COMMAND_MARK_AS_DONE:
			return theRestText;
		default:
			return COMMAND_INVALID;
		}

	}

	public String parseSetLocationCommand(String userCommand) {
		operation = userCommand.trim().split("\\s+")[0];
		theRestText = userCommand.replace(operation, "").trim();
		
		return theRestText;
	}

	public Task parseAddCommand(String userCommand) throws Exception {
		operation = userCommand.trim().split("\\s+")[0];
		theRestText = userCommand.replace(operation, "").trim();
		String description = theRestText.trim().split("\\s+")[0];
		
		Task task = new Task();
		task.setDescription(description);
		task.setStartTime("16:00 Jan 20 2016");
		task.setEndTime("20:00 Jan 20 2016");
		task.setDone(false);		
		return task;
	}

	public Task parseEditCommand(String userCommand) throws Exception {
		Task task = new Task();
		operation = userCommand.trim().split("\\s+")[0];
		theRestText = userCommand.replace(operation, "").trim();
		String newDescription = theRestText.trim().split("\\s+")[0];
		
		task.setDescription(newDescription);
		task.setStartTime("16:00 Jan 20 2016");
		task.setEndTime("20:00 Jan 20 2016");
		task.setDone(false);	
		return task;
	}

}
