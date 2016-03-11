package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserAdd {
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String EMPTY_STRING = "";
	private static final String TIME_FORMAT = "H:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE = "the add command is not correct";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";

	private boolean checkValue;

	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {
		checkValue = isAddingValid(taskInfo);
		if (checkValue) {

			if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) { // For
																										// events
				String description = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
				String startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0]
						.trim();
				String endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1]
						.trim();
				handler.getTask().setDescription(description);
				handler.getTask().setStartTime(startTime);
				handler.getTask().setEndTime(endTime);
			} else if (taskInfo.contains(KEYWORD_DEADLINE)) { // For deadlines
				String description = taskInfo.trim().split(KEYWORD_DEADLINE)[0].trim();
				String endTime = taskInfo.trim().split(KEYWORD_DEADLINE)[1].trim();
				handler.getTask().setDescription(description);
				handler.getTask().setEndTime(endTime);
			} else {
				handler.getTask().setDescription(taskInfo.trim());
			}
		} else {
			handler.setHasError(true);
			handler.setFeedBack(ERROR_MESSAGE);
		}
		return handler;
	}

	public boolean isAddingValid(String taskInfo) throws Exception {
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {
			description = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
			startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0].trim();
			endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1].trim();

			if (description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				return false;
			} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(false);
					Date startingDate = sdf.parse(startTime);
					Date endingDate = sdf.parse(endTime);
					Date todayDate = sdf.parse(sdf.format(new Date()));

					if (startingDate.after(endingDate)) {
						return false;
					} else if (startingDate.after(endingDate) || startingDate.before(todayDate)) {
						return false;
					}

					return true;
				} catch (ParseException e) {
					return false;
				}
			}
		} else if (taskInfo.contains(KEYWORD_DEADLINE)) {
			description = taskInfo.trim().split(KEYWORD_DEADLINE)[0].trim();
			endTime = taskInfo.trim().split(KEYWORD_DEADLINE)[1].trim();

			if (description.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				return false;
			} else if (!endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(false);
					Date endingDate = sdf.parse(endTime);
					Date todayDate = sdf.parse(sdf.format(new Date()));
					if (endingDate.before(todayDate)) {
						return false;
					}
				} catch (ParseException e) {
					return false;
				}
			}
		} else {
			if (taskInfo.equals(EMPTY_STRING)) {
				return false;
			}
		}
		return true;
	}
}
