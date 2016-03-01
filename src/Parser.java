
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
	private String theRestText = "  ";

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
		Task task = new Task();
		theRestText = removeFirstWord(userCommand);
		
		if (!theRestText.contains("from") && !theRestText.contains("by")){
			
			task.setDescription(theRestText);
		}
		else if (theRestText.contains("by")){
			//String description1 = theRestText.trim().substring(0,theRestText.lastIndexOf("by")-1);
			//String endTime1 = theRestText.replace(description1, "").trim().substring(theRestText.lastIndexOf("by")+3);
			
			String[] text = theRestText.split("by");
			String description  = text[0].trim();
			String endTime = text[1].trim();
			task.setDescription(description);
			task.setEndTime(endTime);
		}else {
			String[] text = theRestText.split("from");
			String description  = text[0].trim();
			String[] text1 = text[1].split("to");
			String startTime = text1[0].trim();
			String endTime = text1[1].trim();
			
			//String description1 = theRestText.trim().substring(0,theRestText.lastIndexOf("from")-1);
			//String startime1 = theRestText.trim().substring(theRestText.lastIndexOf("from")+4,theRestText.lastIndexOf("from")-1);
			task.setDescription(description);
			task.setStartTime(startTime);
			task.setEndTime(endTime);
		}
		
		
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
