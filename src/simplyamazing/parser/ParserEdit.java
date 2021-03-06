//@@author A0112659A
package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.joestelmach.natty.*;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String PRIORITY = "priority";
	private static final String END = "end";
	private static final String START = "start";
	private static final String DESCRIPTION = "description";
	private static final String ERROR_MESSAGE_INVALID_INDEX = "Error: Index provided is not an Integer.";
	private static final String ERROR_MESSAGE_INVALID_FIELD = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String ERROR_MESSAGE_START_AFTER_END = "Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT = "Error: Time provided must be after the current time";
	private static final String ERROR_MESSAGE_PRIORITY_LEVEL = "Error: Priority level can be only high, medium, low or none.";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID = "Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String ERROR_MESSAGE_NO_END_TIME = "Error: Unable to allocate a start time when the task has no end time";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static Date startingDate = null;
	private static Date endingDate = null;
	private static int year;

	/*
	 * This method is used to parse/analyze a edit command There are three types
	 * of commands: event, deadline and floating task There are four fields for
	 * the edit command: description, start, end and priority We also allow
	 */
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex, Logger logger)
			throws Exception {
		logger.log(Level.INFO, "start to check the Index of the Edit command");
		if (isInteger(taskIndex, logger)) {
			handler.setIndex(taskIndex);
		} else {
			logger.log(Level.WARNING, "the index of the command is invalid");
			handler.setHasError(true);
			handler.setFeedBack(ERROR_MESSAGE_INVALID_INDEX);
			return handler;
		}
		String[] fieldValuePairs = taskInfoWithoutIndex.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
		assert sdf != null;
		sdf.setLenient(false);

		for (int i = 0; i < fieldValuePairs.length; i++) {
			String field = Parser.getFirstWord(fieldValuePairs[i]);
			String value = Parser.removeFirstWord(fieldValuePairs[i]);

			switch (field.toLowerCase()) {
			case DESCRIPTION:
				handler.getTask().setDescription(value);
				break;
			case START:
				if (value.toLowerCase().equals("none")) {
					handler.getTask().setStartTime(Task.DEFAULT_DATE_VALUE_FOR_NULL);
				} else {
					boolean isStartFormatCorrect = followStandardFormat(value, logger);
					handler = editStartTime(sdf, isStartFormatCorrect, handler, value, logger);
				}
				break;
			case END:
				if (value.toLowerCase().equals("none")) {
					handler.getTask().setEndTime(Task.DEFAULT_DATE_VALUE_FOR_NULL);
				} else {
					boolean isEndFormatCorrect = followStandardFormat(value, logger);
					handler = editEndTime(sdf, isEndFormatCorrect, handler, value, logger);
				}
				break;
			case PRIORITY:
				logger.log(Level.INFO, "start to parse the priority");
				try {
					handler.getTask().setPriority(value);
				} catch (Exception e) {
					logger.log(Level.WARNING, "the priority level entered is invalid");
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_PRIORITY_LEVEL);
				}
				break;
			default:
				logger.log(Level.WARNING, "the field of command is invalid");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_INVALID_FIELD);
			}
		}

		startingDate = handler.getTask().getStartTime();
		endingDate = handler.getTask().getEndTime();
		Date todayDate = new Date();
		assert todayDate != null;

		if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE) != 0
				&& endingDate.compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0
					&& endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0) {
				if (!startingDate.after(todayDate) || !endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				} else if (!endingDate.after(startingDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) == 0
					&& endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0) {
				if (!endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0
					&& endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) == 0) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_NO_END_TIME);
			}
		} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0 && !startingDate.after(todayDate)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
			}

		} else if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE) != 0) {
			if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL) != 0 && !endingDate.after(todayDate)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
			}
		}
		return handler;
	}

	private Handler editStartTime(SimpleDateFormat sdf, boolean isStartFormatCorrect, Handler handler, String value,
			Logger logger) {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
		if (isStartFormatCorrect == true) {
			logger.log(Level.INFO, "startTime use our format");
			try {
				startingDate = (Date) sdf.parse(value);
			} catch (ParseException e) {
				logger.log(Level.WARNING, "startTime is invalid in our time format");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
		} else {
			logger.log(Level.INFO, "startTime use Natty");
			List<DateGroup> dateGroup3 = dateParser.parse(value);

			if (dateGroup3.isEmpty()) {
				logger.log(Level.WARNING, "startTime is invalid in the Natty format");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
				return handler;
			}
			List<Date> date3 = dateGroup3.get(0).getDates();
			startingDate = date3.get(0);
			assert startingDate != null;
		}
		handler.getTask().setStartTime(startingDate);
		return handler;
	}

	private Handler editEndTime(SimpleDateFormat sdf, boolean isEndFormatCorrect, Handler handler, String value,
			Logger logger) {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
		if (isEndFormatCorrect == true) {
			logger.log(Level.INFO, "endTime use our format");
			try {
				endingDate = (Date) sdf.parse(value);
			} catch (ParseException e) {
				logger.log(Level.WARNING, "endTime is invalid in our time format");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
		} else {
			logger.log(Level.INFO, "endTime use Natty");
			List<DateGroup> dateGroup4 = dateParser.parse(value);

			if (dateGroup4.isEmpty()) {
				logger.log(Level.WARNING, "endTime is invalid in the Natty format");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
				return handler;
			}
			List<Date> date4 = dateGroup4.get(0).getDates();
			endingDate = date4.get(0);
		}
		handler.getTask().setEndTime(endingDate);
		return handler;
	}

	/*
	 * This method is used to test the validity of the index.
	 */
	public static boolean isInteger(String taskInfo, Logger logger) {
		logger.log(Level.INFO, "start to analyze the index");
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "the index is not an Integer");
			return false;
		}
		return true;
	}

	public boolean followStandardFormat(String dateTimeString, Logger logger) {
		String[] dateTimeArr = dateTimeString.trim().split(" ");

		if (dateTimeArr.length != 4) {
			return false;
		} else {
			// dateTimeArr len should be 4 here, now check end time
			String time = dateTimeArr[0];
			if ((time.contains(":") && (time.length() == 4 || time.length() == 5))) {

				int date;
				logger.log(Level.INFO, "start to process the date");
				try {
					date = Integer.parseInt(dateTimeArr[1], 10);
				} catch (NumberFormatException e) {
					logger.log(Level.WARNING, "the format for the date is invalid");
					return false;
				}
				// reach here means that it time given follows format and date
				// given is an integer

				String givenMonth = dateTimeArr[2].toLowerCase();
				if (!(givenMonth.contains("jan") || givenMonth.contains("feb") || givenMonth.contains("mar")
						|| givenMonth.contains("apr") || givenMonth.contains("may") || givenMonth.contains("jun")
						|| givenMonth.contains("jul") || givenMonth.contains("aug") || givenMonth.contains("sep")
						|| givenMonth.contains("oct") || givenMonth.contains("nov") || givenMonth.contains("dec"))) {
					// month given follows the required format
					return false;
				} else {
					logger.log(Level.INFO, "start to process the year");
					try {
						year = Integer.parseInt(dateTimeArr[3], 10);
					} catch (NumberFormatException e) {
						logger.log(Level.WARNING, "the format for the year is invalid");
						return false;
					}
				}
			} else {
				return false;
			}
			return true;
		}
	}
}
