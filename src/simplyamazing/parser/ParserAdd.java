package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserAdd {
	private static final String STRING_TIME_FORMATTER = ":";
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String EMPTY_STRING = "";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE_FIELDS_NOT_CORRECT = "Error: Please ensure the fields are correct";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String ERROR_MESSAGE_START_AFTER_END ="Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";
	private static int startTimeIndex;
	private static int endTimeIndex;

	private boolean checkValue;

	private static Logger logger = Logger.getLogger("ParserAdd");
	
	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {
		//logger.log(Level.INFO, "going to start processing cmd");
		checkValue = isAddingValid(handler,taskInfo);
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
		}
		//logger.log(Level.INFO, "returning to parser");
		return handler;
	}

	public boolean isAddingValid(Handler handler,String taskInfo) throws Exception {
		
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
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
				return false;
			} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
					sdf.setLenient(true);
					Date startingDate = null;
					startingDate = (Date)sdf.parse(startTime);
					Date endingDate = null;
					endingDate = (Date)sdf.parse(endTime);
					Date todayDate = null;
					todayDate = (Date)sdf.parse(sdf.format(new Date()));

					if (startingDate.after(endingDate)||startingDate.compareTo(endingDate) == 0) {
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
						return false;
					} else if (!startingDate.after(todayDate) || !endingDate.after(todayDate)) {
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
						return false;
					} else {
						return true;
					}
				} catch (ParseException e) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
					return false;
				}
			}
		} else if (taskInfo.contains(KEYWORD_DEADLINE) && taskInfo.contains(STRING_TIME_FORMATTER)) {
			System.out.println("Found "+KEYWORD_DEADLINE+", "+STRING_TIME_FORMATTER);
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
			description = taskInfo.substring(0, endTimeIndex).trim();

			if (description.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
				return false;
			} else if (!endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
					sdf.setLenient(true);
					Date endingDate = null;
					endingDate = (Date)sdf.parse(endTime);
					Date todayDate = null;
					todayDate = (Date)sdf.parse(sdf.format(new Date()));
					if (!endingDate.after(todayDate)) {
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
						return false;
					}
				} catch (ParseException e) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
					return false;
				}
			}
		} else {
			if(taskInfo.contains(STRING_TIME_FORMATTER)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
				return false;
			} else {
				if (taskInfo.trim().equals(EMPTY_STRING)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
					return false;
				}
			}
		}
		return true;
	}
}