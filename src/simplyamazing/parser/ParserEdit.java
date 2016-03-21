package simplyamazing.parser;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserEdit {
	private static final String MESSAGE_INVALID_FORMAT = "The command of input's field is invalid";
	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	
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
				case "description":
					handler.getTask().setDescription(value);
					break;
				case "starttime":
					handler.getTask().setStartTime(value);
					break;
				case "endtime":
					handler.getTask().setEndTime(value);
					break;
				case "priority":
					handler.getTask().setPriority(value);
					break;
				default:
					handler.setHasError(true);
					handler.setFeedBack(MESSAGE_INVALID_FORMAT);
				}
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
			sdf.setLenient(true);
			Date startingDate = handler.getTask().getStartTime();
			Date endingDate = handler.getTask().getEndTime();
			Date todayDate = sdf.parse(sdf.format(new Date()));

			if (startingDate.after(endingDate)) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			} else if (startingDate.before(todayDate) || startingDate.before(todayDate)) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			}
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
