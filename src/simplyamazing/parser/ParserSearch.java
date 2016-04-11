//@@author A0112659A
package simplyamazing.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.joestelmach.natty.DateGroup;

public class ParserSearch {
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String SPACE = " ";
	private static final String EMPTY_STRING = "";
	private static Date endingDate = null;
	private static int year; 
	private static String outputYear = "";
	private boolean checkValue;

	public Handler parserSearchCommand(Handler handler, String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO,"start to parse the search command");
		checkValue = isSearchingKeyWord(handler,taskInfo,logger);
		assert handler != null;
		if (checkValue == true) {
			handler.setKeyWord(taskInfo);
		}
		return handler;
	}
	public boolean isSearchingKeyWord(Handler handler,String taskInfo, Logger logger) throws Exception {
		logger.log(Level.INFO,"start to parse the searching keyword");
		if (taskInfo.equals(EMPTY_STRING)) {
			handler.setKeyWord(taskInfo);
			return false;
		} else {
			String givenMonth = taskInfo.substring(0, 3).toLowerCase();
			String[] monthAndYear = taskInfo.split(SPACE);
			if (monthAndYear.length == 2) {
				outputYear = ""+ taskInfo.split(SPACE)[1];
				if (givenMonth.contains("jan") || givenMonth.contains("feb") || givenMonth.contains("mar") ||givenMonth.contains("apr")
						|| givenMonth.contains("may") || givenMonth.contains("jun") || givenMonth.contains("jul") || givenMonth.contains("aug")
						|| givenMonth.contains("sep") || givenMonth.contains("oct") || givenMonth.contains("nov") || givenMonth.contains("dec")){

					handler.setKeyWord(givenMonth + SPACE + outputYear);
					return false;
				} else {
					return true;
				}
			}


			com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
			boolean isEndFormatCorrect = followStandardFormat(taskInfo,logger);
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
			assert sdf != null;
			sdf.setLenient(false);


			if (isEndFormatCorrect == true) {
				logger.log(Level.INFO,"Endtime use our time format");
				try {
					endingDate = (Date)sdf.parse(taskInfo);
					handler.setHasEndDate(true);
					handler.getTask().setEndTime(endingDate);
				} catch (ParseException e) {
					logger.log(Level.WARNING,"EndTime is invalid in our time format");
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
					return false;
				}
			} else {
				List<DateGroup> dateGroup2 = dateParser.parse(taskInfo);
				logger.log(Level.INFO,"Endtime use Natty");
				if (dateGroup2.isEmpty()) {
					return true;
				}						
				List<Date> date2 = dateGroup2.get(0).getDates();
				endingDate = date2.get(0);
				handler.setHasEndDate(true);
				handler.getTask().setEndTime(endingDate);
			}
			return false;
		}

	}
	private boolean followStandardFormat(String dateTimeString,Logger logger){
		String[] dateTimeArr = dateTimeString.trim().split(" ");

		if (dateTimeArr.length != 4) {
			return false;
		} else {
			// dateTimeArr len should be 4 here, now check end time
			String time = dateTimeArr[0];
			if ((time.contains(":") && (time.length() == 4 || time.length()== 5))) {

				int date;
				logger.log(Level.INFO, "start to process the date");
				try {
					date = Integer.parseInt(dateTimeArr[1], 10);
				} catch (NumberFormatException e) {
					logger.log(Level.WARNING, "the format for the date is invalid");
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
