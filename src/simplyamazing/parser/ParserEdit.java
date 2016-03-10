package simplyamazing.parser;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String MESSAGE_INVALID_FORMAT = "The command of input's field is invalid";
	
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex) throws Exception {
		handler.setIndex(taskIndex);
		String[] fieldValuePairs = taskInfoWithoutIndex.split(",");
		for (int i = 0; i < fieldValuePairs.length; i++) {
			String field = Parser.getFirstWord(fieldValuePairs[i]);
			String value = Parser.removeFirstWord(fieldValuePairs[i]);
			
			switch(field.toLowerCase()) {
				case "description" :
					handler.getTask().setDescription(value);
					break;
				case "starttime" :
					handler.getTask().setStartTime(value);
					break;
				case "endtime" :
					handler.getTask().setEndTime(value);
					break;
				case "priority" :
					handler.getTask().setPriority(value);
					break;
				default :
					handler.setHasError(true);
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			}
		}
		return handler;
	}
}
