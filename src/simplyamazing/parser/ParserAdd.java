package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserAdd {
	private static final String STRING_TIME_FORMATTER = ":";
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String EMPTY_STRING = "";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE = "the add command is not correct";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";
	private static int startTimeIndex;
	private static int endTimeIndex;

	private boolean checkValue;

	private static Logger logger = Logger.getLogger("ParserAdd");
	
	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {
		//logger.log(Level.INFO, "going to start processing cmd");
		checkValue = isAddingValid(taskInfo);
		if (checkValue) {
			if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO) && taskInfo.contains(STRING_TIME_FORMATTER)) { // For events
				handler.getTask().setDescription(description);
				handler.getTask().setStartTime(startTime);
				handler.getTask().setEndTime(endTime);
			} else if (taskInfo.contains(KEYWORD_DEADLINE) && taskInfo.contains(STRING_TIME_FORMATTER)) { // For deadlines
				handler.getTask().setDescription(description);
				handler.getTask().setEndTime(endTime);
			} else {
				handler.getTask().setDescription(taskInfo.trim());
			}
		} else {
			handler.setHasError(true);
			//logger.log(Level.WARNING, "error with the given input");
			handler.setFeedBack(ERROR_MESSAGE);
		}
		//logger.log(Level.INFO, "returning to parser");
		return handler;
	}

	public boolean isAddingValid(String taskInfo) throws Exception {
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO) && taskInfo.contains(STRING_TIME_FORMATTER)) {
			System.out.println("Found "+KEYWORD_SCHEDULE_FROM+", "+KEYWORD_SCHEDULE_TO+","+STRING_TIME_FORMATTER);
			startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_TO);
			if (startTimeIndex > endTimeIndex) {
				return false;
			}
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

					if (startingDate.after(endingDate)||startingDate.compareTo(endingDate) == 0) {
						return false;
					} else if (startingDate.before(todayDate) || startingDate.before(todayDate)) {
						return false;
					} else {
						return true;
					}
				} catch (ParseException e) {
					return false;
				}
			}
		} else if (taskInfo.contains(KEYWORD_DEADLINE) && taskInfo.contains(STRING_TIME_FORMATTER)) {
			System.out.println("Found "+KEYWORD_DEADLINE+", "+STRING_TIME_FORMATTER);
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
			description = taskInfo.substring(0, endTimeIndex).trim();

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
			if(taskInfo.contains(STRING_TIME_FORMATTER)) {
				System.out.println("Found "+STRING_TIME_FORMATTER);
				return false;
			} else {
				if (taskInfo.equals(EMPTY_STRING)) {
					return false;
				}
			}
		}
		return true;
	}
}