//@@author A0112659A
package simplyamazing.parser;

import com.joestelmach.natty.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserAdd {
	private static final String STRING_TIME_FORMATTER = ":";
	private static final String KEYWORD_SCHEDULE_TO = "to";
	private static final String KEYWORD_SCHEDULE_FROM = "from";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String EMPTY_STRING = "";
	private static final String SPECIAL_STRING = "*";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE_FIELDS_NOT_CORRECT = "Error: Please ensure the fields are correct";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID = "Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String ERROR_MESSAGE_START_AFTER_END = "Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT = "Error: Time provided must be after the current time";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";
	private static Date startingDate = null;
	private static Date endingDate = null;
	private static int startTimeIndex;
	private static int endTimeIndex;
	private static int year;
	private boolean checkValue = false, isEvent = false, isDeadline = false, isFloatingTask = false;

	/*
	 * This method is used to parse/analyze a Add command There are three types
	 * of commands: event, deadline and floating task We allow the users to type
	 * the command in our standard format and flexible format we also allow the
	 * users to key in the duplications of keywords: from, to, by
	 */
	public Handler parseAddCommand(Handler handler, String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO, "going to start parse the Add Command");
		checkValue = isAddingValid(handler, taskInfo, logger);
		assert handler != null;
		if (checkValue) {
			if (isEvent) { // For events
				handler.getTask().setDescription(description);
				handler.getTask().setStartTime(startingDate);
				handler.getTask().setEndTime(endingDate);
			}
			if (isDeadline) { // For deadlines
				handler.getTask().setDescription(description);
				handler.getTask().setEndTime(endingDate);
			}
			if (isFloatingTask) {// For floating tasks
				handler.getTask().setDescription(taskInfo.trim());
			}
		} else {
		}
		logger.log(Level.INFO, "return the handler to parser");
		return handler;
	}

	/*
	 * This method is used to check the adding is valid or not we will check the
	 * input contains special string or not like"*" This means the users key in
	 * the duplication of key words or not we will check the date and time
	 * format the user entered is belong to our time format or the Natty's
	 * format
	 */
	public boolean isAddingValid(Handler handler, String taskInfo, Logger logger) throws Exception {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();

		logger.log(Level.INFO, "start to analyze the type of the task we add");
		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {
			if (taskInfo.contains(SPECIAL_STRING)) {
				assert taskInfo != null;
				int specialStrIndex = taskInfo.lastIndexOf(SPECIAL_STRING);
				if (taskInfo.substring(specialStrIndex + 1, specialStrIndex + 5).matches(KEYWORD_SCHEDULE_FROM)) {
					startTimeIndex = specialStrIndex + 1;
				} else {
					startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
				}
			} else {
				assert (startTimeIndex >= 0);
				startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
			}
			String taskInfoFiltered = taskInfo;
			endTimeIndex = taskInfoFiltered.lastIndexOf(KEYWORD_SCHEDULE_TO);

			while (!taskInfoFiltered.substring((endTimeIndex + 2), (endTimeIndex + 3)).trim().equals(EMPTY_STRING)) {
				taskInfoFiltered = taskInfoFiltered.substring(0, endTimeIndex);
				endTimeIndex = taskInfoFiltered.lastIndexOf(KEYWORD_SCHEDULE_TO);
			}

			logger.log(Level.INFO, "Start to compare the index of \"from and \"to");
			if (startTimeIndex < endTimeIndex) {

				startTime = Parser.removeFirstWord(taskInfo.substring(startTimeIndex, endTimeIndex).trim());
				endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex).trim());
				description = taskInfo.substring(0, startTimeIndex);
				description = description.replace(SPECIAL_STRING, EMPTY_STRING);

				if (description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING)
						|| endTime.equals(EMPTY_STRING)) {
					logger.log(Level.WARNING, "the field added is not correct");
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
					return false;
				} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
					boolean isStartFormatCorrect = followStandardFormat(startTime, logger);
					boolean isEndFormatCorrect = followStandardFormat(endTime, logger);

					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
					sdf.setLenient(false);
					if (isStartFormatCorrect == false && isEndFormatCorrect == false) {
						logger.log(Level.INFO, "Endtime and Startime both use Natty format");
						List<DateGroup> dateGroup1 = dateParser.parse(startTime);
						List<DateGroup> dateGroup2 = dateParser.parse(endTime);

						if (dateGroup1.isEmpty() || dateGroup2.isEmpty()) {
							logger.log(Level.WARNING, "The input time format is wrong");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<Date> date1 = dateGroup1.get(0).getDates();
						startingDate = date1.get(0);
						List<Date> date2 = dateGroup2.get(0).getDates();
						endingDate = date2.get(0);
					} else if (isStartFormatCorrect == true && isEndFormatCorrect == true) {
						logger.log(Level.INFO, "Endtime and Startime both use our format");
						try {
							startingDate = (Date) sdf.parse(startTime);
							endingDate = (Date) sdf.parse(endTime);
						} catch (ParseException e) {
							logger.log(Level.WARNING, "Endtime and Startime are invalid for our format");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
					} else if (isStartFormatCorrect == true && isEndFormatCorrect == false) {
						logger.log(Level.INFO, "Startime use our format, Endtime use Natty format");
						try {
							startingDate = (Date) sdf.parse(startTime);
						} catch (ParseException e) {
							logger.log(Level.WARNING, "StartTime is invalid in our time format");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<DateGroup> dateGroup2 = dateParser.parse(endTime);

						if (dateGroup2.isEmpty()) {
							logger.log(Level.WARNING, "EndTime is invalid in the Natty format");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<Date> date2 = dateGroup2.get(0).getDates();
						endingDate = date2.get(0);
					} else if (isStartFormatCorrect == false && isEndFormatCorrect == true) {
						logger.log(Level.INFO, "Startime use Natty format, Endtime use our format");
						try {
							endingDate = (Date) sdf.parse(endTime);
						} catch (ParseException e) {
							logger.log(Level.WARNING, "EndTime is invalid in our time format");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<DateGroup> dateGroup2 = dateParser.parse(startTime);

						if (dateGroup2.isEmpty()) {
							logger.log(Level.WARNING, "StartTime is invalid in the Natty format");
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<Date> date2 = dateGroup2.get(0).getDates();
						startingDate = date2.get(0);
					}

					Date todayDate = null;
					todayDate = (Date) sdf.parse(sdf.format(new Date()));
					logger.log(Level.INFO, "start to compare the startingDate and endingDate");
					if (startingDate.after(endingDate) || startingDate.compareTo(endingDate) == 0) {
						logger.log(Level.WARNING, "startingDate is greater than or equal to the endingDate");
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
						return false;
					} else if (!startingDate.after(todayDate) || !endingDate.after(todayDate)) {
						logger.log(Level.WARNING, "startingDate or endingDate is before the Today's date");
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
						return false;
					} else {
						logger.log(Level.INFO, "this is an event task");
						isEvent = true;
						return true;
					}
				}
			} else if (taskInfo.contains(KEYWORD_DEADLINE)) {
				endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
				startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
				if (endTimeIndex < startTimeIndex) {
					logger.log(Level.INFO, "This is not an event but floating task");
					isFloatingTask = true;
					return true;
				}
			}
		}
		if (taskInfo.contains(KEYWORD_DEADLINE)) {
			logger.log(Level.INFO, "start to process the deadline tasks");
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
			description = taskInfo.substring(0, endTimeIndex).trim();

			if (description.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				logger.log(Level.WARNING, "the fields entered are not correct");
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
				return false;
			} else if (!endTime.equals(EMPTY_STRING)) {
				boolean isEndFormatCorrect = followStandardFormat(endTime, logger);

				SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
				sdf.setLenient(false);

				if (isEndFormatCorrect == false) {
					logger.log(Level.INFO, "Endtime use Natty");
					List<DateGroup> dateGroup3 = dateParser.parse(endTime);

					if (dateGroup3.isEmpty()) {
						logger.log(Level.WARNING, "Endtime is invalid in the Natty format");
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						return false;
					}
					List<Date> date3 = dateGroup3.get(0).getDates();
					endingDate = date3.get(0);

				} else if (isEndFormatCorrect == true) {
					logger.log(Level.INFO, "Endtime use our time format");
					try {
						logger.log(Level.INFO, "start to parse the endTime in our time format");
						endingDate = (Date) sdf.parse(endTime);
					} catch (ParseException e) {
						logger.log(Level.WARNING, "Endtime is invalid in our time format");
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						return false;
					}
				}

				Date todayDate = null;
				todayDate = (Date) sdf.parse(sdf.format(new Date()));

				if (!endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
					return false;
				}
			}
			isDeadline = true;
			return true;
		} else {
			if (taskInfo.contains(STRING_TIME_FORMATTER)) {
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
		isFloatingTask = true;
		return true;
	}

	/*
	 * This method is used to check the input date which follows our time format
	 * or not There are four parts to check: time, date, month and year We use
	 * it differentiate the input from our time format and the Natty's format
	 */
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