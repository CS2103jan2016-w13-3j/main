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
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String ERROR_MESSAGE_START_AFTER_END ="Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	private static String description = "";
	private static String startTime = "";
	private static String endTime = "";
	private static Date startingDate = null;
	private static Date endingDate = null;
	private static int startTimeIndex;
	private static int endTimeIndex;
	private static int year;
	private boolean checkValue = false, isEvent = false, isDeadline = false, isFloatingTask = false;

	private static Logger logger = Logger.getLogger("ParserAdd");

	public Handler parseAddCommand(Handler handler, String taskInfo) throws Exception {
		//logger.log(Level.INFO, "going to start processing cmd");
		checkValue = isAddingValid(handler,taskInfo);
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
			if (isFloatingTask) {
				handler.getTask().setDescription(taskInfo.trim());
			}
		} else {
		}
		//logger.log(Level.INFO, "returning to parser");
		return handler;
	}

	public boolean isAddingValid(Handler handler,String taskInfo) throws Exception {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();

		if (taskInfo.contains(KEYWORD_SCHEDULE_FROM) && taskInfo.contains(KEYWORD_SCHEDULE_TO)) {
			System.out.println("Found "+KEYWORD_SCHEDULE_FROM+", "+KEYWORD_SCHEDULE_TO);

			if (taskInfo.contains(SPECIAL_STRING)) {
				startTimeIndex = taskInfo.lastIndexOf(SPECIAL_STRING)+1;
			} else {
				startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
			}
			String taskInfoFiltered = taskInfo;
			endTimeIndex = taskInfoFiltered.lastIndexOf(KEYWORD_SCHEDULE_TO);

			while(!taskInfoFiltered.substring((endTimeIndex+2),(endTimeIndex+3)).trim().equals(EMPTY_STRING)) {
				taskInfoFiltered = taskInfoFiltered.substring(0, endTimeIndex) ;
				endTimeIndex = taskInfoFiltered.lastIndexOf(KEYWORD_SCHEDULE_TO);
			}

			System.out.println(startTimeIndex);
			System.out.println(endTimeIndex);
			if (startTimeIndex < endTimeIndex) {

				startTime = Parser.removeFirstWord(taskInfo.substring(startTimeIndex, endTimeIndex).trim());
				endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex).trim());
				description = taskInfo.substring(0, startTimeIndex);
				description = description.replace(SPECIAL_STRING, EMPTY_STRING);
				System.out.println("StartTime:"+ startTime);
				System.out.println("EndTime:" + endTime);

				if (description.equals(EMPTY_STRING) || startTime.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
					return false;
				} else if (!startTime.equals(EMPTY_STRING) && !endTime.equals(EMPTY_STRING)) {
					boolean isStartFormatCorrect = followStandardFormat(startTime);
					boolean isEndFormatCorrect = followStandardFormat(endTime);

					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
					sdf.setLenient(false);
					if(isStartFormatCorrect == false && isEndFormatCorrect == false){
						System.out.println("Endtime and Startime both use Natty format");					
						List<DateGroup> dateGroup1 = dateParser.parse(startTime);
						List<DateGroup> dateGroup2 = dateParser.parse(endTime);

						if(dateGroup1.isEmpty()||dateGroup2.isEmpty() ){
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}						
						List<Date> date1 = dateGroup1.get(0).getDates();
						startingDate = date1.get(0);
						System.out.println(startingDate);
						List<Date> date2 = dateGroup2.get(0).getDates();
						endingDate = date2.get(0);
						System.out.println(endingDate);
					}else if (isStartFormatCorrect == true && isEndFormatCorrect == true){
						System.out.println("Endtime and Startime both use our format");
						try{
							startingDate = (Date)sdf.parse(startTime);
							endingDate = (Date)sdf.parse(endTime);
						}catch (ParseException e) {
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
					}else if (isStartFormatCorrect == true && isEndFormatCorrect == false){
						System.out.println("Startime use our format, Endtime use Natty format");
						try{
							startingDate = (Date)sdf.parse(startTime);
						}catch (ParseException e) {
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<DateGroup> dateGroup2 = dateParser.parse(endTime);

						if(dateGroup2.isEmpty()){
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}						
						List<Date> date2 = dateGroup2.get(0).getDates();
						endingDate = date2.get(0);
					}else if(isStartFormatCorrect == false && isEndFormatCorrect == true){
						System.out.println("Startime use Natty format, Endtime use our format");
						try{
							endingDate = (Date)sdf.parse(endTime);
						}catch (ParseException e) {
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}
						List<DateGroup> dateGroup2 = dateParser.parse(startTime);

						if(dateGroup2.isEmpty()){
							handler.setHasError(true);
							handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
							return false;
						}						
						List<Date> date2 = dateGroup2.get(0).getDates();
						startingDate = date2.get(0);
					}



					/*
					List<DateGroup> dateGroup1 = dateParser.parse(startTime);
					List<DateGroup> dateGroup2 = dateParser.parse(endTime);

					if(dateGroup1.isEmpty()||dateGroup2.isEmpty()){
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						return false;
					}
						SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
						sdf.setLenient(true);
						/*Date startingDate = null;
					startingDate = (Date)sdf.parse(startTime);
					Date endingDate = null;
					endingDate = (Date)sdf.parse(endTime); //
						List<Date> date1 = dateGroup1.get(0).getDates();
						startingDate = date1.get(0);
						List<Date> date2 = dateGroup2.get(0).getDates();
						endingDate = date2.get(0);
					 */
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
						isEvent = true;
						return true;
					}
				}
			} else if(taskInfo.contains(KEYWORD_DEADLINE)){
				endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
				startTimeIndex = taskInfo.lastIndexOf(KEYWORD_SCHEDULE_FROM);
				System.out.println(startTimeIndex);
				System.out.println(endTimeIndex);
				if(endTimeIndex < startTimeIndex){
					System.out.println("This is not an event");
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
					return false;
				}
			}
		}
		if (taskInfo.contains(KEYWORD_DEADLINE)) {
			System.out.println("Found "+KEYWORD_DEADLINE+", "+STRING_TIME_FORMATTER);
			System.out.println("This is a deadline task");
			endTimeIndex = taskInfo.lastIndexOf(KEYWORD_DEADLINE);
			endTime = Parser.removeFirstWord(taskInfo.substring(endTimeIndex));
			description = taskInfo.substring(0, endTimeIndex).trim();

			if (description.equals(EMPTY_STRING) || endTime.equals(EMPTY_STRING)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_FIELDS_NOT_CORRECT);
				return false;
			} else if (!endTime.equals(EMPTY_STRING)) {
				boolean isEndFormatCorrect = followStandardFormat(endTime);

				SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
				sdf.setLenient(false);

				if(isEndFormatCorrect == false){
					System.out.println("Endtime use Natty");
					List<DateGroup> dateGroup3 = dateParser.parse(endTime);

					if(dateGroup3.isEmpty()){
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						return false;
					}
					List<Date> date3 = dateGroup3.get(0).getDates();
					endingDate = date3.get(0);
				}else if (isEndFormatCorrect == true){
					System.out.println("Endtime use our format");
					try{
						endingDate = (Date)sdf.parse(endTime);
					}catch (ParseException e) {
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						return false;
					}
				}

				Date todayDate = null;
				todayDate = (Date)sdf.parse(sdf.format(new Date()));

				/*
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
					sdf.setLenient(true);
					//Date endingDate = null;
					endingDate = (Date)sdf.parse(endTime);//
					Date todayDate = null;
					todayDate = (Date)sdf.parse(sdf.format(new Date()));
					List<Date> date3 = dateGroup3.get(0).getDates();
					endingDate = date3.get(0);
				 */

				if (!endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
					return false;
				} 			
			}
			isDeadline = true;
			return true;
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
		isFloatingTask = true;
		return true;
	}



	public boolean followStandardFormat(String dateTimeString){
		String[] dateTimeArr = dateTimeString.trim().split(" ");

		if (dateTimeArr.length != 4) {
			return false;
		} else {
			// dateTimeArr len should be 4 here, now check end time
			String time = dateTimeArr[0];
			if ((time.contains(":") && (time.length() == 4 || time.length()== 5))) {

				int date;
				try{
					date = Integer.parseInt(dateTimeArr[1], 10);
				} catch (NumberFormatException e){
					return false;
				}	
				// reach here means that it time given follows format and date given is an int

				String givenMonth = dateTimeArr[2].toLowerCase();
				if ( !(givenMonth.contains("jan") || givenMonth.contains("feb") || givenMonth.contains("mar") ||givenMonth.contains("apr")
						|| givenMonth.contains("may") || givenMonth.contains("jun") || givenMonth.contains("jul") || givenMonth.contains("aug")
						|| givenMonth.contains("sep") || givenMonth.contains("oct") || givenMonth.contains("nov") || givenMonth.contains("dec"))) {
					// month given follows the required format
					return false;
				} else {
					try {
						year = Integer.parseInt(dateTimeArr[3], 10);
					} catch (NumberFormatException e) {
						return false; 	// year not in int format
					}
				}			
			} else {
				return false;
			}
			return true;
		}
	}
}