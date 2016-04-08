//@@author A0112659A
package simplyamazing.parser;

import java.text.ParseException;
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
	private static final String ERROR_MESSAGE_NO_END_TIME = "Error: Unable to allocate a start time when the task has no end time";
	private static final String TIME_FORMAT = "HH:mm dd MMM yyyy";
	private static Date startingDate = null;
	private static Date endingDate = null;
	private static int year;
	public Handler parseEditCommand(Handler handler, String taskIndex, String taskInfoWithoutIndex) throws Exception {
		//com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
		if (isInteger(taskIndex)) {
			handler.setIndex(taskIndex);
		} else {
			handler.setHasError(true);
			handler.setFeedBack(ERROR_MESSAGE_INVALID_INDEX);
			return handler;
		}
		String[] fieldValuePairs = taskInfoWithoutIndex.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT,Locale.ENGLISH);
		sdf.setLenient(false);

		for (int i = 0; i < fieldValuePairs.length; i++) {
			String field = Parser.getFirstWord(fieldValuePairs[i]);
			String value = Parser.removeFirstWord(fieldValuePairs[i]);

			switch (field.toLowerCase()) {
			case "description" :
				handler.getTask().setDescription(value);
				break;
			case "start" :				
				if(value.toLowerCase().equals("none")){
					handler.getTask().setStartTime(Task.DEFAULT_DATE_VALUE_FOR_NULL);
				} else {
					boolean isStartFormatCorrect = followStandardFormat(value);
					editStartTime(sdf,isStartFormatCorrect,handler,value);
					handler.getTask().setStartTime(startingDate);
				}
				break;
			case "end" :
				if(value.toLowerCase().equals("none")){
					handler.getTask().setEndTime(Task.DEFAULT_DATE_VALUE_FOR_NULL);
				} else {
					boolean isEndFormatCorrect = followStandardFormat(value);
					editEndTime(sdf,isEndFormatCorrect,handler,value);
					handler.getTask().setEndTime(endingDate);
				}
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
			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0) { 
				if (!startingDate.after(todayDate) || !endingDate.after(todayDate)) { 
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}else if (!endingDate.after(startingDate)){
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_START_AFTER_END);
				}
			} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)==0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0) { // start time set as none
				if (!endingDate.after(todayDate)) { 
					handler.setHasError(true);
					handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
				}
			} else if(startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0 && endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)==0){ // end time set as none
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_NO_END_TIME);
			}
		} else if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // start time is modified
			if (startingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0 && !startingDate.after(todayDate)) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_DATE_BEFORE_CURRENT);
			}

		} else if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE)!=0) { // end time is modified
			if (endingDate.compareTo(Task.DEFAULT_DATE_VALUE_FOR_NULL)!=0 && !endingDate.after(todayDate)) {
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
    private void editStartTime(SimpleDateFormat sdf,boolean isStartFormatCorrect,Handler handler,String value){
    	com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
    	if(isStartFormatCorrect == true){
			System.out.println("startTime use our format");
			try{
				startingDate = (Date)sdf.parse(value);
			}catch (ParseException e) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
		}else{ 
			System.out.println("startTime use Natty");
			List<DateGroup> dateGroup3 = dateParser.parse(value);

			if(dateGroup3.isEmpty()){
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
			List<Date> date3 = dateGroup3.get(0).getDates();
			startingDate = date3.get(0);
		}
    }
    private void editEndTime(SimpleDateFormat sdf,boolean isEndFormatCorrect,Handler handler,String value){
    	com.joestelmach.natty.Parser dateParser = new com.joestelmach.natty.Parser();
    	if(isEndFormatCorrect == true){
			System.out.println("endTime use our format");
			try{
				endingDate = (Date)sdf.parse(value);
			}catch (ParseException e) {
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
		}else{
			System.out.println("endTime use Natty");
			List<DateGroup> dateGroup4 = dateParser.parse(value);

			if(dateGroup4.isEmpty()){
				handler.setHasError(true);
				handler.setFeedBack(ERROR_MESSAGE_TIME_FORMAT_INVALID);
			}
			List<Date> date4 = dateGroup4.get(0).getDates();
			endingDate = date4.get(0);
		}
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
