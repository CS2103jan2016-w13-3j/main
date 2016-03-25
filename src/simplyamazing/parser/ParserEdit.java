package simplyamazing.parser;

import java.util.Date;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String ERROR_MESSAGE_INVALID_INDEX = "Error: Index provided is not an Integer.";
	private final String ERROR_MESSAGE_INVALID_FIELD = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String ERROR_MESSAGE_START_AFTER_END ="Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex) throws Exception {
		if (isInteger(taskIndex)) {
			handler.setIndex(taskIndex);
		} else {
			handler.setHasError(true);
			handler.setFeedBack(ERROR_MESSAGE_INVALID_INDEX);
			return handler;
		}
		String[] fieldValuePairs = taskInfoWithoutIndex.split(",");
		
			for (int i = 0; i < fieldValuePairs.length; i++) {
				String field = Parser.getFirstWord(fieldValuePairs[i]);
				String value = Parser.removeFirstWord(fieldValuePairs[i]);

				switch (field.toLowerCase()) {
				case "description" :
					handler.getTask().setDescription(value);
					break;
				case "start" :
					handler.getTask().setStartTime(value);
					break;
				case "end" :
					handler.getTask().setEndTime(value);
					break;
				case "priority" :
					handler.getTask().setPriority(value);
					break;
				default :
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_INVALID_FIELD);
				}
			}
			
			Date startingDate = handler.getTask().getStartTime();
			Date endingDate = handler.getTask().getEndTime();
			Date todayDate = new Date();

			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // if both start time and end time are modified
				if (!startingDate.after(todayDate) || !endingDate.after(todayDate)){ 
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}else if (!endingDate.after(startingDate)){
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // start time is modified
				if (!startingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			} else if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // end time is modified
				if (!endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			}  
			/*if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && startingDate.after(endingDate)) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			} else if ((startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && startingDate.before(todayDate)) || (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && endingDate.before(todayDate))) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			}*/
		return handler;
	}

	public static boolean isInteger(String taskInfo) {
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
