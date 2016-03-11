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
			//description = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[0].trim();
			//startTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[0].trim();
			int startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
			int endTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_TO);
			startTime = Parser.removeFirstWord(taskInfo.substring(startTimeIndex, endTimeIndex).trim());
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex).trim());
			description = taskInfo.substring(0, startTimeIndex);
			System.out.println(startTime);
			System.out.println(endTime);
			System.out.println(description);
			
			
			//System.out.println(taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[2].trim());
			
			//endTime = taskInfo.trim().split(KEYWORD_SCHEDULE_FROM)[1].trim().split(KEYWORD_SCHEDULE_TO)[1].trim();

			if (description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				System.out.println("empty string");
				return false;
			} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
					sdf.setLenient(false);
					Date startingDate = sdf.parse(startTime);
					Date endingDate = sdf.parse(endTime);
					Date todayDate = sdf.parse(sdf.format(new Date()));

					if (startingDate.after(endingDate)) {
						System.out.println("start after end");
						return false;
					} else if (startingDate.before(todayDate) || startingDate.before(todayDate)) {
						System.out.println("already expired");
						return false;
					}

					return true;
				} catch (ParseException e) {
					System.out.println("Date parse exception");
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
