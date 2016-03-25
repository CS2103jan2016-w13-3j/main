package simplyamazing.parser;

import java.util.Date;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String MESSAGE_INVALID_FORMAT = "The command of input's field is invalid";
	
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex) throws Exception {
		if (isInteger(taskIndex)) {
			handler.setIndex(taskIndex);
		} else {
			handler.setHasError(true);
			handler.setFeedBack(MESSAGE_INVALID_FORMAT);
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
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
				}
			}
			
			Date startingDate = handler.getTask().getStartTime();
			Date endingDate = handler.getTask().getEndTime();
			Date todayDate = new Date();

			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // if both start time and end time are modified
				if (startingDate.before(todayDate) || endingDate.before(todayDate) || startingDate.after(endingDate)|| startingDate.compareTo(endingDate) == 0) {
					handler.setHasError(true);
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // start time is modified
				if (startingDate.before(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
				}
			} else if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // end time is modified
				if (endingDate.before(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
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
