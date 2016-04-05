//@@author A0112659A
package simplyamazing.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.joestelmach.natty.*;

import simplyamazing.data.Task;

public class ParserEdit {
	private static final String ERROR_MESSAGE_INVALID_INDEX = "Error: Index provided is not an Integer.";
	private static final String ERROR_MESSAGE_INVALID_FIELD = "Error: Please input a valid field. Use the \"help edit\" command to see all the valid fields";
	private static final String ERROR_MESSAGE_START_AFTER_END ="Error: Start date and time cannot be after the End date and time";
	private static final String ERROR_MESSAGE_DATE_BEFORE_CURRENT ="Error: Time provided must be after the current time";
	private static final String ERROR_MESSAGE_PRIORITY_LEVEL = "Error: Priority level can be only high, medium, low or none.";
	private static final String ERROR_MESSAGE_TIME_FORMAT_INVALID ="Error: Please ensure the time format is valid. Please use the \"help\"command to view the format";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static Date startingDate = null;
	private static Date endingDate = null;
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex) throws Exception {
		com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
		if (isInteger(taskIndex)) {
			handler.setIndex(taskIndex);
		} else {
			handler.setHasError(true);
			handler.setFeedBack(ERROR_MESSAGE_INVALID_INDEX);
			return handler;
		}
		String[] fieldValuePairs = taskInfoWithoutIndex.split(",");
		
			for (int i = 0; i < fieldValuePairs.length; i++) {
				String field = Parser.getFirstWord(fieldValuePairs[i]);
				String value = Parser.removeFirstWord(fieldValuePairs[i]);

				switch (field.toLowerCase()) {
				case "description" :
					handler.getTask().setDescription(value);
					break;
				case "start" :
					List<DateGroup> dateGroup1 = dateParser.parse(value);
					if(dateGroup1.isEmpty()){
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						break;
					}
					SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
					sdf.setLenient(true);
					List<Date> date1 = dateGroup1.get(0).getDates();
					startingDate = date1.get(0);
					handler.getTask().setStartTime(startingDate);
					break;
				case "end" :
					List<DateGroup> dateGroup2 = dateParser.parse(value);
					if(dateGroup2.isEmpty()){
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
						break;
					}
					SimpleDateFormat sdf2 = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
					sdf2.setLenient(true);
					List<Date> date2 = dateGroup2.get(0).getDates();
					endingDate = date2.get(0);
					handler.getTask().setEndTime(endingDate);
					break;
				case "priority" :
					try{
					handler.getTask().setPriority(value);
					}catch(Exception e){
						handler.setHasError(true);
						handler.setFeedBack(ERROR_MESSAGE_PRIORITY_LEVEL);
					}
					break;
				default :
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_INVALID_FIELD);
				}
			}
			
			     startingDate = handler.getTask().getStartTime();
			     endingDate = handler.getTask().getEndTime();
			Date todayDate = new Date();

			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // if both start time and end time are modified
				if (!startingDate.after(todayDate) || !endingDate.after(todayDate)){ 
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}else if (!endingDate.after(startingDate)){
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // start time is modified
				if (!startingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			} else if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // end time is modified
				if (!endingDate.after(todayDate)) {
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			}  
			/*if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && startingDate.after(endingDate)) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			} else if ((startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && startingDate.before(todayDate)) || (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0 && endingDate.before(todayDate))) {
				handler.setHasError(true);
				handler.setFeedBack(MESSAGE_INVALID_FORMAT);
			}*/
		return handler;
	}

	public static boolean isInteger(String taskInfo) {
		try {
			Integer.parseInt(taskInfo);
		} catch (NumberFormatException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
