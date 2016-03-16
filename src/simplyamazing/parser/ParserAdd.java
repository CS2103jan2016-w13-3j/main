package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	private static int startTimeIndex;
	private static int endTimeIndex;

	private boolean checkValue;

	private static Logger logger = Logger.getLogger("ParserAdd");
	
	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {
		logger.log(Level.INFO, "going to start processing cmd");
		checkValue = isAddingValid(taskInfo);
		if (checkValue) {

			if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) { // For
																										// events
				startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
				endTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_TO);
				startTime = Parser.removeFirstWord(taskInfo.substring(startTimeIndex, endTimeIndex).trim());
				endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex).trim());
				description = taskInfo.substring(0, startTimeIndex);

				handler.getTask().setDescription(description);
				handler.getTask().setStartTime(startTime);
				handler.getTask().setEndTime(endTime);
			} else if (taskInfo.contains(KEYWORD_DEADLINE)) { // For deadlines
				endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
				endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
				description = taskInfo.substring(0, endTimeIndex);

				handler.getTask().setDescription(description);
				handler.getTask().setEndTime(endTime);
			} else {
				handler.getTask().setDescription(taskInfo.trim());
			}
		} else {
			handler.setHasError(true);
			logger.log(Level.WARNING, "error with the given input");
			handler.setFeedBack(ERROR_MESSAGE);
		}
		logger.log(Level.INFO, "returning to parser");
		return handler;
	}

	public boolean isAddingValid(String taskInfo) throws Exception {
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {
			startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_TO);
			startTime = Parser.removeFirstWord(taskInfo.substring(startTimeIndex, endTimeIndex).trim());
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex).trim());
			description = taskInfo.substring(0, startTimeIndex);

			if (description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				return false;
			} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(true);
					Date startingDate = sdf.parse(startTime);
					Date endingDate = sdf.parse(endTime);
					Date todayDate = sdf.parse(sdf.format(new Date()));

					if (startingDate.after(endingDate)) {
						return false;
					} else if (startingDate.before(todayDate) || startingDate.before(todayDate)) {
						return false;
					}

					return true;
				} catch (ParseException e) {
					return false;
				}
			}
		} else if (taskInfo.contains(KEYWORD_DEADLINE)) {
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
			description = taskInfo.substring(0, endTimeIndex);

			if (description.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				return false;
			} else if (!endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(true);
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
