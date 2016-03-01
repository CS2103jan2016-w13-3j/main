
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
	
	private static final String STRING_EMPTY = "";
	private static final String CHARACTER_FIELD_VALUE_SEPARATOR = ":";
	private static final String CHARACTER_SPACE = "\\s";
	
	private static final String MESSAGE_INVALID_FORMAT = "The command is invalid";

	public Parser() {
		
	}

	public String removeFirstWord(String userCommand) {
		return userCommand.replaceFirst(getFirstWord(userCommand), STRING_EMPTY).trim();
	}
	
	public String getFirstWord(String userCommand) {
		return userCommand.trim().split(CHARACTER_SPACE)[0];
	}

	public Task parseAddCommand(String taskInfo) throws Exception {
		Task task = new Task();
		
		if (taskInfo.contains("from") && taskInfo.contains("to")) {	// For events
			String description  = taskInfo.trim().split("from")[0].trim();
			String startTime = taskInfo.trim().split("from")[1].trim().split("to")[0].trim();
			String endTime = taskInfo.trim().split("from")[1].trim().split("to")[1].trim();
			task.setDescription(description);
			task.setStartTime(startTime);
			task.setEndTime(endTime);
		} else if (taskInfo.contains("by")) { // For deadlines
			String description  = taskInfo.trim().split("by")[0].trim();
			String endTime = taskInfo.trim().split("by")[1].trim();
			task.setDescription(description);
			task.setEndTime(endTime);
		} else {
			task.setDescription(taskInfo.trim());
		}
		return task;
	}

	public Task parseEditCommand(String taskInfo) throws Exception {
		Task task = new Task();
		String[] fieldValuePairs = taskInfo.split(",");
		for (int i = 0; i < fieldValuePairs.length; i++) {
			String field = getFirstWord(fieldValuePairs[i]);
			String value = removeFirstWord(fieldValuePairs[i]);
			
			switch(field.toLowerCase()) {
				case "description" :
					task.setDescription(value);
					break;
				case "starttime" :
					task.setStartTime(value);
					break;
				case "endtime" :
					task.setEndTime(value);
					break;
				case "priority" :
					task.setPriority(value);
					break;
				default :
					throw new Exception(MESSAGE_INVALID_FORMAT);
			}
		}
		return task;
	}

}
