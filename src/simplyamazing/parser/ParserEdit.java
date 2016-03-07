package simplyamazing.parser;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String MESSAGE_INVALID_FORMAT = "The command is invalid";
	
	public Task parserEditCommand(Parser parser, String taskInfo) throws Exception {
		Task task = new Task();
		String[] fieldValuePairs = taskInfo.split(",");
		for (int i = 0; i < fieldValuePairs.length; i++) {
			String field = parser.getFirstWord(fieldValuePairs[i]);
			String value = parser.removeFirstWord(fieldValuePairs[i]);
			
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
