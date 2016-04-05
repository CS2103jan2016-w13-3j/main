//@@author A0112659A
package simplyamazing.parser;

import com.joestelmach.natty.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.DateGroup;

public class ParserSearch {
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static Date endingDate = null;
	private static int year;
	private boolean checkValue;

	public Handler parserSearchCommand(Handler handler, String taskInfo) throws Exception {
		checkValue = isSearchingDate(handler,taskInfo);
		if (checkValue == true){
			handler.setKeyWord(taskInfo);
		}
		return handler;
	}
	public boolean isSearchingDate(Handler handler,String taskInfo) throws Exception {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
		boolean isEndFormatCorrect = followStandardFormat(taskInfo);
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
		sdf.setLenient(false);

		if (isEndFormatCorrect == true){
			try{
				endingDate = (Date)sdf.parse(taskInfo);
				handler.setHasEndDate(true);
				handler.getTask().setEndTime(endingDate);
			}catch (ParseException e) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
				return false;
			}
		}else{
			List<DateGroup> dateGroup2 = dateParser.parse(taskInfo);

			if(dateGroup2.isEmpty()){
				return true;
			}						
			List<Date> date2 = dateGroup2.get(0).getDates();
			endingDate = date2.get(0);
			handler.setHasEndDate(true);
			handler.getTask().setEndTime(endingDate);
		}
		return false;

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

				if ((date > 31) || (date < 0)) {	//date given not valid
					return false;
				} else {

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
				}
			} else {
				return false;
			}
			return true;
		}
	}
}
